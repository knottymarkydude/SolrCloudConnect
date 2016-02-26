/*
 * Solr 5 Connect
 */
package org.search.solr.service.solr;

import java.io.IOException;
import java.util.Collection;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.search.solr.connect.SolrCloudConnect;
import org.slf4j.LoggerFactory;

/**
 *
 * Service that sends data to Solr Server
 * 
 * @author mw8
 */
public class InputServiceSolrImpl implements InputServiceSolr {
    
    org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());
    
    /**
     *
     * @param solrDoc
     * @return status
     */
    @Override
    public boolean sendData(SolrInputDocument solrDoc){
        boolean status = false;
        
        SolrCloudConnect solrConnect = new SolrCloudConnect();
        try {
            status = solrConnect.add(solrDoc);
            if(status){
                status = solrConnect.commit();
            }
        } catch (SolrServerException | IOException ex) {
            logger.error("Exception:: "+ ex);
        }
        
        return status;
    }
    
    /**
     * 
     * @param solrDocs
     * @return boolean status
     */
    @Override
    public boolean sendData(Collection<SolrInputDocument> solrDocs){
        boolean status = false;
        
        SolrCloudConnect solrConnect = new SolrCloudConnect();
        try {
            status = solrConnect.add(solrDocs);
            if(status){
                status = solrConnect.commit();
            }
        } catch (SolrServerException | IOException ex) {
            logger.error("Exception:: "+ ex);
        }
        
        return status;
    }

    /**
     * 
     * @param sid
     * @param collection
     * @return boolean status
     */
    boolean sendData(SolrInputDocument sid, String collection) {
        boolean status = false;
        
        SolrCloudConnect solrConnect = new SolrCloudConnect(collection);
        try {
            status = solrConnect.add(sid);
            if(status){
                status = solrConnect.commit();
            }
        } catch (SolrServerException | IOException ex) {
            logger.error("Exception:: "+ ex);
        }
        
        return status;
    }
    
}
