/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common;

/**
 * Cache information useful for Plaintext Equivalence Tests (PETs).
 */
public interface PETCache extends DistDecryptCache {
	boolean hasCommitment(String meta, int block, int ballotIndex, int tellerIndex);

	TabTellerPETShareCommitments getCommitment(String meta, int block, int ballotIndex, int tellerIndex);

	void setCommitment(String meta, int block, int ballotIndex, int tellerIndex, TabTellerPETShareCommitments c);

	boolean hasDecommitment(String meta, int block, int ballotIndex, int tellerIndex);

	TabTellerPETShareDecommitments getDecommitment(String meta, int block, int ballotIndex, int tellerIndex);

	void setDecommitment(String meta, int block, int ballotIndex, int tellerIndex, TabTellerPETShareDecommitments c);
}