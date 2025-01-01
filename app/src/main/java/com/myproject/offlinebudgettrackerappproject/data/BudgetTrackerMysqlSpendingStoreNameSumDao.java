package com.myproject.offlinebudgettrackerappproject.data;

import android.content.Context;

import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlSpendingDto;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingSumCallback;
import com.myproject.offlinebudgettrackerappproject.util.SharedPreferencesManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BudgetTrackerMysqlSpendingStoreNameSumDao extends BaseSpendingSumDao {

    public BudgetTrackerMysqlSpendingStoreNameSumDao(Context context) {
        super(context);
    }

    public void getSearchStoreNameSum(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto, MysqlSpendingSumCallback callback) {
        try {
            String serverUrl = loadServerConfig("server_url");
            String phpSelectFile = loadServerConfig("spending_store_sum_php_file");
            String selectUrl = serverUrl + phpSelectFile;

            Map<String, String> params = new HashMap<>();
            params.put("email", SharedPreferencesManager.getUserEmail(context));
            params.put("store_name", budgetTrackerMysqlSpendingDto.getStoreName());
            params.put("date_from", budgetTrackerMysqlSpendingDto.getDateFrom());
            params.put("date_to", budgetTrackerMysqlSpendingDto.getDateTo());

            sendSumRequest(selectUrl, params, callback);

        } catch (IOException e) {
            e.printStackTrace();
            callback.onError("Error loading server configuration");
        }
    }
}