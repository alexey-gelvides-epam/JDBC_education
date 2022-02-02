package com.gelvides.jdbc_education.entities;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class Account {
    private int id;
    private int accountNumber;
    private String type;
    private int userId;
    private Date openDate;

    public Account(AccountType type, int userId){
        this.type = type.toString();
        this.userId = userId;
    }
}
