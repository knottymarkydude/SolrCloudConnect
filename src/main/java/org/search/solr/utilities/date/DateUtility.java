/*
 * Solr 5 Connect
 */
package org.search.solr.utilities.date;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Date Utility 
 * @author mw8
 */
public class DateUtility {

    /**
     *
     * Get todays date
     * 
     * @return String todays date - format "2015-05-06"
     */
    public static String getTodaysDate() {
        LocalDate today = LocalDate.now();
        return today.toString();
    }

    /**
     *
     * Get a Formatted date
     * 
     * @param aDate
     * @param format
     * @return String in correct format
     */
    public static String getFormattedDate(LocalDate aDate, String format) {
        return aDate.format(DateTimeFormatter.ofPattern(format));
    }
}
