/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.bboard.common;

import org.springframework.data.annotation.Id;

import civitas.crypto.signature.Signature;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.NonNull;

@Data
@XmlRootElement(name = "bbpost")
public final class BBPost {
	@NonNull @Id
	public final String bbid;

	@NonNull public final Long serial;

	@NonNull public final Long timestamp;

	@NonNull public final String meta;

	@NonNull public final String msg;

	@NonNull public final Signature sig;

	@NonNull public final byte[] hash;
}
