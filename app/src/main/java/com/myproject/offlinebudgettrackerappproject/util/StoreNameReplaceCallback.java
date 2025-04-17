package com.myproject.offlinebudgettrackerappproject.util;

import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlSpendingDto;

import java.util.List;

public interface StoreNameReplaceCallback {
    void onSuccess(int affectedRows);
    void onError(String errorMessage);
}
