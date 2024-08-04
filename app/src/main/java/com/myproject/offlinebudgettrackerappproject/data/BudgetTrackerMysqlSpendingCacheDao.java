package com.myproject.offlinebudgettrackerappproject.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.myproject.offlinebudgettrackerappproject.model.BudgetTracker;
import com.myproject.offlinebudgettrackerappproject.model.BudgetTrackerMysqlSpendingCacheEntity;

import java.util.List;

@Dao
public interface BudgetTrackerMysqlSpendingCacheDao {

    @Query("SELECT * FROM budget_tracker_mysql_spending_cache_table ORDER BY product_name ASC")
    LiveData<List<BudgetTracker>> getAllBudgetTrackerMysqlList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<BudgetTrackerMysqlSpendingCacheEntity> budgetTrackerMysqlSpendingCacheEntity);

    @Query("DELETE FROM budget_tracker_mysql_spending_cache_table")
    void deleteAll();

}
