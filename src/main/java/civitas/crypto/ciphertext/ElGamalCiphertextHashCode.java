package civitas.crypto.ciphertext;

public class ElGamalCiphertextHashCode {

	public int hashCode(ElGamalCiphertext that) {
		return that.a.hashCode() ^ that.b.hashCode();
	}

}
