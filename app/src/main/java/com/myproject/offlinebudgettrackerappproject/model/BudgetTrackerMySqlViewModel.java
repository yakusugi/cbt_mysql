package com.myproject.offlinebudgettrackerappproject.model;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.myproject.offlinebudgettrackerappproject.data.BudgetTrackerMySqlRepository;
import com.myproject.offlinebudgettrackerappproject.data.BudgetTrackerSpendingRepository;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerUserDto;
import com.myproject.offlinebudgettrackerappproject.util.Callback;
import com.myproject.offlinebudgettrackerappproject.util.LoginCallback;

public class BudgetTrackerMySqlViewModel extends AndroidViewModel {

    public static BudgetTrackerMySqlRepository repository;


    BudgetTrackerUserDto budgetTrackerUserDto;

    public BudgetTrackerMySqlViewModel(@NonNull Application application) {
        super(application);
        repository = new BudgetTrackerMySqlRepository(application);
    }

    public void login(BudgetTrackerUserDto budgetTrackerUserDto, LoginCallback callback) {
        Log.d("TAG", "login: budgetTrackerUserDto");
        repository.login(budgetTrackerUserDto, callback);
    }
}
