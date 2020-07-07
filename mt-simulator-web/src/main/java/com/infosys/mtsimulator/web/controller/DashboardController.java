package com.infosys.mtsimulator.web.controller;

import com.infosys.mtsimulator.api.request.ParseMessageRequest;
import com.infosys.mtsimulator.api.request.SendFTPRequest;
import com.infosys.mtsimulator.api.response.ParseMessageResponse;
import com.infosys.mtsimulator.domain.service.FileTransferService;
import com.infosys.mtsimulator.domain.service.SimulatorService;
import com.infosys.mtsimulator.properties.ConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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
    private List<String> listLog;

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
        try {
            this.listLog = fileTransferService.getLogFile();
        } catch (IOException e) {
            log.info(e.getMessage());
        }

        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("simulator", request);
        modelAndView.addObject("simulatorResult", response);
        modelAndView.addObject("environmentConfiguration", this.environmentProperties);
        modelAndView.addObject("ftpRequest", sendFTPRequest);
        modelAndView.addObject("logFiles", this.listLog);
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
        modelAndView.addObject("logFiles", this.listLog);
        return modelAndView;
    }

    @PostMapping("/simulator/put")
    public String putParsedMessageToFtp(@ModelAttribute SendFTPRequest sendFTPRequest, @RequestParam(value="type", required = false) String type, HttpServletRequest request) {
        String remoteAddress = request.getHeader("X-FORWARDED-FOR");
        if (remoteAddress == null || "".equals(remoteAddress)) {
            remoteAddress = request.getRemoteAddr();
        }

        SendFTPRequest ftpRequest = SendFTPRequest.builder()
                .configId(sendFTPRequest.getConfigId())
                .simulatorType(this.parseMessageRequest.getSimulatorType())
                .messageType(type)
                .clientAddress(remoteAddress)
                .autoMatchMessage(sendFTPRequest.getAutoMatchMessage())
                .partialMatchMessage(sendFTPRequest.getPartialMatchMessage())
                .unMatchMessage(sendFTPRequest.getUnMatchMessage())
                .build();
        fileTransferService.sendFile(ftpRequest);
        return "redirect:/";
    }
}
