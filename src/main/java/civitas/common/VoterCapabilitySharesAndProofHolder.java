package civitas.common;

import java.security.PublicKey;

import civitas.common.votercapabilitysharesandproofs.VoterCapabilitySharesAndProof;
import lombok.Data;

@Data
public class VoterCapabilitySharesAndProofHolder {

	public final VoterCapabilitySharesAndProof sharesAndProof;
	public final PublicKey voter;

}