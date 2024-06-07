package com.myproject.offlinebudgettrackerappproject.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.myproject.offlinebudgettrackerappproject.model.BudgetTracker;

import java.util.List;

@Dao
public interface BudgetTrackerDao {
    //CRUD
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(BudgetTracker budgetTracker);

    @Query("DELETE FROM budget_tracker_table")
    void deleteAll();

    @Query("SELECT * FROM budget_tracker_table ORDER BY product_name ASC")
    LiveData<List<BudgetTracker>> getAllBudgetTrackerList();

    @Query("SELECT * FROM budget_tracker_table WHERE store_name LIKE '%' || :storeName|| '%'")
    List<BudgetTracker> getStoreNameLists(String storeName);

    @Query("SELECT * FROM budget_tracker_table WHERE product_type LIKE '%' || :productType|| '%'")
    List<BudgetTracker> getProductTypeLists(String productType);

    @Query("SELECT SUM(price) FROM budget_tracker_table WHERE product_type LIKE '%' || :productType|| '%'")
    int getProductSum(String productType);

    @Query("SELECT * FROM budget_tracker_table WHERE product_name LIKE '%' || :productName|| '%'")
    List<BudgetTracker> getProductNameList(String productName);

    @Query("SELECT SUM(price) FROM budget_tracker_table WHERE store_name LIKE '%' || :storeName|| '%'")
    int getStoreSum(String storeName);

    @Query("SELECT * FROM budget_tracker_table WHERE date >= :date1 and date <= :date2")
    List<BudgetTracker> getDateLists(String date1, String date2);

    @Query("SELECT * FROM budget_tracker_table WHERE budget_tracker_table.id == :id")
    LiveData<BudgetTracker> getBudgetTrackerId(int id);

    @Update
    void updateBudgetTracker(BudgetTracker budgetTracker);

    @Delete
    void deleteBudgetTracker(BudgetTracker budgetTracker);

    @Query("SELECT * FROM budget_tracker_table WHERE store_name LIKE '%' || :storeName|| '%' and date >= :date1 and date <= :date2")
    List<BudgetTracker> getRadioStoreNameLists(String storeName, String date1, String date2);

    @Query("SELECT SUM(price) FROM budget_tracker_table WHERE store_name LIKE '%' || :storeName|| '%' and date >= :date1 and date <= :date2")
    int getDateStoreSum(String storeName, String date1, String date2);

    @Query("SELECT * FROM budget_tracker_table WHERE product_name LIKE '%' || :productName|| '%' and date >= :date1 and date <= :date2")
    List<BudgetTracker> getRadioProductNameLists(String productName, String date1, String date2);

    @Query("SELECT SUM(price) FROM budget_tracker_table WHERE product_name LIKE '%' || :productName|| '%' and date >= :date1 and date <= :date2")
    int getDateProductNameSum(String productName, String date1, String date2);

    @Query("SELECT * FROM budget_tracker_table WHERE product_type LIKE '%' || :productType|| '%' and date >= :date1 and date <= :date2")
    List<BudgetTracker> getRadioProductTypeLists(String productType, String date1, String date2);

    @Query("SELECT SUM(price) FROM budget_tracker_table WHERE product_type LIKE '%' || :productType|| '%' and date >= :date1 and date <= :date2")
    int getDateProductTypeSum(String productType, String date1, String date2);

    @Query("update budget_tracker_table set store_name = `replace`(store_name,:storeNameFrom,:storeNameTo) where store_name like :storeNameFrom || '%'")
    void replaceStoreName(String storeNameFrom, String storeNameTo);

    @Query("update budget_tracker_table set product_name = `replace`(product_name,:productNameFrom,:productNameTo) where product_name like :productNameFrom || '%'")
    void replaceProductName(String productNameFrom, String productNameTo);

    @Query("update budget_tracker_table set product_type = `replace`(product_type,:productTypeFrom,:productTypeTo) where product_type like :productTypeFrom || '%'")
    void replaceProductType(String productTypeFrom, String productTypeTo);


}
