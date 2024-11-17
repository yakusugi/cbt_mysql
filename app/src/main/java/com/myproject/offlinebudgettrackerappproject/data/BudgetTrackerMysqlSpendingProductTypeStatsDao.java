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
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingListCallback;
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

public class BudgetTrackerMysqlSpendingProductTypeStatsDao {

    private final Context context;

    public BudgetTrackerMysqlSpendingProductTypeStatsDao(Context context) {
        this.context = context.getApplicationContext();
    }

    public void getSearchProductTypeStatsList(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto, MysqlSpendingListCallback callback) {

        String productType = budgetTrackerMysqlSpendingDto.getProductType();
        String currencyCode = budgetTrackerMysqlSpendingDto.getCurrencyCode();
        String dateFrom = budgetTrackerMysqlSpendingDto.getDateFrom();
        String dateTo = budgetTrackerMysqlSpendingDto.getDateTo();

        Log.d("TAG", "getSearchStoreNameList: " + productType + " " + currencyCode  + " " + dateFrom + " " + dateTo);

        try {
            Properties properties = new Properties();
            InputStream inputStream = context.getAssets().open("server_config.properties");
            properties.load(inputStream);
            String serverUrl = properties.getProperty("server_url");
            String phpSelectFile = properties.getProperty("bank_name_product_type_stats_search_php_file");
            String selectUrl = serverUrl + phpSelectFile;
            Log.d("select_url", selectUrl);

            // Create a map of parameters to send in the POST request
            final Map<String, String> params = new HashMap<>();
            params.put("email", SharedPreferencesManager.getUserEmail(context));
            params.put("product_type", productType);
            params.put("currency_code", currencyCode);
            params.put("date_from", dateFrom);
            params.put("date_to", dateTo);

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
                                        List<BudgetTrackerMysqlSpendingDto> spendingList = new ArrayList<>();
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject jsonObjectItem = jsonArray.getJSONObject(i);
                                            String dateStr = jsonObjectItem.getString("spending_date");
                                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
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
                                        callback.onSuccess(spendingList);
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

    public Date dateTypeReturner(String dateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
