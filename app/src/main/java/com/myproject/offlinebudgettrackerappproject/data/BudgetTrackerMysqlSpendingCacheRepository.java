package com.myproject.offlinebudgettrackerappproject.data;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.myproject.offlinebudgettrackerappproject.model.BudgetTracker;
import com.myproject.offlinebudgettrackerappproject.model.BudgetTrackerMysqlSpendingCacheEntity;
import com.myproject.offlinebudgettrackerappproject.service.MySQLApiService;
import com.myproject.offlinebudgettrackerappproject.util.BudgetTrackerMysqlCacheDatabase;
import com.myproject.offlinebudgettrackerappproject.util.RetrofitClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BudgetTrackerMysqlSpendingCacheRepository {

    private BudgetTrackerMysqlSpendingCacheDao budgetTrackerMysqlSpendingCacheDao;
    private MySQLApiService mySQLApiService;
    private LiveData<List<BudgetTrackerMysqlSpendingCacheEntity>> allItems;

    private Context context = null;

    public BudgetTrackerMysqlSpendingCacheRepository(Context context) throws IOException {
        this.context = context.getApplicationContext();
    }




    public BudgetTrackerMysqlSpendingCacheRepository(Application application) throws IOException {
        BudgetTrackerMysqlCacheDatabase db = Room.databaseBuilder(application, BudgetTrackerMysqlCacheDatabase.class, "item_database")
                .build();
        budgetTrackerMysqlSpendingCacheDao = db.budgetTrackerMysqlSpendingCacheDao();



        allItems = budgetTrackerMysqlSpendingCacheDao.getAllBudgetTrackerMysqlList();

        Properties properties = new Properties();
        try (InputStream inputStream = context.getAssets().open("server_config.properties")) {
            properties.load(inputStream);
        }
        String serverUrl = properties.getProperty("server_url");
        String phpSelectFile = properties.getProperty("spending_all_php_file");
        String selectUrl = serverUrl + phpSelectFile;

        Retrofit retrofit = RetrofitClient.getClient(selectUrl);
        mySQLApiService = retrofit.create(MySQLApiService.class);
    }

    public LiveData<List<BudgetTrackerMysqlSpendingCacheEntity>> getAllItems() {
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
