package interview.change;

import java.util.List;

/**
 * Rule validator has some simple validation methods.
 * It is important to ensure data quality before proceeding to data modifier.
 * 
 * @author Jasmine Soh 
 */
public class RuleValidator {
	public static void validatePositive(List<Integer> valueList) {
		for(int i : valueList) {
			if(i < 0) {
				throw new IllegalArgumentException("Value cannot be negative: " + i);
			}
		}
	}

	public static void validateLength(int length, List<Integer> valueList) {
		if(valueList.size() != length) {
			throw new IllegalArgumentException("Inconsistent List Length");
		}
	}
}
