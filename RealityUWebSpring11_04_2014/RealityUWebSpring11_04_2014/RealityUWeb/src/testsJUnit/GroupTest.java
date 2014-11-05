/**
 * 
 */
package testsJUnit;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import obj.Group;
import dao.GroupsDAO;

import org.junit.Test;



/**
 * Test for Group object
 *
 */
public class GroupTest {

	/**
	 * Test method for {@link obj.Group()}.
	 */
	@Test
	public void testGroup() {
		System.out.println("Test Group constructor: default");
		Group instance = new Group();		
		assertNotNull(instance);
	}
	
	/**
	 * Test method for {@link obj.Group()}.
	 */
	@Test
	public void testGroup2() {
		System.out.println("Test Group constructor: with Student Access Code");
		String expResult = "0abcdefghij";
		Group instance = new Group(0, "a", "b", "c", "d", "e", "f", "g", "h", "i", "j");
		String result = instance.getId() + instance.getName() + instance.getCreated() + instance.getModified() + instance.getHighschool() +
				 instance.getTeacher() + instance.getClassPeriod() + instance.getSurveyStartDate() + instance.getSurveyEndDate() + 
				 instance.getEventDate() + instance.getStudentAccessCode();
		assertEquals(expResult, result);
	}
	
	/**
	 * Test method for {@link obj.Group()}.
	 */
	@Test
	public void testGroup3() {
		System.out.println("Test Group constructor: without Student Access Code");
		String expResult = "0abcdefghi";
		Group instance = new Group(0, "a", "b", "c", "d", "e", "f", "g", "h", "i", "J");
		String result = instance.getId() + instance.getName() + instance.getCreated() + instance.getModified() + instance.getHighschool() +
				 instance.getTeacher() + instance.getClassPeriod() + instance.getSurveyStartDate() + instance.getSurveyEndDate() + 
				 instance.getEventDate();
		assertEquals(expResult, result);
	}

	/**
	 * Test method for {@link obj.Group#generateStudentAccessCode()}.
	 */
	@Test
	public void testGenerateStudentAccessCode() {
		System.out.println("Test Group generateStudentAccessCode() method: generate different codes");
		Group g = new Group();		
		String a = g.generateStudentAccessCode();
		String b = g.generateStudentAccessCode();
		System.out.println("Code a: " + a + ", Code b: "+ b);
		boolean expResult = false;
		boolean result = (a.equals(b)) ? true : false;
		assertEquals(expResult, result);
	}

}
