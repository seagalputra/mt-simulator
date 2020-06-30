package com.infosys.mtsimulator.web.controller;

import com.infosys.mtsimulator.service.SimulatorService;
import com.infosys.mtsimulator.api.request.ParseMessageRequest;
import com.infosys.mtsimulator.api.response.ParseMessageResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
@AllArgsConstructor
@Slf4j
public class DashboardController {

    private final SimulatorService simulatorService;

    @GetMapping
    public ModelAndView messageForm() {
        ParseMessageRequest simulator = ParseMessageRequest.builder().build();
        return new ModelAndView("index", "simulator", simulator);
    }

    @PostMapping
    public String simulator(@ModelAttribute ParseMessageRequest parseMessageRequest) {
        ParseMessageResponse messageResponse = simulatorService.parseMessage(parseMessageRequest);
        log.info("Input Type : " + parseMessageRequest.getSimulatorType());
        log.info("Message : " + parseMessageRequest.getSimulatorMessage());
        log.info("Auto Match Message : " + messageResponse.getAutoMatchMessage());
        return "redirect:/";
    }
}
