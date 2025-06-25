package civitas.bboard.server.services;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import civitas.bboard.common.BBPost;
import civitas.bboard.common.BBPostRepository;
import civitas.bboard.server.GetBoardForId;
import civitas.common.GetServerPrivateKey;
import civitas.common.GetServerPublicKey;
import civitas.crypto.CryptoError;
import civitas.crypto.messagedigest.CryptoHash;
import civitas.crypto.signature.SignWithPublicKey;
import civitas.crypto.signature.Signature;

@Service
public class RetrieveHashService {
	@Autowired
	GetBoardForId getBoardForId;
	@Autowired
	BBPostRepository bBPostRepository;
	@Autowired
	CryptoHash cryptoHash;
	@Autowired
	SignWithPublicKey signWithPublicKey;
	@Autowired
	GetServerPrivateKey getServerPrivateKey;
	@Autowired
	GetServerPublicKey getServerPublicKey;

	@GetMapping("/boards/{bbid}/signature-{fromTime}-{toTime}-{metaCriteria}")
	private Signature apply(@PathVariable("bbid") String bbid,
			@PathVariable("fromTime") Long fromTime,
			@PathVariable("toTime") Long toTime,
			@PathVariable("metaCriteria") String metaCriteria)
			throws IOException, InvalidKeyException, NoSuchAlgorithmException,
			NoSuchProviderException, SignatureException, CryptoError,
			IllegalArgumentException, InvalidKeySpecException {

		getBoardForId.apply(bbid, true);

		Iterable<BBPost> posts = bBPostRepository
				.findByBbidAndTimestampBetweenAndMeta(bbid, fromTime, toTime,
						metaCriteria);

		byte[] hash = null;
		Iterator<BBPost> iter = posts.iterator();
		while (iter.hasNext()) {// FIXME probably this should be something else
			hash = cryptoHash.apply(iter.next(), hash);
		}

		Signature signature = signWithPublicKey.apply(getServerPrivateKey.apply(),
				getServerPublicKey.apply(), hash);

		return signature;

	}

}
