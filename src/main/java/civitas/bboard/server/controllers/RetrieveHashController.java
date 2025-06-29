package civitas.bboard.server.controllers;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Iterator;

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
import civitas.crypto.CryptoError;
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
	private Signature apply(@PathVariable("bbid") String bbid,
			@PathVariable("fromTime") Long fromTime,
			@PathVariable("toTime") Long toTime,
			@PathVariable("metaCriteria") String metaCriteria)
			throws InvalidKeyException, UnrecoverableKeyException,
			NoSuchAlgorithmException, NoSuchProviderException, SignatureException,
			KeyStoreException, CertificateException, CryptoError, IOException {

		getBoardForId.apply(bbid, true);

		Iterable<BBPost> posts = bBPostRepository
				.findByBbidAndTimestampBetweenAndMeta(bbid, fromTime, toTime,
						metaCriteria);

		byte[] hash = null;
		Iterator<BBPost> iter = posts.iterator();
		while (iter.hasNext()) {// FIXME probably this should be something else
			hash = cryptoHash.apply(iter.next(), hash);
		}

		PrivateKey privKey = getPrivateKey.apply(configuration.storePassword,
				configuration.storeFile, configuration.serverKeyEntry);
		PublicKey pubKey = getServerPublicKey.apply(configuration.storePassword,
				configuration.storeFile, configuration.serverKeyEntry);
		Signature signature = signWithPublicKey.apply(
				privKey,
				pubKey,
				hash);

		return signature;

	}

}
