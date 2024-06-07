package com.myproject.offlinebudgettrackerappproject.data;

import android.content.Context;

import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerUserDto;
import com.myproject.offlinebudgettrackerappproject.model.BudgetTrackerSpending;
import com.myproject.offlinebudgettrackerappproject.util.BudgetTrackerDatabase;
import com.myproject.offlinebudgettrackerappproject.util.Callback;
import com.myproject.offlinebudgettrackerappproject.util.LoginCallback;

public class BudgetTrackerMySqlRepository {

    private BudgetTrackerMySqlDao budgetTrackerMySqlDao;
    private Context context;

    public BudgetTrackerMySqlRepository(Context context) {
        this.context = context.getApplicationContext();
        budgetTrackerMySqlDao = new BudgetTrackerMySqlDao(context);
    }

    public void login(BudgetTrackerUserDto budgetTrackerUserDto, LoginCallback callback) {
        BudgetTrackerDatabase.dataWritableExecutor.execute(() -> {
            budgetTrackerMySqlDao.login(budgetTrackerUserDto, callback);
        });
    }

}
