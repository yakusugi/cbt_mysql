package com.myproject.offlinebudgettrackerappproject.model;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.myproject.offlinebudgettrackerappproject.data.BudgetTrackerMysqlIncomeRepository;
import com.myproject.offlinebudgettrackerappproject.data.BudgetTrackerMysqlSpendingRepository;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlIncomeDto;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlSpendingDto;
import com.myproject.offlinebudgettrackerappproject.util.MysqlIncomeInsertCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingListCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingSumCallback;

import java.util.List;

public class BudgetTrackerMysqlIncomeViewModel extends AndroidViewModel {

    public List<BudgetTrackerMysqlSpendingDto> radioSearchStoreNameList;
    public List<BudgetTrackerMysqlSpendingDto> radioSearchProductNameList;

    public List<BudgetTrackerMysqlSpendingDto> radioSearchProductTypeList;

    public static BudgetTrackerMysqlIncomeRepository repository;

    BudgetTrackerMysqlIncomeDto budgetTrackerMysqlIncomeDto;

    Double searchStoreSum;
    Double searchProductSum;

    Double searchProductTypeSum;

    public BudgetTrackerMysqlIncomeViewModel(@NonNull Application application) {
        super(application);
        repository = new BudgetTrackerMysqlIncomeRepository(application);
    }


    /**
     *
     * @param budgetTrackerMysqlIncomeDto
     * @param callback
     */
    public void insert(BudgetTrackerMysqlIncomeDto budgetTrackerMysqlIncomeDto, MysqlIncomeInsertCallback callback) {
        repository.insert(budgetTrackerMysqlIncomeDto, new MysqlIncomeInsertCallback() {

            @Override
            public void onSuccess(List<BudgetTrackerMysqlIncomeDto> incomeInsertList) {
                Log.d("ViewModelResponse", incomeInsertList.toString());
                for (BudgetTrackerMysqlIncomeDto dto : incomeInsertList) {
                    Log.d("ViewModelResponse", dto.toString());
                }
                callback.onSuccess(incomeInsertList);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        });
    }

}





