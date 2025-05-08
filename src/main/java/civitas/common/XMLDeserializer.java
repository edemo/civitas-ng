/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common;

import java.io.IOException;
import java.io.Reader;

/**
 * A deserializer for an XMLSerializable object. Takes in an XML representation
 * of an object via a Reader[ ] and constructs and returns an XMLSerializable
 * object.
 */
public interface XMLDeserializer {
	public XMLSerializable fromXML(Reader s) throws IllegalArgumentException, IOException;
}