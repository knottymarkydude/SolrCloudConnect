/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.search.solr.service.file;

import java.io.File;
import java.io.IOException;
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
public class FileUploadServiceTest {

    Logger logger = LoggerFactory.getLogger(getClass());

    File fileSource;
    File fileDestination;
    String dirSource;
    String dirDestination;
    String filename1, filename2, filename3;

    FileUploadService fus;

    String newFilename;
    String origFilename;

    @Before
    public void setUp() {
        fus = new FileUploadService();

        dirSource = "/Users/mw8/Desktop/journals/";
        dirDestination = "/www/data/spar/201508/";

        filename1 = "test1.pdf";
        filename2 = "test2.pdf";
        filename3 = "test3.pdf";

        newFilename = dirSource + filename3;
        origFilename = dirSource + filename2;

    }

    @After
    public void tearDown() throws Exception {
        boolean result = fus.setNewFilename(origFilename, newFilename);
    }

    @Test
    public void testCopyFile() {
        logger.debug("testCopyFile");

        fileSource = new File(dirSource + filename1);
        fileDestination = new File(dirDestination + filename1);

        boolean b = false;

        try {
            b = fus.copyFile(fileSource, fileDestination);
        } catch (IOException e) {
            logger.error("IOException: " + e);
        }
        assertTrue(b);
    }

    /**
     * Test of setNewFilename method, of class FileUploadService.
     */
    @Test
    public void testSetNewFilename() {
        logger.debug("testSetNewFilename");

        String newFilename = dirSource + filename3;
        String origFilename = dirSource + filename2;

        boolean expResult = true;
        boolean result = fus.setNewFilename(newFilename, origFilename);
        assertEquals(expResult, result);
    }

}
