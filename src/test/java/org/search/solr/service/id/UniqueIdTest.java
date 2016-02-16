/*
 * Solr 5 Connect
 */
package org.search.solr.service.id;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Create a unique id.
 * 
 * @author mw8
 */
public class UniqueIdTest {
    
    public UniqueIdTest() {
    }
    
    Logger logger = LoggerFactory.getLogger(getClass());

    UniqueId uniqueId;

    @Before
    public void setUp() {
        uniqueId = new UniqueId();
    }
    
    @Test
    public void testCreateUniqueId() {
        String result = uniqueId.getId();
        
        logger.info("Id: "+result);
        
        assertNotNull(result);
    }
    
}
