/*
 * Solr 5 Connect
 */
package org.search.solr.service.file;

import java.io.File;
import java.io.IOException;

/**
 * File helper , to help with File manipulation
 *
 * @author mw8
 *
 * Date : 20/07/2015
 *
 */
public class FileService {

    private String fileName = null;
    private File file = null;

    /**
     * 
     * @param fileName 
     */
    public FileService(String fileName) {
        this.fileName = fileName;
        file = new File(this.fileName);
    }

    /**
     * 
     * @return String filename
     */
    public String getFilename() {
        String s = null;
        if (this.file.exists()) {
            s = this.file.getName();
        }
        return s;
    }

    /**
     * 
     * @return String path
     */
    public String getPath() {
        String s = null;
        if (this.file.exists()) {
            s = this.file.getPath();
        }
        return s;
    }

    /**
     * 
     * @return String Absolute path 
     */
    public String getAbsPath() {
        String s = null;
        if (this.file.exists()) {
            s = this.file.getAbsolutePath();
        }
        return s;
    }

    /**
     * 
     * @return String parent 
     */
    public String getParent() {
        String s = null;
        if (this.file.exists()) {
            s = this.file.getParent();
        }
        return s;
    }

    /**
     * 
     * @return boolean isDirectory
     */
    public boolean isDir() {
        boolean s = false;
        if (this.file.exists()) {
            s = this.file.isDirectory();
        }
        return s;
    }

    /**
     * 
     * @return boolean isFile 
     */
    public boolean isFile() {
        boolean s = false;
        if (this.file.exists()) {
            s = this.file.isFile();
        }
        return s;
    }

    /**
     * 
     * @return boolean File Created
     * @throws IOException 
     */
    public boolean createFile() throws IOException {
        return this.file.createNewFile();
    }

    /**
     * 
     * @return  boolean File Deleted
     */
    public boolean deleteFile() {
        return this.file.delete();
    }
    
}
