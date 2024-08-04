package com.myproject.offlinebudgettrackerappproject.data;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.myproject.offlinebudgettrackerappproject.model.BudgetTracker;
import com.myproject.offlinebudgettrackerappproject.model.BudgetTrackerMysqlSpendingCacheEntity;
import com.myproject.offlinebudgettrackerappproject.service.MySQLApiService;
import com.myproject.offlinebudgettrackerappproject.util.BudgetTrackerMysqlCacheDatabase;
import com.myproject.offlinebudgettrackerappproject.util.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BudgetTrackerMysqlSpendingCacheRepository {

    private BudgetTrackerMysqlSpendingCacheDao budgetTrackerMysqlSpendingCacheDao;
    private MySQLApiService mySQLApiService;
    private LiveData<List<BudgetTracker>> allItems;

    public BudgetTrackerMysqlSpendingCacheRepository(Application application) {
        BudgetTrackerMysqlCacheDatabase db = Room.databaseBuilder(application, BudgetTrackerMysqlCacheDatabase.class, "item_database")
                .build();
        budgetTrackerMysqlSpendingCacheDao = db.budgetTrackerMysqlSpendingCacheDao();

        Retrofit retrofit = RetrofitClient.getClient("https://your-api-url.com/");
        mySQLApiService = retrofit.create(MySQLApiService.class);

        allItems = budgetTrackerMysqlSpendingCacheDao.getAllBudgetTrackerMysqlList();
    }

    public LiveData<List<BudgetTracker>> getAllItems() {
        return allItems;
    }

    public void refreshBudgetDataFromBackend() {
        mySQLApiService.getAllItems().enqueue(new Callback<List<BudgetTrackerMysqlSpendingCacheEntity>>() {
            @Override
            public void onResponse(Call<List<BudgetTrackerMysqlSpendingCacheEntity>> call, Response<List<BudgetTrackerMysqlSpendingCacheEntity>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<BudgetTrackerMysqlSpendingCacheEntity> budgetTrackerMysqlSpendingCacheEntities = response.body();
                        // Clear the local database and insert the fetched items
                        new Thread(() -> {
                            budgetTrackerMysqlSpendingCacheDao.deleteAll();
                            budgetTrackerMysqlSpendingCacheDao.insertAll(budgetTrackerMysqlSpendingCacheEntities);
                        }).start();
                    }
            }

            @Override
            public void onFailure(Call<List<BudgetTrackerMysqlSpendingCacheEntity>> call, Throwable throwable) {

            }


        });
    }

    public void insert(List<BudgetTrackerMysqlSpendingCacheEntity> budgetTrackerMysqlSpendingCacheEntity) {
        new Thread(() -> {
            budgetTrackerMysqlSpendingCacheDao.insertAll(budgetTrackerMysqlSpendingCacheEntity);
            mySQLApiService.insert(budgetTrackerMysqlSpendingCacheEntity).enqueue(new Callback<Void>() {


                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {

                }

                @Override
                public void onFailure(Call<Void> call, Throwable throwable) {

                }
            });
        }).start();
    }




}
