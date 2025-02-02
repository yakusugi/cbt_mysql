package com.myproject.offlinebudgettrackerappproject.dto;

import com.myproject.offlinebudgettrackerappproject.enums.SpendingType;
import com.myproject.offlinebudgettrackerappproject.model.BudgetTrackerSpending;

import java.util.Date;

public class BudgetTrackerMysqlUserCurrencyDto {

    private String currencyCode;

    public BudgetTrackerMysqlUserCurrencyDto() {
    }

    public BudgetTrackerMysqlUserCurrencyDto(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Override
    public String toString() {
        return "BudgetTrackerMysqlUserCurrencyDto{" +
                "currencyCode='" + currencyCode + '\'' +
                '}';
    }
}
