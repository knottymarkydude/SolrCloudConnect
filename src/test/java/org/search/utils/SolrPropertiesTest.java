/*
 * Solr 5 Connect
 */
package org.search.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.slf4j.LoggerFactory;

/**
 *
 * @author mw8
 */
public class SolrPropertiesTest {
    
    org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());
    
    public SolrPropertiesTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getPropValue method, of class SolrProperties.
     */
    @Test
    public void testGetPropValue() {
        logger.info("getPropValue");
        String propKey = "zkHost";
        SolrProperties instance = new SolrProperties();
        String expResult = "web-solrclouddev-02:8007,web-solrclouddev-02:8008,web-solrclouddev-02:8009";
        String result = instance.getPropValue(propKey);
        logger.info("Result: "+result);
        assertEquals(expResult, result);
        
    }
    
}
