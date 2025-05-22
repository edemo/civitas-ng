package civitas.crypto.concrete;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import civitas.crypto.algorithms.GenerateElGamalParametersTestData;
import civitas.util.CivitasBigInteger;

public interface ConcreteTestData extends GenerateElGamalParametersTestData {
	public static final String SOMESTRING = "testdata";
	public static final String SOMESTRING_BASE64 = "dGVzdGRhdGE=";
	public static final String SOMESTRING_EXTENDED = "exttestdatawas";
	public static final byte[] BYTES = SOMESTRING.getBytes();
	public static final int SOME_INT = 0xdeadbeef;
	public static final int SOME_SMALL_INT = 0xbeef;
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

	public static final List<String> RANDOMS_BASE64 = Arrays.asList(
			"JY+s34cV7ybeZLEpACLziJ3ZTb5fiCoGkx7duQSKvms=",
			"POILzE0M8iAYB1dCQW44j2oeofu6U7JMxnxoboocpBI=",
			"N8WtyRCrye3u8iDSDnKjAob9Wr/fMDqx3x3FE+OyDkM=",
			"AI6ANU43FzgU0jNSTA/2scyoriAcKi+nHxPt1XBf8i9w",
			"FWuS+OV8XlmHhhSkQlmUEh8WqQKXtGe2ZXrCQYdlWSc=");

	public static final List<CivitasBigInteger> RANDOMS = RANDOMS_BASE64.stream()
			.map((s) -> new CivitasBigInteger(Base64.getDecoder().decode(s)))
			.collect(Collectors.toList());

	public static final CivitasBigInteger RANDOMS_0 = RANDOMS.get(0);
	public static final String RANDOMS_0_BASE64 = RANDOMS_BASE64.get(0);

	public static final CivitasBigInteger RANDOMS_1 = RANDOMS.get(1);
	public static final String RANDOMS_1_BASE64 = RANDOMS_BASE64.get(1);

	public static final CivitasBigInteger RANDOMS_2 = RANDOMS.get(2);
	public static final String RANDOMS_2_BASE64 = RANDOMS_BASE64.get(2);

	public static final int NO_OF_WELL_KNOWN_CIPHERTEXTS = 10;
	public static final int MY_CHOICE = 2;

	public static final CivitasBigInteger BIGINT_A = new CivitasBigInteger(
			SOMESTRING.getBytes());
	public static final String BIGINT_A_BASE64 = SOMESTRING_BASE64;
	public static final String BIGINT_A_ENCRYPTED_SAFE_BASE64 = "ASvIFyEHZA21reK32LTtYEwJG4GSW8Xzrl83llQSdjcztgsIJeyQm0ZZ36vG4aTVYjtADzV9J6xJcK0C32+iCP4=";
	public static final CivitasBigInteger BIGINT_B = new CivitasBigInteger(
			new BigInteger("6789098765432112345").toByteArray());
	public static final String BIGINT_B_BASE64 = "Xje5W2KfxNk=";
	public static final CivitasBigInteger BIGINT_C = new CivitasBigInteger(
			new BigInteger("8765432112678909345").toByteArray());
	public static final String BIGINT_C_BASE64 = "eaUSELMHNaE=";
	public static final String BIGINT_D_BASE64 = "eOmnTcKMIpE=";
	public static final CivitasBigInteger BIGINT_D = new CivitasBigInteger(
			new BigInteger("8712678906543219345").toByteArray());

	public static final String P_OTHER_BASE64 = "AIAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAf8tZIaH63gtefkwkLYnCdtF2sMI5MRddL3IgM7SZK+auYCtQitiTHGI0TF8xu5lDM7bV4GvZA6tTBEaKS9EMxD34H+5xFtBtRqWuCI6XMPEMJg71eqhVT3M5yEqwA1wJXXvD/oKs6nlx+MxcEzb9kQweaVE6aOa/9hPMFdU0wL2ZatNOJfN2+CmsweXdC/gnZSX4ySmWAGP4JTHq4abv1SN4JwkUzgsrWownWy2Pzm5fiCeQBc0yn70GTSdIxuDnfX9em+ORiSXhlh7+ddNcgvC35wkHmfjWELTBhUDUh1L8gQWWEQsq2m56u2cv6sdY6FYo2GuxzASJ9vIWQBDS1Et9AJzs3dVN4PjdKWt2peAKGb1Gl5rcBEmM5b3oYhWzSLSu8axcU2dqP0FNvPpkrD98hEAvWyW2sU/xdk4wwGSw==";

	public static final String G_OTHER_BASE64 = "UVvkQGS9ULYLZfgomIk1JPAkhBjF3FJJf2v/BESOIa0GSawzuu8+UJ3AhsmgF+bb8jhqfUq/UTqyBTR2IFKyItkUrb/B1UoK/wvaw0kbKyfjUrCGXnXOHnIE8bu/6Drjj2QzDy69kKJP37PGhd/jaRxdqGiSoEy1TAD1a/mjkjhmJmUSk4FZdtXUW6rwlq40C63dlKW2pWru+B6XRjtURM1RP4w7joKvcV2N4x3OAQs3/skjmUXyRQoU1k23eoNCee4wYadG+fne2+4MTvr/Qb6JIG4TOEmGmWO3ADHdwOHQxy515OTMP+DoffBMHFZKy7OZnC4G7q/+qHzVbVAJc8iwLVy73zDt21g7PgvEUB+ndESkhWUmVTNBtWANLGgDswoyvZXX8wu1JC5lI485dm8ux8VP5k/4j9TYIA9PzDRxs8M6ykF5ej4XAaa80Yuzpbxw7Eex2rZHiys79Hn3jiM5vhOTND8hdb4Qexhqu5jQw4rwj0j094M921WrScq6";
	public static final String P2_BASE64 = "AIbUeE2FqsTt9fCnfKQj4Gfi0E9YJs3T8CQeXlHdvFghFLqEvqG+0i9FSS6KCPuIrvJTQpP0UK7jmz9ho0nV2kuB8CDK8X0B8OzgIzCMwy5LpEEzZsKhy9G1jV3YyehkqfxZdKkupPZ1M5HI+BNZOR8ohWm/6k7aRon9Z2zs6RfJKD3h9QV8pLJdtX+cFs0hMfv4u8O57H2+W/J2QKd06knDmx05NIXUPzNAJ6ocl4ndjzXxUKwgC0H6191uDei3qJukuW+ZDVEZYmfLUPieeUIPo1HTljnKfqEv+mdyMcEfJrOEzzXB7jRTT3QohtFzZXa6/KmcZ9FdWh54+H2//6ryNyE2oNypfTKWvxxicmUJTeKJDR+lpaBiDzSfs9XM6rcEWVkkWD33YjHb+5M2gkMgJEXx3WrYiIEyyKav7I+ezSlllEk3pwgMBmJW+jgiI+I9ByU87PWNGzKys5rUvFBaF9RCz21zzw2LEvXDYvlkk+QaPPIKj7AM4M+7wKeuSw==";
	public static final String Q2_BASE64 = "APDDzsMTbD5LgggjvzFDfoK3x1BphByXkZqg7zBlHyvj";
	public static final String G2_BASE64 = "FYuXPQPEApf/JDV2CPbfQGF11k1P3RZ3PRn7uH0eRQBSyCttyrEeVeDWMSSj8aDe8jSFBz/AmwOoZ8nMeLVvHoA58egzaej80FNFwF2K6+59hikJ5Wvf0VCKrq4ULv2TCdUnQ/7+XastFN8IEBzMwKXWX0l9xw9JVRNd98jAktkUjIFBjf1Vuoie5Niw3zJ8ySgbOzCymeamN1FxBZcWA29G561RicA0KryOG3BGH8rqWvnHIPyL5PCB6KILyXoKZfjqyrb12SMVcEXO+/y5+6HBu630e5SSFhfxu/5+ejHABwnuRwcdy2rSBIwqp0xKwwhllzTeWvSBWsf22MYwYCgFm8WpG8ZqAZeeXcSqmMeRVL8MPFKscxz6htkp8nB/lGysCvMiPnfAe5yUgOtmpRLAxfNiWdLdLSdRJ2q5aDXwp9GwVKSafmelv3qMs4hPERHpgGoQmMme7XDLrd2z4UxOquIvXUtcpYvp5FC11C5RgeXzYbU6bbPljzwx3vqf";

	public static final CivitasBigInteger BIGINT_P_OTHER = new CivitasBigInteger(
			Base64.getDecoder().decode(P_OTHER_BASE64));
	public static final CivitasBigInteger BIGINT_G_OTHER = new CivitasBigInteger(
			Base64.getDecoder().decode(G_OTHER_BASE64));
	public static final String GENERATOR_OTHER_BASE64 = "B8Ewf1Z2BaJYadh/hzQ2kjNGPmYWBIvA6ZxHSpVpB/Sw4m40L8qWkwsNrfUh87L8GIJM0QZ4tHgYCdduHUjiWmcStRTh+2RvNoinIrK5QnxaW3g6Y2G32rxutDlOKwaoMMbrut50O8XN12mD4wR15inhh4+QVyzeobFVSPnpgheb2gt9i5pTKJY6JQkpcZnD4bHF+i7MGelqeoyGmLGqZu98ODsSxn2HfmMniV+p+aaTYJV8niINbYlvvz2BSwf86YdlQOa5WfzzPGSltmt5v5ACs135JCIw3kRzepRzjWGAINHv+SgPcBVSVTwtuBoykYhxzT03CWWo5WwZ+NzdmgDih7RheaQpMlIfSxP/5ZJVukgDxJv4v70F3KRwJ5V5jY26sjETz2Kayn6y38EfDuQjeOAUOamGYcHpdqKdPySJWTK0o6sfKIQ2etMZG0fUFEEt3CdsoGOUi31saXFDK4pkHqu+jQMzCg2Z5EsXwVgZ6J0SD1Nq/DDGvAU2lQdU";
	public static final CivitasBigInteger GENERATOR_OTHER = new CivitasBigInteger(
			Base64.getDecoder().decode(GENERATOR_OTHER_BASE64));

	public static final CivitasBigInteger GENERATOR_FOR_UNPRIME_P = new CivitasBigInteger(
			Base64.getDecoder()
					.decode("APPuKv0SXyLaZklKlEiPMfeSkEfsEfdN24AXL3NrYBKG"));
	public static final CivitasBigInteger GENERATOR_FOR_UNPRIME_Q = new CivitasBigInteger(
			Base64.getDecoder().decode("SlVGgqFXxlg="));

	public static final CivitasBigInteger D_EXP_TWOK_FROMP = BIGINT_D.modPow(
			BIGINT_P.subtract(CivitasBigInteger.ONE).divide(BIGINT_Q), BIGINT_P);

	public static final ElGamalParametersC EL_GAMAL_PARAMETERS_Q_SAME = new ElGamalParametersC(
			BIGINT_P_OTHER, BIGINT_Q, BIGINT_G_OTHER);

	public static final ElGamalParametersC EL_GAMAL_PARAMETERS_GENERATOR_OTHER = new ElGamalParametersC(
			BIGINT_P, BIGINT_Q, GENERATOR_OTHER);

	public static final String ELGAMAL_PARAMETERS_XML = "<elGamalParameters><p>"
			+ BIGINT_P_BASE64 + "</p><q>" + BIGINT_Q_BASE64 + "</q><g>"
			+ BIGINT_G_BASE64 + "</g></elGamalParameters>";
	public static final String ELGAMAL_PARAMETERS_NOGROUP_XML = "<elGamalParameters><p>"
			+ BIGINT_A_BASE64 + "</p><q>" + BIGINT_B_BASE64 + "</q><g>"
			+ BIGINT_C_BASE64 + "</g></elGamalParameters>";

	public static final String ELGAMAL_PARAMETERS_OTHER_XML = "<elGamalParameters>"
			+ "<p>" + P2_BASE64 + "</p><q>" + Q2_BASE64 + "</q><g>" + G2_BASE64
			+ "</g></elGamalParameters>\n";
	public static final CivitasBigInteger RANDOMS_0_PUBLISHED = BIGINT_G
			.modPow(RANDOMS_0, BIGINT_P);
	public static final String ELGAMAL_PRIVATE_KEY_XML = "<elGamalPrivateKey><params>"
			+ ELGAMAL_PARAMETERS_XML + "</params><x>" + SOMESTRING_BASE64
			+ "</x></elGamalPrivateKey>";
	public static final String EL_GAMAL_PRIVATE_KEY_NULL_XML = "<elGamalPrivateKey><params></params><x></x></elGamalPrivateKey>";

	public static final String EL_GAMAL_PARAMETERS_SAFE_XML = "<elGamalParameters><p>"
			+ SAFE_P_BASE64 + "</p><q>" + SAFE_Q_BASE64 + "</q><g>" + SAFE_G_BASE64
			+ "</g></elGamalParameters>";
	public static final ElGamalReencryptFactorC REENCRYPT_FACTOR_RANDOMS_1 = new ElGamalReencryptFactorC(
			RANDOMS_1);
	public static final CivitasBigInteger PUBKEY_VALUE_OTHER_GENERATOR_RANDOM1_FACTOR = GENERATOR_OTHER
			.modPow(REENCRYPT_FACTOR_RANDOMS_1.r, BIGINT_P);
	public static final CivitasBigInteger PUBKEY_VALUE_OTHER_GENERATOR_RANDOM1_FACTOR_POW_A = PUBKEY_VALUE_OTHER_GENERATOR_RANDOM1_FACTOR
			.modPow(BIGINT_A, BIGINT_P);
	public static final String PUBKEY_VALUE_OTHER_GENERATOR_RANDOM1_FACTOR_POW_A_BASE64 = Base64
			.getEncoder().encodeToString(
					PUBKEY_VALUE_OTHER_GENERATOR_RANDOM1_FACTOR_POW_A.toByteArray());
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
	public static final ElGamalReencryptFactorC ELGAMAL_REENCRYPT_FACTOR = new ElGamalReencryptFactorC(
			BIGINT_A);
	public static final String ELGAMAL_REENCRYPT_FACTOR_NULL_XML = "<r></r>";
	public static final String PET_C_NULL_XML = "<petC></petC>";
	public static final String PET_C_XML = "<petC>" + SOMESTRING_BASE64
			+ "</petC>";

	public static final String PUBLICIZED_SAFE_BIGINT_A_BASE64 = BIGINT_A_ENCRYPTED_SAFE_BASE64;
	public static final String PUBLICIZED_BIGINT_A_BASE64 = "bYNRiWtK8H8jaehxs1S+YKemiiip2QacwnwcQqKHsPBE1KzsZEBVquxOmPa8FGLV4hUd5okpWdQImk9ntTNCKC3q9yq7Grr7xUAoS3QMwQ0N4B18eENIjUO9eACm6IcPf+3tK87fp7h35ZQHA0XJJNxSVRz97ARYjIcXg9x/jBPbsHqXDfkrAhfneWqBajxu5+8kygrp3pS/DVZI9cB/WUvfS5idySJ/YLQKzcXxlqSPeRdHN3vNyE/DaJ2MGHqiEJE1qo2p3kc5R31gceixV9WQFQn7PJ9RrrZ1Isvar+zpsStQdH92Kr0KU+3d/fxuJnpxToA0J8J/mpIat7uj9jugW5qCJ1ERRarjcbjtpi1jZKaDB28704VzudfgAQq5U3O+0MsWRgJ6MClQvWWwXiPK9xAmm7u3b6tvUBemAQhvAbpYgoCmyuq8UiDo7H7EiAB27OcpfrjbprUlEkux7DapIW+X6V85qqZX/myyjnO7kS9l2ju3JYUGOA2A0IlM";

	public static final CivitasBigInteger PUBLICIZED_BIGINT_A = BIGINT_G
			.modPow(BIGINT_A, BIGINT_P);
//	public static CivitasBigInteger PUBLICIZED_BIGINT_A = new CivitasBigInteger(
//			Base64.getDecoder().decode(PUBLICIZED_BIGINT_A_BASE64));

	public static final String PUBLICIZED_BIGINT_B_BASE64 = "d9AUMsYNk5ZQ+ZbjfZz7UgyjyklNj+gwz/MjRyrlLjWVnK/ghtTfAv4qIisD407XCf8Whv+2Us9JcXDuh2ptfNIWJibe2RX6ivjBH/1VyaOylbJ4BGfLFcyNiPxrjAPzJ1LdlSHjkXII3VrmyD72hF4gnzrKExGmDyi9tffhcfZoZvTgxzeVdjvR2PKK/k4Ug0zqjyQyKJUnDP3RB1q+31/RB3S6E+LMyr9ToxQWRS7IymyzsQXV6smygtzZKE+PBU+gIJjxQ6ALt3/VImxazonOPhO9VCSoNfTVwAVvK5FdXvS6utK0GHTnHx0U/D/4W880NWwbB7DIxdvpYnjpK45MxVvoGelhUqMMPOjMM+7cn3o6QLATVB1QHbo7w7yH2P+Q0tcr9itruIjI2sajoZkEMG5n6DJunwqHVZx38BScxUlmKb8rKW8+AhOC3BXBe5biUDReBdRtpSkOFi5as1mWxsIH2EvGAG2bPUtIZuCn0phLG37M32XyhDiOEehm";
	public static final String PUBLICIZED_SAFE_LEGENDRE_BASE64 = "ASvIFyEHZA21reK32LTtYEwJG4GSW8Xzrl83llQSdjcztgsIJeyQm0ZZ36vG4aTVYjtADzV9J6xJ5RJ2U9P/qG4=";

	public static final String VOTE_CAPABILITY_SHARE_XML = "<voteCapabilityShare>"
			+ PUBLICIZED_BIGINT_A_BASE64 + "</voteCapabilityShare>";
	public static final int VOTE_CAPABILITY_SHARE_INTVALUE = -2133817012;
	public static final String VOTE_CAPABILITY_SHARE_NULL_XML = "<voteCapabilityShare></voteCapabilityShare>";
	public static final String VOTE_CAPABILITY_SHARE_JUST_BIGINT_XML = "<voteCapabilityShare>"
			+ SOMESTRING_BASE64 + "</voteCapabilityShare>";

	public static final String VOTE_CAPABILITY_XML = "<voteCapability>"
			+ PUBLICIZED_BIGINT_A_BASE64 + "</voteCapability>";
	public static final int VOTE_CAPABILITY_INTVALUE = VOTE_CAPABILITY_SHARE_INTVALUE;
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

	public static final String PRIVATE_KEY_BASE64 = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDjShmRpZCetckdSNDiRsK6xQj7taabqWOup/armUtkugDKOmhzwakeeiu0HeI0khRyJu5TapctW5hcxfEJ5HjeT7PKkcZjiBC16yAa0uvRVfyRjSnAoBYegnBZyJWo5PTRVZ4TlMGjdrPYPwBPTkALhtnfzNgMI8YM81SWbt2IpjVxTasX82QrpcsHKN+jreRa52NmJwbGV2/Ymi8sM1NsgBU988OtwybhznhWGU8qPRxAdUijUJjJUdThSYXv4opX5kIoTpCi6c+1+OMvov1VFqobopoxz+b5bGrGFod0pH0ZuYBDIUdZoEsC+gIQUe1DbR29YsCEDhrheF8izCI3AgMBAAECggEALBt/JsKIdCQ26RXuY1r0AyalfaNct6yBia1Ssy5GcT+fU59OEditJxryTDvYX/dRPG3NpiZzO/wgbzCCfDb4Nbwl2dO8Oih3hbYhPtNbq/4Ry3UnexgV8snBX8sLMAFueQkyaf5eLYilkft35ev7hcEfmsy3yHNQxcaaza6qExBAf1sYyOqdSvH5m4uEwa1UjIMRPsO/xVfgygm02JBIPv9D5NfixzSXddXCuibLqJJluh7qFCgNDx+f0Y9Mlpc50vmGVygOtYXEkmvSq4HHsfKGO9p0l87HR8i1SVncsVhKfhryfT4bbjdpti1aRqEvQvF/gOVCPWlnEPJkXRRLQQKBgQD1vhXv31wbsAcgMfWU1ku8Dy3nxznH97Pxm1SEy7Z/melvtrKz2yoOfIVVeHGROobY5rpZaZprP5+yySX5c2Njhe9bJS2gFcC99AkKH6sP4GBQBqhogwxJ5aPWOCXTo1uvaI4TCiOXgOcGW1mhXHltQN4SVrduFfZgmoe2eKpikQKBgQDsxtTa51zTDzhSEuSfR5B9XSn35nw7CoyqCqhwzXu5Yz60lSXGSi07IS9cHUW3v9gdLPNU74OCTCCz4nPcPUunR3M7ikQ1UgMQYo7YOv6/SRvMU4YzuwPiMFOcCo47D01QzdgznBgK0qsEsXfegkJCfkvYWJqXlGKZBF1J3iwMRwKBgDVU5o/jaqr7neBH12NQyqjqpmzJ2OlkAaJqsoILb/TrYzUCcnAEHXF9ZrZw6Dy7de52zHSBLake6rqzWtFzUFuWAqdGP0uhzO2sm5Wd39MXP+++MihUYINdGhxwnQz8Q/aie0r4hVbo+7puctpJJnUmgct7FF4I2CqI/vED6P5xAoGBANeuXuJwcjvH13PbxenZVKL9pGcuyuc1OuECCGGIrDU5VDtGyTwRAZalKfDssT8qQACDm7w42UBtfDSgCQ4Lv8hG647G6k7vv97DCH+rbbZtMCc1xj+FEYqOnvq7Gshtre33NAP/Il9pe2MxDZHgcd8EEK6oN1nwTsg4E4pgGRutAoGBAMKBU//f9sWQ+wm2Y462TdAbQyTGnt3PsWWAxjusnoki3+zxwzIPS26v11M5YVb5k2BIoknVE5R2WqoS9ce8csfkOHwSF9BeZPUi2Y1aqvYrMv1TXo2elVPacR3rrPqxMkLC0nkYgud2UOkqlWx3w/elgWyVJp039376uBxANHvp";
	public static final String PUBLIC_KEY_BASE64 = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA40oZkaWQnrXJHUjQ4kbCusUI+7Wmm6ljrqf2q5lLZLoAyjpoc8GpHnortB3iNJIUcibuU2qXLVuYXMXxCeR43k+zypHGY4gQtesgGtLr0VX8kY0pwKAWHoJwWciVqOT00VWeE5TBo3az2D8AT05AC4bZ38zYDCPGDPNUlm7diKY1cU2rF/NkK6XLByjfo63kWudjZicGxldv2JovLDNTbIAVPfPDrcMm4c54VhlPKj0cQHVIo1CYyVHU4UmF7+KKV+ZCKE6QounPtfjjL6L9VRaqG6KaMc/m+WxqxhaHdKR9GbmAQyFHWaBLAvoCEFHtQ20dvWLAhA4a4XhfIswiNwIDAQAB";

	public static final String PUBLIC_KEY2_BASE64 = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA4QraOxcThCsATxuHzTSiVLDsJ++ddk7AAw/8qB6vjMT0/MKbBlVoJyg7innrc2BhH1PLZCGrinOCRDJHoFN4xm/k54JY/6oNEtXmg6xuaMMiaMjQdfTh8WfgU6p/oKhXp6Oo/PfW0zjJqyK9qOOiFWbQRXf2mqg+amDo/ol5K+2Tl+C7fy3hi8r78kUFDmgflnW9amYtPt5mT+wz95hJ994tiQfoVWVD0i5HksJoxW2t0QL3lqCfDcrRTLTGQ/EbuXJbinJDaiA0NunmYYR/lEVOr613PmGxmBsPPEfO1uQpZwhZbNd0SaJVNOzKGQwbs7xY4vUZIQ1tgub4+owdEQIDAQAB";
	public static final String PRIVATE_KEY2_BASE64 = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDhCto7FxOEKwBPG4fNNKJUsOwn7512TsADD/yoHq+MxPT8wpsGVWgnKDuKeetzYGEfU8tkIauKc4JEMkegU3jGb+Tnglj/qg0S1eaDrG5owyJoyNB19OHxZ+BTqn+gqFeno6j899bTOMmrIr2o46IVZtBFd/aaqD5qYOj+iXkr7ZOX4Lt/LeGLyvvyRQUOaB+Wdb1qZi0+3mZP7DP3mEn33i2JB+hVZUPSLkeSwmjFba3RAveWoJ8NytFMtMZD8Ru5cluKckNqIDQ26eZhhH+URU6vrXc+YbGYGw88R87W5ClnCFls13RJolU07MoZDBuzvFji9RkhDW2C5vj6jB0RAgMBAAECggEAIuTFo0Fn/YeUX++/Hg6h9/tJ1sRk2DYMOD1XmfLOGgflC7caBgCiqmWm28kdPLHORWb/A7rjyA8yfOQF7qJauPwbFFmF9rh1l/zSJgsLwJM3J+OxTQToLsACTL7EVNMdakkqV/UO2of03yP/H+uR03syYn8gHell6iU2I1eKEgkVK8xfL4QEQw+Y/kcBuWZc6lohXddnXPqYADkLP8i36HXFWbLaj4So6/d0476v9gJUFECD6NFAAligGTAK0fw9kDYzU5n/7Ta3a9OWnsHvtA+mRWZhjPlrkI1PE/jRhVMw/ZepT3uiAPwVGcUWsGR27MaYjtt1tCpFfoW6mZRiRQKBgQD5MqlgaQ4aVMLBD3i/u12R2Dc5cuUoqLO2T3xTdIv3Vstz7Ujx71dbL5zj3Bi/nc6naKuzTljIyofmhChVVIdLS06vIuw9QXdg7kJFwitRNJujIblBtrjYSc69WzuEHjCpXT1fNTruz/nNRxPQO5yjfcttkxzTfo+jues08q7JhQKBgQDnL2XMcWXWfgWaMtp6tayL0rZDOQd4s6sTSj5f2g6+IjyoDMrNgHgsGO6EaiUUeelacqEwqV5YNAquuqn6qxO+JESst83IwmFtJV9YXJL+HhPpQydOmz297bgye24qO20SpS0siR+KqxChbXlvOYqrY7i+e6XZAGDQFQV8qkf1HQKBgQCb9ZD7vcQNxq58lWpE1Mai2Gb3lWjGIY8Xsndk47QVgESksQTOJbTOZOYT1VPH2MPVEjUjaw013UtggaWcgeKKxP7hrUGctLQ5/eOMNRFXKU2esyR2dB0LgGXbLJX7AcoCtVALngzcGV2Fk85arHRD/h6q3T9VPgg3t8v0v02U4QKBgQCVIJL3HRMHGIW+oR0j2Lsz+rlNhaUXLDanm9W+eOfXqDgeYxoTE9LxBygczGec3MM1cAogXLCaDv/xFEH/i4mRts2B6JLa9OBpypjFY/oB6B8J8iKR5zwdnxGZpts6bNkAFQKd3to01gJFT1vs7l5QDmSmoxYpc7//mf/PGfyO1QKBgQDm+EriDwUAA7/emwQv6nMvPv+FB8j//IttfqtMY/i5ny9ab73YJuloWq7BbQMVJsDY7G4I0Mh34HHk4prc8x0iAgxcYjlrvXl973Eobhj8o+3wcLP2ubLBPU34JlZXzUGw53lhmbLiKfWJ/9UBY9eGKw8SYqCtv369FHu/r6Qr0g==";

	public static final String PRIVATE_KEY_XML = "<privateKey>"
			+ PRIVATE_KEY_BASE64 + "</privateKey>";

	public static final String PUBLIC_KEY_NAME = "bob";
	public static final String PUBLIC_KEY_XML = "<publicKey><name>"
			+ PUBLIC_KEY_NAME + "</name><key>" + PUBLIC_KEY_BASE64
			+ "</key></publicKey>";

	public static final String SHARED_KEY_BASE64 = "dGVzdGRhdGE=";
	public static final String SHARED_KEY_NAME = "shared key name";
	public static final String SHARED_KEY_XML = "<sharedKey><n>" + SHARED_KEY_NAME
			+ "</n><k>" + SHARED_KEY_BASE64 + "</k></sharedKey>";
	public static final String SHARED_KEY_ON_WIRE = SHARED_KEY_NAME + "\n"
			+ SHARED_KEY_BASE64 + "\n";

	public static final String ELGAMAL_PROOF_KNOWN_DISC_LOG_A_BASE64 = "cZP5shp3SOALmUwEY6Zj6crdGhQzIB7fkbIS9sLCUzI4tZDlzROdfCm6EHz1sd5ekqrVwzRfwKBBuOrGeoujtfXJ7e1U5nxgCzcXTJAwhIsuM0KVyvgPV8krRzzRf1/hP7hSwzh4eWCpWKmzfREuoJBe0RKnrv+jBi0U46tSRRgz9qg5x3K92fTEtiCWQnLb5JlA+VWbJNxsdGm99o2t4wcmRdK2ZmOYFPdxmpCLLV5eCWCHCO/rZxBtWwhKScvwhjjbcnSlefNb4MXjEwk3j97Uulz7rt35KKDUMmcxDjpwHbkoAl7lbhtXNPgaPIrcIM60DDdclM8cwXz1ItvUbOzlprPLgjyskGj4g0Kmz+K6v/NfFRCKM2FA9QKlMSlpGWGsQt68+EYAvgTNAdoiSNdoDP+YBY1B6ITg384VcHOIzBhORrLupPR9iPUoIvU0GelBNciOhXjnFUjuWTSpsoCj47N9GBq9GGsYXpTBuXTMI/9wIsqUPT+sjOrvQERl";
	public static final CivitasBigInteger ELGAMAL_PROOF_KNOWN_DISC_LOG_A = new CivitasBigInteger(
			Base64.getDecoder().decode(ELGAMAL_PROOF_KNOWN_DISC_LOG_A_BASE64));

	public static final String ELGAMAL_PROOF_KNOWN_DISC_LOG_C_BASE64 = "V0PAc6rSjBiKZU6P5kIU65fcVr5+eIekC1ABoOrgmvs=";
	public static final CivitasBigInteger ELGAMAL_PROOF_KNOWN_DISC_LOG_C = new CivitasBigInteger(
			Base64.getDecoder().decode(ELGAMAL_PROOF_KNOWN_DISC_LOG_C_BASE64));

	public static final String ELGAMAL_PROOF_KNOWN_DISC_LOG_R_BASE64 = "ElIxK5N4qOvnJH6VBw+5us9mO1M/Y0t8WhDxjNKnHXk=";
	public static final CivitasBigInteger ELGAMAL_PROOF_KNOWN_DISC_LOG_R = new CivitasBigInteger(
			Base64.getDecoder().decode(ELGAMAL_PROOF_KNOWN_DISC_LOG_R_BASE64));

	public static final String ELGAMAL_PROOF_KNOWN_DISC_LOG_XML = "<elGamalProofKnowDiscLog><a>"
			+ ELGAMAL_PROOF_KNOWN_DISC_LOG_A_BASE64 + "</a><c>"
			+ ELGAMAL_PROOF_KNOWN_DISC_LOG_C_BASE64 + "</c><r>"
			+ ELGAMAL_PROOF_KNOWN_DISC_LOG_R_BASE64 + "</r><v>"
			+ PUBLICIZED_BIGINT_A_BASE64 + "</v></elGamalProofKnowDiscLog>";
	public static final String ELGAMAL_PROOF_KNOWN_DISC_LOG_NULL_XML = "<elGamalProofKnowDiscLog><a>"
			+ "</a><c>" + "</c><r>" + "</r><v>" + "</v></elGamalProofKnowDiscLog>";

	public static final String EL_GAMAL_KEY_SHARE_XML = "<elGamalKeyShare><pubKey>"
			+ EL_GAMAL_PUBLIC_KEY_XML + "</pubKey><proof>"
			+ ELGAMAL_PROOF_KNOWN_DISC_LOG_XML + "</proof></elGamalKeyShare>";
	public static final String EL_GAMAL_KEY_SHARE_NULL_XML = "<elGamalKeyShare><pubKey></pubKey><proof></proof></elGamalKeyShare>";

	public static final int SOME_POSITIVE_INTEGER = 0x1eadbeef;
	public static final int KEYSIZE = 1024;

	public static final CryptoFactoryC factory = CryptoFactoryC.singleton();

	public static final String EL_GAMAL_CIPHERTEXT_1_OF_L_XML = "<elGamalCiphertext><a>"
			+ PUBLICIZED_BIGINT_A_BASE64 + "</a><b>"
			+ "VFv/czKkqNzSiah+WxzVEAgZO9hVVhnCjqRH66PFZ3e1y1eWvCjP/SDbmuMFQLfSHzheGmtp3bGwvayVIIV5HnrWyQN8YMF5LM7QxX4rUcYDuC2wG3G+wszjbNIJhyWy2ejltNQFiv71cI0bTU5P9YhgNiuXsq0C1tAWdt6c6lqBW2Oof/g+2VSBHw/gJ+/spR6sf+H6hew4Y/szHEkuNIDBr3gkcpJE2dAk2RV6lr9Xx/Xy7eignXkeXqkpSzXK+qlX6u3gQbCwXIpmls57CAT/J4hBSbbXQbJPxMRfMemZm4hLSiHP70v2g1IYxoydNwL/sUPraXXgoiDFFqsxOSWeN9l3RysnsQUKC1GKON0YvUDNf5WqEQrxdPC/F4q0azNxC2Q3dgznC0oYYlBlaS3uDkTxi2tKSFiu6+mM6YUxKd04pplRtUPeqTLiFK5Q0giqkKP5mK4DLiKhdfJkOdkuS6YEzGiYzLRVuuYFWXx352e3Vcqbp8DpKsphFsnV"
			+ "</b></elGamalCiphertext>";
	public static final String EL_GAMAL_PROOF_1_OF_L_XML = "<elGamalProof1OfL><size>10</size><dv>awmfhAU6U7Wcz5kPkg5cqncO4Tz5tmc4ZtqoiRtyLpA=</dv><dv>R2yN/mgDmwcpUg+ZVUEstEySVXankjgvA95fa5wHGXQ=</dv><dv>AICmwameAGKMoXXKbfljqrBm+JOmPzS2klx8m1EZk9uz</dv><dv>QIj/R6x31/E8wqRzm9XbPb9DiYt2mScs4FhE+2wz4go=</dv><dv>ft13WHaYq8xNHO/24hSt4nlTjH7CKN5hhD5shAoDHm8=</dv><dv>DD5VVJY1BonuOc8nJe5xfYy3wf9kQya9U+sJaDbAQ3k=</dv><dv>AJXL5ONmIuoyvK94CcuoRB2bAdiq8Ok3Gr++iTDqsWeX</dv><dv>axVSQ9fOAIElaqV6wJS1pFT2wu9STftaKfcZkAc2tkM=</dv><dv>Lk+xgoUCqFz/JbQzwDXgbLh1v/HxaijFEZIO1U8q1as=</dv><dv>faC93a1h3ET+0Hzb+tvwUHHQZx9QurDrjq9lYXr5Nec=</dv><rv>LH3xOock3kMSCTC0JfaD/qWoikvKsCwYU4CZmDfyWsg=</rv><rv>AIRo36+ex/i5zY2YDDuGtxDmPoGIH/7M1XhL625qqBFz</rv><rv>CibppMYGZsz37KkI95Z2IStd6/wc3w1QxKyIiKz6dNc=</rv><rv>fgOK4diBwxwnJg1WGwQr5REV1psJa7Zm3PoQZWMImsQ=</rv><rv>ALAooPo4bF254BxADYvC6uOQyhPJOEJAKCk43DwzOdwX</rv><rv>Jzbj2o69WCs46w7Yqli6mBcbvN8t3MNhecxtjN1nmxc=</rv><rv>GZzjyxNJ+2MKQP7SoMOloMF1h7Wyv5//5wCTE3s5Ytk=</rv><rv>RHEFyxQhAgAcSsKqe1WkyzGvAzeVgaiYWs9JcPiSLfA=</rv><rv>bOSCz15zVkOFXQ+fH1sSTgsy305V8yvypIndB2vaJBw=</rv><rv>Ijx1dyYqyhR+v0QiPqTXl49sM5k0wp5DHmMsujVP6x4=</rv></elGamalProof1OfL>";
	public static final String EL_GAMAL_PROOF_1_OF_L_BAD_XML = "<elGamalProof1OfL><size>10</size><dv>"
			+ "R2yN/mgDmwcpUg+ZVUEstEySVXankjgvA95fa5wHGXQ="
			+ "</dv><dv>R2yN/mgDmwcpUg+ZVUEstEySVXankjgvA95fa5wHGXQ=</dv><dv>AICmwameAGKMoXXKbfljqrBm+JOmPzS2klx8m1EZk9uz</dv><dv>QIj/R6x31/E8wqRzm9XbPb9DiYt2mScs4FhE+2wz4go=</dv><dv>ft13WHaYq8xNHO/24hSt4nlTjH7CKN5hhD5shAoDHm8=</dv><dv>DD5VVJY1BonuOc8nJe5xfYy3wf9kQya9U+sJaDbAQ3k=</dv><dv>AJXL5ONmIuoyvK94CcuoRB2bAdiq8Ok3Gr++iTDqsWeX</dv><dv>axVSQ9fOAIElaqV6wJS1pFT2wu9STftaKfcZkAc2tkM=</dv><dv>Lk+xgoUCqFz/JbQzwDXgbLh1v/HxaijFEZIO1U8q1as=</dv><dv>faC93a1h3ET+0Hzb+tvwUHHQZx9QurDrjq9lYXr5Nec=</dv><rv>LH3xOock3kMSCTC0JfaD/qWoikvKsCwYU4CZmDfyWsg=</rv><rv>AIRo36+ex/i5zY2YDDuGtxDmPoGIH/7M1XhL625qqBFz</rv><rv>CibppMYGZsz37KkI95Z2IStd6/wc3w1QxKyIiKz6dNc=</rv><rv>fgOK4diBwxwnJg1WGwQr5REV1psJa7Zm3PoQZWMImsQ=</rv><rv>ALAooPo4bF254BxADYvC6uOQyhPJOEJAKCk43DwzOdwX</rv><rv>Jzbj2o69WCs46w7Yqli6mBcbvN8t3MNhecxtjN1nmxc=</rv><rv>GZzjyxNJ+2MKQP7SoMOloMF1h7Wyv5//5wCTE3s5Ytk=</rv><rv>RHEFyxQhAgAcSsKqe1WkyzGvAzeVgaiYWs9JcPiSLfA=</rv><rv>bOSCz15zVkOFXQ+fH1sSTgsy305V8yvypIndB2vaJBw=</rv><rv>Ijx1dyYqyhR+v0QiPqTXl49sM5k0wp5DHmMsujVP6x4=</rv></elGamalProof1OfL>";

	public static final String EL_GAMAL_1_OF_L_REENCRYPTION_XML = "<elGamal1OfLReencryption>"
			+ EL_GAMAL_CIPHERTEXT_1_OF_L_XML + EL_GAMAL_PROOF_1_OF_L_XML
			+ "</elGamal1OfLReencryption>";
	public static final String EL_GAMAL_1_OF_L_REENCRYPTION_NULL_XML = "<elGamal1OfLReencryption></elGamal1OfLReencryption>";

	public static final String EL_GAMAL_CIPHERTEXT_NAIVE_XML = "<elGamalCiphertext><a>42</a><b>24</b></elGamalCiphertext>";
	public static final ElGamalCiphertextC EL_GAMAL_CIPHERTEXT = new ElGamalCiphertextC(
			ConcreteTestData.GENERATOR_OTHER.modPow(
					ConcreteTestData.REENCRYPT_FACTOR_RANDOMS_1.r,
					ConcreteTestData.BIGINT_P),
			BIGINT_D);
	public static final String EL_GAMAL_CIPHERTEXT_A_XML = "<elGamalCiphertext><a>aHyajDBKPVjLQxSlx21hl1GeVCkrfvctLo4aiwJmas8q20tfehNBRA/O9q1vZhaO2boKwwcEPb0IzIMGgtVUx5UpKQhL8AM6ogZlsuQrHpw5KzdQzyoB/9MPCanfYmdeSRJWkQCYl3dbRiAbp8wTrw9Ft8xooZq0TKPJriBzs1h0i5aUjVzlqrF6T72MDOjem6bS8hFJeqflm8x74e89r+hUeNr/RGGRBbygufzeFTeESJLMfXq1U9aLAszKj4LK21leO9B0nusszhRg8Bz3EcHc6dbGh+2qxMpccvYkG6nxnWq5fEdEpY4+z7YhalrQ8r/JODe2vq5UcIQDnW2toPqD5gKWMRU31V0jrlVKq1phCEvsU5fDDpmE41bOmRoozxN/eJbnAO0Ks+rZzUxXSlYTHiZZ9hfFcZ4uLJbxLXWztBvMJbhlAR1on240A+YSxOHnpzFJGA9TykeD103kJnUJaBmCstYmBIC3g6TDt/4/kbKWD6yiUNj3OnOADbJF</a><b>D7HknJmoALCBli6uEl1zUAMJfcK17oLQwBkEXjCKDsnJZrOJyWbgkBb1SC31VdONSUyqyjfPa682mlcT1vTHfOBJd7wGqWgDe+ESmx+p+ajCK88uXNCNWQRr8zkThLM0a/rGQzB5rBBqg861ntaJTBFGxgEf55nsp+MpQT3XuckNLQwpxH2750x+fvbdbxDoSRMHyApgBdS0eYq+Hg61eW93AdOcRAPpXPP0kUpsKNVDe8YDRkBcWD3x81lcwJdRfQaWVld0CK4AIqLeKcD9k08LQmVVEKXq/FArU5I/AkTHKb3Nj1zK6Xkc2o+Oa1/rjdXH4NGT6iZJ/umUECHg7Nmi6e47JsQOFAbPY2SRrbK6YKTXGCMb1SQLmuaQV6ABqV08heMLoTereu4R/gqPr2f0MjE8o455sq3vRo/OrkHgnEN9lIWVxEjSETf3ddjhTT5QPkhN/dIr90s8TQG5KhYQR5jG+qdC2R0iN+434LH7oc1mrcnY/dk10fDPzjAm</b></elGamalCiphertext>";
	public static final String EL_GAMAL_CIPHERTEXT_B_XML = "<elGamalCiphertext><a>EPb0xRW2twSd/AlN4BzDzAe2XS2c9VlqT1bLu5wKm1hdyIYhxePJtBlAt/bpDPnssOZ9sE6zS+DpuGuY73yfQBUhHWv8/V/GUM0Xhgfo6dq2PbzXthQbzjQ7f6tKLRUYPp8fAcN8NpEa6seTdn00Ep+lwU35qIICfed4ovRSKo8jxjA3z7HJQOrtq9D4QDCC3ugW3nXxAqa0s8MCR3xKNc59bbXqFWTZi+eC180q4b9GPJqc/fCby2uznJwjm4I/0G8JC2BbjhT/PRxbfrMGZm8dzkDWr8fpUD1plqYpV+pioyDwvNjuzjWS7w1UYRCi8SZ8zyFou/LW7vAotsj/08X8YSjnIV/CJka59jpgJGP5hWSPX1yfBVQeKm7aIfY2zqDQpd9LbrfApM/bSvFnIviStBps6RD3c8RajzSx6jR7j6xVo+b31pZ3fCgTu5n1niwKRUHgnyImg4d3VDAcDh8LKj2cbmTcPRy2jaKe0XQwewYS3rza2vmoc7X8M1LJ</a><b>Frns4N1ve8I2rNBMAqVLSLEyE7KfjPe00W4/+O2+65VvuKXrhqmHjweV1RvASArlZkyVeb0hG6caIIaH8k0FNavCyO6HgIgqa75BV51on+OGO/RTs+D5N0R4/GuRQIkNg4P+nVYCdqFIXEgIFcB++5RoO2WfgrSydzkzLbFq5y++pZ3mYDfOr44nFZ+hGJVSdoD7TBm8ywlTgRKZNbOZITOnfjJ7Oe2wiG18X7Ht4KDMgrpigg6fhrlHhzSfloFsLwHZ3b0i6BrYoymWwVT1t4yp4Athx8nbdqS1yWpDzow3Vm/gcLWmmKVliYlc2ukvvPsg7LcfvKgUEhjTflDWmKXuUSj/oahq+ZHuvBk+VE6GEl7P2zcuYPlxxcfQwxh6olc0d2ijr3Mj9QxmmsgQYWpqfpeeYNKPNOIRtupjoHq7X17GT0hma7F3jR4jrmC4i7SR2kgZONBFeb01bK1VdGhEXFIXGERNLXExycHGS6vqGwDhoKxFbjfdr7Nwi50z</b></elGamalCiphertext>";

	public static final String PET_SHARE_XML = "<petShare>"
			+ EL_GAMAL_CIPHERTEXT_A_XML + EL_GAMAL_CIPHERTEXT_B_XML + "<exponent>"
			+ BIGINT_C_BASE64 + "</exponent></petShare>";
	public static final String PET_SHARE_NULL_XML = "<petShare></petShare>";

	public static final String CIPHERTEXT_LIST_AS_XML = "<ciphertextList>"
			+ "<size>1</size>" + EL_GAMAL_CIPHERTEXT_NAIVE_XML + "</ciphertextList>";
	public static final String EMPTY_CIPHERTEXT_LIST_AS_XML = "<ciphertextList>"
			+ "<size>0</size></ciphertextList>";
	public static final String CIPHERTEXT_LIST_XML_WITH_NEGATIVE_LENGTH = "<ciphertextList>"
			+ "<size>-1</size></ciphertextList>";
	public static final String EL_GAMAL_PROOF_DISC_LOG_EQUALITY_XML = "<egPrfKnwDscLog><g1>NKcFJlitm3dZmrEr2DeNXFppMk59PizhbB6khXKf96vx4hyU9mpgN2ZkUAPGXo/LHhXMVyrX2jh7Ark1JrrVzWv6+QsHGh7djBFaImshdN7gWWmm1GWVKb2EdpXJ/AMCjLGIfxk+lgyesU2t/xGn3p80TfxEPLXERQVS7U5s+vej8+xKS3U3w+fCjpoO5enSFD+nw1J9sErbhlSpfVojLr5v4AgYaoTO0hA1vFECASvE2KMIvvn947xHsj0T1Lp0Vmfdoc8fUAhtP+3HrhnLTBp2T2N23Vl/mXg8iOUk/gfyCCHS+iFNo1C/X+tF6nY/l5X8eLb1H7MonXalKZBCOXFl6tXTSGd+Ujn8Vg1b4L0iDlKNPtZq1d3CP8rnbSTDTY6GXZqbcbcTEUJZmseudK/TRNmd1J/aJFeM5aBqz+j5HZWDLO0xzXtgVAQkp/mUIwFwkCYu8ayIQNWZdbtgjvWOfxR0AoTW4i8aYriFSXnmQKe4gL3wUcUKv7SKJFrr</g1><g2>az5EMeordK3a0VbjoUsKeo/++OHFs5WaRLw6hW8Ezu7D1Egid0U3Obtzpn0GG8UHQjJnHINqwK6bX6RXBlVfsDmiKS2lgvWrL53vSPXtZfFIC/307vWLV2RymA9TQaGZ5d4q7O7l2KHNUx+ecK6QVqKqj46IXjwaYzKQXNg2NfwQGfj7dBTaoGHE401pdoHfyLfluAF3k4ZT6imEoIbe4Ar7ybYdI5eJYn96/hoyLouux8jI1LE+Oe7+eI1M5WK30TPbGKVaon4KPnfLYp8+HTloYb/jlNFLckqdZchKa765pBRjvNu+7MmYpHmqBlLuCLd4sBbD//Sy/aHPnxyccZM0MMwRPSSM0NKO8UuXkCEmRSI6gvdwyIk6FwsB8bF4ksvBAMSfN8gJd7rv1t9B46OByCs8YNuL1eJH1wJThwpUf1iAXPjFCkvrR4fm+lOeIYCGhBkEO1pB2sRzTulRVEadM6bFwo2N5eZb3ymPfERXogTVmljtdzxlAao9vBgk</g2><v>LD3Xb9CI+9aPuf4txqHYiDyOL4PcF2RAzcOndY4FeWBOlj7K44vApsoEVdM7NORPAyWFgD5ZukhP101ovmQuCzS0cvlrRdhDCEnItsZTIgJihXa6AO9iYj5Xt9WfIxnTK6awTfsqQG6Mikhxdgj0qtJcJGzr9pVECZYWtjl5j8zwVnglqnes8NEAPCqaFRiHlYI2yepb6qSHjJAcRNZJ6vaMAtg3og9f1tdNGE332/2uglZ+zI+lOnyvnJCTMriC4qFPsgdfZop24YvnJQTEdpx1KdcTA5sBFxiYxRWtBDUS8eRKLv2l6G7aIOdrWBmvIjFcMqBmyOiT9bDl4DwZE+Y/UKV7y3mx2HIJ/ofI1SBbAOE11s3cCoeXboi2LbOQ7BE4L15owPKtZliKhBFwhqJ3cz3L4gZUQ2EqV7nc6S4aC7YYIoVRzqA8juLClg51k7KTZYrcmWw8DR0fH7iNJBPmGqnCqYnFuSzch+0SmJGAQCA9RwEiYSafncM5IO1S</v><w>bYNRiWtK8H8jaehxs1S+YKemiiip2QacwnwcQqKHsPBE1KzsZEBVquxOmPa8FGLV4hUd5okpWdQImk9ntTNCKC3q9yq7Grr7xUAoS3QMwQ0N4B18eENIjUO9eACm6IcPf+3tK87fp7h35ZQHA0XJJNxSVRz97ARYjIcXg9x/jBPbsHqXDfkrAhfneWqBajxu5+8kygrp3pS/DVZI9cB/WUvfS5idySJ/YLQKzcXxlqSPeRdHN3vNyE/DaJ2MGHqiEJE1qo2p3kc5R31gceixV9WQFQn7PJ9RrrZ1Isvar+zpsStQdH92Kr0KU+3d/fxuJnpxToA0J8J/mpIat7uj9jugW5qCJ1ERRarjcbjtpi1jZKaDB28704VzudfgAQq5U3O+0MsWRgJ6MClQvWWwXiPK9xAmm7u3b6tvUBemAQhvAbpYgoCmyuq8UiDo7H7EiAB27OcpfrjbprUlEkux7DapIW+X6V85qqZX/myyjnO7kS9l2ju3JYUGOA2A0IlM</w><a>Rv8nzR8sePqrFAUcQpTG1wp/ppOvVKX2Gc4C3rpPbpQu5si1p9EvgjNSn3eTwwzMeoz0KpuZn1ccs0pMaFqAUW8WcUz80Hh+w4FMvM7yinmZAAzO+W8eEvQ4dNAR1aopWAbDcJDEj6+sq/3Esz9LZr5OEfbeUlzy8u3fIsfqhIsTyMnV3bfMC2sbv7i1Bz7udFa2uBCm6eHgqobZmVWxLFw5jloe1oJapQxjx8lHcatzTmdox0VrChF61On7k0gdWMTL1hffiMnNFaCWT5A3lv+YJ378Xc5cpN0eY5BXnSJ804qdXshg5+fRTukbizmQj6l9nuKp/HwmHL6IbUO651EoYEc1KDNfogQH2Lt4QhQ4RpO5qDjjqOviPLj/+0uR8/FFVzyNSxJUv2K/+UIZ//4JwlEAtczX/eD+bIkodzFOF6U5REo0o3Vn8+b7rgbhvyasLTuHj8v/xApSNd+Xxm1fPg7F/dBfUu+7LBdC7XqEYj59PctCU3a/JeEdqaVA</a><b>cZP5shp3SOALmUwEY6Zj6crdGhQzIB7fkbIS9sLCUzI4tZDlzROdfCm6EHz1sd5ekqrVwzRfwKBBuOrGeoujtfXJ7e1U5nxgCzcXTJAwhIsuM0KVyvgPV8krRzzRf1/hP7hSwzh4eWCpWKmzfREuoJBe0RKnrv+jBi0U46tSRRgz9qg5x3K92fTEtiCWQnLb5JlA+VWbJNxsdGm99o2t4wcmRdK2ZmOYFPdxmpCLLV5eCWCHCO/rZxBtWwhKScvwhjjbcnSlefNb4MXjEwk3j97Uulz7rt35KKDUMmcxDjpwHbkoAl7lbhtXNPgaPIrcIM60DDdclM8cwXz1ItvUbOzlprPLgjyskGj4g0Kmz+K6v/NfFRCKM2FA9QKlMSlpGWGsQt68+EYAvgTNAdoiSNdoDP+YBY1B6ITg384VcHOIzBhORrLupPR9iPUoIvU0GelBNciOhXjnFUjuWTSpsoCj47N9GBq9GGsYXpTBuXTMI/9wIsqUPT+sjOrvQERl</b><c>B28X8Z/5mPsYbQ1KMeM9pw1+kCL641p4IKzGdYRBKcs=</c><r>fjFmifAPnCcEJRCG8Xu51T0ATrAJ+IxsaIoJGh8iLts=</r></egPrfKnwDscLog>";
	public static final String EL_GAMAL_DECRYPTION_SHARE_XML = "<elGamalDecryptionShare><ai>"
			+ PUBKEY_VALUE_OTHER_GENERATOR_RANDOM1_FACTOR_POW_A_BASE64 + "</ai>"
			+ EL_GAMAL_PROOF_DISC_LOG_EQUALITY_XML + "</elGamalDecryptionShare>";

	public static final String EL_GAMAL_DECRYPTION_SHARE_NULL_XML = "<elGamalDecryptionShare><ai></ai></elGamalDecryptionShare>";

	public static final String EL_GAMAL_PROOF_DISC_LOG_EQUALITY_BAD_W_XML = "<egPrfKnwDscLog><g1>"
			+ BIGINT_G_BASE64 + "</g1><g2>" + GENERATOR_OTHER_BASE64
			+ "</g2><v>d9AUMsYNk5ZQ+ZbjfZz7UgyjyklNj+gwz/MjRyrlLjWVnK/ghtTfAv4qIisD407XCf8Whv+2Us9JcXDuh2ptfNIWJibe2RX6ivjBH/1VyaOylbJ4BGfLFcyNiPxrjAPzJ1LdlSHjkXII3VrmyD72hF4gnzrKExGmDyi9tffhcfZoZvTgxzeVdjvR2PKK/k4Ug0zqjyQyKJUnDP3RB1q+31/RB3S6E+LMyr9ToxQWRS7IymyzsQXV6smygtzZKE+PBU+gIJjxQ6ALt3/VImxazonOPhO9VCSoNfTVwAVvK5FdXvS6utK0GHTnHx0U/D/4W880NWwbB7DIxdvpYnjpK45MxVvoGelhUqMMPOjMM+7cn3o6QLATVB1QHbo7w7yH2P+Q0tcr9itruIjI2sajoZkEMG5n6DJunwqHVZx38BScxUlmKb8rKW8+AhOC3BXBe5biUDReBdRtpSkOFi5as1mWxsIH2EvGAG2bPUtIZuCn0phLG37M32XyhDiOEehm</v><w>"
			+ BIGINT_C_BASE64
			+ "</w><a>bYNRiWtK8H8jaehxs1S+YKemiiip2QacwnwcQqKHsPBE1KzsZEBVquxOmPa8FGLV4hUd5okpWdQImk9ntTNCKC3q9yq7Grr7xUAoS3QMwQ0N4B18eENIjUO9eACm6IcPf+3tK87fp7h35ZQHA0XJJNxSVRz97ARYjIcXg9x/jBPbsHqXDfkrAhfneWqBajxu5+8kygrp3pS/DVZI9cB/WUvfS5idySJ/YLQKzcXxlqSPeRdHN3vNyE/DaJ2MGHqiEJE1qo2p3kc5R31gceixV9WQFQn7PJ9RrrZ1Isvar+zpsStQdH92Kr0KU+3d/fxuJnpxToA0J8J/mpIat7uj9jugW5qCJ1ERRarjcbjtpi1jZKaDB28704VzudfgAQq5U3O+0MsWRgJ6MClQvWWwXiPK9xAmm7u3b6tvUBemAQhvAbpYgoCmyuq8UiDo7H7EiAB27OcpfrjbprUlEkux7DapIW+X6V85qqZX/myyjnO7kS9l2ju3JYUGOA2A0IlM</a><b>WQpOdIhgR+KH1I9+lYcl22z9xVYgc4qILfBCtAiRNgzVXPyEmmNnxAsWoLfc8BVuby4myfUf0n6Ck/fbFb7KmFqgTwUFcKCyCNYntd8REmg8Ls2MyS2Ra0iN42YIY8wXWsRrG7b40x27w5Wa4sCQTnERwsreiYAp6d1Ipy7CI3VYkOofeEx/hA/JGyhMbXJBnu0NbRTfxJGEdFzQViWxnsMG7U0jc8U+dzmLnEr2Fqad8v6d+fo76/TKql5Tlf/TjkRXOccyytFDv3uR00xTye3DhfxQSEepg8q+oZEktYsKaAZtFI0XA3o2iLIEU3bIRY7osIwRF3NG/tsJe7lY64C/XlnjYrjZXPJU8lFVa+ia/qf/pBG9iJrxob7bNNusK4T8JzUpa59xj3l+GYTP6xA2+DsIFoE7qQxlzBJ+iZrm4qPDXrX1epXqdtqZxDJ7SMkC7RezNXE4f99zUuyzwjawoloVqwqxdjcTqcA1i58i32PbQ9Nk4GyaMJK8xpPV</b><c>KuVSbUZLCxsOwKXOW+l7eHlxJp4/u8Asi/Y5kRPXnSQ=</c><r>V2q34Ktm0U62EhHJWMVvQUxNec+E79FBqqFyNn5O8K0=</r></egPrfKnwDscLog>";

	public static final String EL_GAMAL_PROOF_DISC_LOG_EQUALITY_NULL_XML = "<egPrfKnwDscLog><g1></g1><g2></g2><v></v><w></w><a></a><b></b><c></c><r></r></egPrfKnwDscLog>";
	public static final String EL_GAMAL_PROOF_DISC_LOG_EQUALITY_NAIVE_XML = "<egPrfKnwDscLog><g1>"
			+ BIGINT_G_BASE64 + "</g1><g2>" + GENERATOR_OTHER_BASE64 + "</g2><v>"
			+ BIGINT_B_BASE64 + "</v><w>" + BIGINT_C_BASE64 + "</w><a>"
			+ BIGINT_A_BASE64 + "</a><b>" + BIGINT_D_BASE64 + "</b><c>"
			+ BIGINT_A_ENCRYPTED_SAFE_BASE64 + "</c><r>" + G_OTHER_BASE64
			+ "</r></egPrfKnwDscLog>";

	public static final CivitasBigInteger BIGINT_A_ENCRYPTED_SAFE = new CivitasBigInteger(
			Base64.getDecoder().decode(BIGINT_A_ENCRYPTED_SAFE_BASE64));

	public static final ElGamalReencryptFactorC ENCRYPT_FACTOR_ZERO = new ElGamalReencryptFactorC(
			CivitasBigInteger.ZERO);

	public static final ElGamalReencryptFactorC ENCRYPT_FACTOR_C = new ElGamalReencryptFactorC(
			BIGINT_C);

	public static final ElGamalReencryptFactorC ENCRYPT_FACTOR_D = new ElGamalReencryptFactorC(
			BIGINT_D);

	public static final String PROOF_VOTE_C_BASE64 = "JLjaqZUzF9Ja/0klrpAtIPe2sETCRf+0OlWeiI1TV/M=";
	public static final String PROOF_VOTE_S1_BASE64 = "S/9gPijKaeaDapS+YtHnBzKupvsiFDWRIaO5r1t1Ja8=";
	public static final String PROOF_VOTE_S2_BASE64 = "RpmdQslm/BT1fr9Bf2NsCdPMiY+ch4reYv+bEaa/RMo=";
	public static final String PROOF_VOTE_XML = "<elGamalProofVote><c>"
			+ PROOF_VOTE_C_BASE64 + "</c><s1>" + PROOF_VOTE_S1_BASE64 + "</s1><s2>"
			+ PROOF_VOTE_S2_BASE64 + "</s2></elGamalProofVote>";
	public static final String PROOF_VOTE_NULL_XML = "<elGamalProofVote><c></c><s1></s1><s2></s2></elGamalProofVote>";

	public static final CivitasBigInteger PROOF_VOTE_C = new CivitasBigInteger(
			Base64.getDecoder().decode(PROOF_VOTE_C_BASE64));
	public static final CivitasBigInteger PROOF_VOTE_S1 = new CivitasBigInteger(
			Base64.getDecoder().decode(PROOF_VOTE_S1_BASE64));
	public static final CivitasBigInteger PROOF_VOTE_S2 = new CivitasBigInteger(
			Base64.getDecoder().decode(PROOF_VOTE_S2_BASE64));

	public static final String DVR_U_BASE64 = "MrPYEhsuQnQS3bfumE7dMkcT3mzW+91OzA67xhDjXBs=";
	public static final String DVR_C_BASE64 = "ASkSAd1PJ+Qyc5LHaF2zkO/JuPcI67TMGFRuvVqNkLY=";
	public static final String EL_GAMAL_CIPHERTEXT_EPRIME_XML = "<elGamalCiphertext><a>bYNRiWtK8H8jaehxs1S+YKemiiip2QacwnwcQqKHsPBE1KzsZEBVquxOmPa8FGLV4hUd5okpWdQImk9ntTNCKC3q9yq7Grr7xUAoS3QMwQ0N4B18eENIjUO9eACm6IcPf+3tK87fp7h35ZQHA0XJJNxSVRz97ARYjIcXg9x/jBPbsHqXDfkrAhfneWqBajxu5+8kygrp3pS/DVZI9cB/WUvfS5idySJ/YLQKzcXxlqSPeRdHN3vNyE/DaJ2MGHqiEJE1qo2p3kc5R31gceixV9WQFQn7PJ9RrrZ1Isvar+zpsStQdH92Kr0KU+3d/fxuJnpxToA0J8J/mpIat7uj9jugW5qCJ1ERRarjcbjtpi1jZKaDB28704VzudfgAQq5U3O+0MsWRgJ6MClQvWWwXiPK9xAmm7u3b6tvUBemAQhvAbpYgoCmyuq8UiDo7H7EiAB27OcpfrjbprUlEkux7DapIW+X6V85qqZX/myyjnO7kS9l2ju3JYUGOA2A0IlM</a><b>PDSCaCKyqTWpyPY5S6xnPojOfrY1tegFiOyLfvjFuoRJr78VWRBm8vfoSeze7YrYXfjvozsV6c17vGebFg2lb0luMzg8a0CHKIra9KOOzAhHGXwTf86FI4MZdGg4WHrEn2HV/b3e4SUN+mDVj/rbEno+OYW1MGKPKy1kWhLJEN1pCEfYrBSx5nNEEhXUj8ENHzMFjcfC6aQryHrzYXOeBmAngCFXS7t+z2i9gvtQe/9uzFtvRstajNdbVawgUZUFvkyLVVC2uW9TZauKbBY53Y1q3xv/rcTrdEcJg3ll3i4wJOFDZSIt2BUM5nyMy4Fr5tTLecEhcycO8Tn11r/PZEqvunSV0hJT/M28n6mxIprBosjkENe+rLA0bSJVQ7Wrx9+Lcdotw5YZgrzwIKV53IuTjuC1+Ttq+PQ4c5ga7DHj14L4fl4kIntPXTjvoGOAe/UW22tdlGL7USadlbLhF0pQT9FoMh9vbtAtJo/BQqFLgEdzTWXQevUWkGiPYcB+</b></elGamalCiphertext>";
	public static final String EL_GAMAL_CIPHERTEXT_E_XML = "<elGamalCiphertext><a>R/gEXJbvQz2fT1ugfIDbEqeMcW546Bb38EK6rgRF/lHWXEIFDuniOe93WieT06kVdhbaPZlimFIt8wj7I9M6Bwg9zTjUdZ69n1FSPncj1zdztC74q6cYekz4ZtHIgz15q7diOMP8gIGZK+f3wQ9ArmZ8WBibEo5jRESUX5MdroyYia5+Yyk8SMeaVve9nEE+koZf3MdOCB4vv9kxSJ2tHIXMwpsh5qg+wTKMiq8YJFeGfoJePWPZKivIwpwnWKsfiHzNmp6GgZ537nRRZ16RAHoEIGhiRriFeH45EHYiyRkdbzQqUx1FqX2aCuyCeOE5HfefDQSzT/z0I/LGgzB1elxcj/oJu6DdfheorS1rqDELCfLoH4bzl0IZsceLBecIdnj+rfqVm5AYoB7ZMLOxRtrMihiMj/melAg658z2ATzbw5hJxJADxKn968bhVQL7HaAMmaYMMhb8kxrScwYRq0JA5tJB8g/DsuGlSx9zCFfZYYugHMVMkdnq45oUEZPR</a><b>bMsqXdO9UQpcmYMlXfsBRZ2m6HLMwWegMiHCleYllF7UfbY89g90TS0YXy2qszrFzDA/01C2/vuenadInhGxf45pF/fZ7ujcHORd3iYqIJbDokjZwXGVWCgpCa6maOLXJG+kGvWrWd3jEgGwSmTwRsboqpMgVJ2F8GdsZZfaePL8/SQdALJOFb9Ga8ffSs8dg0w7tshe83jnYSe6IDuH9tUweHhd1/HaSLKBhdIduumylq/jCOXzbbim5L3anJb2Dn5vcR9ecf4W8/0/vSSkED/GJF1igZ8S7sqaqlxvmT72lwN5+u4uFOevjD0/yxLYjpeXWMhOIOSk7mtEps4HGrOoDBI6qg9eDeIkLoxbYhg+9IXAsbR9ubTe067gUUujT4AZouZl5Vj6ZbElD1nCSOKytZVUm40m9cwzmSDypXC/lxiR0SOYTlx0jYw6eRmRE/NxztlCWTF52jkVkliCU5vvY6Yw0qb7g7CQzcOPKf2Dby1an+wO+SmqV58t5YY+</b></elGamalCiphertext>";
	public static final String EL_GAMAL_PROOF_DVR_XML = "<elGamalProofDVR>"
			+ EL_GAMAL_CIPHERTEXT_E_XML + EL_GAMAL_CIPHERTEXT_EPRIME_XML + "<c>"
			+ DVR_C_BASE64 + "</c><w>" + RANDOMS_1_BASE64 + "</w><r>"
			+ RANDOMS_2_BASE64 + "</r><u>" + DVR_U_BASE64 + "</u></elGamalProofDVR>";
	public static final CivitasBigInteger DVR_C = new CivitasBigInteger(
			Base64.getDecoder().decode(DVR_C_BASE64));
	public static final CivitasBigInteger DVR_U = new CivitasBigInteger(
			Base64.getDecoder().decode(DVR_U_BASE64));

}