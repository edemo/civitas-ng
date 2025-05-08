/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common;

import java.io.PrintWriter;

/**
 * XMLSerializable objects can print XML representations of themselves to a
 * PrintWriter[{}]. This is used to send representations of objects over the
 * network.
 */
public interface XMLSerializable {
	public void toXML(PrintWriter s);
}