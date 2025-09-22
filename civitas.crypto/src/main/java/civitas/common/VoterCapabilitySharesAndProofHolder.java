package civitas.common;

import java.security.PublicKey;

import civitas.common.votercapabilitysharesandproofs.VoterCapabilitySharesAndProof;

public record VoterCapabilitySharesAndProofHolder(VoterCapabilitySharesAndProof sharesAndProof, PublicKey voter) {}
