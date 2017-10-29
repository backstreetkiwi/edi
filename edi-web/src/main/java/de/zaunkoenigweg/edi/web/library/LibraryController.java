package de.zaunkoenigweg.edi.web.library;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import de.zaunkoenigweg.edi.web.player.PlayerController;
import de.zaunkoenigweg.rspio.audio.AudioPlayer;

@Controller
public class LibraryController {

    private final LibraryService libraryService;

    @Autowired
    public LibraryController(LibraryService libraryService, AudioPlayer audioPlayer) {
        this.libraryService = libraryService;
    }

    @GetMapping("/library")
    public String showLibrary(Model model) throws IOException {
        List<Pair<String, String>> files = libraryService
        		.all()
        		.map(path -> Pair.of(path.getFileName().toString(), PlayerController.linkToPlayer(path)))
                .collect(Collectors.toList());
		model.addAttribute("files", files);
        return "library";
    }

    @PostMapping("/library")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) {

        libraryService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/library";
    }

//    @ExceptionHandler(StorageFileNotFoundException.class)
//    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
//        return ResponseEntity.notFound().build();
//    }

}
