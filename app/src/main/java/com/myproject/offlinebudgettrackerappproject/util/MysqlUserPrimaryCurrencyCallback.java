package com.myproject.offlinebudgettrackerappproject.util;

import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlSpendingDto;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlUserPrimaryCurrency;

import java.util.List;

public interface MysqlUserPrimaryCurrencyCallback {
    void onSuccess(BudgetTrackerMysqlUserPrimaryCurrency budgetTrackerMysqlUserPrimaryCurrency);
    void onError(String error);
}
