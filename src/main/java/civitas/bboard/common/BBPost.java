/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.bboard.common;

import civitas.crypto.signature.Signature;
import io.micrometer.common.lang.NonNull;
import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public final class BBPost {
	@NonNull
	@Id
	public final String bbid;
	@Nonnull
	public final Long serial;
	@NonNull
	public final Long timestamp;
	@NonNull
	public final String meta;
	@NonNull
	public final String msg;
	@NonNull
	public final Signature sig;
	@NonNull
	public final byte[] hash;

}