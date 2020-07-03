package com.infosys.mtsimulator.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.*;

@Configuration
@PropertySource("classpath:ftpConfig.properties")
@Data
public class ConfigProperties {

    private final Environment environment;

    @Value("${Environment}")
    private int numberOfEnvironment;

    public ConfigProperties(Environment environment) {
        this.environment = environment;
    }

    public List<Map<String, String>> getEnvironmentConfiguration() {
        List<Map<String, String>> listEnvironment = new ArrayList<>();

        for (int index = 1; index <= numberOfEnvironment; index++) {
            Map<String, String> envProperty = new HashMap<>();
            String configurationId = UUID.randomUUID().toString();
            envProperty.put("id", configurationId);
            envProperty.put("name", environment.getProperty("env." + index + ".name"));
            envProperty.put("ip", environment.getProperty("env." + index + ".ip"));
            envProperty.put("path", environment.getProperty("env." + index + ".path"));
            envProperty.put("user", environment.getProperty("env." + index + ".user"));
            envProperty.put("pass", environment.getProperty("env." + index + ".pass"));

            listEnvironment.add(envProperty);
        }

        return listEnvironment;
    }
}
