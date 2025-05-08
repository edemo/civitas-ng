/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common;

import java.io.PrintWriter;

/**
 * Superclass for vote mixes and capability mixes. This is the information that
 * is posted to the bulletin board, and therefore all information on it is
 * publically readable. The details of the mix (e.g., the permutation used) are
 * kept in a MixInfo object.
 */
public abstract class Mix implements XMLSerializable {

	/**
	 * Which mix is this? Each teller performs a single mix. The first mix is number
	 * 1.
	 */
	public final int number;

	/**
	 * A commitment to a nonce for the mix. May be null if not needed.
	 */
	public final byte[] mixNonceHash;

	/**
	 * One commitment for each element of the mix. Each commitment is a byte array.
	 */
	public byte[][] commitments;

	public Mix(int number, byte[] mixNonceHash) {
		this.number = number;
		this.mixNonceHash = mixNonceHash;
		this.commitments = new byte[0][];
	}

	public abstract int size();

	public abstract void add(Object o, byte[] commitment) throws ClassCastException;

	public abstract Object get(int i) throws IndexOutOfBoundsException;

	protected void addCommitment(byte[] commitment) {
		try {
			byte[][] n = new byte[commitments.length + 1][];
			for (int i = 0; i < commitments.length; i++) {
				try {
					n[i] = commitments[i];
				} catch (NullPointerException ignore) {
				} catch (ArrayIndexOutOfBoundsException ignore) {
				}
			}
			n[commitments.length] = commitment.clone();
			this.commitments = n;
		} catch (NullPointerException imposs) {
		} catch (ArrayIndexOutOfBoundsException imposs) {
		}
	}

	public void toXML(PrintWriter[] sb) {
		toXML(sb);
	}

}