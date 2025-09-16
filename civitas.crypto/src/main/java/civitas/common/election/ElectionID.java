/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common.election;

import civitas.common.board.BulletinBoardIDish;
import lombok.Data;

@Data
public class ElectionID implements BulletinBoardIDish {
	public final String id;
	public final String uriBase;

}