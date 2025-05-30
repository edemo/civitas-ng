package civitas.crypto.algorithms;

import java.util.Random;

import civitas.crypto.Constants;

public class GetRandomGenerator implements Constants {
	public Random apply() {
		return RANDOM;
	}
}
