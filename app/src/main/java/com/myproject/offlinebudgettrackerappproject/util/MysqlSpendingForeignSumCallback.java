package com.myproject.offlinebudgettrackerappproject.util;

public interface MysqlSpendingForeignSumCallback {
    void onSuccess(Double spendingSum);
    void onError(String error);
}
