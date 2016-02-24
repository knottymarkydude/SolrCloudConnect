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
 * Test Tika Service. Using Spar Collection
 *
 * @author mw8
 */
public class InputServiceTikaStreamTest {

    Logger logger = LoggerFactory.getLogger(getClass());

    InputServiceTikaStream inputServiceTikaStream;
    SolrInputDocument sid;
    InputStream is;
    String collection;

    public InputServiceTikaStreamTest() {

    }

    @Before
    public void setUp() {
        collection = "spar";
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of inputDataService method, of class InputServiceTikaStream.
     */
    @Test
    public void testInputDataServiceNoId() {
        logger.info("testInputDataServiceNoId");
        
        try {
            File testFile = new File("/Users/mw8/Desktop/journals/test1.pdf");
            is = new FileInputStream(testFile);

            sid = new SolrInputDocument();
            sid.addField("title", "jUnit Test with No Id");
            sid.addField("authors", "Mr Benn");
            sid.addField("description", "Test document No Id, zibzob");
            sid.addField("pmid", "456645645");
            sid.addField("doi", "4564566654/n01d");
            sid.addField("ordered_by", "mpw");

            inputServiceTikaStream = new InputServiceTikaStream(collection, sid, is);

            boolean expResult = true;
            boolean result = inputServiceTikaStream.inputDataService();
            assertEquals(expResult, result);
        } catch (FileNotFoundException ex) {
            logger.error("FileNotFoundException: " + ex);
        }
    }

}
