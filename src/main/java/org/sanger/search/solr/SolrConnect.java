package org.sanger.search.solr;

import java.io.IOException;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.SolrPingResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.LoggerFactory;

/**
 *
 * @author mw8
 */
public class SolrConnect {
    
    org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());
    
    private final String zkHostString = "web-solrclouddev-02:8007,web-solrclouddev-02:8008,web-solrclouddev-02:8009";
    private final String defaultCollection;
    private final CloudSolrClient solr;
    
    public SolrConnect() {
        this.defaultCollection = "spar";
        solr = new CloudSolrClient(zkHostString);
        solr.setDefaultCollection(defaultCollection);
        solr.connect();

    }

    public SolrPingResponse pingServer() throws SolrServerException, IOException {
        return this.solr.ping();
    }

    public UpdateResponse addDoc(SolrInputDocument solrInputDocument) throws SolrServerException, IOException {
        UpdateResponse add = solr.add(solrInputDocument, 1000);
        return add;
    }
    
}
