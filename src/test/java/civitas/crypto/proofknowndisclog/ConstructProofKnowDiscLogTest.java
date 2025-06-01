package civitas.crypto.proofknowndisclog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.crypto.messagedigest.CryptoHash;
import civitas.util.Tested;
import civitas.util.Use;

public class ConstructProofKnowDiscLogTest extends TestBase
		implements ProofKnowDiscLogTestData {

	@Tested
	ConstructProofKnowDiscLog constructProofKnowDiscLog;
	@Use
	CryptoHash cryptoHash;

	@Test
	@DisplayName("constructProofKnowDiscLog constructs the proof correctly:"
			+ "v:=g^key (mod p), " + "z:=random, " + "a:=g^z (mod p), "
			+ "c:=hash(v,a)%q, " + "r:=z+c*key (mod q)")
	void constructProofKnowDiscLogTest() throws Exception {

		ElGamalProofKnowDiscLog proof = constructProofKnowDiscLog
				.apply(EL_GAMAL_PARAMETERS, EL_GAMAL_PRIVATE_KEY_E);

		assertEquals(
				"<elGamalProofKnowDiscLog><a>cZP5shp3SOALmUwEY6Zj6crdGhQzIB7fkbIS9sLCUzI4tZDlzROdfCm6EHz1sd5ekqrVwzRfwKBBuOrGeoujtfXJ7e1U5nxgCzcXTJAwhIsuM0KVyvgPV8krRzzRf1/hP7hSwzh4eWCpWKmzfREuoJBe0RKnrv+jBi0U46tSRRgz9qg5x3K92fTEtiCWQnLb5JlA+VWbJNxsdGm99o2t4wcmRdK2ZmOYFPdxmpCLLV5eCWCHCO/rZxBtWwhKScvwhjjbcnSlefNb4MXjEwk3j97Uulz7rt35KKDUMmcxDjpwHbkoAl7lbhtXNPgaPIrcIM60DDdclM8cwXz1ItvUbOzlprPLgjyskGj4g0Kmz+K6v/NfFRCKM2FA9QKlMSlpGWGsQt68+EYAvgTNAdoiSNdoDP+YBY1B6ITg384VcHOIzBhORrLupPR9iPUoIvU0GelBNciOhXjnFUjuWTSpsoCj47N9GBq9GGsYXpTBuXTMI/9wIsqUPT+sjOrvQERl</a><c>V0PAc6rSjBiKZU6P5kIU65fcVr5+eIekC1ABoOrgmvs=</c><r>ElIxK5N4qOvnJH6VBw+5us9mO1M/Y0t8WhDxjNKnHXk=</r><v>bYNRiWtK8H8jaehxs1S+YKemiiip2QacwnwcQqKHsPBE1KzsZEBVquxOmPa8FGLV4hUd5okpWdQImk9ntTNCKC3q9yq7Grr7xUAoS3QMwQ0N4B18eENIjUO9eACm6IcPf+3tK87fp7h35ZQHA0XJJNxSVRz97ARYjIcXg9x/jBPbsHqXDfkrAhfneWqBajxu5+8kygrp3pS/DVZI9cB/WUvfS5idySJ/YLQKzcXxlqSPeRdHN3vNyE/DaJ2MGHqiEJE1qo2p3kc5R31gceixV9WQFQn7PJ9RrrZ1Isvar+zpsStQdH92Kr0KU+3d/fxuJnpxToA0J8J/mpIat7uj9jugW5qCJ1ERRarjcbjtpi1jZKaDB28704VzudfgAQq5U3O+0MsWRgJ6MClQvWWwXiPK9xAmm7u3b6tvUBemAQhvAbpYgoCmyuq8UiDo7H7EiAB27OcpfrjbprUlEkux7DapIW+X6V85qqZX/myyjnO7kS9l2ju3JYUGOA2A0IlM</v></elGamalProofKnowDiscLog>",
				EL_GAMAL_PROOF_KNOWN_DISC_LOG_XML);
		assertEquals(EL_GAMAL_PROOF_KNOWN_DISC_LOG, proof);

	}

	@Test
	@DisplayName("constructProofKnowDiscLog returns null if the key is null")
	void constructProofKnowDiscLogTest1() throws Exception {

		assertNull(constructProofKnowDiscLog.apply(EL_GAMAL_PARAMETERS, null));

	}

	@Test
	@DisplayName("constructProofKnowDiscLog returns null if the parameters is null")
	void constructProofKnowDiscLogTest3() throws Exception {

		assertNull(constructProofKnowDiscLog.apply(null, EL_GAMAL_PRIVATE_KEY_E));

	}

}
