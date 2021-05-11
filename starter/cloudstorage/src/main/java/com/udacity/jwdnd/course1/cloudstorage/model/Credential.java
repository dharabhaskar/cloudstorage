package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.Data;

/*
 credentialid INT PRIMARY KEY auto_increment,
    url VARCHAR(100),
    username VARCHAR (30),
    key VARCHAR,
    password VARCHAR,
* * */
@Data
public class Credential {
    private Integer credentialId;
    private String url;
    private String username;
    private String key;
    private String password;
    private Integer userId;
    private String decryptedPassword;
}
