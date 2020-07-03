package com.infosys.mtsimulator.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class SendFTPRequest {
    private String configId;
    private String autoMatchMessageType;
    private String partialMatchMessageType;
    private String unMatchMessageType;
    private String autoMatchMessage;
    private String partialMatchMessage;
    private String unMatchMessage;
}
