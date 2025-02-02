
package com.myproject.offlinebudgettrackerappproject.data;

import android.app.Application;
import android.util.Log;

import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlForeignSpendingDto;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlSpendingDto;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlTargetSpendingDto;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlUserCurrencyDto;
import com.myproject.offlinebudgettrackerappproject.enums.SpendingType;
import com.myproject.offlinebudgettrackerappproject.model.BudgetTrackerSpending;
import com.myproject.offlinebudgettrackerappproject.util.BudgetTrackerDatabase;
import com.myproject.offlinebudgettrackerappproject.util.DateUtils;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingForeignListCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingForeignSumCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingInsertCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingListCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingSumCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingTargetSumCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlUserCurrencyCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BudgetTrackerMysqlUserCurrencyRepository {
     //新しい
    private BudgetTrackerMysqlUserCurrencyInsertDao budgetTrackerMysqlUserCurrencyInsertDao;

    public BudgetTrackerMysqlUserCurrencyRepository(Application application) {
        budgetTrackerMysqlUserCurrencyInsertDao = new BudgetTrackerMysqlUserCurrencyInsertDao(application);
    }


    public void insert(BudgetTrackerMysqlUserCurrencyDto budgetTrackerMysqlUserCurrencyDto, MysqlUserCurrencyCallback callback) {
        budgetTrackerMysqlUserCurrencyInsertDao.insertIntoUserCurrency(budgetTrackerMysqlUserCurrencyDto, new MysqlUserCurrencyCallback() {
            @Override
            public void onSuccess(List<BudgetTrackerMysqlUserCurrencyDto> userCurrencyList) {
                for (BudgetTrackerMysqlUserCurrencyDto dto : userCurrencyList) {
                    Log.d("ViewModelResponse", dto.toString());
                }
                callback.onSuccess(userCurrencyList);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        });
    }
}
