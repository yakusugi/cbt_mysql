package com.myproject.offlinebudgettrackerappproject.service;

import android.content.ClipData;

import com.myproject.offlinebudgettrackerappproject.model.BudgetTrackerMysqlSpendingCacheEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MySQLApiService {
    @GET("your_endpoint_here")
    Call<List<BudgetTrackerMysqlSpendingCacheEntity>> getAllItems();

    @POST("items")
    Call<Void> insert(@Body List<BudgetTrackerMysqlSpendingCacheEntity> budgetTrackerMysqlSpendingCacheEntity);

}
