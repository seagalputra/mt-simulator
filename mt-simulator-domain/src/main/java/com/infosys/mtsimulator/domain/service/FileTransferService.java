package com.infosys.mtsimulator.domain.service;

import com.infosys.mtsimulator.api.request.SendFTPRequest;

public interface FileTransferService {
    void sendFile(SendFTPRequest sendFTPRequest, String type);
}
