package com.infosys.mtsimulator.simulator.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParseMessageResponse {
    private String autoMatchMessage;
    private String partialMatchMessage;
    private String unMatchMessage;
}
