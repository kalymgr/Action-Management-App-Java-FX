/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx_municipality_projects_application.datamodels;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mtsougranis
 */
public class ActionDAOTest {
    
    public ActionDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of insertAction method, of class ActionDAO.
     */
    @Test
    public void testInsertAction() throws Exception {
        
        Action action=new Action("0", "Δοκιμαστική δράση");
        String actionTitle = "Δοκιμαστική δράση";
        int recordInsertedId=ActionDAO.insertAction(action);
        assertFalse(recordInsertedId==-1);     
    }

    /**
     * Test of updateAction method, of class ActionDAO.
     */
    @Test
    public void testUpdateAction() throws Exception {
        System.out.println("updateAction");
        
        //try to update an existing record
        int actionId = 1;
        String actionTitle = "Ηλεκτρονική Διακίνηση εγγράφων";
        Action action=new Action("1","Ηλεκτρονική διακίνηση εγγράφων");
        ActionDAO.updateAction(action);
        
        //try to update a record with an invalid id
   
         Action action2=new Action("-11","Ηλ. διακ. εγγρ.");
        ActionDAO.updateAction(action2);
        
    }
    
    /**
     * Method that tests the testDeleteAction method
     * @throws Exception 
     */
    @Test
    public void testDeleteAction() throws Exception
    {
        //try to delete arecord
        int actionId=56;
        ActionDAO.deleteAction(actionId);
        
    }
    
    /**
     * Method that tests the getActionData - case the record exists in the database
     * @throws ClassNotFoundException 
     */
    @Test
    public void testGetActionData() throws ClassNotFoundException
    {
        //testing the scenario when the action exists in the database        
        Action a=ActionDAO.getActionData(1);
        System.out.println("Τίτλος της δράσης: "+a.getTitle());
        
        
        
    }
    
    /**
     * Method that tests the getActionData - case the record does not exist in the database
     * It should throw a NullPointerException
     * @throws ClassNotFoundException 
     */
    @Test (expected = NullPointerException.class)
    public void testGetActionDataNull() throws ClassNotFoundException
    {
        Action a=ActionDAO.getActionData(147617910);
        System.out.println("Τίτλος της δράσης: "+a.getTitle());
    }
    
    
}
