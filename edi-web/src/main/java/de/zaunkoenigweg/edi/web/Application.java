package de.zaunkoenigweg.edi.web;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import de.zaunkoenigweg.edi.web.library.LibraryProperties;
import de.zaunkoenigweg.edi.web.library.LibraryService;

@SpringBootApplication
@EnableConfigurationProperties(LibraryProperties.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner init(LibraryService libraryService) {
        return (args) -> {
            libraryService.init();
        };
    }
}
