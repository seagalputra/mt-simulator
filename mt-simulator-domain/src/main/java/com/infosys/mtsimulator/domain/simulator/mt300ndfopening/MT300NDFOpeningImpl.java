package com.infosys.mtsimulator.domain.simulator.mt300ndfopening;

import com.infosys.mtsimulator.domain.simulator.SimulatorStrategy;
import com.infosys.mtsimulator.domain.simulator.basesimulator.BaseSimulator;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import static com.infosys.mtsimulator.domain.constant.MTSimulatorConstant.*;

@Component
@AllArgsConstructor
public class MT300NDFOpeningImpl implements SimulatorStrategy {
    private final BaseSimulator baseSimulator;

    @Override
    public boolean isSupport(String simulatorType) {
        return MT300_NDF_OPENING.equals(simulatorType);
    }

    @Override
    public String parse(String message, String type) throws NotFoundException {
        return null;
    }
}
