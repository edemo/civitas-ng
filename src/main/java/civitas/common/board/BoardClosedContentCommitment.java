/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common.board;

import civitas.common.ElectionID;
import lombok.Data;

@Data
public class BoardClosedContentCommitment {
	public final static String META = "boardContents";
	public final static String OPENING_TAG = "boardContents";

	public final ElectionID electionID;
	public final int boardIndex;

	public final String[] voterBlockContentHash;
}