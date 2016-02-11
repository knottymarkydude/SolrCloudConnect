package org.search.solr.connect;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SolrPingResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.SolrParams;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.slf4j.LoggerFactory;

/**
 *
 * @author mw8
 */
public class SolrCloudConnectTest {

    org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

    String collection;
    SolrCloudConnect solrConnect;
    SolrInputDocument solrDoc;
    SolrInputDocument solrDoc1;
    Collection<SolrInputDocument> solrInputDocuments;
    List<String> ids;
    int commitWithinMs;

    public SolrCloudConnectTest() {
        solrConnect = new SolrCloudConnect();
        solrInputDocuments = new ArrayList();
        ids = new ArrayList();
    }

    @Before
    public void setUp() {
        
        collection = "spar";
        
        solrDoc = new SolrInputDocument();
        solrDoc.addField("id", "12345678");
        solrDoc.addField("title", "test zibzob");
        solrDoc.addField("description", "test description");
        solrDoc.addField("ordered_by", "mpw");
        
        solrDoc1 = new SolrInputDocument();
        solrDoc1.addField("id", "12345679");
        solrDoc1.addField("title", "test zibzob1");
        solrDoc1.addField("description", "test1 description");
        solrDoc1.addField("ordered_by", "mpw");

        commitWithinMs = 10;
        
        solrInputDocuments.add(solrDoc);
        solrInputDocuments.add(solrDoc1);
        
        ids.add("12345678");
        ids.add("12345679");
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of pingServer method, of class SolrCloudConnect.
     */
    @Test
    public void testPingServer() throws Exception {
        logger.info("pingServer");

        boolean expResult = true;
        boolean result = solrConnect.pingServer();
        assertEquals(expResult, result);
    }

     /**
     * Test of pingServerDetails method, of class SolrCloudConnect.
     */
    @Test
    public void testPingServerDetails() throws Exception {
        System.out.println("pingServerDetails");
        SolrCloudConnect instance = new SolrCloudConnect();
        SolrPingResponse expResult = null;
        SolrPingResponse result = instance.pingServerDetails();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    /**
     * Test of add method with commitWithin, of class SolrCloudConnect.
     */
    @Test
    public void testAddWithTimeOut() throws Exception {
        logger.info("testAddWithTimeOut");
        boolean response = false;

        try {
            response = solrConnect.add(solrDoc, commitWithinMs);
        } catch (SolrServerException | IOException ex) {
            logger.error("Exception: " + ex);
        }
        logger.info("Response: " + response);
        assertTrue(response);
    }

    /**
     * Test of add method, of class SolrCloudConnect.
     */
    @Test
    public void testAdd_Collection() throws Exception {
        logger.info("testAdd_Collection");
        boolean response = false;

        try {
            response = solrConnect.add(solrInputDocuments);
        } catch (SolrServerException | IOException ex) {
            logger.error("Exception: " + ex);
        }
        logger.info("Response: " + response);
        assertTrue(response);
    }

    /**
     * Test of add method, of class SolrCloudConnect.
     */
    @Test
    public void testAdd_Collection_int() throws Exception {
        logger.info("testAdd_Collection");
        boolean response = false;

        try {
            response = solrConnect.add(solrInputDocuments, commitWithinMs);
        } catch (SolrServerException | IOException ex) {
            logger.error("Exception: " + ex);
        }
        logger.info("Response: " + response);
        assertTrue(response);
    }

    /**
     * Test of add method, of class SolrCloudConnect.
     */
    @Test
    public void testAdd_3args() throws Exception {
      logger.info("testAdd_3args");
        boolean response = false;

        try {
            response = solrConnect.add(collection, solrInputDocuments, commitWithinMs);
        } catch (SolrServerException | IOException ex) {
            logger.error("Exception: " + ex);
        }  
        logger.info("Response: " + response);
        assertTrue(response);
    }

    /**
     * Test of add method, of class SolrCloudConnect.
     */
    @Test
    public void testAdd_String_Collection() throws Exception {
        logger.info("testAdd_String_Collection");
        boolean response = false;

        try {
            response = solrConnect.add(collection, solrInputDocuments);
        } catch (SolrServerException | IOException ex) {
            logger.error("Exception: " + ex);
        }  
        logger.info("Response: " + response);
        assertTrue(response);
    }

    /**
     * Test of commit method, of class SolrCloudConnect.
     */
    @Test
    public void testCommit() throws Exception {
        logger.info("testCommit");
        boolean response = false;

        try {
            response = solrConnect.commit();
        } catch (SolrServerException | IOException ex) {
            logger.error("Exception: " + ex);
        } 
        logger.info("Response: " + response);
        assertTrue(response);
    }

    /**
     * Test of deleteById method, of class SolrCloudConnect.
     */
    @Test
    public void testDeleteById_List() throws Exception {
        logger.info("testDeleteById_List");
        boolean response = false;

        try {
            response = solrConnect.deleteById(ids);
        } catch (SolrServerException | IOException ex) {
            logger.error("Exception: " + ex);
        } 
        logger.info("Response: " + response);
        assertTrue(response);
    }

    /**
     * Test of deleteById method, of class SolrCloudConnect.
     */
    @Test
    public void testDeleteById_List_int() throws Exception {
        System.out.println("deleteById");
        List<String> ids = null;
        int commitWithinMs = 0;
        SolrCloudConnect instance = new SolrCloudConnect();
        boolean expResult = false;
        boolean result = instance.deleteById(ids, commitWithinMs);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteById method, of class SolrCloudConnect.
     */
    @Test
    public void testDeleteById_String() throws Exception {
        System.out.println("deleteById");
        String id = "";
        SolrCloudConnect instance = new SolrCloudConnect();
        boolean expResult = false;
        boolean result = instance.deleteById(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteById method, of class SolrCloudConnect.
     */
    @Test
    public void testDeleteById_String_int() throws Exception {
        System.out.println("deleteById");
        String id = "";
        int commitWithinMs = 0;
        SolrCloudConnect instance = new SolrCloudConnect();
        boolean expResult = false;
        boolean result = instance.deleteById(id, commitWithinMs);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteById method, of class SolrCloudConnect.
     */
    @Test
    public void testDeleteById_3args_1() throws Exception {
        System.out.println("deleteById");
        String collection = "";
        List<String> ids = null;
        int commitWithinMs = 0;
        SolrCloudConnect instance = new SolrCloudConnect();
        boolean expResult = false;
        boolean result = instance.deleteById(collection, ids, commitWithinMs);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteById method, of class SolrCloudConnect.
     */
    @Test
    public void testDeleteById_String_String() throws Exception {
        System.out.println("deleteById");
        String collection = "";
        String id = "";
        SolrCloudConnect instance = new SolrCloudConnect();
        boolean expResult = false;
        boolean result = instance.deleteById(collection, id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteById method, of class SolrCloudConnect.
     */
    @Test
    public void testDeleteById_3args_2() throws Exception {
        System.out.println("deleteById");
        String collection = "";
        String id = "";
        int commitWithinMs = 0;
        SolrCloudConnect instance = new SolrCloudConnect();
        boolean expResult = false;
        boolean result = instance.deleteById(collection, id, commitWithinMs);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteByQuery method, of class SolrCloudConnect.
     */
    @Test
    public void testDeleteByQuery_String_int() throws Exception {
        System.out.println("deleteByQuery");
        String query = "";
        int commitWithinMs = 0;
        SolrCloudConnect instance = new SolrCloudConnect();
        boolean expResult = false;
        boolean result = instance.deleteByQuery(query, commitWithinMs);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteByQuery method, of class SolrCloudConnect.
     */
    @Test
    public void testDeleteByQuery_String() throws Exception {
        System.out.println("deleteByQuery");
        String query = "";
        SolrCloudConnect instance = new SolrCloudConnect();
        boolean expResult = false;
        boolean result = instance.deleteByQuery(query);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteByQuery method, of class SolrCloudConnect.
     */
    @Test
    public void testDeleteByQuery_3args() throws Exception {
        System.out.println("deleteByQuery");
        String collection = "";
        String query = "";
        int commitWithinMs = 0;
        SolrCloudConnect instance = new SolrCloudConnect();
        boolean expResult = false;
        boolean result = instance.deleteByQuery(collection, query, commitWithinMs);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteByQuery method, of class SolrCloudConnect.
     */
    @Test
    public void testDeleteByQuery_String_String() throws Exception {
        System.out.println("deleteByQuery");
        String collection = "";
        String query = "";
        SolrCloudConnect instance = new SolrCloudConnect();
        boolean expResult = false;
        boolean result = instance.deleteByQuery(collection, query);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getById method, of class SolrCloudConnect.
     */
    @Test
    public void testGetById_Collection_SolrParams() throws Exception {
        System.out.println("getById");
        Collection<String> ids = null;
        SolrParams params = null;
        SolrCloudConnect instance = new SolrCloudConnect();
        SolrDocumentList expResult = null;
        SolrDocumentList result = instance.getById(ids, params);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getById method, of class SolrCloudConnect.
     */
    @Test
    public void testGetById_Collection() throws Exception {
        System.out.println("getById");
        Collection<String> ids = null;
        SolrCloudConnect instance = new SolrCloudConnect();
        SolrDocumentList expResult = null;
        SolrDocumentList result = instance.getById(ids);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getById method, of class SolrCloudConnect.
     */
    @Test
    public void testGetById_String() throws Exception {
        System.out.println("getById");
        String id = "";
        SolrCloudConnect instance = new SolrCloudConnect();
        SolrDocument expResult = null;
        SolrDocument result = instance.getById(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getById method, of class SolrCloudConnect.
     */
    @Test
    public void testGetById_3args() throws Exception {
        System.out.println("getById");
        String collection = "";
        Collection<String> ids = null;
        SolrParams params = null;
        SolrCloudConnect instance = new SolrCloudConnect();
        SolrDocumentList expResult = null;
        SolrDocumentList result = instance.getById(collection, ids, params);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getById method, of class SolrCloudConnect.
     */
    @Test
    public void testGetById_String_Collection() throws Exception {
        System.out.println("getById");
        String collection = "";
        Collection<String> ids = null;
        SolrCloudConnect instance = new SolrCloudConnect();
        SolrDocumentList expResult = null;
        SolrDocumentList result = instance.getById(collection, ids);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getById method, of class SolrCloudConnect.
     */
    @Test
    public void testGetById_String_String() throws Exception {
        System.out.println("getById");
        String collection = "";
        String id = "";
        SolrCloudConnect instance = new SolrCloudConnect();
        SolrDocument expResult = null;
        SolrDocument result = instance.getById(collection, id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of query method, of class SolrCloudConnect.
     */
    @Test
    public void testQuery_SolrParams() throws Exception {
        System.out.println("query");
        SolrParams params = null;
        SolrCloudConnect instance = new SolrCloudConnect();
        QueryResponse expResult = null;
        QueryResponse result = instance.query(params);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of query method, of class SolrCloudConnect.
     */
    @Test
    public void testQuery_String_SolrParams() throws Exception {
        System.out.println("query");
        String collection = "";
        SolrParams params = null;
        SolrCloudConnect instance = new SolrCloudConnect();
        QueryResponse expResult = null;
        QueryResponse result = instance.query(collection, params);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of rollback method, of class SolrCloudConnect.
     */
    @Test
    public void testRollback_String() throws Exception {
        System.out.println("rollback");
        String collection = "";
        SolrCloudConnect instance = new SolrCloudConnect();
        boolean expResult = false;
        boolean result = instance.rollback(collection);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of rollback method, of class SolrCloudConnect.
     */
    @Test
    public void testRollback_0args() throws Exception {
        System.out.println("rollback");
        SolrCloudConnect instance = new SolrCloudConnect();
        boolean expResult = false;
        boolean result = instance.rollback();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
