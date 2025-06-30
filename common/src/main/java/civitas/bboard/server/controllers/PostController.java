package civitas.bboard.server.controllers;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import org.bouncycastle.crypto.CryptoException;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import civitas.bboard.common.BBPost;
import civitas.bboard.common.BBPostRepository;
import civitas.bboard.server.GetBoardForId;
import civitas.bboard.server.electioncache.UpdateCache;
import civitas.common.CheckAccess;
import civitas.common.Configuration;
import civitas.common.ConvertToXml;
import civitas.common.GetPrivateKey;
import civitas.common.GetPublicKey;
import civitas.common.LoggerService;
import civitas.common.Operation;
import civitas.crypto.messagedigest.CryptoHash;
import civitas.crypto.rsapublickey.VerifyPublicKeySignature;
import civitas.crypto.signature.SignWithPublicKey;
import civitas.crypto.signature.Signature;
import civitas.util.GetCurrentTime;
import jakarta.xml.bind.JAXBException;

@Controller
//@RestController
public class PostController {
	@Autowired
	GetBoardForId getBoardForId;
	@Autowired
	BBPostRepository bBPostRepository;
	@Autowired
	CryptoHash cryptoHash;
	@Autowired
	LoggerService loggerController;
	@Autowired
	UpdateCache updateCache;
	@Autowired
	ConvertToXml convertToXml;
	@Autowired
	SignWithPublicKey signWithPublicKey;
	@Autowired
	GetPrivateKey getPrivateKey;
	@Autowired
	GetPublicKey getPublicKey;
	@Autowired
	GetCurrentTime getCurrentTime;
	@Autowired
	VerifyPublicKeySignature verifyPublicKeySignature;
	@Autowired
	CheckAccess checkAccess;
	@Autowired
	Configuration configuration;

	@PostMapping("/boards/{bbid}")
	public @ResponseBody Long apply(@PathVariable("bbid") String bbid,
			@RequestBody PostDTO dto) throws CommunicableException {
		String objectID = dto.meta + bbid;
		checkAccess.apply(Operation.POST, dto.signature.getSignerPubKey(),
				objectID);
		try {
			if (!verifyPublicKeySignature.apply(dto.signature,
					dto.payloadXml.getBytes())) {
				throw new CommunicableException("bad signature");
			}
		} catch (CommunicableException | CryptoException e) {
			throw new CommunicableException("bad signature (but in blue)");
		}

		loggerController.apply(MarkerFactory.getMarker("bbs_post"), objectID);

		getBoardForId.apply(bbid, true);

		long t = getCurrentTime.apply();

		Iterable<BBPost> lastPosts = bBPostRepository
				.findByBbidOrderBySerialDesc(bbid);

		BBPost lastPost = null;
		if (lastPosts.iterator().hasNext()) {
			lastPost = lastPosts.iterator().next();
		}
		byte[] newhash = cryptoHash.apply(lastPost, t, dto.signature);

		try {
			updateCache.apply(bbid, dto.meta, dto.payloadXml, t);
		} catch (IOException | JAXBException e) {
			throw new CommunicableException("bad payload xml");
		}

		Long serial = 0L;
		if (lastPost != null)
			serial = lastPost.serial;
		bBPostRepository.save(new BBPost(bbid, serial + 1, t, dto.meta,
				dto.payloadXml, dto.signature, newhash));

		return t;
	}

	public long apply(String bbid, String meta, Object mesg)
			throws JAXBException, UnrecoverableKeyException, KeyStoreException,
			NoSuchAlgorithmException, CertificateException, IOException,
			CryptoException, CommunicableException {
		String msg = convertToXml.apply(mesg);
		PrivateKey privKey = getPrivateKey.apply(configuration.storeFile,
				configuration.storePassword, configuration.serverKeyEntry);
		PublicKey pubKey = getPublicKey.apply(configuration.storeFile,
				configuration.storePassword, configuration.serverKeyEntry);
		Signature sign = signWithPublicKey.apply(privKey, pubKey, msg.getBytes());
		return apply(bbid, new PostDTO(meta, msg, sign));
	}
}
