package civitas.crypto.parameters;

public class CalculateNumberOfPrimeTests {
	int apply(int pLength) {
		int k = (int) Math.ceil(Math.log(pLength) / Math.log(2));
		return (int) Math.pow(2, k + 2);
	}

}
