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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@XmlRootElement(name = "boardclosedcontentcommitment")
@NoArgsConstructor
@AllArgsConstructor
public class BoardClosedContentCommitment implements CommonConstants {
	@Id
	@NonNull
	ElectionID electionID;
	@NonNull
	String boardName;
	@NonNull
	List<String> voterBlockContentHash;
}