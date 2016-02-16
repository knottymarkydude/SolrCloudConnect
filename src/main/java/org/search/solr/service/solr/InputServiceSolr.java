/*
 * Solr 5 Connect
 */
package org.search.solr.service.solr;

import java.util.Collection;
import org.apache.solr.common.SolrInputDocument;

/**
 *
 * Service responsible for sending data to Solr 
 * 
 * @author mw8
 */
public interface InputServiceSolr {
    public boolean sendData(SolrInputDocument solrDoc);
    public boolean sendData(Collection<SolrInputDocument> solrDocs);
}
