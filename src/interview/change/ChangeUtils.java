package interview.change;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ChangeUtils allow stateless operations to be perform base purely on input.
 * Thus, the output or code quality can be better maintained.
 * 
 * @author Jasmine Soh
 *
 */
public class ChangeUtils {

	public static List<Integer> put(int total, List<Integer> denom, List<Integer> currentCountList,
			List<Integer> countList) {
		List<Integer> result = new ArrayList<>();
		for (int i = 0; i < currentCountList.size(); i++) {
			result.add(countList.get(i) + currentCountList.get(i));
			total += (denom.get(i) * countList.get(i));
		}
		result.add(0, total);
		return result;
	}

	public static List<Integer> take(int total, List<Integer> denom, List<Integer> currentCountList,
			List<Integer> countList) {
		List<Integer> result = new ArrayList<>();
		for (int i = 0; i < currentCountList.size(); i++) {
			int count = currentCountList.get(i) - countList.get(i);
			if (count < 0) {
				throw new IllegalArgumentException("Insufficient count.");
			}
			result.add(count);
			total -= (denom.get(i) * countList.get(i));
			if (total < 0) {
				throw new IllegalArgumentException("Insufficient total.");
			}
		}
		result.add(0, total);
		return result;
	}

	public static List<Integer> change(int total, List<Integer> denominationList, List<Integer> registerCountList,
			int amount) throws SorryException {

		if (amount > total) {
			throw new SorryException();
		}

		List<Integer> result = new ArrayList<>(Collections.nCopies(denominationList.size(), 0));
		recursiveChange(-1, amount, denominationList, registerCountList, result);

		if (countCash(denominationList, result) != amount) {
			throw new SorryException();
		}

		return result;
	}

	public static int countCash(List<Integer> denomicationList, List<Integer> countList) {
		int amount = 0;
		for (int i = 0; i < denomicationList.size(); i++) {
			amount += denomicationList.get(i) * countList.get(i);
		}
		return amount;
	}

	private static boolean recursiveChange(int index, int total, List<Integer> denominationList,
			List<Integer> registerCountList, List<Integer> result) throws SorryException {
		index++;
		if (index > denominationList.size() - 1 || total < 0) {
			return false;
		}
		int count = total / denominationList.get(index);

		if (registerCountList.get(index) == 0 || count == 0) {
			result.set(index, 0);
			return recursiveChange(index, total, denominationList, registerCountList, result);
		}
		if (count > registerCountList.get(index)) {
			count = registerCountList.get(index);
		}

		if (total == count * denominationList.get(index)) {
			result.set(index, count);
			return true;
		}

		while (count >= 0) {
			if (recursiveChange(index, total - (count * denominationList.get(index)), denominationList,
					registerCountList, result)) {
				result.set(index, count);
				return true;
			}
			count--;
		}
		return false;
	}

}
