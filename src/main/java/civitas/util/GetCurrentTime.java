package civitas.util;

import org.springframework.stereotype.Service;

@Service
public class GetCurrentTime {
	public long apply() {
		long t = System.currentTimeMillis();
		return t;
	}

}
