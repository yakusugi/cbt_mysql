package com.myproject.offlinebudgettrackerappproject.util;

import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlSpendingDto;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlUserCurrencyDto;

import java.util.List;

public interface MysqlUserCurrencyCallback {
    void onSuccess(List<BudgetTrackerMysqlUserCurrencyDto> userCurrencyList);
    void onError(String error);
}
