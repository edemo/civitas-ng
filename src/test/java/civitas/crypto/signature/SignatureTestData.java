package civitas.crypto.signature;

import civitas.crypto.BasicValuesTestData;

public interface SignatureTestData extends BasicValuesTestData {
	String SIGNATURE_XML = "<signature>dGVzdGRhdGE=</signature>";
	Signature SIGNATURE = new Signature(BYTES);

}
