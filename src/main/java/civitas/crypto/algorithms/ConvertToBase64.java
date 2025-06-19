package civitas.crypto.algorithms;

import java.util.Base64;

import org.springframework.stereotype.Service;

import civitas.util.CivitasBigInteger;

@Service
public class ConvertToBase64 {
	public String apply(byte[] a) {
		return Base64.getEncoder().encodeToString(a);
	}

	public String apply(CivitasBigInteger i) {
		return Base64.getEncoder().encodeToString(i.toByteArray());
	}

}
