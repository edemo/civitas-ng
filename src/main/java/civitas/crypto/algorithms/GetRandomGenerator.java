package civitas.crypto.algorithms;

import java.util.Random;

import org.springframework.stereotype.Service;

import civitas.crypto.Constants;

@Service
public class GetRandomGenerator implements Constants {
	public Random apply() {
		return RANDOM;
	}
}
