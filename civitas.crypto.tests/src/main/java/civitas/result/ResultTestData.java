package civitas.result;

public interface ResultTestData {
	Integer[][] matrix = {
			{ 0, 87, 140, 92, 177, 71, 98, 52, 16, 63, 92, 220 },
			{ 53, 0, 124, 74, 171, 39, 90, 44, 15, 48, 77, 213 },
			{ 35, 41, 0, 38, 123, 26, 64, 30, 12, 30, 44, 218 },
			{ 65, 76, 132, 0, 181, 55, 86, 50, 17, 52, 76, 224 },
			{ 17, 20, 48, 7, 0, 13, 38, 11, 6, 10, 21, 207 },
			{ 71, 92, 145, 103, 191, 0, 98, 55, 18, 68, 98, 220 },
			{ 68, 80, 120, 84, 153, 65, 0, 41, 15, 67, 81, 213 },
			{ 77, 107, 148, 110, 195, 67, 97, 0, 22, 79, 102, 224 },
			{ 93, 125, 172, 127, 209, 93, 113, 84, 0, 103, 124, 228 },
			{ 62, 81, 145, 92, 189, 57, 100, 56, 14, 0, 94, 218 },
			{ 60, 74, 128, 66, 175, 46, 87, 41, 15, 40, 0, 226 },
			{ 1, 0, 0, 1, 7, 0, 7, 1, 0, 0, 0, 0 } };

	String[] candidates = {
			"4-es főút szélesítés",
			"elkerülő út felújítás",
			"bicikliút Szoboszlóra",
			"fürdőfejlesztés",
			"szállodaépítés",
			"vízmegtartó gazdálkodás",
			"cukorgyár újraindítás",
			"élelmiszeripari üzem",
			"orvosi ügyelet",
			"önkormányzati iskola",
			"piacfejlesztés",
			"---" };

	String CLOUDWORDS_CSV = """
			16421,orvosi ügyelet,#ffffff
			4300,élelmiszeripari üzem,#ffffff
			2931,4-es főút szélesítés,#ffffff
			3442,vízmegtartó gazdálkodás,#ffffff
			2885,önkormányzati iskola,#ffffff
			1630,fürdőfejlesztés,#ffffff
			1587,elkerülő út felújítás,#ffffff
			526,piacfejlesztés,#ffffff
			1420,cukorgyár újraindítás,#ffffff
			757,bicikliút Szoboszlóra,#ffffff
			295,szállodaépítés,#ffffff
			0,---,#ffffff
			""";

	String REPORT = """
            1:
                orvosi ügyelet (16421.25) (beats élelmiszeripari üzem by 84:22 (3.82))
            2:
                élelmiszeripari üzem (4300.80) (beats 4-es főút szélesítés by 77:52 (1.48), beats vízmegtartó gazdálkodás by 67:55 (1.22))
            3:
                4-es főút szélesítés (2931.88) (beats önkormányzati iskola by 63:62 (1.02))
                vízmegtartó gazdálkodás (3442.16) (beats önkormányzati iskola by 68:57 (1.19))
            4:
                önkormányzati iskola (2885.34) (beats fürdőfejlesztés by 92:52 (1.77))
            5:
                fürdőfejlesztés (1630.84) (beats elkerülő út felújítás by 76:74 (1.03))
            6:
                elkerülő út felújítás (1587.93) (beats piacfejlesztés by 77:74 (1.04))
            7:
                piacfejlesztés (1526.06) (beats cukorgyár újraindítás by 87:81 (1.07))
            8:
                cukorgyár újraindítás (1420.81) (beats bicikliút Szoboszlóra by 120:64 (1.88))
            9:
                bicikliút Szoboszlóra (757.77) (beats szállodaépítés by 123:48 (2.56))
            10:
                szállodaépítés (295.71) (beats --- by 207:7 (29.57))
            11:
                --- (0.00) ()
            """;

}
