package com.gelvides.jdbc_education;

import com.gelvides.jdbc_education.entities.User;
import com.gelvides.jdbc_education.jdbconnector.JdbcConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JdbcEducationApplication implements CommandLineRunner {
    @Autowired
    private JdbcConnector connector;

    public static void main(String[] args) {
        SpringApplication.run(JdbcEducationApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        connector.createUser(new User("Test", "Test"));
    }
}
