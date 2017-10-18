package de.zaunkoenigweg.edi.web.library;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("library")
public class LibraryProperties {

    private String location = "ediLibrary/";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
