package civitas.bboard.server.electioncache;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import civitas.common.Host;
import civitas.common.election.ElectionDetails;
import civitas.common.election.ElectionStatus;
import civitas.common.tabteller.keysharecommitment.TabTellerKeyShareCommitment;
import civitas.crypto.ciphertextlist.CiphertextList;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NonNull;

@Data
@Embeddable
class ElGamalKeyShareStored {

	@NonNull
	public final BigInteger pubKeyY;
	@NonNull
	public final BigInteger proofA;
	@NonNull
	public final BigInteger proofC;
	@NonNull
	public final BigInteger proofR;
	@NonNull
	public final BigInteger proofV;
}

@Data
@Entity
public class ElectionCache {
	@Id
	@NonNull
	String boardID;
	@NonNull
	Integer myIndex;
	@NonNull
	ElectionStatus status;
	@NonNull
	ElectionDetails electionDetails;
	@ElementCollection
	List<Host> hosts;
	Long electionStartTime;
	Long electionStopTime;
	Long electionFinalizeTime;
	CiphertextList ciphertextList;
	// @ElementCollection
	// ElectionEvent[] electionEvents;
	Integer electoralRollEstimate;
	@ElementCollection
	Map<Integer, ElGamalKeyShareStored> tabTellerKeyShares;
	@ElementCollection
	Map<Integer, TabTellerKeyShareCommitment> tabTellerKeyShareCommitments;
	BigInteger tabTellerSharedKeyY;
	@ElementCollection
	Map<Integer, TabTellerKeyShareCommitment> contentComs;
	@ElementCollection
	final Map<String, String> voterBlocks;
}
