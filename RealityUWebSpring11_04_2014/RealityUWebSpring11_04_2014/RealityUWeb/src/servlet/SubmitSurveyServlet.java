package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import obj.Group;
import obj.Survey;
import dao.GroupsDAO;
import dao.SurveysDAO;

/**
 * Servlet implementation class SubmitSurveyServlet
 */
@WebServlet("/SubmitSurveyServlet")
public class SubmitSurveyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubmitSurveyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    
    //   ==========================  processRequest() Method  ============================
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      
    	String fName, lName, dob, gpa, gender, education, prefJob, job, married, spouse, children;
    	String numChild, cCards, cCardUses, groceries, clothing, home, vehicle, save, entertainment;
    	//Needed for Processed Surveys
    	String childSupport, creditScore;
    	
    	String category = ""; //To hold industry category value
    	String surveyID = ""; //To hold Survey ID value if form edited
    	String pgID = ""; //To hold pageID to determine if Admin (reg. vs. processed) or Student
    	//Make sure parameter exists or error         	
     	if (request.getParameter("pageID") != null) {
     		pgID = request.getParameter("pageID");
     		System.out.println("Assign parameter value to 'pageID': "+pgID);
     	} //end if

     	//Create SurveysDAO Obj
        SurveysDAO sd1 = new SurveysDAO();
        System.out.println("Created SurveysDAO obj.");
     	
    	 List<Survey> lstSurvey = new ArrayList<Survey>();
    	 Survey s1 = null;
         String surveyMsg = ""; //for return msg in jsp
         
         Group grp = null;
         Group surveyviewGrp = null;
         //Read in Group obj from Session FOR STUDENTS (survey.jsp)
         HttpSession ses = request.getSession();
         if (ses.getAttribute("grp") != null) {
        	    grp = (Group)ses.getAttribute("grp");           
         } //end if 
         //Read in Group obj from Session for ADMIN REGULAR (surveyview.jsp)
         if (ses.getAttribute("surveyviewGrp") != null) {
        	 	surveyviewGrp = (Group)ses.getAttribute("surveyviewGrp");           
         } //end if
         //Read in Group obj from Session for ADMIN PROCESSED (surveyprocessed.jsp)
         if (ses.getAttribute("surveyprocessGrp") != null) {
        	 	surveyviewGrp = (Group)ses.getAttribute("surveyprocessGrp");           
         } //end if
     
         try {
        	//Clear form data if "clear" button clicked -- OR -- if parameters don't exist
         	if (request.getParameter("clear") != null || request.getParameter("firstname") == null) {
         		
         		//Set Survey obj to null to clear form
             	s1 = null;
             	surveyMsg = null; //must include value for Session at end
                System.out.println("Clear data.");
                
         	} else if (request.getParameter("delete") != null) {
         		//ADMIN DELETING SURVEY (Regular or Processed)
         		 
	            //Read in Survey ID
            	surveyID = request.getParameter("surveyID");
            	//Create new Survey using surveyID
            	s1 = sd1.find(Integer.parseInt(surveyID));
            	//Delete Survey in dbase
            	sd1.delete(s1);
            	System.out.println("Survey Deleted.");
            	
            	//Set obj values for Session later
             	s1 = new Survey(); //blank values for form
             	grp = new Group(); //blank values for form
             	surveyMsg = "The Survey has been deleted successfully.";
             	category = "";
             	
		    } else { //Process "Submitted" data
		         		
		            fName = request.getParameter("firstname");		
		        	lName = request.getParameter("lastname");
		        	dob = request.getParameter("dateofbirth");
		        	//gpa' won't exist for Student submitted Surveys
		            gpa = (request.getParameter("gpa") == null) ? "0.0" : request.getParameter("gpa");
		        	
		        	gender = request.getParameter("gender");
		        	education = request.getParameter("education");
		        	//Category/Industry won't exist for Processed Admin Surveys
		        	category = (request.getParameter("industry") == null) ? "" : request.getParameter("industry");
		        	//prefJob won't exist for Processed Admin Surveys
		        	prefJob = (request.getParameter("occupation") == null) ? "" : request.getParameter("occupation");
		        	
		        	//Job won't exist for Student Surveys
		        	job = (request.getParameter("job") == null) ? "" : request.getParameter("job");
		        	married = request.getParameter("married");	
		        	//Spouse won't exist for Student Surveys
		        	spouse = (request.getParameter("spouse") == null) ? "" : request.getParameter("spouse");
		        	children = request.getParameter("children");
		        	numChild = (request.getParameter("numchild") == null) ? "0" : request.getParameter("numchild");
		        	
		        	cCards = request.getParameter("creditcard");
		        	cCardUses = (request.getParameter("cc_use") == null) ? "" : request.getParameter("cc_use");
		        	
		        	//groceries, clothing, home & vehicle won't exist for Processed Admin Surveys
		        	groceries = (request.getParameter("groceries") == null) ? "" : request.getParameter("groceries");		        	
		        	clothing = (request.getParameter("clothing") == null) ? "" : request.getParameter("clothing");
		        	home = (request.getParameter("home") == null) ? "" : request.getParameter("home");
		        	vehicle = (request.getParameter("vehicle") == null) ? "" : request.getParameter("vehicle");
		        	
		        	//childSupport & creditScore won't exist for Student Surveys
		        	childSupport = (request.getParameter("childSupport") == null) ? "" : request.getParameter("childSupport");
		        	creditScore = (request.getParameter("creditScore") == null) ? "" : request.getParameter("creditScore");
		        	
		        	//save & entertainment won't exist for Processed Admin Surveys
		        	save = (request.getParameter("save") == null) ? "" : request.getParameter("save");
		        	entertainment = (request.getParameter("entertain") == null) ? "" : request.getParameter("entertain");
		       	    System.out.println("Read in Form Parameters.");
		            
		            //Get value of Group ID: if ADMIN use 'groupID' from form, if STUDENT use grp object
		            int groupID = (request.getParameter("groupID") == null) ? grp.getId() : Integer.parseInt(request.getParameter("groupID")); 
		            
		            //If "submit" button clicked, not "editSurvey" button --> editSurvey button is null
	            	//Create New Survey
		           
		            String newID = "";
		            System.out.println(""+newID);
		            
	            	//if (request.getParameter("editSurvey") == null) {
	            	
		            
			            //Submit survey
	            		newID=sd1.findStudentID( fName = request.getParameter("firstname"),lName = request.getParameter("lastname"),dob = request.getParameter("dateofbirth"));
	            		 System.out.println(""+newID);
	            		sd1.update(s1);
			            sd1.update(fName, lName, dob, gender, groupID, education, prefJob, "", married, 0, children,
		        		  Integer.parseInt(numChild), cCards, cCardUses,  groceries,  clothing, home, vehicle,
		        		  0.0, 0.0, save, entertainment,Integer.parseInt(newID)); 
			            System.out.println("Updted Survey into dbase.");
			            
			            RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
	                	rd.forward(request, response);
	            	
			            //Lookup obj values from dbase to get id, lookup by dob, and compare to match on dob & last name
			            //Assign matching Survey to s1 object
			           // boolean found = false;
			           // lstSurvey = sd1.search("dob", dob);
			            //for (int i = 0; i < lstSurvey.size(); i++) {
			            	//found = lstSurvey.get(i).validateLogin(dob, lName, fName);
			            	//if (found) {
			            		//s1 = lstSurvey.get(i);
			            	//}
			           // } //end for
			            
				        //Msg displays when return to jsp
	            		fName =request.getParameter("firstname");
				        surveyMsg = "Thank you, " + fName + ". Your survey has been submitted successfully.";
				        System.out.println("New survey submitted.");
				        
	            	//} else { //"editSurvey" button clicked
		            	//Edit Existing Survey: FOR STUDENT (survey.jsp) OR ADMIN REG (surveyview.jsp) OR ADMIN PROCESSED (surveyprocessed.jsp)
		            	System.out.println("EDIT EXISTING SURVEY.");
		            	
		            	//Read in Survey ID
		            	surveyID = request.getParameter("surveyID");
		            	//Get values from current Survey object that are not changed on the form (Reg. vs. Processed)
		            	//and use those values so don't overwrite data needed with blanks
		            	Survey s = sd1.find(Integer.parseInt(surveyID));
		            	System.out.println("EDIT EXISTING SURVEY Entertainment: " + s.getEntertainment()+", Job: "+s.getJob());
		            	
		            	if (pgID.equals("adminSurveyEditProcessed")) {
		            		//PROCESSED SURVEY
		            		//Create new Survey with same surveyID but new/edited values
			            	s1 = new Survey(Integer.parseInt(surveyID), fName, lName, dob, Double.parseDouble(gpa), gender, groupID, education, s.getPrefJob(), 
			            			job, married, Integer.parseInt(spouse), children, Integer.parseInt(numChild), s.getCCards(), s.getCCardUses(), 
			            			s.getGroceries(), s.getClothing(), s.getHome(), s.getVehicle(), Double.parseDouble(childSupport), 
			            			Double.parseDouble(creditScore), s.getSave(), s.getEntertainment());
		            	} else { 
		            		//STUDENT SURVEY OR ADMIN REG SURVEY
		            		//Create new Survey with same surveyID but new/edited values
			            	s1 = new Survey(Integer.parseInt(surveyID), fName, lName, dob, Double.parseDouble(gpa), gender, groupID, education, prefJob, s.getJob(), 
			            			married, s.getSpouse(), children, Integer.parseInt(numChild), cCards, cCardUses, groceries, clothing, home, 
			            			vehicle, s.getChildSupport(), s.getCreditScore(), save, entertainment);
		            	} //end if
		            	
		            	
		            	//Edit existing Survey in dbase with edited values
		            	sd1.update(s1);
		            	System.out.println("Updated existing Survey in dbase.");
	            	
		            	//Msg displays when return to jsp
		            	if (pgID.equals("adminSurveyEdit") || pgID.equals("adminSurveyEditProcessed")) {
		            		//Admin message
		            		surveyMsg = "Thank you. The Survey has been edited successfully.";
		            	} else {
		            		//Student message
		            		surveyMsg = "Thank you, " + s1.getFname() + ". Your Survey has been edited successfully.";
		            	} //end if
		            	System.out.println("Survey Edited.");
	            	} //end if
         		
		     //end if "clear" button
        	
         	//Process differently for Admin Edit of Survey vs. Student Survey Submission/Edit
         	if (pgID.equals("adminSurveyEdit") || pgID.equals("adminSurveyEditProcessed")) {
         		//ADMIN PROCESSING (Reg & Processed)

                if (request.getParameter("delete") != null) {
                	//DELETE SURVEY
                	
                	//Forward to Survey Delete confirmation page
                	RequestDispatcher rd = getServletContext().getRequestDispatcher("/surveydeleted.jsp");
                	rd.forward(request, response);
                } else {
                	//EDIT SURVEY
                	
                	if (pgID.equals("adminSurveyEdit")) {
	                	//REG ADMIN SURVEY: Put Objs in Session
	                 	HttpSession ses1 = request.getSession();
	                 	ses1.setAttribute("viewSurvey", s1);
	                 	ses1.setAttribute("surveyviewGrp", surveyviewGrp);
	                    ses1.setAttribute("surveyMssg", surveyMsg);
	                    ses1.setAttribute("categoryIndustry", category);
	                    System.out.println("Survey added to Session.");
	                	
	                	//Forward to Survey page
	                	RequestDispatcher rd = getServletContext().getRequestDispatcher("/surveyview.jsp");
	                	rd.forward(request, response);
                	} else {
                		//PROCESSED ADMIN SURVEY: Put Objs in Session (different names)
	                 	HttpSession ses1 = request.getSession();
	                 	ses1.setAttribute("processSurvey", s1);
	                 	ses1.setAttribute("surveyprocessGrp", surveyviewGrp);
	                    ses1.setAttribute("surveyProcMsg", surveyMsg);
	                    System.out.println("Survey added to Session.");
	                	
	                	//Forward to Processed Survey page
	                	RequestDispatcher rd = getServletContext().getRequestDispatcher("/surveyprocessed.jsp");
	                	rd.forward(request, response);
                	}
                } //end if
         	} else {
         		//STUDENT PROCESSING

         		//Put Objs in Session
	         	HttpSession ses1 = request.getSession();
	         	ses1.setAttribute("s1", s1);
	         	ses1.setAttribute("grp", grp);
	            ses1.setAttribute("surveyMsg", surveyMsg);
	            ses1.setAttribute("Category", category);
	            System.out.println("Survey added to Session.");
	         	
	            //Forward to Survey page
	            RequestDispatcher rd = getServletContext().getRequestDispatcher("/survey.jsp");
	            rd.forward(request, response);
         	} //end if

            System.out.println("Return to survey page.");

         	} catch (Exception e) {
    			// Handle Errors
    			System.out.println("Error: " + e);
            } //end try
         
        } //end processRequest
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//Calls processRequest() Method
        processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//Calls processRequest() Method
        processRequest(request, response);
	}

}
