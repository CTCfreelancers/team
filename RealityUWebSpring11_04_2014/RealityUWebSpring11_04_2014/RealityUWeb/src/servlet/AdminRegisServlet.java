package servlet;
/********************************************************************
 *	RealityUWeb: AdminRegisServlet.java
 *  3/17/2014
 ********************************************************************/
import obj.Administrator;
import dao.AdministratorsDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;

/**
 * Servlet implementation class AdminRegisServlet
 * Admin Registration process, check for existing userid/password
 * Save new userid/password in dbase
 */
@WebServlet("/AdminRegisServlet")
public class AdminRegisServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminRegisServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    //   ==========================  processRequest() Method  ============================
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Declare Variables
        String fname, lname, username, password;
        List<Administrator> lstAdminUserName = new ArrayList<Administrator>();
        List<Administrator> lstAdminPW = new ArrayList<Administrator>();

        try {
        	//Clear form data if "clear" button clicked -- OR -- if parameters don't exist
        	if (request.getParameter("clear") != null || request.getParameter("firstname") == null) {
        		//Set Admin obj to null to clear form
        		//Must name Admin obj different from one in LoginServlet so 
        		//the regis.jsp page doesn't fill in form values from the other obj
            	Administrator adm2 = null;
            	//Put Administrator Obj in Session
                HttpSession ses1 = request.getSession();
                ses1.setAttribute("adm2", adm2);
                System.out.println("Administrator added to Session.");
            	//Return to Registration Page with all data cleared
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/regis.jsp");
                rd.forward(request, response);
                System.out.println("Clear data.");
        	} else { //Process "Submitted" data
        		
	            //Read in Form Data & Cleanup       		
	            fname = cleanupString(request.getParameter("firstname"));
	            lname = cleanupString(request.getParameter("lastname"));
	            username = cleanupString(request.getParameter("userid"));
	            password = cleanupString(request.getParameter("password"));
	            System.out.println("Read in Form Data.");
	            
	            //Create AdministratorDAO & Administrator Objs and Validate Registration
	            AdministratorsDAO adao1 = new AdministratorsDAO();
	            System.out.println("Created AdministratorsDAO obj.");
	            
	            //Search for matches by username
	            //Returns List of Administrators matching search criteria (even if only 1)
	            lstAdminUserName = adao1.search("username", username);  
	            System.out.println("Acquired List of Administrator objs. Num matches User Name: " + lstAdminUserName.size());
	            
	            //Search for matches by password
	            //Returns List of Administrators matching search criteria (even if only 1)
	            lstAdminPW = adao1.search("password", password);  
	            System.out.println("Acquired List of Administrator objs. Num matches Password: " + lstAdminPW.size());
	            
	            if (lstAdminUserName.size() == 0 && lstAdminPW.size() == 0) {
	            	//Valid Registration - No username match and no password match
	            	//Save new Administrator to dbase
	            	adao1.insert(username, password, fname, lname);
	            	//Forward to Admin Home page 
	                RequestDispatcher rd = getServletContext().getRequestDispatcher("/adminhome.jsp");
	                rd.forward(request, response);
	                System.out.println("Yay! Registration Valid.");
	            	
	            } else {
	            	//Invalid Registration - Found duplicate match
	            	//Create Administrator obj to hold user-entered form data values
	            	//Dummy id, don't add to dbase. Key: id 1=dupe userid, 2=dupe pw, 3=dupe both
	            	int keyID = 0;
	            	if (lstAdminUserName.size() != 0) {
	            		//Username duplicated
	            		keyID = 1;
	            	}
	            	if (lstAdminPW.size() != 0) {
	            		//Password duplicated
	            		keyID = 2;
	            	}
	            	if (lstAdminUserName.size() != 0 && lstAdminPW.size() != 0) {
	            		//Both Username & Password duplicated
	            		keyID = 3;
	            	}
	            	//Use keyID in Administrator obj
	            	Administrator adm2 = new Administrator(keyID, username, password, fname, lname);
	            	//Put Administrator Obj in Session
	                HttpSession ses1 = request.getSession();
	                ses1.setAttribute("adm2", adm2);
	                System.out.println("Administrator added to Session.");
	                adm2.display();
	            	//Return to Registration Page to re-enter new data
	                RequestDispatcher rd = getServletContext().getRequestDispatcher("/regis.jsp");
	                rd.forward(request, response);
	                System.out.println("ERROR! Registration Invalid. Try again.");
	            } //end if
        	} //end clear button if
        } catch (Exception e) {
			// Handle Errors
			System.out.println("Error: " + e);
        } //end try
    } //end processRequest
    
    /**
     * Cleanup the String parameter by replacing null with an
     * empty String (if null), or by trimming whitespace from non-null Strings.
     * Return the cleaned up String.
     */
    private String cleanupString(String str) {
        return (str != null) ? str.trim() : "";
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