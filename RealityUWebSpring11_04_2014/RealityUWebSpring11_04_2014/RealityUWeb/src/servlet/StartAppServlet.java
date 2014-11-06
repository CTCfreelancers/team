package servlet;
/********************************************************************
 *	RealityUWeb: StartAppServlet.java
 *  4/26/2014
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
 * Servlet implementation class StartAppServlet
 * 
 */
@WebServlet("/StartAppServlet")
public class StartAppServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public StartAppServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    //   ==========================  processRequest() Method  ============================
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Declare Variables
        

        try {
        	//Forward to index page
        	RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
        	rd.forward(request, response);
        	
        	
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
