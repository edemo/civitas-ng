package civitas.crypto.concrete;

import civitas.common.TestUtil;

public interface ElGamalCiphertextCTestData
		extends BasicValuesTestData, ElGamalParametersCTestData {

	public static final String XML_ELGAMALCIPHERTEXT_TRUNCATED = "<elGamalCiphertext><a>ESIQ9LFs";
	public static final String XML_ELGAMALCIPHERTEXT_ = "<elGamalCiphertext><a>"
			+ SOMESTRING_BASE64 + "</a><b>Xje5W2KfxNk=</b></elGamalCiphertext>";
	public static final String XML_ELGAMALCIPHERTEXT_ANULL = "<elGamalCiphertext><a></a><b>Xje5W2KfxNk=</b></elGamalCiphertext>";
	public static final String XML_ELGAMALCIPHERTEXT_BNULL = "<elGamalCiphertext><a>Xje5W2KfxNk=</a><b></b></elGamalCiphertext>";

	public static final String SHARED_KEY_CIPHERTEXT_XML = "<sharedKeyCiphertext>"
			+ SOMESTRING_BASE64 + "</sharedKeyCiphertext>";
	public static final String PUBLIC_KEY_CIPHERTEXT_XML = "<publicKeyCiphertext>"
			+ SOMESTRING_BASE64 + "</publicKeyCiphertext>";
	public static final String EL_GAMAL_CIPHERTEXT_NAIVE_XML = "<elGamalCiphertext><a>42</a><b>24</b></elGamalCiphertext>";
	public static final ElGamalCiphertextC EL_GAMAL_CIPHERTEXT = new ElGamalCiphertextC(
			ConcreteTestData.GENERATOR_OTHER.modPow(
					ConcreteTestData.REENCRYPT_FACTOR_RANDOMS_1.r,
					ConcreteTestData.BIGINT_P),
			BIGINT_D);

	public static final String EL_GAMAL_CIPHERTEXT_A_XML = "<elGamalCiphertext><a>"
			+ "aHyajDBKPVjLQxSlx21hl1GeVCkrfvctLo4aiwJmas8q20tfehNBRA/O9q1vZhaO2boKwwcEPb0IzIMGgtVUx5UpKQhL8AM6ogZlsuQrHpw5KzdQzyoB/9MPCanfYmdeSRJWkQCYl3dbRiAbp8wTrw9Ft8xooZq0TKPJriBzs1h0i5aUjVzlqrF6T72MDOjem6bS8hFJeqflm8x74e89r+hUeNr/RGGRBbygufzeFTeESJLMfXq1U9aLAszKj4LK21leO9B0nusszhRg8Bz3EcHc6dbGh+2qxMpccvYkG6nxnWq5fEdEpY4+z7YhalrQ8r/JODe2vq5UcIQDnW2toPqD5gKWMRU31V0jrlVKq1phCEvsU5fDDpmE41bOmRoozxN/eJbnAO0Ks+rZzUxXSlYTHiZZ9hfFcZ4uLJbxLXWztBvMJbhlAR1on240A+YSxOHnpzFJGA9TykeD103kJnUJaBmCstYmBIC3g6TDt/4/kbKWD6yiUNj3OnOADbJF"
			+ "</a><b>"
			+ "D7HknJmoALCBli6uEl1zUAMJfcK17oLQwBkEXjCKDsnJZrOJyWbgkBb1SC31VdONSUyqyjfPa682mlcT1vTHfOBJd7wGqWgDe+ESmx+p+ajCK88uXNCNWQRr8zkThLM0a/rGQzB5rBBqg861ntaJTBFGxgEf55nsp+MpQT3XuckNLQwpxH2750x+fvbdbxDoSRMHyApgBdS0eYq+Hg61eW93AdOcRAPpXPP0kUpsKNVDe8YDRkBcWD3x81lcwJdRfQaWVld0CK4AIqLeKcD9k08LQmVVEKXq/FArU5I/AkTHKb3Nj1zK6Xkc2o+Oa1/rjdXH4NGT6iZJ/umUECHg7Nmi6e47JsQOFAbPY2SRrbK6YKTXGCMb1SQLmuaQV6ABqV08heMLoTereu4R/gqPr2f0MjE8o455sq3vRo/OrkHgnEN9lIWVxEjSETf3ddjhTT5QPkhN/dIr90s8TQG5KhYQR5jG+qdC2R0iN+434LH7oc1mrcnY/dk10fDPzjAm"
			+ "</b></elGamalCiphertext>";
	public static final String EL_GAMAL_CIPHERTEXT_B_XML = "<elGamalCiphertext><a>"
			+ G_EXP_D_BASE64 + "</a><b>"
			+ "Frns4N1ve8I2rNBMAqVLSLEyE7KfjPe00W4/+O2+65VvuKXrhqmHjweV1RvASArlZkyVeb0hG6caIIaH8k0FNavCyO6HgIgqa75BV51on+OGO/RTs+D5N0R4/GuRQIkNg4P+nVYCdqFIXEgIFcB++5RoO2WfgrSydzkzLbFq5y++pZ3mYDfOr44nFZ+hGJVSdoD7TBm8ywlTgRKZNbOZITOnfjJ7Oe2wiG18X7Ht4KDMgrpigg6fhrlHhzSfloFsLwHZ3b0i6BrYoymWwVT1t4yp4Athx8nbdqS1yWpDzow3Vm/gcLWmmKVliYlc2ukvvPsg7LcfvKgUEhjTflDWmKXuUSj/oahq+ZHuvBk+VE6GEl7P2zcuYPlxxcfQwxh6olc0d2ijr3Mj9QxmmsgQYWpqfpeeYNKPNOIRtupjoHq7X17GT0hma7F3jR4jrmC4i7SR2kgZONBFeb01bK1VdGhEXFIXGERNLXExycHGS6vqGwDhoKxFbjfdr7Nwi50z"
			+ "</b></elGamalCiphertext>";

	public static final ElGamalCiphertextC EL_GAMAL_CIPHERTEXT_A = TestUtil
			.ElGamalCiphertextCfromXML(EL_GAMAL_CIPHERTEXT_A_XML);
	public static final ElGamalCiphertextC EL_GAMAL_CIPHERTEXT_B = TestUtil
			.ElGamalCiphertextCfromXML(EL_GAMAL_CIPHERTEXT_B_XML);

}
