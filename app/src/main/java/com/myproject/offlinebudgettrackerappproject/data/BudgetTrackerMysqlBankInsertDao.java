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
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlBankDto;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlSpendingDto;
import com.myproject.offlinebudgettrackerappproject.util.DateUtil;
import com.myproject.offlinebudgettrackerappproject.util.MysqlBankInsertCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlBankListCallback;
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

public class BudgetTrackerMysqlBankInsertDao {
    private final Context context;

    public BudgetTrackerMysqlBankInsertDao(Context context) {
        this.context = context.getApplicationContext();
    }

    private DateUtil dateUtil;

    public void insertIntoBank(BudgetTrackerMysqlBankDto budgetTrackerMysqlBankDto, MysqlBankInsertCallback callback) {
        try {
            Properties properties = new Properties();
            InputStream inputStream = context.getAssets().open("server_config.properties");
            properties.load(inputStream);
            String serverUrl = properties.getProperty("server_url");
            String phpInsertFile = properties.getProperty("bank_insert_php_file");
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
                                    Toast.makeText(context, "Bank data has been inserted", Toast.LENGTH_SHORT).show();
                                    // Call the callback's onSuccess method if insertion is successful
                                    List<BudgetTrackerMysqlBankDto> bankList = parseBankList(jsonObject);
                                    callback.onSuccess(bankList);
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

                    params.put("email", SharedPreferencesManager.getUserEmail(context).toString());
                    params.put("bank_name", budgetTrackerMysqlBankDto.getBankName());
                    params.put("balance", budgetTrackerMysqlBankDto.getBankBalance().toString());
                    params.put("note", budgetTrackerMysqlBankDto.getNotes());
                    params.put("currency_code", budgetTrackerMysqlBankDto.getBankCurrencyCode());

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

    // Method to parse the bank list from the JSON response
    private List<BudgetTrackerMysqlBankDto> parseBankList(JSONObject jsonObject) throws JSONException, ParseException {
        List<BudgetTrackerMysqlBankDto> spendingList = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray("spending_data");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject spendingObject = jsonArray.getJSONObject(i);
            BudgetTrackerMysqlBankDto budgetTrackerMysqlBankDto = new BudgetTrackerMysqlBankDto();
            dateUtil = new DateUtil();
            budgetTrackerMysqlBankDto.setBankName(spendingObject.getString("bank_name"));
            budgetTrackerMysqlBankDto.setBankBalance(Double.parseDouble(spendingObject.getString("balance")));
            budgetTrackerMysqlBankDto.setNotes(spendingObject.getString("note"));
            budgetTrackerMysqlBankDto.setBankCurrencyCode(spendingObject.getString("currency_code"));
            spendingList.add(budgetTrackerMysqlBankDto);
        }
        return spendingList;
    }
}
