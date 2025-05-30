package civitas.crypto.parameters;

import java.util.Base64;

import civitas.common.Util;
import civitas.crypto.BasicValuesTestData;
import civitas.util.CivitasBigInteger;

public interface ElGamalParametersCTestData extends BasicValuesTestData {

	public static final String BIGINT_P_BASE64 = "AIAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAQ0gWk4zQiy9p87hS+sgNkxYr5okLM6HhXFTu2eUFgf5BQjju2YsvFhOXce4yJ00rsGSGeZAg5bT1Z45SdexGMevXEdCZrADRdikYU0ZLFTN7UWopWgLXd3DBfu3CY2fzwYzq0YWS0bzJ3cQA4fSAyFdU+Tekcd3vwQlkthh7WJW0VCYF1hGFdiGt9/aDQ7cDrW+fqbg4xrUN+wKoFbEHNHomUkGMaXsGyM9vyLCjtp9Jf/UXQSU9X+jAJS+Y7VXyEa9/ifHxsjAExE5RYpNWzqgjJRoiADVL1XeoqvdL/ltcEhAeq3TnhHNIi7cEGQZSGCvSVMiDPn2JBUeY8AswihwZhI7IiqroysQy6UcsZ45oACaVH0ZYSMSIvuGimPhqv0OVbR95lXipxaoHlygq8pLWJWlgVj9KIQQG1wTnD80liudqIdQ+/yuo10YxtGacmC7YxB/atrTgbJ8V/wRDRQw==";
	public static final CivitasBigInteger BIGINT_P = new CivitasBigInteger(
			Base64.getDecoder().decode(BIGINT_P_BASE64));
	public static final String BIGINT_Q_BASE64 = "ALEbdrNCujCQI0Y4f38yusJkfR1nVqT95/P/H2z28zIH";
	public static final CivitasBigInteger BIGINT_Q = new CivitasBigInteger(
			Base64.getDecoder().decode(BIGINT_Q_BASE64));
	public static final String BIGINT_G_BASE64 = "az5EMeordK3a0VbjoUsKeo/++OHFs5WaRLw6hW8Ezu7D1Egid0U3Obtzpn0GG8UHQjJnHINqwK6bX6RXBlVfsDmiKS2lgvWrL53vSPXtZfFIC/307vWLV2RymA9TQaGZ5d4q7O7l2KHNUx+ecK6QVqKqj46IXjwaYzKQXNg2NfwQGfj7dBTaoGHE401pdoHfyLfluAF3k4ZT6imEoIbe4Ar7ybYdI5eJYn96/hoyLouux8jI1LE+Oe7+eI1M5WK30TPbGKVaon4KPnfLYp8+HTloYb/jlNFLckqdZchKa765pBRjvNu+7MmYpHmqBlLuCLd4sBbD//Sy/aHPnxyccZM0MMwRPSSM0NKO8UuXkCEmRSI6gvdwyIk6FwsB8bF4ksvBAMSfN8gJd7rv1t9B46OByCs8YNuL1eJH1wJThwpUf1iAXPjFCkvrR4fm+lOeIYCGhBkEO1pB2sRzTulRVEadM6bFwo2N5eZb3ymPfERXogTVmljtdzxlAao9vBgk";
	public static final CivitasBigInteger BIGINT_G = new CivitasBigInteger(
			Base64.getDecoder().decode(BIGINT_G_BASE64));
	public static final CivitasBigInteger G_EXP_A = BIGINT_G.modPow(BIGINT_A,
			BIGINT_P);
	public static final String G_EXP_A_BASE64 = Util.fromBigInt(G_EXP_A);
	public static final CivitasBigInteger G_EXP_B = BIGINT_G.modPow(BIGINT_B,
			BIGINT_P);
	public static final String G_EXP_B_BASE64 = Util.fromBigInt(G_EXP_B);
	public static final CivitasBigInteger G_EXP_C = BIGINT_G.modPow(BIGINT_C,
			BIGINT_P);
	public static final String G_EXP_C_BASE64 = Util.fromBigInt(G_EXP_C);
	public static final CivitasBigInteger G_EXP_D = BIGINT_G.modPow(BIGINT_D,
			BIGINT_P);
	public static final String G_EXP_D_BASE64 = Util.fromBigInt(G_EXP_D);

	public static final String P_OTHER_BASE64 = "AIAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAf8tZIaH63gtefkwkLYnCdtF2sMI5MRddL3IgM7SZK+auYCtQitiTHGI0TF8xu5lDM7bV4GvZA6tTBEaKS9EMxD34H+5xFtBtRqWuCI6XMPEMJg71eqhVT3M5yEqwA1wJXXvD/oKs6nlx+MxcEzb9kQweaVE6aOa/9hPMFdU0wL2ZatNOJfN2+CmsweXdC/gnZSX4ySmWAGP4JTHq4abv1SN4JwkUzgsrWownWy2Pzm5fiCeQBc0yn70GTSdIxuDnfX9em+ORiSXhlh7+ddNcgvC35wkHmfjWELTBhUDUh1L8gQWWEQsq2m56u2cv6sdY6FYo2GuxzASJ9vIWQBDS1Et9AJzs3dVN4PjdKWt2peAKGb1Gl5rcBEmM5b3oYhWzSLSu8axcU2dqP0FNvPpkrD98hEAvWyW2sU/xdk4wwGSw==";

	public static final String G_OTHER_BASE64 = "UVvkQGS9ULYLZfgomIk1JPAkhBjF3FJJf2v/BESOIa0GSawzuu8+UJ3AhsmgF+bb8jhqfUq/UTqyBTR2IFKyItkUrb/B1UoK/wvaw0kbKyfjUrCGXnXOHnIE8bu/6Drjj2QzDy69kKJP37PGhd/jaRxdqGiSoEy1TAD1a/mjkjhmJmUSk4FZdtXUW6rwlq40C63dlKW2pWru+B6XRjtURM1RP4w7joKvcV2N4x3OAQs3/skjmUXyRQoU1k23eoNCee4wYadG+fne2+4MTvr/Qb6JIG4TOEmGmWO3ADHdwOHQxy515OTMP+DoffBMHFZKy7OZnC4G7q/+qHzVbVAJc8iwLVy73zDt21g7PgvEUB+ndESkhWUmVTNBtWANLGgDswoyvZXX8wu1JC5lI485dm8ux8VP5k/4j9TYIA9PzDRxs8M6ykF5ej4XAaa80Yuzpbxw7Eex2rZHiys79Hn3jiM5vhOTND8hdb4Qexhqu5jQw4rwj0j094M921WrScq6";
	public static final String P2_BASE64 = "AIbUeE2FqsTt9fCnfKQj4Gfi0E9YJs3T8CQeXlHdvFghFLqEvqG+0i9FSS6KCPuIrvJTQpP0UK7jmz9ho0nV2kuB8CDK8X0B8OzgIzCMwy5LpEEzZsKhy9G1jV3YyehkqfxZdKkupPZ1M5HI+BNZOR8ohWm/6k7aRon9Z2zs6RfJKD3h9QV8pLJdtX+cFs0hMfv4u8O57H2+W/J2QKd06knDmx05NIXUPzNAJ6ocl4ndjzXxUKwgC0H6191uDei3qJukuW+ZDVEZYmfLUPieeUIPo1HTljnKfqEv+mdyMcEfJrOEzzXB7jRTT3QohtFzZXa6/KmcZ9FdWh54+H2//6ryNyE2oNypfTKWvxxicmUJTeKJDR+lpaBiDzSfs9XM6rcEWVkkWD33YjHb+5M2gkMgJEXx3WrYiIEyyKav7I+ezSlllEk3pwgMBmJW+jgiI+I9ByU87PWNGzKys5rUvFBaF9RCz21zzw2LEvXDYvlkk+QaPPIKj7AM4M+7wKeuSw==";
	public static final String Q2_BASE64 = "APDDzsMTbD5LgggjvzFDfoK3x1BphByXkZqg7zBlHyvj";
	public static final String G2_BASE64 = "FYuXPQPEApf/JDV2CPbfQGF11k1P3RZ3PRn7uH0eRQBSyCttyrEeVeDWMSSj8aDe8jSFBz/AmwOoZ8nMeLVvHoA58egzaej80FNFwF2K6+59hikJ5Wvf0VCKrq4ULv2TCdUnQ/7+XastFN8IEBzMwKXWX0l9xw9JVRNd98jAktkUjIFBjf1Vuoie5Niw3zJ8ySgbOzCymeamN1FxBZcWA29G561RicA0KryOG3BGH8rqWvnHIPyL5PCB6KILyXoKZfjqyrb12SMVcEXO+/y5+6HBu630e5SSFhfxu/5+ejHABwnuRwcdy2rSBIwqp0xKwwhllzTeWvSBWsf22MYwYCgFm8WpG8ZqAZeeXcSqmMeRVL8MPFKscxz6htkp8nB/lGysCvMiPnfAe5yUgOtmpRLAxfNiWdLdLSdRJ2q5aDXwp9GwVKSafmelv3qMs4hPERHpgGoQmMme7XDLrd2z4UxOquIvXUtcpYvp5FC11C5RgeXzYbU6bbPljzwx3vqf";
	public static final CivitasBigInteger P2 = Util.asBigint(P2_BASE64);
	public static final CivitasBigInteger Q2 = Util.asBigint(Q2_BASE64);
	public static final CivitasBigInteger G2 = Util.asBigint(G2_BASE64);

	public static final CivitasBigInteger BIGINT_P_OTHER = Util
			.asBigint(P_OTHER_BASE64);
	public static final CivitasBigInteger BIGINT_G_OTHER = Util
			.asBigint(G_OTHER_BASE64);
	public static final String GENERATOR_OTHER_BASE64 = "B8Ewf1Z2BaJYadh/hzQ2kjNGPmYWBIvA6ZxHSpVpB/Sw4m40L8qWkwsNrfUh87L8GIJM0QZ4tHgYCdduHUjiWmcStRTh+2RvNoinIrK5QnxaW3g6Y2G32rxutDlOKwaoMMbrut50O8XN12mD4wR15inhh4+QVyzeobFVSPnpgheb2gt9i5pTKJY6JQkpcZnD4bHF+i7MGelqeoyGmLGqZu98ODsSxn2HfmMniV+p+aaTYJV8niINbYlvvz2BSwf86YdlQOa5WfzzPGSltmt5v5ACs135JCIw3kRzepRzjWGAINHv+SgPcBVSVTwtuBoykYhxzT03CWWo5WwZ+NzdmgDih7RheaQpMlIfSxP/5ZJVukgDxJv4v70F3KRwJ5V5jY26sjETz2Kayn6y38EfDuQjeOAUOamGYcHpdqKdPySJWTK0o6sfKIQ2etMZG0fUFEEt3CdsoGOUi31saXFDK4pkHqu+jQMzCg2Z5EsXwVgZ6J0SD1Nq/DDGvAU2lQdU";
	public static final CivitasBigInteger GENERATOR_OTHER = Util
			.asBigint(GENERATOR_OTHER_BASE64);
	public static final CivitasBigInteger GENERATOR_OTHER_POW_R1 = GENERATOR_OTHER
			.modPow(RANDOMS_1, BIGINT_P);
	public static final String GENERATOR_OTHER__POW_R1_BASE64 = Util
			.fromBigInt(GENERATOR_OTHER_POW_R1);

	public static final CivitasBigInteger GENERATOR_FOR_UNPRIME_P = Util
			.asBigint("APPuKv0SXyLaZklKlEiPMfeSkEfsEfdN24AXL3NrYBKG");
	public static final CivitasBigInteger GENERATOR_FOR_UNPRIME_Q = Util
			.asBigint("SlVGgqFXxlg=");

	public static final ElGamalParametersC EL_GAMAL_PARAMETERS = new ElGamalParametersC(
			BIGINT_P, BIGINT_Q, BIGINT_G);
	public static final ElGamalParametersC EL_GAMAL_PARAMETERS_OTHER = new ElGamalParametersC(
			P2, Q2, G2);

	public static final ElGamalParametersC EL_GAMAL_PARAMETERS_Q_SAME = new ElGamalParametersC(
			BIGINT_P_OTHER, BIGINT_Q, BIGINT_G_OTHER);

	public static final ElGamalParametersC EL_GAMAL_PARAMETERS_GENERATOR_OTHER = new ElGamalParametersC(
			BIGINT_P, BIGINT_Q, GENERATOR_OTHER);

	public static final String EL_GAMAL_PARAMETERS_XML = "<elGamalParameters><p>"
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
			+ EL_GAMAL_PARAMETERS_XML + "</params><x>" + SOMESTRING_BASE64
			+ "</x></elGamalPrivateKey>";
	public static final int SAFE_KEY_LENGTH = EL_GAMAL_GROUP_LENGTH - 1;
	public static final String SAFE_P_BASE64 = "ASvIFyEHZA21reK32LTtYEwJG4GSW8Xzrl83llQSdjcztgsIJeyQm0ZZ36vG4aTVYjtADzV9J6xJ5RJ2U9QDfV8=";
	public static final String SAFE_Q_BASE64 = "AJXkC5CDsgba1vFb7Fp2sCYEjcDJLeL51y+byyoJOxuZ2wWEEvZITaMs79XjcNJqsR2gB5q+k9Yk8ok7KeoBvq8=";
	public static final String SAFE_G_BASE64 = "AKhY5FkyO989WVWT54UIYi29aZjUfx1XD0xlcF13Y7nGa7itniOZIj36m+m8XHW9VN0bzCgLh4btlHBQ8AY3dMo=";
	public static final CivitasBigInteger SAFE_P = new CivitasBigInteger(
			Base64.getDecoder().decode(SAFE_P_BASE64));
	public static final CivitasBigInteger SAFE_Q = new CivitasBigInteger(
			Base64.getDecoder().decode(SAFE_Q_BASE64));
	public static final CivitasBigInteger SAFE_G = new CivitasBigInteger(
			Base64.getDecoder().decode(SAFE_G_BASE64));

	public static final PrimePair SAFE_PRIMES = new PrimePair(SAFE_P,
			SAFE_Q);
	public static final PrimePair SCHNORR_PRIMES = new PrimePair(BIGINT_P,
			BIGINT_Q);

	public static final ElGamalParametersC EL_GAMAL_PARAMETERS_SAFE = new ElGamalParametersC(
			SAFE_P, SAFE_Q, SAFE_G);

	public CivitasBigInteger PLAINTEXT_WITH_LEGENDRE_MINUS_ONE = CivitasBigInteger
			.valueOf(251121);
	public CivitasBigInteger PUBLICIZED_SAFE_LEGENDRE = SAFE_P
			.subtract(PLAINTEXT_WITH_LEGENDRE_MINUS_ONE);
	public static final CivitasBigInteger D_EXP_TWOK_FROMP = BIGINT_D
			.modPow(BIGINT_P.subtract(ONE).divide(BIGINT_Q), BIGINT_P);

}
