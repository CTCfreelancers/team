package servlet;
/********************************************************************
 *	RealityUWeb: ProcessAllSurveysServlet.java
 *  4/28/2014
 ********************************************************************/
import java.util.ArrayList;

import java.util.List;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import processSurveys.ProcessCreditScore;
import processSurveys.ProcessMarried;
import processSurveys.ProcessOccupations;
import dao.GroupsDAO;
import obj.Group;
import dao.SurveysDAO;
import obj.Survey;
import obj.PrintingObj;

/**
 * Servlet implementation class ProcessAllSurveys
 */
@WebServlet("/ProcessAllSurveysServlet")
public class ProcessAllSurveysServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProcessAllSurveysServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    //   ==========================  processRequest() Method  ============================
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      
    	String groupID = ""; //To hold Group ID value if form edited
    	List<Survey> lstSurveys = new ArrayList<Survey>();
    	//Create Group object to put in Session later
    	Group grp = null;
    	String msg = "";
    	//Send back to page to display page differently when true (processed)
    	String isProcessed = null;

        try {
        	
        	//Process Surveys if "submit" button clicked
         	if (request.getParameter("submit") != null) {

	            //Read in Form Data
	            groupID = request.getParameter("groupID");
	            System.out.println("Read in Form Data.");
	            //Create Group from groupID #
	            GroupsDAO gd = new GroupsDAO();
	            grp = gd.find(Integer.parseInt(groupID));
	            
	            //Create SurveysDAO obj
	            SurveysDAO sd = new SurveysDAO();
	            System.out.println("Created SurveysDAO obj.");
	            //Returns List of all Surveys in this Group
	            lstSurveys = sd.search("groupID", groupID);
	            System.out.println("Acquired List of Survey objs. w/ Group ID: " + groupID +". Num of Surveys: "+lstSurveys.size());
	
	            //Call Processing Methods 
	            // no point in doing all the heavy-duty processing if the
	    		// survey group is less than 20 in size.
	    		if (lstSurveys.size() > 19) {
	    			lstSurveys = new ProcessOccupations().doProcess(lstSurveys);
	    			lstSurveys = new ProcessCreditScore().doProcess(lstSurveys);
	    			lstSurveys = new ProcessMarried().doProcess(lstSurveys);
	    			//lstSurveys = new ProcessChildren().doProcess(lstSurveys);
	    			//lstSurveys = new ProcessChildrenDivorcedFemales().doProcess(lstSurveys);
	    			//lstSurveys = new ProcessChildrenDivorcedMales().doProcess(lstSurveys);
	    			//lstSurveys = new ProcessCustodyChildSupport().doProcess(lstSurveys);
	    		
	    			
	    			msg = "Surveys for "+grp.getName()+" have been successfully processed.";
	    			
	    		} else {
	    			lstSurveys = new ProcessOccupations().doProcess(lstSurveys);
	    			lstSurveys = new ProcessCreditScore().doProcess(lstSurveys);
	    			msg = "Surveys for "+grp.getName()+" have been successfully processed to assign Occupations, " +
	    						"Loans, and Credit Scores. Marriage & Children processing have not been done due to having " +
	    						"less than 20 student surveys to process in this group.";
	    			
	    		}
	            
	    		isProcessed = "yes";
	    		//Add Group to session
        		HttpSession ses1 = request.getSession();
                ses1.setAttribute("openGrp", grp);
                ses1.setAttribute("editGroupMsg", msg);
                ses1.setAttribute("isProcessed", isProcessed);
                System.out.println("Group added to Session for 'opengroup.jsp' & isProcessed = "+isProcessed);
	    		
	            //Forward confirmation page
	            RequestDispatcher rd = getServletContext().getRequestDispatcher("/opengroup.jsp");
	            rd.forward(request, response);
	            System.out.println("Surveys PROCESSED.");
            
         	} //end if


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
