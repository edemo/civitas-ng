/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common.mix.revelation;

import civitas.common.mix.elementrevelation.MixElementRevelation;
import lombok.Data;
import lombok.NonNull;

@Data
public class MixRevelation {
	public final int tellerIndex;

	@NonNull public final boolean[] indicators;

	@NonNull public final MixElementRevelation[] revelations;
}
