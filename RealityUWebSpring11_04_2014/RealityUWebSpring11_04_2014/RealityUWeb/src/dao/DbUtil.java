
package dao;
/********************************************************************
 *	RealityUWeb: DbUtil.java
 *  3/21/2014
 ********************************************************************/
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

import dao.DAO;

/**
 * The Class DbUtil is a helper class to open and close the database connection for all dao objs
 */
public class DbUtil {

	//  ==========================  CREATE DBASE CONNECTION  ==========================
	public static Connection createConnection() {
		// Variable Declarations
		Connection conn = null;
		try {
			// Load Driver
			Class.forName(DAO.JDBC_DRIVER);
			System.out.println("Driver Loaded.");
						
			//Connect to Database
			conn = DriverManager.getConnection(DAO.DB, DAO.USER, DAO.PASS);
			System.out.println("Database Connected.");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();	
		} catch (SQLException se) {
			// Handle Errors for JDBC
			System.out.println("JDBC Error. Current DB: " + DAO.DB + se);
		}
		return conn;
	}
	
	//  ==========================  CLOSE DBASE CONNECTION  ==========================
	public static void close(Connection connection) {
		if (connection != null) {
			try {
				//Close connection
				connection.close();
			} catch (SQLException e) {
				System.out.println("ERROR: Unable to Close Connection. " + e);
			}
		}
	}
	//  ==========================  CLOSE STATEMENT  ==========================
	public static void close(Statement statement) {
		if (statement != null) {
			try {
				//Close statement
				statement.close();
			} catch (SQLException e) {
				System.out.println("ERROR: Unable to Close Statement. " + e);
			}
		}
	}
	//  ==========================  CLOSE RESULTSET  ==========================
	public static void close(ResultSet resultSet) {
		if (resultSet != null) {
			try {
				//Close resultSet
				resultSet.close();
			} catch (SQLException e) {
				System.out.println("ERROR: Unable to Close ResultSet. " + e);
			}
		}
	}
	
	
	//  ==========================  CHECK TABLE  ==========================
	/**
	 * This method makes sure table exists<br>
	 * 
	 * @param tableName
	 *            : The table name to look for.
	 * 
	 * @return Returns True/False
	 */
	public static boolean checkTable(String tableName) {

		// Variable Declarations
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			// Load Driver & Connect to Dbase
			conn = DbUtil.createConnection();

			// Create SQL Statement
			//'sqlite_master' table generated automatically by SQLite
			//in dbase schema with list of all dbase tables. 
			//'sqlite_master' table is there even though it doesn't show
			String sql = "SELECT name FROM sqlite_master";
			stmt = conn.prepareStatement(sql);

			// Get ResultSet by Column Name
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				String tName = rs.getString("name");
				if (tName.equals(tableName)) {
					return true;
				}
			}

		} catch (Exception e) {
			// Handle Errors for Class
			System.out.println("Class Error. Current DB: " + DAO.DB + e);
		} finally {
			// Close Query, and Database Connection
			DbUtil.close(stmt);
			DbUtil.close(conn);
			System.out.println("Closed Resources");
		} // End Try/Catch
		System.out.println(tableName + " Table Does Not Exist.");
		return false;
	}
	
	//  ==========================  CREATE TABLE IF DOESN'T EXIST  ==========================
	/**
	 * Creates a table if it doesn't exist
	 * 
	 * @param tableName
	 *            : The table name to look for and create if it doesn't exist.
	 * @param sql
	 *            : The sql string to create the table with all fields.
	 * 
	 * @return Returns True/False
	 */
	public static boolean createTable(String tableName, String sql) {
		boolean success = true;

		if (!checkTable(tableName)) {
			JOptionPane
					.showMessageDialog(null,
					"Error: Table " + tableName + " does not exist! A new table will be created now.");

			// Variable Declarations
			Connection conn = null;
			PreparedStatement stmt = null;

			try {

				// Load Driver & Connect to Dbase
				conn = DbUtil.createConnection();

				// Create SQL Statement & Execute it
				// Use sql parameter value
				stmt = conn.prepareStatement(sql);
				stmt.executeUpdate();
				//Returns True/False if table exists
				success = checkTable(tableName);

			} catch (Exception e) {
				// Handle Errors for Class
				System.out.println("Class Error. Current DB: " + DAO.DB + e);
			} finally {
				// Close Query, and Database Connection
				DbUtil.close(stmt);
				DbUtil.close(conn);
				System.out.println("Closed Resources");
			} // End Try/Catch

			return success;
		} else {
			return success;
		} //end if
	}

} //end class
