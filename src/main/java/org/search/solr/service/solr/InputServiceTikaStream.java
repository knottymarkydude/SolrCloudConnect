/*
 * Solr 5 Connect
 */
package org.search.solr.service.solr;

import java.io.FileNotFoundException;
import java.io.InputStream;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.SolrInputField;
import org.search.solr.service.docs.tika.TikaDoc;
import org.search.solr.service.docs.tika.TikaProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Input SolrInputDocument and tika document via an InputStream
 * 
 * @author mw8
 */
public class InputServiceTikaStream extends InputServiceTika {
    
    Logger logger = LoggerFactory.getLogger(getClass());

    private SolrInputDocument sid;
    private InputStream is;
    private String ipFile;
    
    public InputServiceTikaStream(SolrInputDocument sid, InputStream is) {
        this.sid = sid;
        this.is = is;
    }
    
    public boolean processTikaDoc(InputStream is) {
        boolean status = false;
        SolrInputField idField = null;
        String solrDocId = null;
        TikaDoc tikaDoc;
        TikaProcessor tikaProcessor = new TikaProcessor();
        SolrInputDocument sid = new SolrInputDocument();

        try {
            tikaDoc = tikaProcessor.processInputStream(is);

            //Add content to SolrInputDocument
            logger.debug("add Content to SolrInputDoc");
            sid.addField("content", tikaDoc.getFileContent());
            idField = sid.getField("id");
            if (idField == null) {
                solrDocId = super.generateId();
                sid.addField("id", solrDocId);
            }

        } catch (FileNotFoundException ex) {
            logger.error("Exception" + InputServiceTika.class.getName() + ex);
        }

        return status;
    }
    
    public boolean doInputData() {
        boolean status = false;
        
        
        
        return status;
    }
    
}
