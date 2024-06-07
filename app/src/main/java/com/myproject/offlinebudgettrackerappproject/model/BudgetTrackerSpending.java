package com.myproject.offlinebudgettrackerappproject.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "budget_tracker_spending_table")
public class BudgetTrackerSpending implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "date")
    private String date;

    @Nullable
    @ColumnInfo(name = "store_name")
    private String storeName;

    @Nullable
    @ColumnInfo(name = "product_name")
    private String productName;

    @Nullable
    @ColumnInfo(name = "product_type")
    private String productType;

    @ColumnInfo(name = "price")
    private Double price;

    @ColumnInfo(name = "is_tax")
    private Boolean isTax;

    @ColumnInfo(name = "tax_rate")
    private Double taxRate;

    @Nullable
    @ColumnInfo(name = "notes")
    private String notes;

    //new column
    @Nullable
    @ColumnInfo(name = "currency_code")
    private String currencyCode;

    //new column
    @Nullable
    @ColumnInfo(name = "quantity")
    private int quantity;

    //new column
    @ColumnInfo(name = "creation_date")
    private String creationDate;

    // new constructor
    public BudgetTrackerSpending(@NonNull String date, @Nullable String storeName, @Nullable String productName, @Nullable String productType, Double price, Boolean isTax, Double taxRate, @Nullable String notes, @Nullable String currencyCode, @Nullable int quantity, String creationDate) {
        this.id = id;
        this.date = date;
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

    @Ignore
    public BudgetTrackerSpending(@NonNull String date, @Nullable String storeName, @Nullable String productName, @Nullable String productType, Double price, Boolean isTax, Double taxRate, @Nullable String notes) {
        this.id = id;
        this.date = date;
        this.storeName = storeName;
        this.productName = productName;
        this.productType = productType;
        this.price = price;
        this.isTax = isTax;
        this.taxRate = taxRate;
        this.notes = notes;
    }

    @Ignore
    public BudgetTrackerSpending(String searchKey, String dateFrom, String dateTo) {
        this.id = id;
        this.storeName = storeName;
        this.date = dateFrom;
        this.date = dateTo;
    }

    @Ignore
    public BudgetTrackerSpending(String searchWord, String replaceWith) {
    }

    @Ignore
    public BudgetTrackerSpending() {

    }

    @Ignore
    public BudgetTrackerSpending(String searchKey) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Nullable
    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(@Nullable String storeName) {
        this.storeName = storeName;
    }

    @Nullable
    public String getProductName() {
        return productName;
    }

    public void setProductName(@Nullable String productName) {
        this.productName = productName;
    }

    @Nullable
    public String getProductType() {
        return productType;
    }

    public void setProductType(@Nullable String productType) {
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

    @Nullable
    public String getNotes() {
        return notes;
    }

    public void setNotes(@Nullable String notes) {
        this.notes = notes;
    }

    // new getters and setters
    @Nullable
    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(@Nullable String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Nullable
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(@Nullable int quantity) {
        this.quantity = quantity;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    // for csv?
    public String getColumn1() {
        return date;
    }

    public String getColumn2() {
        return storeName != null ? storeName : "";
    }

    public String getColumn3() {
        return productName != null ? productName : "";
    }

    public String getColumn4() {
        return productType != null ? productType : "";
    }

    public Double getColumn5() {
        return price != null ? price : 0;
    }

    public Boolean getColumn6() {
        return isTax;
    }

    public Double getColumn7() {
        return taxRate != null ? taxRate : 0;
    }

    public String getColumn8() {
        return notes != null ? notes : "";
    }

    public String getColumn9() {
        return currencyCode != null ? currencyCode : "";
    }

    public int getColumn10() {
        return quantity != 0 ? quantity : 0;
    }

    public String getColumn11() {
        return creationDate != null ? creationDate : "";
    }
}
