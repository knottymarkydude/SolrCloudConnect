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
import java.time.LocalDate;
import org.apache.solr.common.SolrInputDocument;
import org.apache.tika.metadata.Metadata;
import org.search.solr.service.docs.tika.TikaDoc;
import org.search.solr.service.docs.tika.TikaProcessor;
import org.search.solr.service.file.FileType;
import org.search.solr.service.file.FileUploadService;
import org.search.solr.service.solr.docs.NewSolrDoc;
import org.search.solr.utilities.date.DateUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Input SolrInputDocument. Uploads file to Solr.
 *
 * The field called filename references a document kept in the repository. To
 * update the form data, the file needs to be parsed again, and added to the
 * content field
 *
 * This class is used to get a file from the command line and transformed into
 * an InputStream.
 *
 * @author mw8
 */
public class InputServiceTikaFile extends InputServiceTika {

    Logger logger = LoggerFactory.getLogger(getClass());

    private final String collection;
    private SolrInputDocument sid;
    private String tikaFile;
    private NewSolrDoc newSolrDoc;

    public InputServiceTikaFile(String collection, String tikaFile) {
        this.collection = collection;
        this.tikaFile = tikaFile;
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
            fileForSolr = this.uploadFileToRepository();
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
            status = super.sendData(sid, this.collection);
        }

        return status;
    }

    /**
     *
     * Uploads document to the repository
     *
     * @return String filename
     * @throws IOException
     */
    private File uploadFileToRepository() throws IOException {
        String solrDocId = super.generateId();
        boolean status = false;
        newSolrDoc = new NewSolrDoc();
        newSolrDoc.setSolrId(solrDocId);

        //Check directory and create it if it's not there
        LocalDate thisMonthYear = LocalDate.now();
        String currentDocDir = DateUtility.getFormattedDate(thisMonthYear, "YYYYMM");
        boolean dirOk = super.checkDirStatus(currentDocDir, collection);
        if (dirOk) {
            newSolrDoc.setCurrentDir(currentDocDir);
        }

        String repository = super.getRepositoryDir(collection);
        newSolrDoc.setRepository(repository);

        File sourceFile = new File(this.tikaFile);
        newSolrDoc.setFilename(sourceFile.getName());

        String destFileName = repository + currentDocDir + "/" + sourceFile.getName();

        newSolrDoc.setFilePath(destFileName);
        File destFile = new File(destFileName);
        //Path destFilePath = new File(destFileName).toPath();

        //If directory exists
        if (dirOk) {
            FileUploadService fileUploadService = new FileUploadService();
            status = fileUploadService.copyFile(sourceFile, destFile);
        }

        return destFile;
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
        sid = new SolrInputDocument();

        try {
            BufferedInputStream bis = new BufferedInputStream(solrIs);

            tikaDoc = tikaProcessor.processInputStream(bis);

            //Add content to SolrInputDocument
            logger.debug("add Content to SolrInputDoc");
            sid.addField("content", tikaDoc.getFileContent());

            logger.info("Content Value" + sid.getField("content").getValueCount());
            if (sid.getField("content").getValueCount() > 0) {
                status = true;
            }

            //add content_type field
            metadata = tikaDoc.getMetaData();
            sid.addField("content_type", FileType.getContentType(metadata));
            String currentDocDir = newSolrDoc.getCurrentDir();
            sid.addField("directory", currentDocDir);
            sid.addField("filename", newSolrDoc.getFilename());
            sid.addField("id", newSolrDoc.getSolrId());

        } catch (FileNotFoundException ex) {
            logger.error("Exception" + InputServiceTika.class.getName() + ex);
            status = false;
        }

        return status;
    }

}
