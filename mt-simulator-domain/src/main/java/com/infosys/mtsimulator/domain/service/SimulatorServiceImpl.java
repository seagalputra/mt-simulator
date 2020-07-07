package com.infosys.mtsimulator.domain.service;

import com.infosys.mtsimulator.api.request.ParseMessageRequest;
import com.infosys.mtsimulator.api.response.ParseMessageResponse;
import com.infosys.mtsimulator.domain.simulator.SimulatorStrategy;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import static com.infosys.mtsimulator.domain.constant.MTSimulatorConstant.*;

@Service
@Slf4j
public class SimulatorServiceImpl implements SimulatorService, ApplicationContextAware {

    @Setter
    private ApplicationContext applicationContext;

    @Override
    public ParseMessageResponse parseMessage(ParseMessageRequest request) {
        SimulatorStrategy simulator = applicationContext.getBeansOfType(SimulatorStrategy.class).values()
                .stream()
                .filter(simulatorStrategy -> simulatorStrategy.isSupport(request.getSimulatorType()))
                .findFirst()
                .orElseThrow(() -> new NullPointerException("No Simulator Supported!"));

        return parseSimulatorMessage(request, simulator);
    }

    private ParseMessageResponse parseSimulatorMessage(ParseMessageRequest request, SimulatorStrategy simulator) {
        String autoMatchMessage = simulator.parse(request.getSimulatorMessage(), AM);
        String partialMatchMessage = simulator.parse(request.getSimulatorMessage(), PM);
        String unMatchMessage = simulator.parse(request.getSimulatorMessage(), UM);

        return ParseMessageResponse.builder()
                .autoMatchMessage(autoMatchMessage)
                .partialMatchMessage(partialMatchMessage)
                .unMatchMessage(unMatchMessage)
                .build();
    }
}
