package com.myproject.offlinebudgettrackerappproject.util;

public interface StoreNameAverageCallback {
    void onSuccess(Double average);
    void onError(String errorMessage);
}
