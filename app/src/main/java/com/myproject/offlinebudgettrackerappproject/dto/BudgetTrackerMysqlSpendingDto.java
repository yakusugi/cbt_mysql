package com.myproject.offlinebudgettrackerappproject.dto;

import com.myproject.offlinebudgettrackerappproject.enums.SpendingType;
import com.myproject.offlinebudgettrackerappproject.model.BudgetTrackerSpending;

import java.time.LocalDate;
import java.util.Date;

public class BudgetTrackerMysqlSpendingDto {

    private Date date;

    private String dateFrom;

    private String dateTo;

    private String storeName;

    private String productName;

    private String productType;

    private Double price;

    private Boolean isTax;

    private Double taxRate;

    private String notes;

    private String currencyCode;

    //new column
    private int quantity;

    //new column
    private String creationDate;

    public BudgetTrackerMysqlSpendingDto() {
    }

    public BudgetTrackerMysqlSpendingDto(SpendingType type, String name, String dateFrom, String dateTo) {
        if (type == SpendingType.STORE) {
            this.storeName = name;
        } else if (type == SpendingType.PRODUCT_NAME) {
            this.productName = name;
        } else if (type == SpendingType.PRODUCT_TYPE) {
            this.productType = name;
        } else if (type == SpendingType.CURRENCY) {
            this.currencyCode = name;
        }
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public BudgetTrackerMysqlSpendingDto(String storeName, String dateFrom, String dateTo) {
        this.storeName = storeName;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;

    }

//    public BudgetTrackerMysqlSpendingDto(String productName, String dateFrom, String dateTo) {
//        this.storeName = productName;
//        this.dateFrom = dateFrom;
//        this.dateTo = dateTo;
//    }

    public BudgetTrackerMysqlSpendingDto(Date date, String dateFrom, String dateTo, String storeName, String productName, String productType, Double price, Boolean isTax, Double taxRate, String notes, String currencyCode, int quantity, String creationDate) {
        this.date = date;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.storeName = storeName;
        this.productName = productName;
        this.productType = productType;
        this.price = price;
        this.isTax = isTax;
        this.taxRate = taxRate;
        this.notes = notes;
        this.currencyCode = currencyCode;
        this.quantity = quantity;
        this.creationDate = creationDate;
    }

    public BudgetTrackerMysqlSpendingDto(Date date, String storeName, String productName, String productType, Double price, Double taxRate, String notes, String currencyCode, int quantity) {
        this.date = date;
        this.storeName = storeName;
        this.productName = productName;
        this.productType = productType;
        this.price = price;
        this.taxRate = taxRate;
        this.notes = notes;
        this.currencyCode = currencyCode;
        this.quantity = quantity;
    }

    public BudgetTrackerMysqlSpendingDto(String dateFrom, String dateTo) {
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

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getTax() {
        return isTax;
    }

    public void setTax(Boolean tax) {
        isTax = tax;
    }

    public Double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }


    @Override
    public String toString() {
        return "BudgetTrackerMysqlSpendingDto{" +
                "date=" + date +
                ", dateFrom='" + dateFrom + '\'' +
                ", dateTo='" + dateTo + '\'' +
                ", storeName='" + storeName + '\'' +
                ", productName='" + productName + '\'' +
                ", productType='" + productType + '\'' +
                ", price=" + price +
                ", isTax=" + isTax +
                ", taxRate=" + taxRate +
                ", notes='" + notes + '\'' +
                ", currencyCode='" + currencyCode + '\'' +
                ", quantity=" + quantity +
                ", creationDate='" + creationDate + '\'' +
                '}';
    }

    public BudgetTrackerSpending toEntity() {
        return new BudgetTrackerSpending(
                this.date.toString(),
                this.storeName,
                this.productName,
                this.productType,
                this.price,
                this.isTax,
                this.taxRate,
                this.notes,
                this.currencyCode,
                this.quantity,
                this.creationDate
        );
    }

}
