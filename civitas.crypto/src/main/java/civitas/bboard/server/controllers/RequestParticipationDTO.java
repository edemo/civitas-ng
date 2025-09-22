package civitas.bboard.server.controllers;

import java.math.BigInteger;
import java.util.List;

import civitas.common.ServerHost;
import civitas.common.ballotdesign.BallotDesign;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestParticipationDTO {
	@NonNull
	public String electionID;
	@NonNull
	public String supervisorPubkey;
	@NonNull
	public String registrarPubKey;
	@NonNull
	public String name;
	@NonNull
	public String description;
	@NonNull
	public String version;
	@NonNull
	public BallotDesign ballotDesign;
	@NonNull
	public String startTime;
	@NonNull
	public String stopTime;
	@NonNull
	public String finalizeTime;
	@NonNull
	public BigInteger elGamalP;
	@NonNull
	public BigInteger elGamalQ;
	@NonNull
	public BigInteger elGamalG;
	@NonNull
	public Integer sharedKeyLength;
	@NonNull
	public Integer nonceLength;
	@NonNull
	public Integer voterAnonymityParam;
	@NonNull
	List<ServerHost> tellerDetails;

}