package civitas.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ConstructTestData {

	public static <I, O> Map<I, O> constructTestData(final List<I> values, final Function<I, O> constructor) {
		Map<I, O> map = new HashMap<>();
		for (I value : values) {
			map.put(value, constructor.apply(value));
		}
		return map;
	}
}
