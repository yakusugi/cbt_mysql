package com.myproject.offlinebudgettrackerappproject.util;

import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlIncomeDto;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlSpendingDto;

import java.util.List;

public interface MysqlIncomeListCallback {
    void onSuccess(List<BudgetTrackerMysqlIncomeDto> incomeList);
    void onError(String error);
}
