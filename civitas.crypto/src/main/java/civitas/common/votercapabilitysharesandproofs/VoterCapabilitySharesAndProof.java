/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common.votercapabilitysharesandproofs;

import civitas.crypto.proofdvr.ElGamalProofDVR;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactor;
import civitas.crypto.votecapabilityshare.VoteCapabilityShare;
import lombok.NonNull;
import lombok.Value;

@Value
public class VoterCapabilitySharesAndProof {
	@NonNull public VoteCapabilityShare[] capabilities;

	@NonNull public ElGamalReencryptFactor[] rencryptFactors;

	@NonNull public ElGamalProofDVR[] proofs;

	public int voterBlock;
}
