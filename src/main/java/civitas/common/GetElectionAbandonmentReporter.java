package civitas.common;

public class GetElectionAbandonmentReporter {
	public String apply(ElectionAbandonment that) {
		if (that.isTabulationTeller) {
			return "tabulation teller " + that.tellerIndex;
		}
		return "unknown entity";
	}

}
