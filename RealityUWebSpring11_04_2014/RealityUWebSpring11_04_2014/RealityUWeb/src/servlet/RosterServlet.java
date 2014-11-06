package servlet;

import java.io.IOException;

import dao.SurveysDAO;
import dao.CoordinatorDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class RosterServlet
 */
@WebServlet("/RosterServlet")
public class RosterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RosterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    //   ==========================  processRequest() Method  ============================
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Declare Variables

        try {
        	
        	HttpSession ses1 = request.getSession();
        	int groupid = Integer.parseInt((String) ses1.getAttribute("groupID"));
        	String fName;
        	String lName;
        	String dob;
        	double gpa;
        	int count = 1;
        	while(!(fName= request.getParameter("input"+count)).equals("")){
        		 fName = (request.getParameter("input"+count));
        		 String newfName = fName.substring(0, 1).toUpperCase() + fName.substring(1);	
        		 count ++;
            	 lName = (request.getParameter("input"+count));
            	 String newlName = lName.substring(0, 1).toUpperCase() + lName.substring(1);
            	 count ++;
            	 dob = (request.getParameter("input"+count));
            	 count ++;
            	 gpa = (Double.parseDouble(request.getParameter("input"+count)));
            	 count ++;
            	CoordinatorDAO dao = new CoordinatorDAO();
        		dao.rosterinsert(newfName,newlName,dob,gpa,groupid);
        		System.out.println(count + fName);
        		if ((fName= request.getParameter("input"+count)).equals("")) break;
        		}
        
        
        	
        	RequestDispatcher rd = getServletContext().getRequestDispatcher("/CoordinatorOpenGroup.jsp");
        	rd.forward(request, response);
        } catch(Exception e) {
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
