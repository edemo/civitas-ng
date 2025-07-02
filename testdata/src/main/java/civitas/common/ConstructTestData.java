package civitas.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ConstructTestData {

	public static <I, O> Map<I, O> constructTestData(List<I> values,
			Function<I, O> konstruktor) {
		Map<I, O> map = new HashMap<>();
		for (I value : values) {
			map.put(value, konstruktor.apply(value));
		}
		return map;
	}

}
