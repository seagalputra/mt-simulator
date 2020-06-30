package com.infosys.mtsimulator.simulator;

import javassist.NotFoundException;
import org.springframework.stereotype.Service;

public interface SimulatorStrategy {
    String parse(String message, String type) throws NotFoundException;
}
