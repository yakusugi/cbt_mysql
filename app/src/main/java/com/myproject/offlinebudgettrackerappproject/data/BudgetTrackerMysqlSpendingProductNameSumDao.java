package com.myproject.offlinebudgettrackerappproject.data;

import android.content.Context;

import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlSpendingDto;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingSumCallback;
import com.myproject.offlinebudgettrackerappproject.util.SharedPreferencesManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BudgetTrackerMysqlSpendingProductNameSumDao extends BaseSpendingSumDao {

    public BudgetTrackerMysqlSpendingProductNameSumDao(Context context) {
        super(context);
    }

    public void getSearchProductNameSum(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto, MysqlSpendingSumCallback callback) {
        try {
            String serverUrl = loadServerConfig("server_url");
            String phpSelectFile = loadServerConfig("spending_product_name_sum_php_file");
            String selectUrl = serverUrl + phpSelectFile;

            Map<String, String> params = new HashMap<>();
            params.put("email", SharedPreferencesManager.getUserEmail(context));
            params.put("product_name", budgetTrackerMysqlSpendingDto.getProductName());
            params.put("date_from", budgetTrackerMysqlSpendingDto.getDateFrom());
            params.put("date_to", budgetTrackerMysqlSpendingDto.getDateTo());

            sendSumRequest(selectUrl, params, callback);

        } catch (IOException e) {
            e.printStackTrace();
            callback.onError("Error loading server configuration");
        }
    }
}
