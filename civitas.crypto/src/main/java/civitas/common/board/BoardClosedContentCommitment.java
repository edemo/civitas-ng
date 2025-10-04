/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common.board;

import java.util.List;

import org.springframework.data.annotation.Id;

import civitas.common.CommonConstants;
import civitas.common.election.ElectionID;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.NonNull;
import lombok.Value;

@Value
@XmlRootElement(name = "boardclosedcontentcommitment")
public class BoardClosedContentCommitment implements CommonConstants {
	@Id
	@NonNull public ElectionID electionID;

	@NonNull public String boardName;

	@NonNull public List<String> voterBlockContentHash;
}
