/*
 * Solr 5 Connect
 */
package org.search.solr.service.file;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author mw8
 */
public class FileServiceTest {

    Logger logger = LoggerFactory.getLogger(getClass());

    File fileSource;
    File fileDestination;
    String dirSource;
    String dirDestination;
    String filename;

    FileService fsFile;
    FileService fsDir;
    FileUploadService fus;

    public FileServiceTest() {
        
        fileSource = new File(dirSource + "test1.pdf");
        fileDestination = new File(dirSource + filename);
        fus = new FileUploadService();
        try {
            boolean copied = fus.copyFile(fileSource, fileDestination);
        } catch (IOException ex) {
            logger.debug("Exception:  "+ex);
        }
        
        dirSource = "/Users/mw8/Desktop/journals";
        filename = "test.pdf";
        
        fsFile = new FileService(dirSource + "/" + filename);
        fsDir = new FileService(dirSource);
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of createFile method, of class FileService.
     */
    @Test
    public void testCreateFile() throws Exception {
        logger.debug("testCreateFile");

        boolean result = fsFile.createFile();
        logger.debug("Result: " + result);
        assertTrue(result);
    }
    
    /**
     * Test of getFilename method, of class FileService.
     */
    @Test
    public void testGetFilename() {
        logger.debug("testGetFilename");

        String expResult = filename;
        
        String result = fsFile.getFilename();
        logger.debug(result);
        assertEquals(result,expResult);
    }

    /**
     * Test of getPath method, of class FileService.
     */
    @Test
    public void testGetPath() {
        logger.debug("testGetPath");

        String expResult = dirSource;
        
        String result = fsDir.getPath();
        logger.debug(result);
        assertEquals(result,expResult);
    }

    /**
     * Test of getAbsPath method, of class FileService.
     */
    @Test
    public void testGetAbsPath() {
        logger.debug("testGetAbsPath");

        String expResult = dirSource;
        
        String result = fsDir.getAbsPath();
        logger.debug(result);
        assertEquals(result,expResult);
    }

    /**
     * Test of getParent method, of class FileService.
     */
    @Test
    public void testGetParent() {
        logger.debug("testGetParent");

        String expResult = dirSource;
        
        String result = fsFile.getParent();
        logger.debug(result);
        assertEquals(result,expResult);
    }

    /**
     * Test of isDir method, of class FileService.
     */
    @Test
    public void testIsDir() {
        logger.debug("testIsDir");

        boolean result = fsDir.isDir();
        logger.debug("Result: " + result);
        assertTrue(result);
    }

    /**
     * Test of isFile method, of class FileService.
     */
    @Test
    public void testIsFile() {
        logger.debug("testIsFile");

        boolean result = fsFile.isFile();
        logger.debug("Result: " + result);
        assertTrue(result);
    }

    
    /**
     * Test of deleteFile method, of class FileService.
     */
    @Test
    public void testDeleteFile() {
        logger.debug("testDeleteFile");

        boolean result = fsFile.deleteFile();
        assertTrue(result);

    }
}
