/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common.election;

import civitas.common.ElectionID;
import civitas.common.ballotdesign.BallotDesign;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.rsapublickey.PublicKey;
import lombok.Data;

@Data
public class ElectionDetails {
	public final ElectionID electionID;
	public final PublicKey supervisor;
	public final PublicKey registrar;
	public final String name;
	public final String description;
	public final String version;
	public final BallotDesign ballotDesign;
	public final String startTime;
	public final String stopTime;
	public final String finalizeTime;
	public final ElGamalParameters elGamalParameters;
	public final int sharedKeyLength;
	public final int nonceLength;
	public final int voterAnonymityParam;

}