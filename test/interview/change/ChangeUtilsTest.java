package interview.change;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class ChangeUtilsTest {

	// basic test case as specified per requirement doc
	@Test
	public void testJapaneseYen() throws SorryException {
		List<Integer> denominationList = Arrays.asList(10000, 5000, 2000, 1000);
		List<Integer> countList = Arrays.asList(1, 2, 3, 4);

		int total = ChangeUtils.countCash(denominationList, countList);
		Assert.assertEquals(30000, total);

		List<Integer> result = ChangeUtils.put(total, denominationList, countList, Arrays.asList(4, 3, 2, 1));
		Assert.assertTrue(90000 == result.get(0));
		for (int i = 1; i < countList.size(); i++) {
			Assert.assertTrue(5 == result.get(i));
		}

		result = ChangeUtils.take(total, denominationList, countList, Arrays.asList(1, 1, 1, 1));
		Assert.assertTrue(12000 == result.get(0));
		for (int i = 1; i < countList.size(); i++) {
			Assert.assertTrue((i - 1) == result.get(i));
		}

		countList = Arrays.asList(1, 3, 4, 0);

		result = ChangeUtils.change(total, denominationList, countList, 17000);
		Assert.assertTrue(1 == result.get(0));
		Assert.assertTrue(1 == result.get(1));
		Assert.assertTrue(1 == result.get(2));
		Assert.assertTrue(0 == result.get(3));

		result = ChangeUtils.change(total, denominationList, countList, 18000);
		Assert.assertTrue(1 == result.get(0));
		Assert.assertTrue(0 == result.get(1));
		Assert.assertTrue(4 == result.get(2));
		Assert.assertTrue(0 == result.get(3));
	}

	@Test(expected = SorryException.class)
	public void testJapaneseYen_noChange1() throws SorryException {
		List<Integer> denominationList = Arrays.asList(10000, 5000, 2000, 1000);
		List<Integer> countList = Arrays.asList(1, 2, 3, 4);
		ChangeUtils.change(30000, denominationList, countList, 1);
	}

	@Test(expected = SorryException.class)
	public void testJapaneseYen_noChange2() throws SorryException {
		List<Integer> denominationList = Arrays.asList(10000, 5000, 2000, 1000);
		List<Integer> countList = Arrays.asList(1, 2, 3, 4);
		ChangeUtils.change(30000, denominationList, countList, 31000);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testJapaneseYen_badTake() throws SorryException {
		List<Integer> denominationList = Arrays.asList(10000, 5000, 2000, 1000);
		List<Integer> countList = Arrays.asList(1, 2, 3, 0);
		ChangeUtils.take(30000, denominationList, countList, Arrays.asList(1, 1, 1, 1));
	}

	@Test
	public void testUsDollar() throws SorryException {
		List<Integer> denominationList = Arrays.asList(20, 10, 5, 2, 1);
		List<Integer> countList = Arrays.asList(1, 2, 3, 3, 0);

		int total = ChangeUtils.countCash(denominationList, countList);
		Assert.assertEquals(61, total);

		List<Integer> result = ChangeUtils.put(total, denominationList, countList, Arrays.asList(2, 1, 0, 0, 3));
		Assert.assertTrue(114 == result.get(0));
		for (int i = 1; i < countList.size(); i++) {
			Assert.assertTrue(3 == result.get(i));
		}

		result = ChangeUtils.take(total, denominationList, countList, Arrays.asList(1, 2, 3, 3, 0));
		Assert.assertTrue(0 == result.get(0));
		for (int i = 1; i < countList.size(); i++) {
			Assert.assertTrue(0 == result.get(i));
		}

		result = ChangeUtils.change(total, denominationList, countList, 11);
		Assert.assertTrue(0 == result.get(0));
		Assert.assertTrue(0 == result.get(1));
		Assert.assertTrue(1 == result.get(2));
		Assert.assertTrue(3 == result.get(3));
		Assert.assertTrue(0 == result.get(4));

		result = ChangeUtils.change(total, denominationList, countList, 41);
		Assert.assertTrue(1 == result.get(0));
		Assert.assertTrue(1 == result.get(1));
		Assert.assertTrue(1 == result.get(2));
		Assert.assertTrue(3 == result.get(3));
		Assert.assertTrue(0 == result.get(4));
	}
}
