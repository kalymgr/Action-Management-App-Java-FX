/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx_municipality_projects_application.datamodels;

import Utilities.DbUtil;
import com.sun.net.httpserver.Authenticator;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author mtsougranis
 */
public class ActionDAO {
    
    /**
     * Method that inserts a new action in the db table called action
     * @param actionTitle
     * @return Returns the record id of the record inserted. Else, returns -1.
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public static int insertAction(Action action) throws SQLException,ClassNotFoundException
    {
        int recordInsertedId=-1;
        String sql="insert into "+Action.DB_TABLE_NAME+"("
                + Action.DB_TABLE_COLUMNS.COLUMN_TITLE+""
                + ")"
                + "values ("
                + "'"+action.getTitle()+"'"
                + ");";
        try
        {
            recordInsertedId=DbUtil.dbExecuteInsertUpdateQuery(sql);
            return recordInsertedId;
        }
        catch (SQLException e)
        {
            System.out.println("SQL Exception occured while inserting the data");
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * Method that updates a specific action, given an action id
     * @param actionId
     * @param actionTitle
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public static void updateAction(Action action) throws SQLException,ClassNotFoundException
    {
        //String sql="update action set title='"+action.getTitle()+"' where id="+action.getId();
        
        String sql="update "+Action.DB_TABLE_NAME+" set "
                + Action.DB_TABLE_COLUMNS.COLUMN_TITLE+"= '"+action.getTitle()+"'"
                + " where "
                + Action.DB_TABLE_COLUMNS.COLUMN_ID+"="+action.getId();
        try
        {
            DbUtil.dbExecuteInsertUpdateQuery(sql);
        }
        catch (SQLException e)
        {
            System.out.println("Error occured while updating the Action record in the database");
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * Method used for deleting actions
     * @param actionId
     * @return the number of rows deleted
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public static int deleteAction(int actionId) throws SQLException,ClassNotFoundException
    {
        String sql="delete from "+Action.DB_TABLE_NAME
                + " where "
                + Action.DB_TABLE_COLUMNS.COLUMN_ID+"="+actionId;
        
        int rowsAffected=0;
        try
        {
            rowsAffected=DbUtil.dbExecuteDeleteQuery(sql);
            System.out.println(rowsAffected+" records deleted");
            return rowsAffected;
        }
        catch (SQLException e)
        {
            System.out.println("Error occured while deleting the record");
            e.printStackTrace();
            throw e;
        }          
    }
    
    /**
     * Method that returns the Action data for a specific action id. The data
     * returned is of type Action, or null if there is no record in the database
     *
     * @param actionId
     * @return Action item
     * @throws ClassNotFoundException
     */
    public static Action getActionData(int actionId) 
    {
        String sql="select * from action where id="+actionId;
        
        try
        {
            //get the resultset data for the specific action
            ResultSet rs=DbUtil.dbExecute(sql);
            if (rs.first())//if there were search results
            {
                Action action=new Action();
                int id=rs.getInt(Action.DB_TABLE_COLUMNS.COLUMN_ID);
                //action.setId(String.valueOf(id));
                //action.setTitle(rs.getString(Action.DB_TABLE_COLUMNS.COLUMN_TITLE));
                action=new Action(String.valueOf(id),rs.getString(Action.DB_TABLE_COLUMNS.COLUMN_TITLE));
                
                return action;
            }
            else //no search results
            {
                return null;
            }
            
        }
        catch (SQLException e)
        {
            System.out.println("Problem with retrieving the data for "
                    + "the specific action from the database");
            return null;
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }
        
    }
    
}

