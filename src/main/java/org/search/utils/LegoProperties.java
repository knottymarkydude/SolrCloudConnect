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
public class LegoProperties {

    public String getPropValue(String propKey) {
        InputStream inputStream;

        String propValue;

        Properties prop = new Properties();
        String propFileName = "lego.properties";

        inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

        if (inputStream != null) {
            try {
                prop.load(inputStream);
            } catch (IOException ex) {
                Logger.getLogger(SolrProperties.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return propValue = ((propKey == null) ? "N/A" : prop.getProperty(propKey));
    }
}
