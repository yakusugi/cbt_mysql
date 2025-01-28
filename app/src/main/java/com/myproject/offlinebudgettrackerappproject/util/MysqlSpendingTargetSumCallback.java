package com.myproject.offlinebudgettrackerappproject.util;

public interface MysqlSpendingTargetSumCallback {
    void onSuccess(Double spendingSum);
    void onError(String error);
}
