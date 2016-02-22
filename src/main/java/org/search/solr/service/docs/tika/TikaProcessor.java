/*
 * Solr 5 Connect
 */
package org.search.solr.service.docs.tika;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/**
 *
 * @author mw8
 */
public class TikaProcessor {
    
    Logger logger = LoggerFactory.getLogger(getClass());
    
    /**
     * 
     * 
     * 
     * @param stream
     * @return TikaDoc
     * @throws FileNotFoundException 
     */
    public TikaDoc processInputStream(InputStream stream) throws FileNotFoundException {
        
        TikaDoc tikaDoc = new TikaDoc();
        
        BodyContentHandler handler;
        InputStream input = stream;
        BufferedInputStream bis = null;
        
            try {
                
                Metadata metadata = new Metadata();
                ParseContext pcontext = new ParseContext();
                
                bis = new BufferedInputStream(input);
                handler = new BodyContentHandler(-1); // Unlimited

                Parser parser = new AutoDetectParser();
                parser.parse(bis, handler, metadata, pcontext);
                
                logger.debug("Contents of file: " + handler.toString());

                //metadataNames = metadata.names();
                tikaDoc.setMetaData(metadata);
                tikaDoc.setFileContent(handler.toString());
        
            } catch (IOException ex) {
                logger.error("IOException in " + TikaProcessor.class.getName(), ex);
            } catch (SAXException ex) {
                logger.error("SAXException in " + TikaProcessor.class.getName(), ex);
            } catch (TikaException ex) {
                logger.error("TikaException in " + TikaProcessor.class.getName(), ex);
            } finally {
                try {
                    input.close();
                } catch (IOException ex) {
                    logger.error("IOException in " + TikaProcessor.class.getName(), ex);
                }
            }

        
        return tikaDoc;
    }
}
