package com.myproject.offlinebudgettrackerappproject.util;

public interface LoginCallback {
    void onSuccess();
    void onFailure(String errorMessage);
}
