package com.infosys.mtsimulator.web.controller;

import com.infosys.mtsimulator.domain.service.SimulatorService;
import com.infosys.mtsimulator.api.request.ParseMessageRequest;
import com.infosys.mtsimulator.api.response.ParseMessageResponse;
import com.infosys.mtsimulator.properties.ConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class DashboardController {

    private final SimulatorService simulatorService;
    private final ConfigProperties configProperties;

    private ParseMessageResponse parseMessageResponse;
    private ParseMessageRequest parseMessageRequest;
    private List<Map<String, String>> environmentProperties;

    public DashboardController(SimulatorService simulatorService, ConfigProperties configProperties) {
        this.simulatorService = simulatorService;
        this.configProperties = configProperties;
    }

    @GetMapping
    public ModelAndView messageForm() {
        ParseMessageRequest request = ParseMessageRequest.builder().build();
        ParseMessageResponse response = ParseMessageResponse.builder().build();
        this.environmentProperties = configProperties.getEnvironmentConfiguration();

        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("simulator", request);
        modelAndView.addObject("simulatorResult", response);
        modelAndView.addObject("environmentConfiguration", this.environmentProperties);
        return modelAndView;
    }

    @PostMapping
    public String simulator(@ModelAttribute ParseMessageRequest request) {
        this.parseMessageResponse = simulatorService.parseMessage(request);
        this.parseMessageRequest = request;
        return "redirect:/simulator/result";
    }

    @GetMapping("/simulator/result")
    public ModelAndView getSimulatorResult() {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("simulator", this.parseMessageRequest);
        modelAndView.addObject("simulatorResult", this.parseMessageResponse);
        modelAndView.addObject("environmentConfiguration", this.environmentProperties);
        return modelAndView;
    }

    @PostMapping("/simulator/put")
    public String putParsedMessageToFtp() {
        return "redirect:/";
    }
}
