/*
 * Solr 5 Connect
 */
package org.search.solr.service.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author mw8
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
}
