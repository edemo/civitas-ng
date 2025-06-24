/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common.board;

import civitas.common.CommonConstants;
import civitas.common.election.ElectionID;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class BoardClosedContentCommitment implements CommonConstants {
	@Id
	public final ElectionID electionID;
	public final String boardName;
	@ElementCollection
	public final String[] voterBlockContentHash;
}