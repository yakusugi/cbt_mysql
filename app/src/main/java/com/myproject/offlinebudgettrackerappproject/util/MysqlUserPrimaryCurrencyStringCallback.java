package com.myproject.offlinebudgettrackerappproject.util;

import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlUserPrimaryCurrency;

public interface MysqlUserPrimaryCurrencyStringCallback {
    void onSuccess(String budgetTrackerMysqlUserPrimaryCurrency);
    void onError(String error);
}
