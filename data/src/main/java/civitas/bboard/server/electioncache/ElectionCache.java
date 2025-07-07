package civitas.bboard.server.electioncache;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.data.annotation.Id;

import civitas.common.ServerHost;
import civitas.common.election.ElectionDetails;
import civitas.common.election.ElectionStatus;
import civitas.common.tabteller.keysharecommitment.TabTellerKeyShareCommitment;
import civitas.crypto.ciphertext.ElGamalCiphertextish;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
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
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
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
	List<ServerHost> hosts;
	Long electionStartTime;
	Long electionStopTime;
	Long electionFinalizeTime;
	List<ElGamalCiphertextish> ciphertextList;
	// @ElementCollection
	// ElectionEvent[] electionEvents;
	Integer electoralRollEstimate;
	Map<Integer, ElGamalKeyShareStored> tabTellerKeyShares;
	Map<Integer, TabTellerKeyShareCommitment> tabTellerKeyShareCommitments;
	BigInteger tabTellerSharedKeyY;
	Map<Integer, TabTellerKeyShareCommitment> contentComs;
	final Map<String, String> voterBlocks = new ConcurrentHashMap<>();
}
