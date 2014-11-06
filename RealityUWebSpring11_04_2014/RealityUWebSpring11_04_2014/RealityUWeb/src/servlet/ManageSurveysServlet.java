package servlet;
/********************************************************************
 *	RealityUWeb: ManageSurveysServlet.java
 *  4/19/2014
 ********************************************************************/
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
import dao.GroupsDAO;
import obj.Survey;
import dao.SurveysDAO;
import obj.Occupation;
import dao.OccupationsDAO;

/**
 * Servlet implementation class ManageSurveysServlet
 */
@WebServlet("/ManageSurveysServlet")
public class ManageSurveysServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageSurveysServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    
    //   ==========================  processRequest() Method  ============================
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
    	
    	String category = ""; //To hold industry category value
    	String surveyID = ""; //To hold Survey ID value

    	List<Survey> lstSurvey = new ArrayList<Survey>();
    	Survey viewSurvey = null;
    	String surveyMssg = null; //for return msg in jsp       

    	Group surveyviewGrp = null;
    	//Read in Group obj from Session
    	HttpSession ses = request.getSession();
    	if (ses.getAttribute("surveyviewGrp") != null) {
    		surveyviewGrp = (Group)ses.getAttribute("surveyviewGrp");           
    	} //end if 

    	try {
    		//Delete Survey if "delete" button clicked
    		if (request.getParameter("delete") != null) {
    			//DELETE SURVEY

    			//Create SurveysDAO Obj
    			SurveysDAO sd1 = new SurveysDAO();
    			System.out.println("Created SurveysDAO obj.");

    			//Read in Survey ID
    			surveyID = request.getParameter("surveyID");
    			//Create new Survey using surveyID
    			viewSurvey = sd1.find(Integer.parseInt(surveyID));
    			//Delete Survey in dbase
    			sd1.delete(viewSurvey);
    			System.out.println("Survey Deleted.");

    			//No message, will update list of student surveys for group with this one deleted
    			//surveyMssg = "The Survey has been deleted successfully.";

    			//Update opengroup page, so it doesn't show deleted survey          	
    			//Get Group object from lookup by group id
    			GroupsDAO gd = new GroupsDAO();
    			surveyviewGrp = gd.find(viewSurvey.getGroupID()); //Get Group ID from deleted Survey

    			//Declare variables for list of Surveys
    			List<Survey> lstSurveys = new ArrayList<Survey>();
    			SurveysDAO sd = new SurveysDAO();
    			//Create list of all Survey objs in this Group (same GroupID)
    			lstSurveys = sd.search("groupID", (""+viewSurvey.getGroupID())); //Use String groupID value for 'search' parameter which is String

    			//Add Group to session, add List of Surveys to session 
    			HttpSession ses1 = request.getSession();
    			ses1.setAttribute("openGrp", surveyviewGrp); //name Group obj differently
    			ses1.setAttribute("lstSurveys", lstSurveys);
    			//Have to send and reset to null so doesn't show up if msg sent previously
    			String msg = null;
    			ses1.setAttribute("editGroupMsg", msg); //name Msg differently
    			System.out.println("Group & List of Surveys added to Session for 'opengroup.jsp'.");

    			//Forward to page
    			RequestDispatcher rd = getServletContext().getRequestDispatcher("/opengroup.jsp");
    			rd.forward(request, response);
    			System.out.println("Go to Survey Deleted confirmation page.");

    		} else if (request.getParameter("view") != null || request.getParameter("processed") != null) {
    			//VIEW INDIVIDUAL STUDENT SURVEY BY surveyID (REGULAR OR PROCESSED SURVEYS)
    			surveyID = request.getParameter("surveyID");

    			//Create SurveysDAO Obj
    			SurveysDAO sd1 = new SurveysDAO();
    			System.out.println("Created SurveysDAO obj.");
    			//Lookup Survey obj by surveyID
    			viewSurvey = sd1.find(Integer.parseInt(surveyID));
    			System.out.println("Create Survey: "+viewSurvey.getFname());

    			//Create GroupsDAO Obj
    			GroupsDAO gd1 = new GroupsDAO();
    			System.out.println("Created GroupsDAO obj.");
    			//Lookup Group obj by groupID in Survey obj
    			surveyviewGrp = gd1.find(viewSurvey.getGroupID());
    			System.out.println("Create Group: "+surveyviewGrp.getName());

    			if (request.getParameter("view") != null) {
    				//VIEW REGULAR SURVEY (unprocessed)
    				
    				//Create OccupationsDAO Obj
	    			OccupationsDAO od1 = new OccupationsDAO();
	    			System.out.println("Created OccupationsDAO obj.");
	    			List<Occupation> lstOccup = new ArrayList<Occupation>();
	    			//Lookup Category(industry) based on Survey prefJob
	    			lstOccup = od1.search("name", viewSurvey.getPrefJob());
	    			//Get Category from Occupation object, should only be 1 obj in list for this job
	    			category = lstOccup.get(0).getCategory();	            
	    			System.out.println("Extracted Category: "+category);
	
	    			//Put Objs in Session
	    			HttpSession ses1 = request.getSession();
	    			ses1.setAttribute("viewSurvey", viewSurvey);
	    			ses1.setAttribute("surveyviewGrp", surveyviewGrp);
	    			ses1.setAttribute("surveyMssg", surveyMssg);
	    			ses1.setAttribute("categoryIndustry", category);
	    			
	    			
	    			System.out.println("Survey & Group added to Session.");
	
	    			//Forward to page
	    			RequestDispatcher rd = getServletContext().getRequestDispatcher("/surveyview.jsp");
	    			rd.forward(request, response);
	    			System.out.println("Go to View Survey page in new window.");
    			} else { 
    				//VIEW PROCESSED SURVEY

	    			//Put Objs in Session
	    			HttpSession ses1 = request.getSession();
	    			ses1.setAttribute("processSurvey", viewSurvey);
	    			ses1.setAttribute("surveyprocessGrp", surveyviewGrp);
	    			ses1.setAttribute("surveyProcMsg", surveyMssg);
	    			System.out.println("Survey & Group added to Session.");
	
	    			//Forward to page
	    			RequestDispatcher rd = getServletContext().getRequestDispatcher("/surveyprocessed.jsp");
	    			rd.forward(request, response);
	    			System.out.println("Go to View PROCESSED Survey page in new window.");
    				
    			} //end if Regular vs Processed Survey

    	} //end if for button selected  

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
