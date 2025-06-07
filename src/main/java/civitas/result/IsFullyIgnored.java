package civitas.result;

public class IsFullyIgnored {

	public boolean apply(Boolean[] ignore) {
		boolean ignored = true;
		for (int i = 0; i < ignore.length; i++)
			ignored = ignored && ignore[i];
		return ignored;
	}

}
