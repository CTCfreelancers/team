package dao;
/********************************************************************
 *	RealityUWeb: SurveysDAO.java
 *  3/19/2014
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

import obj.Survey;
import dao.DbUtil;
/**
 * The Class SurveysDAO retrieves data from the "Survey" table.
 */

public class SurveysDAO implements DAO {
	
	private Date currentDateTime = new Date();

	//  ==========================  SELECT/FIND - LIST OF SURVEYS  ==========================
	/**
	 * Gets a List of Surveys by search criteria.
	 * 
	 * @param column
	 *            : The column name in the table to look for.
	 * @param search
	 *            : The term to look for in the column.
	 * @return Returns a List of Survey objects.
	 */
	public List<Survey> search(String column, String search) {
		//Check Table & Create Table if it doesn't already exist
		boolean success = createTable();
		System.out.println("Check if table exists (create if doesn't exist). Table exists: " + success);

		// Variable Declarations
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		List<Survey> lstSurvey = new ArrayList<Survey>();

		String sql = "";
		String strWhere = "";

		try {
			// Load Driver & Connect to Dbase
			conn = DbUtil.createConnection();

			//Delete single quotes in SQL string for 'search' parameter for dbase fields that are non-string fields
			if (column != null) {
				//Change for Integer & Double dbase fields
				if (column.equals("id") || column.equals("gpa") || column.equals("groupID") || column.equals("spouse") 
						|| column.equals("numChild") || column.equals("childSupport") || column.equals("creditScore")) {
					//No single quotes on 'search'
					strWhere = "WHERE " + column + " = " + search;
				} else { //for all String dbase fields
					//Single quotes on 'search'
					strWhere = "WHERE " + column + " = '" + search + "'";
				} //end if
			} //end if

			// Create SQL Statement
			sql = "SELECT * FROM Survey " + strWhere;
			stmt = conn.prepareStatement(sql);
			System.out.println("SQL: " + sql);

			// Execute Statement - Get ResultSet by Column Name
			rs = stmt.executeQuery();
			
			//Process the ResultSet
			while (rs.next()) {
				Survey survey = new Survey();

				survey.setId(rs.getInt("id"));
				survey.setFname(rs.getString("fName"));
				survey.setLname(rs.getString("lName"));
				
				survey.setDob(rs.getString("dob"));
				survey.setGpa(rs.getDouble("gpa"));
				survey.setGender(rs.getString("gender"));
				
				survey.setGroupID(rs.getInt("groupId"));
				survey.setEducation(rs.getString("education"));				
				survey.setPrefJob(rs.getString("prefJob"));
				
				survey.setJob(rs.getString("job"));
				survey.setMarried(rs.getString("married"));
				survey.setSpouse(rs.getInt("spouse"));
				
				survey.setChildren(rs.getString("children"));
				survey.setNumChild(rs.getInt("numChild"));
				survey.setCCards(rs.getString("cCards"));
				
				survey.setCCardUses(rs.getString("cCardUses"));
				survey.setGroceries(rs.getString("groceries"));
				survey.setClothing(rs.getString("clothing"));
				
				survey.setHome(rs.getString("home"));
				survey.setVehicle(rs.getString("vehicle"));
				survey.setChildSupport(rs.getDouble("childSupport"));
				
				survey.setCreditScore(rs.getDouble("creditScore"));
				survey.setSave(rs.getString("save"));
				survey.setEntertainment(rs.getString("entertainment"));
				
				lstSurvey.add(survey);
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
		
		return lstSurvey;
	}
		
//  ==========================  SELECT/FIND - ONE SURVEY  ==========================	
	/**
	 * Finds a Survey by id.
	 * 
	 * @param id
	 *            : The Survey id to search for.
	 * @return Returns Survey object with that id.
	 */
	public Survey find(int id) {
		//Check Table & Create Table if it doesn't already exist
		boolean success = createTable();
		System.out.println("Check if table exists (create if doesn't exist). Table exists: " + success);
		
		// Variable Declarations
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		Survey survey = new Survey();

		String sql = "";
		String strWhere = "";

		try {
			// Load Driver & Connect to Dbase
			conn = DbUtil.createConnection();

			// Create SQL Statement
			strWhere = "WHERE id = " + id;

			sql = "SELECT * FROM Survey " + strWhere;
			stmt = conn.prepareStatement(sql);
			System.out.println("SQL: " + sql);

			// Execute Statement - Get ResultSet by Column Name
			 rs = stmt.executeQuery();

			//Process the ResultSet
			while (rs.next()) {
				survey.setId(rs.getInt("id"));
				survey.setFname(rs.getString("fName"));
				survey.setLname(rs.getString("lName"));
				
				survey.setDob(rs.getString("dob"));
				survey.setGpa(rs.getDouble("gpa"));
				survey.setGender(rs.getString("gender"));
				
				survey.setGroupID(rs.getInt("groupId"));
				survey.setEducation(rs.getString("education"));				
				survey.setPrefJob(rs.getString("prefJob"));
				
				survey.setJob(rs.getString("job"));
				survey.setMarried(rs.getString("married"));
				survey.setSpouse(rs.getInt("spouse"));
				
				survey.setChildren(rs.getString("children"));
				survey.setNumChild(rs.getInt("numChild"));
				survey.setCCards(rs.getString("cCards"));
				
				survey.setCCardUses(rs.getString("cCardUses"));
				survey.setGroceries(rs.getString("groceries"));
				survey.setClothing(rs.getString("clothing"));
				
				survey.setHome(rs.getString("home"));
				survey.setVehicle(rs.getString("vehicle"));
				survey.setChildSupport(rs.getDouble("childSupport"));
				
				survey.setCreditScore(rs.getDouble("creditScore"));
				survey.setSave(rs.getString("save"));
				survey.setEntertainment(rs.getString("entertainment"));	
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

		return survey;
	} //end find

//  ==========================  UPDATE  ==========================
	/**
	 * Update.
	 * 
	 * @param survey
	 *            : the Survey is being updated
	 * @return Returns an integer:<br>
	 *         0: Failure<br>
	 *         1: Success
	 */
	public int update(Survey survey) {
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
			String sql = "UPDATE Survey SET fName=?, lName=?, dob=?, gpa=?, gender=?, groupID=?, education=?, prefJob=?, job=?, "
					+ "married=?, spouse=?, children=?, numChild=?, cCards=?, cCardUses=?, "
					+ "groceries=?, clothing=?, home=?, vehicle=?, childSupport=?, creditScore=?, save=?, entertainment=? " + "WHERE id=?";

			stmt = conn.prepareStatement(sql);

			stmt.setString(1, survey.getFname());
			stmt.setString(2, survey.getLname());
			
			stmt.setString(3, survey.getDob());
			stmt.setDouble(4, survey.getGpa());	
			stmt.setString(5, survey.getGender());
			
			stmt.setInt(6, survey.getGroupID());
			stmt.setString(7, survey.getEducation());
			stmt.setString(8, survey.getPrefJob());
			
			stmt.setString(9, survey.getJob());
			stmt.setString(10, survey.getMarried());
			stmt.setInt(11, survey.getSpouse());
			
			stmt.setString(12, survey.getChildren());
			stmt.setInt(13, survey.getNumChild());
			stmt.setString(14, survey.getCCards());
			
			stmt.setString(15, survey.getCCardUses());
			stmt.setString(16, survey.getGroceries());	
			stmt.setString(17, survey.getClothing());
			
			stmt.setString(18, survey.getHome());
			stmt.setString(19, survey.getVehicle());
			stmt.setDouble(20, survey.getChildSupport());
			
			stmt.setDouble(21, survey.getCreditScore());
			stmt.setString(22, survey.getSave());
			stmt.setString(23, survey.getEntertainment());
			
			stmt.setInt(24,survey.getId());
			System.out.println("SQL: " + sql);
			//Execute Statement
			rows = stmt.executeUpdate();
		} catch (Exception e) {
			// Handle Errors for Class
			System.out.println("Class Error. Current DB: " + DB + e);
		} finally {
			//Close Query, and Database Connection
			DbUtil.close(stmt);
			DbUtil.close(conn);
			System.out.println("Closed Resources");
		} // End Try/Catch
		return rows;
	}
	
//  ==========================  INSERT  ==========================
	/**
	 * Insert a new Survey.
	 * 
	 * @param fName
	 * @param lName		
	 * @param dob
	 * @param gpa
	 * @param gender		
	 * @param groupID
	 * @param education
	 * @param prefJob		
	 * @param job
	 * @param married 
	 * @param spouse 		
	 * @param children
	 * @param numChild
	 * @param cCards		
	 * @param cCardUses
	 * @param groceries
	 * @param clothing		
	 * @param home
	 * @param vehicle      
	 * @param childSupport
	 * @param creditScore
	 * @param save
	 * @param entertainment
	 * @return Returns an integer:<br>
	 *         0: Failure<br>
	 *         1: Success
	 */
	public int insert(String fName, String lName, String dob, double gpa, String gender, 
			int groupID, String education, String prefJob, String job, String married, int spouse, String children, 
			int numChild, String cCards, String cCardUses, String groceries, String clothing, String home, 
			String vehicle, double childSupport, double creditScore, String save, String entertainment){
		
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
			String sql = "INSERT INTO Survey (fName, lName, dob, gpa, gender, groupID, education, " +
						"prefJob, job, married, spouse, children, numChild, cCards, cCardUses, groceries, " +
						"clothing, home, vehicle, childSupport, creditScore, save, entertainment) " + 
						"VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			stmt = conn.prepareStatement(sql);

			stmt.setString(1, fName);
			stmt.setString(2, lName);
			
			stmt.setString(3, dob);
			stmt.setDouble(4, gpa);
			stmt.setString(5, gender);
			
			stmt.setInt(6, groupID);
			stmt.setString(7, education);
			stmt.setString(8, prefJob);
			
			stmt.setString(9, job);
			stmt.setString(10, married);
			stmt.setInt(11, spouse);
			
			stmt.setString(12, children);
			stmt.setInt(13, numChild);
			stmt.setString(14, cCards);
		
			stmt.setString(15, cCardUses);
			stmt.setString(16, groceries);
			stmt.setString(17, clothing);
			
			stmt.setString(18, home);
			stmt.setString(19, vehicle);
			stmt.setDouble(20, childSupport);
			
			stmt.setDouble(21, creditScore);
			stmt.setString(22, save);
			stmt.setString(23, entertainment);
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
	 * Delete a Survey.
	 * 
	 * @param survey
	 *            : The Survey to delete
	 * @return Returns an integer:<br>
	 *         0: Failure<br>
	 *         1: Success
	 */
	public int delete(Survey survey) {
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
			String sql = "DELETE FROM Survey WHERE id = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, survey.getId());
			System.out.println("SQL: " + sql);

			// Execute SQL Statement
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
	
	public int update(String fName, String lName, String dob, String gender, 
			int groupID, String education, String prefJob, String job, String married, int spouse, String children, 
			int numChild, String cCards, String cCardUses, String groceries, String clothing, String home, 
			String vehicle, double childSupport, double creditScore, String save, String entertainment,int id){
		
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
			String sql = "UPDATE Survey SET fName='"+fName+"', lName='"+lName+"', dob='"+dob+"', gender='"+gender+"', groupID="+groupID+", education='"+education+"', prefJob='"+prefJob+"', job='"+job+"', "
					+ "married='"+married+"', spouse="+spouse+", children='"+children+"', numChild="+numChild+", cCards='"+cCards+"', cCardUses='"+cCardUses+"', "
					+ "groceries='"+groceries+"', clothing='"+clothing+"', home='"+home+"', vehicle='"+vehicle+"', childSupport="+childSupport+", creditScore="+creditScore+", save='"+save+"', entertainment='"+entertainment+"'" + " Where id ="+id+"";
			
			stmt = conn.prepareStatement(sql);
			System.out.println("SQL: " + sql);
			stmt.executeUpdate();
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
	Survey s1 = new Survey();
	public String findStudentID(String fName, String lName, String dob){
		Connection conn = null;
		PreparedStatement stmt = null;	
		ResultSet rs = null;
		String data= "";
		int num = 0;
		try {
			// Load Driver & Connect to Dbase
			conn = DbUtil.createConnection();
		
		String sql = "Select id From Survey where(fName ='"+fName+"' and lName ='"+lName+"' and dob ='"+dob+"')";
		System.out.println(fName+""+lName+""+dob);
		stmt = conn.prepareStatement(sql);
		rs = stmt.executeQuery();
		while(rs.next()){
		num = s1.setID(rs.getInt("id"));	
		
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
	System.out.println(data);
	data =  Integer.toString(num);
	return String.valueOf(data);
} //end find
	
	
	
	//  ==========================  CHECK TABLE  ==========================
	/**
	 * This method makes sure table exists<br>
	 * 
	 * @return Returns True/False
	 */
	@Override
	public boolean checkTable() {
		String tableName = "Survey";
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
		String tableName = "Survey";
		
		// Create SQL Statement
		String sql = "CREATE TABLE IF NOT EXISTS " + tableName
				+ " ('id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
				+ "'fName' VARCHAR,"
				+ "'lName' VARCHAR,"
				+ "'dob' VARCHAR,"
				+ "'gpa' DOUBLE,"
				+ "'gender' VARCHAR,"
				+ "'groupID' INTEGER,"
				+ "'education' VARCHAR,"
				+ "'prefJob' VARCHAR,"
				+ "'job' VARCHAR,"
				+ "'married' VARCHAR,"
				+ "'spouse' INTEGER,"
				+ "'children' VARCHAR,"
				+ "'numChild' INTEGER,"
				+ "'cCards' VARCHAR,"
				+ "'cCardUses' VARCHAR,"
				+ "'groceries' VARCHAR,"
				+ "'clothing' VARCHAR,"
				+ "'home' VARCHAR,"
				+ "'vehicle' VARCHAR,"
				+ "'childSupport' DOUBLE,"
				+ "'creditScore' DOUBLE,"
				+ "'save' VARCHAR,"
				+ "'entertainment' VARCHAR)";
		
		success = DbUtil.createTable(tableName, sql);
		
		return success;
	}
	
	

//  ========================  MAIN METHOD  ==================== 
	public static void main(String[] args) {
		List<Survey> lstSurvey = new ArrayList<Survey>();
		Survey surv = new Survey();
       //Create SurveysDAO & Survey Objs and Validate Login
       SurveysDAO surv1 = new SurveysDAO();
       //Returns List of Survey fields matching search criteria (even if only 1)
       lstSurvey = surv1.search("dob", "11/12/1979"); //Lookup by dob
       //Extract single Survey obj from List
       //Loop thru Survey List (should only be 1 obj in list)
       for (int i = 0; i < lstSurvey.size(); i++)
       {
       	if (i == 0) {
       		surv = lstSurvey.get(i);
       		System.out.println("Extracted Survey obj from List.");
       		surv.display();
       	} else { //more than one obj in list
       		System.out.println("Error - Duplicate Survey.");
       	} //end if
       } //end for
		
               
       //Test find
//     Administrator adm = new Administrator();
//     AdministratorsDAO adao1 = new AdministratorsDAO();        
//     adm = adao1.find(2);
//     adm.display();
     
     //Test update
//     Administrator adm = new Administrator();
//     AdministratorsDAO adao1 = new AdministratorsDAO(); 
//     adm = adao1.find(3);
//     adm.setFname("Billy");
//     int rows = adao1.update(adm);
//     System.out.println("Rows = " + rows);
//     adm.display();
     
     
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

