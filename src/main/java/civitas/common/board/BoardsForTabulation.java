/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common.board;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class BoardsForTabulation {
	@NonNull
	@ElementCollection
	public final BoardClosedContentCommitment[] contentComs;
}