/*
 * Solr 5 Connect
 */
package org.search.solr.service.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * File Upload Service
 * 
 * @author mw8
 * 
 * @since Feb 2016
 */
public class FileUploadService {
    Logger logger = LoggerFactory.getLogger(getClass());
    
    /**
     *
     * Copy a file from 1 place to another
     * 
     * @param sourceFile
     * @param destFile
     * @return boolean
     * @throws IOException
     */
    public boolean copyFile(File sourceFile, File destFile) throws IOException {
        if (!sourceFile.exists()) {
            return false;
        }
        if (!destFile.exists()) {
            destFile.createNewFile();
        }
        FileChannel source = null;
        FileChannel destination = null;
        source = new FileInputStream(sourceFile).getChannel();
        destination = new FileOutputStream(destFile).getChannel();
        if (destination != null && source != null) {
            destination.transferFrom(source, 0, source.size());
        }
        if (source != null) {
            source.close();
        }
        if (destination != null) {
            destination.close();
        }
        return destFile.exists();

    }
    
    /**
     * 
     * Move InputStream to File
     * 
     * @param is
     * @param destFileName
     * @return boolean
     * @throws IOException 
     */
    public boolean copyFile(InputStream is, String destFileName) throws IOException {
        
        File destFile = new File(destFileName);
        
        Path destFilePath = new File(destFileName).toPath();
        
        Files.copy(is, destFilePath, StandardCopyOption.REPLACE_EXISTING);
                
        return destFile.exists();

    }
    
    /**
     * 
     * @param newFilename
     * @param origFilename
     * @return boolean result
     */
    public boolean setNewFilename(String newFilename, String origFilename) {

        boolean renamed = false;
        boolean checkCanWrite = false;

        try {

            File newFile = new File(newFilename);
            File origFile = new File(origFilename);

            if (origFile.exists()) {
                renamed = origFile.renameTo(newFile);
            }

        } catch (Exception e) {
            logger.error("Exception", e);
        }

        return renamed;
    }
}
