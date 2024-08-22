package com.myproject.offlinebudgettrackerappproject.util;

import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlSpendingDto;

import java.util.List;

public interface MysqlSpendingInsertCallback {
    void onSuccess(List<BudgetTrackerMysqlSpendingDto> spendingInsertList);
    void onError(String error);
}
