package com.gelvides.jdbc_education.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class User {
    private int id;
    private String name;
    private String surname;

    public User(String name, String surname){
        this.name = name;
        this.surname = surname;
    }
}
