/*
 * Solr 5 Connect
 */
package org.search.solr.service.solr;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.SolrInputField;
import org.apache.tika.metadata.Metadata;
import org.search.solr.service.docs.tika.TikaDoc;
import org.search.solr.service.docs.tika.TikaProcessor;
import org.search.solr.service.file.FileType;
import org.search.solr.service.file.FileUploadService;
import org.search.solr.utilities.date.DateUtility;
import org.search.utils.DefaultProperties;
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

    @SuppressWarnings("FieldMayBeFinal")
    private SolrInputDocument sid;
    @SuppressWarnings("FieldMayBeFinal")
    private InputStream is;
    private String ipFile;
    private String collection;

    public InputServiceTikaStream(SolrInputDocument sid) {
        this.sid = sid;
    }

    public InputServiceTikaStream(SolrInputDocument sid, String collection) {
        this.sid = sid;
        this.collection = collection;
    }

    public InputServiceTikaStream(SolrInputDocument sid, InputStream is) {
        this.sid = sid;
        this.is = is;
    }

    public InputServiceTikaStream(SolrInputDocument sid, InputStream is, String collection) {
        this.sid = sid;
        this.is = is;
        this.collection = collection;
    }

    /**
     * Process Document and send Data to Solr
     *
     * @return status
     */
    public boolean inputDataService() {
        boolean status = false;
        
        status = this.processTikaDoc(is);
        
        if (status){
            status = super.sendData(sid);
        }

        return status;
    }

    /**
     * Add Tika Doc to SolrInputDocument
     *
     * @param is
     * @return boolean status
     */
    private boolean processTikaDoc(InputStream is) {
        boolean status = false;
        SolrInputField idField = null;
        SolrInputField contentType = null;
        String solrDocId = null;
        TikaDoc tikaDoc;
        Metadata metadata;
        TikaProcessor tikaProcessor = new TikaProcessor();
        sid = new SolrInputDocument();
        boolean dirOk = false;

        try {
            tikaDoc = tikaProcessor.processInputStream(is);

            //Add content to SolrInputDocument
            logger.debug("add Content to SolrInputDoc");
            sid.addField("content", tikaDoc.getFileContent());
            idField = sid.getField("id");

            // Check to see if id field is in place, if not then this is a new
            // document, and a new id needs to be generated.
            if (idField == null) {
                solrDocId = super.generateId();
                sid.addField("id", solrDocId);

                //add content_type field
                metadata = tikaDoc.getMetaData();
                sid.addField("content_type", FileType.getContentType(metadata));

                //Add directory and create it if it's not there
                LocalDate thisMonthYear = LocalDate.now();
                String currentDocDir = DateUtility.getFormattedDate(thisMonthYear, "YYYYMM");
                dirOk = super.checkDirStatus(currentDocDir, collection);
                sid.addField("directory", currentDocDir);

                //Add Filename
                FileType fileType = new FileType();
                String fileExtension = fileType.getExtension(is);

                String newDocName = solrDocId + fileExtension;
                sid.addField("filename", newDocName);

                //Upload file to repository
                boolean fileUploadStatus = super.uploadFileToDir(is, newDocName, currentDocDir, collection);

                //If all status flags ok, upload data to Solr 
                if (dirOk && fileUploadStatus) {
                    status = true;
                }

            }
        } catch (FileNotFoundException ex) {
            logger.error("Exception" + InputServiceTika.class.getName() + ex);
        } catch (IOException ex) {
            logger.error("Exception" + InputServiceTika.class.getName() + ex);
        }

        return status;
    }

    

}
