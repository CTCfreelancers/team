package testsJUnit;

import static org.junit.Assert.*;
import obj.Survey;

import org.junit.Test;

public class SurveyTest {

	/**
	 * Test method for {@link obj.Survey()}.
	 */
	@Test
	public void testGroup2() {
		System.out.println("Test Survey constructor");
		String expResult = "0abc0.0e2ghij3n0opqrst0.01.1uv";
		Survey instance = new Survey(0, "a", "b", "c", 0.0, "e", 2, "g", "h", "i", "j", 3, "n", 0, "o", "p", "q", "r", "s", "t", 0.0, 1.1, "u", "v");
		String result = instance.getId() + instance.getFname() + instance.getLname() + instance.getDob() + instance.getGpa() +
				instance.getGender() + instance.getGroupID() + instance.getEducation() + instance.getPrefJob() + instance.getJob() +
				instance.getMarried() + instance.getSpouse() + instance.getChildren() + instance.getNumChild() +
				instance.getCCards() + instance.getCCardUses() + instance.getGroceries() + instance.getClothing() + instance.getHome() +
				instance.getVehicle() + instance.getChildSupport() + instance.getCreditScore() + instance.getSave() + instance.getEntertainment();
		assertEquals(expResult, result);
	}

}
