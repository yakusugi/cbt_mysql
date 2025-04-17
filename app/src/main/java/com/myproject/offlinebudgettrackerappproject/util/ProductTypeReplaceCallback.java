package com.myproject.offlinebudgettrackerappproject.util;

public interface ProductTypeReplaceCallback {
    void onSuccess(int affectedRows);
    void onError(String errorMessage);
}
