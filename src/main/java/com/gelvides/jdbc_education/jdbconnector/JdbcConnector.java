package com.gelvides.jdbc_education.jdbconnector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;

@Slf4j
@Component
public class JdbcConnector {
    @Value("${database.connections.postrgres.host}")
    private String host;
    @Value("${database.connections.postrgres.port}")
    private String port;
    @Value("${database.connections.postrgres.driver}")
    private String driver;
    @Value("${database.connections.postrgres.user-name}")
    private String userName;
    @Value("${database.connections.postrgres.user-pass}")
    private String userPass;
    @Value("${database.connections.postrgres.database}")
    private String database;

    public void connection(){
        loging();
        try{
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(host + ":" + port + "/" + database,
                    userName, userPass);
            log.info("Вроде сконектились");
        } catch (Exception ex){
            log.error(ex.getMessage());
        }

    }

    private void loging(){
        log.info("host: " + host);
        log.info("port: " + port);
        log.info("driver: " + driver);
        log.info("user-name: " + userName);
        log.info("user-pass: " + userPass);
        log.info("database: " + database);
    }





}
