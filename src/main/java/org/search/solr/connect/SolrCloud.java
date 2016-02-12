/*
 * Solr 5 Connect
 */
package org.search.solr.connect;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SolrPingResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.SolrParams;

/**
 *
 * Connect to Solr Server.
 * 
 * @author mw8
 */
public interface SolrCloud {
    
    public boolean pingServer() throws SolrServerException, IOException;
    public SolrPingResponse pingServerDetails()  throws SolrServerException, IOException;
    public boolean add(SolrInputDocument solrInputDocument, int commitWithinMs) throws SolrServerException, IOException;
    public boolean add(Collection<SolrInputDocument> solrInputDocuments) throws SolrServerException, IOException;
    public boolean add(Collection<SolrInputDocument> solrInputDocuments, int commitWithinMs) throws SolrServerException, IOException;
    public boolean add(String collection, Collection<SolrInputDocument> solrInputDocuments, int commitWithinMs) throws SolrServerException, IOException;
    public boolean add(String collection, Collection<SolrInputDocument> solrInputDocuments)  throws SolrServerException, IOException;
    public boolean commit() throws SolrServerException, IOException;
    public boolean deleteById(List<String> ids) throws SolrServerException, IOException;
    public boolean deleteById(List<String> ids, int commitWithinMs) throws SolrServerException, IOException;
    public boolean deleteById(String id) throws SolrServerException, IOException;
    public boolean deleteById(String id, int commitWithinMs) throws SolrServerException, IOException;
    public boolean deleteById(String collection, List<String> ids, int commitWithinMs) throws SolrServerException, IOException;
    public boolean deleteById(String collection, String id) throws SolrServerException, IOException;
    public boolean deleteById(String collection, String id, int commitWithinMs) throws SolrServerException, IOException;
    public boolean deleteByQuery(String query) throws SolrServerException, IOException;
    public boolean deleteByQuery(String collection, String query, int commitWithinMs) throws SolrServerException, IOException;
    public boolean deleteByQuery(String collection, String query) throws SolrServerException, IOException;
    public SolrDocumentList getById(Collection<String> ids, SolrParams params) throws SolrServerException, IOException;
    public SolrDocumentList getById(Collection<String> ids) throws SolrServerException, IOException;
    public SolrDocumentList getById(String collection, Collection<String> ids, SolrParams params) throws SolrServerException, IOException;
    public SolrDocumentList getById(String collection, Collection<String> ids) throws SolrServerException, IOException;
    public SolrDocument getById(String id) throws SolrServerException, IOException;
    public SolrDocument getById(String collection, String id) throws SolrServerException, IOException;
    public QueryResponse query(SolrParams params) throws SolrServerException, IOException;
    public QueryResponse query(String collection, SolrParams params) throws SolrServerException, IOException;
}
