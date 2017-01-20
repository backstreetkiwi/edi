package de.zaunkoenigweg.edi.server;

import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import de.zaunkoenigweg.edi.core.config.EdiConfig;

@Configuration
@PropertySource("${edi.config}")
public class SpringContext {

    @Autowired
    Environment environment;
    
    @Bean
    public EdiConfig config() {
        EdiConfig config = new EdiConfig();
        config.setMediaFolder(Paths.get(environment.getProperty("media.folder")));
        return config;
    }
    
    @Bean
    public EdiServer server() {
        return new EdiServer();
    }
}
