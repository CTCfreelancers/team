package dao;
/********************************************************************
 *	RealityUWeb:CoordinatorDAO.java

 *	Created by Josh
 *  10/5/2014
 *  
 *  Page for the coordinator login also for finding information
 *  with methods at the bottom of the page
 ********************************************************************/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import obj.Coordinator;
import obj.Group;
import obj.Survey;
import dao.GroupsDAO;

import javax.swing.JOptionPane;


public class CoordinatorDAO implements DAO {

		private Date currentDateTime = new Date();
		
		//  ==========================  SELECT/FIND - LIST OF Coordinators  ==========================
		/**
		 * Gets a List of Administrators by search criteria.
		 * 
		 * @param column
		 *            : The column name in the table to look for.
		 * @param search
		 *            : The term to look for in the column.
		 * @return Returns a List of Administrator objects.
		 */
		public List<Coordinator> search(String column, String search) {
			//was static method --> 'public static List....etc'
			
			//Check Table & Create Table if it doesn't already exist
			boolean success = createTable();
			System.out.println("Check if table exists (create if doesn't exist). Table exists: " + success);

			// Variable Declarations
			Connection conn = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;

			List<Coordinator> login = new ArrayList<Coordinator>();

			String sql = "";
			String strWhere = "";

			try {
				// Load Driver & Connect to Dbase
				conn = DbUtil.createConnection();

				// Create SQL Statement
				if (column != null)
					strWhere = "WHERE " + column + " = '" + search + "'";

				sql = "SELECT * FROM CoordinatorLogin " + strWhere;
				stmt = conn.prepareStatement(sql);
				System.out.println("SQL: " + sql);

				// Execute Statement - Get ResultSet by Column Name
				rs = stmt.executeQuery();

				//Process the ResultSet
				while (rs.next()) {
					Coordinator getlogin = new Coordinator();

					getlogin.setUsername(rs.getString("username"));
					getlogin.setPassword(rs.getString("password"));

					login.add(getlogin);
				}
			} catch (Exception e) {
				// Handle Errors for Class
				System.out.println("Class Error. Current DB: " + DB + e);
			} finally {
				// Close ResultSet, Query, and Database Connection
				DbUtil.close(rs);
				DbUtil.close(stmt);
				DbUtil.close(conn);
				System.out.println("Closed Resources");
			} // End Try/Catch

			return login;
		}

	//  ==========================  SELECT/FIND - ONE ADMINISTRATOR  ==========================	
		/**
		 * Finds an Administrator by id.
		 * 
		 * @param id
		 *            : The Coordinator id to search for.
		 * @return Returns an Coordinator id.
		 */
		public Coordinator find(String login) {
			//Check Table & Create Table if it doesn't already exist
			boolean success = createTable();
			System.out.println("Check if table exists (create if doesn't exist). Table exists: " + success);
			
			// Variable Declarations
			Connection conn = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;
			
			Coordinator newlogin = new Coordinator();

			String sql = "";
			String strWhere = "";

			try {
				// Load Driver & Connect to Dbase
				conn = DbUtil.createConnection();

				// Create SQL Statement
				strWhere = "WHERE UserName = " + login;

				sql = "SELECT * FROM CoordinatorLogin " + strWhere;
				stmt = conn.prepareStatement(sql);
				System.out.println("SQL: " + sql);

				// Execute Statement - Get ResultSet by Column Name
				rs = stmt.executeQuery();

				//Process the ResultSet
				while (rs.next()) {
					newlogin.setUsername(rs.getString("username"));
					newlogin.setPassword(rs.getString("password"));
				}
			} catch (Exception e) {
				// Handle Errors for Class
				System.out.println("Class Error. Current DB: " + DB + e);
			} finally {
				// Close ResultSet, Query, and Database Connection
				DbUtil.close(rs);
				DbUtil.close(stmt);
				DbUtil.close(conn);
				System.out.println("Closed Resources");
			} // End Try/Catch

			return newlogin;
		} //end find

		//  ==========================  UPDATE  ==========================
		/**
		 * Update.
		 * 
		 * @param admin
		 *            : the coordinator being updated
		 * @return Returns an integer:<br>
		 *         0: Failure<br>
		 *         1: Success
		 */
		public int update(Coordinator login) {
			//Check Table & Create Table if it doesn't already exist
			boolean success = createTable();
			System.out.println("Check if table exists (create if doesn't exist). Table exists: " + success);

			// Variable Declarations
			Connection conn = null;
			PreparedStatement stmt = null;
			int rows = 0;

			try {
				// Load Driver & Connect to Dbase
				conn = DbUtil.createConnection();

				// Create SQL Statement
				String sql = "UPDATE CoordinatorLogin SET username=?, password=?" + "WHERE id=?";

				stmt = conn.prepareStatement(sql);
				stmt.setString(1, login.getUsername());
				stmt.setString(2, login.getPassword());
				System.out.println("SQL: " + sql);
				
				//Execute Statement
				rows = stmt.executeUpdate();
			} catch (Exception e) {
				// Handle Errors for Class
				System.out.println("Class Error. Current DB: " + DB + e);
			} finally {
				// Close Query, and Database Connection
				DbUtil.close(stmt);
				DbUtil.close(conn);
				System.out.println("Closed Resources");
			} // End Try/Catch

			return rows;
		}

		//  ==========================  INSERT  ==========================
		/**
		 * Insert a new coordinator.
		 * 

		 * @return Returns an integer:<br>
		 *         0: Failure<br>
		 *         1: Success
		 */
		public void insert(String username, String password) {
			//Check Table & Create Table if it doesn't already exist
			boolean success = createTable();
			System.out.println("Check if table exists (create if doesn't exist). Table exists: " + success);

			// Variable Declarations
			Connection conn = null;
			PreparedStatement stmt = null;

			try {

				// Load Driver & Connect to Dbase
				conn = DbUtil.createConnection();

				// Create SQL Statement
				String sql = "INSERT INTO CoordinatorLogin VALUES('"+ username +"','"+password+"');";
				
				stmt = conn.prepareStatement(sql);
				stmt.executeUpdate();
				System.out.println("SQL: " + sql);

				//Execute Statement
			} catch (Exception e) {
				// Handle Errors for Class
				System.out.println("Class Error. Current DB: " + DB + e);
			} finally {
				// Close Query, and Database Connection
				DbUtil.close(stmt);
				DbUtil.close(conn);
				System.out.println("Closed Resources");
			} // End Try/Catch
		

		}

		//  ==========================  DELETE  ==========================
		/**
		 * Delete an coordinator.
		 * 
		
		 * @return Returns an integer:<br>
		 *         0: Failure<br>
		 *         1: Success
		 */
		public int delete(Coordinator login) {
			//Check Table & Create Table if it doesn't already exist
			boolean success = createTable();
			System.out.println("Check if table exists (create if doesn't exist). Table exists: " + success);

			// Variable Declarations
			Connection conn = null;
			PreparedStatement stmt = null;
			int rows = 0;

			try {
				// Load Driver & Connect to Dbase
				conn = DbUtil.createConnection();

				// Create SQL Statement
				String sql = "DELETE FROM CoordinatorLogin WHERE username = ?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, login.getUsername());
				System.out.println("SQL: " + sql);

				// Execute Statement
				rows = stmt.executeUpdate();

			} catch (Exception e) {
				// Handle Errors for Class
				System.out.println("Class Error. Current DB: " + DB + e);
			} finally {
				// Close Query, and Database Connection
				DbUtil.close(stmt);
				DbUtil.close(conn);
				System.out.println("Closed Resources");
			} // End Try/Catch

			return rows;
		}
		
		// Used to get the teachers name from the coodinator code
		public String findName(String data) {
			Connection conn = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;
		
			Group grp = new Group();
			String sql = "";
			String strWhere = "";
			try {

				// Load Driver & Connect to Dbase
				conn = DbUtil.createConnection();
				// Create SQL Statement
				strWhere = "WHERE coordinatorCode = " + data;
				sql = "SELECT name FROM Group_ " + strWhere;
				stmt = conn.prepareStatement(sql);
				System.out.println("SQL: " + sql);
				rs = stmt.executeQuery();
				while(rs.next()){
				data = rs.getString("name");
				System.out.println(data);
				}
			} catch (Exception e) {
				// Handle Errors for Class
				System.out.println("Class Error. Current DB: " + DB + e);
			} finally {
				// Close ResultSet, Query, and Database Connection
				DbUtil.close(rs);
				DbUtil.close(stmt);
				DbUtil.close(conn);
				System.out.println("Closed Resources");
			} // End Try/Catch
			return String.valueOf(data);
		} //end find
		
		//Used to find the high school name with the coordinator code
		public String findHighschool(String data) {
			Connection conn = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;
		
			Group grp = new Group();
			String sql = "";
			String strWhere = "";
			try {

				// Load Driver & Connect to Dbase
				conn = DbUtil.createConnection();
				// Create SQL Statement
				strWhere = "WHERE coordinatorCode = '" + data+"'";
				sql = "SELECT highschool FROM Group_ " + strWhere;
				stmt = conn.prepareStatement(sql);
				System.out.println("SQL: " + sql);
				rs = stmt.executeQuery();
				while(rs.next()){
				data = rs.getString("highschool");
				System.out.println(data);
				}
			} catch (Exception e) {
				// Handle Errors for Class
				System.out.println("Class Error. Current DB: " + DB + e);
			} finally {
				// Close ResultSet, Query, and Database Connection
				DbUtil.close(rs);
				DbUtil.close(stmt);
				DbUtil.close(conn);
				System.out.println("Closed Resources");
			} // End Try/Catch
			return String.valueOf(data);
		} //end find
		
		//finds the teachers name matching the coordinator code
		public String findteacher(String data) {
			Connection conn = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;
		
			Group grp = new Group();
			String sql = "";
			String strWhere = "";
			try {

				// Load Driver & Connect to Dbase
				conn = DbUtil.createConnection();
				// Create SQL Statement
				strWhere = "WHERE coordinatorCode = '" + data+"'";
				sql = "SELECT teacher FROM Group_ " + strWhere;
				stmt = conn.prepareStatement(sql);
				System.out.println("SQL: " + sql);
				rs = stmt.executeQuery();
				while(rs.next()){
				data = rs.getString("teacher");
				System.out.println(data);
				}
			} catch (Exception e) {
				// Handle Errors for Class
				System.out.println("Class Error. Current DB: " + DB + e);
			} finally {
				// Close ResultSet, Query, and Database Connection
				DbUtil.close(rs);
				DbUtil.close(stmt);
				DbUtil.close(conn);
				System.out.println("Closed Resources");
			} // End Try/Catch
			return String.valueOf(data);
		} //end find
		//This method finds the coordinator code randomly generated in create group
		public String findCoordinatorCode(String data) {
			Connection conn = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;
		
			Group grp = new Group();
			String sql = "";
			String strWhere = "";
			try {

				// Load Driver & Connect to Dbase
				conn = DbUtil.createConnection();
				// Create SQL Statement
				strWhere = "WHERE coordinatorCode = " + data;
				sql = "SELECT coodinatorCode FROM Group_ " + strWhere;
				stmt = conn.prepareStatement(sql);
				System.out.println("SQL: " + sql);
				rs = stmt.executeQuery();
				while(rs.next()){
				data = rs.getString("coodinatorCode");
				System.out.println(data);
				}
			} catch (Exception e) {
				// Handle Errors for Class
				System.out.println("Class Error. Current DB: " + DB + e);
			} finally {
				// Close ResultSet, Query, and Database Connection
				DbUtil.close(rs);
				DbUtil.close(stmt);
				DbUtil.close(conn);
				System.out.println("Closed Resources");
			} // End Try/Catch
			return String.valueOf(data);
		} //end find
		
		//This method used when creating a roster in the createRoster.jsp
		public void rosterinsert(String fName, String lName, String dob, double gpa,int groupid){
			
			//Check Table & Create Table if it doesn't already exist
			boolean success = createTable();
			System.out.println("Check if table exists (create if doesn't exist). Table exists: " + success);
			
			// Variable Declarations
			Connection conn = null;
			PreparedStatement stmt = null;
			

			try {
				// Load Driver & Connect to Dbase
				conn = DbUtil.createConnection();	
				String sql = "INSERT INTO Survey (fName,lName,dob,gpa,groupID) Values ('"+fName+"','"+lName+"','"+dob+"',"+gpa+","+groupid+")";
				stmt = conn.prepareStatement(sql);
				System.out.println("SQL: " + sql);
				stmt.executeUpdate();
				//Execute Statement
			} catch (Exception e) {
				// Handle Errors for Class
				System.out.println("Class Error. Current DB: " + DB + e);
			} finally {
				// Close Query, and Database Connection
				DbUtil.close(stmt);
				DbUtil.close(conn);
				System.out.println("Closed Resources");
			} // End Try/Catch

		}
		
		//This method is used to validate if the student has filled in the survey
		public String findinfo(String data) {
			Connection conn = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;
		
			Group grp = new Group();
			String sql = "";
			String strWhere = "";
			try {

				// Load Driver & Connect to Dbase
				conn = DbUtil.createConnection();
				// Create SQL Statement
				strWhere = "WHERE id = " + data;
				sql = "SELECT gender FROM Survey " + strWhere;
				stmt = conn.prepareStatement(sql);
				System.out.println("SQL: " + sql);
				rs = stmt.executeQuery();
				while(rs.next()){
				data = rs.getString("gender");
				System.out.println(data);
				}
			} catch (Exception e) {
				// Handle Errors for Class
				System.out.println("Class Error. Current DB: " + DB + e);
			} finally {
				// Close ResultSet, Query, and Database Connection
				DbUtil.close(rs);
				DbUtil.close(stmt);
				DbUtil.close(conn);
				System.out.println("Closed Resources");
			} // End Try/Catch
			return String.valueOf(data);
		} //end find

		

		
		
	    //   ========================  MAIN METHOD  ==================== 
		public static void main(String[] args) {		
			List<Coordinator> lstAdmin = new ArrayList<Coordinator>();
			Coordinator adm = new Coordinator();
	        //Create AdministratorDAO & Administrator Objs and Validate Login
			CoordinatorDAO adao1 = new CoordinatorDAO();
	        
	        System.out.println("DB string = " + DAO.DB);
	        
	        //Returns List of Administrators matching search criteria (even if only 1)
	        lstAdmin = adao1.search("username", "test1"); //Lookup by username 
	        //Extract single Administrator obj from List
	        //Loop thru Administrator List (should only be 1 obj in list)
	        for (int i = 0; i < lstAdmin.size(); i++)
	        {
	        	if (i == 0) {
	        		adm = lstAdmin.get(i);
	        		System.out.println("Extracted Administrator obj from List.");
	        		adm.display();
	        	} else { //more than one obj in list
	        		System.out.println("Error - Duplicate Username.");
	        	} //end if
	        } //end for
			
}

		@Override
		public boolean checkTable() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean createTable() {
			// TODO Auto-generated method stub
			return false;
		}
}
