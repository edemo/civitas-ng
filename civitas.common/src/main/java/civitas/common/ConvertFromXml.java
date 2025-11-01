package civitas.common;

import java.io.StringReader;

import org.springframework.stereotype.Controller;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.adapters.NormalizedStringAdapter;

@Controller
public class ConvertFromXml {

	public <T> T apply(final String xmlString, final Class<T> klass) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(klass);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		unmarshaller.setAdapter(new NormalizedStringAdapter());

		try (var reader = new StringReader(xmlString)) {
			Object o = unmarshaller.unmarshal(reader);
			return klass.cast(o);
		}
	}
}
