package com.infosys.mtsimulator.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ParseMessageResponse {
    private String autoMatchMessage;
    private String partialMatchMessage;
    private String unMatchMessage;
}
