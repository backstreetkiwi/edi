package de.zaunkoenigweg.edi.server;

import java.nio.file.Paths;
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
import de.zaunkoenigweg.rspio.core.component.Action;
import de.zaunkoenigweg.rspio.core.component.Blockable;
import de.zaunkoenigweg.rspio.core.component.PushButton;
import de.zaunkoenigweg.rspio.core.input.InputController;

@Configuration
@PropertySource("${edi.config}")
public class SpringContext {

    @Autowired
    Environment environment;
    
    @Bean
    public EdiConfig config() {
        EdiConfig config = new EdiConfig();
        config.setMediaFolder(Paths.get(environment.getProperty("media.folder")));
        config.setShutdownButtonPin(RaspiPin.getPinByAddress(environment.getProperty("gpio.shutdown.button.pin.number", Integer.class)));
        config.setPowerLedPin(RaspiPin.getPinByAddress(environment.getProperty("gpio.power.led.pin.number", Integer.class)));
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
    public PushButton shutdownButton(EdiConfig config, EdiServer server) {
        PushButton shutdownButton = new PushButton("Shutdown Button", config.getShutdownButtonPin());
        shutdownButton.setAction(new Action() {
            @Override
            public void run(Blockable blockable) {
                server.shutdown();
            }
        });
        return shutdownButton;
    }

    @Bean
    public EdiServer server() {
        return new EdiServer();
    }
}

