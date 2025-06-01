package civitas.util;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class ReaderAnswer<T> implements Answer<T> {

	private final Map<String, T> mockingMap;

	public ReaderAnswer(Map<String, T> mockingMap) {
		this.mockingMap = mockingMap;
	}

	@Override
	public T answer(InvocationOnMock invocation) throws IOException {
		Reader reader = (Reader) invocation.getArguments()[0];
		for (Entry<String, T> entry : mockingMap
				.entrySet().stream().sorted((o1, o2) -> Integer
						.valueOf(o1.getKey().length()).compareTo(o2.getKey().length()))
				.collect(Collectors.toList())) {
			T value = mockValueFromStream(reader, entry.getKey(), entry.getValue());
			if (null != value) {
				return value;
			}
		}
		return null;
	}

	T mockValueFromStream(Reader reader, String theString, T theValue)
			throws IOException {
		int len = theString.length();
		reader.mark(len + 1);
		char[] buf = new char[len];
		reader.read(buf);
		if (new String(buf).equals(theString)) {
			return theValue;
		} else {
			reader.reset();
			return null;
		}
	}

}