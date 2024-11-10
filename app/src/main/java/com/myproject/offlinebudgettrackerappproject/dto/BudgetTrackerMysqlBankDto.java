package com.myproject.offlinebudgettrackerappproject.dto;

import com.myproject.offlinebudgettrackerappproject.enums.SpendingType;
import com.myproject.offlinebudgettrackerappproject.model.BudgetTrackerSpending;

import java.util.Date;

public class BudgetTrackerMysqlBankDto {

    private String bankName;

    private Double bankBalance;

    private String notes;

    private String bankCurrencyCode;

    private String creationDate;

    public BudgetTrackerMysqlBankDto() {
    }

    public BudgetTrackerMysqlBankDto(String bankName, Double bankBalance, String notes, String bankCurrencyCode) {
        this.bankName = bankName;
        this.bankBalance = bankBalance;
        this.notes = notes;
        this.bankCurrencyCode = bankCurrencyCode;
    }

    public BudgetTrackerMysqlBankDto(String bankName, Double bankBalance, String notes, String bankCurrencyCode, String creationDate) {
        this.bankName = bankName;
        this.bankBalance = bankBalance;
        this.notes = notes;
        this.bankCurrencyCode = bankCurrencyCode;
        this.creationDate = creationDate;
    }

    public BudgetTrackerMysqlBankDto(String bankName) {
        this.bankName = bankName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Double getBankBalance() {
        return bankBalance;
    }

    public void setBankBalance(Double bankBalance) {
        this.bankBalance = bankBalance;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getBankCurrencyCode() {
        return bankCurrencyCode;
    }

    public void setBankCurrencyCode(String bankCurrencyCode) {
        this.bankCurrencyCode = bankCurrencyCode;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "BudgetTrackerMysqlBankDto{" +
                "bankName='" + bankName + '\'' +
                ", bankBalance=" + bankBalance +
                ", notes='" + notes + '\'' +
                ", bankCurrencyCode='" + bankCurrencyCode + '\'' +
                ", creationDate='" + creationDate + '\'' +
                '}';
    }
}
