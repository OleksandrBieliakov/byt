package b_Money;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK, DKK;
	Bank Nordea;
	Bank DanskeBank;
	Bank SweBank;
	Account testAccount;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10000000, SEK));
		SweBank.deposit("Alice", new Money(1000000, SEK));
	}
	
	@Test
	public void testAddRemoveTimedPayment() {
		assertFalse("no payment before", testAccount.timedPaymentExists("payment1"));
		testAccount.addTimedPayment("payment1", 10, 10, new Money(10000, SEK), SweBank, "Alice");
		assertTrue("payment exists", testAccount.timedPaymentExists("payment1"));
		testAccount.removeTimedPayment("payment1");
		assertFalse("no payment after", testAccount.timedPaymentExists("payment1"));
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		testAccount.addTimedPayment("payment1", 2, 1, new Money(10_000_00, SEK), SweBank, "Alice");
		assertEquals("before tick1 Hans", 100_000_00, testAccount.getBalance().getAmount().intValue());
		assertEquals("before tick1 Alice", 10_000_00, SweBank.getBalance("Alice").intValue());
		testAccount.tick();
		assertEquals("after tick1 Hans", 100_000_00, testAccount.getBalance().getAmount().intValue());
		assertEquals("after tick1 Alice", 10_000_00, SweBank.getBalance("Alice").intValue());
		testAccount.tick();
		assertEquals("after tick2 Hans", 90_000_00, testAccount.getBalance().getAmount().intValue());
		assertEquals("after tick2 Alice", 20_000_00, SweBank.getBalance("Alice").intValue());
		testAccount.tick();
		assertEquals("after tick3 Hans", 90_000_00, testAccount.getBalance().getAmount().intValue());
		assertEquals("after tick3 Alice", 20_000_00, SweBank.getBalance("Alice").intValue());
		testAccount.tick();
		assertEquals("after tick4 Hans", 90_000_00, testAccount.getBalance().getAmount().intValue());
		assertEquals("after tick4 Alice", 20_000_00, SweBank.getBalance("Alice").intValue());
		testAccount.tick();
		assertEquals("after tick5 Hans", 80_000_00, testAccount.getBalance().getAmount().intValue());
		assertEquals("after tick5 Alice", 30_000_00, SweBank.getBalance("Alice").intValue());
	}

	@Test
	public void testAddWithdraw() {
		testAccount.deposit(new Money(10000000, SEK));
		assertEquals("deposit", 20000000, testAccount.getBalance().getAmount().intValue());
		testAccount.withdraw(new Money(10000000, SEK));
		assertEquals("withdraw", 10000000, testAccount.getBalance().getAmount().intValue());
	}
	
	@Test
	public void testGetBalance() {
		assertEquals("Hans", 10000000, testAccount.getBalance().getAmount().intValue());
	}
}
