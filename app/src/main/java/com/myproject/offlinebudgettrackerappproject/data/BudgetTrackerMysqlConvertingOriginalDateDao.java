package com.myproject.offlinebudgettrackerappproject.data;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlForeignSpendingDto;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlSpendingDto;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingForeignListCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingListCallback;
import com.myproject.offlinebudgettrackerappproject.util.SharedPreferencesManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BudgetTrackerMysqlConvertingOriginalDateDao extends BaseSpendingDao {

    public BudgetTrackerMysqlConvertingOriginalDateDao(Context context) {
        super(context);
    }

    public void getSearchDateForeignList(BudgetTrackerMysqlForeignSpendingDto dto, MysqlSpendingForeignListCallback callback) {
        try {
            String serverUrl = loadServerConfig("server_url");
            String phpFile = loadServerConfig("spending_date_search_php_file");
            String endpoint = serverUrl + phpFile;

            Map<String, String> params = new HashMap<>();
            params.put("email", SharedPreferencesManager.getUserEmail(context));
            params.put("currency_code", dto.getCurrencyCode());
            params.put("date_from", dto.getDateFrom());
            params.put("date_to", dto.getDateTo());

            sendForeignRequest(endpoint, params, callback);
        } catch (IOException e) {
            e.printStackTrace();
            callback.onError("Error loading server configuration");
        }
    }

    protected void sendForeignRequest(String endpoint, Map<String, String> params, MysqlSpendingForeignListCallback callback) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, endpoint,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.optString("success", "");
                        if ("1".equals(success)) {
                            JSONArray jsonArray = jsonObject.optJSONArray("result");
                            if (jsonArray != null) {
                                List<BudgetTrackerMysqlForeignSpendingDto> spendingList = parseForeignJsonArray(jsonArray);
                                callback.onSuccess(spendingList);
                            } else {
                                callback.onError("No spending data found");
                            }
                        } else {
                            callback.onError(jsonObject.optString("error_message", "Error parsing JSON"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        callback.onError("Error parsing JSON");
                    }
                },
                error -> {
                    error.printStackTrace();
                    callback.onError("Unable to fetch data: " + error.getMessage());
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    protected List<BudgetTrackerMysqlForeignSpendingDto> parseForeignJsonArray(JSONArray jsonArray) throws JSONException {
        List<BudgetTrackerMysqlForeignSpendingDto> spendingList = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObjectItem = jsonArray.getJSONObject(i);
            String dateStr = jsonObjectItem.getString("spending_date");
            Date date = null;
            try {
                date = dateFormat.parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String storeName = jsonObjectItem.getString("store_name");
            String productName = jsonObjectItem.getString("product_name");
            String productType = jsonObjectItem.getString("product_type");
            String vatRateString = jsonObjectItem.getString("vat_rate");
            String priceString = jsonObjectItem.getString("price");
            String note = jsonObjectItem.getString("note");
            String currencyCode = jsonObjectItem.getString("currency_code");
            String convertedPriceString = jsonObjectItem.getString("converted_price");
            String targetCurrencyCode = jsonObjectItem.getString("target_currency_code");
            String conversionRateString = jsonObjectItem.getString("conversion_rate");
            int quantity = Integer.parseInt(jsonObjectItem.getString("quantity"));
            double vatRate = vatRateString.isEmpty() ? 0.0 : Double.parseDouble(vatRateString);
            double price = priceString.isEmpty() ? 0.0 : Double.parseDouble(priceString);
            double convertedPrice = priceString.isEmpty() ? 0.0 : Double.parseDouble(convertedPriceString);
            double conversionRate = priceString.isEmpty() ? 0.0 : Double.parseDouble(conversionRateString);

            BudgetTrackerMysqlForeignSpendingDto spendingDto = new BudgetTrackerMysqlForeignSpendingDto(
                    date,
                    storeName,
                    productName,
                    productType,
                    price,
                    vatRate,
                    note,
                    currencyCode,
                    convertedPrice,
                    targetCurrencyCode,
                    conversionRate,
                    quantity
            );
            spendingList.add(spendingDto);
        }

        return spendingList;
    }
}

