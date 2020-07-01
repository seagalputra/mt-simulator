package com.infosys.mtsimulator.domain.simulator.mt320borrow;

import com.infosys.mtsimulator.domain.simulator.SimulatorStrategy;
import com.infosys.mtsimulator.domain.simulator.basesimulator.BaseSimulator;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import static com.infosys.mtsimulator.domain.constant.MTSimulatorConstant.MT320BORROW;

@Component
@AllArgsConstructor
public class MT320BorrowSimulatorImpl implements SimulatorStrategy {

    private final BaseSimulator baseSimulator;

    @Override
    public boolean isSupport(String simulatorType) {
        return MT320BORROW.equals(simulatorType);
    }

    @Override
    public String parse(String message, String type) throws NotFoundException {
        String messageResult = baseSimulator.replaceApplicationHeader(message);
        messageResult = baseSimulator.addPrefixCPTY(":20:", messageResult);
        messageResult = baseSimulator.swapValue(":82A:", ":87A:", messageResult);
        messageResult = baseSimulator.replaceValue(":17R:", messageResult, ":17R:L");
        messageResult = baseSimulator.swapField(":57A:", messageResult);
        messageResult = messageResult.replace(":34E:", ":34E:N");
        messageResult = baseSimulator.removeField(":58A:", messageResult);
        return messageResult;
    }
}
