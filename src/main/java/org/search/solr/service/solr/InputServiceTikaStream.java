/*
 * Solr 5 Connect
 */
package org.search.solr.service.solr;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.logging.Level;
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
     * Add Tika Doc to SolrInputDocument
     *
     * @param is
     * @return boolean status
     */
    public boolean processTikaDoc(InputStream is) {
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
                dirOk = this.checkDirStatus(currentDocDir);
                sid.addField("directory", currentDocDir);
                
                //Add Filename
                FileType fileType = new FileType();
                String fileExtension = fileType.getExtension(is);
                
                String newDocName = solrDocId + fileExtension;
                sid.addField("filename", newDocName);
                
                //Upload file to repository
                boolean fileUploadStatus = this.uploadFileToDir(newDocName, currentDocDir);
                
                //If all status flags ok, upload data to Solr
                
            }

        } catch (FileNotFoundException ex) {
            logger.error("Exception" + InputServiceTika.class.getName() + ex);
        } catch (IOException ex) {
            logger.error("Exception" + InputServiceTika.class.getName() + ex);
        }

        return status;
    }

    /**
     * Upload file to Repository
     * 
     * @param docName
     * @return 
     */
    private boolean uploadFileToDir(String docName, String docDir) throws IOException {
        boolean status = false;

        String repository = this.getRepositoryDir(collection);
        
        String fileDestination = repository + docDir + "/" + docName;
        
        FileUploadService uploadService = new FileUploadService();
        status = uploadService.moveFile(is, fileDestination);
        
        return status;
    }

    public boolean doInputData() {
        boolean status = false;

        return status;
    }

    /**
     * Check to see if the directory exists, and if not, create it.
     * 
     * @param currentDocDir
     * @return 
     */
    private boolean checkDirStatus(String currentDocDir) {
        boolean status = false;

        File currentFileDir = new File(this.getRepositoryDir(collection) + currentDocDir);
        
        //Does directory exist, if it doesn't then create it.
        if (currentFileDir.exists()){
            status = true;
        }else{
            status = currentFileDir.mkdir();
        }
        
        logger.debug("Directory Status: " + currentFileDir.exists());
       
        return status;

    }
    
    
    /**
     * 
     * @param collection
     * @return String respositoryDir
     */
    private String getRepositoryDir(String collection){
 
        String propFile = collection + ".properties";
        
        DefaultProperties prop = new DefaultProperties(propFile);
        
        String respositoryDir = prop.getPropValue("doc_live_directory");
        
        return respositoryDir;
    }

}
