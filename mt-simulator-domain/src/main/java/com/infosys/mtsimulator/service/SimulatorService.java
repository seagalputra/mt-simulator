package com.infosys.mtsimulator.service;

import com.infosys.mtsimulator.api.request.ParseMessageRequest;
import com.infosys.mtsimulator.api.response.ParseMessageResponse;

public interface SimulatorService {
    ParseMessageResponse parseMessage(ParseMessageRequest request);
}
