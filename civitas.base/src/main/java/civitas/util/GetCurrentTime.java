package civitas.util;

import org.springframework.stereotype.Controller;

@Controller
public class GetCurrentTime {
	public long apply() {
		return System.currentTimeMillis();
	}
}
