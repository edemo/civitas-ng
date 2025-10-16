package civitas.bboard.server;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bouncycastle.crypto.CryptoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

import civitas.bboard.common.BBPostTestData;
import civitas.bboard.server.controllers.GetRestTemplate;
import civitas.bboard.server.controllers.NewBoardController;
import civitas.bboard.server.controllers.PostController;
import civitas.bboard.server.controllers.PostDTO;
import civitas.bboard.server.controllers.RequestParticipationController;
import civitas.bboard.server.controllers.RequestParticipationDTO;
import civitas.common.Configuration;
import civitas.common.GetPrivateKey;
import civitas.common.ServerHost;
import civitas.common.ServerRole;
import civitas.common.board.BulletinBoardTestData;
import civitas.common.election.ElectionDetailsTestData;
import civitas.crypto.CryptoBase;
import civitas.crypto.rsapublickey.ConvertPublicKeyToString;
import civitas.crypto.rsapublickey.PublicKeyTestData;
import civitas.crypto.signature.SignWithPublicKey;
import civitas.crypto.signature.Signature;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class EndToEndTest implements BulletinBoardTestData, BBPostTestData, ElectionDetailsTestData, PublicKeyTestData {
	@LocalServerPort
	private int port;

	@Autowired
	GetRestTemplate getRestTemplate;

	@Autowired
	SignWithPublicKey signWithPublicKey;

	@Autowired
	CryptoBase cryptoBase;

	@Autowired
	PostController postController;

	@Autowired
	NewBoardController newBoardController;

	@Autowired
	RequestParticipationController requestParticipationController;

	@Autowired
	Configuration configuration;

	@Autowired
	GetPrivateKey getPrivateKey;

	@Autowired
	ConvertPublicKeyToString convertPublicKeyToString;

	private RSAPublicKey supervisorPub;

	private java.security.PrivateKey supervisorPriv;

	private Map<String, PublicKey> keys;

	private Map<String, String> keyStrings;

	@Test
	@Tag("endtoend")
	@DisplayName("starting an election and posting to the board related to it")
	void testPost() throws Exception {
		Long startTime = System.currentTimeMillis();
		String urlBase = "http://localhost:" + port;
		getTestKeys();
		String boardId = getRestTemplate
				.apply()
				.postForObject(urlBase + "/requestParticipation", createRequestParticipationDTO(), String.class);

		String url = urlBase + "/boards/" + boardId;
		Long actual = getRestTemplate.apply().postForObject(url, createPostDTO(), Long.class);
		long endTime = System.currentTimeMillis();
		assertTrue(actual >= startTime && actual <= endTime);
	}

	public PostDTO createPostDTO() throws CryptoException {
		Signature signature =
				signWithPublicKey.apply(supervisorPriv, supervisorPub, BOARD_CLOSED_CONTENT_COMMITMENT_XML.getBytes());
		return new PostDTO(BOARD_CLOSED_CONTENT_COMMITMENT_META, BOARD_CLOSED_CONTENT_COMMITMENT_XML, signature);
	}

	public RequestParticipationDTO createRequestParticipationDTO() {
		List<ServerHost> tellerDetails = List.of(
				new ServerHost(ServerRole.BBS, "https://localhost/", keyStrings.get("bbs")),
				new ServerHost(
						ServerRole.TABULATION_TELLER, "https://localhost:" + port + "/", keyStrings.get("tabteller")),
				new ServerHost(
						ServerRole.REGISTRATION_TELLER,
						"https://localhost:" + port + "/",
						keyStrings.get("regteller")));
		return RequestParticipationDTO.builder()
				.electionID(ELECTION_ID_STRING)
				.supervisorPubkey(PUBLIC_KEY_BASE64)
				.registrarPubKey(PUBLIC_KEY2_BASE64)
				.name(ELECTION_NAME)
				.description(ELECTION_DESCRIPTION)
				.version(VERSIONSTRING)
				.ballotDesign(BALLOTDESIGN)
				.startTime(START_TIME)
				.stopTime(STOP_TIME)
				.finalizeTime(FINALIZE_TIME)
				.elGamalP(BIGINT_P.i)
				.elGamalQ(BIGINT_Q.i)
				.elGamalG(BIGINT_G.i)
				.sharedKeyLength(KEYSIZE)
				.nonceLength(NONCE_LENGTH)
				.voterAnonymityParam(BLOCKSIZE)
				.tellerDetails(tellerDetails)
				.build();
	}

	public void getTestKeys()
			throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException,
					UnrecoverableKeyException {
		char[] pwdArray = "test12345".toCharArray();
		KeyStore store = KeyStore.getInstance(new File("lib/server.jks"), pwdArray);
		supervisorPriv = (java.security.PrivateKey) store.getKey("supervisor", pwdArray);
		keys = new HashMap<>();
		keyStrings = new HashMap<>();

		for (String name : List.of(
				"supervisor", "registrar", "bbs", "regteller", "tabteller", "user1", "user2", "user3", "user4")) {
			PublicKey publicKey = store.getCertificate(name).getPublicKey();
			keys.put(name, publicKey);
			keyStrings.put(name, convertPublicKeyToString.apply(publicKey));
		}
		supervisorPub = (RSAPublicKey) keys.get("supervisor");
	}
}
