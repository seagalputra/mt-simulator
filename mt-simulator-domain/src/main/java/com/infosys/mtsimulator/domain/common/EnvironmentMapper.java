package com.infosys.mtsimulator.domain.common;

import com.infosys.mtsimulator.properties.ConfigProperties;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class EnvironmentMapper {

    private final ConfigProperties configProperties;

    public Map<String, String> obtainEnvironment(String configId) {
        List<Map<String, String>> environmentList = configProperties.getEnvironmentConfiguration();
        return environmentList.stream()
                .filter(environment -> environment.get("id").equals(configId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Configuration Not Found!"));
    }
}
