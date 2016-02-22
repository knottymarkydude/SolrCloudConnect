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
 * Test Tika Service
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
    public void testInputDataServiceWithId() {
        try {
            logger.info("testInputDataServiceWithId");

            File testFile = new File("/Users/mw8/Desktop/journals/test2.pdf");
            is = new FileInputStream(testFile);

            sid = new SolrInputDocument();
            sid.addField("id", "31051965", 1.0f);
            sid.addField("title", "jUnit Test with Id");
            sid.addField("authors", "Mr Benn");
            sid.addField("description", "Test document with Id, zibzob");
            sid.addField("pmid", "456645645");
            sid.addField("doi", "4564566654/456mpw");
            sid.addField("content_type", "application/pdf");
            sid.addField("directory", "201512");
            sid.addField("filename", "201512122913580082616.pdf");
            sid.addField("ordered_by", "knottymarkdude");

            inputServiceTikaStream = new InputServiceTikaStream(sid, is, collection);

            boolean expResult = true;
            boolean result = inputServiceTikaStream.inputDataService();
            assertEquals(expResult, result);
        } catch (FileNotFoundException ex) {
            logger.error("FileNotFoundException: " + ex);
        }
    }

    /**
     * Test of inputDataService method, of class InputServiceTikaStream.
     */
    @Test
    public void testInputDataServiceNoId() {
        try {
            logger.info("testInputDataServiceNoId");

            File testFile = new File("/Users/mw8/Desktop/journals/test1.pdf");
            is = new FileInputStream(testFile);

            sid = new SolrInputDocument();
            sid.addField("title", "jUnit Test with No Id");
            sid.addField("authors", "Mr Benn");
            sid.addField("description", "Test document No Id, zibzob");
            sid.addField("pmid", "456645645");
            sid.addField("doi", "4564566654/n01d");
            sid.addField("ordered_by", "knottymarkdude");

            inputServiceTikaStream = new InputServiceTikaStream(sid, is, collection);

            boolean expResult = true;
            boolean result = inputServiceTikaStream.inputDataService();
            assertEquals(expResult, result);
        } catch (FileNotFoundException ex) {
            logger.error("FileNotFoundException: " + ex);
        }
    }

}
