/*
 * Solr 5 Connect
 */
package org.search.solr.service.solr;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author mw8
 */
public class InputServiceTikaFile extends InputServiceTika {
    
    Logger logger = LoggerFactory.getLogger(getClass());

    private SolrInputDocument sid;
    private InputStream is;
    private String ipFile;
    
    public InputServiceTikaFile(String filePathName) {
        this.ipFile = filePathName;
        this.is = null;
        this.sid = new SolrInputDocument();
    }
    
    /**
     * boolean status
     */
    public InputStream convertFileToStream(String ipFile) {
        boolean status = false;
        InputStream is = null;

        File initialFile = new File(ipFile);
        try {
            is = new FileInputStream(initialFile);
        } catch (FileNotFoundException ex) {
            logger.error("Exception" + InputServiceTika.class.getName() + ex);
        } finally {
            if (is != null) {
                status = true;
            }
        }
        return is;
    }
    
}
