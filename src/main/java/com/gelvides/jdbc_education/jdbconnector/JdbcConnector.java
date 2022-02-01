package com.gelvides.jdbc_education.jdbconnector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Connection;
import java.sql.DriverManager;

@Slf4j
public class JdbcConnector {
    private String host = "jdbc:postgresql://localhost";
    private String port = "5433";
    private String driver = "org.postgresql.Driver";
    private String userName = "postgres";
    private String userPass = "qwerty";
    private String database = "postgres";

    public JdbcConnector(){
        loging();
    }

    public void connection(){
        try{
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(host + ":" + port + "/" + database,
                    userName, userPass);
            log.info("Вроде сконектились");
        } catch (Exception ex){
            ex.printStackTrace();
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
