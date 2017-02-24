package de.zaunkoenigweg.edi.server;

import java.io.File;
import java.util.function.Supplier;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;

import de.zaunkoenigweg.edi.core.config.EdiConfig;
import de.zaunkoenigweg.rspio.core.input.InputController;
import de.zaunkoenigweg.rspio.core.omx.AudioTrack;

public class EdiServer {
    
    private final static Log LOG = LogFactory.getLog(EdiServer.class);
    
    @Autowired
    private EdiConfig config;

    // TODO use RSPIO instead of GpioController itself...
    @Autowired
    private Supplier<GpioController> gpioControllerSupplier;

    @Autowired
    private InputController inputController;

    private GpioPinDigitalOutput powerLed;
            
    private boolean shutdown = false;
    
    public void run() {
        LOG.info("EDI server started...");
        LOG.info(String.format("EDI server using config: %s", config));
        LOG.info("Starting GPIO...");
        powerLed = this.gpioControllerSupplier.get().provisionDigitalOutputPin(config.getPowerLedPin(), "Power", PinState.HIGH);
        powerLed.setShutdownOptions(true, PinState.LOW);
        boolean inputControllerStarted = inputController.start();
        if(!inputControllerStarted) {
            LOG.error("EDI Server could not start GPIO.");
            return;
        }
        LOG.info("GPIO started...");
        try {
            while(!shutdown) {
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            LOG.error("EDI server was interrupted unexpectedly.", e);
        }
        LOG.info("Stopping GPIO...");
        this.inputController.stop();
        this.gpioControllerSupplier.get().shutdown();
        LOG.info("GPIO stopped...");
        LOG.info("EDI Server terminated.");
    }
    
    public void shutdown() {
        this.shutdown = true;
    }
    
    

}
