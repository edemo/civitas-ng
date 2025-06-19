package civitas.result;

import org.springframework.stereotype.Service;

@Service
public class IsFullyIgnored {

	public boolean apply(Boolean[] ignore) {
		boolean ignored = true;
		for (Boolean element : ignore)
			ignored = ignored && element;
		return ignored;
	}

}
