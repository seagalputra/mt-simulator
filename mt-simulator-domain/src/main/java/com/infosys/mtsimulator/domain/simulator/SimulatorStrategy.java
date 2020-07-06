package com.infosys.mtsimulator.domain.simulator;

import javassist.NotFoundException;

public interface SimulatorStrategy {

    boolean isSupport(String simulatorType);
    String parse(String message, String type);
}
