package civitas.util;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class ObtainProbablePrime {

	public CivitasBigInteger apply(int bitLenght, int certainty, Random random) {
		return new CivitasBigInteger(bitLenght, certainty, random);
	}

}
