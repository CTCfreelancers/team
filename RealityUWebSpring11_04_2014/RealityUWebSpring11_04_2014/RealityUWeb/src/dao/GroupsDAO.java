package dao;
/********************************************************************
 *	RealityUWeb: GroupsDAO.java
	Edited by josh 10/5/14
 *  3/21/2014
 ********************************************************************/
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import ctrl.Controller;




import obj.Group;
import dao.DAO;
import dao.DbUtil;

/**
 * The Class GroupsDAO retrieves data from the "Group_" table.
 */
public class GroupsDAO implements DAO {

	private Date currentDateTime = new Date();
	
	//  ==========================  SELECT/FIND - LIST OF GROUPS  ==========================
	/**
	 * Gets a List of Groups by search criteria.
	 * 
	 * @param column
	 *            : The column name in the table to look for.
	 * @param search
	 *            : The term to look for in the column.
	 * @return Returns a List of Group objects.
	 */
	public List<Group> search(String column, String search) {
		//Check Table & Create Table if it doesn't already exist
		boolean success = createTable();
		System.out.println("Check if table exists (create if doesn't exist). Table exists: " + success);

		// Variable Declarations
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		List<Group> lstGroup = new ArrayList<Group>();

		String sql = "";
		String strWhere = "";

		try {
			// Load Driver & Connect to Dbase
			conn = DbUtil.createConnection();

			// Create SQL Statement
			if (column != null)
				strWhere = "WHERE " + column + " = '" + search + "'";

			sql = "SELECT * FROM Group_ " + strWhere;
			stmt = conn.prepareStatement(sql);
			System.out.println("SQL: " + sql);

			// Execute Statement - Get ResultSet by Column Name
			rs = stmt.executeQuery();

			//Process the ResultSet
			while (rs.next()) {
				Group grp = new Group();

				grp.setId(rs.getInt("id"));
				grp.setName(rs.getString("name"));
				grp.setCreated(rs.getString("created"));
				grp.setModified(rs.getString("modified"));
				grp.setHighschool(rs.getString("highschool"));
				grp.setTeacher(rs.getString("teacher"));
				grp.setClassPeriod(rs.getString("classPeriod"));
				grp.setSurveyStartDate(rs.getString("surveyStartDate"));
				grp.setSurveyEndDate(rs.getString("surveyEndDate"));
				grp.setEventDate(rs.getString("eventDate"));
				grp.setStudentAccessCode(rs.getString("studentAccessCode"));
				
				/*--========================================-->
				<!--Adding Marriage Choice Radio Button		-->
				<!--           UPDATED CODE                 -->	
				<!--         Created by Josh                -->
				<!--======================================== */
				//Adding To The Database
				grp.setmarriageChoice(rs.getString("marriageChoice"));
				grp.setStudentAccessCode(rs.getString("coordinatorCode"));
				
				lstGroup.add(grp);
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

		return lstGroup;
	}

//  ==========================  SELECT/FIND - ONE GROUP BY ID  ==========================	
	/**
	 * Finds a Group by id.
	 * 
	 * @param id
	 *            : The Group id to search for.
	 * @return Returns an Group object with that id.
	 */
	public Group find(int id) {
		//Check Table & Create Table if it doesn't already exist
		boolean success = createTable();
		System.out.println("Check if table exists (create if doesn't exist). Table exists: " + success);
		
		// Variable Declarations
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
			strWhere = "WHERE id = " + id;

			sql = "SELECT * FROM Group_ " + strWhere;
			stmt = conn.prepareStatement(sql);
			System.out.println("SQL: " + sql);

			// Execute Statement - Get ResultSet by Column Name
			rs = stmt.executeQuery();

			//Process the ResultSet
			while (rs.next()) {
				grp.setId(rs.getInt("id"));
				grp.setName(rs.getString("name"));
				grp.setCreated(rs.getString("created"));
				grp.setModified(rs.getString("modified"));
				grp.setHighschool(rs.getString("highschool"));
				grp.setTeacher(rs.getString("teacher"));
				grp.setClassPeriod(rs.getString("classPeriod"));
				grp.setSurveyStartDate(rs.getString("surveyStartDate"));
				grp.setSurveyEndDate(rs.getString("surveyEndDate"));
				grp.setEventDate(rs.getString("eventDate"));
				grp.setStudentAccessCode(rs.getString("studentAccessCode"));
				//change in code = josh
				grp.setmarriageChoice(rs.getString("marriageChoice"));
				grp.setcoordinatorCode(rs.getString("coordinatorCode"));
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

		return grp;
	} //end find

//  ==========================  SELECT/FIND - ONE GROUP BY STUDENT ACCESS CODE  ==========================	
	/**
	 * Finds a Group by student access code.
	 * 
	 * @param studentAccessCode
	 *            : The Group access code to search for.
	 * @return Returns an Group object with that access code.
	 */
	public Group find(String studentAccessCode) {
		//Check Table & Create Table if it doesn't already exist
		boolean success = createTable();
		System.out.println("Check if table exists (create if doesn't exist). Table exists: " + success);
				
		// Variable Declarations
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
			strWhere = "WHERE studentAccessCode = '" + studentAccessCode + "'";

			sql = "SELECT * FROM Group_ " + strWhere;
			stmt = conn.prepareStatement(sql);
			System.out.println("SQL: " + sql);

			// Execute Statement - Get ResultSet by Column Name
			rs = stmt.executeQuery();

			//Process the ResultSet
			while (rs.next()) {
				grp.setId(rs.getInt("id"));
				grp.setName(rs.getString("name"));
				grp.setCreated(rs.getString("created"));
				grp.setModified(rs.getString("modified"));
				grp.setHighschool(rs.getString("highschool"));
				grp.setTeacher(rs.getString("teacher"));
				grp.setClassPeriod(rs.getString("classPeriod"));
				grp.setSurveyStartDate(rs.getString("surveyStartDate"));
				grp.setSurveyEndDate(rs.getString("surveyEndDate"));
				grp.setEventDate(rs.getString("eventDate"));
				grp.setStudentAccessCode(rs.getString("studentAccessCode"));
				//change in code = josh
				grp.setmarriageChoice(rs.getString("marriageChoice"));
				grp.setcoordinatorCode(rs.getString("coordinatorCode"));
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

		return grp;
	} //end find
	
	//  ==========================  SELECT/FIND - LIST OF ALL GROUPS  ==========================
	/**
	 * Creates a List of all Group objects.
	 * 
	 * @return Returns a List of Group objects.
	 */
	public List<Group> findAllGroups() {
		//Check Table & Create Table if it doesn't already exist
		boolean success = createTable();
		System.out.println("Check if table exists (create if doesn't exist). Table exists: " + success);
				
		// Variable Declarations
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		List<Group> lstGroup = new ArrayList<Group>();

		String sql = "";

		try {
			// Load Driver & Connect to Dbase
			conn = DbUtil.createConnection();

			// Create SQL Statement
			sql = "SELECT * FROM Group_ Order by eventDate ASC";
			stmt = conn.prepareStatement(sql);
			System.out.println("SQL: " + sql);

			// Execute Statement - Get ResultSet by Column Name
			rs = stmt.executeQuery();

			//Process the ResultSet
			while (rs.next()) {
				Group grp = new Group();

				grp.setId(rs.getInt("id"));
				grp.setName(rs.getString("name"));
				grp.setCreated(rs.getString("created"));
				grp.setModified(rs.getString("modified"));
				grp.setHighschool(rs.getString("highschool"));
				grp.setTeacher(rs.getString("teacher"));
				grp.setClassPeriod(rs.getString("classPeriod"));
				grp.setSurveyStartDate(rs.getString("surveyStartDate"));
				grp.setSurveyEndDate(rs.getString("surveyEndDate"));
				grp.setEventDate(rs.getString("eventDate"));
				grp.setStudentAccessCode(rs.getString("studentAccessCode"));
				//change in code = josh
				grp.setmarriageChoice(rs.getString("marriageChoice"));
				grp.setcoordinatorCode(rs.getString("coordinatorCode"));

				lstGroup.add(grp);
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

		return lstGroup;
	}
	
	//  ==========================  UPDATE  ==========================
	/**
	 * Update.
	 * 
	 * @param grp
	 *            : the Group being updated
	 * @return Returns an integer:<br>
	 *         0: Failure<br>
	 *         1: Success
	 */
	public int update(Group grp) {
		//Check Table & Create Table if it doesn't already exist
		boolean success = createTable();
		System.out.println("Check if table exists (create if doesn't exist). Table exists: " + success);

		// Variable Declarations
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
			int id = grp.getId();
		try {
			// Load Driver & Connect to Dbase
			conn = DbUtil.createConnection();

			// Create SQL Statement
			String sql = "UPDATE Group_ SET name=?, created=?, modified=?, highschool=?, " +
						"teacher=?, classPeriod=?, surveyStartDate=?, surveyEndDate=?, eventDate=?, studentAccessCode=?, marriageChoice=?, coordinatorCode = ? "+ "WHERE id="+id+"";

			stmt = conn.prepareStatement(sql);
			stmt.setInt(12, grp.getId());
			stmt.setString(1, grp.getName());
			stmt.setString(2, grp.getCreated());
			stmt.setString(3, grp.getModified());
			stmt.setString(4, grp.getHighschool());
			stmt.setString(5, grp.getTeacher());
			stmt.setString(6, grp.getClassPeriod());
			stmt.setString(7, grp.getSurveyStartDate());
			stmt.setString(8, grp.getSurveyEndDate());
			stmt.setString(9, grp.getEventDate());
			stmt.setString(10, grp.getStudentAccessCode());
			stmt.setString(11, grp.getmarriageChoice());
			stmt.setString(12, grp.getcoordinatorCode());
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
	 * Insert a new Group.
	 * 
	 * @param name
	 * @param created
	 * @param modified
	 * @param highschool
	 * @param teacher
	 * @param classPeriod 
	 * @param surveyStartDate
	 * @param surveyEndDate
	 * @param eventDate
	 * @param studentAccessCode
	 * @return Returns an integer:<br>
	 *         0: Failure<br>
	 *         1: Success
	 */
	public int insert(String name, String created, String modified, String highschool, 
			String teacher, String classPeriod, String surveyStartDate, String surveyEndDate,
			String eventDate, String studentAccessCode, String marriageChoice, String coordinatorCode){

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
			String sql = "INSERT INTO Group_ (name, created, modified, highschool, " +
						"teacher, classPeriod, surveyStartDate, surveyEndDate, " +
						"eventDate, studentAccessCode, marriageChoice, coordinatorCode) " + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

			stmt = conn.prepareStatement(sql);
			stmt.setString(1, name);
			stmt.setString(2, created);
			stmt.setString(3, modified);
			stmt.setString(4, highschool);
			stmt.setString(5, teacher);
			stmt.setString(6, classPeriod);
			stmt.setString(7, surveyStartDate);
			stmt.setString(8, surveyEndDate);
			stmt.setString(9, eventDate);
			stmt.setString(10, studentAccessCode);
			//change in code = josh
			stmt.setString(11, marriageChoice);
			stmt.setString(12, coordinatorCode);
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
	 * Delete a Group.
	 * 
	 * @param grp
	 *            : The Group to delete
	 * @return Returns an integer:<br>
	 *         0: Failure<br>
	 *         1: Success
	 */
	public int delete(Group grp) {
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
			String sql = "DELETE FROM Group_ WHERE id = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, grp.getId());
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
		String tableName = "Group_";
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
		String tableName = "Group_";
		
		// Create SQL Statement
		String sql = "CREATE TABLE IF NOT EXISTS " + tableName
				+ " ('id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
				+ "'name' VARCHAR NOT NULL,"
				+ "'created' DATETIME,"
				+ "'modified' DATETIME,"	
				+ "'highschool' VARCHAR,"
				+ "'teacher' VARCHAR,"
				+ "'classPeriod' VARCHAR,"
				+ "'surveyStartDate' DATETIME,"
				+ "'surveyEndDate' DATETIME,"
				+ "'eventDate' DATETIME,"
				+ "'studentAccessCode' VARCHAR"
				+ "'marriageChoice' VARCHAR)";
		
		success = DbUtil.createTable(tableName, sql);
		
		return success;
	}
	
//==========================Josh's MarriageChoice===========================================
	
	public String marriageChoice(String choice) {
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
			strWhere = "WHERE id = " + choice;
			sql = "SELECT marriageChoice FROM Group_ " + strWhere;
			stmt = conn.prepareStatement(sql);
			System.out.println("SQL: " + sql);
			rs = stmt.executeQuery();
			while(rs.next()){
			choice = rs.getString("marriageChoice");
			System.out.println(choice);
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
		return String.valueOf(choice);
	} //end find
	
	public List<Group> findCoordinatorGroups(String code) {
		//Check Table & Create Table if it doesn't already exist
		boolean success = createTable();
		System.out.println("Check if table exists (create if doesn't exist). Table exists: " + success);
				
		// Variable Declarations
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		List<Group> lstGroup = new ArrayList<Group>();

		String sql = "";

		try {
			// Load Driver & Connect to Dbase
			conn = DbUtil.createConnection();

			// Create SQL Statement
			sql = "SELECT * FROM Group_ WHERE coordinatorCode = '"+code+"'";
			stmt = conn.prepareStatement(sql);
			System.out.println("SQL: " + sql);

			// Execute Statement - Get ResultSet by Column Name
			rs = stmt.executeQuery();

			//Process the ResultSet
			while (rs.next()) {
				Group grp = new Group();

				grp.setId(rs.getInt("id"));
				grp.setName(rs.getString("name"));
				grp.setCreated(rs.getString("created"));
				grp.setModified(rs.getString("modified"));
				grp.setHighschool(rs.getString("highschool"));
				grp.setTeacher(rs.getString("teacher"));
				grp.setClassPeriod(rs.getString("classPeriod"));
				grp.setSurveyStartDate(rs.getString("surveyStartDate"));
				grp.setSurveyEndDate(rs.getString("surveyEndDate"));
				grp.setEventDate(rs.getString("eventDate"));
				grp.setStudentAccessCode(rs.getString("studentAccessCode"));
				//change in code = josh
				grp.setmarriageChoice(rs.getString("marriageChoice"));
				grp.setcoordinatorCode(rs.getString("coordinatorCode"));

				lstGroup.add(grp);
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

		return lstGroup;
	}
	

	public List<Group>diplayGroups() {
		//Check Table & Create Table if it doesn't already exist
		boolean success = createTable();
		System.out.println("Check if table exists (create if doesn't exist). Table exists: " + success);
				
		// Variable Declarations
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		List<Group> lstGroup = new ArrayList<Group>();

		String sql = "";

		try {
			// Load Driver & Connect to Dbase
			conn = DbUtil.createConnection();

			// Create SQL Statement
			sql = "SELECT * FROM Group_ Order by highschool ASC";
			stmt = conn.prepareStatement(sql);
			System.out.println("SQL: " + sql);

			// Execute Statement - Get ResultSet by Column Name
			rs = stmt.executeQuery();

			//Process the ResultSet
			while (rs.next()) {
				Group grp = new Group();

				grp.setId(rs.getInt("id"));
				grp.setName(rs.getString("name"));
				grp.setCreated(rs.getString("created"));
				grp.setModified(rs.getString("modified"));
				grp.setHighschool(rs.getString("highschool"));
				grp.setTeacher(rs.getString("teacher"));
				grp.setClassPeriod(rs.getString("classPeriod"));
				grp.setSurveyStartDate(rs.getString("surveyStartDate"));
				grp.setSurveyEndDate(rs.getString("surveyEndDate"));
				grp.setEventDate(rs.getString("eventDate"));
				grp.setStudentAccessCode(rs.getString("studentAccessCode"));
				//change in code = josh
				grp.setmarriageChoice(rs.getString("marriageChoice"));
				grp.setcoordinatorCode(rs.getString("coordinatorCode"));

				lstGroup.add(grp);
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

		return lstGroup;
	}
	

	
    //   ========================  MAIN METHOD  ==================== 
	public static void main(String[] args) {		
		List<Group> lstGrp = new ArrayList<Group>();
		Group grp = new Group();
        //Create GroupsDAO & Group Objs and Validate Login
        GroupsDAO g1 = new GroupsDAO();
        
        System.out.println("DB string = " + DAO.DB);
        
        //Returns List of Groups matching search criteria (even if only 1)
        lstGrp = g1.search("name", "Group A"); //Lookup by name         
        //Extract single Group obj from List
        //Loop thru Group List (should only be 1 obj in list)
        for (int i = 0; i < lstGrp.size(); i++)
        {
        	if (i == 0) {
        		grp = lstGrp.get(i);
        		System.out.println("Extracted Group obj from List.");
        		grp.display();
        	} else { //more than one obj in list
        		System.out.println("Error - Duplicate Username.");
        	} //end if
        } //end for
        
        
        boolean n = g1.createTable();
        System.out.println("Create Table Returns: " + n);

		
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