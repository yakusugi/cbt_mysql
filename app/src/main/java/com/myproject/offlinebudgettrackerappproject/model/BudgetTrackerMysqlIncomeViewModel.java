package com.myproject.offlinebudgettrackerappproject.model;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.myproject.offlinebudgettrackerappproject.data.BudgetTrackerMysqlSpendingRepository;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlIncomeDto;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlSpendingDto;
import com.myproject.offlinebudgettrackerappproject.util.MysqlIncomeInsertCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingInsertCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingListCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingSumCallback;

import java.util.List;

public class BudgetTrackerMysqlIncomeViewModel extends AndroidViewModel {

    public List<BudgetTrackerMysqlSpendingDto> radioSearchStoreNameList;
    public List<BudgetTrackerMysqlSpendingDto> radioSearchProductNameList;

    public List<BudgetTrackerMysqlSpendingDto> radioSearchProductTypeList;

    public static BudgetTrackerMysqlSpendingRepository repository;

    BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto;

    Double searchStoreSum;
    Double searchProductSum;

    Double searchProductTypeSum;

    public BudgetTrackerMysqlIncomeViewModel(@NonNull Application application) {
        super(application);
        repository = new BudgetTrackerMysqlSpendingRepository(application);
    }

//    budgetTrackerMysqlSpendingDto = new BudgetTrackerMysqlSpendingDto();

    public void getSearchStoreNameList(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto, MysqlSpendingListCallback callback) {
        repository.getSearchStoreNameList(budgetTrackerMysqlSpendingDto, new MysqlSpendingListCallback() {
            @Override
            public void onSuccess(List<BudgetTrackerMysqlSpendingDto> spendingList) {
//                Log.d("ViewModelResponse", spendingList.toString());
                for (BudgetTrackerMysqlSpendingDto dto : spendingList) {
                    Log.d("ViewModelResponse", dto.toString());
                }
                radioSearchStoreNameList = spendingList;
                callback.onSuccess(spendingList);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        });
    }

    /**
     *
     * @param budgetTrackerMysqlSpendingDto
     * @param callback
     */
    public void insert(BudgetTrackerMysqlIncomeDto budgetTrackerMysqlSpendingDto, MysqlIncomeInsertCallback callback) {
//        repository.insert(budgetTrackerMysqlSpendingDto, new MysqlSpendingInsertCallback() {
//            @Override
//            public void onSuccess(List<BudgetTrackerMysqlSpendingDto> spendingList) {
//                Log.d("ViewModelResponse", spendingList.toString());
//                for (BudgetTrackerMysqlSpendingDto dto : spendingList) {
//                    Log.d("ViewModelResponse", dto.toString());
//                }
//                radioSearchStoreNameList = spendingList;
//                callback.onSuccess(spendingList);
//            }
//
//            @Override
//            public void onError(String error) {
//                callback.onError(error);
//            }
//        });
    }

}





