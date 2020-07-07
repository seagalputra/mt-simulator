package com.infosys.mtsimulator.domain.service;

import com.infosys.mtsimulator.api.exception.FailedToSendException;
import com.infosys.mtsimulator.api.request.SendFTPRequest;
import com.infosys.mtsimulator.domain.common.EnvironmentMapper;
import com.infosys.mtsimulator.domain.common.SendFTPLogger;
import com.infosys.mtsimulator.domain.simulator.basesimulator.BaseSimulator;
import com.infosys.mtsimulator.entity.MatchedString;
import com.infosys.mtsimulator.properties.FTPConfiguration;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.sftp.session.SftpSession;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static com.infosys.mtsimulator.domain.constant.MTSimulatorConstant.*;

@Service
@AllArgsConstructor
@Slf4j
public class FileTransferServiceImpl implements FileTransferService {

    private final FTPConfiguration ftpConfiguration;
    private final EnvironmentMapper environmentMapper;
    private final BaseSimulator baseSimulator;

    @Override
    @Async
    @SendFTPLogger
    public void sendFile(SendFTPRequest sendFTPRequest) {
        Map<String, String> configuration = environmentMapper.obtainEnvironment(sendFTPRequest.getConfigId());
        String message = filterMessage(sendFTPRequest);

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

    private String filterMessage(SendFTPRequest sendFTPRequest) {
        String message = "";
        if (isAutoMatchType(sendFTPRequest.getMessageType())) {
            message = sendFTPRequest.getAutoMatchMessage();
        } else if (isPartialMatch(sendFTPRequest.getMessageType())) {
            message = sendFTPRequest.getPartialMatchMessage();
        } else if (isUnMatchType(sendFTPRequest.getMessageType())) {
            message = sendFTPRequest.getUnMatchMessage();
        }
        return message;
    }
}
