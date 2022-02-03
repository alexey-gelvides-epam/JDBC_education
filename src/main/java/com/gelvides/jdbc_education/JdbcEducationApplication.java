package com.gelvides.jdbc_education;

import com.gelvides.jdbc_education.entities.AccountType;
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
    public void run(String... args) {
        connector.createTable();
        connector.createUser("Boris", "Petrov");
        connector.createUser("Anastasya", "Ivanova");
        connector.createUser("Andrew", "Ivanov");
        connector.createUser("Sergey", "Turgenev");
        connector.createUser("Aleksandr", "Pushkin");
        connector.createUser("Vladimir", "Putin");
        connector.createUser("Vladimir", "Zelenskii");
        connector.createUser("Elizabeth", "Queen");
        connector.createUser("Petrov", "Boris");

        connector.createAccount(AccountType.CREDIT, connector.getUser("Andrew", "Ivanov"));
        connector.createAccount(AccountType.CREDIT, connector.getUser("Petrov", "Boris"));

        connector.getInfo("Ivanov");
        connector.getInfo("Ivanov", AccountType.CREDIT);

        connector.deleteUserAccount("Petrov", "Boris");

        connector.editUser(connector.getUser("Anastasya", "Ivanova"),
                "Anastasya", "Mashkova");

    }
}
