package com.infosys.mtsimulator;

import javassist.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface SimulatorStrategy {
    String parse(String message, String type) throws NotFoundException;
}
