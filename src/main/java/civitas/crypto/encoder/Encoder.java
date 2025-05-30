package civitas.crypto.encoder;

import civitas.crypto.CryptoException;
import civitas.util.CivitasBigInteger;

public interface Encoder {
	CivitasBigInteger encodePlaintext(CivitasBigInteger p) throws CryptoException;

	CivitasBigInteger decodeMessage(CivitasBigInteger m) throws CryptoException;
}