package civitas.util.tests;

import java.io.PrintWriter;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class XMLAnswer implements Answer<Void> {
	String theXML;

	public XMLAnswer(final String theXML) {
		this.theXML = theXML;
	}

	@Override
	public Void answer(final InvocationOnMock invocation) {
		try (PrintWriter printWriter = (PrintWriter) invocation.getArguments()[1]) {
			printWriter.append(theXML);
		}
		return null;
	}
}
