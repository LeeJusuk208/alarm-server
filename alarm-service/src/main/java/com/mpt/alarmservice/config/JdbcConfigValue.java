package com.mpt.alarmservice.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Getter
@Component
public class JdbcConfigValue {
    @Value("${spring.datasource.driver-class-name}")
    private String dbDriverName;

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Value("${mpt.database-port}")
    private String dbPort;

    @Value("${mpt.database-name}")
    private String dbName;

    public String getJdbcUrl() {
        return dbUrl + ":" + dbPort + "/" + dbName;
    }
}
