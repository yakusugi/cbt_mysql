package com.myproject.offlinebudgettrackerappproject.data;

import android.content.Context;

import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlSpendingDto;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingListCallback;
import com.myproject.offlinebudgettrackerappproject.util.SharedPreferencesManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BudgetTrackerMysqlSpendingStoreNameAverageListDao extends BaseSpendingDao {

    public BudgetTrackerMysqlSpendingStoreNameAverageListDao(Context context) {
        super(context);
    }

    public void getSearchDateList(BudgetTrackerMysqlSpendingDto dto, MysqlSpendingListCallback callback) {
        try {
            String serverUrl = loadServerConfig("server_url");
            String phpFile = loadServerConfig("spending_store_average_php_file");
            String endpoint = serverUrl + phpFile;

            Map<String, String> params = new HashMap<>();
            params.put("email", SharedPreferencesManager.getUserEmail(context));
            params.put("currency_code", dto.getCurrencyCode());
            params.put("store_name", dto.getStoreName());
            params.put("date_from", dto.getDateFrom());
            params.put("date_to", dto.getDateTo());

            sendRequest(endpoint, params, callback);
        } catch (IOException e) {
            e.printStackTrace();
            callback.onError("Error loading server configuration");
        }
    }
}

