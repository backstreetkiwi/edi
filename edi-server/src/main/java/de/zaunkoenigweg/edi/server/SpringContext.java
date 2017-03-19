package de.zaunkoenigweg.edi.server;

import java.nio.file.Paths;
import java.time.Duration;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.RaspiPin;

import de.zaunkoenigweg.edi.core.config.EdiConfig;
import de.zaunkoenigweg.edi.core.media.PlayAudioOnButtonAction;
import de.zaunkoenigweg.rspio.core.component.PushButton;
import de.zaunkoenigweg.rspio.core.component.ReleaseButton;
import de.zaunkoenigweg.rspio.core.input.InputController;
import de.zaunkoenigweg.rspio.core.omx.AudioPlayer;
import de.zaunkoenigweg.rspio.core.omx.MediaLibrary;

@Configuration
@PropertySource("${edi.config}")
public class SpringContext {

    @Autowired
    Environment environment;
    
    @Bean
    public EdiConfig config() {
        EdiConfig config = new EdiConfig();
        config.setMediaFolder(Paths.get(environment.getProperty("media.folder")));
        config.setShutdownButtonPin(RaspiPin.getPinByAddress(environment.getProperty("gpio.shutdown-button.pin.number", Integer.class)));
        config.setPowerLedPin(RaspiPin.getPinByAddress(environment.getProperty("gpio.power-led.pin.number", Integer.class)));
        config.setPlaybackLedPin(RaspiPin.getPinByAddress(environment.getProperty("gpio.playback-led.pin.number", Integer.class)));
        config.setActionButtonAPin(RaspiPin.getPinByAddress(environment.getProperty("gpio.action-button-a.pin.number", Integer.class)));
        return config;
    }
    
    @Bean
    public InputController inputController() {
        return new InputController();
    }

    @Bean
    public Supplier<GpioController> gpioControllerSupplier() {
        return GpioFactory::getInstance;
    }

    @Bean
    public ReleaseButton shutdownButton(EdiConfig config, EdiServer server) {
        ReleaseButton shutdownButton = new ReleaseButton("Shutdown Button", config.getShutdownButtonPin(), Duration.ofSeconds(3));
        shutdownButton.setAction(blockable -> server.shutdown());
        return shutdownButton;
    }

    @Bean
    public MediaLibrary mediaLibrary(EdiConfig config) {
        MediaLibrary mediaLibrary = new MediaLibrary();
        mediaLibrary.setRootFolder(config.getMediaFolder());
        return mediaLibrary;
    }

    @Bean
    public PlayAudioOnButtonAction playAudioOnButtonAction() {
        return new PlayAudioOnButtonAction();
    }

    @Bean
    public PushButton actionButtonA(EdiConfig config, PlayAudioOnButtonAction action) {
        PushButton actionButtonA = new PushButton("ActionButton A", config.getActionButtonAPin());
        actionButtonA.setAction(blockable -> {
            action.action();
        });
        return actionButtonA;
    }

    @Bean
    public AudioPlayer audioPlayer(EdiConfig config) {
        AudioPlayer audioPlayer = new AudioPlayer();
        audioPlayer.setPlaybackLedPin(config.getPlaybackLedPin());
        return audioPlayer;
    }

    
    @Bean
    public EdiServer server() {
        return new EdiServer();
    }
}

