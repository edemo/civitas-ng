/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common.tabteller.details;

import civitas.common.Host;
import lombok.Data;

@Data
public class TellerDetails {
	public final Host[] registrationTellers;
	public final Host[] tabulationTellers;
	public final Host[] voterBBs;

}