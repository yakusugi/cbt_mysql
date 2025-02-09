
package com.myproject.offlinebudgettrackerappproject.data;

import android.app.Application;
import android.util.Log;

import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlUserCurrencyDto;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlUserPrimaryCurrency;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingListCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlUserCurrencyCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlUserPrimaryCurrencyCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlUserPrimaryCurrencyStringCallback;

import java.util.List;

public class BudgetTrackerMysqlUserPrimaryCurrencyRepository {
    private BudgetTrackerMysqlUserPrimaryCurrencyDao budgetTrackerMysqlUserPrimaryCurrencyDao;

    public BudgetTrackerMysqlUserPrimaryCurrencyRepository(Application application) {
        budgetTrackerMysqlUserPrimaryCurrencyDao = new BudgetTrackerMysqlUserPrimaryCurrencyDao(application);
    }

    public void getUserPrimaryCurrency(String email, MysqlUserPrimaryCurrencyStringCallback callback) {
        BudgetTrackerMysqlUserPrimaryCurrency dto = new BudgetTrackerMysqlUserPrimaryCurrency(email);

        new Thread(() -> {
            String primaryCurrency = budgetTrackerMysqlUserPrimaryCurrencyDao.getUserPrimaryCurrency(dto);
            if (primaryCurrency != null) {
                callback.onSuccess(primaryCurrency);
            } else {
                callback.onError("Failed to fetch user primary currency");
            }
        }).start();
    }
}
