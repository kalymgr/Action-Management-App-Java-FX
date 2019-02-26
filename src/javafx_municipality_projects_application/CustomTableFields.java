/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx_municipality_projects_application;

import Utilities.DateUtilities;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;
import javafx_municipality_projects_application.datamodels.Task;

/**
 *
 * @author kalymgr
 */
//class for the dates in the tasks table
class DatePickerCell<S,T> extends TextFieldTableCell<S, Number>{
    
    private ObservableList<S> observableData;
       
    public DatePickerCell(ObservableList<S> ol){
        setConverter(new DateStringConverter<>());
        
       observableData=ol; 
       /*
       if (!isEmpty()){
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler(){
            @Override
            public void handle(Event event){
                System.out.println("Mouse clicked");
                //startEdit();
                setText(getConverter().toString(getItem()));
                System.out.println("Editing?: "+isEditing());
            }
         });
       }
*/
       
    }  
     @Override
     public void updateItem(Number item,boolean empty){
         if (item!=null)
            //System.out.println("updateItem method called for item : "+item.longValue());
         super.updateItem(item, empty);
         if (!empty){
            String sDate=DateUtilities.convertDaystoStringDate(item.longValue());
            setText(sDate);
         }
     }
   
}

class DateStringConverter<Number> extends StringConverter<Number>{
    
    

    @Override
    public Number fromString(String string){        
        
        Number n=
                (Number) new Long(DateUtilities.convertStringDateToDaysSinceEpochDay(string));
        return n;
    }
    
    public String toString(Number number){
        long days=(Long) number;
        return DateUtilities.convertDaystoStringDate(days);
    }
    
}

