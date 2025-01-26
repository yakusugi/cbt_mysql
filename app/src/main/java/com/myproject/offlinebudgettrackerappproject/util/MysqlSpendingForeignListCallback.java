package com.myproject.offlinebudgettrackerappproject.util;

import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlForeignSpendingDto;

import java.util.List;

public interface MysqlSpendingForeignListCallback {
    void onSuccess(List<BudgetTrackerMysqlForeignSpendingDto> spendingList);
    void onError(String error);
}
