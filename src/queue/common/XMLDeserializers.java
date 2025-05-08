/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */ 
package civitas.common;

import java.io.*;

/*
 * Factory for XMLDeserializers, and classes to deserialize XML representations
 * of objects.
 */
public class XMLDeserializers {
    public static XMLDeserializer ElectionEvent() {
        return new XMLDeserializerElectionEvent();
    }
    public static XMLDeserializer ElectionDetails() {
        return new XMLDeserializerElectionDetails();
    }
    public static XMLDeserializer CiphertextList() {
        return new XMLDeserializerCiphertextList();
    }
    public static XMLDeserializer BoardClosedContentCommitment() {
        return new XMLDeserializerBoardClosedContentCommitment();
    }
    public static XMLDeserializer BoardsForTabulation() {
        return new XMLDeserializerBoardsForTabulation();
    }
    public static XMLDeserializer TellerDetails() {
        return new XMLDeserializerTellerDetails();
    }
    public static XMLDeserializer ElectoralRollEstimate() {
        return new XMLDeserializerElectoralRollEstimate();
    }
    public static XMLDeserializer ElectoralRoll() {
        return new XMLDeserializerElectoralRoll();
    }
    public static XMLDeserializer ElectoralRollCapabilityShares() {
        return new XMLDeserializerElectoralRollCapabilityShares();
    }
    public static XMLDeserializer TabTellerKeyShare() {
        return new XMLDeserializerTabTellerKeyShare();
    }
    public static XMLDeserializer TabTellerKeyShareCommitment() {
        return new XMLDeserializerTabTellerKeyShareCommitment();
    }
    public static XMLDeserializer ElectionAbandonment() {
        return new XMLDeserializerElectionAbandonment();
    }
    public static XMLDeserializer ElectionResults() {
        return new XMLDeserializerElectionResults();
    }
    public static XMLDeserializer TabTellerDistributedDecryptions() {
        return new XMLDeserializerTabTellerDistributedDecryptions();
    }
    public static XMLDeserializer VoteMix() {
        return new XMLDeserializerVoteMix();
    }
    public static XMLDeserializer CapabilityMix() {
        return new XMLDeserializerCapabilityMix();
    }
    public static XMLDeserializer MixHashRevelation() {
        return new XMLDeserializerMixHashRevelation();
    }
    public static XMLDeserializer MixRevelation() {
        return new XMLDeserializerMixRevelation();
    }
    public static XMLDeserializer TabTellerPETShareCommitments() {
        return new XMLDeserializerTabTellerPETShareCommitments();
    }
    public static XMLDeserializer TabTellerPETShareDecommitments() {
        return new XMLDeserializerTabTellerPETShareDecommitments();
    }
    public static XMLDeserializer TabTellerVoterBlockContents() {
        return new XMLDeserializerTabTellerVoterBlockContents();
    }
    public static XMLDeserializer VoterSubmission() {
        return new XMLDeserializerVoterSubmission();
    }
    public static XMLDeserializer generic() {
        return new XMLDeserializerGeneric();
    }   
}

class XMLDeserializerElectionEvent implements XMLDeserializer {
    public XMLSerializable{} fromXML{}(Reader[{}]{} s):{} throws IllegalArgumentException, IOException {
        return ElectionEvent.fromXML(new label {}, s);
    }
}
class XMLDeserializerElectionDetails implements XMLDeserializer {
    public XMLSerializable{} fromXML{}(Reader[{}]{} s):{} throws IllegalArgumentException, IOException {
        return ElectionDetails.fromXML(new label {}, s);
    }
}
class XMLDeserializerCiphertextList implements XMLDeserializer {
    public XMLSerializable{} fromXML{}(Reader[{}]{} s):{} throws IllegalArgumentException, IOException {
        return CiphertextList.fromXML(new label {}, s);
    }
}
class XMLDeserializerBoardClosedContentCommitment implements XMLDeserializer {
    public XMLSerializable{} fromXML{}(Reader[{}]{} s):{} throws IllegalArgumentException, IOException {
        return BoardClosedContentCommitment.fromXML(new label {}, s);
    }
}
class XMLDeserializerBoardsForTabulation implements XMLDeserializer {
    public XMLSerializable{} fromXML{}(Reader[{}]{} s):{} throws IllegalArgumentException, IOException {
        return BoardsForTabulation.fromXML(new label {}, s);
    }
}
class XMLDeserializerTellerDetails implements XMLDeserializer {
    public XMLSerializable{} fromXML{}(Reader[{}]{} s):{} throws IllegalArgumentException, IOException {
        return TellerDetails.fromXML(new label {}, s);
    }
}
class XMLDeserializerElectoralRollEstimate implements XMLDeserializer {
    public XMLSerializable{} fromXML{}(Reader[{}]{} s):{} throws IllegalArgumentException, IOException {
        return ElectoralRollEstimate.fromXML(new label {}, s);
    }
}
class XMLDeserializerElectoralRoll implements XMLDeserializer {
    public XMLSerializable{} fromXML{}(Reader[{}]{} s):{} throws IllegalArgumentException, IOException {
        return ElectoralRoll.fromXML(s);
    }
}
class XMLDeserializerElectoralRollCapabilityShares implements XMLDeserializer {
    public XMLSerializable{} fromXML{}(Reader[{}]{} s):{} throws IllegalArgumentException, IOException {
        return ElectoralRollCapabilityShares.fromXML(s);
    }
}
class XMLDeserializerTabTellerKeyShare implements XMLDeserializer {
    public XMLSerializable{} fromXML{}(Reader[{}]{} s):{} throws IllegalArgumentException, IOException {
        return TabTellerKeyShare.fromXML(s);
    }
}
class XMLDeserializerTabTellerKeyShareCommitment implements XMLDeserializer {
    public XMLSerializable{} fromXML{}(Reader[{}]{} s):{} throws IllegalArgumentException, IOException {
        return TabTellerKeyShareCommitment.fromXML(s);
    }
}
class XMLDeserializerElectionAbandonment implements XMLDeserializer {
    public XMLSerializable{} fromXML{}(Reader[{}]{} s):{} throws IllegalArgumentException, IOException {
        return ElectionAbandonment.fromXML(s);
    }
}
class XMLDeserializerElectionResults implements XMLDeserializer {
    public XMLSerializable{} fromXML{}(Reader[{}]{} s):{} throws IllegalArgumentException, IOException {
        return ElectionResults.fromXML(new label {}, s);
    }
}
class XMLDeserializerTabTellerDistributedDecryptions implements XMLDeserializer {
    public XMLSerializable{} fromXML{}(Reader[{}]{} s):{} throws IllegalArgumentException, IOException {
        return TabTellerDistributedDecryptions.fromXML(new label {}, s);
    }
}
class XMLDeserializerVoteMix implements XMLDeserializer {
    public XMLSerializable{} fromXML{}(Reader[{}]{} s):{} throws IllegalArgumentException, IOException {
        return VoteMix.fromXML(s);
    }
}
class XMLDeserializerCapabilityMix implements XMLDeserializer {
    public XMLSerializable{} fromXML{}(Reader[{}]{} s):{} throws IllegalArgumentException, IOException {
        return CapabilityMix.fromXML(s);
    }
}
class XMLDeserializerMixHashRevelation implements XMLDeserializer {
    public XMLSerializable{} fromXML{}(Reader[{}]{} s):{} throws IllegalArgumentException, IOException {
        return MixHashRevelation.fromXML(s);
    }
}
class XMLDeserializerMixRevelation implements XMLDeserializer {
    public XMLSerializable{} fromXML{}(Reader[{}]{} s):{} throws IllegalArgumentException, IOException {
        return MixRevelation.fromXML(s);
    }
}
class XMLDeserializerTabTellerPETShareCommitments implements XMLDeserializer {
    public XMLSerializable{} fromXML{}(Reader[{}]{} s):{} throws IllegalArgumentException, IOException {
        return TabTellerPETShareCommitments.fromXML(s);
    }
}
class XMLDeserializerTabTellerPETShareDecommitments implements XMLDeserializer {
    public XMLSerializable{} fromXML{}(Reader[{}]{} s):{} throws IllegalArgumentException, IOException {
        return TabTellerPETShareDecommitments.fromXML(s);
    }
}
class XMLDeserializerTabTellerVoterBlockContents implements XMLDeserializer {
    public XMLSerializable{} fromXML{}(Reader[{}]{} s):{} throws IllegalArgumentException, IOException {
        return TabTellerVoterBlockContents.fromXML(new label {}, s);
    }
}
class XMLDeserializerVoterSubmission implements XMLDeserializer {
    public XMLSerializable{} fromXML{}(Reader[{}]{} s):{} throws IllegalArgumentException, IOException {
        return VoterSubmission.fromXML(new label {}, s);
    }
}
class XMLDeserializerGeneric implements XMLDeserializer {
    public XMLSerializable{} fromXML{}(Reader[{}]{} s):{} throws IllegalArgumentException, IOException {
        return XMLGeneric.fromXML(s);
    }
}
class XMLGeneric implements XMLSerializable {
    private final String  xml;
    XMLGeneric(String  xml) {
        this.xml = xml;
    }
    public void toXML{}(PrintWriter[{}]{} s) {
        if (s != null) s.print(xml);
    }
    public static XMLGeneric{} fromXML{}(Reader[{}]{} s):{} throws IllegalArgumentException, IOException {
        String tag = Util.nextTag(new label {}, s);
        String xml = Util.readUntil(new label {}, s, "</" + tag + ">");
        return new XMLGeneric(xml); 
    }
}