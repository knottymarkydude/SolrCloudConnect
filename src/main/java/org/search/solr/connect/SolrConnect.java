/*
 * Solr 5 Connect
 */
package org.search.solr.connect;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SolrPingResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.SolrParams;
import org.search.utils.SolrProperties;
import org.slf4j.LoggerFactory;

/**
 *
 * @author mw8
 */
public class SolrConnect {

    org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

    private final String zkHostKey = "zkHost";
    private final String zkHostPropVal; //"web-solrclouddev-02:8007,web-solrclouddev-02:8008,web-solrclouddev-02:8009";
    private final String defaultCollection;
    private final CloudSolrClient solr;

    public SolrConnect() {

        SolrProperties props = new SolrProperties();
        zkHostPropVal = props.getPropValue("zkHost");
        this.defaultCollection = props.getPropValue("defaultCollection");
        solr = new CloudSolrClient(zkHostPropVal);
        solr.setDefaultCollection(defaultCollection);
        solr.connect();

    }

    /**
     *
     * @return status
     * @throws SolrServerException
     * @throws IOException
     */
    public boolean pingServer() throws SolrServerException, IOException {
        SolrPingResponse response = solr.ping();
        return this.getStatus(response.getStatus());
    }

    public SolrPingResponse pingServerDetails() throws SolrServerException, IOException {
        return solr.ping();
    }
    
    /**
     * Adds a document, specifying max time before they become committed
     *
     * @param solrInputDocument
     * @param commitWithinMs
     * @return boolean
     * @throws SolrServerException
     * @throws IOException
     */
    public boolean add(SolrInputDocument solrInputDocument, int commitWithinMs) throws SolrServerException, IOException {
        UpdateResponse response = solr.add(solrInputDocument, commitWithinMs);
        return this.getStatus(response.getStatus());
    }

    /**
     *
     * @param solrInputDocuments
     * @return boolean
     * @throws SolrServerException
     * @throws IOException
     */
    public boolean add(Collection<SolrInputDocument> solrInputDocuments) throws SolrServerException, IOException {
        UpdateResponse response = solr.add(solrInputDocuments);
        return this.getStatus(response.getStatus());
    }

    /**
     *
     * Adds a collection of documents, specifying max time before they become
     * committed
     *
     * @param solrInputDocuments
     * @param commitWithinMs
     * @return boolean
     * @throws SolrServerException
     * @throws IOException
     */
    public boolean add(Collection<SolrInputDocument> solrInputDocuments, int commitWithinMs) throws SolrServerException, IOException {
        UpdateResponse response = solr.add(solrInputDocuments, commitWithinMs);
        return this.getStatus(response.getStatus());
    }

    /**
     * Adds a collection of documents, specifying max time before they become
     * committed
     *
     * @param collection
     * @param solrInputDocuments
     * @param commitWithinMs
     * @return boolean
     * @throws SolrServerException
     * @throws IOException
     */
    public boolean add(String collection, Collection<SolrInputDocument> solrInputDocuments, int commitWithinMs) throws SolrServerException, IOException {
        UpdateResponse response = solr.add(collection, solrInputDocuments, commitWithinMs);
        return this.getStatus(response.getStatus());
    }

    /**
     *
     * @param collection
     * @param solrInputDocuments
     * @return boolean
     * @throws SolrServerException
     * @throws IOException
     */
    public boolean add(String collection, Collection<SolrInputDocument> solrInputDocuments) throws SolrServerException, IOException {
        UpdateResponse response = solr.add(collection, solrInputDocuments);
        return this.getStatus(response.getStatus());
    }

    /**
     *
     * @return @throws SolrServerException
     * @throws IOException
     */
    public boolean commit() throws SolrServerException, IOException {
        UpdateResponse response = solr.commit();
        return this.getStatus(response.getStatus());
    }

    /**
     *
     * @param ids
     * @return
     * @throws SolrServerException
     * @throws IOException
     */
    public boolean deleteById(List<String> ids) throws SolrServerException, IOException {
        UpdateResponse response = solr.deleteById(ids);
        return this.getStatus(response.getStatus());
    }

    /**
     *
     * @param ids
     * @param commitWithinMs
     * @return
     * @throws SolrServerException
     * @throws IOException
     */
    public boolean deleteById(List<String> ids, int commitWithinMs) throws SolrServerException, IOException {
        UpdateResponse response = solr.deleteById(ids, commitWithinMs);
        return this.getStatus(response.getStatus());
    }

    /**
     *
     * @param id
     * @return
     * @throws SolrServerException
     * @throws IOException
     */
    public boolean deleteById(String id) throws SolrServerException, IOException {
        UpdateResponse response = solr.deleteById(id);
        return this.getStatus(response.getStatus());
    }

    /**
     *
     * @param id
     * @param commitWithinMs
     * @return
     * @throws SolrServerException
     * @throws IOException
     */
    public boolean deleteById(String id, int commitWithinMs) throws SolrServerException, IOException {
        UpdateResponse response = solr.deleteById(id, commitWithinMs);
        return this.getStatus(response.getStatus());
    }

    /**
     *
     * @param collection
     * @param ids
     * @param commitWithinMs
     * @return
     * @throws SolrServerException
     * @throws IOException
     */
    public boolean deleteById(String collection, List<String> ids, int commitWithinMs) throws SolrServerException, IOException {
        UpdateResponse response = solr.deleteById(collection, ids, commitWithinMs);
        return this.getStatus(response.getStatus());
    }

    public boolean deleteById(String collection, String id) throws SolrServerException, IOException {
        UpdateResponse response = solr.deleteById(collection, id);
        return this.getStatus(response.getStatus());
    }

    /**
     *
     * @param collection
     * @param id
     * @param commitWithinMs
     * @return
     * @throws SolrServerException
     * @throws IOException
     */
    public boolean deleteById(String collection, String id, int commitWithinMs) throws SolrServerException, IOException {
        UpdateResponse response = solr.deleteById(collection, id, commitWithinMs);
        return this.getStatus(response.getStatus());
    }

    /**
     *
     * @param query
     * @param commitWithinMs
     * @return
     * @throws SolrServerException
     * @throws IOException
     */
    public boolean deleteByQuery(String query, int commitWithinMs) throws SolrServerException, IOException {
        UpdateResponse response = solr.deleteByQuery(query, commitWithinMs);
        return this.getStatus(response.getStatus());
    }

    /**
     *
     * @param query
     * @return
     * @throws SolrServerException
     * @throws IOException
     */
    public boolean deleteByQuery(String query) throws SolrServerException, IOException {
        UpdateResponse response = solr.deleteByQuery(query);
        return this.getStatus(response.getStatus());
    }

    /**
     *
     * @param collection
     * @param query
     * @param commitWithinMs
     * @return
     * @throws SolrServerException
     * @throws IOException
     */
    public boolean deleteByQuery(String collection, String query, int commitWithinMs) throws SolrServerException, IOException {
        UpdateResponse response = solr.deleteByQuery(collection, query, commitWithinMs);
        return this.getStatus(response.getStatus());
    }

    /**
     *
     * @param collection
     * @param query
     * @return boolean
     * @throws SolrServerException
     * @throws IOException
     */
    public boolean deleteByQuery(String collection, String query) throws SolrServerException, IOException {
        UpdateResponse response = solr.deleteByQuery(collection, query);
        return this.getStatus(response.getStatus());
    }

    /**
     * 
     * @param collection
     * @param ids
     * @return SolrDocumentList
     * @throws SolrServerException
     * @throws IOException 
     */
    public SolrDocumentList getById(Collection<String> ids, SolrParams params) throws SolrServerException, IOException {
        return solr.getById(ids, params);
    }
    
    /**
     *
     * @param collection
     * @param ids
     * @return SolrDocumentList
     * @throws SolrServerException
     * @throws IOException
     */
    public SolrDocumentList getById(Collection<String> ids) throws SolrServerException, IOException {
        return solr.getById(ids);
    }

    /**
     * 
     * @param id
     * @return SolrDocument
     * @throws SolrServerException
     * @throws IOException 
     */
    public SolrDocument getById(String id) throws SolrServerException, IOException {
        return solr.getById(id);
    }
    
    /**
     * 
     * @param collection
     * @param ids
     * @param params
     * @return SolrDocumentList
     * @throws SolrServerException
     * @throws IOException 
     */
    public SolrDocumentList getById(String collection, Collection<String> ids, SolrParams params) throws SolrServerException, IOException {
        return solr.getById(collection, ids, params);
    }
    
    /**
     * 
     * @param collection
     * @param ids
     * @return SolrDocumentList
     * @throws SolrServerException
     * @throws IOException 
     */
    public SolrDocumentList getById(String collection, Collection<String> ids) throws SolrServerException, IOException {
        return solr.getById(collection, ids);
    }

    /**
     * 
     * @param collection
     * @param id
     * @return SolrDocument
     * @throws SolrServerException
     * @throws IOException 
     */
    public SolrDocument getById(String collection, String id) throws SolrServerException, IOException {
        return solr.getById(collection, id);
    }
    
    /**
     * 
     * @param params
     * @return QueryResponse
     * @throws SolrServerException
     * @throws IOException 
     */
    public QueryResponse query(SolrParams params) throws SolrServerException, IOException{
        return solr.query(params);
    }
    
    /**
     * 
     * @param collection
     * @param params
     * @return QueryResponse
     * @throws SolrServerException
     * @throws IOException 
     */
    public QueryResponse query(String collection, SolrParams params) throws SolrServerException, IOException{
        return solr.query(collection, params);
    }
    
    /**
     * 
     * @param collection
     * @return boolean
     * @throws SolrServerException
     * @throws IOException 
     */
    public boolean rollback(String collection) throws SolrServerException, IOException {
        UpdateResponse response = solr.rollback(collection);
        return this.getStatus(response.getStatus());
    }
    
    /**
     * 
     * @return boolean
     * @throws SolrServerException
     * @throws IOException 
     */
    public boolean rollback() throws SolrServerException, IOException {
        UpdateResponse response = solr.rollback();
        return this.getStatus(response.getStatus());
    }
    
    /**
     *
     * @param statusVal
     * @return boolean status
     */
    private boolean getStatus(int statusVal) {

        boolean status = false;

        if (statusVal == 0) {
            status = true;
        }
        return status;
    }

}
