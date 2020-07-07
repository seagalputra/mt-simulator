package com.infosys.mtsimulator.domain.common;

import com.infosys.mtsimulator.api.request.SendFTPRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

        return proceed;
    }
}
