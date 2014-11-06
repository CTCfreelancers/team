/**
 * 
 */
package testsJUnit;

import static org.junit.Assert.*;

import org.junit.Test;
import obj.Administrator;

/**
 * Test for Administrator object
 *
 */
public class AdministratorTest {

	/**
	 * Test method for {@link obj.Administrator#validateLogin(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testValidateLogin() {
		System.out.println("Test validateLogin: both match");
		String ur = "jsmith";
		String pw = "pwd2";
		Administrator instance = new Administrator(1, "jsmith", "pwd2", "John", "Smith");
		boolean expResult = true;
		boolean result = instance.validateLogin(ur, pw);
		assertEquals(expResult, result);
	}
	
	@Test
	public void testValidateLogin2() {
		System.out.println("Test validateLogin: one match, one no match");
		String ur = "jsmith";
		String pw = "pwd1";
		Administrator instance = new Administrator(1, "jsmith", "pwd2", "John", "Smith");
		boolean expResult = false;
		boolean result = instance.validateLogin(ur, pw);
		assertEquals(expResult, result);
	}
	
	@Test
	public void testValidateLogin3() {
		System.out.println("Test validateLogin: neither matches");
		String ur = "smith";
		String pw = "pwd1";
		Administrator instance = new Administrator(1, "jsmith", "pwd2", "John", "Smith");
		boolean expResult = false;
		boolean result = instance.validateLogin(ur, pw);
		assertEquals(expResult, result);
	}


}
