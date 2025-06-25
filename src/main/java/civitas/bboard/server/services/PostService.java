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
import civitas.common.CheckAccess;
import civitas.common.ConvertToXml;
import civitas.common.GetServerPrivateKey;
import civitas.common.GetServerPublicKey;
import civitas.common.LoggerService;
import civitas.common.Operation;
import civitas.crypto.CryptoError;
import civitas.crypto.messagedigest.CryptoHash;
import civitas.crypto.rsapublickey.VerifyPublicKeySignature;
import civitas.crypto.signature.SignWithPublicKey;
import civitas.crypto.signature.Signature;
import civitas.util.GetCurrentTime;

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
	@Autowired
	GetServerPublicKey getServerPublicKey;
	@Autowired
	GetCurrentTime getCurrentTime;
	@Autowired
	VerifyPublicKeySignature verifyPublicKeySignature;
	@Autowired
	CheckAccess checkAccess;

	@PostMapping("/boards/{bbid}")
	public long apply(@PathVariable("bbid") String bbid, String meta, String mesg,
			Signature sign) throws IOException, SecurityException {
		String objectID = meta + bbid;
		checkAccess.apply(Operation.POST, sign.getSigner(), objectID);
		if (!verifyPublicKeySignature.apply(sign, mesg.getBytes())) {
			throw new IllegalArgumentException("bad signature");
		}

		loggerService.apply(MarkerFactory.getMarker("bbs_post"), objectID);

		getBoardForId.apply(bbid, true);

		long t = getCurrentTime.apply();

		Iterable<BBPost> lastPosts = bBPostRepository
				.findByBbidOrderBySerialDesc(bbid);

		BBPost lastPost = null;
		if (lastPosts.iterator().hasNext()) {
			lastPost = lastPosts.iterator().next();
		}
		byte[] newhash = cryptoHash.apply(lastPost, t, sign);

		updateCache.apply(bbid, meta, mesg, t);

		Long serial = 0L;
		if (lastPost != null)
			serial = lastPost.serial;
		bBPostRepository
				.save(new BBPost(bbid, serial + 1, t, meta, mesg, sign, newhash));

		return t;
	}

	public long apply(String bbid, String meta, Object mesg) throws IOException,
			InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException,
			SignatureException, CryptoError, IllegalArgumentException,
			InvalidKeySpecException, IllegalAccessException {

		String msg = convertToXml.apply(mesg);
		Signature sign = signWithPublicKey.apply(getServerPrivateKey.apply(),
				getServerPublicKey.apply(), msg.getBytes());
		return apply(bbid, meta, msg, sign);
	}
}
