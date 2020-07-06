package com.infosys.mtsimulator.web.controller;

import com.infosys.mtsimulator.api.request.SendFTPRequest;
import com.infosys.mtsimulator.domain.service.FileTransferService;
import com.infosys.mtsimulator.domain.service.SimulatorService;
import com.infosys.mtsimulator.api.request.ParseMessageRequest;
import com.infosys.mtsimulator.api.response.ParseMessageResponse;
import com.infosys.mtsimulator.properties.ConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class DashboardController {

    private final SimulatorService simulatorService;
    private final FileTransferService fileTransferService;
    private final ConfigProperties configProperties;

    private ParseMessageResponse parseMessageResponse;
    private ParseMessageRequest parseMessageRequest;
    private List<Map<String, String>> environmentProperties;

    public DashboardController(SimulatorService simulatorService, FileTransferService fileTransferService, ConfigProperties configProperties) {
        this.simulatorService = simulatorService;
        this.fileTransferService = fileTransferService;
        this.configProperties = configProperties;
    }

    @GetMapping
    public ModelAndView messageForm() {
        ParseMessageRequest request = ParseMessageRequest.builder().build();
        ParseMessageResponse response = ParseMessageResponse.builder().build();
        SendFTPRequest sendFTPRequest = SendFTPRequest.builder().build();
        this.environmentProperties = configProperties.getEnvironmentConfiguration();

        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("simulator", request);
        modelAndView.addObject("simulatorResult", response);
        modelAndView.addObject("environmentConfiguration", this.environmentProperties);
        modelAndView.addObject("ftpRequest", sendFTPRequest);
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
        SendFTPRequest sendFTPRequest = SendFTPRequest.builder().build();
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("simulator", this.parseMessageRequest);
        modelAndView.addObject("simulatorResult", this.parseMessageResponse);
        modelAndView.addObject("environmentConfiguration", this.environmentProperties);
        modelAndView.addObject("ftpRequest", sendFTPRequest);
        return modelAndView;
    }

    @PostMapping("/simulator/put")
    public String putParsedMessageToFtp(@ModelAttribute SendFTPRequest sendFTPRequest, @RequestParam(value="type", required = false) String type) {
        fileTransferService.sendFile(sendFTPRequest, type);
        return "redirect:/";
    }
}
