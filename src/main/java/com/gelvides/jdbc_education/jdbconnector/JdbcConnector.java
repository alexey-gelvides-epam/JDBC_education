package com.gelvides.jdbc_education.jdbconnector;
import com.gelvides.jdbc_education.entities.AccountType;
import com.gelvides.jdbc_education.entities.User;
import com.gelvides.jdbc_education.prop.AppProperties;
import com.gelvides.jdbc_education.utils.SqlScriptReader;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.sql.*;

@Slf4j
@Component
@EnableConfigurationProperties(AppProperties.class)
public class JdbcConnector {
    @Autowired AppProperties appProperties;
    @Autowired SqlScriptReader reader;


    private Connection connection(){
        try{
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(appProperties.getHost() +
                            ":" + appProperties.getPort() +
                            "/" + appProperties.getDatabase(),
                    appProperties.getUserName(), appProperties.getUserPass());
            log.info("Подключились к базе данных " + appProperties.getDatabase());
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

    public void createUser(String name, String surName){
        var sql = "insert into USERS (user_name, user_surname) values ((?), (?))";
        try(PreparedStatement statement = connection().prepareStatement(sql)){
            statement.setString(1, name);
            statement.setString(2, surName);
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
            statement.setInt(2, user.getId());
            statement.executeUpdate();
            log.info("Договор создан");
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    public String getInfo(String surNameUser){
        var sql = "select user_name, user_surname, account_number, type, opendate \n" +
                "from users, accounts \n" +
                "where user_surname = '" + surNameUser + "' and users.user_id = accounts.user_id";
        StringBuilder stringBuilder = new StringBuilder();
        try(ResultSet resultSet = connection().createStatement().executeQuery(sql)){
            while (resultSet.next()){
                stringBuilder.append(resultSet.getString("user_name") + " | " +
                        resultSet.getString("user_surname") + " | " +
                        resultSet.getInt("account_number") + " | " +
                        resultSet.getString("type") + " | " +
                        resultSet.getString("opendate"));
            }
            return stringBuilder.toString();
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return null;
    }

    public String getInfo(String surNameUser, AccountType type){
        var sql = "select user_name, user_surname, account_number, type, opendate \n" +
                "from users, accounts \n" +
                "where user_surname = '" + surNameUser + "' and type = '" + type + "' and users.user_id = accounts.user_id";
        StringBuilder stringBuilder = new StringBuilder();
        try(ResultSet resultSet = connection().createStatement().executeQuery(sql)){
            while (resultSet.next()){
                stringBuilder.append(resultSet.getString("user_name") + " | " +
                        resultSet.getString("user_surname") + " | " +
                        resultSet.getInt("account_number") + " | " +
                        resultSet.getString("type") + " | " +
                        resultSet.getString("opendate"));
            }
            return stringBuilder.toString();
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return null;
    }

    public String deleteUserAccount(String name, String surname){
        var deleteAccount = "delete from accounts using users\n" +
                "where users.user_name = '" + name + "' and user_surname = '" + surname + "' " +
                "and users.user_id = accounts.user_id;";
        var deleteUser = "delete from users\n" +
                "where users.user_name = '" + name + "' and user_surname = '" + surname + "'";
        try(Statement statement = connection().createStatement()){
            statement.execute(deleteAccount);
            statement.execute(deleteUser);
            log.info("Записи успешно удалены");
            System.out.println("Записи " + name + " " + surname + " успешно удалены");
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return null;
    }

    public void editUser(User user, String newName, String newSurName){
        var sql = "update users\n" +
                "set user_name = '"+ newName +"', user_surname = '"+ newSurName + "'\n" +
                "where user_name ='"+ user.getName() +"' and user_surname = '"+ user.getSurname() +"' \n " +
                "returning user_name, user_surname";
        int changes = 0;
        try(ResultSet resultSet = connection().createStatement().executeQuery(sql)){
            while(resultSet.next()){
                if(!resultSet.getString("user_name").equals(user.getName()))
                    changes++;
                if(!resultSet.getString("user_surname").equals(user.getSurname()))
                    changes++;
            }
            log.info("Юзер изменён успешно");
            System.out.println("Пользователь был изменен. Изменено " + changes + " строк");
        }catch (Exception e){
            log.error(e.getMessage());
            System.out.println("Пользователь не был изменен");
        }
    }

    public User getUser(String name, String surName){
        var sql = "select * from users where user_name = '" + name + "' and user_surname = '" + surName + "'";
        var user = new User();
        try(ResultSet resultSet = connection().createStatement().executeQuery(sql)){
            while(resultSet.next()){
                user.setName(resultSet.getString("user_name"))
                        .setSurname(resultSet.getString("user_surname"))
                        .setId(resultSet.getInt("user_id"));
                log.info("Юзер найден ID: " + user.getId());
            }
            if(user.getId() == 0)
                throw new Exception("User not found");
            return user;
        }catch (Exception ex){
            log.error(ex.getMessage());
            return null;
        }
    }


}
