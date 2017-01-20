package de.zaunkoenigweg.edi.server;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import de.zaunkoenigweg.edi.core.config.EdiConfig;

public class EdiServer {
    
    @Autowired
    private EdiConfig config;

    private final static Log LOG = LogFactory.getLog(EdiServer.class);

    public void run() {
        LOG.info("EDI server started...");
        LOG.info(String.format("EDI server using config: %s", config));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            LOG.error("EDI server was interrupted unexpectedly.", e);
        }
        LOG.info("EDI Server terminated.");
    }
    
    

}
