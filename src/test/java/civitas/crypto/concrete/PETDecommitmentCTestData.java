package civitas.crypto.concrete;

import civitas.common.TestUtil;
import civitas.util.CivitasBigInteger;

public interface PETDecommitmentCTestData
		extends ElGamalProofDiscLogEqualityCTestData {

	public static final String PET_DECOMMITMENT_D_BASE64 = "MSXYn+/AgD1p9v4NWcEoowO4CzjHVh8VbE4wYBifZAWIP+Xdo52+r5djtqyWDWbuzXsI9i0Neneez49SCv0ygeJiR7MmdAmG4J3ORqA6xTD4UDx55PcPPMpc6uTuc4dvGqjdNbKilXA4jWXLqZenbMZqTRi3aEADvLoZxwaf8oivyUZEAVL+N31+8wcduU7iicBMQpg8GquJuvrKpQrJ/NegEKKbOAC/hUhu5DjtG9OOwNn0YNIYXrPMfVvIynqbncXQDlshfYwbgW7w7MbJLwEhyCvqSYPJp4N2PEZxdmxq7+Aa3/qLgRvJFvyjWCUG0Iz6gK487CLqLsO15gWOPVIPUi4CKhEjmx4vjwpZfw1I56iVyiUjHN3B2QElgh3AyprVYyPLT2+07HwEUbMOjWnZKR2hS/s73t2lLfFUKNw+H4c4aw73tRF06yVv81l6yEtKYiDNzcpr4pCaoUOIz9Nt24A7NMxryfAxIMgr0CfQZIs3WXTXs22OrUJ1w5cD";
	public static final String PET_DECOMMITMENT_E_BASE64 = "CKgUB0V3mSOMAkJpqgfgqu/b/2+w78IjffAFEnc+TQ7fyHNFvXzBQAW0oWQKUzPZBN7BdTaz3qAN60/TZ2YhRDsKnYGKhXhuB0WZyy8gBxXCs3TGWFu3YtHJvbGe1/5MERVJgxCcgyasctZYHKpdvH0u8+hmRREMQfd6jjmSI9Zqjdcroih7i0z1eYkcU2HJUI7IoR5ifusqz3gBUiGV6pFu6FpruYfKxYYYfEG9N82CNmQQagsZJlT9/aWzOjUrTexfOOA+IxROWf8GjwAI1dzY/bpa49lRmJoFbmPgNan9rFKtn7CtCKR/rhU8VO0zPijYtl7WdkVDXd1ZfJ8uAz4wG97+1h/9x9tJJPtL3MVBJakiKiam/Nz15BNyKoP/tG1VCMYWV/KChCspQcZEt72n58m66nizw9FFApMipMBXg/FTAZDsqEte8nfqfbIxRO9ifkD367ktsdOWrzcngvbl951WIiWAoGIlJhXg0yOkfWJhQq8ALURac+j6VLFW";

	public static final CivitasBigInteger PET_DECOMMITMENT_D = TestUtil
			.asBigint(PET_DECOMMITMENT_D_BASE64);
	public static final CivitasBigInteger PET_DECOMMITMENT_E = TestUtil
			.asBigint(PET_DECOMMITMENT_E_BASE64);
	public static final String PET_DECOMMITMENT_XML = "<petD><d>"
			+ PET_DECOMMITMENT_D_BASE64 + "</d><e>" + PET_DECOMMITMENT_E_BASE64
			+ "</e><prf>" + EL_GAMAL_DISC_LOG_EQUALITY_PROOF_XML + "</prf></petD>";

}
