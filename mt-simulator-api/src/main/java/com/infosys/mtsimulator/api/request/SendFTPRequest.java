package com.infosys.mtsimulator.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class SendFTPRequest {
    private String configId;
    private String simulatorType;
    private String messageType;
    private String clientAddress;
    private String autoMatchMessage;
    private String partialMatchMessage;
    private String unMatchMessage;
}
