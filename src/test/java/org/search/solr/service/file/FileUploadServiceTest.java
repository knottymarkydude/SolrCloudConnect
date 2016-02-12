/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.search.solr.service.file;

import java.io.File;
import java.io.IOException;
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
    String filename;

    FileUploadService fus;

    @Before
    public void setUp() {
        fus = new FileUploadService();
    }
    
    @Test
    public void testCopyFile() {
        dirSource = "/Users/mw8/Desktop/journals/";
        dirDestination = "/www/data/spar/201508/";
        filename = "ironman uk guide 2013.pdf"; 

        fileSource = new File(dirSource + filename);
        fileDestination = new File(dirDestination + filename);

        boolean b = false;

        try {
            b = fus.copyFile(fileSource, fileDestination);
        } catch (IOException e) {
             logger.error("IOException: " + e);
        }
        assertTrue(b);
    }
    
}
