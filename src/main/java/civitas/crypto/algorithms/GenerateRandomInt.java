package civitas.crypto.algorithms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenerateRandomInt {

	@Autowired
	GetRandomGenerator getRandomGenerator;

	public int apply(int n) {
		if (n <= 0)
			return 0;
		return getRandomGenerator.apply().nextInt(n);
	}

}
