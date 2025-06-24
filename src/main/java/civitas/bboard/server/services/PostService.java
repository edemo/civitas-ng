package civitas.bboard.server.services;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import civitas.bboard.common.BBPost;
import civitas.bboard.common.BBPostRepository;
import civitas.bboard.server.GetBoardForId;
import civitas.bboard.server.electioncache.UpdateCache;
import civitas.common.ConvertToXml;
import civitas.common.GetServerPrivateKey;
import civitas.common.LoggerService;
import civitas.crypto.CryptoError;
import civitas.crypto.messagedigest.CryptoHash;
import civitas.crypto.signature.SignWithPublicKey;
import civitas.crypto.signature.Signature;

@Service
public class PostService {
	@Autowired
	GetBoardForId getBoardForId;
	@Autowired
	BBPostRepository bBPostRepository;
	@Autowired
	CryptoHash cryptoHash;
	@Autowired
	LoggerService loggerService;
	@Autowired
	UpdateCache updateCache;
	@Autowired
	ConvertToXml convertToXml;
	@Autowired
	SignWithPublicKey signWithPublicKey;
	@Autowired
	GetServerPrivateKey getServerPrivateKey;

	@PostMapping("/boards/{bbid}")
	public long apply(@PathVariable("bbid") String bbid, String meta, String mesg,
			Signature sign) throws IOException {
		getBoardForId.apply(bbid, true);

		long t = System.currentTimeMillis();
		updateCache.apply(bbid, meta, mesg, t);

		Iterable<BBPost> lastPosts = bBPostRepository
				.findByBbidOrderBySerialDesc(bbid);

		byte[] lastHash = null;
		long lastSerial = 0;
		if (lastPosts.iterator().hasNext()) {
			BBPost lastPost = lastPosts.iterator().next();
			lastHash = lastPost.hash;
			lastSerial = lastPost.serial;
		}
		byte[] newhash = cryptoHash.apply(lastHash, sign.signature);

		loggerService.apply(MarkerFactory.getMarker("bbs_post"), meta);

		bBPostRepository
				.save(new BBPost(bbid, lastSerial + 1, t, meta, mesg, sign, newhash));

		return t;
	}

	public long apply(String bbid, String meta, Object mesg)
			throws IOException, InvalidKeyException, NoSuchAlgorithmException,
			NoSuchProviderException, SignatureException, CryptoError,
			IllegalArgumentException, InvalidKeySpecException {

		String msg = convertToXml.apply(mesg);
		Signature sign = signWithPublicKey.apply(getServerPrivateKey.apply(),
				msg.getBytes());
		return apply(bbid, meta, msg, sign);
	}
}
