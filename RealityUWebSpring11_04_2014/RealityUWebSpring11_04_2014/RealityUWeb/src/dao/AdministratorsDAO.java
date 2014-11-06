package dao;
/********************************************************************
 *	RealityUWeb: AdminstratorsDAO.java

 *  3/11/2014
 ********************************************************************/
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

//import ctrl.Controller;







import obj.Administrator;
import dao.DAO;
import dao.DbUtil;

/**
 * The Class AdministratorsDAO retrieves data from the "Administrators" table.
 */
public class AdministratorsDAO implements DAO {

	private Date currentDateTime = new Date();
	
	//  ==========================  SELECT/FIND - LIST OF ADMINISTRATORS  ==========================
	/**
	 * Gets a List of Administrators by search criteria.
	 * 
	 * @param column
	 *            : The column name in the table to look for.
	 * @param search
	 *            : The term to look for in the column.
	 * @return Returns a List of Administrator objects.
	 */
	public List<Administrator> search(String column, String search) {
		//was static method --> 'public static List....etc'
		
		//Check Table & Create Table if it doesn't already exist
		boolean success = createTable();
		System.out.println("Check if table exists (create if doesn't exist). Table exists: " + success);

		// Variable Declarations
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		List<Administrator> lstAdmin = new ArrayList<Administrator>();

		String sql = "";
		String strWhere = "";

		try {
			// Load Driver & Connect to Dbase
			conn = DbUtil.createConnection();

			// Create SQL Statement
			if (column != null)
				strWhere = "WHERE " + column + " = '" + search + "'";

			sql = "SELECT * FROM Administrator " + strWhere;
			stmt = conn.prepareStatement(sql);
			System.out.println("SQL: " + sql);

			// Execute Statement - Get ResultSet by Column Name
			rs = stmt.executeQuery();

			//Process the ResultSet
			while (rs.next()) {
				Administrator admin = new Administrator();

				admin.setId(rs.getInt("id"));
				admin.setUsername(rs.getString("username"));
				admin.setPassword(rs.getString("password"));
				admin.setFname(rs.getString("fname"));
				admin.setLname(rs.getString("lname"));

				lstAdmin.add(admin);
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

		return lstAdmin;
	}

//  ==========================  SELECT/FIND - ONE ADMINISTRATOR  ==========================	
	/**
	 * Finds an Administrator by id.
	 * 
	 * @param id
	 *            : The Administrator id to search for.
	 * @return Returns an Administrator object with that id.
	 */
	public Administrator find(int id) {
		//Check Table & Create Table if it doesn't already exist
		boolean success = createTable();
		System.out.println("Check if table exists (create if doesn't exist). Table exists: " + success);
		
		// Variable Declarations
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		Administrator admin = new Administrator();

		String sql = "";
		String strWhere = "";

		try {
			// Load Driver & Connect to Dbase
			conn = DbUtil.createConnection();

			// Create SQL Statement
			strWhere = "WHERE id = " + id;

			sql = "SELECT * FROM Administrator " + strWhere;
			stmt = conn.prepareStatement(sql);
			System.out.println("SQL: " + sql);

			// Execute Statement - Get ResultSet by Column Name
			rs = stmt.executeQuery();

			//Process the ResultSet
			while (rs.next()) {
				admin.setId(rs.getInt("id"));
				admin.setUsername(rs.getString("username"));
				admin.setPassword(rs.getString("password"));
				admin.setFname(rs.getString("fname"));
				admin.setLname(rs.getString("lname"));
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

		return admin;
	} //end find

	//  ==========================  UPDATE  ==========================
	/**
	 * Update.
	 * 
	 * @param admin
	 *            : the Administrator being updated
	 * @return Returns an integer:<br>
	 *         0: Failure<br>
	 *         1: Success
	 */
	public int update(Administrator admin) {
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
			String sql = "UPDATE Administrator SET username=?, password=?, fname=?, lname=? " + "WHERE id=?";

			stmt = conn.prepareStatement(sql);
			stmt.setString(1, admin.getUsername());
			stmt.setString(2, admin.getPassword());
			stmt.setString(3, admin.getFname());
			stmt.setString(4, admin.getLname());
			stmt.setInt(5, admin.getId());
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
	 * Insert a new Administrator.
	 * 
	 * @param username
	 *            : The Administrator username
	 * @param password
	 *            : The Administrator password
	 * @param fname
	 *            : The Administrator fname
	 * @param lname
	 *            : The Administrator lname
	 * @return Returns an integer:<br>
	 *         0: Failure<br>
	 *         1: Success
	 */
	public int insert(String username, String password, String fname, String lname) {
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
			String sql = "INSERT INTO Administrator (username, password, fname, lname) " + "VALUES (?,?,?,?)";

			stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			stmt.setString(2, password);
			stmt.setString(3, fname);
			stmt.setString(4, lname);
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

	//  ==========================  DELETE  ==========================
	/**
	 * Delete an Administrator.
	 * 
	 * @param admin
	 *            : The Administrator to delete
	 * @return Returns an integer:<br>
	 *         0: Failure<br>
	 *         1: Success
	 */
	public int delete(Administrator admin) {
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
			String sql = "DELETE FROM Administrator WHERE id = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, admin.getId());
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
	
	
	//  ==========================  CHECK TABLE  ==========================
	/**
	 * This method makes sure table exists<br>
	 * 
	 * @return Returns True/False
	 */
	@Override
	public boolean checkTable() {
		String tableName = "Administrator";
		boolean found = false;
		found = DbUtil.checkTable(tableName);
		return found;
	}
	
	//  ==========================  CREATE TABLE IF DOESN'T EXIST  ==========================
	/**
	 * Creates a table if it doesn't exist
	 * 
	 * @return Returns True/False
	 */
	@Override
	public boolean createTable() {
		boolean success = true;
		String tableName = "Administrator";
		
		// Create SQL Statement
		String sql = "CREATE TABLE IF NOT EXISTS " + tableName
				+ " ('id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
				+ "'username' VARCHAR NOT NULL,"
				+ "'password' VARCHAR NOT NULL,"
				+ "'fname' VARCHAR,"
				+ "'lname' VARCHAR)";
		
		success = DbUtil.createTable(tableName, sql);
		
		return success;
	}
	
	
    //   ========================  MAIN METHOD  ==================== 
	public static void main(String[] args) {		
		List<Administrator> lstAdmin = new ArrayList<Administrator>();
		Administrator adm = new Administrator();
        //Create AdministratorDAO & Administrator Objs and Validate Login
        AdministratorsDAO adao1 = new AdministratorsDAO();
        
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
		
        //Test find
//        Administrator adm = new Administrator();
//        AdministratorsDAO adao1 = new AdministratorsDAO();        
//        adm = adao1.find(2);
//        adm.display();
        
        //Test update
//        Administrator adm = new Administrator();
//        AdministratorsDAO adao1 = new AdministratorsDAO(); 
//        adm = adao1.find(3);
//        adm.setFname("Billy");
//        int rows = adao1.update(adm);
//        System.out.println("Rows = " + rows);
//        adm.display();
        
        
        //Test insert
//		AdministratorsDAO adao1 = new AdministratorsDAO();        
//		int rows = adao1.insert("test4", "pwd4", "Mary", "Tester");
//		Administrator adm = new Administrator();
//		adm = adao1.find(4);
//		adm.display();
		
		//Test delete
//		AdministratorsDAO adao1 = new AdministratorsDAO(); 
//		Administrator adm = new Administrator();
//		adm = adao1.find(4);
//		int rows = adao1.delete(adm);
//		adm = adao1.find(4);
//		adm.display();	//Should be nothing if deleted
//		System.out.println("Rows = " + rows);

	} //end main()	

}
