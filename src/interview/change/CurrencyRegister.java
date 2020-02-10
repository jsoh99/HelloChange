package interview.change;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Currency register allow user to initialize a cash register for various denominations
 * This is important if this register need to adapt to foreign currency.
 * 
 * Given a register starting with list of denomination ($100, $50, etc) and count for each denomination,
 * The total will be calculated at constructor level.
 * 
 * @author Jasmine Soh 
 *
 */
public class CurrencyRegister {
	
	private int total = 0;

	private final List<Integer> currencyDenomination;

	private List<Integer> countList;

	public static final String ACTION_CHANGE = "change";
	public static final String ACTION_PUT = "put";
	public static final String ACTION_SHOW = "show";
	public static final String ACTION_TAKE = "take";
	
	public CurrencyRegister(List<Integer> currencyDenomination, List<Integer> countList) {
		RuleValidator.validatePositive(currencyDenomination);
		this.currencyDenomination = currencyDenomination;
		RuleValidator.validatePositive(countList);
		this.countList = countList;

		RuleValidator.validateLength(currencyDenomination.size(), countList);
		total = ChangeUtils.countCash(currencyDenomination, countList);
	}
	
	/**
	 * The command will be an string input of <ACTION> [optional parameters]
	 * Supported command is change, put, show, take
	 * @param input
	 */
	public void process(String input) {
		
		try {
			List<Integer> values = null;
			String[] tokens = input.split(" ", 0);
			String action = tokens[0];
			switch(action) {
				case ACTION_SHOW:
					show(total, countList);
					break;
				case ACTION_PUT:
					values = extractValue(tokens);
					updateRegister(ChangeUtils.put(total, currencyDenomination, countList, values));
					show(total, countList);
					break;
				case ACTION_TAKE:
					values = extractValue(tokens);
					updateRegister(ChangeUtils.take(total, currencyDenomination, countList, values));
					show(total, countList);
					break;
				case ACTION_CHANGE:
					values = extractValue(tokens);
					List<Integer> listChange = ChangeUtils.change(total, currencyDenomination, countList, values.get(0));
					updateRegister(ChangeUtils.take(total, currencyDenomination, countList, listChange));
					showChange(listChange);
					break;
				default:
					System.out.println("no action");
			} 
		}
		catch(SorryException | IllegalArgumentException e) {
			System.err.println(e.getMessage());
		}
	}

	private void updateRegister(List<Integer> list) {
		// all values must be validated before updating register values
		RuleValidator.validatePositive(list);
		total = list.get(0);
		countList = list.subList(1, list.size());
	}

	private List<Integer> extractValue(String[] tokens) {
		List<Integer> values = Arrays.stream(tokens, 1, tokens.length)
				.map(Integer::parseInt)
				.collect(Collectors.toList());
		RuleValidator.validatePositive(values);
		return values;
	}

	/**
	 * 
	 * @param valueList
	 */
	public static void show(int total, List<Integer> valueList) {
		StringBuilder sb = new StringBuilder().append('$').append(total);
		
		for(int i: valueList) {
			sb.append(' ').append(i);
		}
		System.out.println(sb.toString());
	}

	/**
	 * 
	 * @param valueList
	 */
	public static void showChange(List<Integer> valueList) {
		StringBuilder sb = new StringBuilder().append(valueList.get(0));
		
		for(int i = 1; i < valueList.size(); i++) {
			sb.append(' ').append(valueList.get(i));
		}
		System.out.println(sb.toString());
	}

}
