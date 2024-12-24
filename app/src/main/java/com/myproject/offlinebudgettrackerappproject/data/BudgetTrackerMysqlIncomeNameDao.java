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
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlBankDto;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlIncomeDto;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlSpendingDto;
import com.myproject.offlinebudgettrackerappproject.util.MysqlBankListCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlIncomeListCallback;
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

public class BudgetTrackerMysqlIncomeNameDao {

    private final Context context;
    private List<BudgetTrackerMysqlSpendingDto> radioSearchStoreNameList;
    private List<BudgetTrackerMysqlSpendingDto> radioSearchProductNameList;
    private List<BudgetTrackerMysqlSpendingDto> radioSearchProductTypeList;

    Double searchStoreSum;
    Double searchProductNameSum;
    Double searchProductTypeSum;

    public BudgetTrackerMysqlIncomeNameDao(Context context) {
        this.context = context.getApplicationContext();
    }

    /**
     * Retrieve income search data
     * @param budgetTrackerMysqlIncomeDto
     * @param callback
     */
    public void getSearchIncomeNameList(BudgetTrackerMysqlIncomeDto budgetTrackerMysqlIncomeDto, MysqlIncomeListCallback callback) {
        try {
            Properties properties = new Properties();
            InputStream inputStream = context.getAssets().open("server_config.properties");
            properties.load(inputStream);
            String serverUrl = properties.getProperty("server_url");
            String phpSelectFile = properties.getProperty("income_name_search_php_file");
            String selectUrl = serverUrl + phpSelectFile;
            Log.d("select_url", selectUrl);

            // Create a map of parameters to send in the POST request
            final Map<String, String> params = new HashMap<>();
            params.put("email", SharedPreferencesManager.getUserEmail(context));
            params.put("income_name", budgetTrackerMysqlIncomeDto.getIncomeName());
            Log.d("income_name", "getSearchIncomeNameList: " + budgetTrackerMysqlIncomeDto.getIncomeName());

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
                                        List<BudgetTrackerMysqlIncomeDto> incomeList = new ArrayList<>();
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject jsonObjectItem = jsonArray.getJSONObject(i);
                                            String dateStr = jsonObjectItem.getString("income_date");
                                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                            Date date = null;
                                            try {
                                                date = dateFormat.parse(dateStr);
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }
                                            String incomeCategory = jsonObjectItem.getString("income_category");
                                            String incomeName = jsonObjectItem.getString("income_name");
                                            String incomeString = jsonObjectItem.getString("income");
                                            String note = jsonObjectItem.getString("note");
                                            String currencyCode = jsonObjectItem.getString("currency_code");
                                            double income = incomeString.isEmpty() ? 0.0 : Double.parseDouble(incomeString);

                                            BudgetTrackerMysqlIncomeDto incomeDto = new BudgetTrackerMysqlIncomeDto(
                                                    date,
                                                    incomeCategory,
                                                    incomeName,
                                                    income,
                                                    note,
                                                    currencyCode
                                            );
                                            incomeList.add(incomeDto);
                                        }
                                        callback.onSuccess(incomeList);
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
