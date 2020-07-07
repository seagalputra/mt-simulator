package com.infosys.mtsimulator.domain.common;

import com.google.common.io.Files;
import com.infosys.mtsimulator.api.exception.FailedToSaveException;
import com.infosys.mtsimulator.api.request.SendFTPRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Aspect
@Component
@Slf4j
@AllArgsConstructor
public class SendFTPLoggerAspect {

    private final EnvironmentMapper environmentMapper;

    @Around("@annotation(SendFTPLogger)")
    public Object printAndWriteLog(ProceedingJoinPoint joinPoint) throws Throwable {
        SendFTPRequest sendFTPRequest = (SendFTPRequest) joinPoint.getArgs()[0];
        Map<String, String> configuration = environmentMapper.obtainEnvironment(sendFTPRequest.getConfigId());

        String logInfo = String.format("%s - generate %s %s @[%s, %s] from [%s]",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd hh:mm:ss")),
                sendFTPRequest.getSimulatorType(),
                sendFTPRequest.getMessageType(),
                configuration.get("name"),
                configuration.get("ip"),
                sendFTPRequest.getClientAddress());

        Object proceed = joinPoint.proceed();

        log.info(logInfo);

        writeLogToFile(logInfo);

        return proceed;
    }

    private void writeLogToFile(String logInfo) throws IOException {
        File file = new File("application.log");
        List<String> listLog = Files.readLines(file, StandardCharsets.UTF_8);
        if (listLog.size() >= 100) {
            listLog.remove(0);
        }
        listLog.add(logInfo);

        try {
            Files.asCharSink(file, StandardCharsets.UTF_8).writeLines(listLog);
        } catch (IOException e) {
            throw new FailedToSaveException(e.getMessage());
        }
    }
}
