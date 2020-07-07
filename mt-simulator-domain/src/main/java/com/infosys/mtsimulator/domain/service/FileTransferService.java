package com.infosys.mtsimulator.domain.service;

import com.infosys.mtsimulator.api.request.SendFTPRequest;

import java.io.IOException;
import java.util.List;

public interface FileTransferService {
    void sendFile(SendFTPRequest sendFTPRequest);
    List<String> getLogFile() throws IOException;
}
