/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common.tabteller.distributeddecryptions;

import civitas.crypto.decriptionshare.ElGamalDecryptionShare;
import civitas.crypto.proofdisclog.ElGamalProofDiscLogEquality;
import lombok.Data;
import lombok.NonNull;

@Data
public class TabTellerDistributedDecryptions {
	public final int tellerIndex;
	@NonNull
	public final ElGamalDecryptionShare[] decrypts;
	@NonNull
	public final ElGamalProofDiscLogEquality[] proofs;

}
