/**
 * 
 */
package obj;

/**
 * @author Me
 *
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Sample
{
  public static void main(String[] args) throws ClassNotFoundException
  {
    // load the sqlite-JDBC driver using the current class loader
    Class.forName("org.sqlite.JDBC");
    System.out.println("Driver");

    Connection connection = null;
    
    try
    {
      // create a database connection
      connection = DriverManager.getConnection("jdbc:sqlite:..\\RealityUWeb\\realityu_production.dat"); //works!
      	//connection = DriverManager.getConnection("jdbc:sqlite:" + System.getProperty("user.dir") + "\\realityu_production.dat");
      		//Above works here run as Java file, but creates different path for user.dir when use Server so doesn't work then
      	//connection = DriverManager.getConnection("jdbc:sqlite:D:\\MyDocuments\\CTCSpring2014\\CIST2931_AdvSysProj\\Workspace\\RealityUWeb\\realityu_production.dat");
      		//Above works: complete path to dbase
      System.out.println("User.dir Property = " + System.getProperty("user.dir"));
      System.out.println("User.home Property = " + System.getProperty("user.home"));
      System.out.println("Got connection.");
      
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);  // set timeout to 30 sec.

      String sql = "SELECT * FROM Survey";
      statement = connection.createStatement();
      System.out.println("Create Statement.");
      
      ResultSet rs = statement.executeQuery(sql);
      while(rs.next())
      {
        // read the result set
        System.out.println("ID = " + rs.getInt("id"));
        System.out.println("First Name = " + rs.getString("fName"));
        System.out.println("Last Name = " + rs.getString("lName"));
        System.out.println("=====================================");
      }
    }
    catch(SQLException e)
    {
      // if the error message is "out of memory", 
      // it probably means no database file is found
      System.err.println(e.getMessage());
    }
    finally
    {
      try
      {
        if(connection != null)
          connection.close();
      }
      catch(SQLException e)
      {
        // connection close failed.
        System.err.println(e);
      }
    }
  }
}

