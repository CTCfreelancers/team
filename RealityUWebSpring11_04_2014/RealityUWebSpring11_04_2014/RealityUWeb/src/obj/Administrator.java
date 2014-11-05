package obj;

/********************************************************************
 *	RealityUWeb: Adminstrator.java
 *  3/11/2014
 ********************************************************************/
/**
 * The Class Administrator.
 */
import java.util.ArrayList;

import java.util.Date;
import java.util.List;

import dao.AdministratorsDAO;

public class Administrator {
    //   ========================== Properties ===========================
	/*********************************
	 * Declarations
	 ********************************/
	private int id;
	private String username;
	private String password;
	private String fname;
	private String lname;       

	//   ==========================  Constructors  ========================
	/**
	 * 
	 */
	public Administrator() {
		this.id = 0;
		this.username = "";
		this.password = "";
		this.fname = "";
		this.lname = "";
	}

	/**
	 * @param id
	 * @param username
	 * @param password
	 * @param fname
	 * @param lname
	 */
	public Administrator(int id, String username, String password,
			String fname, String lname) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.fname = fname;
		this.lname = lname;
	}

	//   ==========================  Behaviors  ==========================
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}	
	
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

	/**
	 * @return the fname
	 */
	public String getFname() {
		return fname;
	}

	/**
	 * @param fname the fname to set
	 */
	public void setFname(String fname) {
		this.fname = fname;
	}

	/**
	 * @return the lname
	 */
	public String getLname() {
		return lname;
	}

	/**
	 * @param lname the lname to set
	 */
	public void setLname(String lname) {
		this.lname = lname;
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
		System.out.println("AdminID\t\t= " + getId());
		System.out.println("UserName\t= " + getUsername());
        System.out.println("Password\t= " + getPassword());
		System.out.println("First Name\t= " + getFname());
		System.out.println("Last Name\t= " + getLname());
	} //end display()
        

    //   ========================  MAIN METHOD  ==================== 
	public static void main(String[] args) {
		String fieldName = "username";
		String fieldUserValue = "test1";
		String pwdValue = "pwd1";
		List<Administrator> lstAdmin = new ArrayList<Administrator>();
		Administrator adm = new Administrator();
        //Create AdministratorDAO & Administrator Objs and Validate Login
        AdministratorsDAO adao1 = new AdministratorsDAO();
        //Returns List of Administrators matching search criteria (even if only 1)
        lstAdmin = adao1.search(fieldName, fieldUserValue); //Lookup by username 
        //Extract single Administrator obj from List
        //Loop thru Administrator List (should only be 1 obj in list)
        for (int i = 0; i < lstAdmin.size(); i++)
        {
        	if (i == 0) {
        		adm = lstAdmin.get(i);
        		adm.display();
        	} else { //more than one obj in list
        		System.out.println("Error - Duplicate Username.");
        	} //end if
        } //end for
        
        if (adm.validateLogin(fieldUserValue, pwdValue)) {               
            System.out.println("Yay! Login Valid.");
        } else {
            System.out.println("ERROR! Login Invalid.");
        } //end if
		
	} //end main()
}

