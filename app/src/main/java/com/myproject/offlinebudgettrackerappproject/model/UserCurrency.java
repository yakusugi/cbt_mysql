package com.myproject.offlinebudgettrackerappproject.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Index;
import androidx.room.Ignore;
import androidx.annotation.NonNull;
@Entity(tableName = "user_currency", indices = {@Index(value = "email", unique = true)})
public class UserCurrency {

    @PrimaryKey(autoGenerate = true)  // Auto-incrementing ID
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "primary_currency")
    private String primaryCurrency;

    @ColumnInfo(name = "last_updated")
    private Long lastUpdated;

    @NonNull
    @ColumnInfo(name = "conversion_rate")
    private Double conversionRate;

    @NonNull
    @ColumnInfo(name = "country_code")
    private String countryCode;

    @ColumnInfo(name = "is_active")
    private Boolean isActive;

    public UserCurrency(@NonNull String email, String primaryCurrency, Long lastUpdated, Double conversionRate, String countryCode, Boolean isActive) {
        this.id = id;
        this.email = email;
        this.primaryCurrency = primaryCurrency;
        this.lastUpdated = lastUpdated;
        this.conversionRate = conversionRate;
        this.countryCode = countryCode;
        this.isActive = isActive;
    }

    // Getter Methods
    public int getId() { return id; }
    public String getEmail() { return email; }
    public String getPrimaryCurrency() { return primaryCurrency; }
    public Long getLastUpdated() { return lastUpdated; }
    public Double getConversionRate() { return conversionRate; }
    public String getCountryCode() { return countryCode; }
    public Boolean getIsActive() { return isActive; }

    // Setter Methods
    public void setId(int id) { this.id = id; }
    public void setEmail(String email) { this.email = email; }
    public void setPrimaryCurrency(String primaryCurrency) { this.primaryCurrency = primaryCurrency; }
    public void setLastUpdated(Long lastUpdated) { this.lastUpdated = lastUpdated; }
    public void setConversionRate(Double conversionRate) { this.conversionRate = conversionRate; }
    public void setCountryCode(String countryCode) { this.countryCode = countryCode; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

}
