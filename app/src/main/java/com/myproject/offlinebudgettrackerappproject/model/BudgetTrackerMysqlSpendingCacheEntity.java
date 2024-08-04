package com.myproject.offlinebudgettrackerappproject.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "budget_tracker_mysql_spending_cache_table")
public class BudgetTrackerMysqlSpendingEntity {
    @ColumnInfo(name = "spending_id")
    private int spendingId;
    @ColumnInfo(name = "date")
    private String date;
    @ColumnInfo(name = "store_name")
    private String storeName;
    @ColumnInfo(name = "product_name")
    private String productName;
    @ColumnInfo(name = "product_type")
    private String productType;
    @ColumnInfo(name = "price")
    private double price;

    @ColumnInfo(name = "tax_rate")
    private double taxRate;

    @ColumnInfo(name = "notes")
    private String notes;

    @ColumnInfo(name = "currency_code")
    private String currencyCode;

    @ColumnInfo(name = "quantity")
    private int quantity;

    public BudgetTrackerMysqlSpendingEntity() {
    }

    public BudgetTrackerMysqlSpendingEntity(int spendingId, String date, String storeName, String productName, String productType, double price, double taxRate, String notes, String currencyCode, int quantity) {
        this.spendingId = spendingId;
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

    public int getSpendingId() {
        return spendingId;
    }

    public void setSpendingId(int spendingId) {
        this.spendingId = spendingId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
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
}
