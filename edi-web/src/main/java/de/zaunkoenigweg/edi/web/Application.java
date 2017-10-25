package de.zaunkoenigweg.edi.web;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import de.zaunkoenigweg.edi.web.library.LibraryProperties;
import de.zaunkoenigweg.edi.web.library.LibraryService;
import de.zaunkoenigweg.rspio.audio.AudioPlayer;
import de.zaunkoenigweg.rspio.audio.dummy.DummyAudioPlayer;
import de.zaunkoenigweg.rspio.audio.omx.OmxAudioPlayer;

@SpringBootApplication
@EnableConfigurationProperties({LibraryProperties.class, AudioProperties.class})
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
    
    @Bean
    AudioPlayer audioPlayer(AudioProperties audioProperties) {
    	if(DummyAudioPlayer.class.getCanonicalName().equals(audioProperties.getAudioPlayerClassName())) {
    		return new DummyAudioPlayer();
    	}
    	if(OmxAudioPlayer.class.getCanonicalName().equals(audioProperties.getAudioPlayerClassName())) {
    		return new OmxAudioPlayer();
    	}
    	throw new RuntimeException("undefined audio player");
    }
}
