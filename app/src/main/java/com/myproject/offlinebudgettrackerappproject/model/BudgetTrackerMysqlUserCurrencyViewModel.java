package com.myproject.offlinebudgettrackerappproject.model;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.myproject.offlinebudgettrackerappproject.data.BudgetTrackerMysqlSpendingRepository;
import com.myproject.offlinebudgettrackerappproject.data.BudgetTrackerMysqlUserCurrencyRepository;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlForeignSpendingDto;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlSpendingDto;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlTargetSpendingDto;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlUserCurrencyDto;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingForeignListCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingForeignSumCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingInsertCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingListCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingSumCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingTargetSumCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlUserCurrencyCallback;

import java.util.List;

public class BudgetTrackerMysqlUserCurrencyViewModel extends AndroidViewModel {

    public static BudgetTrackerMysqlUserCurrencyRepository repository;

    public BudgetTrackerMysqlUserCurrencyViewModel(@NonNull Application application) {
        super(application);
        repository = new BudgetTrackerMysqlUserCurrencyRepository(application);
    }

    /**
     *
     * @param budgetTrackerMysqlUserCurrencyDto
     * @param callback
     */
    public void insert(BudgetTrackerMysqlUserCurrencyDto budgetTrackerMysqlUserCurrencyDto, MysqlUserCurrencyCallback callback) {
        repository.insert(budgetTrackerMysqlUserCurrencyDto, new MysqlUserCurrencyCallback() {
            @Override
            public void onSuccess(List<BudgetTrackerMysqlUserCurrencyDto> userCurrencyList) {
//                Log.d("ViewModelResponse", spendingList.toString());
                for (BudgetTrackerMysqlUserCurrencyDto dto : userCurrencyList) {
                    Log.d("ViewModelResponse", dto.toString());
                }
//                radioSearchStoreNameList = spendingList;
                callback.onSuccess(userCurrencyList);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        });
    }

}





