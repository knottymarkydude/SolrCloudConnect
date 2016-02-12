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
import org.apache.solr.common.params.ModifiableSolrParams;
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

    private static final String WT = "json";
    private static final String QT = "/tika2";
    private static final String JSONNL = "map";
    private static final String FACETMINCOUNT = "1";
    private static final String QUERY = "zibzob";
    private static final String STARTDOC = "0";
    private static final String ROWS = "10";

    String collection;
    SolrCloudConnect solrConnect;
    SolrInputDocument solrDoc;
    SolrInputDocument solrDoc1;
    Collection<SolrInputDocument> solrInputDocuments;
    List<String> ids;
    int commitWithinMs;
    String id1;
    String id2;
    String query;
    ModifiableSolrParams solrParams;
    SolrDocumentList solrDocList;

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
        solrDoc1.addField("title", "test zibzob 1");
        solrDoc1.addField("description", "test1 description");
        solrDoc1.addField("ordered_by", "mpw");

        commitWithinMs = 10;

        solrInputDocuments.add(solrDoc);
        solrInputDocuments.add(solrDoc1);

        id1 = "12345678";
        id2 = "12345679";
        query = "zibzob";

        ids.add(id1);
        ids.add(id2);

        solrParams = new ModifiableSolrParams();
        solrParams.set("q", QUERY);
        solrParams.set("rows", ROWS);
        solrParams.set("wt", WT);
        solrParams.set("start", STARTDOC);
        solrParams.set("qt", QT);
        solrParams.set("json.nl", JSONNL);

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
        logger.info("testPingServerDetails");
        SolrPingResponse solrPingResponse = new SolrPingResponse();

        try {
            solrPingResponse = solrConnect.pingServerDetails();
        } catch (SolrServerException | IOException ex) {
            logger.error("Exception: " + ex);
        }
        logger.info("Response: " + solrPingResponse);
        assertNotNull(solrPingResponse.getQTime());
    }

    /**
     * Test add method, of class SolrCloudConnect.
     */
    @Test
    public void testAdd() throws Exception {
        logger.info("testAdd");
        boolean response = false;

        try {
            response = solrConnect.add(solrDoc);
        } catch (SolrServerException | IOException ex) {
            logger.error("Exception: " + ex);
        }
        logger.info("Response: " + response);
        assertTrue(response);
    }

    /**
     * Test add method with commitWithin, of class SolrCloudConnect.
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
            response = solrConnect.add(solrDoc, commitWithinMs);
            response = solrConnect.add(solrDoc1, commitWithinMs);
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

        logger.info("testDeleteById_List_int");
        boolean response = false;

        try {
            response = solrConnect.add(solrDoc, commitWithinMs);
            response = solrConnect.deleteById(id1, commitWithinMs);
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
    public void testDeleteById_String() throws Exception {
        logger.info("testDeleteById_String");
        boolean response = false;

        try {
            response = solrConnect.add(solrDoc, commitWithinMs);
            response = solrConnect.deleteById(id1);
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
    public void testDeleteById_String_int() throws Exception {

        logger.info("testDeleteById_String_int");
        boolean response = false;

        try {
            response = solrConnect.add(solrDoc, commitWithinMs);
            response = solrConnect.deleteById(id1, commitWithinMs);
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
    public void testDeleteById_3args_1() throws Exception {
        logger.info("testDeleteById_3args_1");
        boolean response = false;

        try {
            response = solrConnect.add(solrDoc, commitWithinMs);
            response = solrConnect.deleteById(collection, id1, commitWithinMs);
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
    public void testDeleteById_Collection_Id() throws Exception {
        logger.info("testDeleteById_Collection_Id");
        boolean response = false;

        try {
            response = solrConnect.add(solrDoc, commitWithinMs);
            response = solrConnect.deleteById(collection, id1);
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
    public void testDeleteById_Collection_Id_CommitWithin() throws Exception {
        logger.info("testDeleteById_Collection_Id_CommitWithin");
        boolean response = false;

        try {
            response = solrConnect.add(solrDoc, commitWithinMs);
            response = solrConnect.deleteById(collection, id1, commitWithinMs);
        } catch (SolrServerException | IOException ex) {
            logger.error("Exception: " + ex);
        }
        logger.info("Response: " + response);
        assertTrue(response);
    }

    /**
     * Test of deleteByQuery method, of class SolrCloudConnect.
     */
    @Test
    public void testDeleteByQuery_commitWithinMs() throws Exception {

        logger.info("testDeleteByQuery_commitWithinMs");
        boolean response = false;

        try {
            response = solrConnect.add(solrDoc, commitWithinMs);
            response = solrConnect.add(solrDoc1, commitWithinMs);
            response = solrConnect.deleteByQuery(query, commitWithinMs);
        } catch (SolrServerException | IOException ex) {
            logger.error("Exception: " + ex);
        }
        logger.info("Response: " + response);
        assertTrue(response);
    }

    /**
     * Test of deleteByQuery method, of class SolrCloudConnect.
     */
    @Test
    public void testDeleteByQuery() throws Exception {
        logger.info("testDeleteByQuery");
        boolean response = false;

        try {
            response = solrConnect.add(solrDoc, commitWithinMs);
            response = solrConnect.add(solrDoc1, commitWithinMs);
            response = solrConnect.deleteByQuery(query);
        } catch (SolrServerException | IOException ex) {
            logger.error("Exception: " + ex);
        }
        logger.info("Response: " + response);
        assertTrue(response);
    }

    /**
     * Test of deleteByQuery method, of class SolrCloudConnect.
     */
    @Test
    public void testDeleteByQuery_Collection_commitWithinMs() throws Exception {
        logger.info("testDeleteByQuery_Collection_commitWithinMs");
        boolean response = false;

        try {
            response = solrConnect.add(solrDoc, commitWithinMs);
            response = solrConnect.add(solrDoc1, commitWithinMs);
            response = solrConnect.deleteByQuery(collection, query, commitWithinMs);
        } catch (SolrServerException | IOException ex) {
            logger.error("Exception: " + ex);
        }
        logger.info("Response: " + response);
        assertTrue(response);
    }

    /**
     * Test of deleteByQuery method, of class SolrCloudConnect.
     */
    @Test
    public void testDeleteByQuery_Collection() throws Exception {
        logger.info("testDeleteByQuery_Collection");
        boolean response = false;

        try {
            response = solrConnect.add(solrDoc, commitWithinMs);
            response = solrConnect.add(solrDoc1, commitWithinMs);
            response = solrConnect.deleteByQuery(collection, query);
        } catch (SolrServerException | IOException ex) {
            logger.error("Exception: " + ex);
        }
        logger.info("Response: " + response);
        assertTrue(response);
    }

    /**
     * Test of getById method, of class SolrCloudConnect.
     */
    /**
     * @Test public void testGetById_Collection_SolrParams() throws Exception {
     *
     * ModifiableSolrParams solrParamsLocal = new ModifiableSolrParams();
     * solrParamsLocal.set("rows", ROWS); solrParamsLocal.set("qt", QT);
     *
     * logger.info("testGetById_Collection_SolrParams"); boolean response =
     * false; SolrDocumentList result = null; boolean remove =
     * solrParams.remove("q", QUERY);
     *
     * try { response = solrConnect.add(solrDoc, commitWithinMs); response =
     * solrConnect.add(solrDoc1, commitWithinMs); result =
     * solrConnect.getById(ids, solrParamsLocal);
     *
     * result.stream().forEach((SolrDocument solrDoc) -> { logger.info("SolrDoc:
     * "+ solrDoc.toString()); });
     *
     * } catch (SolrServerException | IOException ex) { logger.error("Exception:
     * " + ex); } logger.info("testGetById_Collection_SolrParams results: " +
     * result); assertNotNull(result);
     *
     * }
     *
     */
    /**
     * Test of getById method, of class SolrCloudConnect.
     */
    @Test
    public void testGetByIds() throws Exception {
        logger.info("testGetByIds");
        boolean response = false;
        SolrDocumentList results = null;

        try {
            response = solrConnect.add(solrDoc, commitWithinMs);
            response = solrConnect.add(solrDoc1, commitWithinMs);
            results = solrConnect.getById(ids);

            results.stream().forEach((solrDoc) -> {
                logger.info(solrDoc.toString());
            });

        } catch (SolrServerException | IOException ex) {
            logger.error("Exception: " + ex);
        }
        logger.info("Response: " + response);
        assertEquals(results.size(), 2);
    }

    /**
     * Test of getById method, of class SolrCloudConnect.
     */
    @Test
    public void testGetById() throws Exception {
        logger.info("testGetById");
        boolean response = false;
        SolrDocument solrOutputDoc = null;

        try {
            response = solrConnect.add(solrDoc, commitWithinMs);
            response = solrConnect.add(solrDoc1, commitWithinMs);
            solrOutputDoc = solrConnect.getById(id1);
        } catch (SolrServerException | IOException ex) {
            logger.error("Exception: " + ex);
        }
        logger.info("Response: " + response);
        assertEquals(solrOutputDoc.getFieldValue("ordered_by"), "mpw");
    }

    /**
     * Test of getById method, of class SolrCloudConnect.
     */
    /**
     * @Test public void testGetById_Collection_ids_params() throws Exception {
     * logger.info("testGetById_Collection_ids_params"); boolean response =
     * false; SolrDocumentList results = null; boolean remove =
     * solrParams.remove("q", QUERY);
     *
     * try { response = solrConnect.add(solrDoc, commitWithinMs); response =
     * solrConnect.add(solrDoc1, commitWithinMs); results =
     * solrConnect.getById(collection, ids, solrParams);
     *
     * results.stream().forEach((solrDoc) -> { logger.info(solrDoc.toString());
     * });
     *
     * } catch (SolrServerException | IOException ex) { logger.error("Exception:
     * " + ex); } logger.info("Response: " + response);
     * assertEquals(results.size(), 2); }
*
     */
    /**
     * Test of getById method, of class SolrCloudConnect.
     */
    @Test
    public void testGetById_Collection_ids() throws Exception {
        logger.info("testGetById_Collection_ids");
        boolean response = false;
        SolrDocumentList results = null;

        try {
            response = solrConnect.add(solrDoc, commitWithinMs);
            response = solrConnect.add(solrDoc1, commitWithinMs);
            results = solrConnect.getById(collection, ids);

            results.stream().forEach((solrDoc) -> {
                logger.info(solrDoc.toString());
            });

        } catch (SolrServerException | IOException ex) {
            logger.error("Exception: " + ex);
        }
        logger.info("Response: " + response);
        assertEquals(results.size(), 2);
    }

    /**
     * Test of getById method, of class SolrCloudConnect.
     */
    @Test
    public void testGetById_Collection() throws Exception {
        logger.info("testGetById_Collection");
        boolean response = false;
        SolrDocument solrOutputDoc = null;

        try {
            response = solrConnect.add(solrDoc, commitWithinMs);
            response = solrConnect.add(solrDoc1, commitWithinMs);
            solrOutputDoc = solrConnect.getById(collection, id1);
        } catch (SolrServerException | IOException ex) {
            logger.error("Exception: " + ex);
        }
        logger.info("Response: " + response);
        assertEquals(solrOutputDoc.getFieldValue("ordered_by"), "mpw");
    }

    /**
     * Test of query method, of class SolrCloudConnect.
     */
    @Test
    public void testQuery_SolrParams() throws Exception {
        logger.info("testQuery_SolrParams");
        boolean response = false;
        QueryResponse queryResponse = null;

        try {
            response = solrConnect.add(solrDoc, commitWithinMs);
            response = solrConnect.add(solrDoc1, commitWithinMs);
            queryResponse = solrConnect.query(solrParams);
        } catch (SolrServerException | IOException ex) {
            logger.error("Exception: " + ex);
        }
        logger.info("queryResponse: " + queryResponse.getStatus());
        assertEquals(queryResponse.getStatus(), 0);
    }

    /**
     * Test of query method, of class SolrCloudConnect.
     */
    @Test
    public void testQuery_SolrParams_Collection() throws Exception {
        logger.info("testQuery_SolrParams_Collection");
        boolean response = false;
        QueryResponse queryResponse = null;

        try {
            response = solrConnect.add(solrDoc, commitWithinMs);
            response = solrConnect.add(solrDoc1, commitWithinMs);
            queryResponse = solrConnect.query(collection, solrParams);
        } catch (SolrServerException | IOException ex) {
            logger.error("Exception: " + ex);
        }
        logger.info("queryResponse: " + queryResponse.getStatus());
        assertEquals(queryResponse.getStatus(), 0);
    }

}
