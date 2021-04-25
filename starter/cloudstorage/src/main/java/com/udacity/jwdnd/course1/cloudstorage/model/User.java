package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Data
@Builder
public class User {
    private int userid;
    private String username;
    private String salt;
    private String password;
    private String firstname;
    private String lastname;

    @Tolerate
    public User(){}
}
