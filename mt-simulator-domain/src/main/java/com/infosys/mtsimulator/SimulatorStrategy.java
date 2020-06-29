package com.infosys.mtsimulator;

import javassist.NotFoundException;

public interface SimulatorStrategy {
    String parse(String message, String type) throws NotFoundException;
}
