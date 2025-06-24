/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common.election;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class ElectoralRollEstimate {
	public final int estimate;
}