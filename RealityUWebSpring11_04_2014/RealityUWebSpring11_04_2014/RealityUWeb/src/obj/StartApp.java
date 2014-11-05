package obj;


//FOR OPENING URL CONNECTION
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
//import sun.net.www.URLConnection;
//import sun.net.www.protocol.http.HttpURLConnection;



//FOR OPENING BROWSER WINDOW
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;



//FOR LAUNCHING TOMCAT
//import java.io.InputStream;
//import java.io.File;


//FOR MY ORIGINAL CODE, TRYING TO LAUNCH SERVLET
//import java.io.IOException;
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;



public class StartApp {

    /**
     * Uses RequestDispatcher to forward to JSP page specified as parameter 'page'
     */
	//FOR MY ORIGINAL CODE, TRYING TO LAUNCH/GO TO SERVLET, BUT DOESN'T WORK CUZ I NEED request/response PARAMETERS
	//TO FEED TO SERVLET
/*
	public void goToPage(String page, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	//Go to page specified
        RequestDispatcher rd = getServletContext().getRequestDispatcher(page);
        rd.forward(request, response);
        System.out.println("Go to page: " + page);
    }
*/
	
	
	
	public static void main(String[] args) {
		
		//FOR MY ORIGINAL CODE, TRYING TO LAUNCH/GO TO SERVLET, BUT DOESN'T WORK CUZ I NEED request/response PARAMETERS
		//TO FEED TO SERVLET		
/*		//Create obj
		StartApp sa = new StartApp();
		//Go to start page index.jsp
		sa.goToPage("/index.jsp", request, response);
*/
		

		
		
//DIDN'T WORK, SEEMED TO START TOMCAT BUT COULDN'T RUN: TOMCAT SERVER LOG PROBLEM, ACCESS DENIED, ETC.		
/*		String tomcat_path = "C:\\Program Files\\Apache Software Foundation\\Apache Tomcat 7.0.34\\bin\\startup.bat";
		String catalinaHome_path = "C:\\Program Files\\Apache Software Foundation\\Apache Tomcat 7.0.34";
		Runtime runner = Runtime.getRuntime();
		System.out.println("Get Runtime for starting Tomcat.");
		try {
			//CATALINA_HOME environment variable, and either JAVA_HOME OR JRE_HOME environment variables need to be set up
			//Must add these variables on each individual PC via Control Panel>System>Advanced Systems Settings>Advanced Tab: Environment Variables:
			//in System Variables section "New" --> create both variables here: CATALINA_HOME = C:\Program Files\Apache Software Foundation\Apache Tomcat 7.0.34  
			//and JAVA_HOME = C:\Program Files\Java\jdk1.7.0_51
			System.out.println("Catalina Home: " + System.getenv("CATALINA_HOME"));	
			System.out.println("Java Home: " + System.getenv("JAVA_HOME"));
			Process p1 = runner.exec(tomcat_path);
					//Process p1 = runner.exec("cmd /c start " + catalinaHome_path + " run ", null, new File (tomcat_path));
			System.out.println("Create Process obj with tomcat path.");	
			InputStream is = p1.getInputStream();
			System.out.println("Create InputStream.");
			int i = 0;
			while ((i = is.read()) != -1) {
				System.out.print((char)i);
			}
			
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
*/	
		

		
		
		
		//TO RUN WEB APP VIA JAR FILE:
			 //MUST EXPORT AND SAVE ENTIRE APP AS JAVA>JAR FILE, AND SELECT THIS CLASS TO LAUNCH 'MAIN' METHOD
			 //IN LAST 'NEXT' WHEN SAVING AS JAR. TOMCAT SERVER MUST BE STARTED
			 //SEPARATELY FIRST BEFORE RUNNING THE JAR FILE. 
		
			 //TO RUN JAR FILE FROM THE COMMAND WINDOW:
			 //IN COMMAND WINDOW, NAVIGATE TO FOLDER WHERE JAR FILE IS: java -jar RealityUWebApp.jar
			
			 //TO RUN JAR FILE WITHOUT COMMAND WINDOW (WON'T WORK WITH EVERYTHING, WORKS WITH OPENING BROWSER): 
			 //SIMPLY DOUBLE-CLICK .JAR FILE & BROWSER WILL BE LAUNCHED 
		
	
		
		
			 //THIS WORKS: IT WILL SHOW THE CODE FOR THE 'index.jsp' PAGE CALLED FROM THE StartAppServlet IN THE COMMAND WINDOW (NOT IN BROWSER)
			 //THIS WAY ONLY WORKS BY LAUNCHING JAR FILE FROM COMMAND WINDOW.
/*			 try {
			 	System.out.println("Start try block.");

			 	//URL url = new URL("http://localhost/servlet/StartAppServlet");
			 	URL url = new URL("http://localhost:8080/RealityUWeb/StartAppServlet");
			 	System.out.println("URL object created.");

			 	URLConnection conn = url.openConnection();
			 	conn.setDoOutput(true);
			 	System.out.println("Connection created.");

			 	BufferedWriter out = new BufferedWriter( new OutputStreamWriter( conn.getOutputStream() ) );
			 	System.out.println("Buffer OUT created.");
			 	out.write("username=name\r\n");
			 	out.flush();
			 	out.close();
			 	BufferedReader in = new BufferedReader( new InputStreamReader( conn.getInputStream() ) );
			 	System.out.println("Buffer IN created.");

			 	String response;
			 	while ( (response = in.readLine()) != null ) {
			 		System.out.println( "This is the response: " + response );
			 	}
			 	in.close();
			 }
			 catch ( MalformedURLException ex ) {
			 	System.out.println("########"+ex);
			 	ex.printStackTrace();
			 }
			 catch ( IOException ex ) {
			 	System.out.println("@@@@@@@@@@@@@@"+ex); 
			 	ex.printStackTrace();
			 }catch(Exception e){
			 	System.out.println("$$$$$$$$$$$$$$"+e);
			 	e.printStackTrace();
			 }
*/
			 
			 
		

		
		
		
		
			 
			 //LAUNCHES BROWSER (WORKS BY RUNNING FROM COMMAND WINDOW OR SIMPLY CLICKING JAR FILE)
		
			 //Both of these paths work, can go directly to jsp page or use Servlet
			 String url = "http://localhost:8080/RealityUWeb/index.jsp";		 			
			 			//String url = "http://localhost:8080/RealityUWeb/StartAppServlet";

		        if(Desktop.isDesktopSupported()) {
		            Desktop desktop = Desktop.getDesktop();
		            try {
		                desktop.browse(new URI(url));
		            } catch (IOException | URISyntaxException e) {
		                // TODO Auto-generated catch block
		                e.printStackTrace();
		            }
		        } else {
		            Runtime runtime = Runtime.getRuntime();
		            try {
		                runtime.exec("xdg-open " + url);
		            } catch (IOException e) {
		                // TODO Auto-generated catch block
		                e.printStackTrace();
		            }
		        }


			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 


		

	} //end main

} //end class
