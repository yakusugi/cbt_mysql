package com.myproject.offlinebudgettrackerappproject.data;

import android.app.Application;

import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlSpendingDto;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingListCallback;

import java.util.List;

public class BudgetTrackerMysqlSpendingRepository {

    private BudgetTrackerMysqlSpendingStoreNameDao budgetTrackerMysqlSpendingStoreNameDao;

    private List<BudgetTrackerMysqlSpendingDto> radioSearchStoreNameList;
    private List<BudgetTrackerMysqlSpendingDto> radioSearchProductNameList;

    private List<BudgetTrackerMysqlSpendingDto> radioSearchProductTypeList;

    Double searchStoreSum;

    Double searchProductNameSum;

    public BudgetTrackerMysqlSpendingRepository(Application application) {
        budgetTrackerMysqlSpendingStoreNameDao = new BudgetTrackerMysqlSpendingStoreNameDao(application);
    }

    public void getSearchStoreNameList(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto, MysqlSpendingListCallback callback) {
        budgetTrackerMysqlSpendingStoreNameDao.getSearchStoreNameList(budgetTrackerMysqlSpendingDto, new MysqlSpendingListCallback() {
            @Override
            public void onSuccess(List<BudgetTrackerMysqlSpendingDto> spendingList) {
                radioSearchStoreNameList = spendingList;
                callback.onSuccess(spendingList);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        });
    }

    public double getSearchStoreSum(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto) {
        searchStoreSum = budgetTrackerMysqlSpendingStoreNameDao.getSearchStoreSum(budgetTrackerMysqlSpendingDto);
        return searchStoreSum;
    }

    public List<BudgetTrackerMysqlSpendingDto> getSearchProductNameList(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto) {
        radioSearchProductNameList = budgetTrackerMysqlSpendingStoreNameDao.getSearchProductNameList(budgetTrackerMysqlSpendingDto);
        return radioSearchProductNameList;
    }

    public double getSearchProductNameSum(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto) {
        searchStoreSum = budgetTrackerMysqlSpendingStoreNameDao.getSearchProductNameSum(budgetTrackerMysqlSpendingDto);
        return searchStoreSum;
    }

    public List<BudgetTrackerMysqlSpendingDto> getSearchProductTypeList(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto) {
        radioSearchProductTypeList = budgetTrackerMysqlSpendingStoreNameDao.getSearchProductTypeList(budgetTrackerMysqlSpendingDto);
        return radioSearchProductTypeList;
    }

    public double getSearchProductTypeSum(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto) {
        searchProductNameSum = budgetTrackerMysqlSpendingStoreNameDao.getSearchProductTypeSum(budgetTrackerMysqlSpendingDto);
        return searchProductNameSum;
    }

}
