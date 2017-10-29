package de.zaunkoenigweg.edi.web.player;

import java.nio.file.Path;
import java.time.Duration;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import de.zaunkoenigweg.edi.web.library.LibraryService;
import de.zaunkoenigweg.rspio.audio.AudioPlayer;
import de.zaunkoenigweg.rspio.audio.AudioTrack;

@Controller
public class PlayerController {

    private final LibraryService libraryService;
	private AudioPlayer audioPlayer;
	private AudioTrack audioTrack;

    @Autowired
    public PlayerController(LibraryService libraryService, AudioPlayer audioPlayer) {
        this.libraryService = libraryService;
		this.audioPlayer = audioPlayer;
    }

    @GetMapping("/player/launch/{filename:.+}")
    public String play(@PathVariable String filename) {
    	this.audioPlayer.stopAll();
    	String decodedFilename = new String(Base64.getDecoder().decode(filename));
    	audioTrack = audioPlayer.track(libraryService.load(decodedFilename));
    	return "redirect:/player";
    }
    
    @GetMapping("/player")
    public String player(Model model) {
        model.addAttribute("track", audioTrack);
        return "player";
    }
    
    @GetMapping("/player/play")
    public String play() {
    	audioTrack.play();
    	return "redirect:/player";
    }
    
    @GetMapping("/player/play30")
    public String play30() {
    	audioTrack.play(Duration.ofSeconds(30));
    	return "redirect:/player";
    }
    
    @GetMapping("/player/pause")
    public String pause() {
    	audioTrack.pause();
    	return "redirect:/player";
    }
    
    public static String linkToPlayer(Path audioFile) {
    	String encodedAudioFileName = Base64.getUrlEncoder().encodeToString(audioFile.getFileName().toString().getBytes());
    	return MvcUriComponentsBuilder.fromMethodName(PlayerController.class, "play", encodedAudioFileName).build().toString();
    }

}
