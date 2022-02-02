package com.gelvides.jdbc_education.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "database.connections.postrgres")
public class AppProperties {
    private String host;
    private String port;
    private String userName;
    private String userPass;
    private String database;
}
