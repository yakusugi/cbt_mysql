package com.myproject.offlinebudgettrackerappproject.util;

import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlBankDto;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlSpendingDto;

import java.util.List;

public interface MysqlBankInsertCallback {
    void onSuccess(List<BudgetTrackerMysqlBankDto> spendingInsertList);
    void onError(String error);
}
