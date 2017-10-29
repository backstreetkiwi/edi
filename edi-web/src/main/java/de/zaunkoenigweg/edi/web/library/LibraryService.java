package de.zaunkoenigweg.edi.web.library;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.web.multipart.MultipartFile;

public interface LibraryService {

	void init();

	void store(MultipartFile file);

	Stream<Path> all();

	Path load(String filename);
	//
	// Resource loadAsResource(String filename);
	//
	// void deleteAll();

}
