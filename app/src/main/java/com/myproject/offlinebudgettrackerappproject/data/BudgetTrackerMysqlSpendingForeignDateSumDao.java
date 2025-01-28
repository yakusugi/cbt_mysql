package com.myproject.offlinebudgettrackerappproject.data;

import android.content.Context;

import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlForeignSpendingDto;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlSpendingDto;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingForeignListCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingForeignSumCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingListCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingTargetSumCallback;
import com.myproject.offlinebudgettrackerappproject.util.SharedPreferencesManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BudgetTrackerMysqlSpendingForeignDateSumDao extends BaseForeignSumDao {

    public BudgetTrackerMysqlSpendingForeignDateSumDao(Context context) {
        super(context);
    }

    public void getSearchForeignDateSum(BudgetTrackerMysqlForeignSpendingDto dto, MysqlSpendingForeignSumCallback callback) {
        try {
            String serverUrl = loadServerConfig("server_url");
            String phpFile = loadServerConfig("foreign_date_sum_php_file");
            String endpoint = serverUrl + phpFile;

            Map<String, String> params = new HashMap<>();
            params.put("email", SharedPreferencesManager.getUserEmail(context));
            params.put("currency_code", dto.getCurrencyCode());
            params.put("date_from", dto.getDateFrom());
            params.put("date_to", dto.getDateTo());

            sendForeignRequest(endpoint, params, callback);
        } catch (IOException e) {
            e.printStackTrace();
            callback.onError("Error loading server configuration");
        }
    }



}

