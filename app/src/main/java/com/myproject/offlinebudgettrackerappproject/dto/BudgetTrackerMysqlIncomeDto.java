package com.myproject.offlinebudgettrackerappproject.dto;

import com.myproject.offlinebudgettrackerappproject.enums.IncomeType;
import com.myproject.offlinebudgettrackerappproject.enums.SpendingType;
import com.myproject.offlinebudgettrackerappproject.model.BudgetTrackerSpending;

import java.util.Date;

public class BudgetTrackerMysqlIncomeDto {

    private Date date;
    private String dateFrom;
    private String dateTo;
    private String incomeCategory;
    private String incomeName;
    private Double income;
    private String incomeNote;
    private String incomeCurrencyCode;
    private String creationDate;

    private double aliasPercentage;

    public BudgetTrackerMysqlIncomeDto() {
    }

    public BudgetTrackerMysqlIncomeDto(Date date, String incomeCategory, String incomeName, Double income, String incomeNote, String incomeCurrencyCode) {
        this.date = date;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.incomeCategory = incomeCategory;
        this.incomeName = incomeName;
        this.income = income;
        this.incomeNote = incomeNote;
        this.incomeCurrencyCode = incomeCurrencyCode;
    }

    public BudgetTrackerMysqlIncomeDto(IncomeType type, String name, String dateFrom, String dateTo) {
        if (type == IncomeType.INCOME_NAME) {
            this.incomeName = name;
        } else if (type == IncomeType.INCOME_CATEGORY) {
            this.incomeCategory = name;
        }
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public String getIncomeCategory() {
        return incomeCategory;
    }

    public void setIncomeCategory(String incomeCategory) {
        this.incomeCategory = incomeCategory;
    }

    public String getIncomeName() {
        return incomeName;
    }

    public void setIncomeName(String incomeName) {
        this.incomeName = incomeName;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public String getIncomeNote() {
        return incomeNote;
    }

    public void setIncomeNote(String incomeNote) {
        this.incomeNote = incomeNote;
    }

    public String getIncomeCurrencyCode() {
        return incomeCurrencyCode;
    }

    public void setIncomeCurrencyCode(String incomeCurrencyCode) {
        this.incomeCurrencyCode = incomeCurrencyCode;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public double getAliasPercentage() {
        return aliasPercentage;
    }

    public void setAliasPercentage(double aliasPercentage) {
        this.aliasPercentage = aliasPercentage;
    }

    @Override
    public String toString() {
        return "BudgetTrackerMysqlIncomeDto{" +
                "date=" + date +
                ", dateFrom='" + dateFrom + '\'' +
                ", dateTo='" + dateTo + '\'' +
                ", incomeCategory='" + incomeCategory + '\'' +
                ", incomeName='" + incomeName + '\'' +
                ", income=" + income +
                ", incomeNote='" + incomeNote + '\'' +
                ", incomeCurrencyCode='" + incomeCurrencyCode + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", aliasPercentage=" + aliasPercentage +
                '}';
    }
}
