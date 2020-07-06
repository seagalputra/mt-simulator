package com.infosys.mtsimulator.domain.simulator.mt320lend;

import com.infosys.mtsimulator.domain.simulator.SimulatorStrategy;
import com.infosys.mtsimulator.domain.simulator.basesimulator.BaseSimulator;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import static com.infosys.mtsimulator.domain.constant.MTSimulatorConstant.*;

@Component
@AllArgsConstructor
public class MT320LendSimulatorImpl implements SimulatorStrategy {

    private final BaseSimulator baseSimulator;

    @Override
    public boolean isSupport(String simulatorType) {
        return MT320LEND.equals(simulatorType);
    }

    @Override
    public String parse(String message, String type) {
        String messageResult = baseSimulator.replaceApplicationHeader(message);
        messageResult = baseSimulator.addPrefixCPTY(":20:", messageResult);
        messageResult = baseSimulator.swapValue(":82A:", ":87A:", messageResult);
        messageResult = baseSimulator.replaceValue(":17R:", messageResult, ":17R:B");
        messageResult = messageResult.replace(":34E:N", ":34E:");
        messageResult = baseSimulator.swapField(":57A:", messageResult);
        messageResult = baseSimulator.removeField(":58A:", messageResult);

        if (isPartialMatch(type) || isUnMatchType(type)) {
            messageResult = baseSimulator.replaceValue(":30T:", messageResult, ":30T:20191225");
        }

        if (isUnMatchType(type)) {
            messageResult = baseSimulator.replaceValue(":30V:", messageResult, ":30V:20191225");
            messageResult = baseSimulator.replaceValue(":30P:", messageResult, ":30P:20200101");
        }
        return messageResult;
    }
}
