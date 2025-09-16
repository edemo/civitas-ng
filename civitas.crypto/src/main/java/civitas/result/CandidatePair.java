package civitas.result;

import lombok.Data;

@Data
public class CandidatePair<C, D> {
	final C car;
	final D cdr;
}
