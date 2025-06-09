/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common.board;

import lombok.Data;

@Data
public class BulletinBoardID implements BulletinBoardIDish {
	public final String host;
	public final int port;
	public final String id;

}