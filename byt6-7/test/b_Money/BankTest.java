package b_Money;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;
	
	@Before
	public void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

	@Test
	public void testGetName() {
		assertEquals("Nordea", Nordea.getName());
		assertEquals("DanskeBank", DanskeBank.getName());
		assertEquals("SweBank", SweBank.getName());
	}

	@Test
	public void testGetCurrency() {
		assertEquals("SweBank", SEK, SweBank.getCurrency());
		assertEquals("Nordea", SEK, Nordea.getCurrency());
		assertEquals("DanskeBank", DKK, DanskeBank.getCurrency());
	}

	//@Test(expected = AccountExistsException.class)
	@Test
	public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {
		try {
			SweBank.openAccount("Grunewald");
		} catch (AccountExistsException e) {
			fail("Grunewald 1");
		}
		try {
			SweBank.openAccount("Bob");
			fail("already exist1");
			Nordea.openAccount("Bob");
			fail("already exist2");
			DanskeBank.openAccount("Gertrud");
			fail("already exist3");
			SweBank.openAccount("Grunewald");
			fail("already exist4");
		} catch (AccountExistsException ignored) {
		}
	}

	@Test
	public void testDeposit() throws AccountDoesNotExistException {
		assertEquals("Ulrika before", 0, SweBank.getBalance("Ulrika").intValue());
		SweBank.deposit("Ulrika", new Money(10000, SEK));
		assertEquals("Ulrika after", 10000, SweBank.getBalance("Ulrika").intValue());
	}

	@Test
	public void testWithdraw() throws AccountDoesNotExistException {
		assertEquals("Ulrika before", 0, SweBank.getBalance("Ulrika").intValue());
		SweBank.deposit("Ulrika", new Money(10000, SEK));
		assertEquals("Ulrika after deposit", 10000, SweBank.getBalance("Ulrika").intValue());
		SweBank.withdraw("Ulrika", new Money(5000, SEK));
		assertEquals("Ulrika after withdraw", 5000, SweBank.getBalance("Ulrika").intValue());
	}
	
	@Test
	public void testGetBalance() throws AccountDoesNotExistException {
		assertEquals("Ulrika", 0, SweBank.getBalance("Ulrika").intValue());
		try {
			assertEquals("NO", 0, SweBank.getBalance("NO").intValue());
			fail("no such account");
		} catch (AccountDoesNotExistException ignored) {
		}
	}
	
	@Test
	public void testTransfer() throws AccountDoesNotExistException {
		SweBank.deposit("Ulrika", new Money(10000, SEK));
		try {
			SweBank.transfer("Ulrika NO", Nordea, "Bob", new Money(5000, SEK));
			fail("Acc does not exist1");
			SweBank.transfer("Ulrika", Nordea, "Bob NO", new Money(5000, SEK));
			fail("Acc does not exist1");
		} catch (AccountDoesNotExistException ignored) {
		}
		SweBank.transfer("Ulrika", Nordea, "Bob", new Money(5000, SEK));
		assertEquals("Ulrika", 5000, SweBank.getBalance("Ulrika").intValue());
		assertEquals("Bob", 5000, Nordea.getBalance("Bob").intValue());
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		SweBank.deposit("Ulrika", new Money(100_000_00, SEK));
		Nordea.deposit("Bob", new Money(10_000_00, SEK));
		SweBank.addTimedPayment("Ulrika", "payment1", 2, 1, new Money(10_000_00, SEK), Nordea, "Bob");
		assertEquals("before tick1 Ulrika", 100_000_00, SweBank.getBalance("Ulrika").intValue());
		assertEquals("before tick1 Bob", 10_000_00, Nordea.getBalance("Bob").intValue());
		SweBank.tick();
		assertEquals("after tick1 Ulrika", 100_000_00, SweBank.getBalance("Ulrika").intValue());
		assertEquals("after tick1 Bob", 10_000_00, Nordea.getBalance("Bob").intValue());
		SweBank.tick();
		assertEquals("after tick2 Ulrika", 90_000_00, SweBank.getBalance("Ulrika").intValue());
		assertEquals("after tick2 Bob", 20_000_00, Nordea.getBalance("Bob").intValue());
		SweBank.tick();
		assertEquals("after tick3 Ulrika", 90_000_00, SweBank.getBalance("Ulrika").intValue());
		assertEquals("after tick3 Bob", 20_000_00, Nordea.getBalance("Bob").intValue());
		SweBank.tick();
		assertEquals("after tick4 Ulrika", 90_000_00, SweBank.getBalance("Ulrika").intValue());
		assertEquals("after tick4 Bob", 20_000_00, Nordea.getBalance("Bob").intValue());
		SweBank.tick();
		assertEquals("after tick5 Ulrika", 80_000_00, SweBank.getBalance("Ulrika").intValue());
		assertEquals("after tick5 Bob", 30_000_00, Nordea.getBalance("Bob").intValue());
	}
}
