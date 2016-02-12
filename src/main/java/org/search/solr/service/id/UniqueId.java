/*
 * Solr 5 Connect
 */
package org.search.solr.service.id;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Create a unique Id to be used in Solr Id field
 * 
 * @author mw8
 */
public class UniqueId {
    private static final Random RANDOM = new Random(System.nanoTime());
    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     *
     */
    public UniqueId() {
    }
    
    /**
     *
     *  Create a new unique id for Solr.
     * 
     * @return String newId
     */
    public String getId() {

        String newId = null;

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMHHmmss");
        Integer randomNum = this.random(100, 2147483647);
        newId = sdf.format(date) + randomNum.toString().trim();
        
        return newId;
    }

    
    private Integer random(final int pMin, final int pMax) {
        int randomInt = RANDOM.nextInt(pMax);
        Integer randomVal = pMin + randomInt * (pMax - pMin);
        return randomVal;
    }
}
