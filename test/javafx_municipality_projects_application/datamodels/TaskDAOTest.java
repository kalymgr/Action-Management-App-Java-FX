/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx_municipality_projects_application.datamodels;

import java.util.ArrayList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mtsougranis
 */
public class TaskDAOTest {
    
    public TaskDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of insertTask method, of class TaskDAO.
     */
    /*
    @Test
    public void testInsertTask() throws Exception {
        System.out.println("insertTask");
        //create new test task, for action with id 1
        Task task = new Task(1,0,"testing insert task",1,1,
                Task.PRIORITY.PRIORITY_LOW,true);
        //execute the insert query and get the id of the new task
        int result = TaskDAO.insertTask(task);
        //print the task id
        System.out.println("Id of task inserted: "+result);
        assertNotEquals(-1,result);
        
        //create a new task for an action that does not exist (db constraints violation)
        Task task2=new Task(127492,0,"testing insert task",1,1,
                Task.PRIORITY.PRIORITY_LOW,true);
        //execute query and get the id returned which should be -1
        int result2 = TaskDAO.insertTask(task2);
        assertEquals(-1,result2);
        
    }
*/
    
    @Test
    public void testUpdateTask() throws ClassNotFoundException
    {
        //create new task object, for action with id 1
        Task task = new Task(1,1,"testing updating2 task",1,1,
                Task.PRIORITY.PRIORITY_MEDIUM,true);
        //execute the update query
        TaskDAO.updateTask(task);
    }
    
    @Test
    public void testDeleteTask() throws ClassNotFoundException
    {
        //id of the task that will be deleted
        int taskId=24;
        TaskDAO.deleteTask(24);
    }
    
    @Test
    public void testGetTaskDataByTaskId(){
        //get task data for an existing task id
        int taskId=1;
        Task task=TaskDAO.getTaskDataByTaskId(taskId);
        assertFalse(task==null);
        
        //get task data for a non existing task id
        int taskId2=-3;
        Task task2=TaskDAO.getTaskDataByTaskId(taskId2);
        assertTrue(task2==null);
    }
    
    @Test
    public void testGetTaskDataByActionId()
    {
        //get task data for an existing action
        int actionId=1;
        ArrayList<Task> aList=TaskDAO.getTaskDataByActionId(actionId);
        assertTrue(aList.size()>0);
        
        //get task data for a non existing action
        int actionId2=-21;
        
        //assertTrue(TaskDAO.getTaskDataByActionId(actionId2)==null);
    }
    
    
    @Test
    public void testInsertMultipleTasks() throws ClassNotFoundException
    {
        //create a tasks list and add three tasks
        ArrayList<Task> tasksList=new ArrayList<>();
        for (int i=0;i<3;i++)
        {
            tasksList.add(
                    new Task(1,0,"testing insert task",1,1,
                    Task.PRIORITY.PRIORITY_LOW,true)
            );
        }
        TaskDAO.insertMultipleTasks(tasksList);
    }
    
    @Test
    public void testUpdateMultipleTasks() throws ClassNotFoundException
    {
        //create a tasks list with tasks already existing in the database
        ArrayList<Task> tasksList=new ArrayList<>();
        
        tasksList.add(
                new Task(1,30,"testing insert task",1,1,
                Task.PRIORITY.PRIORITY_MEDIUM,false)
        );
        tasksList.add(
                new Task(1,31,"testing insert task",1,1,
                Task.PRIORITY.PRIORITY_MEDIUM,false)
        );
        
        TaskDAO.updateMultipleTasks(tasksList);
    }
    
     @Test
    public void testDeleteMultipleTasks() throws ClassNotFoundException
    {
        //create a tasks list with tasks already existing in the database
        ArrayList<Task> tasksList=new ArrayList<>();
        
        tasksList.add(
                new Task(1,30,"testing insert task",1,1,
                Task.PRIORITY.PRIORITY_MEDIUM,false)
        );
        tasksList.add(
                new Task(1,31,"testing insert task",1,1,
                Task.PRIORITY.PRIORITY_MEDIUM,false)
        );
        
        TaskDAO.deleteMultipleTasks(tasksList);
    }
}
