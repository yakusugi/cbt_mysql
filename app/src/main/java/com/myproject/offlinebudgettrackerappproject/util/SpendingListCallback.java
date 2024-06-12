package com.myproject.offlinebudgettrackerappproject.util;

import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlSpendingDto;

import java.util.List;

public interface SpendingListCallback {
    void onSuccess(List<BudgetTrackerMysqlSpendingDto> spendingList);
    void onError(String error);
}
