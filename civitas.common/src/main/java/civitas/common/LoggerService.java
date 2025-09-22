package civitas.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.springframework.stereotype.Service;

@Service
public class LoggerService {

	Logger logger = LoggerFactory.getLogger("civitas");

	public void apply(Marker marker, String msg) {
		logger.info(marker, msg);
	}

	public void apply(Marker marker, String msg, Throwable throwable) {
		logger.info(marker, msg, throwable);
	}

}
