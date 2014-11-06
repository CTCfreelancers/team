package dao;
/********************************************************************
 *	RealityUWeb: OccupationsDAO.java
 *  3/26/2014
 ********************************************************************/
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

import obj.Occupation;
import dao.DAO;
import dao.DbUtil;

/**
 * The Class OccupationsDAO retrieves data from the "Occupation" table.
 */
public class OccupationsDAO implements DAO {
    
	//  ==========================  SELECT/FIND - LIST OF OCCUPATIONS  ==========================
	/**
	 * Gets a List of Occupations by search criteria.
	 * 
	 * @param column
	 *            : The column name in the table to look for.
	 * @param search
	 *            : The term to look for in the column.
	 * @return Returns a List of Occupation objects.
	 */
	public List<Occupation> search(String column, String search) {
		//Check Table & Create Table if it doesn't already exist
		boolean success = createTable();
		System.out.println("Check if table exists (create if doesn't exist). Table exists: " + success);

		// Variable Declarations
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		List<Occupation> lstOccupation = new ArrayList<Occupation>();

		String sql = "";
		String strWhere = "";

		try {
			// Load Driver & Connect to Dbase
			conn = DbUtil.createConnection();
			
			//Delete single quotes in SQL string for 'search' parameter for dbase fields that are non-string fields
			if (column != null) {
				//Change for Integer & Double dbase fields
				if (column.equals("id") || column.equals("annGrossSal") || column.equals("monGrossSal") 
						|| column.equals("marAnnualTax") || column.equals("marMonthlyTax") || column.equals("marAfterTax") 
						|| column.equals("sinAnnualTax") || column.equals("sinMonthlyTax") || column.equals("sinAfterTax") 
						|| column.equals("gpaCategory") || column.equals("loan")) {
					//No single quotes on 'search'
					strWhere = "WHERE " + column + " = " + search;
				} else { //for all String dbase fields
					//Single quotes on 'search'
					strWhere = "WHERE " + column + " = '" + search + "'";
				} //end if
			} //end if

			sql = "SELECT * FROM Occupation " + strWhere;
			stmt = conn.prepareStatement(sql);
			System.out.println("SQL: " + sql);

			// Execute Statement - Get ResultSet by Column Name
			rs = stmt.executeQuery();

			//Process the ResultSet
			while (rs.next()) {
				Occupation ocp  = new Occupation();
				
				ocp.setId(rs.getInt("id"));
				ocp.setName(rs.getString("name"));
				ocp.setType(rs.getString("type"));
				
				ocp.setIndustry(rs.getString("industry"));
				ocp.setCategory(rs.getString("category"));
				
				ocp.setAnnGrossSal(rs.getDouble("annGrossSal"));			
				ocp.setMonGrossSal(rs.getDouble("monGrossSal"));
				
				ocp.setMarAnnualTax(rs.getDouble("marAnnualTax"));				
				ocp.setMarMonthlyTax(rs.getDouble("marMonthlyTax"));				
				ocp.setMarAfterTax(rs.getDouble("marAfterTax"));
				
				ocp.setSinAnnualTax(rs.getDouble("sinAnnualTax"));
				ocp.setSinMonthlyTax(rs.getDouble("sinMonthlyTax"));
				ocp.setSinAfterTax(rs.getDouble("sinAfterTax"));
				
				ocp.setGpaCategory(rs.getInt("gpaCategory"));
				ocp.setLoan(rs.getDouble("loan"));
				
				lstOccupation.add(ocp);
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

		return lstOccupation;
	}

//  ==========================  SELECT/FIND - ONE OCCUPATION BY ID  ==========================	
	/**
	 * Finds an Occupation by id.
	 * 
	 * @param id
	 *            : The Occupation id to search for.
	 * @return Returns an Occupation object with that id.
	 */
	public Occupation find(int id) {
		//Check Table & Create Table if it doesn't already exist
		boolean success = createTable();
		System.out.println("Check if table exists (create if doesn't exist). Table exists: " + success);
		
		// Variable Declarations
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		Occupation ocp = new Occupation();

		String sql = "";
		String strWhere = "";

		try {
			// Load Driver & Connect to Dbase
			conn = DbUtil.createConnection();

			// Create SQL Statement
			strWhere = "WHERE id = " + id;

			sql = "SELECT * FROM Occupation " + strWhere;
			stmt = conn.prepareStatement(sql);
			System.out.println("SQL: " + sql);

			// Execute Statement - Get ResultSet by Column Name
			rs = stmt.executeQuery();

			//Process the ResultSet
			while (rs.next()) {
				ocp.setId(rs.getInt("id"));
				ocp.setName(rs.getString("name"));
				ocp.setType(rs.getString("type"));
				
				ocp.setIndustry(rs.getString("industry"));
				ocp.setCategory(rs.getString("category"));
				
				ocp.setAnnGrossSal(rs.getDouble("annGrossSal"));			
				ocp.setMonGrossSal(rs.getDouble("monGrossSal"));
				
				ocp.setMarAnnualTax(rs.getDouble("marAnnualTax"));				
				ocp.setMarMonthlyTax(rs.getDouble("marMonthlyTax"));				
				ocp.setMarAfterTax(rs.getDouble("marAfterTax"));
				
				ocp.setSinAnnualTax(rs.getDouble("sinAnnualTax"));
				ocp.setSinMonthlyTax(rs.getDouble("sinMonthlyTax"));
				ocp.setSinAfterTax(rs.getDouble("sinAfterTax"));
				
				ocp.setGpaCategory(rs.getInt("gpaCategory"));
				ocp.setLoan(rs.getDouble("loan"));
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

		return ocp;
	} //end find
	
	//  ==========================  SELECT/FIND - LIST OF ALL OCCUPATIONS  ==========================
	/**
	 * Creates a List of all Occupation objects.
	 * 
	 * @return Returns a List of Occupation objects.
	 */
	public List<Occupation> findAllOccupations() {
		//Check Table & Create Table if it doesn't already exist
		boolean success = createTable();
		System.out.println("Check if table exists (create if doesn't exist). Table exists: " + success);

		// Variable Declarations
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		List<Occupation> lstOccupation = new ArrayList<Occupation>();

		String sql = "";

		try {
			// Load Driver & Connect to Dbase
			conn = DbUtil.createConnection();

			// Create SQL Statement
			sql = "SELECT * FROM Occupation ";
			stmt = conn.prepareStatement(sql);
			System.out.println("SQL: " + sql);

			// Execute Statement - Get ResultSet by Column Name
			rs = stmt.executeQuery();

			//Process the ResultSet
			while (rs.next()) {
				Occupation ocp  = new Occupation();
				
				ocp.setId(rs.getInt("id"));
				ocp.setName(rs.getString("name"));
				ocp.setType(rs.getString("type"));
				
				ocp.setIndustry(rs.getString("industry"));
				ocp.setCategory(rs.getString("category"));
				
				ocp.setAnnGrossSal(rs.getDouble("annGrossSal"));			
				ocp.setMonGrossSal(rs.getDouble("monGrossSal"));
				
				ocp.setMarAnnualTax(rs.getDouble("marAnnualTax"));				
				ocp.setMarMonthlyTax(rs.getDouble("marMonthlyTax"));				
				ocp.setMarAfterTax(rs.getDouble("marAfterTax"));
				
				ocp.setSinAnnualTax(rs.getDouble("sinAnnualTax"));
				ocp.setSinMonthlyTax(rs.getDouble("sinMonthlyTax"));
				ocp.setSinAfterTax(rs.getDouble("sinAfterTax"));
				
				ocp.setGpaCategory(rs.getInt("gpaCategory"));
				ocp.setLoan(rs.getDouble("loan"));
				
				lstOccupation.add(ocp);
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

		return lstOccupation;
	}
	
	//  ==========================  SELECT/FIND - LIST OF ALL CATEGORIES  ==========================
	/**
	 * Creates a String List of all Categories.
	 * 
	 * @return Returns a String List of all Categories.
	 */
	public List<String> findAllCategories() {
		//Check Table & Create Table if it doesn't already exist
		boolean success = createTable();
		System.out.println("Check if table exists (create if doesn't exist). Table exists: " + success);

		// Variable Declarations
		List<Occupation> lstOccupation = new ArrayList<Occupation>();
		List<String> lstCategories = new ArrayList<String>();
		String cat = "";
		Set<String> tempSet = new HashSet<String>();
		
		//Get ArrayList of all Occupation objs
		lstOccupation = findAllOccupations();

		for (int i = 0; i < lstOccupation.size(); i++) {
		    cat = lstOccupation.get(i).getCategory();
		    //Sets do not add dupes, so will be unduplicated category list
		    if (tempSet.add(cat)) {
		    	//Use ArrayList to be able to order (can't order Sets)
		    	lstCategories.add(cat);
		    }
		    		//System.out.println("Item " + i + " : " + cat);
		}

/*		//Display in Console for Testing Purposes Only
		for (String str : lstCategories) {
			System.out.println("Category ArrayList : " + str);
		}
		System.out.println("===========================");
		for (String str : tempSet) {
			System.out.println("TempSet List : " + str);
		}
*/

		return lstCategories;
	}
	
	//  ==============  SELECT/FIND - LIST OF ALL OCCUPATIONS IN EACH CATEGORY (STRING LIST OF OCCUPATION NAMES)  ===============
	/**
	 * Creates a String List of all Occupation names for each category.
	 * 
	 * @return Returns a List of all Categories, whereby each Category List contains an inner List of Occupation names within that category.
	 */
	public List<List<String>> findOccupationNamesPerCategory() {
		//Check Table & Create Table if it doesn't already exist
		boolean success = createTable();
		System.out.println("Check if table exists (create if doesn't exist). Table exists: " + success);

		// Variable Declarations
		List<Occupation> lstOccupation = new ArrayList<Occupation>();
		//Creates a List of Lists
		List<List<String>> lstOccupsByCats = new ArrayList<List<String>>();
		List<String> lstCategories = new ArrayList<String>();
		
		//Get list of all categories
		lstCategories = findAllCategories();
		
		//Loop thru all Categories and find Occupations for each
		for (int i = 0; i < lstCategories.size(); i++) {
			//Search for all Occupations in each Category
		    //(Note: Call 'search' method directly to find all Occupations within one category only)
			lstOccupation = search("category", lstCategories.get(i));
					//System.out.println("Category " + i + " : " + lstCategories.get(i));
			
			//Makes a secondary(nested) anonymous String list for each nested list
			lstOccupsByCats.add(new ArrayList<String>()); 

			//Loop thru all Occupation objs and add name of Occupation for each to String List
			for (int j = 0; j < lstOccupation.size(); j++) {
				//Add name of Occupation to nested String Occupation Names List inside of overall 'lstOccupsByCats' array
				lstOccupsByCats.get(i).add(lstOccupation.get(j).getName());
						//System.out.println("Occupation " + j + " : " + lstOccupation.get(j).getName());
			} //end for
		} //end for

		//Display in Console for Testing Purposes Only
/*		for (int i = 0; i < lstOccupsByCats.size(); i++) {
			System.out.println("============ Category " + i + ": " + lstCategories.get(i) + "===============");			
			System.out.println("Nested Array Values: " + lstOccupsByCats.get(i));
			//Get length of each nested ArrayList --> lstOccupsByCats.get(i).size()
			for (int j = 0; j < lstOccupsByCats.get(i).size(); j++) {
				System.out.println("Occupation: " + lstOccupsByCats.get(i).get(j));
			} //end for
		} //end for
*/		
		//Alternative method: Display in Console for Testing Purposes Only
/*		int c = 0;
		for (List<String> cats : lstOccupsByCats) {
			System.out.println("============" + lstCategories.get(c) + "===============");
			c++;
			for (String occps : cats) {
				System.out.println("Occupation: " + occps);
			}
		}
*/
		
		return lstOccupsByCats;
	}
	
	
	//  =================  SELECT/FIND - LIST OF ALL OCCUPATIONS IN EACH CATEGORY (OCCUPATION OBECTS)  ================
	/**
	 * Creates a List of all Occupation objects for each category.
	 * 
	 * @return Returns a List of all Categories, whereby each Category List contains an inner List of Occupation objects within that category.
	 */
	public List<List<Occupation>> findOccupationsPerCategory() {
		//Check Table & Create Table if it doesn't already exist
		boolean success = createTable();
		System.out.println("Check if table exists (create if doesn't exist). Table exists: " + success);

		// Variable Declarations
		List<Occupation> lstOccupation = new ArrayList<Occupation>();
		//Creates a List of Occupation Lists
		List<List<Occupation>> lstOccupsByCats = new ArrayList<List<Occupation>>();
		List<String> lstCategories = new ArrayList<String>();
		
		//Get list of all categories
		lstCategories = findAllCategories();
		
		
		//Loop thru all Categories and find Occupations for each
		for (int i = 0; i < lstCategories.size(); i++) {
			//Search for all Occupations in each Category
		    //(Note: Call 'search' method directly to find all Occupations within one category only)
			lstOccupation = search("category", lstCategories.get(i));
			//Add Occupation List to overall Occupations by Categories List
			lstOccupsByCats.add(lstOccupation);
		    	//System.out.println("Category " + i + " : " + lstCategories.get(i));
		}

/*		//Display in Console for Testing Purposes Only
		for (List<Occupation> lst : lstOccupsByCats) {
			System.out.println("===========================");
			for (Occupation oc : lst) {
				System.out.println("Category: " + oc.getCategory() + ", Occupation: " + oc.getName());
			}
		}
*/
		
		return lstOccupsByCats;
	}
	
	
	//  =================  SELECT/FIND - STRING LIST OF ALL TYPES & INDUSTRIES FOR EACH CATEGORY  ================
	/**
	 * Creates a String List of all Types and Industries for each Category.
	 * 
	 * @return Returns a String List of all Categories, whereby each Category List contains an inner List of the Type & Industry for that Category.
	 */
	public List<List<String>> findTypesAndIndustriesPerCategory() {
			//Check Table & Create Table if it doesn't already exist
			boolean success = createTable();
			System.out.println("Check if table exists (create if doesn't exist). Table exists: " + success);

			// Variable Declarations
			List<Occupation> lstOccupation = new ArrayList<Occupation>();
			//Creates a List of Lists
			List<List<String>> lstOccupsByTypeAndIndustry = new ArrayList<List<String>>();
			List<String> lstCategories = new ArrayList<String>();		
			//Get list of all categories
			lstCategories = findAllCategories();
			
			String type, industry;
			
			//Loop thru all Categories and find Type & Industry for each
			for (int i = 0; i < lstCategories.size(); i++) {
				//Search for all Occupations in each Category (only need one Occupation to check Type & Industry) 
			    //(Note: Call 'search' method directly to find all Occupations within one category only)
				lstOccupation = search("category", lstCategories.get(i));
						//System.out.println("Category " + i + " : " + lstCategories.get(i));
				
				//Check 1st Occupation in list to get Type & Industry for that Category (only need to check one in list)
				type = lstOccupation.get(0).getType();
				industry = lstOccupation.get(0).getIndustry();
				
				//Makes a secondary(nested) anonymous String list for each nested list
				lstOccupsByTypeAndIndustry.add(new ArrayList<String>()); 
				//Add type & industry as elements in nested String List
				lstOccupsByTypeAndIndustry.get(i).add(type);
				lstOccupsByTypeAndIndustry.get(i).add(industry);

			} //end for

			//Display in Console for Testing Purposes Only
/*			for (int i = 0; i < lstOccupsByTypeAndIndustry.size(); i++) {
				String itemName = "";
				System.out.println("============ Category " + i + ": " + lstCategories.get(i) + "===============");			
				System.out.println("Nested Array Values: " + lstOccupsByTypeAndIndustry.get(i));
				//Get length of each nested ArrayList --> lstOccupsByTypeAndIndustry.get(i).size()
				for (int j = 0; j < lstOccupsByTypeAndIndustry.get(i).size(); j++) {
					itemName = (j == 0) ? "Type: " : "Industry: ";
					System.out.println(itemName + lstOccupsByTypeAndIndustry.get(i).get(j));
				} //end for
			} //end for
*/		
			//Alternative method: Display in Console for Testing Purposes Only
	/*		int c = 0;
			for (List<String> cats : lstOccupsByTypeAndIndustry) {
				System.out.println("============" + lstCategories.get(c) + "===============");
				c++;
				for (String occps : cats) {
					System.out.println("Type or Industry: " + occps);
				}
			}
	*/
			
			return lstOccupsByTypeAndIndustry;
		}	
	
	//  ==========================  UPDATE  ==========================
	/**
	 * Update.
	 * 
	 * @param ocp
	 *            : the Occupation being updated
	 * @return Returns an integer:<br>
	 *         0: Failure<br>
	 *         1: Success
	 */
	public int update(Occupation ocp) {
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
			String sql = "UPDATE Occupation SET name=?, type=?, industry=?, category=?, " +
					"annGrossSal=?, monGrossSal=?, marAnnualTax=?, marMonthlyTax=?, marAfterTax=?, " +
					"sinAnnualTax=?, sinMonthlyTax=?, sinAfterTax=?, gpaCategory=?, loan=? " + "WHERE id=?";

			stmt = conn.prepareStatement(sql);
			stmt.setString(1, ocp.getName());
			stmt.setString(2, ocp.getType());
			stmt.setString(3, ocp.getIndustry());
			stmt.setString(4, ocp.getCategory());
			stmt.setDouble(5, ocp.getAnnGrossSal());
			stmt.setDouble(6, ocp.getMonGrossSal());
			stmt.setDouble(7, ocp.getMarAnnualTax());
			stmt.setDouble(8, ocp.getMarMonthlyTax());
			stmt.setDouble(9, ocp.getMarAfterTax());
			stmt.setDouble(10, ocp.getSinAnnualTax());
			stmt.setDouble(11, ocp.getSinMonthlyTax());
			stmt.setDouble(12, ocp.getSinAfterTax());
			stmt.setInt(13, ocp.getGpaCategory());
			stmt.setDouble(14, ocp.getLoan());
			stmt.setInt(15, ocp.getId());
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
	 * Insert a new Occupation.
	 * 
	 * @param name;
	 * @param type;
	 *
	 * @param industry;
	 * @param category;
	 * 
	 * @param annGrossSal;
	 * @param monGrossSal;
	 * 
	 * @param marAnnualTax;
	 * @param marMonthlyTax;
	 * @param marAfterTax;
	 * 
	 * @param sinAnnualTax ;
	 * @param sinMonthlyTax;
	 * @param sinAfterTax;
	 * 
	 * @param gpa;
	 * @param loan;
	 * @return Returns an integer:<br>
	 *         0: Failure<br>
	 *         1: Success
	 */
	public int insert(String name, String type, String industry,
			String category, double annGrossSal, double monGrossSal, double marAnnualTax,
			double marMonthlyTax, double marAfterTax, double sinAnnualTax,
			double sinMonthlyTax, double sinAfterTax, int gpaCategory, double loan) {
		
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
			String sql = "INSERT INTO Occupation (name, type, industry, category, " +
						"annGrossSal, monGrossSal, marAnnualTax, marMonthlyTax, marAfterTax, " +
						"sinAnnualTax, sinMonthlyTax, sinAfterTax, gpaCategory, loan) " + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			stmt = conn.prepareStatement(sql);
			stmt.setString(1, name);
			stmt.setString(2, type);
			stmt.setString(3, industry);
			stmt.setString(4, category);
			stmt.setDouble(5, annGrossSal);
			stmt.setDouble(6, monGrossSal);
			stmt.setDouble(7, marAnnualTax);
			stmt.setDouble(8, marMonthlyTax);
			stmt.setDouble(9, marAfterTax);
			stmt.setDouble(10, sinAnnualTax);
			stmt.setDouble(11, sinMonthlyTax);
			stmt.setDouble(12, sinAfterTax);
			stmt.setInt(13, gpaCategory);
			stmt.setDouble(14, loan);
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
	 * Delete a Occupation.
	 * 
	 * @param ocp
	 *            : The Occupation to delete
	 * @return Returns an integer:<br>
	 *         0: Failure<br>
	 *         1: Success
	 */
	public int delete(Occupation ocp) {
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
			String sql = "DELETE FROM Occupation WHERE id = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, ocp.getId());
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
		String tableName = "Occupation";
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
		String tableName = "Occupation";
		
		// Create SQL Statement
		String sql = "CREATE TABLE IF NOT EXISTS " + tableName
				+ " ('id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
				+ "'name' VARCHAR NOT NULL,"
				+ "'type' VARCHAR,"
				+ "'industry' VARCHAR,"
				+ "'category' VARCHAR,"
				+ "'annGrossSal' DOUBLE,"
				+ "'monGrossSal' DOUBLE,"
				+ "'marAnnualTax' DOUBLE,"
				+ "'marMonthlyTax' DOUBLE,"
				+ "'marAfterTax' DOUBLE,"
				+ "'sinAnnualTax' DOUBLE,"
				+ "'sinMonthlyTax' DOUBLE,"
				+ "'sinAfterTax' DOUBLE,"
				+ "'gpaCategory' INTEGER,"
				+ "'loan' DOUBLE)";
		
		success = DbUtil.createTable(tableName, sql);
		
		return success;
	}
	
	
    //   ========================  MAIN METHOD  ==================== 
	public static void main(String[] args) {		
		List<Occupation> lstOccp = new ArrayList<Occupation>();
		Occupation oc = new Occupation();
        //Create OccupationsDAO & Occupation Objs
        OccupationsDAO oc1 = new OccupationsDAO();
        
        System.out.println("DB string = " + DAO.DB);
        
        //Returns List of Groups matching search criteria (even if only 1)
        lstOccp = oc1.search("name", "Actor"); //Lookup by occupation name 
        //Extract single Occupation obj from List
        //Loop thru Occupation List (should only be 1 obj in list)
        for (int i = 0; i < lstOccp.size(); i++)
        {
        	if (i == 0) {
        		oc = lstOccp.get(i);
        		System.out.println("Extracted Occupation obj from List.");
        		oc.display();
        	} else { //more than one obj in list
        		System.out.println("Error - Duplicate name.");
        	} //end if
        } //end for
        
/*        //Test List of all Categories - Get Occupation Names
        OccupationsDAO oc2 = new OccupationsDAO();
        oc2.findAllCategories();
        //Test List of Lists (Occupation Names String List inside Categories List)
        List<List<String>> loc = new ArrayList<List<String>>();
        loc = oc2.findOccupationNamesPerCategory();
*/
          
        //Test List of all Categories - Get Type & Industry
        OccupationsDAO oc2 = new OccupationsDAO();
        //Test List of Lists (Type & Industry String List inside Categories List)
        List<List<String>> loc = new ArrayList<List<String>>();
        loc = oc2.findTypesAndIndustriesPerCategory();
        

        //Test List of Lists (Occupation OBJECT List inside Categories List)
//        List<List<Occupation>> loccp = new ArrayList<List<Occupation>>();
//        loccp = oc2.findOccupationsPerCategory();
		
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