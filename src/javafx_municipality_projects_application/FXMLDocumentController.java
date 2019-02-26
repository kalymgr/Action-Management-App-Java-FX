/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javafx_municipality_projects_application;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.event.WeakEventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javafx.util.converter.DefaultStringConverter;
import javafx_municipality_projects_application.datamodels.Action;
import javafx_municipality_projects_application.datamodels.ActionDAO;
import javafx_municipality_projects_application.datamodels.Task;
import javafx_municipality_projects_application.datamodels.TaskDAO;

/**
 *Class that controls the Project/Action form of the application
 * @author mtsougranis
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Label lblActionId;
    @FXML
    private TextField txtActionId;
    @FXML
    private Label lblActionName;
    @FXML
    private TextField txtActionName;
    @FXML
    private TableView<Task> tblTasks;
    @FXML
    private TableColumn<Task, String> columnTitle;
    @FXML
    private TableColumn<Task, Number> columnDateFrom;
    @FXML
    private TableColumn<Task, Number> columnDateTo;
    @FXML
    private TableColumn<Task, String> columnPriority;
    @FXML
    private TableColumn<Task, Boolean> columnCompleted;
    @FXML
    private Button btnNewTask;
    @FXML
    private Button btnDeleteTask;
    
    //list with the task data
    ObservableList<Task> taskData;
    
    //variable that shows if the record has been modified
    boolean recordModified;
    
    //structure that holds the task ids of the task records modified
    //each time a task is modified, it's id will be added to the following set
    Set taskUpdatedIds;
    
    //setup the object that holds the action/project data
    private Action action;
    
    @FXML
    private Label lblRecordStatus;
    @FXML
    private Button btnSaveRecord;
    @FXML
    private Button btnNewAction;
    @FXML
    private Button btnDeleteAction;
      
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        //initialize a new action object
        //action=new Action();
        
        //action id of the action that will be retrieved from the database. 
        // For testing purposes, we use action with id=1
        int actionId=1;
        
        //load action data or create a new action
        if (ActionDAO.getActionData(actionId)!=null)
            loadActionData(actionId);
        else
            action=new Action();
        
        //create bidirectional bindings between the values of the action object and the form fields
        //this means that any change on the fields or the object data will cause a bidirectional update
        //bindDirectional is called on textfields so they can load the initial values from the action object
        txtActionId.textProperty().bindBidirectional(action.idProperty());
        txtActionName.textProperty().bindBidirectional(action.titleProperty());
        
        // TODO
        System.out.println("Controller initialized");
        
        setRecordStatus(false);
        
        
       
        //Initialize tasks table
        init_TasksTable();   
        
       //track when changes to the data have happened
       trackMainRecordModifications();
       
       //initialize the set of the tasks ids, for the tasks that have been updated
       taskUpdatedIds=new HashSet();
       
    }
    
    /**
     * Method that load all the data of an action, given an action id
     * @param actionId 
     */
    public void loadActionData(int actionId)
    {
        //load action basic data
        action=ActionDAO.getActionData(actionId);
        
        //load tasks data
        //create taskData observable list
        ArrayList<Task> tasksArrayList = TaskDAO.getTaskDataByActionId(actionId);
        
        //create the observable list, with an extractor of the fields (observable values)
        taskData = FXCollections.observableList(tasksArrayList,
    (Task tp) -> new Observable[]{tp.titleProperty(),tp.dateFromProperty(),
        tp.dateToProperty(),tp.priorityProperty(),tp.completedProperty()});
    }
    
    /**
     * Method that returns the record status (modified or not)
     * @return 
     */
    private boolean getRecordStatus()
    {
        return recordModified;
    }
    
    /**
     * Method that tracks record (data) modifications to the main fields of
     * the project/action form, so as to enable the save button
     * and prompt the user to store the altered data
     */
    private void trackMainRecordModifications(){
        //track when  basic form fields of action/project have been edited
       txtActionName.textProperty().addListener((observable) -> {
           setRecordStatus(true);
           //System.out.println("Observable: "+observable.toString());
           
       });
       
       //TODO: add listeners for the rest of the fields
               
    }

    /**
     * Method that sets the record status and shows message
     * @param recordStatus 
     */
    void setRecordStatus(boolean recordStatus){
        this.recordModified=recordStatus;
         if (recordModified==false){
            lblRecordStatus.setText("Η εγγραφή δεν έχει τροποποιηθεί");
        }
        else
        {
            lblRecordStatus.setText("Η εγγραφή έχει τροποποιηθεί");
        }
    }
    
    private void setUpTasksObservableData(){
        
         ListChangeListener<Task> listener=new ListChangeListener<Task>(){
           @Override
           public void onChanged(ListChangeListener.Change<? extends Task> c) {
               //System.out.println("ListChangeListener activated");
               setRecordStatus(true);
               
               while(c.next()){
                    //System.out.println("next: ");
                    if(c.wasAdded()){
                        //System.out.println("- wasAdded");
                    }
                    if(c.wasPermutated()){
                        //System.out.println("- wasPermutated");
                    }
                    if(c.wasRemoved()){
                        //System.out.println("- wasRemoved");
                    }
                    if(c.wasReplaced()){
                        //System.out.println("- wasReplaced");
                    }
                    if(c.wasUpdated()){//in case a record has been modified/updated
                        
                        //get the id of the task that was updated
                        Task taskUpdated=                     
                            tblTasks.getItems().get(c.getFrom());//.getTaskId();
                        int taskUpdatedId=taskUpdated.getTaskId();
                        
                        //store the task in a structure that will be used when storing
                        //the updated data in the database
                        taskUpdatedIds.add(taskUpdatedId);
                        
                        
                    }
                }
                 
            }
       };
       taskData.addListener(listener);
    }

    /**
     * Method that initializes the tasks table (insert taskData, associate tasks model with columns etc.)
     */
    void init_TasksTable(){
        
        //setup tasks data
        setUpTasksObservableData();
                 
        //associate table columns with model properties
        columnTitle.setCellValueFactory(new Callback<CellDataFeatures<Task, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<Task, String> p) {
                // p.getValue() returns the Task instance for a particular TableView row
                return p.getValue().titleProperty();
            }
        });     
        
        columnDateFrom.setCellValueFactory(new Callback<CellDataFeatures<Task, Number>, ObservableValue<Number>>() {
            public ObservableValue<Number> call(CellDataFeatures<Task, Number> p) {
                // p.getValue() returns the Task instance for a particular TableView row
                
                return p.getValue().dateFromProperty();
            }
        }); 
        
        columnDateTo.setCellValueFactory(new Callback<CellDataFeatures<Task, Number>, ObservableValue<Number>>() {
            public ObservableValue<Number> call(CellDataFeatures<Task, Number> p) {
                // p.getValue() returns the Task instance for a particular TableView row
                return p.getValue().dateToProperty();
            }
        });
        
        columnPriority.setCellValueFactory(new Callback<CellDataFeatures<Task, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<Task, String> p) {
                // p.getValue() returns the TAsk instance for a particular TableView row
                return p.getValue().priorityProperty();
            }
        });
        
        columnCompleted.setCellValueFactory(new Callback<CellDataFeatures<Task, Boolean>, ObservableValue<Boolean>>() {
            public ObservableValue<Boolean> call(CellDataFeatures<Task, Boolean> p) {
                // p.getValue() returns the Person instance for a particular TableView row
                return p.getValue().completedProperty();
            }
        });   
               
        //give the ability to select multiple cells
        tblTasks.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
                
        //associate taskData with table
        tblTasks.setItems(taskData);
              
        //---set table editable and change the content of cells depending on type
        //title column
         columnTitle.setCellFactory((param) -> {
             return new TextFieldTableCell<>(new DefaultStringConverter()); //To change body of generated lambdas, choose Tools | Templates.
         });        
        //completed column
         columnCompleted.setCellFactory((param) -> {
             return new CheckBoxTableCell<>(); //To change body of generated lambdas, choose Tools | Templates.
         });
         //priority column
         columnPriority.setCellFactory((param) -> {
             return new ChoiceBoxTableCell<>(
                     FXCollections.observableArrayList("Low","Medium","High")
             ); //To change body of generated lambdas, choose Tools | Templates.
             
         });
         
         //column date from
         columnDateFrom.setCellFactory((param) -> {
             return new DatePickerCell(taskData); //To change body of generated lambdas, choose Tools | Templates.
         });
         
         //column date to
         columnDateTo.setCellFactory((param) -> {
             return new DatePickerCell(taskData); //To change body of generated lambdas, choose Tools | Templates.
             
         });
        
        //set Completed field as a checkbox
        //columnCompleted.setEditable(true);
        
        //set columns prefered widths
        columnTitle.setPrefWidth(250);
        columnDateFrom.setPrefWidth(100);
        columnDateTo.setPrefWidth(100);         
    }
    
    /**
     * Method that sets the form fields empty for a new action to be inserted
     */
    private void setFormFieldsEmpty()
    {
        txtActionId.setText("");
        txtActionName.setText("");
        taskData.clear();
    }
    
    /**
     * Method that inserts a new action/project in the database 
     * and updates the Action Id text field
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    private void insertAction() throws ClassNotFoundException,SQLException
    {
        int recordInsertedId=ActionDAO.insertAction(action);
        action.setId(String.valueOf(recordInsertedId));
        
    }
    
    /**
     * Method that updates an action
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    private void updateAction() throws ClassNotFoundException,SQLException
    {
        
        ActionDAO.updateAction(action);
    }
    
    /**
     * Method for deleting an action
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    private void deleteAction() throws ClassNotFoundException,SQLException
    {
        if (action.getId()!="")//if the action id field has a value
        {
            //delete the action
            ActionDAO.deleteAction(Integer.parseInt(action.getId()));
        }
    }

    @FXML
    private void btnNewTaskButtonPressed(ActionEvent event) {
        //tblTasks.getItems().add(new Task());
        taskData.add(new Task());
    }

    @FXML
    private void btnDeleteTaskPressed(ActionEvent event) {
        
        
        if (tblTasks.getItems().size()>0){
            int index=tblTasks.getSelectionModel().getFocusedIndex();
            tblTasks.getItems().remove(index);
            System.out.println("Η εργασία στη θέση "+index+" διαγράφηκε");
            System.out.println("Number of tasks: "+taskData.size());
        }
        else{
            System.out.println("Δεν υπάρχουν εργασίες προς διαγραφή");
        }
    }

    
    /**
     * Method that handles the event that occurs when save button is pressed
     * @param event
     */
    @FXML
    private void btnSaveRecordPressed(ActionEvent event) throws Exception
    {
        //update the basic data of the project/action, if needed
        //update all the modified tasks records in the database
        //save all the new tasks records in the database
        //delete all the tasks records (that have been deleted from the tableview) in the database
        if (getRecordStatus()){
            if (action.getId().length()==0){//Action doesn't have id, so new action should be added
                insertAction();
                System.out.println("New action added");
            }
            else
            {
               updateAction();
               System.out.println("Action updated");    
            }
             

            //set the flag that the records has been modified as false
            setRecordStatus(false);
        }
        
    }  

    /**
     * Button pressed for creating a new action
     * @param event 
     */
    @FXML
    private void btnNewActionPressed(ActionEvent event) {
        setFormFieldsEmpty();
    }

    @FXML
    private void btnDeleteActionPressed(ActionEvent event) throws Exception
    {
        deleteAction();
        setFormFieldsEmpty();
    }
}

