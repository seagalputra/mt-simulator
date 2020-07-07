package com.infosys.mtsimulator.domain.simulator;

public interface SimulatorStrategy {

    boolean isSupport(String simulatorType);
    String parse(String message, String type);
}
