/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */ 
package civitas.supervisor;

import civitas.common.*;

public class RequestTellerResults {
    public final TellerDetails  accepted;
    public final TellerDetails  declined;
    public final TellerDetails  uncontactable;
    
    RequestTellerResults(TellerDetails  accepted, TellerDetails  declined, TellerDetails  uncontactable) {
        this.accepted = accepted;
        this.declined = declined;
        this.uncontactable = uncontactable;
    }
}