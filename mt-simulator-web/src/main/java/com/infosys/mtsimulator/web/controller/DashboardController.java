package com.infosys.mtsimulator.web.controller;

import com.infosys.mtsimulator.simulator.request.ParseMessageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
@Slf4j
public class DashboardController {
    @GetMapping
    public ModelAndView messageForm() {
        ParseMessageRequest simulator = new ParseMessageRequest();
        return new ModelAndView("index", "simulator", simulator);
    }

    @PostMapping
    public String simulator(@ModelAttribute ParseMessageRequest parseMessageRequest) {
        log.info("Input Type : " + parseMessageRequest.getSimulatorType());
        log.info("Message : " + parseMessageRequest.getSimulatorMessage());
        return "redirect:/";
    }
}
