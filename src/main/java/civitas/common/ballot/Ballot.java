/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common.ballot;

import civitas.common.CommonConstants;
import lombok.Data;
import lombok.NonNull;

@Data
public class Ballot implements CommonConstants {
	@NonNull
	public final Integer k;
	@NonNull
	public final int[] matrix;
}