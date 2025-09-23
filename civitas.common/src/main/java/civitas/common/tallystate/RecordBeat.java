package civitas.common.tallystate;

import org.springframework.stereotype.Controller;

@Controller
public class RecordBeat {
	public void apply(final TallyState that, final int i, final int j) {
		if (i < 0 || i >= that.size || j < 0 || j >= that.size || i == j) {
			throw new IllegalArgumentException("first candidate is illegal");
		}

		that.matrix[i][j]++;
	}
}
