/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common.ballotdesign;

import java.util.Map;

import civitas.common.CommonConstants;
import civitas.util.CivitasBigInteger;
import lombok.Data;

@Data
public class BallotDesign implements CommonConstants {

	public final String[] candidates;
	Map<CivitasBigInteger, Integer> decodeMap;

}