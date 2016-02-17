/*
 * Solr 5 Connect
 */
package org.search.solr.service.solr;

import org.search.solr.service.id.UniqueId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Service that sends Tika documents to Solr
 *
 * @author mw8
 */
public class InputServiceTika extends InputServiceSolrImpl {

    Logger logger = LoggerFactory.getLogger(getClass());

    

    public String generateId() {
        UniqueId myId = new UniqueId();
        return myId.getId();
    }

    public boolean uploadFile(String fileName) {
        boolean status = false;

        return status;
    }

}
