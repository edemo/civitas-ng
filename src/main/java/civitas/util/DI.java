package civitas.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class DI {

	private static Map<Class<?>, Object> instances = new HashMap<Class<?>, Object>();

	public static <T> T get(Class<T> klass) {
		if (instances.containsKey(klass)) {
			return (T) instances.get(klass);
		}
		Object instance;
		try {
			Constructor<T> constructor = klass.getDeclaredConstructor();
			constructor.setAccessible(true);
			instance = constructor.newInstance();
			fill(instance);
		} catch (Exception e) {
			throw new Error(e);
		}
		instances.put(klass, instance);
		return (T) instance;
	}

	public static void fill(Object o) {

		for (Field field : o.getClass().getDeclaredFields()) {
			if (field.isAnnotationPresent(Use.class)) {
				field.setAccessible(true);
				Object instance = get(field.getType());
				try {
					field.set(o, instance);
				} catch (Exception e) {
					throw new Error(e);
				}
			}
		}

	}
}
