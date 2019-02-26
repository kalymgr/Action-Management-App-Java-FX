/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * TODO:
 * - Exception handling must be improved
 * - Change the structure (add interfaces etc) so as to support other dbms except from MySQL
 */
package Utilities;

import com.sun.rowset.CachedRowSetImpl;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author mtsougranis
 */
public class DbUtil {
    private static final String JDBC_DRIVER="com.mysql.jdbc.Driver";
    private static Connection connection=null;
    private static String dbName="actionapplicationdb";
    private static final String CONN_STR="jdbc:mysql://localhost/"+dbName+"?useUnicode=true&characterEncoding=utf-8";
    
    /**
     * Method that sets up the SQL Connection
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public static void dbConnect() throws SQLException,ClassNotFoundException
    {
        try
        {
            Class.forName(JDBC_DRIVER);//if not found, throws ClassNotFoundException
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
        }
        
        //System.out.println("JDBC driver found");
        
        try
        {
            //get the connection
            connection=DriverManager.getConnection(CONN_STR,"root","");
        }
        catch (SQLException e)
        {
            System.out.println("Connection failed.");
            throw e;
        }       
    }
    
    /**
     * Method that disconnects the database
     * @throws SQLException 
     */
    public static void dbDisconnect() throws SQLException
    {
        try
        {
            if (connection!=null && !connection.isClosed())
            {
                connection.close();
            }
        }
        catch (Exception e)
        {
            throw e;
        }
                
    }
    
    /**
     * Method used for delete queries
     * @param sqlStm
     * @return Returns the number of rows affected
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public static int dbExecuteDeleteQuery(String sqlStm) throws SQLException,ClassNotFoundException
    {
         Statement stmt=null;
          try
        {
            dbConnect();
            stmt=connection.createStatement();
            int rowCount= stmt.executeUpdate(sqlStm);
            
            return rowCount;
            
        }
        
        catch (SQLException e)
        {
            System.out.println("Problem with executing the sql statement. SQL Exception caught. "+e);
            //exception must be thrown for the related test 
            //called "testDbExecuteQuerySQLExceptionHandling" (for exception handling) to work properly
            throw e;
        }
        
        finally
        {
            if (stmt!=null)
            {
                stmt.close();
            }
            
            dbDisconnect();  
        }
         
    }
    
    /**
     * Method used for insert and update sql statements
     * @param sqlStm
     * @return It returns the record id that has been automatically generated from the database, in case
     * of insertion. If no key is generated, it returns -1
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public static int dbExecuteInsertUpdateQuery(String sqlStm) throws SQLException,ClassNotFoundException
    {
        Statement stmt=null;
        
        try
        {
            dbConnect();
            stmt=connection.createStatement();
            int rowCount= stmt.executeUpdate(sqlStm,Statement.RETURN_GENERATED_KEYS);
            
            ResultSet rsGenKeys=stmt.getGeneratedKeys(); //get generated keys, in case of insertion
            int newRecordId=-1; //variable for the new record id, in case of insertion
            if (rsGenKeys != null) //if an insert statement has occured and a new generated value has been returned
            {
                //get the new record key
                if (rsGenKeys.first())
                {
                    newRecordId=rsGenKeys.getInt(1);
                }
            }
            
            return newRecordId;
            
        }
        
        catch (SQLException e)
        {
            System.out.println("Problem with executing the sql statement. SQL Exception caught. "+e);
            //exception must be thrown for the related test 
            //called "testDbExecuteQuerySQLExceptionHandling" (for exception handling) to work properly
            throw e;
        }
        
        finally
        {
            if (stmt!=null)
            {
                stmt.close();
            }
            
            dbDisconnect();  
        }
    }
    
    /**
     *Method that executes an SQL Query and fetches a resultset
     * @param sqlQuery
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static ResultSet dbExecute(String sqlQuery) throws ClassNotFoundException,SQLException
    {
        Statement stmt=null;
        ResultSet rs=null;
        CachedRowSetImpl c=null;
        
        try
        {
            dbConnect();
            stmt=connection.createStatement();
            rs=stmt.executeQuery(sqlQuery);
            c=new CachedRowSetImpl();
            c.populate(rs);
        }
        catch (SQLException e)
        {
            System.out.println("Error occured in dbExecute method "+e);
            throw e;
        }
        finally
        {
            if (rs!=null)
            {
                rs.close();
            }
            if (stmt!=null)
            {
                stmt.close();
            }
            dbDisconnect();
        }
        return c;
        
    }
    
    
    /**
     * Method that converts the given date to a format suitable for storing in
     * the database
     * @param the days that have passed since the epoch day (1-1-1970)
     * @return 
     */
    public static String convertToDbDateFormat(long daysSinceEpoch)
    {
        LocalDate lDate=LocalDate.ofEpochDay(daysSinceEpoch);
                
        //mysql date format and mysql date formatter
        String mySQLDateFormatPattern="y-M-d";
        DateTimeFormatter df=DateTimeFormatter.ofPattern(mySQLDateFormatPattern);
        
        String newDate=lDate.format(df);
        return newDate;
    }
    
    /**
     * Method that converts the mysql db date to days since epoch day
     * @param dateString
     * @return 
     */
    public static long convertFromDbDateFormat(String dateString)
    {
        //mysql date format and mysql date formatter
        String mySQLDateFormatPattern="y-M-d";
        DateTimeFormatter df=DateTimeFormatter.ofPattern(mySQLDateFormatPattern);
        
        LocalDate lDate=LocalDate.parse(dateString,df);
        return DateUtilities.convertLocalDateToLongDays(lDate);
    }
    
    /**
     * Method that converts values true and false to the representation stored in
     * the MySQL database (1 for true and 0 for false)
     * @param value
     * @return 
     */
    public static int convertToMySQLBoolean(boolean value)
    {
        if (value)
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }
    
    /**
     * Method that converts from 1 and 0 (mysql boolean values) to true and false
     * @param mySqlValue
     * @return 
     */
    public static boolean convertFromMySQLBoolean(int mySqlValue)
    {
        if (mySqlValue==1)
            return true;
        else
            return false;
    }
}
