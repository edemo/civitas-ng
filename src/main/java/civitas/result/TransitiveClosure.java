package civitas.result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransitiveClosure {

	@Autowired
	Min min;
	@Autowired
	Max max;

	void apply(Pair<Integer, Integer>[][] m, Integer n) {
		for (int k = 0; k < n; k++)
			for (int i = 0; i < n; i++)
				for (int j = 0; j < n; j++)
					m[i][j] = max.apply(m[i][j], min.apply(m[i][k], m[k][j]));
	}

}
