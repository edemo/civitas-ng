package civitas.common;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.MockingDetails;
import org.mockito.Mockito;
import org.mockito.exceptions.misusing.NotAMockException;
import org.mockito.invocation.Invocation;
import org.springframework.beans.factory.annotation.Autowired;

public class TestBase {
	boolean doPrintMockInvocations = false;

	public static List<EnvironmentState> states;

	@BeforeEach
	public void setUp() {
		states = List.of();
		stubUp(this);
		RandomAnswer.step = 0;

	}

	@AfterEach
	public void tearDown() throws Exception {
		if (doPrintMockInvocations)
			printMockInvocations(this);
		doPrintMockInvocations = false;
	}

	protected void given(EnvironmentState state) {
		TestBase.states = List.of(state);
	}

	public static void printMockInvocations(Object test)
			throws IllegalArgumentException, IllegalAccessException {
		for (Field objField : test.getClass().getDeclaredFields()) {
			objField.setAccessible(true);
			Object service = objField.get(test);
			if (objField.isAnnotationPresent(InjectMocks.class)) {
				for (Field mockField : objField.getType().getDeclaredFields()) {
					mockField.setAccessible(true);
					Object mock = mockField.get(service);
					try {
						MockingDetails mockingDetails = Mockito.mockingDetails(mock);
						Collection<Invocation> invocations = mockingDetails
								.getInvocations();
						System.out.println(mockField.getName());
						invocations.forEach(x -> System.out.println(x));
					} catch (NotAMockException e) {
					}
				}
			}
		}
	}

	public static void stubUp(Object test) {
		try {
			for (Field objField : test.getClass().getDeclaredFields()) {
				if (objField.isAnnotationPresent(InjectMocks.class)) {
					Class<?> type = objField.getType();
					Constructor<?> constructor = type.getDeclaredConstructor();
					constructor.setAccessible(true);
					Object instance = constructor.newInstance();
					objField.setAccessible(true);
					objField.set(test, instance);
					stubFill(instance);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Error(e);
		}
	}

	public static void stubFill(Object instance) {
		Class<? extends Object> type = instance.getClass();
		for (Field field : type.getDeclaredFields()) {
			if (field.isAnnotationPresent(Autowired.class)) {
				String stubName = field.getType().getName() + "Stub";
				Class<?> stub;
				Object value;
				try {
					stub = Class.forName(stubName);
					Method method = stub.getDeclaredMethod("stub");
					if (null == method)
						throw new Error(stubName + " does not have stub");
					method.setAccessible(true);
					value = method.invoke(null);
				} catch (Exception e) {
					e.printStackTrace();
					throw new Error("problem with stub " + stubName, e);
				}
				field.setAccessible(true);
				try {
					field.set(instance, value);
				} catch (Exception e) {
					throw new Error(e);
				}
			}
		}
	}

}