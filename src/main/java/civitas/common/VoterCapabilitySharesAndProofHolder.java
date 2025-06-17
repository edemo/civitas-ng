package civitas.common;

import civitas.common.votercapabilitysharesandproofs.VoterCapabilitySharesAndProof;
import civitas.crypto.rsapublickey.PublicKey;
import lombok.Data;

@Data
public class VoterCapabilitySharesAndProofHolder {

	public final VoterCapabilitySharesAndProof sharesAndProof;
	public final PublicKey voter;

}