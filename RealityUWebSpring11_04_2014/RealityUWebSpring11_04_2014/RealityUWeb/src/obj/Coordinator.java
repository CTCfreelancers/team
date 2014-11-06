package obj;
/********************************************************************
 *	RealityUWeb:Coordinator.java
 *	Created by Josh
 *  10/5/2014.
 *  Page used for all the get and set methods for the coordinator 
 ********************************************************************/

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dao.CoordinatorDAO;

public class Coordinator {

	 //   ========================== Properties ===========================
		/*********************************
		 * Declarations
		 ********************************/
		private String username;
		private String password;
    

		//   ==========================  Constructors  ========================
		/**
		 * 
		 */
		public Coordinator() {

			this.username = "";
			this.password = "";

		}

		/**
		 * @param username
		 * @param password

		 */
		public Coordinator(String username, String password) {

			this.username = username;
			this.password = password;

		}

		//   ==========================  Behaviors  ==========================


		/**
		 * @return the username
		 */
		public String getUsername() {
			return username;
		}

		/**
		 * @param username the username to set
		 */
		public void setUsername(String username) {
			this.username = username;
		}
		
		/**
		 * @return the password
		 */
		public String getPassword() {
			return password;
		}

		/**
		 * @param password the password to set
		 */
		public void setPassword(String password) {
			this.password = password;
		}


	    //   ==========================  validateLogin() Method ==================
	    public boolean validateLogin(String user, String pw) {
	         boolean found = false;
	         if (user.equals(getUsername()) && pw.equals(getPassword())) {
	             found = true;
	         }
	         return found;
	    } //end validateLogin
	    
	    //   ========================  DISPLAY METHOD  ====================        
		public void display() {
			System.out.println("UserName\t= " + getUsername());
	        System.out.println("Password\t= " + getPassword());
	
		} //end display()
	        

	 
	}

	
