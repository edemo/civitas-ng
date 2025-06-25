package civitas.common;

import org.springframework.stereotype.Service;

import civitas.util.KeyOnWire;

@Service
public class CheckAccess {

	public void apply(Operation operation, KeyOnWire principal, String objectID)
			throws SecurityException {
		switch (operation) {
		default:
			throw new SecurityException(operation + " is not authorized for "
					+ objectID + " by " + principal.keyBase64);
		}
	}

}
