package civitas.util;

import org.springframework.stereotype.Controller;

@Controller
public class GetCurrentTime {
	public long apply() {
		long t = System.currentTimeMillis();
		return t;
	}

}
