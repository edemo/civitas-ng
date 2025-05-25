package civitas.crypto.algorithms;

import java.util.Random;

public class GetRandomGenerator implements Constants {
	public Random apply() {
		return RANDOM;
	}
}
