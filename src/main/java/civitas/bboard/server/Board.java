package civitas.bboard.server;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Board {
	@NonNull
	@Id
	public String boardName;
	@NonNull
	public String keyName;
	@NonNull
	public String keyData;
	public boolean isOpen = true;
}