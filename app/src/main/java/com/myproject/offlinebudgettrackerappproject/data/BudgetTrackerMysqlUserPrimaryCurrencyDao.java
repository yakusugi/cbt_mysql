package com.myproject.offlinebudgettrackerappproject.data;

import android.content.Context;

import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlSpendingDto;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlUserPrimaryCurrency;
import com.myproject.offlinebudgettrackerappproject.util.SharedPreferencesManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BudgetTrackerMysqlUserPrimaryCurrencyDao extends BaseUserPrimaryCurrencyDao {

    private List<BudgetTrackerMysqlSpendingDto> radioSearchStoreNameList;
    private List<BudgetTrackerMysqlSpendingDto> radioSearchProductNameList;
    private List<BudgetTrackerMysqlSpendingDto> radioSearchProductTypeList;

    Double searchStoreSum;
    Double searchProductNameSum;
    Double searchProductTypeSum;

    public BudgetTrackerMysqlUserPrimaryCurrencyDao(Context context) {
        super(context);
    }

    public String getUserPrimaryCurrency(BudgetTrackerMysqlUserPrimaryCurrency dto) {
        try {
            String serverUrl = loadServerConfig("server_url");
            String phpFile = loadServerConfig("user_currency_select_php_file");
            String endpoint = serverUrl + phpFile;

            Map<String, String> params = new HashMap<>();
            params.put("email", SharedPreferencesManager.getUserEmail(context));

//            sendRequest(endpoint, params, callback);
            return sendRequestAndGetResult(endpoint, params);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
