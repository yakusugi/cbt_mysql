package com.myproject.offlinebudgettrackerappproject.util;

public interface ProductNameReplaceCallback {
    void onSuccess(int affectedRows);
    void onError(String errorMessage);
}
