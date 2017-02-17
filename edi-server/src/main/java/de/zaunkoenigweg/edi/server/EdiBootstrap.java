package de.zaunkoenigweg.edi.server;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class EdiBootstrap {
    
    private static final String SYSTEM_PROPERTY_LOG_DIR = "edi.log.dir";
    
    public static void main(String[] args) {
        System.out.println("EDI: bootstrap started...");
        if(StringUtils.isBlank(System.getProperty(SYSTEM_PROPERTY_LOG_DIR))) {
            System.out.printf("EDI: system property '%s' is not set.%n", SYSTEM_PROPERTY_LOG_DIR);
            System.exit(1);
        }
        Path logDir = Paths.get(System.getProperty(SYSTEM_PROPERTY_LOG_DIR));
        if(!Files.exists(logDir) || !Files.isDirectory(logDir)) {
            System.out.printf("EDI: directory '%s' does not exist.%n", SYSTEM_PROPERTY_LOG_DIR);
            System.exit(1);
        }
        AbstractApplicationContext springContext = new AnnotationConfigApplicationContext(SpringContext.class);
        EdiServer server = springContext.getBean(EdiServer.class);
        server.run();
        springContext.close();
        System.out.println("EDI: bootstrap terminated.");
    }
}
