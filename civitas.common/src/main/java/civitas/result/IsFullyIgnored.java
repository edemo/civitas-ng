package civitas.result;

import org.springframework.stereotype.Controller;

@Controller
public class IsFullyIgnored {

	public boolean apply(final Boolean... ignore) {
		boolean ignored = true;
		for (Boolean element : ignore) {
			ignored = ignored && element;
		}
		return ignored;
	}
}
