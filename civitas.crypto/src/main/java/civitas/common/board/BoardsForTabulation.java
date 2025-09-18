/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common.board;

import io.micrometer.common.lang.NonNull;
import lombok.Data;

@Data
public class BoardsForTabulation {
	@NonNull public final BoardClosedContentCommitment[] contentComs;
}
