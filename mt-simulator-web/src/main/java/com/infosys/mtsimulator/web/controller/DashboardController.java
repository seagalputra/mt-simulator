package com.infosys.mtsimulator.web.controller;

import com.infosys.mtsimulator.domain.service.SimulatorService;
import com.infosys.mtsimulator.api.request.ParseMessageRequest;
import com.infosys.mtsimulator.api.response.ParseMessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
public class DashboardController {

    private final SimulatorService simulatorService;
    private ParseMessageResponse parseMessageResponse;

    public DashboardController(SimulatorService simulatorService) {
        this.simulatorService = simulatorService;
    }

    @GetMapping
    public ModelAndView messageForm() {
        ParseMessageRequest parseMessageRequest = ParseMessageRequest.builder().build();
        ParseMessageResponse parseMessageResponse = ParseMessageResponse.builder().build();
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("simulator", parseMessageRequest);
        modelAndView.addObject("simulatorResult", parseMessageResponse);
        return modelAndView;
    }

    @PostMapping
    public String simulator(@ModelAttribute ParseMessageRequest parseMessageRequest) {
        this.parseMessageResponse = simulatorService.parseMessage(parseMessageRequest);
        return "redirect:/simulator/result";
    }

    @GetMapping("/simulator/result")
    public ModelAndView getSimulatorResult() {
        return new ModelAndView("index", "simulatorResult", this.parseMessageResponse);
    }
}
