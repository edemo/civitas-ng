package civitas.bboard.server.controllers;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import org.bouncycastle.crypto.CryptoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import civitas.bboard.common.BBPost;
import civitas.bboard.common.BBPostRepository;
import civitas.bboard.server.GetBoardForId;
import civitas.common.Configuration;
import civitas.common.GetPrivateKey;
import civitas.common.GetPublicKey;
import civitas.crypto.messagedigest.CryptoHash;
import civitas.crypto.signature.SignWithPublicKey;
import civitas.crypto.signature.Signature;

@Controller
public class RetrieveHashController {
	@Autowired
	GetBoardForId getBoardForId;

	@Autowired
	BBPostRepository bBPostRepository;

	@Autowired
	CryptoHash cryptoHash;

	@Autowired
	SignWithPublicKey signWithPublicKey;

	@Autowired
	GetPrivateKey getPrivateKey;

	@Autowired
	Configuration configuration;

	@Autowired
	GetPublicKey getServerPublicKey;

	@GetMapping("/boards/{bbid}/signature-{fromTime}-{toTime}-{metaCriteria}")
	private Signature apply(
			@PathVariable("bbid") String bbid,
			@PathVariable("fromTime") Long fromTime,
			@PathVariable("toTime") Long toTime,
			@PathVariable("metaCriteria") String metaCriteria)
			throws CommunicableException {

		getBoardForId.apply(bbid, true);

		Iterable<BBPost> posts =
				bBPostRepository.findByBbidAndTimestampBetweenAndMeta(bbid, fromTime, toTime, metaCriteria);

		byte[] hash = null;
		for (BBPost post : posts) { // FIXME probably this should be something else
			hash = cryptoHash.apply(post.sig.signatureBytes, hash);
		}

		Signature signature;
		try {
			PrivateKey privKey;
			privKey = getPrivateKey.apply(
					configuration.storePassword, configuration.storeFile, configuration.serverKeyEntry);
			PublicKey pubKey = getServerPublicKey.apply(
					configuration.storePassword, configuration.storeFile, configuration.serverKeyEntry);
			signature = signWithPublicKey.apply(privKey, pubKey, hash);
		} catch (UnrecoverableKeyException
				| KeyStoreException
				| NoSuchAlgorithmException
				| CertificateException
				| IOException
				| CryptoException e) {
			throw new CommunicableException("internal error");
		}

		return signature;
	}
}
