package civitas.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import civitas.common.CommonUtil;

class CivitasBigIntegerDeserializer extends StdDeserializer<CivitasBigInteger> {

	private static final long serialVersionUID = 1L;

	protected CivitasBigIntegerDeserializer() {
		this(null);
	}

	protected CivitasBigIntegerDeserializer(Class<CivitasBigInteger> vc) {
		super(vc);
	}

	@Override
	public CivitasBigInteger deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JacksonException {
		JsonNode node = p.getCodec().readTree(p);
		return CommonUtil.asBigint(node.asText());
	}
}
