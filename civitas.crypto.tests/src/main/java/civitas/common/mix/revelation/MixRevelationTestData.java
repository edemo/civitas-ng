package civitas.common.mix.revelation;

import civitas.common.mix.capabilitymixrevelation.MixCapabilityElementRevelationTestData;
import civitas.common.mix.elementrevelation.MixElementRevelation;

public interface MixRevelationTestData
		extends MixCapabilityElementRevelationTestData {
	boolean[] MIX_REVELATION_DIRECTIONS = { true, false };
	boolean[] MIX_REVELATION_DIRECTIONS_SHORT = { true };
	boolean[] MIX_REVELATION_DIRECTIONS_BAD = { false, false };
	MixRevelation MIX_REVELATION = new MixRevelation(1, MIX_REVELATION_DIRECTIONS,
			new MixElementRevelation[] {
					CAPABILITY_ELEMENT_RELEVATION_RIGHT,
					CAPABILITY_ELEMENT_RELEVATION_LEFT });
	MixRevelation MIX_REVELATION_WITH_NULL_REVELATION = new MixRevelation(1,
			MIX_REVELATION_DIRECTIONS, new MixElementRevelation[] { null, null });
	MixRevelation MIX_REVELATION_WITH_BAD_INDICATOR_LENGTH = new MixRevelation(1,
			MIX_REVELATION_DIRECTIONS_SHORT,
			new MixElementRevelation[] { null, null });

}
