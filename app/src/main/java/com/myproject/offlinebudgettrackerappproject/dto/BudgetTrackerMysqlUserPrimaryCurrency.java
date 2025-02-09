package com.myproject.offlinebudgettrackerappproject.dto;

public class BudgetTrackerMysqlUserPrimaryCurrency {
    private String email;
    private String primaryCurrency;

    public BudgetTrackerMysqlUserPrimaryCurrency() {
    }

    public BudgetTrackerMysqlUserPrimaryCurrency(String email, String primaryCurrency) {
        this.email = email;
        this.primaryCurrency = primaryCurrency;
    }

    public BudgetTrackerMysqlUserPrimaryCurrency(String email) {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPrimaryCurrency() {
        return primaryCurrency;
    }

    public void setPrimaryCurrency(String primaryCurrency) {
        this.primaryCurrency = primaryCurrency;
    }

    @Override
    public String toString() {
        return "BudgetTrackerMysqlUserPrimaryCurrency{" +
                "email='" + email + '\'' +
                ", primaryCurrency='" + primaryCurrency + '\'' +
                '}';
    }
}
