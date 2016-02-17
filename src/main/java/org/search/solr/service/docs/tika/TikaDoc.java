/*
 * Solr 5 Connect
 */
package org.search.solr.service.docs.tika;

import org.apache.tika.metadata.Metadata;

/**
 * Document to collect text for file supported by Tika
 * 
 * @author mw8
 */
public class TikaDoc {
    
    private String fileContent;
    private Metadata metaData;

    public TikaDoc() {
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    public Metadata getMetaData() {
        return metaData;
    }

    public void setMetaData(Metadata metaData) {
        this.metaData = metaData;
    }

    @Override
    public String toString() {
        return "DocData{" + "fileContent=" + fileContent + ", metaData=" + metaData + '}';
    }
}
