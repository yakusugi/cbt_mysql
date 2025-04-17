package com.myproject.offlinebudgettrackerappproject.data;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlSpendingDto;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingListCallback;
import com.myproject.offlinebudgettrackerappproject.util.SharedPreferencesManager;
import com.myproject.offlinebudgettrackerappproject.util.StoreNameReplaceCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BudgetTrackerMysqlSpendingStoreNameReplaceDao extends BaseSpendingDao {

    private List<BudgetTrackerMysqlSpendingDto> radioSearchStoreNameList;
    private List<BudgetTrackerMysqlSpendingDto> radioSearchProductNameList;
    private List<BudgetTrackerMysqlSpendingDto> radioSearchProductTypeList;

    Double searchStoreSum;
    Double searchProductNameSum;
    Double searchProductTypeSum;

    public BudgetTrackerMysqlSpendingStoreNameReplaceDao(Context context) {
        super(context);
    }

    public void replaceStoreName(String storeNameFrom, String storeNameTo, StoreNameReplaceCallback callback) {
        try {
            String serverUrl = loadServerConfig("server_url");
            String phpFile = loadServerConfig("spending_store_replace_php_file");
            String endpoint = serverUrl + phpFile;

            Map<String, String> params = new HashMap<>();
            params.put("email", SharedPreferencesManager.getUserEmail(context));
            params.put("store_name_from", storeNameFrom);
            params.put("store_name_to", storeNameTo);

            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST, endpoint,
                    response -> {
                        try {
                            JSONObject json = new JSONObject(response);
                            if (json.optInt("success", 0) == 1) {
                                int affected = json.optInt("affected_rows", 0);
                                callback.onSuccess(affected);
                            } else {
                                String error = json.optString("error", "Unknown error");
                                callback.onError(error);
                            }
                        } catch (JSONException e) {
                            callback.onError("JSON error: " + e.getMessage());
                        }
                    },
                    error -> callback.onError("Volley error: " + error.getMessage())
            ) {
                @Override
                protected Map<String, String> getParams() {
                    return params;
                }
            };

            RequestQueue queue = Volley.newRequestQueue(context);
            queue.add(stringRequest);

        } catch (IOException e) {
            callback.onError("IO error: " + e.getMessage());
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


    public Double getSearchStoreSum(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto) {

        String storeName = budgetTrackerMysqlSpendingDto.getStoreName();
        String dateFrom = budgetTrackerMysqlSpendingDto.getDateFrom();
        String dateTo = budgetTrackerMysqlSpendingDto.getDateTo();

        Log.d("TAG", "getSearchStoreNameList: " + storeName + " " + dateFrom + " " + dateTo);

        return searchStoreSum;
    }

    public Double getSearchProductTypeSum(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto) {

        String storeName = budgetTrackerMysqlSpendingDto.getStoreName();
        String dateFrom = budgetTrackerMysqlSpendingDto.getDateFrom();
        String dateTo = budgetTrackerMysqlSpendingDto.getDateTo();

        Log.d("TAG", "getSearchStoreNameList: " + storeName + " " + dateFrom + " " + dateTo);

        return searchProductTypeSum;
    }


}
