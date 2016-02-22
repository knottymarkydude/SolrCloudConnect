/*
 * Solr 5 Connect
 */
package org.search.solr.service.solr;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.SolrInputField;
import org.apache.tika.metadata.Metadata;
import org.search.solr.service.docs.tika.TikaDoc;
import org.search.solr.service.docs.tika.TikaProcessor;
import org.search.solr.service.file.FileType;
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
        boolean dirOk = false;
        boolean fileUploadStatus = false;

        try {
            tikaDoc = tikaProcessor.processInputStream(is);

            if (!tikaDoc.getFileContent().isEmpty()){
                status = true;
            }
            
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
                String fileExtension = fileType.getFileType(metadata);

                String newDocName = solrDocId + fileExtension;
                sid.addField("filename", newDocName);
                
                String destFileName = this.getRepositoryDir(collection) + currentDocDir +"/"+ newDocName;
                File destFile = new File(destFileName);
        
                Path destFilePath = new File(destFileName).toPath();
                //Upload file to repository
                BufferedInputStream bis = new BufferedInputStream(is);
                Files.copy(is, destFilePath, StandardCopyOption.REPLACE_EXISTING);
                //fileUploadStatus = super.uploadFileToDir(bis, newDocName, currentDocDir, collection);

                //If all status flags ok, upload data to Solr
                if(!dirOk && !fileUploadStatus ){
                    status = false;
                }
            }
        } catch (FileNotFoundException ex) {
            logger.error("Exception" + InputServiceTika.class.getName() + ex);
            status = false;
        } catch (IOException ex) {
            logger.error("Exception" + InputServiceTika.class.getName() + ex);
            status = false;
        }

        return status;
    }
    
    /**
     *
     * @param collection
     * @return String respositoryDir
     */
    private String getRepositoryDir(String collection) {

        String propFile = collection + ".properties";

        DefaultProperties prop = new DefaultProperties(propFile);

        String respositoryDir = prop.getPropValue("doc_live_directory");

        return respositoryDir;
    }
}
