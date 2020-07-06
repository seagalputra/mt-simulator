package com.infosys.mtsimulator.domain.service;

import com.infosys.mtsimulator.api.exception.FailedToSendException;
import com.infosys.mtsimulator.api.request.SendFTPRequest;
import com.infosys.mtsimulator.domain.simulator.basesimulator.BaseSimulator;
import com.infosys.mtsimulator.entity.MatchedString;
import com.infosys.mtsimulator.properties.ConfigProperties;
import com.infosys.mtsimulator.properties.FTPConfiguration;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.sftp.session.SftpSession;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static com.infosys.mtsimulator.domain.constant.MTSimulatorConstant.*;

@Service
@AllArgsConstructor
@Slf4j
public class FileTransferServiceImpl implements FileTransferService {

    private final ConfigProperties configProperties;
    private final FTPConfiguration ftpConfiguration;
    private final BaseSimulator baseSimulator;

    @Override
    @Async
    public void sendFile(SendFTPRequest sendFTPRequest, String type) {
        Map<String, String> configuration = obtainEnvironment(sendFTPRequest.getConfigId());
        String message = filterMessage(sendFTPRequest, type);

        SftpSession session = ftpConfiguration.getFTPConfigurationFactory(configuration)
                .getSession();

        String filename = getFilename(message);

        InputStream inputStream = new ByteArrayInputStream(message.getBytes());
        try {
            session.write(inputStream, configuration.get("path") + "/" + filename + ".txt");
        } catch (IOException e) {
            throw new FailedToSendException("Failed to send file to SFTP");
        }
    }

    private String getFilename(String message) {
        MatchedString matchedString = baseSimulator.matchPattern(message, ":20:");
        return matchedString.getValue();
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
