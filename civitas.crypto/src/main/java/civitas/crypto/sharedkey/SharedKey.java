/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.sharedkey;

import javax.crypto.SecretKey;

import lombok.NonNull;

public record SharedKey(@NonNull SecretKey k, @NonNull String name) {}
