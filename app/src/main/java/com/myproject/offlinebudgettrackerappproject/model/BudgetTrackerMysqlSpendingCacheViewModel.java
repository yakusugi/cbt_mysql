package com.myproject.offlinebudgettrackerappproject.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.myproject.offlinebudgettrackerappproject.data.BudgetTrackerMysqlSpendingCacheRepository;

import java.io.IOException;
import java.util.List;

public class BudgetTrackerMysqlSpendingCacheViewModel extends AndroidViewModel {
    private BudgetTrackerMysqlSpendingCacheRepository repository;
    private LiveData<List<BudgetTrackerMysqlSpendingCacheEntity>> allItems;
    public BudgetTrackerMysqlSpendingCacheViewModel(@NonNull Application application) throws IOException {
        super(application);
        repository = new BudgetTrackerMysqlSpendingCacheRepository(application);
        allItems = repository.getAllItems();
    }

    public LiveData<List<BudgetTrackerMysqlSpendingCacheEntity>> getAllItems() {
        return allItems;
    }

    public void refreshItemsFromBackend() {
        repository.refreshBudgetDataFromBackend();
    }

    public void insert(List<BudgetTrackerMysqlSpendingCacheEntity> budgetTrackerMysqlSpendingCacheEntity) {
        repository.insert(budgetTrackerMysqlSpendingCacheEntity);
    }
}
