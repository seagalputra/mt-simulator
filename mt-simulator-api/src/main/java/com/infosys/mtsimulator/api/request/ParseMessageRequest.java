package com.infosys.mtsimulator.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ParseMessageRequest {
    private String simulatorType;
    private String simulatorMessage;
}
