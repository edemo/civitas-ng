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

@XmlRootElement(name = "boardclosedcontentcommitment")
public record BoardClosedContentCommitment(
		@Id @NonNull ElectionID electionID, @NonNull String boardName, @NonNull List<String> voterBlockContentHash)
		implements CommonConstants {}
