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
 * Create a unique Id
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
     *  Create a new unique id from todays date and some random number.
     * 
     * @return String newId
     */
    public String getId() {

        String newId = null;

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMHHmmss");
        Integer randomNum = this.random(24754);
        newId = sdf.format(date) + randomNum.toString().trim();
        
        return newId;
    }

    
    private int random(final int pMax) {
        int randomInt = RANDOM.nextInt(pMax);
        logger.info("randomInt:  " + randomInt);
        int randomVal = randomInt * pMax;
        logger.info("randomVal:  " + randomVal);
        return randomVal;
    }
}
