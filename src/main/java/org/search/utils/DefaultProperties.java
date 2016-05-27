/*
 * Solr 5 Connect
 */
package org.search.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mw8
 */

public class DefaultProperties {

    private final String propFileName;
    
    public DefaultProperties(String propFileName) {
        this.propFileName = propFileName + ".properties";
    }
    
    /**
     * 
     * @param propKey
     * @return String propValue
     */
    public String getPropValue(String propKey) {
        InputStream inputStream;

        String propValue;

        Properties prop = new Properties();

        inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

        if (inputStream != null) {
            try {
                prop.load(inputStream);
            } catch (IOException ex) {
                Logger.getLogger(DefaultProperties.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return propValue = ((propKey == null) ? "N/A" : prop.getProperty(propKey));
    }
}
