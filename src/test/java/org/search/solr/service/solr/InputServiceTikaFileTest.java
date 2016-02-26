/*
 * Solr 5 Connect
 */
package org.search.solr.service.solr;

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
public class InputServiceTikaFileTest {

    Logger logger = LoggerFactory.getLogger(getClass());

    InputServiceTikaFile inputServiceTikaFile;
    String collection;

    public InputServiceTikaFileTest() {
    }

    @Before
    public void setUp() {
        collection = "lego";
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of inputDataService method, of class InputServiceTikaSidFile.
     */
    @Test
    public void testInputDataService() {
        logger.info("testInputDataService");
        String testFile = "/www/data/spar/201602/unittest.pdf";

        inputServiceTikaFile  = new InputServiceTikaFile(collection, testFile);
        
        boolean expResult = true;
        boolean result = inputServiceTikaFile.inputDataService();
        assertEquals(expResult, result);

    }

}
