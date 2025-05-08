/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common;

/**
 * This class is the super class of all tally states. A tally state is an object
 * to store state in while tallying the results on an election. TallyStateDesign
 * subclasses should be the only code to modify a tally state.
 */
public abstract class TallyState {
	public abstract TallyStateFinal finalTally();
}