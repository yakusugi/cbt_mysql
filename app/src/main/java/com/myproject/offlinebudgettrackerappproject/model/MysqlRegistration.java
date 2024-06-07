package com.myproject.offlinebudgettrackerappproject.model;

import java.io.Serializable;

public class MysqlRegistration implements Serializable {

    private String userId;
    private String password;


    public MysqlRegistration(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
