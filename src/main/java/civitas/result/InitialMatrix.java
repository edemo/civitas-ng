package civitas.result;

public class InitialMatrix {

	Pair<Integer, Integer>[][] apply(Integer[][] m, Integer n) {
		@SuppressWarnings("unchecked")
		Pair<Integer, Integer>[][] r = new Pair[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				Integer x = m[i][j];
				Integer y = m[j][i];
				if (x == y)
					r[i][j] = new Pair<>(0, 0);
				else if (x > y) {
					r[i][j] = new Pair<>(x, y);
					r[j][i] = new Pair<>(0, 0);
				} else {
					r[i][j] = new Pair<>(0, 0);
					r[j][i] = new Pair<>(y, x);
				}
			}
		return r;
	}

}
