package com.myproject.offlinebudgettrackerappproject.dto;

import com.myproject.offlinebudgettrackerappproject.enums.SpendingType;
import com.myproject.offlinebudgettrackerappproject.model.BudgetTrackerSpending;

import java.util.Date;

public class BudgetTrackerMysqlForeignSpendingDto {

    private Date date;

    private String dateFrom;

    private String dateTo;

    private String storeName;

    private String productName;

    private String productType;

    private Double price;

    private Double convertedPrice;

    private String targetCurrencyCode;

    private Double conversionRate;

    private Boolean isTax;

    private Double taxRate;

    // Make notes nullable and provide a default value if needed
    private String notes = "No notes available";
//    private String notes;

    private String currencyCode;

    //new column
    private int quantity;

    //new column
    private String creationDate;


    public BudgetTrackerMysqlForeignSpendingDto() {
    }

    public BudgetTrackerMysqlForeignSpendingDto(SpendingType type, String name, String dateFrom, String dateTo) {
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

    public BudgetTrackerMysqlForeignSpendingDto(SpendingType type, String name, String currencyCode, String dateFrom, String dateTo) {
        if (type == SpendingType.STORE) {
            this.storeName = name;
        } else if (type == SpendingType.PRODUCT_NAME) {
            this.productName = name;
        } else if (type == SpendingType.PRODUCT_TYPE) {
            this.productType = name;
        }
        this.currencyCode = currencyCode;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public BudgetTrackerMysqlForeignSpendingDto(String storeName, String dateFrom, String dateTo) {
        this.storeName = storeName;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;

    }

    public BudgetTrackerMysqlForeignSpendingDto(Date date, String storeName, String productName, String productType, Double price, Double taxRate, String notes, String currencyCode, int quantity) {
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

    public BudgetTrackerMysqlForeignSpendingDto(String dateFrom, String dateTo) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public BudgetTrackerMysqlForeignSpendingDto(Date date, String dateFrom, String dateTo, String storeName, String productName, String productType, Double price, Double convertedPrice, String targetCurrencyCode, Double conversionRate, Boolean isTax, Double taxRate, String notes, String currencyCode, int quantity, String creationDate, double aliasPercentage) {
        this.date = date;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.storeName = storeName;
        this.productName = productName;
        this.productType = productType;
        this.price = price;
        this.convertedPrice = convertedPrice;
        this.targetCurrencyCode = targetCurrencyCode;
        this.conversionRate = conversionRate;
        this.isTax = isTax;
        this.taxRate = taxRate;
        this.notes = notes;
        this.currencyCode = currencyCode;
        this.quantity = quantity;
        this.creationDate = creationDate;
    }

    public BudgetTrackerMysqlForeignSpendingDto(Date date, String storeName, String productName, String productType, double price, double vatRate, String note, String currencyCode, double convertedPrice, String targetCurrencyCode, double conversionRate, int quantity) {
        this.date = date;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.storeName = storeName;
        this.productName = productName;
        this.productType = productType;
        this.price = price;
        this.convertedPrice = convertedPrice;
        this.targetCurrencyCode = targetCurrencyCode;
        this.conversionRate = conversionRate;
        this.isTax = isTax;
        this.taxRate = taxRate;
        this.notes = notes;
        this.currencyCode = currencyCode;
        this.quantity = quantity;
        this.creationDate = creationDate;
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

    public Double getConvertedPrice() {
        return convertedPrice;
    }

    public void setConvertedPrice(Double convertedPrice) {
        this.convertedPrice = convertedPrice;
    }

    public String getTargetCurrencyCode() {
        return targetCurrencyCode;
    }

    public void setTargetCurrencyCode(String targetCurrencyCode) {
        this.targetCurrencyCode = targetCurrencyCode;
    }

    public Double getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(Double conversionRate) {
        this.conversionRate = conversionRate;
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
        return "BudgetTrackerMysqlForeignSpendingDto{" +
                "date=" + date +
                ", dateFrom='" + dateFrom + '\'' +
                ", dateTo='" + dateTo + '\'' +
                ", storeName='" + storeName + '\'' +
                ", productName='" + productName + '\'' +
                ", productType='" + productType + '\'' +
                ", price=" + price +
                ", convertedPrice=" + convertedPrice +
                ", targetCurrencyCode='" + targetCurrencyCode + '\'' +
                ", conversionRate=" + conversionRate +
                ", isTax=" + isTax +
                ", taxRate=" + taxRate +
                ", notes='" + notes + '\'' +
                ", currencyCode='" + currencyCode + '\'' +
                ", quantity=" + quantity +
                ", creationDate='" + creationDate + '\'' +
                '}';
    }
}
