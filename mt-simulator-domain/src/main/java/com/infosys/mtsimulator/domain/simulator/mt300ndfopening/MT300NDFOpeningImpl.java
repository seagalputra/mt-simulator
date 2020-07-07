package com.infosys.mtsimulator.domain.simulator.mt300ndfopening;

import com.infosys.mtsimulator.domain.simulator.SimulatorStrategy;
import com.infosys.mtsimulator.domain.simulator.basesimulator.BaseSimulator;
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
    public String parse(String message, String type) {
        String messageResult = baseSimulator.replaceApplicationHeader(message);
        messageResult = baseSimulator.addPrefixCPTY(":20:", messageResult);
        messageResult = baseSimulator.removeUnusedField(":58A:", messageResult);
        messageResult = baseSimulator.swapValue(":82A:", ":87A:", messageResult);
        messageResult = baseSimulator.swapField(":57J:", ":57A:", messageResult);
        messageResult = baseSimulator.swapValue(":32B:", ":33B:", messageResult);

        if (isPartialMatch(type) || isUnMatchType(type))
            messageResult = baseSimulator.replaceValue(":30T:", messageResult, ":30T:20192512");

        if (isUnMatchType(type)) {
            messageResult = baseSimulator.replaceValue(":30V:", messageResult, ":30V:20200101");
            messageResult = baseSimulator.replaceValue(":32E:", messageResult, ":32E:MYR");
        }

        return messageResult;
    }
}
