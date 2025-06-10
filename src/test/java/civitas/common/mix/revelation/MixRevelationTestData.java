package civitas.common.mix.revelation;

import civitas.common.mix.capabilitymixrevelation.MixCapabilityElementRevelationTestData;
import civitas.common.mix.elementrevelation.MixElementRevelation;

public interface MixRevelationTestData
		extends MixCapabilityElementRevelationTestData {
	boolean[] directions = new boolean[] { true, false };
	MixRevelation MIX_REVELATION = new MixRevelation(1, directions,
			new MixElementRevelation[] {
					CAPABILITY_ELEMENT_RELEVATION_RIGHT,
					CAPABILITY_ELEMENT_RELEVATION_LEFT });

}
