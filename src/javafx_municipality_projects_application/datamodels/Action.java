/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx_municipality_projects_application.datamodels;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author mtsougranis
 */
public class Action {
    private SimpleStringProperty id;
    private SimpleStringProperty title;
    
    //name of the table in the database
    public final static String DB_TABLE_NAME="action";
    
    //columns in the database
    class DB_TABLE_COLUMNS{
        public static final String COLUMN_ID="id";
        public static final String COLUMN_TITLE="title";
    }

    public Action(String id, String title) {
        //initialize the properties so they can be used for binding with the textfields
        this.id = new SimpleStringProperty(id);
        this.title = new SimpleStringProperty(title);
    }
    
    
    public Action(){
        //initialize the properties so they can be used for binding with the textfields
        id=new SimpleStringProperty();
        title=new SimpleStringProperty();
    }
    
    public SimpleStringProperty idProperty(){
        return id;
    }
    
    public SimpleStringProperty titleProperty(){
        return title;
    }
    
    public void setId(String id){
        this.id.set(id);
    }
    
    public void setTitle(String title)
    {
        this.setTitle(title);
    }
    
    public String getId(){
        return id.get();
    }
    
    public String getTitle(){
        return title.get();
    }
    
    
}
