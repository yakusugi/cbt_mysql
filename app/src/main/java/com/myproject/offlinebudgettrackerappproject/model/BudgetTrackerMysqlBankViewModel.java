package com.myproject.offlinebudgettrackerappproject.model;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.myproject.offlinebudgettrackerappproject.data.BudgetTrackerMysqlBankRepository;
import com.myproject.offlinebudgettrackerappproject.data.BudgetTrackerMysqlSpendingRepository;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlBankDto;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlSpendingDto;
import com.myproject.offlinebudgettrackerappproject.util.MysqlBankInsertCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlBankListCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingInsertCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingListCallback;

import java.util.List;

public class BudgetTrackerMysqlBankViewModel extends AndroidViewModel {

    public List<BudgetTrackerMysqlBankDto> bankNameList;
    public List<BudgetTrackerMysqlSpendingDto> radioSearchProductNameList;

    public List<BudgetTrackerMysqlSpendingDto> radioSearchProductTypeList;

    public static BudgetTrackerMysqlBankRepository repository;

    BudgetTrackerMysqlBankDto budgetTrackerMysqlBankDto;

    Double searchStoreSum;
    Double searchProductSum;
    Double searchProductTypeSum;

    public BudgetTrackerMysqlBankViewModel(@NonNull Application application) {
        super(application);
        repository = new BudgetTrackerMysqlBankRepository(application);
    }

    public void insert(BudgetTrackerMysqlBankDto budgetTrackerMysqlBankDto, MysqlBankInsertCallback callback) {
        repository.insert(budgetTrackerMysqlBankDto, new MysqlBankInsertCallback() {

            @Override
            public void onSuccess(List<BudgetTrackerMysqlBankDto> bankInsertList) {
                for (BudgetTrackerMysqlBankDto dto : bankInsertList) {
                    Log.d("ViewModelResponse", dto.toString());
                }
//                radioSearchStoreNameList = spendingList;
                callback.onSuccess(bankInsertList);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        });
    }

    public void getBankList(BudgetTrackerMysqlBankDto budgetTrackerMysqlBankDto, MysqlBankListCallback callback) {
        repository.getBankList(budgetTrackerMysqlBankDto, new MysqlBankListCallback() {
            @Override
            public void onSuccess(List<BudgetTrackerMysqlBankDto> bankgList) {
//                Log.d("ViewModelResponse", spendingList.toString());
                for (BudgetTrackerMysqlBankDto dto : bankgList) {
                    Log.d("ViewModelResponse", dto.toString());
                }
                bankNameList = bankgList;
                callback.onSuccess(bankgList);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        });
    }



//    public void syncFromMysql(List<BudgetTrackerMysqlSpendingDto> dtoList) {
//        repository.insertFromMysql(dtoList);
//    }

}





