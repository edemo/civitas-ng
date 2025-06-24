package civitas.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeyOnWire {
	@NonNull
	public String name;
	@NonNull
	public String key;
}