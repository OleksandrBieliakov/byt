package b_Money;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CurrencyTest {
	Currency SEK, DKK, NOK, EUR;
	
	@Before
	public void setUp() throws Exception {
		/* Setup currencies with exchange rates */
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
	}

	@Test
	public void testGetName() {
		assertEquals("SEK", SEK.getName());
		assertEquals("DKK", DKK.getName());
		assertEquals("EUR", EUR.getName());
	}
	
	@Test
	public void testGetRate() {
		assertEquals("SEK", 0.15, SEK.getRate(), 0.001);
		assertEquals("DKK", 0.20, DKK.getRate(), 0.001);
		assertEquals("EUR", 1.5, EUR.getRate(), 0.001);
	}
	
	@Test
	public void testSetRate() {
		SEK.setRate(0.35);
		DKK.setRate(0.10);
		EUR.setRate(1.8);
		assertEquals("SEK", 0.35, SEK.getRate(), 0.001);
		assertEquals("DKK", 0.10, DKK.getRate(), 0.001);
		assertEquals("EUR", 1.8, EUR.getRate(), 0.001);
	}
	
	@Test
	public void testGlobalValue() {
		assertEquals("SEK", 15, SEK.universalValue(100).intValue());
		assertEquals("DKK", 20, DKK.universalValue(100).intValue());
		assertEquals("EUR", 150, EUR.universalValue(100).intValue());
	}
	
	@Test
	public void testValueInThisCurrency() {
		assertEquals("SEK - EUR", 1000, SEK.valueInThisCurrency(100, EUR).intValue());
		assertEquals("SEK - DKK", 1000, SEK.valueInThisCurrency(750, DKK).intValue());
		assertEquals("DKK - EUR", 750, DKK.valueInThisCurrency(100, EUR).intValue());
	}

}
