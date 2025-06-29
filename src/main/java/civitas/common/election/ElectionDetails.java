/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common.election;

import java.math.BigInteger;

import civitas.common.ballotdesign.BallotDesign;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@XmlRootElement(name = "electiondetails")
public class ElectionDetails {
	@NonNull
	String electionID;
	@NonNull
	String supervisorPubkey;
	@NonNull
	String registrarPubKey;
	@NonNull
	String name;
	@NonNull
	String description;
	@NonNull
	String version;
	@NonNull
	BallotDesign ballotDesign;
	@NonNull
	String startTime;
	@NonNull
	String stopTime;
	@NonNull
	String finalizeTime;
	@NonNull
	BigInteger elGamalP;
	@NonNull
	BigInteger elGamalQ;
	@NonNull
	BigInteger elGamalG;
	@NonNull
	Integer sharedKeyLength;
	@NonNull
	Integer nonceLength;
	@NonNull
	Integer voterAnonymityParam;
}