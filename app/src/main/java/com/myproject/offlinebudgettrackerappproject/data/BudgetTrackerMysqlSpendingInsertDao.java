package com.myproject.offlinebudgettrackerappproject.data;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlSpendingDto;
import com.myproject.offlinebudgettrackerappproject.util.DateUtil;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class BudgetTrackerMysqlSpendingInsertDao {
    private final Context context;

    public BudgetTrackerMysqlSpendingInsertDao(Context context) {
        this.context = context.getApplicationContext();
    }

    private DateUtil dateUtil;

    public void insertIntoSpending(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto, MysqlSpendingListCallback callback) {
        try {
            Properties properties = new Properties();
            InputStream inputStream = context.getAssets().open("server_config.properties");
            properties.load(inputStream);
            String serverUrl = properties.getProperty("server_url");
            String phpInsertFile = properties.getProperty("spending_date_insert_php_file");
            String insertUrl = serverUrl + phpInsertFile;
            Log.d("insert_url", insertUrl);

            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST, insertUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");

                                if (Integer.parseInt(success) > 0) {
                                    Toast.makeText(context, "User data has been inserted", Toast.LENGTH_SHORT).show();
                                    // Call the callback's onSuccess method if insertion is successful
                                    List<BudgetTrackerMysqlSpendingDto> spendingList = parseSpendingList(jsonObject);
                                    callback.onSuccess(spendingList);
                                } else {
                                    // Handle the case where success is not greater than 0
                                    callback.onError("Failed to insert data");
                                }

                            } catch (JSONException e) {
                                Log.e("JSONException", e.toString());
                                e.printStackTrace();
                                callback.onError("JSON parsing error: " + e.getMessage());
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error instanceof ServerError) {
                                com.android.volley.NetworkResponse networkResponse = error.networkResponse;
                                if (networkResponse != null) {
                                    Log.e("Volley", "Error. HTTP Status Code: " + networkResponse.statusCode);
                                }
                            }
                            Toast.makeText(context, "Unable to insert data: " + error, Toast.LENGTH_SHORT).show();
                            Log.e("VolleyError", error.toString());
                            callback.onError("Volley error: " + error.toString());
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();

                    // Format the date to "yyyy-MM-dd"
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String formattedDate = dateFormat.format(budgetTrackerMysqlSpendingDto.getDate());

                    params.put("email", SharedPreferencesManager.getUserEmail(context).toString());
                    params.put("date", formattedDate);
                    params.put("store_name", budgetTrackerMysqlSpendingDto.getStoreName());
                    params.put("product_name", budgetTrackerMysqlSpendingDto.getProductName());
                    params.put("product_type", budgetTrackerMysqlSpendingDto.getProductType());
                    params.put("vat_rate", budgetTrackerMysqlSpendingDto.getTaxRate().toString());
                    params.put("price", budgetTrackerMysqlSpendingDto.getPrice().toString());
                    params.put("note", budgetTrackerMysqlSpendingDto.getNotes());
                    params.put("currency_code", budgetTrackerMysqlSpendingDto.getCurrencyCode());
                    params.put("quantity", String.valueOf(budgetTrackerMysqlSpendingDto.getQuantity()));

                    return params;
                }
            };

            com.android.volley.RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
        } catch (IOException e) {
            e.printStackTrace();
            callback.onError("IOException: " + e.getMessage());
        }
    }

    // Method to parse the spending list from the JSON response
    private List<BudgetTrackerMysqlSpendingDto> parseSpendingList(JSONObject jsonObject) throws JSONException, ParseException {
        List<BudgetTrackerMysqlSpendingDto> spendingList = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray("spending_data");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject spendingObject = jsonArray.getJSONObject(i);
            BudgetTrackerMysqlSpendingDto spendingDto = new BudgetTrackerMysqlSpendingDto();
            dateUtil = new DateUtil();
            spendingDto.setDate(DateUtil.stringToDate(spendingObject.getString("date"), "yyyy-MM-dd"));
            spendingDto.setStoreName(spendingObject.getString("store_name"));
            spendingDto.setProductName(spendingObject.getString("product_name"));
            spendingDto.setProductType(spendingObject.getString("product_type"));
            spendingDto.setTaxRate(Double.parseDouble(spendingObject.getString("vat_rate")));
            spendingDto.setPrice(Double.parseDouble(spendingObject.getString("price")));
            spendingDto.setNotes(spendingObject.getString("note"));
            spendingDto.setCurrencyCode(spendingObject.getString("currency_code"));
            spendingDto.setQuantity(Integer.parseInt(spendingObject.getString("quantity")));
            spendingList.add(spendingDto);
        }
        return spendingList;
    }
}
