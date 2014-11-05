package servlet;
/********************************************************************
 *	RealityUWeb: AdminLoginServlet.java
 *  3/12/2014
 ********************************************************************/
import obj.Administrator;
import obj.Group;
import dao.AdministratorsDAO;
import dao.DAO;

import java.io.IOException;
import java.sql.SQLException;
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
 * Servlet implementation class AdminLoginServlet
 * Admin Login process. Validate userid/password given and
 * forward to valid login or invalid login.
 */
@WebServlet("/AdminLoginServlet")
public class AdminLoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminLoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    //   ==========================  processRequest() Method  ============================
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Declare Variables
        String userID;
        String pw;
        List<Administrator> lstAdmin = new ArrayList<Administrator>();
        Administrator adm = new Administrator();

        try {
            //Read in Form Data & Cleanup
            userID = cleanupString(request.getParameter("userid"));
            pw = cleanupString(request.getParameter("password"));
            System.out.println("Read in Form Data.");
            
            //Create AdministratorDAO obj
            AdministratorsDAO adao1 = new AdministratorsDAO();
            System.out.println("Created AdministratorsDAO obj.");
            //Returns List of Administrators matching search criteria (even if only 1)
            lstAdmin = adao1.search("username", userID); //Lookup by username 
            System.out.println("Acquired List of Administrator objs. Num matches: " + lstAdmin.size());
            
            //Extract single Administrator obj from List
            //Loop thru Administrator List (should only be 1 obj or none in list)
            for (int i = 0; i < lstAdmin.size(); i++)
            {
            	if (i == 0) {
            		adm = lstAdmin.get(i);
            		System.out.println("Extracted Administrator obj from List.");
            	} else { //more than one obj in list
            		System.out.println("Error - Duplicate Username.");
            	} //end if
            } //end for
            
            //Validate Login
            if (adm.validateLogin(userID, pw)) { 
            	
            	//Check if need regular Login or Master Login (for Registration) processing
                //If null, do regular Login processing
                if (request.getParameter("checkMasterLogin") == null) {

		            //Put Administrator Obj in Session
		            HttpSession ses1 = request.getSession();
		            ses1.setAttribute("adm", adm);
		            System.out.println("Administrator added to Session.");
	                //Forward to Admin Home page 
	                RequestDispatcher rd = getServletContext().getRequestDispatcher("/adminhome.jsp");
	                rd.forward(request, response);
	                System.out.println("Yay! Login Valid.");
                
                } else {
                	//Do Master Login (for Registration) processing
                	
                	//Check if login is the Master Login
                	//Both first and last name must equal "Master" to be the Master Login
                	if (adm.getFname().equals("Master") && adm.getLname().equals("Master")) {
	                	//Put Administrator Obj in Session
			            HttpSession ses1 = request.getSession();
			            ses1.setAttribute("adm", adm);
			            System.out.println("Administrator added to Session.");
		                //Forward to Registration page 
		                RequestDispatcher rd = getServletContext().getRequestDispatcher("/regis.jsp");
		                rd.forward(request, response);
		                System.out.println("Yay! Master Login Valid.");
                	} else { //not the Master Login
                    	//Forward to Error Page for Invalid Login
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/invalidLogin.jsp");
                        rd.forward(request, response);
                        System.out.println("ERROR! Master Login Invalid.");
                	} //end if
                	     
                } //end if checkMasterLogin
                              
            } else {
                //Forward to Error Page for Invalid Login
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/invalidLogin.jsp");
                rd.forward(request, response);
                System.out.println("ERROR! Login Invalid.");
            } //end if
            adm.display();
       
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
    	Group expireCheck=new Group();
       
			try {
				expireCheck.DeleteGroup();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("In Delete Part");
		
       
      processRequest(request, response);
  } //end doPost
 
    //Returns a short description of the servlet.
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
