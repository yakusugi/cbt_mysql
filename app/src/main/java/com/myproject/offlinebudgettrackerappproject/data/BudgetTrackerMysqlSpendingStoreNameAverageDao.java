package com.myproject.offlinebudgettrackerappproject.data;

import android.content.Context;

import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlSpendingDto;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingSumCallback;
import com.myproject.offlinebudgettrackerappproject.util.SharedPreferencesManager;
import com.myproject.offlinebudgettrackerappproject.util.StoreNameAverageCallback;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BudgetTrackerMysqlSpendingStoreNameAverageDao extends BaseSpendingSumDao {

    public BudgetTrackerMysqlSpendingStoreNameAverageDao(Context context) {
        super(context);
    }

    public void getStoreNameAverage(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto, StoreNameAverageCallback callback) {
        try {
            String serverUrl = loadServerConfig("server_url");
            String phpSelectFile = loadServerConfig("spending_store_average_php_file");
            String selectUrl = serverUrl + phpSelectFile;

            Map<String, String> params = new HashMap<>();
            params.put("email", SharedPreferencesManager.getUserEmail(context));
            params.put("store_name", budgetTrackerMysqlSpendingDto.getStoreName());
            params.put("date_from", budgetTrackerMysqlSpendingDto.getDateFrom());
            params.put("date_to", budgetTrackerMysqlSpendingDto.getDateTo());

            String currencyCode = budgetTrackerMysqlSpendingDto.getCurrencyCode();
            if (currencyCode != null) {
                params.put("currency_code", currencyCode);
            }

            sendAverageRequest(selectUrl, params, callback);

        } catch (IOException e) {
            e.printStackTrace();
            callback.onError("Error loading server configuration");
        }
    }
}