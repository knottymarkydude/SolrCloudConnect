/*
 * Solr 5 Connect
 */
package org.search.solr.service.file;

import org.apache.tika.metadata.Metadata;
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
     * @param metadata
     * @return String
     */
    public String getFileType(Metadata metadata) {
        String extension = null;
        String fileType = FileType.getContentType(metadata);
        extension = this.getFileExtension(fileType);
        return extension;

    }

    /**
     *
     * @param contentType
     * @return String fileExtension
     */
    private String getFileExtension(String contentType) {
        String extension = "unknown";

        switch (contentType) {
            case "application/pdf":
                extension = ".pdf";
                break;
            case "image/png":
                extension = ".png";
                break;
            case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
                extension = ".docx";
                break;
            case "application/vnd.openxmlformats-officedocument.presentationml.presentation":
                extension = ".pptx";
                break;
            case "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet":
                extension = ".xlsx";
                break;
                case "application/vnd.ms-excel.sheet.macroenabled.12":
                extension = ".xlsx";
                break;
            case "image/jpeg":
                extension = ".jpg";
                break;
            case "application/vnd.ms-excel":
                extension = ".xls";
                break;
            case "application/vnd.ms-powerpoint":
                extension = ".ppt";
                break;
            case "application/zip":
                extension = ".zip";
                break;
            case "image/tiff":
                extension = ".tif";
                break;
            case "application/msword":
                extension = ".doc";
                break;
            case "text/html":
                extension = ".html";
                break;
            case "text/plain":
                extension = ".txt";
                break;
            case "video/x-msvideo":
                extension = ".avi";
                break;
            case "video/mp4":
                extension = ".mp4";
                break;
            default:
                throw new IllegalArgumentException("Invalid, file extension not supported: " + extension);
        }

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
