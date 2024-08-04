package com.myproject.offlinebudgettrackerappproject.util;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.myproject.offlinebudgettrackerappproject.data.BudgetTrackerMysqlSpendingCacheDao;
import com.myproject.offlinebudgettrackerappproject.model.BudgetTrackerMysqlSpendingCacheEntity;

@Database(entities = {BudgetTrackerMysqlSpendingCacheEntity.class}, version = 1)
public abstract class BudgetTrackerMysqlCacheDatabase extends RoomDatabase {
    public abstract BudgetTrackerMysqlSpendingCacheDao budgetTrackerMysqlSpendingCacheDao();
}
