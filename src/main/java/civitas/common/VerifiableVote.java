/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common;

import java.io.IOException;
import java.io.Reader;

import civitas.crypto.CryptoUtil;
import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.ciphertextlist.CiphertextList;
import civitas.crypto.oneoflreencryption.ElGamal1OfLReencryption;
import civitas.crypto.proof1ofl.VerifyElGamal1OfLReencryption;
import civitas.crypto.proofvote.ProofVote;
import civitas.crypto.proofvote.ProofVote;
import civitas.crypto.proofvote.VerifyProofVote;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.util.Use;

/**
 * Similar to a <code>Vote</code>, but the capability is encrypted and signed,
 * and there is also a proof that the choice is in fact a valid choice. This is
 * part of what a voter submits; the tabulation process will strip off the
 * proof, and change the capability to be malleable (for the re-encryption
 * mixes).
 */
public class VerifiableVote {
	public static final String OPENING_TAG = "verifiableVote";

	@Use
	VerifyElGamal1OfLReencryption verifyElGamal1OfLReencryption;
	@Use
	VerifyProofVote verifyProofVote;
	/**
	 * Indicates the election and block that this vote belongs to.
	 */
	public final String context;

	/**
	 * Encrypted choice
	 */
	public final ElGamal1OfLReencryption encChoice;

	/**
	 * Proof of knowledge of the voter's vote, including its capability, choice,
	 * and
	 * context.
	 */
	public final ProofVote proofVote;

	/**
	 * Encrypted capability
	 */
	public final ElGamalCiphertext encCapability;

	public VerifiableVote(String context, ElGamal1OfLReencryption encChoice,
			ElGamalCiphertext encCapability, ProofVote proofVote) {
		this.context = context;
		this.encChoice = encChoice;
		this.encCapability = encCapability;
		this.proofVote = proofVote;
	}

	public boolean equals(VerifiableVote vv) {
		if (this == vv)
			return true;
		if (vv == null)
			return false;
		return (this.context == vv.context
				|| (this.context != null && this.context.equals(vv.context)))
				&& (this.encChoice == vv.encChoice
						|| (this.encChoice != null && this.encChoice.equals(vv.encChoice)))
				&& (this.encCapability == vv.encCapability
						|| (this.encCapability != null
								&& this.encCapability.equals(vv.encCapability)))
				&& (this.proofVote == vv.proofVote
						|| (this.proofVote != null && this.proofVote.equals(vv.proofVote)));
	}

	public boolean verify(ElGamalPublicKey pubKey, CiphertextList ciphertexts,
			int L) {
		if (proofVote == null || encChoice == null || (pubKey == null))
			return false;
		return verifyElGamal1OfLReencryption.apply(encChoice, pubKey, ciphertexts,
				L)
				&& verifyProofVote.apply((ProofVote) proofVote, pubKey.params,
						encCapability, encChoice.m, context);
	}

	public static VerifiableVote fromXML(Reader r)
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