package com.myproject.offlinebudgettrackerappproject.data;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlSpendingDto;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingListCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public abstract class BaseSpendingStatsDao {
    protected final Context context;

    public BaseSpendingStatsDao(Context context) {
        this.context = context.getApplicationContext();
    }

    protected String loadServerConfig(String propertyName) throws IOException {
        Properties properties = new Properties();
        InputStream inputStream = context.getAssets().open("server_config.properties");
        properties.load(inputStream);
        return properties.getProperty(propertyName);
    }

    protected void sendRequest(String endpoint, Map<String, String> params, MysqlSpendingListCallback callback) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, endpoint,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.optString("success", "");
                        if ("1".equals(success)) {
                            JSONArray jsonArray = jsonObject.optJSONArray("result");
                            if (jsonArray != null) {
                                List<BudgetTrackerMysqlSpendingDto> spendingList = parseJsonArray(jsonArray);
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

    private List<BudgetTrackerMysqlSpendingDto> parseJsonArray(JSONArray jsonArray) throws JSONException {
        List<BudgetTrackerMysqlSpendingDto> spendingList = new ArrayList<>();
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
            String aliasPercentageString = jsonObjectItem.getString("alias_percentage");
            String currencyCode = jsonObjectItem.getString("currency_code");
            int quantity = Integer.parseInt(jsonObjectItem.getString("quantity"));
            double vatRate = vatRateString.isEmpty() ? 0.0 : Double.parseDouble(vatRateString);
            double price = priceString.isEmpty() ? 0.0 : Double.parseDouble(priceString);
            double aliasPercentage = aliasPercentageString.isEmpty() ? 0.0 : Double.parseDouble(aliasPercentageString);

            BudgetTrackerMysqlSpendingDto spendingDto = new BudgetTrackerMysqlSpendingDto(
                    date,
                    storeName,
                    productName,
                    productType,
                    price,
                    vatRate,
                    currencyCode,
                    quantity,
                    aliasPercentage
            );
            spendingList.add(spendingDto);
        }
        return spendingList;
    }
}

