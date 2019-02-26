/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx_municipality_projects_application.datamodels;

import Utilities.DateUtilities;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author mtsougranis
 * Tasks Model class. Contains the data related to tasks
 * The methods are named according to java fx property binding rules (https://docs.oracle.com/javase/8/javafx/properties-binding-tutorial/binding.htm#JFXBD107)
 */

public class Task {
    private SimpleIntegerProperty actionId;
    private SimpleIntegerProperty taskId;
    private SimpleStringProperty title;
    private SimpleLongProperty dateFrom;
    private SimpleLongProperty dateTo;
    private SimpleStringProperty priority;
    private SimpleBooleanProperty completed;
    
    //name of the table in the database
    public final static String DB_TABLE_NAME="task";
   
    //columns in the database
    class DB_TABLE_COLUMNS
    {
        public static final String COLUMN_ID="id";
        public static final String COLUMN_ACTION_ID="actionId";
        public static final String COLUMN_TITLE="title";
        public static final String COLUMN_DATEFROM="dateFrom";
        public static final String COLUMN_DATETO="dateTo";
        public static final String COLUMN_PRIORITY="priority";
        public static final String COLUMN_COMPLETED="completed";
    }
    
    //task priority
    public class PRIORITY
    {
        public static final String PRIORITY_LOW="(1) Low";
        public static final String PRIORITY_MEDIUM="(2) Medium";
        public static final String PRIORITY_HIGH="(3) High";
    }
    
    
    /**
     * class constructor
     * @param title
     * @param dateFrom
     * @param dateTo
     * @param priority
     * @param completed 
     */
    public Task(int actionId,int taskId,String title, long dateFrom, long dateTo, String priority, boolean completed) {
        this.actionId= new SimpleIntegerProperty(actionId);
        this.taskId=new SimpleIntegerProperty(taskId);
        this.title = new SimpleStringProperty(title);
        this.dateFrom = new SimpleLongProperty(dateFrom);
        this.dateTo = new SimpleLongProperty(dateTo);
        this.priority = new SimpleStringProperty(priority);
        this.completed = new SimpleBooleanProperty(completed);
    }
    
    public Task(){
        //if this constructor is selected, create a task with the following default values
        //this(0,"",DateUtilities.getCurrentDateAsLongDays(),DateUtilities.getCurrentDateAsLongDays()+1,"",false);
    }
    
    public SimpleIntegerProperty actionIdProperty()
    {
        return actionId;
    }

    public SimpleIntegerProperty taskIdProperty(){
        return taskId;
    }
    
    public SimpleStringProperty titleProperty(){
        return title;
    }
    
    public SimpleLongProperty dateFromProperty(){
        return dateFrom;
    }
    
    public SimpleLongProperty dateToProperty(){
        return dateTo;
    }
    
    public SimpleStringProperty priorityProperty(){
        return priority;
    }
    
    public SimpleBooleanProperty completedProperty(){
        return completed;
    }
    
    final public void setActionId(int actionId){
        this.actionId.set(actionId);
    }
    
    final public void setTaskId(int tId){
        this.taskId.set(tId);
    }
    
    final public void setTitle(String title) {
        this.title.set(title);
    }

    final public void setDateFrom(long dateFrom) {
        this.dateFrom.set(dateFrom);
    }

    final public void setDateTo(long dateTo) {
        this.dateTo.set(dateTo);
    }

    final public void setPriority(String priority) {
        this.priority.set(priority);
    }

   final public void setCompleted(boolean completed) {
        this.completed.set(completed);
    }

   final public int getActionId(){
       return actionId.get();
   }
   
   final public int getTaskId(){
       return taskId.get();
   }
   
    final public String getTitle() {
        return title.get();
    }

    final public long getDateFrom() {
        return dateFrom.get();
    }

    final public long getDateTo() {
        return dateTo.get();
    }

    final public String getPriority() {
        return priority.get();
    }

    final public boolean getCompleted() {
        return completed.get();
    }
       
}
