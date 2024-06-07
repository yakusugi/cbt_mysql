package com.myproject.offlinebudgettrackerappproject.model;

import com.myproject.offlinebudgettrackerappproject.R;

import java.util.ArrayList;

public class Currency {

    private static ArrayList<Currency> currencyArrayList = new ArrayList<>();

    static {
        initCurrencies();
    }

    private String id;
    private String currency;

    public Currency(String id, String currency) {
        this.id = id;
        this.currency = currency;
    }

    public Currency() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public static void initCurrencies() {
        Currency usd = new Currency("0", "USD");
        currencyArrayList.add(usd);

        Currency jpy = new Currency("1", "JPY");
        currencyArrayList.add(jpy);

        Currency euro = new Currency("2", "Euro");
        currencyArrayList.add(euro);

        Currency pound = new Currency("3", "GBP");
        currencyArrayList.add(pound);

        Currency aus_dollar = new Currency("4", "AUD");
        currencyArrayList.add(aus_dollar);

        Currency nz_dollar = new Currency("5", "NZD");
        currencyArrayList.add(nz_dollar);

        Currency can_dollar = new Currency("6", "CAD");
        currencyArrayList.add(can_dollar);


    }

    public int getCurrencyImage() {
        switch (getId()) {
            case "0":
                return R.drawable.ic_money;
            case "1":
                return R.drawable.ic_yen;
            case "2":
                return R.drawable.ic_euro;
            case "3":
                return R.drawable.ic_pound;
            case "4":
                return R.drawable.ic_money;
            case "5":
                return R.drawable.ic_money;
            case "6":
                return R.drawable.ic_money;
        }
        return R.drawable.ic_money;
    }

    //added sep 4 2022
    public String getCurrencyString() {
        switch (getId()) {
            case "0":
                return "USD";
            case "1":
                return "JPY";
            case "2":
                return "EUR";
            case "3":
                return "GBP";
            case "4":
                return "AUD";
            case "5":
                return "NZD";
            case "6":
                return "CAD";
        }
        return "US Dollar";
    }

    public static ArrayList<Currency> getCurrencyArrayList() {
        return currencyArrayList;
    }
}
