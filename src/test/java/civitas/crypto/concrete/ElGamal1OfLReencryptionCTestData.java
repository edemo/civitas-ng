package civitas.crypto.concrete;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import civitas.common.CiphertextList;
import civitas.common.TestUtil;
import civitas.common.Util;
import civitas.util.CivitasBigInteger;

public interface ElGamal1OfLReencryptionCTestData
		extends ElGamalCiphertextCTestData, ElGamalPublicKeyCTestData {
	public static final String EL_GAMAL_CIPHERTEXT_1_OF_L_B_BASE64 = "Euy7AYHNjcRYbqpqXxSE3g/VwpApulVwXYa5KG9VWJ9X+zJoLOMCOlzl0rgth2Qj688OtOZ9DMQoMcsLWQ6FZQdQeO1JvytUNjFLYYREJU1rycTrdsCaT/+xi8ObmOqjxk7yKbxzVLs3UbKyeeYDIkXIBebD8fRrmHCd8t4/SKc4lKQTLd51+r/HN3BMo0koy5cNejXUSyIN+naKsJz4nvWaO3KEkeiwarLS9P4nRvdVLsyuUo5XvNucYzcG9N+UEVhJZUTApb3GIeAWx1XQKlWdpvxyJnstc5yl/ZJ0wjgFhfJuWzV3U7LAnnflZO8nWslE6W7CUQVk6RAvQhexsjMKjHkXqA0919LxgChbozhpd+Moa592LKDUHy3+GeYS1gLZRQrpCP2uHeTZUZisP8gVbNkX3aCFFeQsVscDOaT37s+NXUWQpZ7DUs4ZXVtmnjeyEtob+bL9xAYP0tVP4gmB1ip/e8yv1lcTe0xaADwhilAI11fVT+20fw1IGjeE";
	public static final CivitasBigInteger EL_GAMAL_CIPHERTEXT_1_OF_L_B = Util
			.asBigint(EL_GAMAL_CIPHERTEXT_1_OF_L_B_BASE64);
	public static final ElGamalCiphertextC EL_GAMAL_CIPHERTEXT_1_OF_L = new ElGamalCiphertextC(
			G_EXP_D, EL_GAMAL_CIPHERTEXT_1_OF_L_B);

	public static final String EL_GAMAL_CIPHERTEXT_1_OF_L_XML = EL_GAMAL_CIPHERTEXT_1_OF_L
			.toXML();
	public static final String EL_GAMAL_PROOF_1_OF_L_XML_DV_BASE64 = "MJ1Xq+nl8r14FGUvYnGhrluyp+T7m6OqH+V2wM4MTdA=";
	public static final CivitasBigInteger EL_GAMAL_PROOF_1_OF_L_XML_DV = Util
			.asBigint(EL_GAMAL_PROOF_1_OF_L_XML_DV_BASE64);
	public static final String EL_GAMAL_PROOF_1_OF_L_XML_RV_BASE64 = "AOgJzYG6kBgACBUA0fAAJLdBBIJUn3cK4n76GZP8hXs=";
	public static final CivitasBigInteger EL_GAMAL_PROOF_1_OF_L_XML_RV = Util
			.asBigint(EL_GAMAL_PROOF_1_OF_L_XML_RV_BASE64);
	public static final String EL_GAMAL_PROOF_1_OF_L_XML = "<elGamalProof1OfL>"
			+ "<size>4</size>" + "<dv>" + RANDOMS_BASE64.get(0) + "</dv><dv>"
			+ RANDOMS_BASE64.get(2) + "</dv><dv>"
			+ EL_GAMAL_PROOF_1_OF_L_XML_DV_BASE64 + "</dv><dv>"
			+ RANDOMS_BASE64.get(6) + "</dv><rv>" + RANDOMS_BASE64.get(1)
			+ "</rv><rv>" + RANDOMS_BASE64.get(3) + "</rv><rv>"
			+ EL_GAMAL_PROOF_1_OF_L_XML_RV_BASE64 + "</rv><rv>"
			+ RANDOMS_BASE64.get(7) + "</rv>" + "</elGamalProof1OfL>";

	public static final String EL_GAMAL_PROOF_1_OF_L_BAD_XML = "<elGamalProof1OfL><size>10</size><dv>"
			+ "R2yN/mgDmwcpUg+ZVUEstEySVXankjgvA95fa5wHGXQ="
			+ "</dv><dv>R2yN/mgDmwcpUg+ZVUEstEySVXankjgvA95fa5wHGXQ=</dv><dv>AICmwameAGKMoXXKbfljqrBm+JOmPzS2klx8m1EZk9uz</dv><dv>QIj/R6x31/E8wqRzm9XbPb9DiYt2mScs4FhE+2wz4go=</dv><dv>ft13WHaYq8xNHO/24hSt4nlTjH7CKN5hhD5shAoDHm8=</dv><dv>DD5VVJY1BonuOc8nJe5xfYy3wf9kQya9U+sJaDbAQ3k=</dv><dv>AJXL5ONmIuoyvK94CcuoRB2bAdiq8Ok3Gr++iTDqsWeX</dv><dv>axVSQ9fOAIElaqV6wJS1pFT2wu9STftaKfcZkAc2tkM=</dv><dv>Lk+xgoUCqFz/JbQzwDXgbLh1v/HxaijFEZIO1U8q1as=</dv><dv>faC93a1h3ET+0Hzb+tvwUHHQZx9QurDrjq9lYXr5Nec=</dv><rv>LH3xOock3kMSCTC0JfaD/qWoikvKsCwYU4CZmDfyWsg=</rv><rv>AIRo36+ex/i5zY2YDDuGtxDmPoGIH/7M1XhL625qqBFz</rv><rv>CibppMYGZsz37KkI95Z2IStd6/wc3w1QxKyIiKz6dNc=</rv><rv>fgOK4diBwxwnJg1WGwQr5REV1psJa7Zm3PoQZWMImsQ=</rv><rv>ALAooPo4bF254BxADYvC6uOQyhPJOEJAKCk43DwzOdwX</rv><rv>Jzbj2o69WCs46w7Yqli6mBcbvN8t3MNhecxtjN1nmxc=</rv><rv>GZzjyxNJ+2MKQP7SoMOloMF1h7Wyv5//5wCTE3s5Ytk=</rv><rv>RHEFyxQhAgAcSsKqe1WkyzGvAzeVgaiYWs9JcPiSLfA=</rv><rv>bOSCz15zVkOFXQ+fH1sSTgsy305V8yvypIndB2vaJBw=</rv><rv>Ijx1dyYqyhR+v0QiPqTXl49sM5k0wp5DHmMsujVP6x4=</rv></elGamalProof1OfL>";

	public static final String EL_GAMAL_1_OF_L_REENCRYPTION_XML = "<elGamal1OfLReencryption>"
			+ EL_GAMAL_CIPHERTEXT_1_OF_L_XML + EL_GAMAL_PROOF_1_OF_L_XML
			+ "</elGamal1OfLReencryption>";
	public static final String EL_GAMAL_1_OF_L_REENCRYPTION_NULL_XML = "<elGamal1OfLReencryption></elGamal1OfLReencryption>";

	public static final String CIPHERTEXT_LIST_AS_XML = "<ciphertextList>"
			+ "<size>1</size>" + EL_GAMAL_CIPHERTEXT_NAIVE_XML + "</ciphertextList>";
	public static final String EMPTY_CIPHERTEXT_LIST_AS_XML = "<ciphertextList>"
			+ "<size>0</size></ciphertextList>";
	public static final String CIPHERTEXT_LIST_XML_WITH_NEGATIVE_LENGTH = "<ciphertextList>"
			+ "<size>-1</size></ciphertextList>";

	public static final int NO_OF_WELL_KNOWN_CIPHERTEXTS = 4;
	public static final int MY_CHOICE = 2;

	public static final CryptoFactoryC factory = CryptoFactoryC.singleton();

	public static final CiphertextList CIPHERTEXT_LIST = new CiphertextList(
			factory.constructWellKnownCiphertexts(EL_GAMAL_PUBLIC_KEY,
					NO_OF_WELL_KNOWN_CIPHERTEXTS));

	public static final List<CivitasBigInteger> DVS = ((Supplier<List<CivitasBigInteger>>) () -> {
		List<CivitasBigInteger> d = RANDOMS.stream().map(TestUtil.pick(0))
				.filter((s) -> (s != null)).collect(Collectors.toList());
		d.set(MY_CHOICE, EL_GAMAL_PROOF_1_OF_L_XML_DV);
		return d;
	}).get();

	public static final List<CivitasBigInteger> RVS = ((Supplier<List<CivitasBigInteger>>) () -> {
		List<CivitasBigInteger> r = RANDOMS.stream().map(TestUtil.pick(1))
				.filter((s) -> (s != null)).collect(Collectors.toList());
		r.set(MY_CHOICE, EL_GAMAL_PROOF_1_OF_L_XML_RV);
		return r;
	}).get();

	public static final ElGamalProof1OfLC EL_GAMAL_PROOF_1_OF_L = new ElGamalProof1OfLC(
			NO_OF_WELL_KNOWN_CIPHERTEXTS,
			DVS.toArray(new CivitasBigInteger[NO_OF_WELL_KNOWN_CIPHERTEXTS]),
			RVS.toArray(new CivitasBigInteger[NO_OF_WELL_KNOWN_CIPHERTEXTS]));

	public static final ElGamal1OfLReencryptionC EL_GAMAL_1_OF_L_REENCRYPTION = new ElGamal1OfLReencryptionC(
			EL_GAMAL_CIPHERTEXT_1_OF_L, EL_GAMAL_PROOF_1_OF_L);

}
