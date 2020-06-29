package com.infosys.mtsimulator.mt300nonndf;

import com.infosys.mtsimulator.basesimulator.BaseSimulator;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MT300NonNDFSimulatorImpl implements MT300NonNDFSimulator {
    private final BaseSimulator baseSimulator;

    @Override
    public String parse(String message, String type) throws NotFoundException {
        String messageResult = message;
        messageResult = baseSimulator.replaceApplicationHeader(messageResult);
        messageResult = baseSimulator.addPrefixCPTY(":20:", messageResult);
        messageResult = baseSimulator.swapValue(":82A:", ":87A:", messageResult);
        messageResult = baseSimulator.swapValue(":32B:", ":33B:", messageResult);
        messageResult = baseSimulator.swapField(":57A:", messageResult);
        messageResult = baseSimulator.removeUnusedField(":58A:", messageResult);
        return messageResult;
    }
}
