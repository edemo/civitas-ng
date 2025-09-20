package civitas.common;

import java.io.StringWriter;

import org.springframework.stereotype.Controller;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

@Controller
public class ConvertToXml {

	public <T> String apply(T that) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(that.getClass());

		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

		StringWriter writer = new StringWriter();
		marshaller.marshal(that, writer);
		return writer.toString();
	}
}
