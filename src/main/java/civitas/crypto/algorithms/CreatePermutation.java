package civitas.crypto.algorithms;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreatePermutation {

	@Autowired
	GenerateRandomInt generateRandomInt;

	public int[] apply(int size) {
		List<Integer> l = new LinkedList<>();
		for (int i = 0; i < size; i++) {
			l.add(Integer.valueOf(i));
		}

		// now select and remove elements at random from the list.
		int[] perm = new int[size];
		for (int i = 0; i < size; i++) {
			int j = generateRandomInt.apply(l.size());
			perm[i] = l.remove(j).intValue();
		}

		return perm;
	}

}
