/*
 * Solr 5 Connect
 */
package org.search.solr.service.solr;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.search.solr.connect.SolrCloudConnect;
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
        status = uploadService.moveFile(is, fileDestination);

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
     * @param solrDoc
     * @return boolean status
     */
    @Override
    public boolean sendData(SolrInputDocument solrDoc) {
        boolean status = false;
        return super.sendData(solrDoc);
    }

}
