/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common.tallystatefinal;

import civitas.common.CommonConstants;
import lombok.Data;

@Data
public class TallyStateFinal implements CommonConstants {
	private final Integer size;
	private final Integer[][] matrix;

}
