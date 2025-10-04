package civitas.bboard.server.controllers;

import java.math.BigInteger;
import java.util.List;

import civitas.common.ServerHost;
import civitas.common.ballotdesign.BallotDesign;
import lombok.Builder;
import lombok.NonNull;

@Builder
public record RequestParticipationDTO(
		@NonNull String electionID,
		@NonNull String supervisorPubkey,
		@NonNull String registrarPubKey,
		@NonNull String name,
		@NonNull String description,
		@NonNull String version,
		@NonNull BallotDesign ballotDesign,
		@NonNull String startTime,
		@NonNull String stopTime,
		@NonNull String finalizeTime,
		@NonNull BigInteger elGamalP,
		@NonNull BigInteger elGamalQ,
		@NonNull BigInteger elGamalG,
		@NonNull Integer sharedKeyLength,
		@NonNull Integer nonceLength,
		@NonNull Integer voterAnonymityParam,
		@NonNull List<ServerHost> tellerDetails) {}
