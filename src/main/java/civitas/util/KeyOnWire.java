package civitas.util;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class KeyOnWire {
	@NonNull
	public String name;
	@NonNull
	public String key;
}