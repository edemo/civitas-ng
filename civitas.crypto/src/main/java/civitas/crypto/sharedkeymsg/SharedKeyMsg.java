/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.sharedkeymsg;

import lombok.Data;
import lombok.NonNull;

@Data
public class SharedKeyMsg {
	@NonNull
	public final String m;

}
