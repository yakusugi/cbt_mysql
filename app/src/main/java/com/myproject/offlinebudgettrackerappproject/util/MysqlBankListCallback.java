package com.myproject.offlinebudgettrackerappproject.util;

import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlBankDto;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlSpendingDto;

import java.util.List;

public interface MysqlBankListCallback {
    void onSuccess(List<BudgetTrackerMysqlBankDto> spendingList);
    void onError(String error);
}
