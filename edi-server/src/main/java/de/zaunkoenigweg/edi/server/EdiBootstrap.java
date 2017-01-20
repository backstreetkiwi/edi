package de.zaunkoenigweg.edi.server;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class EdiBootstrap {
    
    private final static Log LOG = LogFactory.getLog(EdiBootstrap.class);

    public static void main(String[] args) {
        LOG.info("EDI bootstrap started...");
        LOG.info("Spring context successfully initialized.");
        AbstractApplicationContext springContext = new AnnotationConfigApplicationContext(SpringContext.class);
        EdiServer server = springContext.getBean(EdiServer.class);
        server.run();
        springContext.close();
        LOG.info("EDI bootstrap terminated.");
    }
}
