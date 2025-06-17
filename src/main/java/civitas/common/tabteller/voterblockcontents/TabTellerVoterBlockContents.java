/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common.tabteller.voterblockcontents;

import lombok.Data;

@Data
public class TabTellerVoterBlockContents {

	public final int tellerIndex;
	public final int voterBlock;
	public final String hash;

}