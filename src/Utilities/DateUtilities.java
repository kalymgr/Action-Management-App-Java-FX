/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.text.DateFormatter;

/**
 *
 * @author mtsougranis
 */
public class DateUtilities {
    
    final static String DATE_FORMAT_PATTERN="d/M/y";
    
    /**
     * Method that converts the date from String to the number of days since 1-1-1970
     * @param stringDate
     * @return 
     */
     static public long convertStringDateToDaysSinceEpochDay(String stringDate){
        DateTimeFormatter df=DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN);
         LocalDate lDate=LocalDate.parse(stringDate,df);
        
        //set formatter
         
         lDate.format(df);
        long longDaysDate= lDate.toEpochDay();
        
        return longDaysDate;
    }
     
     /**
      * Method that converts the days since epoch day to string, according to 
      * the day format used
      * @param days since the epoch day
      * @return date as string, using the date formatter
      */
     static public String convertDaystoStringDate(long days){
         //convert days to Localdate type
         LocalDate ld=LocalDate.ofEpochDay(days);
         
         return convertLocalDatetoStringDate(ld);
     }
     
     /**
      * Method that converts a date from string to LocalDate
      * @param date
      * @return 
      */
     static public LocalDate convertStringDateToLocalDate(String date)
     {
         DateTimeFormatter df=DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN);
         return LocalDate.parse(date,df);
     }
     
     /**
      * Method that return the LocalDate as String, using the defined formatter
      * @param ldate
      * @return date as string
      */
     static public String convertLocalDatetoStringDate(LocalDate ldate){
         //create formatter
         DateTimeFormatter df=DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN);
         
         //convert localdate to string
         String stringDate=ldate.format(df);
         
         return stringDate;
     }
     
     /**
      * Method that converts local date to days since epoch
      * @param ldate
      * @return 
      */
     static public long convertLocalDateToLongDays(LocalDate ldate)
     {
         return ldate.toEpochDay();
     }
     
     /**
      * Method that gets the currentDate as  Days since epoch
      * @return 
      */
     static public long getCurrentDateAsLongDays(){
         return (convertLocalDateToLongDays(LocalDate.now()));
     }
     
     /**
      * Method that get the current date as LocalDate
      * @return 
      */
     static public LocalDate getCurrentDateAsLocalDate(){
         return LocalDate.now();
     }
     
     /**
      * Method that gets the current date as String
      * @return 
      */
     static public String getCurrentDateAsString(){
        return convertLocalDatetoStringDate(getCurrentDateAsLocalDate());
     }   
}
