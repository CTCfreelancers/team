package servlet;
/********************************************************************
 *	RealityUWeb: EditOccupationServlet.java
 *  4/29/2014
 ********************************************************************/
import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import obj.Occupation;
import dao.GroupsDAO;
import dao.OccupationsDAO;

/**
 * Servlet implementation class EditOccupationServlet
 */
@WebServlet("/EditOccupationServlet")
public class EditOccupationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditOccupationServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	//  ==========================  processRequest() Method  ============================
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//Create OccupationsDao and Occupation Obj
		OccupationsDAO ocd = new OccupationsDAO();
		Occupation editOccp = null;
		System.out.println("Created OccupationsDAO obj.");
		
		String buttonID = "";
		String occupID = "";
		
		String occupsMsg = "";
		String editOccupsMsg = "";
		
		try {
			//Since we're appending the Occupation ID on the button name, we don't know the exact parameter name
			//Get all parameters from form to find out which button was used to submit form
			Enumeration en = request.getParameterNames();
			String[] temp; //array to use for splitting param names
	        while (en.hasMoreElements()) {     
	            String paramName = (String) en.nextElement();
	            System.out.println(paramName + " = " + request.getParameter(paramName));
	            
	            //Split each parameter by "_" underscore (if it has underscore)
	            temp = paramName.split("_");
	            //Search for "edit" or "delete" as index 0 of array
	            if (temp[0].equals("edit")) {
	            	buttonID = "edit";
	            	occupID = temp[1];
	            }
	            if (temp[0].equals("delete")) {
	            	buttonID = "delete";
	            	occupID = temp[1];
	            }
	        } //end while


			//Delete Occupation if "delete" button clicked
			if (buttonID.equals("delete")) {
				//DELETE OCCUPATION

				//Create new Occupation using occupID
				editOccp = ocd.find(Integer.parseInt(occupID));
				//Delete Occupation in dbase
				ocd.delete(editOccp);
				System.out.println("Occupation Deleted.");
				
				occupsMsg = "The occupation was deleted successfully.";
				// Put OccupationsDAO object in Session using HttpSession
				HttpSession ses1;
				ses1 = request.getSession();
				ses1.setAttribute("occupsMsg", occupsMsg);
				System.out.println("occupsMsg added to session");
				
				//Return to occupations.jsp page
				RequestDispatcher dispatcher;
				dispatcher = getServletContext().getRequestDispatcher("/occupations.jsp");
				dispatcher.forward(request, response);
						

			} else { //"edit" button clicked or "submit" button clicked
				//EDIT OCCUPATION OR ADD NEW OCCUPATION
				
				if (request.getParameter("submit")!= null) {
					//Add New Occupation, set Occupation to null (for blank values)
					editOccp = null;
					
				} else {
					//Edit an Occupation
				
					//Create new Occupation using occupID
					editOccp = ocd.find(Integer.parseInt(occupID));
				} //end if
				
				//Reset message String in case used previously
				editOccupsMsg = null;
				
				//Put Occupations object in Session using HttpSession
				HttpSession ses1;
				ses1 = request.getSession();
				ses1.setAttribute("editOccp", editOccp);
				ses1.setAttribute("editOccupsMsg", editOccupsMsg);
				System.out.println("Occupation object added to session");
								
				//Forward to editoccups.jsp page
				RequestDispatcher dispatcher;
				dispatcher = getServletContext().getRequestDispatcher("/editoccups.jsp");
				dispatcher.forward(request, response);

			} //end if

		} catch (Exception e) {
			// Handle Errors
			System.out.println("Error: " + e);
		} //end try   

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
