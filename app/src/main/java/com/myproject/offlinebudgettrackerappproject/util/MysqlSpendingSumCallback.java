package com.myproject.offlinebudgettrackerappproject.util;

import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlSpendingDto;

import java.util.List;

public interface MysqlSpendingSumCallback {
    void onSuccess(Double spendingSum);
    void onError(String error);
}
