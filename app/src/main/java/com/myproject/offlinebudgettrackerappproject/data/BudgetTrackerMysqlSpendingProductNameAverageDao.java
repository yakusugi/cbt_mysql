package com.myproject.offlinebudgettrackerappproject.data;

import android.content.Context;

import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlSpendingDto;
import com.myproject.offlinebudgettrackerappproject.util.SharedPreferencesManager;
import com.myproject.offlinebudgettrackerappproject.util.StoreNameAverageCallback;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BudgetTrackerMysqlSpendingProductNameAverageDao extends BaseSpendingSumDao {

    public BudgetTrackerMysqlSpendingProductNameAverageDao(Context context) {
        super(context);
    }

    public void getProductNameAverage(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto, StoreNameAverageCallback callback) {
        try {
            String serverUrl = loadServerConfig("server_url");
            String phpSelectFile = loadServerConfig("spending_product_name_average_php_file");
            String selectUrl = serverUrl + phpSelectFile;

            Map<String, String> params = new HashMap<>();
            params.put("email", SharedPreferencesManager.getUserEmail(context));
            params.put("product_name", budgetTrackerMysqlSpendingDto.getProductName());
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