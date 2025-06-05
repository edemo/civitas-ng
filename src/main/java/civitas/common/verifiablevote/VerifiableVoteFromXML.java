package civitas.common.verifiablevote;

import java.io.IOException;
import java.io.Reader;

import civitas.common.Util;
import civitas.crypto.CryptoUtil;
import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.oneoflreencryption.ElGamal1OfLReencryption;
import civitas.crypto.proofvote.ProofVote;

public class VerifiableVoteFromXML {
	public VerifiableVote apply(Reader r)
			throws IllegalArgumentException, IOException {
		Util.swallowTag(r, "verifiableVote");
		String context = Util.unescapeString(Util.readSimpleTag(r, "context"));
		Util.swallowTag(r, "encChoice");
		ElGamal1OfLReencryption encChoice = null;
		try {
			encChoice = CryptoUtil.factory().elGamal1OfLReencryptionFromXML(r);
		} catch (NullPointerException imposs) {
		}

		Util.swallowEndTag(r, "encChoice");
		Util.swallowTag(r, "encCapability");
		ElGamalCiphertext encCapability = null;
		try {
			encCapability = CryptoUtil.factory().elGamalCiphertextFromXML(r);
		} catch (NullPointerException imposs) {
		}

		Util.swallowEndTag(r, "encCapability");
		Util.swallowTag(r, "proof");
		ProofVote proofVote = null;
		try {
			proofVote = CryptoUtil.factory().proofVoteFromXML(r);
		} catch (NullPointerException imposs) {
		}
		Util.swallowEndTag(r, "proof");

		Util.swallowEndTag(r, "verifiableVote");
		return new VerifiableVote(context, encChoice, encCapability, proofVote);
	}
}
