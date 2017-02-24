package de.zaunkoenigweg.edi.core.config;

import java.nio.file.Files;
import java.nio.file.Path;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanInitializationException;

import com.pi4j.io.gpio.Pin;

public class EdiConfig {

    private final static Log LOG = LogFactory.getLog(EdiConfig.class);

    private Path mediaFolder;
    private Pin powerLedPin;
    private Pin shutdownButtonPin;
    private Pin actionButtonAPin;

    /**
     * Creates EDI configuration using properties.
     * 
     * @throws BeanInitializationException if EDI configuration cannot be read. 
     */
    @PostConstruct
    public void init() {

        if(mediaFolder==null) {
            String msg = "media folder property is not set.";
            LOG.error(msg);
            throw new BeanInitializationException(msg);
        }
        
        if(mediaFolder==null || !Files.exists(mediaFolder) || !Files.isDirectory(mediaFolder)) {
            String msg = String.format("media folder '%s' does not exist or is not a directory.", mediaFolder);
            LOG.error(msg);
            throw new BeanInitializationException(msg);
        }
        
        if(shutdownButtonPin==null) {
            String msg = "GPIO pin for shutdown button is not set.";
            LOG.error(msg);
            throw new BeanInitializationException(msg);
        }
        
        if(actionButtonAPin==null) {
            String msg = "GPIO pin for action button A is not set.";
            LOG.error(msg);
            throw new BeanInitializationException(msg);
        }
        
        if(powerLedPin==null) {
            String msg = "GPIO pin for power LED is not set.";
            LOG.error(msg);
            throw new BeanInitializationException(msg);
        }
        
        if(shutdownButtonPin.equals(powerLedPin)) {
            String msg = "GPIO pin for power LED is the same as GPIO pin for shutdown button.";
            LOG.error(msg);
            throw new BeanInitializationException(msg);
        }
        
        if(shutdownButtonPin.equals(actionButtonAPin)) {
            String msg = "GPIO pin for action button A is the same as GPIO pin for shutdown button.";
            LOG.error(msg);
            throw new BeanInitializationException(msg);
        }
        
        if(powerLedPin.equals(actionButtonAPin)) {
            String msg = "GPIO pin for power LED is the same as GPIO pin for action button A.";
            LOG.error(msg);
            throw new BeanInitializationException(msg);
        }
        
        LOG.info(String.format("EDI configuration: media-folder='%s', shutdown button pin: %s, action button A pin: %s, power led pin: %s", mediaFolder, shutdownButtonPin, actionButtonAPin, powerLedPin));
    }

    public Path getMediaFolder() {
        return mediaFolder;
    }

    public void setMediaFolder(Path mediaFolder) {
        this.mediaFolder = mediaFolder;
    }

    public Pin getPowerLedPin() {
        return powerLedPin;
    }

    public void setPowerLedPin(Pin powerLedPin) {
        this.powerLedPin = powerLedPin;
    }

    public Pin getShutdownButtonPin() {
        return shutdownButtonPin;
    }

    public void setShutdownButtonPin(Pin shutdownButtonPin) {
        this.shutdownButtonPin = shutdownButtonPin;
    }

    public Pin getActionButtonAPin() {
        return actionButtonAPin;
    }

    public void setActionButtonAPin(Pin actionButtonAPin) {
        this.actionButtonAPin = actionButtonAPin;
    }
}
