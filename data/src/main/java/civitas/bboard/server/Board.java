package civitas.bboard.server;

import org.springframework.data.annotation.Id;

import io.micrometer.common.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Board {
	@NonNull
	@Id
	public String boardName;
	@NonNull
	public String keyString;
	public boolean isOpen = true;
}