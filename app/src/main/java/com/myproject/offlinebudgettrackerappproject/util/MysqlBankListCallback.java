package com.myproject.offlinebudgettrackerappproject.util;

import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlBankDto;

import java.util.List;

public interface MysqlBankListCallback {
    void onSuccess(List<BudgetTrackerMysqlBankDto> bankgList);
    void onError(String error);
}
