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
 * Creates a connection to the Solr Cloud instance so that you can carry out
 * tasks. CRUD tasks as well as querying the index.
 * 
 *
 * @author mw8
 */
public class SolrCloudConnect implements SolrConnect {

    org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

    private final String zkHostKey = "zkHost";
    private final String zkHostPropVal; //"web-solrclouddev-02:8007,web-solrclouddev-02:8008,web-solrclouddev-02:8009";
    private final String defaultCollection;
    private final CloudSolrClient solr;

    
    /**
     *  Creates connection to Solr Instance from property zkHost in properties file. Sets the default collection from property defaultCollection
     */
    public SolrCloudConnect() {

        SolrProperties props = new SolrProperties();
        zkHostPropVal = props.getPropValue(zkHostKey);
        this.defaultCollection = props.getPropValue("defaultCollection");
        solr = new CloudSolrClient(zkHostPropVal);
        solr.setDefaultCollection(defaultCollection);
        solr.connect();

    }

    /**
     *
     * Returns a status of sending a ping request to the server.
     * 
     * @return boolean status of ping
     * @throws org.apache.solr.client.solrj.SolrServerException
     * @throws java.io.IOException
     */
    @Override
    public boolean pingServer() throws SolrServerException, IOException {
        SolrPingResponse response = solr.ping();
        return this.getStatus(response.getStatus());
    }

    /**
     *
     * Sends a ping request to the connected Solr Server, and returns a SolrPingResponse
     * for more detail
     * 
     * @throws org.apache.solr.client.solrj.SolrServerException
     * @throws java.io.IOException
     * @see org.apache.solr.client.solrj.response.SolrPingResponse
     * 
     * @return SolrPingResponse Ping Response
     */
    @Override
    public SolrPingResponse pingServerDetails() throws SolrServerException, IOException {
        return solr.ping();
    }

    /**
     * Adds a document
     * 
     * @param solrInputDocument
     * @return boolean status
     * @throws SolrServerException
     * @throws IOException 
     */
    public boolean add(SolrInputDocument solrInputDocument) throws SolrServerException, IOException {
        UpdateResponse response = solr.add(solrInputDocument);
        return this.getStatus(response.getStatus());
    }
    
    /**
     * Adds a document, specifying max time before they become committed
     *
     * @param solrInputDocument
     * @param commitWithinMs
     * @return boolean status
     * @throws org.apache.solr.client.solrj.SolrServerException
     * @throws java.io.IOException
     */
    @Override
    public boolean add(SolrInputDocument solrInputDocument, int commitWithinMs) throws SolrServerException, IOException {
        UpdateResponse response = solr.add(solrInputDocument, commitWithinMs);
        return this.getStatus(response.getStatus());
    }

    /**
     *
     * @param solrInputDocuments
     * @return boolean
     * @throws org.apache.solr.client.solrj.SolrServerException
     * @throws java.io.IOException
     */
    @Override
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
     * @throws org.apache.solr.client.solrj.SolrServerException
     * @throws java.io.IOException
     */
    @Override
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
     * @throws org.apache.solr.client.solrj.SolrServerException
     * @throws java.io.IOException
     */
    @Override
    public boolean add(String collection, Collection<SolrInputDocument> solrInputDocuments, int commitWithinMs) throws SolrServerException, IOException {
        UpdateResponse response = solr.add(collection, solrInputDocuments, commitWithinMs);
        return this.getStatus(response.getStatus());
    }

    /**
     *
     * @param collection
     * @param solrInputDocuments
     * @return boolean
     * @throws org.apache.solr.client.solrj.SolrServerException
     * @throws java.io.IOException
     */
    @Override
    public boolean add(String collection, Collection<SolrInputDocument> solrInputDocuments) throws SolrServerException, IOException {
        UpdateResponse response = solr.add(collection, solrInputDocuments);
        return this.getStatus(response.getStatus());
    }

    /**
     *
     * @return boolean status
     * @throws org.apache.solr.client.solrj.SolrServerException
     * @throws java.io.IOException
     */
    @Override
    public boolean commit() throws SolrServerException, IOException {
        UpdateResponse response = solr.commit();
        return this.getStatus(response.getStatus());
    }

    /**
     *
     * @param ids
     * @return
     * @throws org.apache.solr.client.solrj.SolrServerException
     * @throws java.io.IOException
     */
    @Override
    public boolean deleteById(List<String> ids) throws SolrServerException, IOException {
        UpdateResponse response = solr.deleteById(ids);
        return this.getStatus(response.getStatus());
    }

    /**
     *
     * @param ids
     * @param commitWithinMs
     * @return
     * @throws org.apache.solr.client.solrj.SolrServerException
     * @throws java.io.IOException
     */
    @Override
    public boolean deleteById(List<String> ids, int commitWithinMs) throws SolrServerException, IOException {
        UpdateResponse response = solr.deleteById(ids, commitWithinMs);
        return this.getStatus(response.getStatus());
    }

    /**
     *
     * @param id
     * @return
     * @throws org.apache.solr.client.solrj.SolrServerException
     * @throws java.io.IOException
     */
    @Override
    public boolean deleteById(String id) throws SolrServerException, IOException {
        UpdateResponse response = solr.deleteById(id);
        return this.getStatus(response.getStatus());
    }

    /**
     *
     * @param id
     * @param commitWithinMs
     * @return boolean status
     * @throws org.apache.solr.client.solrj.SolrServerException
     * @throws java.io.IOException
     */
    @Override
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
     * @throws org.apache.solr.client.solrj.SolrServerException
     * @throws java.io.IOException
     */
    @Override
    public boolean deleteById(String collection, List<String> ids, int commitWithinMs) throws SolrServerException, IOException {
        UpdateResponse response = solr.deleteById(collection, ids, commitWithinMs);
        return this.getStatus(response.getStatus());
    }

    /**
     *
     * @param collection
     * @param id
     * @return
     * @throws SolrServerException
     * @throws IOException
     */
    @Override
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
     * @throws org.apache.solr.client.solrj.SolrServerException
     * @throws java.io.IOException
     */
    @Override
    public boolean deleteById(String collection, String id, int commitWithinMs) throws SolrServerException, IOException {
        UpdateResponse response = solr.deleteById(collection, id, commitWithinMs);
        return this.getStatus(response.getStatus());
    }

    /**
     *
     * @param query
     * @param commitWithinMs
     * @return
     * @throws org.apache.solr.client.solrj.SolrServerException
     * @throws java.io.IOException
     */
    public boolean deleteByQuery(String query, int commitWithinMs) throws SolrServerException, IOException {
        UpdateResponse response = solr.deleteByQuery(query, commitWithinMs);
        return this.getStatus(response.getStatus());
    }

    /**
     *
     * @param query
     * @return
     * @throws org.apache.solr.client.solrj.SolrServerException
     * @throws java.io.IOException
     */
    @Override
    public boolean deleteByQuery(String query) throws SolrServerException, IOException {
        UpdateResponse response = solr.deleteByQuery(query);
        return this.getStatus(response.getStatus());
    }

    /**
     *
     * @param collection Solr Collection
     * @param query Solr Query
     * @param commitWithinMs Solr commit time
     * @return boolean status
     * @throws org.apache.solr.client.solrj.SolrServerException
     * @throws java.io.IOException
     */
    @Override
    public boolean deleteByQuery(String collection, String query, int commitWithinMs) throws SolrServerException, IOException {
        UpdateResponse response = solr.deleteByQuery(collection, query, commitWithinMs);
        return this.getStatus(response.getStatus());
    }

    /**
     *
     * @param collection
     * @param query
     * @return boolean
     * @throws org.apache.solr.client.solrj.SolrServerException
     * @throws java.io.IOException
     */
    @Override
    public boolean deleteByQuery(String collection, String query) throws SolrServerException, IOException {
        UpdateResponse response = solr.deleteByQuery(collection, query);
        return this.getStatus(response.getStatus());
    }

    /**
     *
     * @param ids
     * @param params
     * @return SolrDocumentList
     * @throws org.apache.solr.client.solrj.SolrServerException
     * @throws java.io.IOException
     */
    @Override
    public SolrDocumentList getById(Collection<String> ids, SolrParams params) throws SolrServerException, IOException {
        return solr.getById(ids, params);
    }

    /**
     *
     * @param ids
     * @return SolrDocumentList
     * @throws org.apache.solr.client.solrj.SolrServerException
     * @throws java.io.IOException
     */
    @Override
    public SolrDocumentList getById(Collection<String> ids) throws SolrServerException, IOException {
        return solr.getById(ids);
    }

    /**
     *
     * @param id
     * @return SolrDocument
     * @throws org.apache.solr.client.solrj.SolrServerException
     * @throws java.io.IOException
     */
    public SolrDocument getById(String id) throws SolrServerException, IOException {
        return solr.getById(id);
    }

    /**
     *
     * @param collection Solr Collection
     * @param ids
     * @param params
     * @return SolrDocumentList
     * @throws org.apache.solr.client.solrj.SolrServerException
     * @throws java.io.IOException
     */
    @Override
    public SolrDocumentList getById(String collection, Collection<String> ids, SolrParams params) throws SolrServerException, IOException {
        return solr.getById(collection, ids, params);
    }

    /**
     *
     * @param collection Solr Collection
     * @param ids
     * @return SolrDocumentList
     * @throws org.apache.solr.client.solrj.SolrServerException
     * @throws java.io.IOException
     */
    @Override
    public SolrDocumentList getById(String collection, Collection<String> ids) throws SolrServerException, IOException {
        return solr.getById(collection, ids);
    }

    /**
     *
     * @param collection Solr Collection
     * @param id Solr Document Id
     * @return SolrDocument
     * @throws org.apache.solr.client.solrj.SolrServerException
     * @throws java.io.IOException
     */
    @Override
    public SolrDocument getById(String collection, String id) throws SolrServerException, IOException {
        return solr.getById(collection, id);
    }

    /**
     *
     * @param params
     * @return QueryResponse
     * @throws org.apache.solr.client.solrj.SolrServerException
     * @throws java.io.IOException
     */
    @Override
    public QueryResponse query(SolrParams params) throws SolrServerException, IOException {
        return solr.query(params);
    }

    /**
     *
     * @param collection
     * @param params
     * @return QueryResponse
     * @throws org.apache.solr.client.solrj.SolrServerException
     * @throws java.io.IOException
     */
    @Override
    public QueryResponse query(String collection, SolrParams params) throws SolrServerException, IOException {
        return solr.query(collection, params);
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
