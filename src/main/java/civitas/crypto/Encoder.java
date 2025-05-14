package civitas.crypto;

import civitas.util.CivitasBigInteger;

public interface Encoder {
	CivitasBigInteger encodePlaintext(CivitasBigInteger p) throws CryptoException;

	CivitasBigInteger decodeMessage(CivitasBigInteger m) throws CryptoException;
}