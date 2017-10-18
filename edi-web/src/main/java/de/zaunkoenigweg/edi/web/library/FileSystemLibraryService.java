package de.zaunkoenigweg.edi.web.library;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


@Service
public class FileSystemLibraryService implements LibraryService {

	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
    private final Path rootLocation;

    @Autowired
    public FileSystemLibraryService(LibraryProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public Stream<Path> all() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(path -> this.rootLocation.relativize(path))
                    .sorted();
        }
        catch (IOException e) {
            throw new LibraryException("Failed to read stored files", e);
        }

    }
    
    @Override
    public void store(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                throw new LibraryException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new LibraryException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            Files.copy(file.getInputStream(), this.rootLocation.resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e) {
            throw new LibraryException("Failed to store file " + filename, e);
        }
    }

	@Override
	public void init() {
		try {
            Files.createDirectories(rootLocation);
            LOG.info("Library configured to use path '{}'.", this.rootLocation.toAbsolutePath());
        }
        catch (IOException e) {
            throw new LibraryException("Could not initialize library location", e);
        }
		
	}
}
