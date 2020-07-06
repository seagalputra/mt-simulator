package com.infosys.mtsimulator.properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;

import java.util.Map;

@Configuration
public class FTPConfiguration {

    @Bean
    public FTPConfiguration ftpConfiguration() {
        return new FTPConfiguration();
    }

    public DefaultSftpSessionFactory getFTPConfigurationFactory(Map<String, String> ftpConfiguration) {
        DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory();
        factory.setHost(ftpConfiguration.get("ip"));
        factory.setPort(22);
        factory.setAllowUnknownKeys(true);
        factory.setUser(ftpConfiguration.get("user"));
        factory.setPassword(ftpConfiguration.get("pass"));
        return factory;
    }
}
