package civitas.crypto.parameters;

import org.springframework.stereotype.Controller;

@Controller
public class CalculateNumberOfPrimeTests {
	int apply(final int pLength) {
		int k = (int) Math.ceil(Math.log(pLength) / Math.log(2));
		return (int) Math.pow(2, k + 2);
	}
}
