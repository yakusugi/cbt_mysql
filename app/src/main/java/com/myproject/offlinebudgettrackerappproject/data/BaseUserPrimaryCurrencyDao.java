package com.myproject.offlinebudgettrackerappproject.data;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlSpendingDto;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlUserPrimaryCurrency;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingListCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlUserPrimaryCurrencyCallback;

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
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public abstract class BaseUserPrimaryCurrencyDao {
    protected final Context context;

    public BaseUserPrimaryCurrencyDao(Context context) {
        this.context = context.getApplicationContext();
    }

    protected String loadServerConfig(String propertyName) throws IOException {
        Properties properties = new Properties();
        InputStream inputStream = context.getAssets().open("server_config.properties");
        properties.load(inputStream);
        return properties.getProperty(propertyName);
    }

    protected void sendRequest(String endpoint, Map<String, String> params, MysqlUserPrimaryCurrencyCallback callback) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, endpoint,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.optString("success", "");
                        if ("1".equals(success)) {
                            JSONArray jsonArray = jsonObject.optJSONArray("result");
                            if (jsonArray != null) {
                                BudgetTrackerMysqlUserPrimaryCurrency budgetTrackerMysqlUserPrimaryCurrency = new BudgetTrackerMysqlUserPrimaryCurrency();
                                callback.onSuccess(budgetTrackerMysqlUserPrimaryCurrency);
                            } else {
                                callback.onError("No user currency data found");
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

    protected  String sendRequestAndGetResult(String endpoint, Map<String, String> params) {
        final String[] result = {null}; // To store the response
        final CountDownLatch latch = new CountDownLatch(1); // Latch to wait for response

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, endpoint,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.optInt("success") == 1) {
                            JSONArray resultArray = jsonObject.optJSONArray("result");
                            if (resultArray != null && resultArray.length() > 0) {
                                JSONObject currencyObject = resultArray.getJSONObject(0);
                                result[0] = currencyObject.optString("primary_currency", null);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("JSON_ERROR", "Error parsing JSON response: " + e.getMessage());
                    } finally {
                        latch.countDown(); // ✅ Release the lock once response is received
                    }
                },
                error -> {
                    Log.e("VolleyError", "Error fetching currency: " + error.getMessage());
                    latch.countDown(); // ✅ Release the lock on error too
                }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };

        requestQueue.add(stringRequest);

        try {
            latch.await(5, TimeUnit.SECONDS); // ✅ Wait for response (max 5 seconds)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result[0]; // ✅ Return the fetched currency (or null if failed)
    }
}

