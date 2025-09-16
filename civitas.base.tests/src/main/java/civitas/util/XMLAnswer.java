package civitas.util;

import java.io.PrintWriter;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class XMLAnswer implements Answer<Void> {
	String theXML;

	public XMLAnswer(String theXML) {
		this.theXML = theXML;
	}

	@Override
	public Void answer(InvocationOnMock invocation) {
		PrintWriter printWriter = (PrintWriter) invocation.getArguments()[1];
		printWriter.append(theXML);
		return null;
	}

}