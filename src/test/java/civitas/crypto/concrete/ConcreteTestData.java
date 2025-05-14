package civitas.crypto.concrete;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

import java.io.StringReader;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import civitas.util.CivitasBigInteger;

public class ConcreteTestData {
	public static final String SOMESTRING = "testdata";
	public static final String SOMESTRING_BASE64 = "dGVzdGRhdGE=";
	public static final String SOMESTRING_EXTENDED = "exttestdatawas";
	public static final byte[] BYTES = SOMESTRING.getBytes();
	public static final int SOME_INT = 0xdeadbeef;
	public static final CivitasBigInteger SOME_INT_BIG = CivitasBigInteger
			.valueOf(SOME_INT);
	public static final String ONE_BASE64 = "AQ==";
	public static final long LONG = 0xdeadbeeff001be41L;
	public static final byte[] LONG_AS_BYTES = new byte[] {
			(byte) 0x41,
			(byte) 0xbe,
			(byte) 0x01,
			(byte) 0xf0,
			(byte) 0xef,
			(byte) 0xbe,
			(byte) 0xad,
			(byte) 0xde };

	public static final CivitasBigInteger BIGINT_A = new CivitasBigInteger(
			SOMESTRING.getBytes());
	public static final CivitasBigInteger BIGINT_B = new CivitasBigInteger(
			new BigInteger("6789098765432112345").toByteArray());
	public static final CivitasBigInteger BIGINT_C = new CivitasBigInteger(
			new BigInteger("8765432112678909345").toByteArray());
	public static final String BIGINT_D_BASE64 = "eOmnTcKMIpE=";
	public static final CivitasBigInteger BIGINT_D = new CivitasBigInteger(
			new BigInteger("8712678906543219345").toByteArray());

	public static final String BIGINT_P_BASE64 = "APwphl/4ehyaxnHc9zpabYMgC/uAWu98qOSGrtPkoXkPjcjUAU16qE88YIziIf3pXNfKGRQTHEhkajztMBAKh02/82YEV0jOFlR3HuDKPEhxn5GJfW9SKzuYQkSd76lf0id4xCzAGJLjmQUwOlSFLVtYl0auGciOYoHU2m8qAVNcN+gyyOXKhMwQqPdi15t6q1PeTAGP6wdEBGLHdCHxMVZh5NAEoFWeTbPEyMSI9uy6pYj4HCb7F83vA/O33OcEcgqTd9P96tFrD2/vm6m7CF0BsQZg6nZgMC+SMC7ac1TIiVGtNNxNseXSrK8SVqpSvNBniqmxya08/mcZVZH6CxWLrgLl7Zjcf0Ra2U1sdpVWryI/yKW+99T+DZX9UPMavhVGS+3XTBaUIoWBhmhU+epTMhavIKvihWdB1ePZWfzEGBElS6EsWnt61rlswY6TOqZvq0cHf8qbkQyeu5HNwrtqBywf7T3bFVwhcUugnQBnEc/JjTkwIo5Ask2mNH3r4w==";
	public static final CivitasBigInteger BIGINT_P = new CivitasBigInteger(
			Base64.getDecoder().decode(BIGINT_P_BASE64));
	public static final String BIGINT_Q_BASE64 = "ALEbdrNCujCQI0Y4f38yusJkfR1nVqT95/P/H2z28zIH";
	public static final CivitasBigInteger BIGINT_Q = new CivitasBigInteger(
			Base64.getDecoder().decode(BIGINT_Q_BASE64));
	public static final String BIGINT_G_BASE64 = "XCtSTFQcDDYDNIMInF/LaYu+4G51VqiF5LxlUAztrNuIp64M3K/UiupM+lF3fUrfqncLm/9iD8dspTD+2r29TOQ7PBHgQ3l1Xw9VNS4BNq0pZcsu5XM0vzExk6h9e3Dcw3F/xUSYm4zK8xnTyUZdHiwY0YnF4wAZbRgKVB6LOnZd4LUbBorB4syNlcCLvCO8Pnw3VvysnuqdJw7yhjz5xfZ1W2noUjGVmWgOdvM/JoaSc4vrcIZ+KhUuv7fDoEeUTTWLp+P6xEau/UtmTcvjhEyKifWW7sOVpwKX6tAFDzMxswKGtLtfsWkm/XnbMtTwcq2MQqischgD1pzJBTC70tNIDrnqXFFzuFuez904lYmJV3e5oeEpLomWfwF9zndHXkR6LTCGNsb/QdEDMVcbgOijD1s+bjuy2rHY2UCXSX84IYrYqUIHH4639OasJkUQy5RECjCpbfetWXc5VEjXhFFi2AWHbZEAPWYdAqn6AZUl5+v6CjcRXdt+oC2+JXxD";
	public static final CivitasBigInteger BIGINT_G = new CivitasBigInteger(
			Base64.getDecoder().decode(BIGINT_G_BASE64));
	public static final String ELGAMAL_PARAMETERS_XML = "<elGamalParameters><p>"
			+ BIGINT_P_BASE64 + "</p><q>" + BIGINT_Q_BASE64 + "</q><g>"
			+ BIGINT_G_BASE64 + "</g></elGamalParameters>";
	public static final String ELGAMAL_PRIVATE_KEY_XML = "<elGamalPrivateKey><params>"
			+ ELGAMAL_PARAMETERS_XML + "</params><x>" + SOMESTRING_BASE64
			+ "</x></elGamalPrivateKey>";
	public static final String EL_GAMAL_PRIVATE_KEY_NULL_XML = "<elGamalPrivateKey><params></params><x></x></elGamalPrivateKey>";

	public static final String EL_GAMAL_PARAMETERS_SAFE_XML = "<elGamalParameters><p>ASvIFyEHZA21reK32LTtYEwJG4GSW8Xzrl83llQSdjcztgsIJeyQm0ZZ36vG4aTVYjtADzV9J6xJ5RJ2U9QDfV8=</p><q>AJXkC5CDsgba1vFb7Fp2sCYEjcDJLeL51y+byyoJOxuZ2wWEEvZITaMs79XjcNJqsR2gB5q+k9Yk8ok7KeoBvq8=</q><g>AKhY5FkyO989WVWT54UIYi29aZjUfx1XD0xlcF13Y7nGa7itniOZIj36m+m8XHW9VN0bzCgLh4btlHBQ8AY3dMo=</g></elGamalParameters>";

	public static final String ELGAMAL_PARAMETERS_OTHER_XML = "<elGamalParameters>"
			+ "<p>AIbUeE2FqsTt9fCnfKQj4Gfi0E9YJs3T8CQeXlHdvFghFLqEvqG+0i9FSS6KCPuIrvJTQpP0UK7jmz9ho0nV2kuB8CDK8X0B8OzgIzCMwy5LpEEzZsKhy9G1jV3YyehkqfxZdKkupPZ1M5HI+BNZOR8ohWm/6k7aRon9Z2zs6RfJKD3h9QV8pLJdtX+cFs0hMfv4u8O57H2+W/J2QKd06knDmx05NIXUPzNAJ6ocl4ndjzXxUKwgC0H6191uDei3qJukuW+ZDVEZYmfLUPieeUIPo1HTljnKfqEv+mdyMcEfJrOEzzXB7jRTT3QohtFzZXa6/KmcZ9FdWh54+H2//6ryNyE2oNypfTKWvxxicmUJTeKJDR+lpaBiDzSfs9XM6rcEWVkkWD33YjHb+5M2gkMgJEXx3WrYiIEyyKav7I+ezSlllEk3pwgMBmJW+jgiI+I9ByU87PWNGzKys5rUvFBaF9RCz21zzw2LEvXDYvlkk+QaPPIKj7AM4M+7wKeuSw==</p><q>APDDzsMTbD5LgggjvzFDfoK3x1BphByXkZqg7zBlHyvj</q><g>FYuXPQPEApf/JDV2CPbfQGF11k1P3RZ3PRn7uH0eRQBSyCttyrEeVeDWMSSj8aDe8jSFBz/AmwOoZ8nMeLVvHoA58egzaej80FNFwF2K6+59hikJ5Wvf0VCKrq4ULv2TCdUnQ/7+XastFN8IEBzMwKXWX0l9xw9JVRNd98jAktkUjIFBjf1Vuoie5Niw3zJ8ySgbOzCymeamN1FxBZcWA29G561RicA0KryOG3BGH8rqWvnHIPyL5PCB6KILyXoKZfjqyrb12SMVcEXO+/y5+6HBu630e5SSFhfxu/5+ejHABwnuRwcdy2rSBIwqp0xKwwhllzTeWvSBWsf22MYwYCgFm8WpG8ZqAZeeXcSqmMeRVL8MPFKscxz6htkp8nB/lGysCvMiPnfAe5yUgOtmpRLAxfNiWdLdLSdRJ2q5aDXwp9GwVKSafmelv3qMs4hPERHpgGoQmMme7XDLrd2z4UxOquIvXUtcpYvp5FC11C5RgeXzYbU6bbPljzwx3vqf</g></elGamalParameters>\n";
	public static final String XML_ELGAMALCIPHERTEXT_TRUNCATED = "<elGamalCiphertext><a>ESIQ9LFs";
	public static final String XML_ELGAMALCIPHERTEXT_ = "<elGamalCiphertext><a>"
			+ SOMESTRING_BASE64 + "</a><b>Xje5W2KfxNk=</b></elGamalCiphertext>";
	public static final String XML_ELGAMALCIPHERTEXT_ANULL = "<elGamalCiphertext><a></a><b>Xje5W2KfxNk=</b></elGamalCiphertext>";
	public static final String XML_ELGAMALCIPHERTEXT_BNULL = "<elGamalCiphertext><a>Xje5W2KfxNk=</a><b></b></elGamalCiphertext>";

	public static final String SHARED_KEY_CIPHERTEXT_XML = "<sharedKeyCiphertext>"
			+ SOMESTRING_BASE64 + "</sharedKeyCiphertext>";
	public static final String PUBLIC_KEY_CIPHERTEXT_XML = "<publicKeyCiphertext>"
			+ SOMESTRING_BASE64 + "</publicKeyCiphertext>";
	public static final String ELGAMAL_REENCRYPT_FACTOR_XML = "<r>"
			+ SOMESTRING_BASE64 + "</r>";
	public static final String ELGAMAL_REENCRYPT_FACTOR_NULL_XML = "<r></r>";
	public static final String PET_C_NULL_XML = "<petC></petC>";
	public static final String PET_C_XML = "<petC>" + SOMESTRING_BASE64
			+ "</petC>";

	public static final String PUBLICIZED_SAFE_BIGINT_A_BASE64 = "ASvIFyEHZA21reK32LTtYEwJG4GSW8Xzrl83llQSdjcztgsIJeyQm0ZZ36vG4aTVYjtADzV9J6xJcK0C32+iCP4=";
	public static final String PUBLICIZED_BIGINT_A_BASE64 = "AJ7DTOVtIdTy2i35nTSkIGrlYIN7vFLIWnbCBYoKrXbS66Ns9BfzCJhBwIzjddiILQiNohiwQSNaY+oEQR7Qh9TF1MzMgnPY7mtdqZD9y8+wc+KiGU5W2Oy5i4jLbIp0YZ1yaUTMCdMAK0HNod5MAlXYhjyjZyKlRK55lml6tz2pet+ZTv0XL46xzFRbdEChLve61294SrKR91n+MmtdC97E/RJ/mOYDGuzx1Hia0jdqPMBaVNivKIju+z2K5zo8Y7tgbW98mjoHvwFh7rzyHOLhJHjTCnDE1rwoPlR4svSJB01MC8COS0ppG5ct3QeQu9H5C3GuyqFF0J5fUKUhsVmsC4K9KNtimpZeuALg+987MOuXpqIt+DkTJyZbehLJWzUGKyGHV6Eb93XfigFhEtj3d2+GwTtFxadBo58KZStBta3fiGCt5IdF/xcFty1eckh+etpaSTRV0rdEq6j8mpc+S38PBJq3cv08cLHRphiByTEU7VAXyEuuk4+YzqWc5Q==";

	public static CivitasBigInteger PUBLICIZED_BIGINT_A = BIGINT_G
			.modPow(BIGINT_A, BIGINT_P);
//	public static CivitasBigInteger PUBLICIZED_BIGINT_A = new CivitasBigInteger(
//			Base64.getDecoder().decode(PUBLICIZED_BIGINT_A_BASE64));

	public static final String PUBLICIZED_BIGINT_B_BASE64 = "dmucPUeZk1gVt66CFa219z5ruAlVM29uHKAqur7d9/nxFKweCybxCgyhsmPzmrZHRhtwDQ7CeQVNvfsDsU18bH97W0ReuGWlcyej2MBRi9XSWfD3HqxbIbM7oCpD5jfEY9XCD/QyaxGROtGYMUDRW0XzFKIOMVZdmc6FoneVo+u25Ui3ADu/wibmp5lYjCgZCnIFfBkxDAP0Wkd9hiVXlaGpAnvCdBKuxLYIWwdqhTirdVCMiNFDNuCInSpB8+GNZZvnG5i08TdgsbulJU7ISIuMDt02oF96cFi0CmL2LV6Zr5i3dhE7uDSyohxDTiPe41yx9WAG+GwT7rd2wGR1YGVC1Imt2igwHQQErDqA+h0KWVS7RoLrlg2j1s3IyMJZs/qeb82y7iDAyffkIaUwDHN8ix4gp/P3/+wVRglT0ydb0YG49OC8N0e5YkF3nmEKE3BL6UatYXDle9kjSCulfkk4NfBvN1NTyrpXdKqbEMgs5bnWI/4Y4nYBzoJ6srXK";
	public static final String PUBLICIZED_SAFE_LEGENDRE_BASE64 = "ASvIFyEHZA21reK32LTtYEwJG4GSW8Xzrl83llQSdjcztgsIJeyQm0ZZ36vG4aTVYjtADzV9J6xJ5RJ2U9P/qG4=";

	public static final String VOTE_CAPABILITY_SHARE_XML = "<voteCapabilityShare>"
			+ PUBLICIZED_BIGINT_A_BASE64 + "</voteCapabilityShare>";
	public static final int VOTE_CAPABILITY_SHARE_INTVALUE = -828007195;
	public static final String VOTE_CAPABILITY_SHARE_NULL_XML = "<voteCapabilityShare></voteCapabilityShare>";
	public static final String VOTE_CAPABILITY_SHARE_JUST_BIGINT_XML = "<voteCapabilityShare>"
			+ SOMESTRING_BASE64 + "</voteCapabilityShare>";

	public static final String VOTE_CAPABILITY_XML = "<voteCapability>"
			+ PUBLICIZED_BIGINT_A_BASE64 + "</voteCapability>";
	public static final int VOTE_CAPABILITY_INTVALUE = -828007195;
	public static final String VOTE_CAPABILITY_NULL_XML = "<voteCapability>"
			+ "</voteCapability>";
	public static final String VOTE_CAPABILITY_JUST_BIGINT_XML = "<voteCapability>"
			+ SOMESTRING_BASE64 + "</voteCapability>";

	public static final String PLAINTEXT_OF_BIG_SECRET_BASE64 = "AJXkC5CDsgba1vFb7Fp2sCYEjcDJLeL51y+byyoJOxuZ2wWEEvZITaMs79XjcNJqsR2gB5q+k9YkfiPHtYWgSk8=";
	public CivitasBigInteger PLAINTEXT_WITH_LEGENDRE_MINUS_ONE = CivitasBigInteger
			.valueOf(251121);

	public static final String EL_GAMAL_PUBLIC_KEY_XML = "<elGamalPublicKey><params>"
			+ ELGAMAL_PARAMETERS_XML + "</params><y>" + PUBLICIZED_BIGINT_A_BASE64
			+ "</y></elGamalPublicKey>";
	public static final String EL_GAMAL_PUBLIC_KEY_NULL_XML = "<elGamalPublicKey>"
			+ "<params></params><y></y></elGamalPublicKey>";
	public static final String EL_GAMALPUBLIC_KEY_NAME = "ElGamalPublicKey-"
			+ PUBLICIZED_BIGINT_A_BASE64;

	public static final String ENCRYPTED_ZERO_FACTOR_XML = "<elGamalCiphertext><a>"
			+ ONE_BASE64 + "</a><b>" + PUBLICIZED_BIGINT_B_BASE64
			+ "</b></elGamalCiphertext>";

	public static String PRIVATE_KEY_BASE64 = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDdA1mJJE/7CVdHU9dZQXW1aefxFMyIZbGzOcNz4Npaj/rpDSnwitk99zjK5Ga9kltht2K1292LIwczcQKT3r/EgB9UPS72gUQX4eY8Oac/Z6hdDDn9bMWYMybh9NrDq+MqHbsPuPvNskPJ+0JZtH59rf6G/3Br9WQBtQiPEOG7uwFEN6IAT59ecGnEO23GVzAAYDG7ZYGcMNsr9MowNR1xdYtXYPLDx/PznsMVlpHsvqECw44kwxMBHGrKvNkU3ulsWXH0qKK/iaAdfBi+r5MwS55sE/DP1sB1R1FbqUsKliCUtYu9nSWb1lWLditDF0D82zIDGeoK3yUeHTgtZ9VfAgMBAAECggEAV5rfzzobD+VEeMv1dPY7C7VWkFuPtVMmDGZNoK2Ur8gZi25B3QyUjvjjRJ0jUmpkmYeg83gayW+Rh6T4gheNyPyGLpRv/JsBnDvZUK8DqeDTPkq948PNUQaZl4BAhMYp7cRKJTHMcd0C/NmBE+C1MOkalnVDIS/sO7cAFQF6kLgTdoJ6FyMSiV/ZTtB+0hGtQ/8ttfWfjWn+1QBZq6G48cAdm351Y/CmQSWFpk6ThFIG2qjncS/HNUggr8QNCGx1GNboHFRsWp9bVPBowiOKgMWl8QvnUwwxXDZ4FcqYLsfjQ7VbSH+aOQNJhfj76Vn+jREO1OlrB2GWOaIIleA7WQKBgQDuUVLcSTN+oBdtBYZz/gQS3RFFLwvVv+CoqVnKYqPe1VBrNU3nqJLSus2g1DkU1tPZ3kouYCr2940qjEfb5HUt0iTSIfIGy+SYtK2H7nNXvEkXbrkt5c337eZcugukf+BAMDeb2kEtJ5fL215V7mLxnQ4K970pvgn9d/YmQ9j/WQKBgQDtaVZwrtrfmEKo4opeH0wURt/YTOmS/zLImNX5DsHT0KUu/3CAW7iHujiQzUoiwPHWSYt9rQ8cW2Y+9YAcrNGRBHGlHh76cqUQWsL4PiWCyH9cAGp7GoYrlsnIJk6uKSX+KFzGsSpib2QI2F5+FsiK7OtaasmkHN+p8gp9PtjbdwKBgQDnBtc4zY2p9zA+c9i/oUP5tW0EkHL8p6H2XxW4aJ9LaacoBuia5oRO3OByRMmBNjDxR9jJ1FhSSfEDijIvLO2bmdgwDNA/XnEgyCZhW/tc1h5VJPVHVTuyWWG0Cnc25PBTyWpfH9mHGJbC/Moaq5ond4UhhMBQMvYkTaLFvYgjIQKBgQCjKARGtnQmu638AIwU+S9AtCB7twfChO1ynE5YtkKQfdA3h0baf65GYL0lDmBHmA/bM3nQqdE7rLiMxg0AJ+hx3/r1/chOV+GGZsmc7qC6y21zXbnP1j+kzyDnEyR5XkAE9doklnbJZUH7Li9qOeAiUYXBAqiGx20SjwuUXU+NKwKBgBP7F4TSMSZHjwB/pOetKQB8kT6FcvsL5ALValkm0Lxjh7RqTGsn9Y357pOOeg8v6NBuJKePfn9KJmh3AeJ3yShm3xd0JorCrjgyHGQIJiGzP3C9858DAGEk//bGX4nrrYVKtUt/HJIpvBDbmk/OyCrFyL9KOVghorLCPk5W03Ee";

	public static String PRIVATE_KEY_XML = "<privateKey>" + PRIVATE_KEY_BASE64
			+ "</privateKey>";

	public static final int FAKE_RANDOM_FACTOR = 42;

	public static final ElGamalReencryptFactorC ENCRYPT_FACTOR_ZERO = new ElGamalReencryptFactorC(
			CivitasBigInteger.ZERO);

	CryptoFactoryC factory = CryptoFactoryC.singleton();

	public static final SecureRandom RANDOM_GENERATOR_FAKE_ORDER;
	public static final SecureRandom RANDOM_GENERATOR_FAKE_BIGINT_A;

	public static final java.security.PrivateKey PRIVATE_KEY;

	public static final ElGamalParametersC EL_GAMAL_PARAMETERS;
	public static final ElGamalParametersC EL_GAMAL_PARAMETERS_OTHER;
	public static final ElGamalPrivateKeyC ELGAMAL_PRIVATE_KEY;
	public static final VoteCapabilityShareC VOTE_CAPABILITY_SHARE;
	public static final VoteCapabilityShareC VOTE_CAPABILITY_SHARE_JUST_BIGINT;
	public static final ElGamalParametersC EL_GAMAL_PARAMETERS_SAFE;
	public static final CivitasBigInteger BIGINT_A_ENCRYPTED_SAFE;
	public static final CivitasBigInteger SOME_INT_ENCRYPTED_SAFE;
	public static final VoteCapabilityC VOTE_CAPABILITY;
	public static final VoteCapabilityC VOTE_CAPABILITY_JUST_BIGINT;

	public static final ElGamalPublicKeyC EL_GAMAL_PUBLIC_KEY;

	static {
		try {
			EL_GAMAL_PARAMETERS = ElGamalParametersC
					.fromXML(new StringReader(ELGAMAL_PARAMETERS_XML));
			EL_GAMAL_PARAMETERS_SAFE = ElGamalParametersC
					.fromXML(new StringReader(EL_GAMAL_PARAMETERS_SAFE_XML));
			EL_GAMAL_PARAMETERS_OTHER = ElGamalParametersC
					.fromXML(new StringReader(ELGAMAL_PARAMETERS_OTHER_XML));
			ELGAMAL_PRIVATE_KEY = new ElGamalPrivateKeyC(BIGINT_A,
					EL_GAMAL_PARAMETERS);
			EL_GAMAL_PUBLIC_KEY = new ElGamalPublicKeyC(PUBLICIZED_BIGINT_A,
					EL_GAMAL_PARAMETERS);
			VOTE_CAPABILITY_SHARE = new VoteCapabilityShareC(BIGINT_A,
					EL_GAMAL_PARAMETERS);
			VOTE_CAPABILITY_SHARE_JUST_BIGINT = new VoteCapabilityShareC(BIGINT_A);
			VOTE_CAPABILITY = new VoteCapabilityC(BIGINT_A, EL_GAMAL_PARAMETERS);
			VOTE_CAPABILITY_JUST_BIGINT = new VoteCapabilityC(BIGINT_A);

			BIGINT_A_ENCRYPTED_SAFE = EL_GAMAL_PARAMETERS_SAFE
					.encodePlaintext(BIGINT_A);
			SOME_INT_ENCRYPTED_SAFE = EL_GAMAL_PARAMETERS_SAFE
					.encodePlaintext(SOME_INT_BIG);

			KeySpec keySpec = new PKCS8EncodedKeySpec(
					Base64.getDecoder().decode(PRIVATE_KEY_BASE64));
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PRIVATE_KEY = keyFactory.generatePrivate(keySpec);

			RANDOM_GENERATOR_FAKE_ORDER = mock(SecureRandom.class,
					withSettings().withoutAnnotations());
			doAnswer(new Answer<Void>() {
				@Override
				public Void answer(InvocationOnMock invocation) {
					byte[] array = invocation.getArgument(0);
					java.util.Arrays.fill(array, (byte) 0);
					array[array.length - 1] = (byte) FAKE_RANDOM_FACTOR;
					return null;
				}
			}).when(RANDOM_GENERATOR_FAKE_ORDER).nextBytes(any());

			RANDOM_GENERATOR_FAKE_BIGINT_A = mock(SecureRandom.class,
					withSettings().withoutAnnotations());
			doAnswer(new Answer<Void>() {
				@Override
				public Void answer(InvocationOnMock invocation) {
					byte[] array = invocation.getArgument(0);
					java.util.Arrays.fill(array, (byte) 0);
					byte[] aBytes = BIGINT_A.toByteArray();
					for (int i = array.length - aBytes.length; i < array.length; i++) {
						array[i] = aBytes[i - (array.length - aBytes.length)];
					}
					return null;
				}
			}).when(RANDOM_GENERATOR_FAKE_BIGINT_A).nextBytes(any());

		} catch (Exception e) {
			throw new Error(e);
		}
	}

}