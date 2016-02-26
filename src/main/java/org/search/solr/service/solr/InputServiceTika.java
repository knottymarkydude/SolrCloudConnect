/*
 * Solr 5 Connect
 */
package org.search.solr.service.solr;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.apache.solr.common.SolrInputDocument;
import org.search.solr.service.file.FileUploadService;
import org.search.solr.service.id.UniqueId;
import org.search.utils.DefaultProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Service that sends Tika documents to Solr
 *
 * @author mw8
 */
public class InputServiceTika extends InputServiceSolrImpl {

    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     *
     * @return String id
     */
    public String generateId() {
        UniqueId myId = new UniqueId();
        return myId.getId();
    }

    /**
     *
     * @param is
     * @param docName
     * @param docDir
     * @param collection
     * @return boolean status
     * @throws IOException
     */
    public boolean uploadFileToDir(InputStream is, String docName, String docDir, String collection) throws IOException {
        boolean status = false;

        String repository = this.getRepositoryDir(collection);

        String fileDestination = repository + docDir + "/" + docName;

        FileUploadService uploadService = new FileUploadService();
        status = uploadService.copyFile(is, fileDestination);

        return status;
    }
    
    /**
     * 
     * @param bis
     * @param filePath
     * @return boolean status
     * @throws IOException 
     */
    public boolean uploadFileToDir(BufferedInputStream bis, String filePath) throws IOException {
        boolean status = false;

        FileUploadService uploadService = new FileUploadService();
        status = uploadService.copyFile(bis, filePath);

        return status;
    }

    /**
     *
     * @param collection
     * @return String respositoryDir
     */
    String getRepositoryDir(String collection) {

        String propFile = collection + ".properties";

        DefaultProperties prop = new DefaultProperties(propFile);

        String respositoryDir = prop.getPropValue("doc_live_directory");

        return respositoryDir;
    }

    /**
     * Check to see if the directory exists, and if not, create it.
     *
     * @param currentDocDir
     * @return boolean status
     */
    boolean checkDirStatus(String currentDocDir, String collection) {
        boolean status = false;

        File currentFileDir = new File(this.getRepositoryDir(collection) + currentDocDir);

        //Does directory exist, if it doesn't then create it.
        if (currentFileDir.exists()) {
            status = true;
        } else {
            status = currentFileDir.mkdir();
        }

        logger.debug("Directory Status: " + currentFileDir.exists());

        return status;

    }

    /**
     * 
     * @param sid
     * @return boolean status
     */
    @Override
    public boolean sendData(SolrInputDocument sid) {
        boolean status = false;
        return super.sendData(sid);
    }

    /**
     * 
     * @param sid
     * @param collection
     * @return boolean status
     */
    @Override
    boolean sendData(SolrInputDocument sid, String collection) {
        boolean status = false;
        return super.sendData(sid, collection);
    }

}
