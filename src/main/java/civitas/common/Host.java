/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common;

import civitas.crypto.rsapublickey.PublicKey;
import lombok.Data;

@Data
public class Host {
	public final String address;
	public final int port;
	public final PublicKey publicKey;

}