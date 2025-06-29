package civitas.crypto.algorithms;

import java.util.Random;

import org.springframework.stereotype.Controller;

import civitas.crypto.Constants;

@Controller
public class GetRandomGenerator implements Constants {
	public Random apply() {
		return RANDOM;
	}
}
