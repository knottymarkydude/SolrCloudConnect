
package org.sanger.search.solr;

import java.io.IOException;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.slf4j.LoggerFactory;

/**
 *
 * @author mw8
 */
public class SolrConnectTest {
    
    org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());
    
    SolrConnect solrConnect = null;
    
    public SolrConnectTest() {
       solrConnect = new SolrConnect();
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of pingServer method, of class SolrConnect.
     */
    @Test
    public void testPingServer() throws Exception {
        System.out.println("pingServer");
        solrConnect = new SolrConnect();
        int expResult = 0;
        int result = solrConnect.pingServer().getStatus();
        assertEquals(expResult, result);
    }

    /**
     * Test of addDoc method, of class SolrConnect.
     */
    @Test
    public void testAddDoc() throws Exception {
        UpdateResponse response = null;
        
        try {
            logger.info("testAddDoc");
            SolrInputDocument solrDoc = new SolrInputDocument();
            solrDoc.addField("id", "12345678");
            solrDoc.addField("title", "test zibzob");
            solrDoc.addField("description", "test description");
            solrDoc.addField("ordered_by", "mpw");
            
            response = solrConnect.addDoc(solrDoc);
        } catch (SolrServerException | IOException ex) {
            logger.error("Exception: " + ex);
        }
        
        logger.info("Response: "+response);
        
        assertEquals(response.getStatus(), 0);
    }
    
}
