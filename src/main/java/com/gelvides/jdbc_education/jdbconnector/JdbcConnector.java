package com.gelvides.jdbc_education.jdbconnector;
import com.gelvides.jdbc_education.entities.AccountType;
import com.gelvides.jdbc_education.entities.User;
import com.gelvides.jdbc_education.utils.SqlScriptReader;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;

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
    @Autowired
    SqlScriptReader reader;

    @SneakyThrows
    private Connection connection(){
        loging();
        try{
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(host + ":" + port + "/" + database,
                    userName, userPass);
            log.info("Подключились к базе данных " + database);
            return connection;
        } catch (Exception ex){
            log.error(ex.getMessage());
        }
        return null;
    }

    public void createTable() {
        try (Statement statement = connection().createStatement()){
            statement.execute(reader.getSQLScript("src/main/resources/sql/users.sql"));
            log.info("Таблица \"USERS\" создана");
            statement.execute(reader.getSQLScript("src/main/resources/sql/accounts.sql"));
            log.info("Таблица \"ACCOUNTS\" создана");
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    public void createUser(User user){
        var sql = "insert into USERS (user_name, user_surname) values ((?), (?))";
        try(PreparedStatement statement = connection().prepareStatement(sql)){
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.executeUpdate();
            log.info("Юзер создан");
        }catch (Exception ex){
            log.error(ex.getMessage());
        }
    }

    public void createAccount(AccountType type, User user){
        var sql = "insert into accounts (type, user_id) VALUES ((?), (?))";
        try(PreparedStatement statement = connection().prepareStatement(sql)){
            statement.setString(1, type.toString());
            statement.setInt(2, getIdUser(user));
            statement.executeUpdate();
            log.info("Договор создан");
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    private int getIdUser(User user){
        var sql = "select user_id from users where user_name = '" + user.getName() + "' and user_surname = '" + user.getSurname() + "'";
        int userId = 0;
        try(ResultSet resultSet = connection().createStatement().executeQuery(sql)){
            while(resultSet.next()){
                userId = resultSet.getInt("user_id");
                log.info("Юзер найден ID: " + userId);
            }
            if(userId == 0)
                throw new Exception("User not found");
            return userId;
        }catch (Exception ex){
            log.error(ex.getMessage());
            return 0;
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
