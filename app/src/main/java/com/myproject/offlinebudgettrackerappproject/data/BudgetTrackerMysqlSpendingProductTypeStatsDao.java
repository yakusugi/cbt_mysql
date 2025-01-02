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

public class BudgetTrackerMysqlSpendingProductTypeStatsDao extends BaseSpendingStatsDao{

    public BudgetTrackerMysqlSpendingProductTypeStatsDao(Context context) {
        super(context);
    }

    public void getSearchProductTypeStatsList(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto, MysqlSpendingListCallback callback) {

        try {
            String serverUrl = loadServerConfig("server_url");
            String phpFile = loadServerConfig("product_type_stats_search_php_file");
            String endpoint = serverUrl + phpFile;

            Map<String, String> params = new HashMap<>();
            params.put("email", SharedPreferencesManager.getUserEmail(context));
            params.put("product_type", budgetTrackerMysqlSpendingDto.getProductType());
            params.put("currency_code", budgetTrackerMysqlSpendingDto.getCurrencyCode());
            params.put("date_from", budgetTrackerMysqlSpendingDto.getDateFrom());
            params.put("date_to", budgetTrackerMysqlSpendingDto.getDateTo());

            sendRequest(endpoint, params, callback);
        } catch (IOException e) {
            e.printStackTrace();
            callback.onError("Error loading server configuration");
        }
    }
}
