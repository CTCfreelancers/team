package testsJUnit;

import static org.junit.Assert.*;
import obj.Occupation;

import org.junit.Test;

public class OccupationTest {

	/**
	 * Test method for {@link obj.Occupation()}.
	 */
	@Test
	public void testOccupation() {
		System.out.println("Test Occupation constructor: default");
		Occupation instance = new Occupation();		
		assertNotNull(instance);
	}
	
	/**
	 * Test method for {@link obj.Occupation()}.
	 */
	@Test
	public void testOccupation2() {
		System.out.println("Test Occupation constructor: with values");
		String expResult = "0abcd0.01.12.23.34.45.56.67.789.9";
		Occupation instance = new Occupation(0, "a", "b", "c", "d", 0.0, 1.1, 2.2, 3.3, 4.4, 5.5, 6.6, 7.7, 8, 9.9);
		String result = instance.getId() + instance.getName() + instance.getType() + instance.getIndustry() + instance.getCategory() +
				 instance.getAnnGrossSal() + instance.getMonGrossSal() + instance.getMarAnnualTax() + instance.getMarMonthlyTax() + 
				 instance.getMarAfterTax() + instance.getSinAnnualTax() + instance.getSinMonthlyTax() + instance.getSinAfterTax() +
				 instance.getGpaCategory() + instance.getLoan();
		assertEquals(expResult, result);
	}
	

}
