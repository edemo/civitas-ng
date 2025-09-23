package civitas.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import civitas.common.CommonUtil;

class CivitasBigIntegerSerializer extends StdSerializer<CivitasBigInteger> {

	private static final long serialVersionUID = 1L;

	protected CivitasBigIntegerSerializer() {
		this(null);
	}

	protected CivitasBigIntegerSerializer(final Class<CivitasBigInteger> t) {
		super(t);
	}

	@Override
	public void serialize(final CivitasBigInteger value, final JsonGenerator gen, final SerializerProvider provider)
			throws IOException {
		gen.writeString(CommonUtil.fromBigInt(value));
	}
}
