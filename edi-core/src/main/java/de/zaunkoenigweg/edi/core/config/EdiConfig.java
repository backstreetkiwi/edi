package de.zaunkoenigweg.edi.core.config;

import java.nio.file.Files;
import java.nio.file.Path;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanInitializationException;

public class EdiConfig {

    private final static Log LOG = LogFactory.getLog(EdiConfig.class);

    private Path mediaFolder;

    /**
     * Creates EDI configuration using properties.
     * 
     * @throws BeanInitializationException if EDI configuration cannot be read. 
     */
    @PostConstruct
    public void init() {

        if(mediaFolder==null) {
            String msg = "media folder property is not set.";
            LOG.error(msg);
            throw new BeanInitializationException(msg);
        }
        
        if(mediaFolder==null || !Files.exists(mediaFolder) || !Files.isDirectory(mediaFolder)) {
            String msg = String.format("media folder '%s' does not exist or is not a directory.", mediaFolder);
            LOG.error(msg);
            throw new BeanInitializationException(msg);
        }
        
        LOG.info(String.format("EDI configuration: media-folder='%s'", this.mediaFolder));
    }

    public Path getMediaFolder() {
        return mediaFolder;
    }

    public void setMediaFolder(Path mediaFolder) {
        this.mediaFolder = mediaFolder;
    }
}
