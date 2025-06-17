package civitas.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class ConvertToXml {

	public <T> String apply(T that) throws JsonProcessingException {
		XmlMapper mapper = new XmlMapper();
		String xml = mapper.writeValueAsString(that);
		return xml;
	}

}
