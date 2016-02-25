/*
 * Solr 5 Connect
 */
package org.search.solr.service.solr.docs;

/**
 *
 * @author mw8
 */
public class NewSolrDoc {
    
    private String solrId;
    private String currentDir;
    private String repository;
    private String filePath;
    private String filename;

    public String getSolrId() {
        return solrId;
    }

    public void setSolrId(String solrId) {
        this.solrId = solrId;
    }

    public String getCurrentDir() {
        return currentDir;
    }

    public void setCurrentDir(String currentDir) {
        this.currentDir = currentDir;
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public String toString() {
        return "NewSolrDoc{" + "solrId=" + solrId + ", currentDir=" + currentDir + ", repository=" + repository + ", filePath=" + filePath + ", filename=" + filename + '}';
    }

}
