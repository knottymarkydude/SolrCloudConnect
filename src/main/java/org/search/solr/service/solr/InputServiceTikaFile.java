/*
 * Solr 5 Connect
 */
package org.search.solr.service.solr;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.solr.common.SolrInputDocument;
import org.apache.tika.metadata.Metadata;
import org.search.solr.service.docs.tika.TikaDoc;
import org.search.solr.service.docs.tika.TikaProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Input SolrInputDocument. Uploads the web form data to a SolrInputDocument.
 *
 * The field called filename references a document kep in the repository. To
 * update the form data, the file needs to be parsed again, and added to the
 * content field
 *
 * This class is used to edit data from a web form that has a reference to a
 * file, that is transformed into an InputStream.
 *
 * @author mw8
 */
public class InputServiceTikaFile extends InputServiceTika {

    Logger logger = LoggerFactory.getLogger(getClass());

    private final String collection;

    @SuppressWarnings("FieldMayBeFinal")
    private SolrInputDocument sid;
    
    public InputServiceTikaFile(String collection, SolrInputDocument sid) {
        this.collection = collection;
        this.sid = sid;

    }

    /**
     * Process Document and send Data to Solr
     *
     * @return status
     */
    public boolean inputDataService() {
        boolean status = false;
        File fileForSolr = null;
        // Upload file to repository 
        String filePath = null;
        try {
            fileForSolr = this.getFileFromRepository();
        } catch (IOException ex) {
            logger.error("IOException: " + ex);
        }

        if (fileForSolr != null && fileForSolr.exists()) {
            InputStream solrIs;
            try {
                solrIs = new FileInputStream(fileForSolr);
                status = this.processTikaDoc(solrIs);
            } catch (FileNotFoundException ex) {
                logger.error("FileNotFoundException: " + ex);
            }
        }

        if (status) {
            status = super.sendData(sid);
        }

        return status;
    }

    /**
     *
     * Get file from repository
     *
     * @return String filename
     * @throws IOException
     */
    private File getFileFromRepository() throws IOException {
        boolean status = false;

        String repository = super.getRepositoryDir(collection);
        String directory = sid.getFieldValue("directory").toString();
        String filename = sid.getFieldValue("filename").toString();

        String currentFilePath = repository + "/" + directory + "/" + filename;

        File currentFile = new File(currentFilePath);

        return currentFile;
    }

    /**
     * Add Tika Doc to SolrInputDocument
     *
     * @param InputStream solrIs
     * @return boolean status
     */
    private boolean processTikaDoc(InputStream solrIs) {
        boolean status = false;
        TikaDoc tikaDoc;
        Metadata metadata;
        TikaProcessor tikaProcessor = new TikaProcessor();

        try {
            BufferedInputStream bis = new BufferedInputStream(solrIs);

            tikaDoc = tikaProcessor.processInputStream(bis);

            //Add content to SolrInputDocument
            logger.debug("add Content to SolrInputDoc");
            sid.addField("content", tikaDoc.getFileContent());

            logger.info("Content Value" + sid.getField("content").getValueCount());
            if (sid.getField("content").getValueCount() > 0 ) {
                status = true;
            }
            
        } catch (FileNotFoundException ex) {
            logger.error("Exception" + InputServiceTika.class.getName() + ex);
            status = false;
        }

        return status;
    }

}
