package civitas.crypto.concrete;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

import java.io.StringReader;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import civitas.common.CiphertextList;
import civitas.util.CivitasBigInteger;

public class ConcreteTestData {
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

	public static final String BIGINT_P_BASE64 = "AIAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAQ0gWk4zQiy9p87hS+sgNkxYr5okLM6HhXFTu2eUFgf5BQjju2YsvFhOXce4yJ00rsGSGeZAg5bT1Z45SdexGMevXEdCZrADRdikYU0ZLFTN7UWopWgLXd3DBfu3CY2fzwYzq0YWS0bzJ3cQA4fSAyFdU+Tekcd3vwQlkthh7WJW0VCYF1hGFdiGt9/aDQ7cDrW+fqbg4xrUN+wKoFbEHNHomUkGMaXsGyM9vyLCjtp9Jf/UXQSU9X+jAJS+Y7VXyEa9/ifHxsjAExE5RYpNWzqgjJRoiADVL1XeoqvdL/ltcEhAeq3TnhHNIi7cEGQZSGCvSVMiDPn2JBUeY8AswihwZhI7IiqroysQy6UcsZ45oACaVH0ZYSMSIvuGimPhqv0OVbR95lXipxaoHlygq8pLWJWlgVj9KIQQG1wTnD80liudqIdQ+/yuo10YxtGacmC7YxB/atrTgbJ8V/wRDRQw==";
	public static final CivitasBigInteger BIGINT_P = new CivitasBigInteger(
			Base64.getDecoder().decode(BIGINT_P_BASE64));
	public static final String BIGINT_Q_BASE64 = "ALEbdrNCujCQI0Y4f38yusJkfR1nVqT95/P/H2z28zIH";
	public static final CivitasBigInteger BIGINT_Q = new CivitasBigInteger(
			Base64.getDecoder().decode(BIGINT_Q_BASE64));
	public static final String BIGINT_G_BASE64 = "az5EMeordK3a0VbjoUsKeo/++OHFs5WaRLw6hW8Ezu7D1Egid0U3Obtzpn0GG8UHQjJnHINqwK6bX6RXBlVfsDmiKS2lgvWrL53vSPXtZfFIC/307vWLV2RymA9TQaGZ5d4q7O7l2KHNUx+ecK6QVqKqj46IXjwaYzKQXNg2NfwQGfj7dBTaoGHE401pdoHfyLfluAF3k4ZT6imEoIbe4Ar7ybYdI5eJYn96/hoyLouux8jI1LE+Oe7+eI1M5WK30TPbGKVaon4KPnfLYp8+HTloYb/jlNFLckqdZchKa765pBRjvNu+7MmYpHmqBlLuCLd4sBbD//Sy/aHPnxyccZM0MMwRPSSM0NKO8UuXkCEmRSI6gvdwyIk6FwsB8bF4ksvBAMSfN8gJd7rv1t9B46OByCs8YNuL1eJH1wJThwpUf1iAXPjFCkvrR4fm+lOeIYCGhBkEO1pB2sRzTulRVEadM6bFwo2N5eZb3ymPfERXogTVmljtdzxlAao9vBgk";
	public static final CivitasBigInteger BIGINT_G = new CivitasBigInteger(
			Base64.getDecoder().decode(BIGINT_G_BASE64));

	public static final String P_OTHER_BASE64 = "AIAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAf8tZIaH63gtefkwkLYnCdtF2sMI5MRddL3IgM7SZK+auYCtQitiTHGI0TF8xu5lDM7bV4GvZA6tTBEaKS9EMxD34H+5xFtBtRqWuCI6XMPEMJg71eqhVT3M5yEqwA1wJXXvD/oKs6nlx+MxcEzb9kQweaVE6aOa/9hPMFdU0wL2ZatNOJfN2+CmsweXdC/gnZSX4ySmWAGP4JTHq4abv1SN4JwkUzgsrWownWy2Pzm5fiCeQBc0yn70GTSdIxuDnfX9em+ORiSXhlh7+ddNcgvC35wkHmfjWELTBhUDUh1L8gQWWEQsq2m56u2cv6sdY6FYo2GuxzASJ9vIWQBDS1Et9AJzs3dVN4PjdKWt2peAKGb1Gl5rcBEmM5b3oYhWzSLSu8axcU2dqP0FNvPpkrD98hEAvWyW2sU/xdk4wwGSw==";

	public static final String G_OTHER_BASE64 = "UVvkQGS9ULYLZfgomIk1JPAkhBjF3FJJf2v/BESOIa0GSawzuu8+UJ3AhsmgF+bb8jhqfUq/UTqyBTR2IFKyItkUrb/B1UoK/wvaw0kbKyfjUrCGXnXOHnIE8bu/6Drjj2QzDy69kKJP37PGhd/jaRxdqGiSoEy1TAD1a/mjkjhmJmUSk4FZdtXUW6rwlq40C63dlKW2pWru+B6XRjtURM1RP4w7joKvcV2N4x3OAQs3/skjmUXyRQoU1k23eoNCee4wYadG+fne2+4MTvr/Qb6JIG4TOEmGmWO3ADHdwOHQxy515OTMP+DoffBMHFZKy7OZnC4G7q/+qHzVbVAJc8iwLVy73zDt21g7PgvEUB+ndESkhWUmVTNBtWANLGgDswoyvZXX8wu1JC5lI485dm8ux8VP5k/4j9TYIA9PzDRxs8M6ykF5ej4XAaa80Yuzpbxw7Eex2rZHiys79Hn3jiM5vhOTND8hdb4Qexhqu5jQw4rwj0j094M921WrScq6";

	public static final CivitasBigInteger BIGINT_P_OTHER = new CivitasBigInteger(
			Base64.getDecoder().decode(P_OTHER_BASE64));
	public static final CivitasBigInteger BIGINT_G_OTHER = new CivitasBigInteger(
			Base64.getDecoder().decode(G_OTHER_BASE64));
	public static final ElGamalParametersC EL_GAMAL_PARAMETERS_Q_SAME = new ElGamalParametersC(
			BIGINT_P_OTHER, BIGINT_Q, BIGINT_G_OTHER);

	public static final String GENERATOR_OTHER_BASE64 = "B8Ewf1Z2BaJYadh/hzQ2kjNGPmYWBIvA6ZxHSpVpB/Sw4m40L8qWkwsNrfUh87L8GIJM0QZ4tHgYCdduHUjiWmcStRTh+2RvNoinIrK5QnxaW3g6Y2G32rxutDlOKwaoMMbrut50O8XN12mD4wR15inhh4+QVyzeobFVSPnpgheb2gt9i5pTKJY6JQkpcZnD4bHF+i7MGelqeoyGmLGqZu98ODsSxn2HfmMniV+p+aaTYJV8niINbYlvvz2BSwf86YdlQOa5WfzzPGSltmt5v5ACs135JCIw3kRzepRzjWGAINHv+SgPcBVSVTwtuBoykYhxzT03CWWo5WwZ+NzdmgDih7RheaQpMlIfSxP/5ZJVukgDxJv4v70F3KRwJ5V5jY26sjETz2Kayn6y38EfDuQjeOAUOamGYcHpdqKdPySJWTK0o6sfKIQ2etMZG0fUFEEt3CdsoGOUi31saXFDK4pkHqu+jQMzCg2Z5EsXwVgZ6J0SD1Nq/DDGvAU2lQdU";
	public static final CivitasBigInteger GENERATOR_OTHER = new CivitasBigInteger(
			Base64.getDecoder().decode(GENERATOR_OTHER_BASE64));
	public static final String ELGAMAL_PARAMETERS_XML = "<elGamalParameters><p>"
			+ BIGINT_P_BASE64 + "</p><q>" + BIGINT_Q_BASE64 + "</q><g>"
			+ BIGINT_G_BASE64 + "</g></elGamalParameters>";
	public static final String ELGAMAL_PARAMETERS_NOGROUP_XML = "<elGamalParameters><p>"
			+ BIGINT_A_BASE64 + "</p><q>" + BIGINT_B_BASE64 + "</q><g>"
			+ BIGINT_C_BASE64 + "</g></elGamalParameters>";
	public static final String ELGAMAL_PRIVATE_KEY_XML = "<elGamalPrivateKey><params>"
			+ ELGAMAL_PARAMETERS_XML + "</params><x>" + SOMESTRING_BASE64
			+ "</x></elGamalPrivateKey>";
	public static final String EL_GAMAL_PRIVATE_KEY_NULL_XML = "<elGamalPrivateKey><params></params><x></x></elGamalPrivateKey>";

	public static final String EL_GAMAL_PARAMETERS_SAFE_XML = "<elGamalParameters><p>ASvIFyEHZA21reK32LTtYEwJG4GSW8Xzrl83llQSdjcztgsIJeyQm0ZZ36vG4aTVYjtADzV9J6xJ5RJ2U9QDfV8=</p><q>AJXkC5CDsgba1vFb7Fp2sCYEjcDJLeL51y+byyoJOxuZ2wWEEvZITaMs79XjcNJqsR2gB5q+k9Yk8ok7KeoBvq8=</q><g>AKhY5FkyO989WVWT54UIYi29aZjUfx1XD0xlcF13Y7nGa7itniOZIj36m+m8XHW9VN0bzCgLh4btlHBQ8AY3dMo=</g></elGamalParameters>";
//FIXME substitue here:
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

	public static final String PUBLICIZED_SAFE_BIGINT_A_BASE64 = BIGINT_A_ENCRYPTED_SAFE_BASE64;
	public static final String PUBLICIZED_BIGINT_A_BASE64 = "bYNRiWtK8H8jaehxs1S+YKemiiip2QacwnwcQqKHsPBE1KzsZEBVquxOmPa8FGLV4hUd5okpWdQImk9ntTNCKC3q9yq7Grr7xUAoS3QMwQ0N4B18eENIjUO9eACm6IcPf+3tK87fp7h35ZQHA0XJJNxSVRz97ARYjIcXg9x/jBPbsHqXDfkrAhfneWqBajxu5+8kygrp3pS/DVZI9cB/WUvfS5idySJ/YLQKzcXxlqSPeRdHN3vNyE/DaJ2MGHqiEJE1qo2p3kc5R31gceixV9WQFQn7PJ9RrrZ1Isvar+zpsStQdH92Kr0KU+3d/fxuJnpxToA0J8J/mpIat7uj9jugW5qCJ1ERRarjcbjtpi1jZKaDB28704VzudfgAQq5U3O+0MsWRgJ6MClQvWWwXiPK9xAmm7u3b6tvUBemAQhvAbpYgoCmyuq8UiDo7H7EiAB27OcpfrjbprUlEkux7DapIW+X6V85qqZX/myyjnO7kS9l2ju3JYUGOA2A0IlM";

	public static CivitasBigInteger PUBLICIZED_BIGINT_A = BIGINT_G
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

	public static final String ELGAMAL_PROOF_KNOWN_DISC_LOG_A_BASE64 = "E9dC6sI2u2IUaXMbyB98p/kDMZhLLdwBMN1jH0ry15ZzmQBlx06eY8wi/iKjDObaNteQ9lSrVPUpY6QEEoP1IEV/DKr+6uCWVyBThdvwSvxgUJ1Xqovj8v+uRdfjrMBeqq5blToFmsBAxe3sAYLbEM2YR4oBdkj3V+uCHwA2dxS0bgk+axzgn3S7AElybSI+KtbkD3RC06WEK087hwSAjlE70BlSGyKj0rKn121dg0ZiyhNZDLuYkWm/yPkjgGh1MbIpjHto8v++LfMjLjvzNTAC0cBDCYE3IWIGTclfsX0Whe8BIJiiU8Gz2EBKA9Lgopr/SMPg+v3bPoDlZfTnRhRoM2PHSnIm4xcTsF60grC5nZk2h2jap/l3VifmCjGPbesSfnR3agICtmoQAwnV0QP1QFQDlh2kxgY1BFiMghyaz5ZgJ/YQ3jeubG05PQaDSS8PGuDYl4gq75RvB7W569dEN83r14t6QBWFnBeEq2LzsOViILBZYbyJQ7zKJzUN";
	public static final CivitasBigInteger ELGAMAL_PROOF_KNOWN_DISC_LOG_A = new CivitasBigInteger(
			Base64.getDecoder().decode(ELGAMAL_PROOF_KNOWN_DISC_LOG_A_BASE64));

	public static final String ELGAMAL_PROOF_KNOWN_DISC_LOG_C_BASE64 = "TonF90Z+Prf89FIqLgLftE2mgiMHSBK2hTbubM9P6yk=";
	public static final CivitasBigInteger ELGAMAL_PROOF_KNOWN_DISC_LOG_C = new CivitasBigInteger(
			Base64.getDecoder().decode(ELGAMAL_PROOF_KNOWN_DISC_LOG_C_BASE64));

	public static final String ELGAMAL_PROOF_KNOWN_DISC_LOG_R_BASE64 = "JTUMRrCKUZcgvK2n3UtvaHybC92/xki4CFhPYFnD3Zs=";
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

	public static final CiphertextList CIPHERTEXT_LIST;
	public static final String EL_GAMAL_CIPHERTEXT_1_OF_L_XML = "<elGamalCiphertext><a>"
			+ PUBLICIZED_BIGINT_A_BASE64 + "</a><b>"
			+ "VFv/czKkqNzSiah+WxzVEAgZO9hVVhnCjqRH66PFZ3e1y1eWvCjP/SDbmuMFQLfSHzheGmtp3bGwvayVIIV5HnrWyQN8YMF5LM7QxX4rUcYDuC2wG3G+wszjbNIJhyWy2ejltNQFiv71cI0bTU5P9YhgNiuXsq0C1tAWdt6c6lqBW2Oof/g+2VSBHw/gJ+/spR6sf+H6hew4Y/szHEkuNIDBr3gkcpJE2dAk2RV6lr9Xx/Xy7eignXkeXqkpSzXK+qlX6u3gQbCwXIpmls57CAT/J4hBSbbXQbJPxMRfMemZm4hLSiHP70v2g1IYxoydNwL/sUPraXXgoiDFFqsxOSWeN9l3RysnsQUKC1GKON0YvUDNf5WqEQrxdPC/F4q0azNxC2Q3dgznC0oYYlBlaS3uDkTxi2tKSFiu6+mM6YUxKd04pplRtUPeqTLiFK5Q0giqkKP5mK4DLiKhdfJkOdkuS6YEzGiYzLRVuuYFWXx352e3Vcqbp8DpKsphFsnV"
			+ "</b></elGamalCiphertext>";
	public static final String EL_GAMAL_PROOF_1_OF_L_XML = "<elGamalProof1OfL><size>10</size><dv>awmfhAU6U7Wcz5kPkg5cqncO4Tz5tmc4ZtqoiRtyLpA=</dv><dv>R2yN/mgDmwcpUg+ZVUEstEySVXankjgvA95fa5wHGXQ=</dv><dv>AICmwameAGKMoXXKbfljqrBm+JOmPzS2klx8m1EZk9uz</dv><dv>QIj/R6x31/E8wqRzm9XbPb9DiYt2mScs4FhE+2wz4go=</dv><dv>ft13WHaYq8xNHO/24hSt4nlTjH7CKN5hhD5shAoDHm8=</dv><dv>DD5VVJY1BonuOc8nJe5xfYy3wf9kQya9U+sJaDbAQ3k=</dv><dv>AJXL5ONmIuoyvK94CcuoRB2bAdiq8Ok3Gr++iTDqsWeX</dv><dv>axVSQ9fOAIElaqV6wJS1pFT2wu9STftaKfcZkAc2tkM=</dv><dv>Lk+xgoUCqFz/JbQzwDXgbLh1v/HxaijFEZIO1U8q1as=</dv><dv>faC93a1h3ET+0Hzb+tvwUHHQZx9QurDrjq9lYXr5Nec=</dv><rv>LH3xOock3kMSCTC0JfaD/qWoikvKsCwYU4CZmDfyWsg=</rv><rv>AIRo36+ex/i5zY2YDDuGtxDmPoGIH/7M1XhL625qqBFz</rv><rv>CibppMYGZsz37KkI95Z2IStd6/wc3w1QxKyIiKz6dNc=</rv><rv>fgOK4diBwxwnJg1WGwQr5REV1psJa7Zm3PoQZWMImsQ=</rv><rv>ALAooPo4bF254BxADYvC6uOQyhPJOEJAKCk43DwzOdwX</rv><rv>Jzbj2o69WCs46w7Yqli6mBcbvN8t3MNhecxtjN1nmxc=</rv><rv>GZzjyxNJ+2MKQP7SoMOloMF1h7Wyv5//5wCTE3s5Ytk=</rv><rv>RHEFyxQhAgAcSsKqe1WkyzGvAzeVgaiYWs9JcPiSLfA=</rv><rv>bOSCz15zVkOFXQ+fH1sSTgsy305V8yvypIndB2vaJBw=</rv><rv>Ijx1dyYqyhR+v0QiPqTXl49sM5k0wp5DHmMsujVP6x4=</rv></elGamalProof1OfL>";

	public static final String EL_GAMAL_1_OF_L_REENCRYPTION_XML = "<elGamal1OfLReencryption>"
			+ EL_GAMAL_CIPHERTEXT_1_OF_L_XML + EL_GAMAL_PROOF_1_OF_L_XML
			+ "</elGamal1OfLReencryption>";
	public static final String EL_GAMAL_1_OF_L_REENCRYPTION_NULL_XML = "<elGamal1OfLReencryption></elGamal1OfLReencryption>";

	public static final String EL_GAMAL_CIPHERTEXT_NAIVE_XML = "<elGamalCiphertext><a>42</a><b>24</b></elGamalCiphertext>";

	public static final String EL_GAMAL_CIPHERTEXT_A_XML = "<elGamalCiphertext><a>OV06eyWps23y8EllxzjMvP1rt5qpLb4hF4C7Ov/edEbbkNvzAI2Uf4gB70IF0N5NGLo8lPLKUzIiyNVw4EXChSNVUoMMj2m2RGBWNLp0eAkVEy6a+g1X63LobizYHctc44ZqvH08ZmhcQ+RxoD6bhWZA3qxLUrGO1QTmbzO58vgus3U1H7DS5qqNPI6fLbs/xpRMC8vwRi9wVB4sok8+nX4oPP5b0Xhht1nVFz8iP7sxESECxxhea8BOaGGqe/1pB9TMzG30lER0RGoS5Zki0SXEO0TT7ulqvEThyz1aEIz3nPGlc/1Y4rwM43jiSpPHVauvgRC5ltBHcQfl9qH2M4t/F9TaXshOyCu1UL2q+TCcR6ERA7M76BmYBagzfIBc1SzVNKv3uTBVCpXQajq+RBz/1bX7vuSVuAR410yX741dQsDaUQ/uXuq9RcZFPRDxybEyyFzqjY9fJjTmwa7urHVIpObjFHiMbdv7aLv7Rq7li9UJD5MNDzsZ7WbE574B</a><b>IC3774r8d/p1p/HU+G3M1rccmOSgR1o2eCNC1JLy/TMpkBQ3nTOa3xf6VgpUR7pptFfOCPwANSiK+PEI5KCdoWhat9VLGkcipRkFdZUEyevwSi9uodb36roJWu6RHZ/YbGpMvp9MnJlDzos20fpLi5q52T3cco1vXfTBGWaLsEy/ptxFsutVlTNc+V/JOdTPGvynJvZcDcNP7zMfv90iuMj9OnEsi1EcS4AIZDOKbs/py9k3vHpNcWllz2hJs4S7TQnTwbkiG/CISnE9LJLe36+n2013guXMSRw6PnrDT1E6OnPZRzVWmQFa2kvipDifiPlNo7l/YB7Rh4ssSswTVPSXpmNqp5rWYmIYxyKK+1fRbe3lC8geXfSMJDVWklIu6NlAPKwkaavyj2VfOEYBReqACwwOjrGihEveNwwDD3rg8tWqaA8wRuBQY7F6mhbWa2YMoqjvBJXb3bTAjf9WcJj4izY95IDI0N2lckXRNh/f01q03xMpAw8cdCboM46j</b></elGamalCiphertext>";
	public static final String EL_GAMAL_CIPHERTEXT_B_XML = "<elGamalCiphertext><a>Xj3axcYXaDSCgtPeL4j0kt4sjD607TMKU8db1sUOKNd7kLpTaMLul6UqLWonsX1bQVefMuSaB0RlIqvRvPXiCaEIydtaKi78JsdqggnUh0nSx+AXpc28zXIil3cBQVvxAVoaTTeeHc+umzfdgArhpZKKKQeq9t2lJ+2HaVYhfqPuPn6DQbyROjMVKyEJj8BkAPKo6MeDR0GZkdAKTEkdr8u9TeuBntbvVNo3MxnvkbMc0BXLxop/YUHVnVNM4iqEDVFqe1gH9Stgs4Raq+8V+l9j7FgulRNJJc+ILZPBCUCmWai3PBIlsvcMAuC+k8RwtXcbV0y+yh3DxvcUkEuCZB/6gUbM2BdM58es1hNclZ5krQy4LJOBeK5YbwmLE+tMInLypHPNNexGmOkjREDno2efpYp7d83Ge8bMpipPr9MDDQRDvFBQKBukfvpqX+ynSp4KIYKWx+0yuYW+UgtTEzAI3jHblk8dQxrl+qkAekmCY6cwEXmfnRbWkmoDKYyd</a><b>eCeftF5UPY+fKTNuHf2cpiI8vu+6hmPjwFNeJAPWMkvmRjWJItCRf200bqnGfY5OfFSFfLcdbV1Ig86OY1aMU+pitrkUZhtFu9Hiz+LVDbUjHSyZPxPZxe0J6qUOQdMXHPj0xI2s5ilulSZWlHa4fAMyspQbqKZOiHxY10pad5X0OOZyPkH5IK5ZTkyYFtnYaesbDFia4OEsV/xe0lN9iZ/wy8tQn2Xkvbhu+AutiUyrEAQx9JFtE3BXHHziwpRCD0jIwu0evfiHfTrR8ZyCC/lv8ZPTG8wW+z+vqG+QWEuAm2+JeJSKMGgcOHWyJ18rH1/yyJtD5ViLCuSewmxp3lI4PjUSqzpEe4HFTgpNXbt/2i1wUVfmddwxpwJIZmQOC+mL0b/+WLX/cJQEWj3z3x2QA+a4+88hGsgGarIVcXl0/rkPgRAc/UVv1oNOtBWcW86mCOXEWSG8qCbcVPNMHPukUDsXlyjuikFoJgtWFzrbHTmNk1Dy65AttoOnErEU</b></elGamalCiphertext>";

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
	public static final String EL_GAMAL_PROOF_DISC_LOG_EQUALITY_XML = "<egPrfKnwDscLog><g1>"
			+ BIGINT_G_BASE64 + "</g1><g2>" + GENERATOR_OTHER_BASE64
			+ "</g2><v>d9AUMsYNk5ZQ+ZbjfZz7UgyjyklNj+gwz/MjRyrlLjWVnK/ghtTfAv4qIisD407XCf8Whv+2Us9JcXDuh2ptfNIWJibe2RX6ivjBH/1VyaOylbJ4BGfLFcyNiPxrjAPzJ1LdlSHjkXII3VrmyD72hF4gnzrKExGmDyi9tffhcfZoZvTgxzeVdjvR2PKK/k4Ug0zqjyQyKJUnDP3RB1q+31/RB3S6E+LMyr9ToxQWRS7IymyzsQXV6smygtzZKE+PBU+gIJjxQ6ALt3/VImxazonOPhO9VCSoNfTVwAVvK5FdXvS6utK0GHTnHx0U/D/4W880NWwbB7DIxdvpYnjpK45MxVvoGelhUqMMPOjMM+7cn3o6QLATVB1QHbo7w7yH2P+Q0tcr9itruIjI2sajoZkEMG5n6DJunwqHVZx38BScxUlmKb8rKW8+AhOC3BXBe5biUDReBdRtpSkOFi5as1mWxsIH2EvGAG2bPUtIZuCn0phLG37M32XyhDiOEehm</v><w>VEQvbL3Y6nwg4vxa9AsymcTlnCOQayn/CYU4xvjR9ZVHPXboeInPCk929omcYxyQCxBNCWNuu/cgQsXMhX1/pu5GQpHFrfh/b1SkOeifSkCokOyNXU1QgWvdO7r7HMqGN4/ko1qiIsP4ZZiGD7c9TMZZKKbXamv/SkWDewhIWDCgn5AetUSv+HrrsC0hFUmfzYy31K8E/LqmyYlCr6biNLmRt2dfkVzTaC0nHCwXbweeXAk2i5QGNm1aXC4WRNHCCLyaapV9Au60cbwE6LK6S2cCrLKUzaybj6ehLysWokjef/xGkZP04wRmzaqGgYKRF97NihcY4BioBqA1/uBhkdv8Hmr7lIODRcm9rZiLGKXFQZLjDF3o3wyskiUVVvHkRrIO4O7NF//b4slDNRao8d096RT4yTJDTkzNdHah+AEJC7nVFqHFuwsdTIF/gN1nfEYmmbSaTnMC3sJCQb2NWNhC4KZzocne7L6GA0rH0lnfzU0zHbOFT96d7pg2OVas</w><a>bYNRiWtK8H8jaehxs1S+YKemiiip2QacwnwcQqKHsPBE1KzsZEBVquxOmPa8FGLV4hUd5okpWdQImk9ntTNCKC3q9yq7Grr7xUAoS3QMwQ0N4B18eENIjUO9eACm6IcPf+3tK87fp7h35ZQHA0XJJNxSVRz97ARYjIcXg9x/jBPbsHqXDfkrAhfneWqBajxu5+8kygrp3pS/DVZI9cB/WUvfS5idySJ/YLQKzcXxlqSPeRdHN3vNyE/DaJ2MGHqiEJE1qo2p3kc5R31gceixV9WQFQn7PJ9RrrZ1Isvar+zpsStQdH92Kr0KU+3d/fxuJnpxToA0J8J/mpIat7uj9jugW5qCJ1ERRarjcbjtpi1jZKaDB28704VzudfgAQq5U3O+0MsWRgJ6MClQvWWwXiPK9xAmm7u3b6tvUBemAQhvAbpYgoCmyuq8UiDo7H7EiAB27OcpfrjbprUlEkux7DapIW+X6V85qqZX/myyjnO7kS9l2ju3JYUGOA2A0IlM</a><b>WQpOdIhgR+KH1I9+lYcl22z9xVYgc4qILfBCtAiRNgzVXPyEmmNnxAsWoLfc8BVuby4myfUf0n6Ck/fbFb7KmFqgTwUFcKCyCNYntd8REmg8Ls2MyS2Ra0iN42YIY8wXWsRrG7b40x27w5Wa4sCQTnERwsreiYAp6d1Ipy7CI3VYkOofeEx/hA/JGyhMbXJBnu0NbRTfxJGEdFzQViWxnsMG7U0jc8U+dzmLnEr2Fqad8v6d+fo76/TKql5Tlf/TjkRXOccyytFDv3uR00xTye3DhfxQSEepg8q+oZEktYsKaAZtFI0XA3o2iLIEU3bIRY7osIwRF3NG/tsJe7lY64C/XlnjYrjZXPJU8lFVa+ia/qf/pBG9iJrxob7bNNusK4T8JzUpa59xj3l+GYTP6xA2+DsIFoE7qQxlzBJ+iZrm4qPDXrX1epXqdtqZxDJ7SMkC7RezNXE4f99zUuyzwjawoloVqwqxdjcTqcA1i58i32PbQ9Nk4GyaMJK8xpPV</b><c>KuVSbUZLCxsOwKXOW+l7eHlxJp4/u8Asi/Y5kRPXnSQ=</c><r>V2q34Ktm0U62EhHJWMVvQUxNec+E79FBqqFyNn5O8K0=</r></egPrfKnwDscLog>";

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
	public static final ElGamalProofDiscLogEqualityC EL_GAMAL_PROOF_DISC_LOG_EQUALITY;

	public static final ElGamal1OfLReencryptionC EL_GAMAL_1_OF_L_REENCRYPTION;

	public static final ElGamalReencryptFactorC ENCRYPT_FACTOR_ZERO = new ElGamalReencryptFactorC(
			CivitasBigInteger.ZERO);

	public static final SecureRandom RANDOM_GENERATOR_FAKE_ORDER;
	public static final SecureRandom RANDOM_GENERATOR_FAKE_BIGINT_A;

	public static final java.security.PrivateKey PRIVATE_KEY;
	public static final java.security.PublicKey PUBLIC_KEY;
	public static final java.security.PrivateKey PRIVATE_KEY2;
	public static final java.security.PublicKey PUBLIC_KEY2;

	public static final ElGamalParametersC EL_GAMAL_PARAMETERS;
	public static final ElGamalParametersC EL_GAMAL_PARAMETERS_OTHER;
	public static final ElGamalPrivateKeyC ELGAMAL_PRIVATE_KEY;
	public static final VoteCapabilityShareC VOTE_CAPABILITY_SHARE;
	public static final VoteCapabilityShareC VOTE_CAPABILITY_SHARE_JUST_BIGINT;
	public static final ElGamalParametersC EL_GAMAL_PARAMETERS_SAFE;
	public static final CivitasBigInteger SOME_INT_ENCRYPTED_SAFE;
	public static final VoteCapabilityC VOTE_CAPABILITY;
	public static final VoteCapabilityC VOTE_CAPABILITY_JUST_BIGINT;

	public static final ElGamalPublicKeyC EL_GAMAL_PUBLIC_KEY;
	public static final ElGamalProofKnowDiscLogC ELGAMAL_PROOF_KNOWN_DISC_LOG;
	public static final ElGamalKeyShareC EL_GAMAL_KEY_SHARE_C;

	public static final ElGamalCiphertextC EL_GAMAL_CIPHERTEXT_A;
	public static final ElGamalCiphertextC EL_GAMAL_CIPHERTEXT_B;

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

			SOME_INT_ENCRYPTED_SAFE = EL_GAMAL_PARAMETERS_SAFE
					.encodePlaintext(SOME_INT_BIG);

			boolean doGenerate = false;
			if (doGenerate) {
				KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "BC");
				generator.initialize(2048);
				KeyPair pair = generator.generateKeyPair();
				PublicKey pub = pair.getPublic();
				PrivateKey priv = pair.getPrivate();
				System.out
						.println(Base64.getEncoder().encodeToString(pub.getEncoded()));
				System.out
						.println(Base64.getEncoder().encodeToString(priv.getEncoded()));

				SecretKeySpec skeySpec = new SecretKeySpec(BYTES, "AES");
				SecretKeyFactory shkf = SecretKeyFactory.getInstance("AES", "BC");
				SecretKey skey = shkf.generateSecret(skeySpec);
				System.out
						.println(Base64.getEncoder().encodeToString(skey.getEncoded()));
			}
			KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");

			PRIVATE_KEY = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(
					Base64.getDecoder().decode(PRIVATE_KEY_BASE64)));

			PUBLIC_KEY = keyFactory.generatePublic(new X509EncodedKeySpec(
					Base64.getDecoder().decode(PUBLIC_KEY_BASE64)));

			PRIVATE_KEY2 = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(
					Base64.getDecoder().decode(PRIVATE_KEY2_BASE64)));

			PUBLIC_KEY2 = keyFactory.generatePublic(new X509EncodedKeySpec(
					Base64.getDecoder().decode(PUBLIC_KEY2_BASE64)));

			RANDOM_GENERATOR_FAKE_ORDER = mock(SecureRandom.class,
					withSettings().withoutAnnotations());
			doAnswer(new Answer<Void>() {
				@Override
				public Void answer(InvocationOnMock invocation) {
					byte[] array = invocation.getArgument(0);
					java.util.Arrays.fill(array, (byte) 0);
					byte[] aBytes = BigInteger.valueOf(SOME_POSITIVE_INTEGER)
							.toByteArray();
					for (int i = array.length - aBytes.length; i < array.length; i++) {
						array[i] = aBytes[i - (array.length - aBytes.length)];
					}
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

			EL_GAMAL_1_OF_L_REENCRYPTION = (ElGamal1OfLReencryptionC) ElGamal1OfLReencryptionC
					.fromXML(new StringReader(EL_GAMAL_1_OF_L_REENCRYPTION_XML));
			CIPHERTEXT_LIST = new CiphertextList(
					factory.constructWellKnownCiphertexts(EL_GAMAL_PUBLIC_KEY,
							NO_OF_WELL_KNOWN_CIPHERTEXTS));
			ELGAMAL_PROOF_KNOWN_DISC_LOG = ElGamalProofKnowDiscLogC
					.fromXML(new StringReader(ELGAMAL_PROOF_KNOWN_DISC_LOG_XML));

			EL_GAMAL_KEY_SHARE_C = new ElGamalKeyShareC(EL_GAMAL_PUBLIC_KEY,
					ELGAMAL_PROOF_KNOWN_DISC_LOG);

			EL_GAMAL_PROOF_DISC_LOG_EQUALITY = (ElGamalProofDiscLogEqualityC) ElGamalProofDiscLogEqualityC
					.fromXML(new StringReader(EL_GAMAL_PROOF_DISC_LOG_EQUALITY_XML));

			EL_GAMAL_CIPHERTEXT_A = ElGamalCiphertextC
					.fromXML(new StringReader(EL_GAMAL_CIPHERTEXT_A_XML));
			EL_GAMAL_CIPHERTEXT_B = ElGamalCiphertextC
					.fromXML(new StringReader(EL_GAMAL_CIPHERTEXT_B_XML));

		} catch (Exception e) {
			throw new Error(e);
		}
	}

}