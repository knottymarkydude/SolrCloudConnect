/*
 * Solr 5 Connect
 */
package org.search.solr.utilities.date;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author mw8
 */
public class DateUtilityTest {
    
    Logger logger = LoggerFactory.getLogger(getClass());
    
    public DateUtilityTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getTodaysDate method, of class DateUtility.
     */
     @Test
    public void testTodaysDate(){
        logger.debug("testTodaysDate");
        String result = DateUtility.getTodaysDate();
        logger.debug("Result inputString::"+result);
        String test1 = LocalDate.now().toString();
        
        assertEquals(test1, result);
    }
    

    
    @Test
    public void testFormattedDate(){
        logger.debug("testFormattedDate");
        LocalDate testDate = LocalDate.now();
        logger.debug("Date:" + testDate.toString());
        String result = DateUtility.getFormattedDate(testDate, "YYYYMM");
        logger.debug("Result inputString::"+result);
        String test2 = testDate.format(DateTimeFormatter.ofPattern("YYYYMM"));
        assertEquals(test2, result);
    }
}
