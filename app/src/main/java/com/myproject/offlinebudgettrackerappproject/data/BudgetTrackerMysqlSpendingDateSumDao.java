package com.myproject.offlinebudgettrackerappproject.data;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlSpendingDto;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingSumCallback;
import com.myproject.offlinebudgettrackerappproject.util.SharedPreferencesManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class BudgetTrackerMysqlSpendingDateSumDao {

    private final Context context;

    public BudgetTrackerMysqlSpendingDateSumDao(Context context) {
        this.context = context.getApplicationContext();
    }


    public void getSearchDateSum(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto, MysqlSpendingSumCallback callback) {

        String currencyCode = budgetTrackerMysqlSpendingDto.getCurrencyCode();
        String dateFrom = budgetTrackerMysqlSpendingDto.getDateFrom();
        String dateTo = budgetTrackerMysqlSpendingDto.getDateTo();

        Log.d("TAG", "getSearchDateList: " +  currencyCode + " " +dateFrom + " " + dateTo);

        try {
            Properties properties = new Properties();
            InputStream inputStream = context.getAssets().open("server_config.properties");
            properties.load(inputStream);
            String serverUrl = properties.getProperty("server_url");
            String phpSelectFile = properties.getProperty("spending_store_date_sum_php_file");
            String selectUrl = serverUrl + phpSelectFile;
            Log.d("select_url", selectUrl);

            // Create a map of parameters to send in the POST request
            final Map<String, String> params = new HashMap<>();
            params.put("email", SharedPreferencesManager.getUserEmail(context));
            params.put("currency_code", budgetTrackerMysqlSpendingDto.getCurrencyCode().toString());
            params.put("date_from", budgetTrackerMysqlSpendingDto.getDateFrom().toString());
            params.put("date_to", budgetTrackerMysqlSpendingDto.getDateTo().toString());

            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST, selectUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("JSONResponse", response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.optString("success", "");

                                if (success.equals("1")) {
                                    JSONArray jsonArray = jsonObject.optJSONArray("result");
                                    if (jsonArray != null) {
                                        double totalSpending = 0.0;
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject spendingObject = jsonArray.getJSONObject(i);
                                            String spendingCalcSum = spendingObject.getString("total_sum");
                                            double spendingCalcSumDouble = Double.parseDouble(spendingCalcSum);
                                            totalSpending += spendingCalcSumDouble;
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
                                e.printStackTrace();
                                callback.onError("Error parsing JSON");
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("VolleyError", error.toString());
                            callback.onError("Unable to fetch data: " + error.getMessage());
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);

        } catch (IOException e) {
            e.printStackTrace();
            callback.onError("Error loading server configuration");
        }
    }

}
