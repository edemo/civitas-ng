/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.signature;

import civitas.util.KeyOnWire;
import jakarta.annotation.Nonnull;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NonNull;

@Data
@Embeddable
public class Signature {
	@NonNull
	public final byte[] signature;
	@Nonnull
	KeyOnWire signer;

}
