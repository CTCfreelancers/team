package dao;

import java.io.File;
import java.text.SimpleDateFormat;

/**
 * The Interface DAO sets the connection information for all DAO's.
 */
public interface DAO {

	// JDBC Driver and URL
	/** The Constant JDBC_DRIVER. */
	static final String JDBC_DRIVER = "org.sqlite.JDBC";

	/** The Constant DB. */
	static final String DB = "jdbc:sqlite:C:\\Users\\joshs laptop\\Desktop\\New folder\\RealityUWebSpring2014\\RealityUWeb\\realityu_production.dat";
	
	/** The Constant USER. */
	static final String USER = "project";

	/** The Constant PASS. */
	static final String PASS = "project";

	/** The Constant XML Jobs File. */
	static final File XML_DEFAULT_JOBS = new File("lib/jobs.xml");

	/** The Constant DATE_FORMAT. */
	static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	

	/**
	 * This method makes sure table exists<br>
	 * The DAO will check each time an action is performed.
	 * 
	 * @return Returns True/False
	 * 
	 */
	boolean checkTable();

	/**
	 * Creates a table if it doesn't exist
	 * 
	 * @return Returns True/False
	 */
	boolean createTable();
} //end interface
