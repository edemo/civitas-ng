package civitas.result;

public class InitialMatrix {
	/*
	 * # initial_matrix($m,$n): Return a matrix which is the
	 * # initial starting point for the Floyd-Warshall algorithm.
	 * # Input m is a reference to an n-by-n matrix. For any given
	 * # pair of elements ij and ji in m, at most one is
	 * # initialized to something other than (0,0): the one that
	 * # contains a larger value in m. That element is initialized
	 * # to a reference to a pair containing the larger and the
	 * # smaller of the two values. Thus, diagonal elements are
	 * # initialized to (0,0); if m_ij=m_ji, both are initialized
	 * # to (0,0).
	 * #
	 */

	Pair<Integer, Integer>[][] apply(Integer[][] m, Integer n) {
		@SuppressWarnings("unchecked")
		Pair<Integer, Integer>[][] r = new Pair[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				Integer x = m[i][j];
				Integer y = m[j][i];
				if (x == y)
					r[i][j] = new Pair<Integer, Integer>(0, 0);
				else if (x > y) {
					r[i][j] = new Pair<Integer, Integer>(x, y);
					r[j][i] = new Pair<Integer, Integer>(0, 0);
				} else {
					r[i][j] = new Pair<Integer, Integer>(0, 0);
					r[j][i] = new Pair<Integer, Integer>(y, x);
				}
			}
		return r;
	}

}
