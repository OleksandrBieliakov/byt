package b_Money;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MoneyTest {
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
	}

	@Test
	public void testGetAmount() {
		assertEquals("SEK100", 10000, SEK100.getAmount().intValue());
		assertEquals("EUR10", 1000, EUR10.getAmount().intValue());
		assertEquals("SEK200", 20000, SEK200.getAmount().intValue());
		assertEquals("EUR20", 2000, EUR20.getAmount().intValue());
		assertEquals("SEK0", 0, SEK0.getAmount().intValue());
		assertEquals("EUR0", 0, EUR0.getAmount().intValue());
		assertEquals("SEKn100", -10000, SEKn100.getAmount().intValue());
	}

	@Test
	public void testGetCurrency() {
		assertEquals("SEK100", SEK, SEK100.getCurrency());
		assertEquals("EUR10", EUR, EUR10.getCurrency());
	}

	@Test
	public void testToString() {
		assertEquals("100.0 SEK", SEK100.toString());
		assertEquals("10.0 EUR", EUR10.toString());
		assertEquals("0 SEK", SEK0.toString());
		assertEquals("-100.0 SEK", SEKn100.toString());
	}

	@Test
	public void testGlobalValue() {
		assertEquals("SEK100", 1500, SEK100.universalValue().intValue());
		assertEquals("SEK0", 0, SEK0.universalValue().intValue());
		assertEquals("SEKn100", -1500, SEKn100.universalValue().intValue());
	}

	@Test
	public void testEqualsMoney() {
		assertTrue("SEK0 - EUR0", SEK0.equals(EUR0));
		assertTrue("SEK100 - EUR10", SEK100.equals(EUR10));
		assertFalse("SEK100 - EUR20", SEK100.equals(EUR20));
	}

	@Test
	public void testAdd() {
		assertEquals("SEK100 + SEK200", 30000, SEK100.add(SEK200).getAmount().intValue());
		assertEquals("EUR20 + SEK100", 3000, EUR20.add(SEK100).getAmount().intValue());
		assertEquals("SEK100 + SEKn100", 0, SEK100.add(SEKn100).getAmount().intValue());
	}

	@Test
	public void testSub() {
		assertEquals("SEK100 - SEK200", -10000, SEK100.sub(SEK200).getAmount().intValue());
		assertEquals("SEK100 - EUR20", -10000, SEK100.sub(EUR20).getAmount().intValue());
		assertEquals("EUR0 - SEKn100", 1000, EUR0.sub(SEKn100).getAmount().intValue());
	}

	@Test
	public void testIsZero() {
		assertFalse("SEK100", SEK100.isZero());
		assertTrue("SEK0", SEK0.isZero());
		assertTrue("EUR0", EUR0.isZero());
	}

	@Test
	public void testNegate() {
		assertEquals("EUR20", -2000, EUR20.negate().getAmount().intValue());
		assertEquals("SEKn100", 10000, SEKn100.negate().getAmount().intValue());
		assertEquals("SEK0", 0, SEK0.negate().getAmount().intValue());
	}

	@Test
	public void testCompareTo() {
		assertTrue("EUR20 > SEK100", EUR20.compareTo(SEK100) > 0);
		assertTrue("SEKn100 < EUR0", SEKn100.compareTo(EUR0) < 0);
		assertTrue("SEK100 == EUR10", SEK100.compareTo(EUR10) == 0);
	}
}
