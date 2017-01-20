package de.zaunkoenigweg.edi.core.config;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.BeanInitializationException;

public class EdiConfigTest {

	private EdiConfig sut;
    private Path existingTextFile;
    private Path existingMediaFolder;
    private Path nonExistingMediaFolder;

    @Before
    public void setUp() throws IOException {
        existingTextFile = Files.createTempFile("", ".txt");
        existingTextFile.toFile().deleteOnExit();
        existingMediaFolder = Files.createTempDirectory("ediMediaFolder");
        existingMediaFolder.toFile().deleteOnExit();
        nonExistingMediaFolder = Paths.get("thisfolderdoesnotexistA");
    }

    @Test(expected=BeanInitializationException.class)
    public void testConfigNoPropertiesSet() {
        sut = new EdiConfig();
        sut.init();
    }

    @Test(expected=BeanInitializationException.class)
    public void testNoMediaFolderSet() {
        sut = new EdiConfig();
        sut.init();
    }
    
    @Test(expected=BeanInitializationException.class)
    public void testMediaFolderNotExisting() {
        sut = new EdiConfig();
        sut.setMediaFolder(nonExistingMediaFolder);
        sut.init();
    }
    
    @Test(expected=BeanInitializationException.class)
    public void testTextFileSetAsMediaFolder() {
        sut = new EdiConfig();
        sut.setMediaFolder(existingTextFile);
        sut.init();
    }
    
    @Test
    public void testAllSet() {
        sut = new EdiConfig();
        sut.setMediaFolder(existingMediaFolder);
        sut.init();
        assertNotNull(sut);
    }
    
}
