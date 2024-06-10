package com.myproject.offlinebudgettrackerappproject.data;

import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlSpendingDto;
import com.myproject.offlinebudgettrackerappproject.model.BudgetTrackerSpending;

import java.util.List;

public class BudgetTrackerMysqlSpendingRepository {

    private BudgetTrackerMysqlSpendingDao budgetTrackerMysqlSpendingDao;

    private List<BudgetTrackerMysqlSpendingDto> radioSearchStoreNameList;
    private List<BudgetTrackerMysqlSpendingDto> radioSearchProductNameList;

    private List<BudgetTrackerMysqlSpendingDto> radioSearchProductTypeList;

    Double searchStoreSum;

    Double searchProductNameSum;

    public List<BudgetTrackerMysqlSpendingDto> getSearchStoreNameList(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto) {
        radioSearchStoreNameList = budgetTrackerMysqlSpendingDao.getSearchStoreNameList(budgetTrackerMysqlSpendingDto);
        return radioSearchStoreNameList;
    }

    public double getSearchStoreSum(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto) {
        searchStoreSum = budgetTrackerMysqlSpendingDao.getSearchStoreSum(budgetTrackerMysqlSpendingDto);
        return searchStoreSum;
    }

    public List<BudgetTrackerMysqlSpendingDto> getSearchProductNameList(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto) {
        radioSearchProductNameList = budgetTrackerMysqlSpendingDao.getSearchStoreNameList(budgetTrackerMysqlSpendingDto);
        return radioSearchProductNameList;
    }

    public double getSearchProductNameSum(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto) {
        searchStoreSum = budgetTrackerMysqlSpendingDao.getSearchStoreSum(budgetTrackerMysqlSpendingDto);
        return searchStoreSum;
    }

    public List<BudgetTrackerMysqlSpendingDto> getSearchProductTypeList(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto) {
        radioSearchProductTypeList = budgetTrackerMysqlSpendingDao.getSearchStoreNameList(budgetTrackerMysqlSpendingDto);
        return radioSearchProductTypeList;
    }

    public double getSearchProductTypeSum(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto) {
        searchProductNameSum = budgetTrackerMysqlSpendingDao.getSearchStoreSum(budgetTrackerMysqlSpendingDto);
        return searchProductNameSum;
    }

}
