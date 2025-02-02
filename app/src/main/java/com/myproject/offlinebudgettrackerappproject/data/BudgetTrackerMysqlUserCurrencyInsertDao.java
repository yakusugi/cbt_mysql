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
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlUserCurrencyDto;
import com.myproject.offlinebudgettrackerappproject.util.DateUtil;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingListCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlUserCurrencyCallback;
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

public class BudgetTrackerMysqlUserCurrencyInsertDao {
    private final Context context;

    public BudgetTrackerMysqlUserCurrencyInsertDao(Context context) {
        this.context = context.getApplicationContext();
    }

    private DateUtil dateUtil;

    public void insertIntoUserCurrency(BudgetTrackerMysqlUserCurrencyDto budgetTrackerMysqlUserCurrencyDto, MysqlUserCurrencyCallback callback) {
        try {
            Properties properties = new Properties();
            InputStream inputStream = context.getAssets().open("server_config.properties");
            properties.load(inputStream);
            String serverUrl = properties.getProperty("server_url");
            String phpInsertFile = properties.getProperty("user_currency_insert_php_file");
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
                                    List<BudgetTrackerMysqlUserCurrencyDto> userCurenncyList = parseSpendingList(jsonObject);
                                    callback.onSuccess(userCurenncyList);
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
                    //todo debug
                    String email = SharedPreferencesManager.getUserEmail(context);
                    if (email == null || email.isEmpty()) {
                        Log.e("API_ERROR", "Email is NULL or EMPTY!");
                    } else {
                        Log.d("API_SUCCESS", "Email found: " + email);
                    }
                    params.put("email", SharedPreferencesManager.getUserEmail(context).toString());
                    params.put("primary_currency", budgetTrackerMysqlUserCurrencyDto.getCurrencyCode());
                    return params;
                }
            };

            com.android.volley.RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
            Log.d("API_REQUEST", "Request added to queue");
        } catch (IOException e) {
            e.printStackTrace();
            callback.onError("IOException: " + e.getMessage());
        }
    }

    // Method to parse the spending list from the JSON response
    private List<BudgetTrackerMysqlUserCurrencyDto> parseSpendingList(JSONObject jsonObject) throws JSONException, ParseException {
        List<BudgetTrackerMysqlUserCurrencyDto> userCurrencyList = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray("spending_data");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject spendingObject = jsonArray.getJSONObject(i);
            BudgetTrackerMysqlUserCurrencyDto userCurrencyDto = new BudgetTrackerMysqlUserCurrencyDto();
            dateUtil = new DateUtil();
            userCurrencyDto.setCurrencyCode(spendingObject.getString(""));
            userCurrencyList.add(userCurrencyDto);
        }
        return userCurrencyList;
    }
}
