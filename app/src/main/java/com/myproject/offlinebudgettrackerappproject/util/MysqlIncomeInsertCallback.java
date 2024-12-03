package com.myproject.offlinebudgettrackerappproject.util;

import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlIncomeDto;

import java.util.List;

public interface MysqlIncomeInsertCallback {
    void onSuccess(List<BudgetTrackerMysqlIncomeDto> incomeInsertList);
    void onError(String error);
}
