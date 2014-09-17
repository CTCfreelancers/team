package servlet;
/********************************************************************
 *	RealityUWeb: NewGroupServlet.java
 *  3/22/2014
 ********************************************************************/
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import obj.Group;
import dao.GroupsDAO;
import obj.Survey;
import dao.SurveysDAO;

/**
 * Servlet implementation class NewGroupServlet
 * Create a new Group and save to dbase.
 * If 'clear' button clicked on previous page (jsp), it sets Group object to null to clear the form.
 * Otherwise, it processes submitted data for Group, which includes the following:
 * It checks if group name is a duplicate name. If it is not a dupe, or if the 'editGroup' button clicked, 
 * it either creates the New Group and saves it to the database, or Edits the Group.
 * If Editing the Group, it checks again if the group name is duplicated since the name could have been changed.
 * If it is the same name or an unduplicated name, the Group is edited and changes saved to the database.
 * If it matches another group name, user is required to change the name. 
 * If the initial check for group name finds a dupe, user is required to change the name.
 * Regardless of the outcome, user is returned to the originating New Group page, with an appropriate message.
 */
@WebServlet("/NewGroupServlet")
public class NewGroupServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewGroupServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    //   ==========================  processRequest() Method  ============================
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Declare Variables
        String name, highschool, teacher, classPeriod, surveyStartDate, surveyEndDate, eventDate, studentAccessCode;
        List<Group> lstGrpName = new ArrayList<Group>();
        Group newGrp = null;
        Group openGrp = null;
        String newGroupMsg = ""; //for return msg in jsp
        
        //FOR OPEN GROUP PAGE: Declare variables for list of Surveys
        List<Survey> lstSurveys = new ArrayList<Survey>();
        SurveysDAO sd = new SurveysDAO();

    
        
        
        try {
        
             
        	//Clear form data if "clear" button clicked -- OR -- if parameters don't exist (groupid is from 'opengroup.jsp' 
        	//page so know it's not coming from that page which doesn't have a "clear" function
        	if (request.getParameter("clear") != null || 
        			(request.getParameter("name") == null && request.getParameter("groupid") == null)) {
        		
        		//Set Group obj to null to clear form
            	newGrp = null;
            	newGroupMsg = null; //must include as parameter to 'placeInSession' method at end
                System.out.println("Clear data.");
                
        	} else if (request.getParameter("opengroup") != null) { 
        		//PROCESS OPEN GROUP FROM 'opengroup.jsp'
        		
        		//Read in Form Data & cleanup - get Group ID
	            String groupID = cleanupString(request.getParameter("groupid"));
	            //Get Group object from lookup by group id
	            GroupsDAO gd = new GroupsDAO();
	            newGrp = gd.find(Integer.parseInt(groupID));
	            
	            //Create list of all Survey objs in this Group (same GroupID)
	            lstSurveys = sd.search("groupID", groupID); //Use String groupID value for 'search' parameter which is String
	            

        	} else { //Process "Submitted" data
        		
	            //Read in Form Data & cleanup
	            name = cleanupString(request.getParameter("name"));
	            highschool = cleanupString(request.getParameter("highschool"));
	            teacher = cleanupString(request.getParameter("teacher"));
	            classPeriod = cleanupString(request.getParameter("classPeriod"));
	            surveyStartDate = cleanupString(request.getParameter("surveyStartDate"));
	            surveyEndDate = cleanupString(request.getParameter("surveyEndDate"));
	            eventDate = cleanupString(request.getParameter("eventDate"));
	            studentAccessCode = cleanupString(request.getParameter("studentAccessCode"));
	            System.out.println("Read in Form Data.");
	            
	        	/*--========================================-->
	        	<!--Adding Marriage Choice Radio Button		-->
	        	<!--           UPDATED CODE                 -->	
	        	<!--         Created by Josh                -->
	        	<!--========================================  */
	        	String choise =request.getParameter("marriageChoice");
	        	double percent = 0;
	        	HttpSession sesNew = request.getSession();
	    		if(choise .equals("y")){
	    			percent = .45;
	    			 sesNew.setAttribute("choise", percent);
	    			}
	    		else if(choise .equals("n")){
	    			percent = 0;
	    			sesNew.setAttribute("choise", percent);
	    			}
	        	
	            
	            //Create holder Group obj to hold form data values. With id=0 and null for created & modified dates
	            newGrp = new Group(0, name, null, null, highschool, teacher, classPeriod, surveyStartDate, surveyEndDate, eventDate, studentAccessCode);
	            
	            //Create GroupsDAO
	            GroupsDAO gd1 = new GroupsDAO();
	            Group g = new Group();
	            System.out.println("Created GroupsDAO obj.");
	            
	            //Search for matches by Group Name
	            //Returns List of Groups matching search criteria (even if only 1)
	            lstGrpName = gd1.search("name", name);  //Lookup by group name
	            System.out.println("Acquired List of Group objs. Num matches Group Name: " + lstGrpName.size());
	            
	            if (lstGrpName.size() == 0 || (request.getParameter("editGroup") != null && lstGrpName.size() <= 1)) {
	            	//Valid Entry - No group name match --- OR --- Editing a Group (Name could be same, but should not match more than 1)
	            	//If Editing a Group: could be from 'newgroup.jsp' or 'opengroup.jsp'
	            	
	            	//If "submit" button clicked, not "editGroup" button --> editGroup button is null
	            	//Create New Group
	            	if (request.getParameter("editGroup") == null) {
	            		System.out.println("CREATE NEW GROUP.");
		            	//Generate random Student Access Code
		            	studentAccessCode = newGrp.generateStudentAccessCode();
		            	//Create current date for 'created' dbase field
		            	String created = createDate();
		            	System.out.println("Current Date for created field: " + created);
		            	//Save new Group to dbase, set 'modified' date to null
		            	//Need to do this before putting values in Group obj to get the auto generated id 
		            	gd1.insert(name, created, null, highschool, teacher, classPeriod, surveyStartDate, surveyEndDate, eventDate, studentAccessCode);
		            	System.out.println("Inserted new group into dbase.");
		            	//Set Group obj values from dbase to get id, lookup by studentAccessCode 
		            	newGrp = gd1.find(studentAccessCode);
		            	System.out.println("Assigned final values to Group obj.");
		            	//Msg displays when return to jsp
		                newGroupMsg = "The " + newGrp.getName() + " group has been created successfully.";
		                System.out.println("New Group Created.");
	            	} else { //"editGroup" button clicked
	            	//Edit Existing Group ('newgroup.jsp' or 'opengroup.jsp')
	            		System.out.println("EDIT EXISTING GROUP.");            		
		            	
		            	//Lookup Group by studentAccessCode to get the group name, and for later use: id, and created date
		            	g = gd1.find(studentAccessCode); //studentAccessCode value from form (was previously generated on new group creation)		    
		            	System.out.println("Lookup Group by Student Access Code: " + studentAccessCode + ". Group ID: " + g.getId());
		            	
		            	//If there was a match to Group Name, check to make sure it was from
		            	//the same group record (matching itself), and not dupe of another group name
		            	//OR if no match --> lstGrpName.size() == 0 it's a unique name - OK
	            		String groupName = g.getName();
	            		if (groupName.equals(name) || lstGrpName.size() == 0) {
	            			//Valid - process group edit
	            			System.out.println("Group name matches itself or is unique. Okay.");
	            			//Get Group id and created date from 'g' Group, and assign those values to 'newGrp' holder Group
	            			//which contains all other edited data from form values
	            			newGrp.setId(g.getId());
			            	newGrp.setCreated(g.getCreated());
			            	//Create current date for 'modified' dbase field
			            	newGrp.setModified(createDate());
			            	System.out.println("Current Date for modified field: " + newGrp.getModified());
			            	
			            	//Edit existing Group in dbase with edited values
			            	gd1.update(newGrp);
			            	System.out.println("Updated existing group in dbase.");
			            	//Msg displays when return to jsp
			                newGroupMsg = "The " + newGrp.getName() + " group has been edited successfully.";
			                System.out.println("New Group Edited.");
	            			
	            		} else {
	            			//Group Name changed, and matches name from another group - dupe
	            			System.out.println("Group names DON'T match original group created. Matches another group.");
	    	            	//Use Group obj created to hold user-entered form data values
	    	            	//Dummy id, don't add to dbase. Key: id -1=dupe group name (Edited Group)
	            			//Change id to -1
	    	            	newGrp.setId(-1); 	            	
	    	            	//Msg displays when return to jsp
			                newGroupMsg = "The " + newGrp.getName() + " group has NOT been edited successfully. This Group Name is already being used.";
			                System.out.println("ERROR! Group has NOT been edited. Invalid Group Name.");
	            		} //end if
	            		
	            	} //end if
	            	
	            } else {
	            	//Invalid Group Name - Found duplicate match
	            	//Use Group obj 'newGrp' created to hold user-entered form data values
	            	//Dummy id, don't add to dbase. Key: id 0=dupe group name (New Group)
	            	//Id already set to 0 as default
	            	    	
	            	//Msg displays when return to jsp
	                newGroupMsg = "The new group has not yet been created. This Group Name is already being used.";

	                System.out.println("ERROR! New Group not Created. Group Name Invalid. Try again.");
	            } //end if process Submitted data
        	} //end clear button if
        	
        	
        	//PUT OBJS IN SESSION AND RETURN TO PAGE
        	if (request.getParameter("opengroup") != null) { 
        		//USE OBJS FOR OPEN GROUP FROM 'opengroup.jsp' -- TO DISPLAY/OPEN GROUP INFO
        	
        		//Add Group to session, add List of Surveys to session 
        		HttpSession ses1 = request.getSession();
                ses1.setAttribute("openGrp", newGrp); //name Group obj differently
                ses1.setAttribute("lstSurveys", lstSurveys);
                //Have to send and reset to null so doesn't show up if msg sent previously
                newGroupMsg = null;
                ses1.setAttribute("editGroupMsg", newGroupMsg); //name Msg differently
                System.out.println("Group & List of Surveys added to Session for 'opengroup.jsp'.");
        		
        		//Return to Open Group page 
        		goToPage("/opengroup.jsp", request, response);
        		
        	} else if (request.getParameter("pageID") != null) { 
            	//USE OBJS FOR OPEN GROUP FROM 'opengroup.jsp' --> TO EDIT GROUP
        		
        		//Add Group to session
        		HttpSession ses1 = request.getSession();
                ses1.setAttribute("openGrp", newGrp); //name Group obj differently in session
                ses1.setAttribute("editGroupMsg", newGroupMsg); //name Msg differently
                System.out.println("Group added to Session for 'opengroup.jsp'.");
        		
        		//Return to Open Group page 
        		goToPage("/opengroup.jsp", request, response);
        		
        	} else {
        		//USE OBJS FOR NEW GROUP OR EDIT GROUP FROM 'newgroup.jsp'
        		
        		//Put Group Obj in Session
        		placeInSession(newGrp, newGroupMsg, request);
        	
        		//Return to New Group page 
        		goToPage("/newgroup.jsp", request, response);
        	} //end if
        	
        	
        } catch (Exception e) {
			// Handle Errors
			System.out.println("Error: " + e);
        } //end try
    } //end processRequest
    
    
//  #########################################  SERVLET METHODS  #############################################
	
    /**
     * Uses RequestDispatcher to forward to JSP page specified as parameter 'page'
     */
	public void goToPage(String page, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	//Go to page specified
        RequestDispatcher rd = getServletContext().getRequestDispatcher(page);
        rd.forward(request, response);
        System.out.println("Go to page: " + page);
	}

    /**
     * Places object(s) in Session
     */
	public void placeInSession(Group newGrp, String newGroupMsg, HttpServletRequest request) {
      
		//Add Group to session, add newGroupMsg string to session
		HttpSession ses1 = request.getSession();
        ses1.setAttribute("newGrp", newGrp);
        ses1.setAttribute("newGroupMsg", newGroupMsg);
        System.out.println("Group added to Session.");
        if (newGrp != null) {
        		newGrp.display();
        } //end if
	}
	
    /**
     * Cleanup the String parameter by replacing null with an
     * empty String (if null), or by trimming whitespace from non-null Strings.
     * Return the cleaned up String.
     */
    private String cleanupString(String str) {
        return (str != null) ? str.trim() : "";
    }
    
    /**
     * Create a date, format it and return it as a String.
     */
    private String createDate() {
    	//Create current date
    	Date date = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    	return sdf.format(date);
    }
    

    //   ==========================  doGet() Method  ============================
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Calls processRequest() Method
        processRequest(request, response);
    } //end doGet

    //   ==========================  doPost() Method  ============================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Calls processRequest() Method
        processRequest(request, response);
    } //end doPost

    //Returns a short description of the servlet.
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}