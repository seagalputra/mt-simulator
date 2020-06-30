package com.infosys.mtsimulator.domain.simulator.mt300ndfopening;

import com.infosys.mtsimulator.domain.simulator.SimulatorStrategy;
import com.infosys.mtsimulator.domain.simulator.basesimulator.BaseSimulator;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MT300NDFOpeningImpl implements SimulatorStrategy {
    private final BaseSimulator baseSimulator;

    @Override
    public boolean isSupport(String simulatorType) {
        return false;
    }

    @Override
    public String parse(String message, String type) throws NotFoundException {
        return null;
    }
}
