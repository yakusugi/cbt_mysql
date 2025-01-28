package com.myproject.offlinebudgettrackerappproject.data;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlForeignSpendingDto;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingForeignListCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingForeignSumCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingSumCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingTargetSumCallback;

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

public class BaseForeignSumDao {

    protected final Context context;

    public BaseForeignSumDao(Context context) {
        this.context = context.getApplicationContext();
    }

    protected String loadServerConfig(String propertyName) throws IOException {
        Properties properties = new Properties();
        InputStream inputStream = context.getAssets().open("server_config.properties");
        properties.load(inputStream);
        return properties.getProperty(propertyName);
    }

    protected void sendForeignRequest(String endpoint, Map<String, String> params, MysqlSpendingForeignSumCallback callback) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, endpoint,
                response -> {
                    try {
                        Log.d("JSONResponse", response);
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.optString("success", "");

                        if ("1".equals(success)) {
                            JSONArray jsonArray = jsonObject.optJSONArray("result");
                            if (jsonArray != null) {
                                double totalSpending = 0.0;
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject spendingObject = jsonArray.getJSONObject(i);
                                    String spendingCalcSum = spendingObject.getString("total_sum");
                                    totalSpending += Double.parseDouble(spendingCalcSum);
                                }
                                callback.onSuccess(totalSpending);
                            } else {
                                callback.onError("No spending data found");
                            }
                        } else {
                            String errorMessage = jsonObject.optString("error_message", "Error parsing JSON");
                            callback.onError(errorMessage);
                        }
                    } catch (JSONException e) {
                        Log.e("JSONException", e.toString());
                        callback.onError("Error parsing JSON");
                    }
                },
                error -> {
                    Log.e("VolleyError", error.toString());
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

    protected void sendTargetRequest(String endpoint, Map<String, String> params, MysqlSpendingTargetSumCallback callback) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, endpoint,
                response -> {
                    try {
                        Log.d("JSONResponse", response);
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.optString("success", "");

                        if ("1".equals(success)) {
                            JSONArray jsonArray = jsonObject.optJSONArray("result");
                            if (jsonArray != null) {
                                double totalSpending = 0.0;
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject spendingObject = jsonArray.getJSONObject(i);
                                    String spendingCalcSum = spendingObject.getString("total_sum");
                                    totalSpending += Double.parseDouble(spendingCalcSum);
                                }
                                callback.onSuccess(totalSpending);
                            } else {
                                callback.onError("No spending data found");
                            }
                        } else {
                            String errorMessage = jsonObject.optString("error_message", "Error parsing JSON");
                            callback.onError(errorMessage);
                        }
                    } catch (JSONException e) {
                        Log.e("JSONException", e.toString());
                        callback.onError("Error parsing JSON");
                    }
                },
                error -> {
                    Log.e("VolleyError", error.toString());
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
}

