package civitas.common.tallystate;

import org.springframework.stereotype.Service;

@Service
public class RecordBeat {
	public void apply(TallyState that, int i, int j)
			throws IndexOutOfBoundsException {
		if (i < 0 || i >= that.size || j < 0 || j >= that.size || i == j)
			throw new IllegalArgumentException("first candidate is illegal");

		that.matrix[i][j]++;
	}

}
