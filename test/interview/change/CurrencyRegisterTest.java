package interview.change;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CurrencyRegisterTest {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final PrintStream printOut = System.out;
	private final PrintStream printErr = System.err;

	private CurrencyRegister currencyRegister;

	@Before
	public void setUpStreams() {
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
	}

	@After
	public void restoreStreams() throws IOException {
		outContent.close();
		errContent.close();
		System.setOut(printOut);
		System.setErr(printErr);
	}

	@Before
	public void setUp() throws Exception {
		currencyRegister = new CurrencyRegister(Arrays.asList(20, 10, 5, 2, 1), Arrays.asList(1, 2, 3, 4, 5));
	}

	// basic test case as specified per requirement doc
	@Test
	public void test() throws IOException {
		currencyRegister.process("show");
		Assert.assertEquals(outContent.toString(), "$68 1 2 3 4 5" + System.getProperty("line.separator"));
		outContent.reset();
		
		currencyRegister.process("put 1 2 3 0 5");
		Assert.assertEquals(outContent.toString(), "$128 2 4 6 4 10" + System.getProperty("line.separator"));
		outContent.reset();

		currencyRegister.process("take 1 4 3 0 10");
		Assert.assertEquals(outContent.toString(), "$43 1 0 3 4 0" + System.getProperty("line.separator"));
		outContent.reset();

		currencyRegister.process("change 11");
		Assert.assertEquals(outContent.toString(), "0 0 1 3 0" + System.getProperty("line.separator"));
		outContent.reset();

		currencyRegister.process("change 13");
		Assert.assertEquals(errContent.toString(), "sorry" + System.getProperty("line.separator"));
		errContent.reset();
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_badInput1() throws SorryException {
		currencyRegister = new CurrencyRegister(Arrays.asList(20, 10, 5, 2, 1), Arrays.asList(1, 2, 3, 4));
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_badInput2() throws SorryException {
		currencyRegister = new CurrencyRegister(Arrays.asList(20, 10, 5, 2, -1), Arrays.asList(1, 2, 3, 4, -1));
	}

}
