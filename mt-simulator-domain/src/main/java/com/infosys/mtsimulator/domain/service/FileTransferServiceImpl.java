package com.infosys.mtsimulator.domain.service;

import com.infosys.mtsimulator.api.request.SendFTPRequest;
import com.infosys.mtsimulator.properties.ConfigProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.infosys.mtsimulator.domain.constant.MTSimulatorConstant.*;

@Service
@AllArgsConstructor
@Slf4j
public class FileTransferServiceImpl implements FileTransferService {

    private final ConfigProperties configProperties;

    @Override
    public void sendFile(SendFTPRequest sendFTPRequest, String type) {
        Map<String, String> configuration = obtainEnvironment(sendFTPRequest.getConfigId());
        String message = filterMessage(sendFTPRequest, type);

        log.info(message);
        log.info(configuration.toString());
    }

    private String filterMessage(SendFTPRequest sendFTPRequest, String type) {
        String message = "";
        if (isAutoMatchType(type)) {
            message = sendFTPRequest.getAutoMatchMessage();
        } else if (isPartialMatch(type)) {
            message = sendFTPRequest.getPartialMatchMessage();
        } else if (isUnMatchType(type)) {
            message = sendFTPRequest.getUnMatchMessage();
        }
        return message;
    }

    private Map<String, String> obtainEnvironment(String configId) {
        List<Map<String, String>> environmentList = configProperties.getEnvironmentConfiguration();
        return environmentList.stream()
                .filter(environment -> environment.get("id").equals(configId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Configuration Not Found!"));
    }
}
