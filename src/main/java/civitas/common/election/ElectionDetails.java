/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common.election;

import java.math.BigInteger;

import civitas.common.ballotdesign.BallotDesign;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NonNull;

@Data
@Embeddable
public class ElectionDetails {
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

}