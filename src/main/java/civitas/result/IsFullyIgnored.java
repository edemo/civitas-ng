package civitas.result;

public class IsFullyIgnored {

	public boolean apply(Boolean[] ignore) {
		boolean ignored = true;
		for (Boolean element : ignore)
			ignored = ignored && element;
		return ignored;
	}

}
