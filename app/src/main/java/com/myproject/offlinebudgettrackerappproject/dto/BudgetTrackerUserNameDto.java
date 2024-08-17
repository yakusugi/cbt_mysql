package com.myproject.offlinebudgettrackerappproject.dto;

public class BudgetTrackerUserNameDto {
    private String userName;

    public BudgetTrackerUserNameDto() {
    }

    public BudgetTrackerUserNameDto(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
