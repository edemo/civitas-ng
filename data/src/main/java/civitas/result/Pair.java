package civitas.result;

import lombok.Data;

@Data
public class Pair<C, D> {
	final C car;
	final D cdr;
}
