package com.infosys.mtsimulator.mt300nonndf;

import com.infosys.mtsimulator.SimulatorStrategy;
import com.infosys.mtsimulator.basesimulator.BaseSimulator;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.infosys.mtsimulator.constant.MTSimulatorConstant.*;

@Service
@AllArgsConstructor
public class MT300NonNDFSimulatorImpl implements SimulatorStrategy {
    private final BaseSimulator baseSimulator;

    @Override
    public String parse(String message, String type) throws NotFoundException {
        String messageResult = baseSimulator.replaceApplicationHeader(message);
        messageResult = baseSimulator.addPrefixCPTY(":20:", messageResult);
        messageResult = baseSimulator.swapValue(":82A:", ":87A:", messageResult);

        if (isUnMatchType(type)) {
            messageResult = baseSimulator.replaceValue(":30T:", messageResult, ":30T:20191225");
            messageResult = baseSimulator.replaceValue(":30V:", messageResult, ":30V:20200101");
        }

        if (isAutoMatchType(type) || isUnMatchType(type)) {
            messageResult = baseSimulator.swapField(":57A:", messageResult);
            messageResult = baseSimulator.swapValue(":32B:", ":33B:", messageResult);
        } else if (isPartialMatch(type)) {
            messageResult = baseSimulator.swapValue(":32B:", ":33B:", messageResult);
        }

        messageResult = baseSimulator.removeUnusedField(":58A:", messageResult);
        return messageResult;
    }
}
