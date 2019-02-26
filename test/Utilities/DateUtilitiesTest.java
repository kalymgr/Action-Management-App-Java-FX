/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mtsougranis
 */
public class DateUtilitiesTest {
    
    public DateUtilitiesTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of convertStringDateToDaysSinceEpochDay method, of class DateUtilities.
     */
    @Test
    public void testConvertStringDateToDaysSinceEpochDay() {
        assertEquals(DateUtilities.convertStringDateToDaysSinceEpochDay("2/1/1970"), 1);
    }
    
    @Test
    public void testConvertDaystoStringDate(){
        assertEquals(DateUtilities.convertDaystoStringDate(0), "1/1/1970");
    }
    
    @Test
    public void testConvertLocalDatetoStringDate(){
        assertEquals(DateUtilities.convertLocalDatetoStringDate(LocalDate.ofEpochDay(0)), "1/1/1970");
    }
    
    @Test
    public void  testConvertLocalDateToLongDays()
    {
        LocalDate ld=DateUtilities.convertStringDateToLocalDate("1/1/1970");
        assertEquals(0, DateUtilities.convertLocalDateToLongDays(ld));
        
    }
    
    @Test
    public void testgetCurrentDateAsLongDays()
    {
        assertEquals(DateUtilities.getCurrentDateAsLongDays(),
                DateUtilities.convertLocalDateToLongDays(DateUtilities.getCurrentDateAsLocalDate()));
    }
    
    @Test
    public void testGetCurrentDateAsLocalDate()
    {
        assertEquals(DateUtilities.getCurrentDateAsLocalDate(), 
                DateUtilities.convertStringDateToLocalDate(DateUtilities.getCurrentDateAsString()));
    }
    
    @Test
    public void testGetCurrentDateAsString(){
        System.out.println(DateUtilities.getCurrentDateAsString());
    }
    
   
      
}
