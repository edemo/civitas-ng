/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common.tabteller.distributeddecryptions;

import civitas.crypto.decriptionshare.ElGamalDecryptionShare;
import civitas.crypto.proofdisclog.ElGamalProofDiscLogEquality;
import lombok.NonNull;
import lombok.Value;

@Value
public class TabTellerDistributedDecryptions {
	public int tellerIndex;

	@NonNull public ElGamalDecryptionShare[] decrypts;

	@NonNull public ElGamalProofDiscLogEquality[] proofs;
}
