package interview.change;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		// configure simple currency register per requirement
		CurrencyRegister currencyRegister = new CurrencyRegister(
				Arrays.asList(20,10,5,2,1), 
				Arrays.asList(1,2,3,4,5));
		System.out.println("ready");
		try (Scanner in = new Scanner(System.in)) {
			String text = "";
			while(true) {
				text = in.nextLine();
				if("quit".equals(text)) {
					break;
				}
				currencyRegister.process(text);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
