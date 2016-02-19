/*
 * Solr 5 Connect
 */
package org.search.solr.service.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Get the file extension.
 *
 * @author mw8
 */
public class FileType {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileType.class);

    /**
     *
     * @param inputStream
     * @return String
     */
    public String getExtension(InputStream inputStream){
        String extension = null;
        try {
            extension = this.getMimeTypeExt(inputStream);

        } catch (MimeTypeException | IOException ex) {
            LOGGER.error("IOException in " + FileType.class.getName(), ex);
        }
        return extension;

    }

    /**
     *
     * @param file
     * @return String
     */
    public String getExtension(File file) {
        String extension = null;
        InputStream inputStream;

        try {
            inputStream = new FileInputStream(file);
            extension = this.getMimeTypeExt(inputStream);

        } catch (MimeTypeException | IOException ex) {
            LOGGER.error("IOException in " + FileType.class.getName(), ex);
        }
        return extension;

    }

    private String getMimeTypeExt(InputStream inputStream) throws IOException, MimeTypeException {
        String extension = null;

        TikaConfig config = TikaConfig.getDefaultConfig();

        MediaType mediaType = config.getMimeRepository().detect(inputStream, new Metadata());
        MimeType mimeType = config.getMimeRepository().forName(mediaType.toString());

        extension = mimeType.getExtension();

        return extension;
    }

    /**
     *
     * @param metaData
     * @return
     */
    public static String getContentType(Metadata metaData) {
        String[] metadataNames;
        String contentType = null;

        metadataNames = metaData.names();

        for (String name : metadataNames) {
            if ("Content-Type".equals(name)) {
                contentType = metaData.get(name);
                LOGGER.debug("contentType: " + metaData.get(name));
            }
        }
        return contentType;
    }
}
