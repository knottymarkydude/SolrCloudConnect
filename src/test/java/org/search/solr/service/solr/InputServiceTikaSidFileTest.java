/*
 * Solr 5 Connect
 */
package org.search.solr.service.solr;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import org.apache.solr.common.SolrInputDocument;
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
public class InputServiceTikaSidFileTest {

    Logger logger = LoggerFactory.getLogger(getClass());

    InputServiceTikaSidFile inputServiceTikaFile;
    SolrInputDocument sid;
    String collection;

    public InputServiceTikaSidFileTest() {
    }

    @Before
    public void setUp() {
        collection = "spar";
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
        File testFile = new File("/www/data/spar/201602/unittest.pdf");

        sid = new SolrInputDocument();
        sid.addField("id", "12345678");
        sid.addField("title", "jUnit Test with Id");
        sid.addField("authors", "Mr Benn");
        sid.addField("description", "Test document No Id, zibzob");
        sid.addField("pmid", "456645645");
        sid.addField("doi", "3333333/4444");
        sid.addField("ordered_by", "mpw");
        sid.addField("directory", "201602");
        sid.addField("filename", "unittest.pdf");
        sid.addField("content_type", "application/pdf");
        sid.addField("date_published", "2016");
        
        inputServiceTikaFile  = new InputServiceTikaSidFile(collection, sid);
        
        boolean expResult = true;
        boolean result = inputServiceTikaFile.inputDataService();
        assertEquals(expResult, result);

    }

}
