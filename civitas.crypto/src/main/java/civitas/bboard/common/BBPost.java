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
import lombok.NonNull;
import lombok.Value;

@Value
@XmlRootElement(name = "bbpost")
public class BBPost {
	@NonNull @Id
	public String bbid;

	@NonNull public Long serial;

	@NonNull public Long timestamp;

	@NonNull public String meta;

	@NonNull public String msg;

	@NonNull public Signature sig;

	public byte @NonNull [] hash;
}
