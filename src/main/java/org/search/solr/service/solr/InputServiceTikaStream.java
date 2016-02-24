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
import java.nio.file.Path;
import java.time.LocalDate;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.SolrInputField;
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
 * Input SolrInputDocument and tika document via an InputStream. Uploads the file 
 * to the repository, then parses the file and adds the text to a SolrInputDocument
 * field. 
 * fields added: 
 * filename
 * directory
 * content_type
 * content
 * 
 * This class is used to add data from a web form that has a MultiPartFile as 
 * part of the form, that is transformed into an InputStream.
 *
 * @author mw8
 */
public class InputServiceTikaStream extends InputServiceTika {

    Logger logger = LoggerFactory.getLogger(getClass());

    private final String collection;
    private SolrInputDocument sid;
    private InputStream is;
    private NewSolrDoc newSolrDoc;

    public InputServiceTikaStream(String collection, SolrInputDocument sid, InputStream is) {
        this.is = null;
        this.collection = collection;
        this.sid = sid;
        this.is = is;
    }

    /**
     * Process Document and send Data to Solr
     *
     * @return status
     */
    public boolean inputDataService() {
        boolean status = false;

        // Upload file to repository 
        String filePath = null;
        try {
            filePath = this.uploadFileToRepository();
        } catch (IOException ex) {
            logger.error("IOException: " + ex);
        }

        if (filePath != null) {
            //Create new File
            File fileForSolr = new File(filePath);
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
     * Uploads document to the repository
     * 
     * @return String filename
     * @throws IOException
     */
    private String uploadFileToRepository() throws IOException {
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

        String destFileName = repository + currentDocDir + "/" + solrDocId;
        newSolrDoc.setFilePath(destFileName);
        File destFile = new File(destFileName);
        Path destFilePath = new File(destFileName).toPath();

        //If directory exists
        if (dirOk) {
            status = super.uploadFileToDir(new BufferedInputStream(is), destFileName);
        }

        // Destination File created
        if (!status) {
            destFileName = null;
        }

        return destFileName;
    }

    /**
     * Add Tika Doc to SolrInputDocument
     *
     * @param InputStream solrIs
     * @return boolean status
     */
    private boolean processTikaDoc(InputStream solrIs) {
        String newFileName = null;
        String origFileName = null;
        boolean status = false;
        SolrInputField contentType = null;
        String solrDocId = null;
        TikaDoc tikaDoc;
        Metadata metadata;
        TikaProcessor tikaProcessor = new TikaProcessor();
        boolean dirOk = false;
        boolean fileUploadStatus = false;

        try {
            BufferedInputStream bis = new BufferedInputStream(solrIs);

            tikaDoc = tikaProcessor.processInputStream(bis);

            //Add content to SolrInputDocument
            logger.debug("add Content to SolrInputDoc");
            sid.addField("content", tikaDoc.getFileContent());

            solrDocId = newSolrDoc.getSolrId();

            sid.addField("id", solrDocId);

            //add content_type field
            metadata = tikaDoc.getMetaData();
            sid.addField("content_type", FileType.getContentType(metadata));
            String currentDocDir = newSolrDoc.getCurrentDir();
            sid.addField("directory", currentDocDir);

            //get Extension
            FileType fileType = new FileType();
            String fileExtension = fileType.getFileType(metadata);
            //Add Filename
            String newDocName = solrDocId + fileExtension;
            sid.addField("filename", newDocName);

            newFileName = this.getRepositoryDir(collection) + currentDocDir + "/" + newDocName;
            origFileName = newSolrDoc.getFilePath();
            FileUploadService fileUploadService = new FileUploadService();
            status = fileUploadService.setNewFilename(newFileName, origFileName);

        } catch (FileNotFoundException ex) {
            logger.error("Exception" + InputServiceTika.class.getName() + ex);
            status = false;
        }

        return status;
    }

    
}
