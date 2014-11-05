package obj;
/********************************************************************
 *	RealityUWeb: Group.java
	Edited by josh 10/5/14
 *  3/21/2014
 ********************************************************************/

/*--================================================-->
<!--Added marriageChoice Object to Code and Working	-->

<!--================================================ */
/********************************************************************
 *	RealityUWeb: Group.java
 *  3/21/2014
 ********************************************************************/
/**
 * The Class Group.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import dao.DbUtil;
import dao.GroupsDAO;

import java.text.*;

public class Group {
    //   ========================== Properties ===========================
	/*********************************
	 * Declarations
	 ********************************/
	private int id;
	private String name;
	private String created; //change to date
	private String modified; //change to date
	private String highschool;
	private String teacher;
	private String classPeriod;  
	private String surveyStartDate; //change to date
	private String surveyEndDate; //change to date
	private String eventDate; //change to date
	private String studentAccessCode;
	/*--========================================-->
	<!--Adding Marriage Choice Radio Button		-->
	<!--           UPDATED CODE                 -->	
	<!--         Created by Josh                -->
	<!--======================================== */
	private String marriageChoice;
	private String coordinatorCode;
	
	//List of all Group id's
    private ArrayList<String> allGroupIds = new ArrayList<String>();

	//   ==========================  Constructors  ========================
	/**
	 * Default Constructor
	 */
	public Group() {
		this.id = 0;
		this.name = "";
		this.created = "";
		this.modified = "";
		this.highschool = "";
		this.teacher = "";
		this.classPeriod = "";  
		this.surveyStartDate = "";
		this.surveyEndDate = "";
		this.eventDate = "";
		this.studentAccessCode = "";
		//change in code = josh
		this.marriageChoice ="";
		this.coordinatorCode="";
	}

	/**
	 * Constructor with all parameters
	 * 
	 * @param id
	 * @param name
	 * @param created
	 * @param modified
	 * @param highschool
	 * @param teacher
	 * @param classPeriod 
	 * @param surveyStartDate
	 * @param surveyEndDate
	 * @param eventDate
	 * @param studentAccessCode
	 */
	public Group(int id, String name, String created, String modified, String highschool, 
			String teacher, String classPeriod, String surveyStartDate, String surveyEndDate,
			String eventDate, String studentAccessCode, String marriageChoice,String coordinatorCode) {
		this.id = id;
		this.name = name;
		this.created = created;
		this.modified = modified;
		this.highschool = highschool;
		this.teacher = teacher;
		this.classPeriod = classPeriod;  
		this.surveyStartDate = surveyStartDate;
		this.surveyEndDate = surveyEndDate;
		this.eventDate = eventDate;
		this.studentAccessCode = studentAccessCode;
		//change in code = josh
		this.marriageChoice = marriageChoice;
		this.coordinatorCode = coordinatorCode;
	}
	
	/**
	 * Constructor with all parameters except studentAccessCode
	 * which is automatically generated via the generateStudentAccessCode() method
	 * 
	 * @param id
	 * @param name
	 * @param created
	 * @param modified
	 * @param highschool
	 * @param teacher
	 * @param classPeriod 
	 * @param surveyStartDate
	 * @param surveyEndDate
	 * @param eventDate
	 */
	public Group(int id, String name, String created, String modified, String highschool, 
			String teacher, String classPeriod, String surveyStartDate, String surveyEndDate,
			String eventDate, String marriageChoice) {
		this.id = id;
		this.name = name;
		this.created = created;
		this.modified = modified;
		this.highschool = highschool;
		this.teacher = teacher;
		this.classPeriod = classPeriod;  
		this.surveyStartDate = surveyStartDate;
		this.surveyEndDate = surveyEndDate;
		this.eventDate = eventDate;
		//change in code = josh
		this.marriageChoice = marriageChoice;
		this.studentAccessCode = generateStudentAccessCode();
		this.coordinatorCode = generateStudentAccessCode();
		
		//Use method to generate random access code
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the created
	 */
	public String getCreated() {
		return created;
	}

	/**
	 * @param created the created to set
	 */
	public void setCreated(String created) {
		this.created = created;
	}

	/**
	 * @return the modified
	 */
	public String getModified() {
		return modified;
	}

	/**
	 * @param modified the modified to set
	 */
	public void setModified(String modified) {
		this.modified = modified;
	}

	/**
	 * @return the highschool
	 */
	public String getHighschool() {
		return highschool;
	}

	/**
	 * @param highschool the highschool to set
	 */
	public void setHighschool(String highschool) {
		this.highschool = highschool;
	}

	/**
	 * @return the teacher
	 */
	public String getTeacher() {
		return teacher;
	}

	/**
	 * @param teacher the teacher to set
	 */
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	/**
	 * @return the classPeriod
	 */
	public String getClassPeriod() {
		return classPeriod;
	}

	/**
	 * @param classPeriod the classPeriod to set
	 */
	public void setClassPeriod(String classPeriod) {
		this.classPeriod = classPeriod;
	}

	/**
	 * @return the surveyStartDate
	 */
	public String getSurveyStartDate() {
		return surveyStartDate;
	}

	/**
	 * @param surveyStartDate the surveyStartDate to set
	 */
	public void setSurveyStartDate(String surveyStartDate) {
		this.surveyStartDate = surveyStartDate;
	}

	/**
	 * @return the surveyEndDate
	 */
	public String getSurveyEndDate() {
		return surveyEndDate;
	}

	/**
	 * @param surveyEndDate the surveyEndDate to set
	 */
	public void setSurveyEndDate(String surveyEndDate) {
		this.surveyEndDate = surveyEndDate;
	}

	/**
	 * @return the eventDate
	 */
	public String getEventDate() {
		return eventDate;
	}

	/**
	 * @param eventDate the eventDate to set
	 */
	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}

	/**
	 * @return the studentAccessCode
	 */
	public String getStudentAccessCode() {
		return studentAccessCode;
	}

/**
 * @param studentAccessCode the studentAccessCode to set
 */
public void setStudentAccessCode(String studentAccessCode) {
	this.studentAccessCode = studentAccessCode;
}
	/*==================================
	 ===================================* 
	 ===================================* 
	 ================================== * 
	 *    Change in code =josh          *
	 ===================================* 
	 ================================= =*/
	public void settmarriageChoice(String d){marriageChoice = d;}	
	public String gettmarriageChoice(){return marriageChoice;}
	
	public String getmarriageChoice() {
		return marriageChoice;
	}

	public void setmarriageChoice(String marriageChoice) {
		this.marriageChoice = marriageChoice;
	}
	
	public String getcoordinatorCode() {
		return coordinatorCode;
	}

/**
 * @param studentAccessCode the studentAccessCode to set
 */
public void setcoordinatorCode(String coordinatorCode) {
	this.coordinatorCode = coordinatorCode;
}
	
	
	/**
	 * @return the allGroupIds
	 */
	public ArrayList<String> getAllGroupIds() {
		return allGroupIds;
	}

	/**
	 * @param allGroupIds the allGroupIds to set
	 */
	public void setAllGroupIds(ArrayList<String> allGroupIds) {
		this.allGroupIds = allGroupIds;
	}
    
	
	
    //   ========================  GENERATE RANDOM ALPHANUMERIC STUDENT ACCESS CODE  ====================
	/**
	 * Generates random Student Access Code, and checks for duplicates in dbase.
	 * 
	 * @param length
	 *            : Sets length of student access code
	 * @return Returns a String student access code.
	 */
	public String generateStudentAccessCode(int length){
			//Use numbers and only upper case letters in code
			String alphabet = new String("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");
			int n = alphabet.length();

			String code = new String(); 
			Random r = new Random();

			//Loop set to 'length' to create code with that many characters
			for (int i=0; i<length; i++) {
				//Generate random character from alphabet
				code += alphabet.charAt(r.nextInt(n));
			} //end for
			System.out.println("Code = " + code);
			
			//Search dbase for match to code
			List<Group> lstGrp = new ArrayList<Group>();
			GroupsDAO gd1 = new GroupsDAO();
			lstGrp = gd1.search("studentAccessCode", code);
			System.out.println("Checked dbase for match to code (0=no match): " + lstGrp.size());
			if (lstGrp.size() != 0) {
            	//Invalid Code - Already used in dbase (found match)
				//Generate new code by calling itself (recursive)
				code = generateStudentAccessCode(length);
				System.out.println("Recursive Code = " + code);
			} //end if
		
			System.out.println("Final Returned Code = " + code);
			return code;
	} //end method
	/**
	 * Generates random Student Access Code, and checks for duplicates in dbase.
	 * Implements method overloading, no length parameter here. Length is set
	 * as default value of 8.
	 * 
	 * @return Returns a String student access code.
	 */
	public String generateStudentAccessCode(){
		//To use as default value for 'length' if don't want to set it
		int length = 8;
		//Use numbers and only upper case letters in code
		String alphabet = new String("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");
		int n = alphabet.length();

		String code = new String(); 
		Random r = new Random();

		//Loop set to 'length' to create code with that many characters
		for (int i=0; i<length; i++) {
			//Generate random character from alphabet
			code += alphabet.charAt(r.nextInt(n));
		} //end for
		System.out.println("Code = " + code);
		
		//Search dbase for match to code
		List<Group> lstGrp = new ArrayList<Group>();
		GroupsDAO gd1 = new GroupsDAO();
		lstGrp = gd1.search("studentAccessCode", code);
		System.out.println("Checked dbase for match to code (0=no match): " + lstGrp.size());
		if (lstGrp.size() != 0) {
        	//Invalid Code - Already used in dbase (found match)
			//Generate new code by calling itself (recursive)
			code = generateStudentAccessCode(length);
			System.out.println("Recursive Code = " + code);
		} //end if
	
		System.out.println("Final Returned Code = " + code);
		return code;
} //end method
	
	
	
	/*--========================================-->
	<!          Delete Expired Group	        -->
	<!--           UPDATED CODE                 -->	
	<!--         Created by Halston             -->
	<!--======================================== */
	

	
	public void DeleteGroup() throws SQLException, ClassNotFoundException
	{
		
		
			Connection conn=null;
			Statement stmt=null;
			Statement st=null;
			Statement st2=null;
			ResultSet rs;
		
			//ArrayList to hold a list of Coordinator Strings
			ArrayList<String> ClogList = new ArrayList<String>();
		
			//The Gregorian Calendar class gives us todays date so that we can compare it with the survey End date
			//and find out which groups have expired
			int day, month, year;
		      GregorianCalendar date = new GregorianCalendar();
		 
		      //methods to get current month day and year
		      day = date.get(Calendar.DAY_OF_MONTH);
		      month = date.get(Calendar.MONTH);
		      year = date.get(Calendar.YEAR);
		     
		      DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		      String todaysDate =(month+1)+"/"+day+"/"+year;
		     
		      
			
			try
			{
				conn = DbUtil.createConnection();
                //This command will select all of the Groups whose survey end date are less than or equal to today
				String sql="select * from Group_";
				stmt = conn.createStatement();
				System.out.println(sql);
				rs=stmt.executeQuery(sql);
				
			
				
				while (rs.next()) 
		   {
				//This pulls the coordinator code from the groups whose survey date is less than todays date
				//so that we can use it to delete the group and	coordinator login its associated with
				String Clog=rs.getString("studentAccessCode");
				String doomsday=rs.getString("surveyEndDate");
				System.out.println(doomsday+" also "+todaysDate);
				Date Dooms=df.parse(doomsday); 
				Date today=df.parse(todaysDate);
				
				if(Dooms.compareTo(today) < 0)
				
	          {
				System.out.println(Dooms +" is less Than"+ today);
				
				//Creates List of expired Coordinatoor Logins so that we can delete group&CoordinatorLogin 
				//that they're associated with	
				ClogList.add(Clog);
		      }
	      }							
				System.out.println(ClogList.size());	
	
       }
			catch (Exception e) {
		// Handle Errors for Class
		System.err.println(e);
	} finally 
	
	{
		// Close ResultSet, Query, and Database Connection
		DbUtil.close(stmt);
		DbUtil.close(conn);
		System.out.println("Closed Resources");
		
		
	 // End Try/Catch
	}
		//Sends the ArrayList of coordinator logins that have expired to get Deleted from database
			Seeya(ClogList);	
}

	//Method to delete the contents of ArrayList
	private void Seeya(ArrayList<String> DoomsList) throws SQLException 
	{
		
		Connection conn=null;
		Statement stmt=null;
		Statement st=null;
		Statement st2=null;
		
		for (int i = 0; i < DoomsList.size(); i++) {
			
		//This deletes the Group that is associated with Expired coordinator
		conn = DbUtil.createConnection();
		String sqldel = "DELETE FROM Group_ WHERE studentAccessCode='"+DoomsList.get(i)+"'";
		st = conn.createStatement();
		System.out.println(sqldel);
		st.executeUpdate(sqldel);
		
		
		//this deletes the coordinator logins from the database that are associated with the expired coordinator
		String sqldel2 = "DELETE FROM CoordinatorLogin WHERE UserName='"+DoomsList.get(i)+"'";
		st2= conn.createStatement();
		System.out.println(sqldel2);
		st2.executeUpdate(sqldel2);
		System.out.println(DoomsList.get(i)+" has been deleted from Coordinator Login");
		}
		
		DbUtil.close(stmt);
		DbUtil.close(conn);
		System.out.println("Closed Resources");	
	
	}

	//   ========================  DISPLAY METHOD  ====================        
	public void display() {
		System.out.println("GroupID\t\t\t= " + getId());
		System.out.println("Group Name\t\t= " + getName());
        System.out.println("Date Created\t\t= " + getCreated());
		System.out.println("Date Modified\t\t= " + getModified());
		System.out.println("High School\t\t= " + getHighschool());
		System.out.println("Teacher\t\t\t= " + getTeacher());
        System.out.println("Class Period\t\t= " + getClassPeriod());
		System.out.println("Survey Start Date\t= " + getSurveyStartDate());
		System.out.println("Survey End Date\t\t= " + getSurveyEndDate());
		System.out.println("Event Date\t\t= " + getEventDate());
		System.out.println("Student Access Code\t= " + getStudentAccessCode());
		//change in code = josh
		System.out.println("marriageChoice\t\t= " + getmarriageChoice());
		System.out.println("coordinatorCode\t\t= " + getcoordinatorCode());
	} //end display()
      
    //   ========================  DISPLAY LIST METHOD  ====================
    /******************************************************
    * Display Group List: display id list for all Groups 
    *****************************************************/
   public void displayGroupList()
   {
       System.out.println("LIST OF GROUPS");
       System.out.println("---------------------------");
           for (int i = 0; i < allGroupIds.size(); i++)
           {
               System.out.println("GroupID "+i+"\t\t= " + allGroupIds.get(i));                    
               System.out.println("---------------------------");
           } //end for
   } //end displayGroupList

    //   ========================  MAIN METHOD  ==================== 
	public static void main(String[] args) {
		//Test generateAccessCdoe() method
		Group g = new Group();
		String a = g.generateStudentAccessCode();
		
		//Test Constructor with auto generated access code
		Group g2 = new Group(1, "a", "a", "a", "a", "a", "a", "a", "a", "a", "a");
		g2.display();
	} //end main()
}

