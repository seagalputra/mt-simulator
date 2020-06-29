package com.infosys.mtsimulator.simulator.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParseMessageRequest {
    private String simulatorType;
    private String simulatorMessage;
}
