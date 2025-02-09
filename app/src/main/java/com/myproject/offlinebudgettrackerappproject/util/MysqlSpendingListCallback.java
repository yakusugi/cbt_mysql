package com.myproject.offlinebudgettrackerappproject.util;

import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlSpendingDto;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlUserPrimaryCurrency;

import java.util.List;

public interface MysqlSpendingListCallback {
    void onSuccess(List<BudgetTrackerMysqlSpendingDto> spendingList);

    void onError(String error);
}
