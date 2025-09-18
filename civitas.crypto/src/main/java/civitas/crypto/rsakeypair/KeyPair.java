/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.rsakeypair;

import java.security.PrivateKey;
import java.security.PublicKey;

import lombok.NonNull;

public record KeyPair(@NonNull PublicKey publicKey, @NonNull PrivateKey privateKey) {}
