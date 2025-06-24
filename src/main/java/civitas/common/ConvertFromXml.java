package civitas.common;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

@Service
public class ConvertFromXml {

	public <T> T apply(String xmlString, Class<T> klass)
			throws JsonMappingException, JsonProcessingException {
		XmlMapper mapper = new XmlMapper();
		return mapper.readValue(xmlString, klass);
	}

}
