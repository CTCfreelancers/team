package servlet;
/********************************************************************
 *	RealityUWeb: AddOccupationServlet.java
 *  5/1/2014
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

import obj.Occupation;
import dao.OccupationsDAO;

/**
 * Servlet implementation class AddOccupationServlet
 */
@WebServlet("/AddOccupationServlet")
public class AddOccupationServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddOccupationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    //   ==========================  processRequest() Method  ============================
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
    	//Declare Variables
    	String name, type, industry, category;
    	String annGrossSal, monGrossSal, marAnnualTax, marMonthlyTax, marAfterTax, sinAnnualTax, sinMonthlyTax, sinAfterTax; 
    	String gpaCategory, loan; 

    	String occupID = "";
    	String editOccupsMsg = ""; //ms for editoccups page
    	String occupsMsg = null; //msg for occupations page --> need to clear out when Occupations table refreshed

    	//Create OccupationsDao and Occupation Obj
    	OccupationsDAO ocd = new OccupationsDAO();
    	Occupation editOccp = null;
    	System.out.println("Created OccupationsDAO & Occupation obj.");

    	try {
    		
    		//Clear form data if "clear" button clicked -- OR -- if parameters don't exist
        	if (request.getParameter("clear") != null || request.getParameter("name") == null) {
        		
        		//Set Occupation obj to null to clear form
        		editOccp = null;
        		editOccupsMsg = null;
                System.out.println("Clear data.");
                
        	} else {
        		//EDIT OR ADD NEW OCCUPATION

	    		//Read in edited/new Form Data 
	    		//'occupID' won't exist for New Occupations submitted
	        	occupID = (request.getParameter("occpsID") == null) ? "0" : request.getParameter("occpsID");
	    		name = request.getParameter("name");		
	    		type = request.getParameter("type");
	    		industry = request.getParameter("industry");
	    		category= request.getParameter("category");
	
	    		annGrossSal= request.getParameter("annGrossSal");
	    		monGrossSal = request.getParameter("monGrossSal");
	    		marAnnualTax = request.getParameter("marAnnualTax");
	    		marMonthlyTax= request.getParameter("marMonthlyTax");
	
	    		marAfterTax = request.getParameter("marAfterTax");		
	    		sinAnnualTax = request.getParameter("sinAnnualTax");
	    		sinMonthlyTax = request.getParameter("sinMonthlyTax");
	    		sinAfterTax = request.getParameter("sinAfterTax");
	    		gpaCategory = request.getParameter("gpaCategory");	
	    		loan = request.getParameter("loan");
	
	    		if (request.getParameter("editOccp") != null) {
	    			//EDIT OCCUPATION
	    			System.out.println("EDIT occupation.");

	    			//Create Occupation
	    			editOccp = new Occupation(Integer.parseInt(occupID), name, type, industry, category, Double.parseDouble(annGrossSal), 
	    					Double.parseDouble(monGrossSal), Double.parseDouble(marAnnualTax), Double.parseDouble(marMonthlyTax), 
	    					Double.parseDouble(marAfterTax), Double.parseDouble(sinAnnualTax), Double.parseDouble(sinMonthlyTax), 
	    					Double.parseDouble(sinAfterTax), Integer.parseInt(gpaCategory), Double.parseDouble(loan));
	
	    			//Edit existing occupation in dbase with edited values
	    			ocd.update(editOccp);
	    			System.out.println("Updated existing Occupation in dbase.");
	
	    			editOccupsMsg = "The occupation was edited successfully.";

	    		} else {
	    			//ADD NEW OCCUPATION	
	    			System.out.println("CREATE NEW occupation.");
	
	    			//Enter new Occupation in dbase with form data values 
	    			ocd.insert(name, type, industry, category, Double.parseDouble(annGrossSal), Double.parseDouble(monGrossSal),
	    					Double.parseDouble(marAnnualTax), Double.parseDouble(marMonthlyTax), Double.parseDouble(marAfterTax), 
	    					Double.parseDouble(sinAnnualTax), Double.parseDouble(sinMonthlyTax), Double.parseDouble(sinAfterTax),
	    					Integer.parseInt(gpaCategory), Double.parseDouble(loan));
	    			
	    			//Create Occupation List
	    			List<Occupation> lstOccp = new ArrayList<Occupation>();
	    			//Create Occupation obj by using values from dbase --> to get id, lookup by name (which should be unique) 
	    			lstOccp = ocd.search("name", name); 
	    			//Create Occupation obj: Occupation List should only have one value, so use index 0
	    			editOccp = lstOccp.get(0);
	
	    			editOccupsMsg = "The new occupation was created successfully.";
	
	    		} //end if
	    		
        	} //end outer if
        	
        	//For all scenarios:
        	//Put Occupation object in Session using HttpSession
			HttpSession ses1;
			ses1 = request.getSession();
			ses1.setAttribute("editOccp", editOccp);
			ses1.setAttribute("editOccupsMsg", editOccupsMsg);
			ses1.setAttribute("occupsMsg", occupsMsg);
			System.out.println("Occupation object added to session");

			//Forward to editoccups.jsp page
			RequestDispatcher dispatcher;
			dispatcher = getServletContext().getRequestDispatcher("/editoccups.jsp");
			dispatcher.forward(request, response);
			System.out.println("Go to editoccups page.");

    	} catch (Exception e) {
    		// Handle Errors
    		System.out.println("Error: " + e);
    	} //end try
    } //end processRequest


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