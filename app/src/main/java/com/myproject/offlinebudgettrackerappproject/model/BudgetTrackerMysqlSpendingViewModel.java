package com.myproject.offlinebudgettrackerappproject.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.myproject.offlinebudgettrackerappproject.data.BudgetTrackerMysqlSpendingRepository;
import com.myproject.offlinebudgettrackerappproject.data.BudgetTrackerSpendingRepository;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlSpendingDto;

import java.util.List;

public class BudgetTrackerMysqlSpendingViewModel extends AndroidViewModel {

    public List<BudgetTrackerMysqlSpendingDto> radioSearchStoreNameList;
    public List<BudgetTrackerMysqlSpendingDto> radioSearchProductNameList;

    public List<BudgetTrackerMysqlSpendingDto> radioSearchProductTypeList;

    public static BudgetTrackerMysqlSpendingRepository repository;

    BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto;

    Double searchStoreSum;
    Double searchProductSum;

    Double searchProductTypeSum;

    public BudgetTrackerMysqlSpendingViewModel(@NonNull Application application) {
        super(application);
    }

//    budgetTrackerMysqlSpendingDto = new BudgetTrackerMysqlSpendingDto();

    public List<BudgetTrackerMysqlSpendingDto> getSearchStoreNameList(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto) {
        radioSearchStoreNameList = repository.getSearchStoreNameList(budgetTrackerMysqlSpendingDto);
        return radioSearchStoreNameList;
    }

    public double getSearchStoreSum(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto) {
        searchStoreSum = repository.getSearchStoreSum(budgetTrackerMysqlSpendingDto);
        return searchStoreSum;
    }

    public List<BudgetTrackerMysqlSpendingDto> getSearchProductNameList(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto) {
        radioSearchProductNameList = repository.getSearchProductNameList(budgetTrackerMysqlSpendingDto);
        return radioSearchStoreNameList;
    }

    public double getSearchProductNameSum(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto) {
        searchProductSum = repository.getSearchProductNameSum(budgetTrackerMysqlSpendingDto);
        return searchProductSum;
    }

    public List<BudgetTrackerMysqlSpendingDto> getSearchProductTypeList(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto) {
        radioSearchProductTypeList = repository.getSearchProductTypeList(budgetTrackerMysqlSpendingDto);
        return radioSearchProductTypeList;
    }

    public double getSearchProductTypeSum(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto) {
        searchProductTypeSum = repository.getSearchProductTypeSum(budgetTrackerMysqlSpendingDto);
        return searchProductTypeSum;
    }

}
