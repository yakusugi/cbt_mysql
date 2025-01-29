package com.myproject.offlinebudgettrackerappproject.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.myproject.offlinebudgettrackerappproject.model.UserCurrency;

@Dao
public interface UserCurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUserCurrency(UserCurrency userCurrency);

    @Query("SELECT * FROM user_currency WHERE email = :userEmail LIMIT 1")
    UserCurrency getUserCurrency(String userEmail);

    @Update
    void updateUserCurrency(UserCurrency userCurrency);

    @Query("DELETE FROM user_currency WHERE email = :userEmail")
    void deleteUserCurrency(String userEmail);
}

