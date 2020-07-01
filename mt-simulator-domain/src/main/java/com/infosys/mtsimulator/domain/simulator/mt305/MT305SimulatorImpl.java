package com.infosys.mtsimulator.domain.simulator.mt305;

import com.google.common.collect.Iterables;
import com.infosys.mtsimulator.domain.simulator.SimulatorStrategy;
import com.infosys.mtsimulator.domain.simulator.basesimulator.BaseSimulator;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.infosys.mtsimulator.domain.constant.MTSimulatorConstant.*;

@Component
@AllArgsConstructor
public class MT305SimulatorImpl implements SimulatorStrategy {

    private final BaseSimulator baseSimulator;

    @Override
    public boolean isSupport(String simulatorType) {
        return MT305.equals(simulatorType);
    }

    @Override
    public String parse(String message, String type) throws NotFoundException {
        String messageResult = baseSimulator.replaceApplicationHeader(message);
        messageResult = baseSimulator.addPrefixCPTY(":20:", messageResult);
        messageResult = baseSimulator.swapValue(":82A:", ":87A:", messageResult);

        if (isContains(messageResult, ":23:BUY")) {
            messageResult = messageResult.replace(":23:BUY", ":23:SELL");
            messageResult = messageResult.replace(":34P:", ":34R:");
            messageResult = baseSimulator.removeUnusedField(":22U:", messageResult);
            messageResult = removeSenderCorrespondent(messageResult);
        } else if (isContains(messageResult, ":23:SELL")) {
            messageResult = messageResult.replace(":23:SELL", ":23:BUY");
            messageResult = messageResult.replace(":34R:", ":34P:");
        }

        if (isPartialMatch(type) || isUnMatchType(type)) {
            messageResult = baseSimulator.replaceValue(":30:", messageResult, ":30:20191225");
        }

        if (isUnMatchType(type))
            messageResult = baseSimulator.replaceValue(":31E:", messageResult, ":31E:20200101");

        return messageResult;
    }

    private boolean isContains(String message, String key) {
        return message.contains(key);
    }

    private String removeSenderCorrespondent(String message) {
        List<String> listMessage = Arrays.asList(message.split("\n"));
        int senderIndex = Iterables.indexOf(listMessage, element -> isContains(element, ":53A:"));
        List<String> removedMessage = new ArrayList<>();

        for (int index = 0; index < listMessage.size(); index++) {
            if (isNotSenderCorrespondentIndex(senderIndex, index)) {
                removedMessage.add(listMessage.get(index));
            }
        }

        return String.join("\n", removedMessage);
    }

    private boolean isNotSenderCorrespondentIndex(int senderIndex, int currentIndex) {
        return currentIndex != senderIndex && currentIndex != senderIndex + 1;
    }
}
