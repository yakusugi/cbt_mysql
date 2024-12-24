
package com.myproject.offlinebudgettrackerappproject.data;

import android.app.Application;
import android.util.Log;

import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlBankDto;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlIncomeDto;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlSpendingDto;
import com.myproject.offlinebudgettrackerappproject.enums.SpendingType;
import com.myproject.offlinebudgettrackerappproject.model.BudgetTrackerSpending;
import com.myproject.offlinebudgettrackerappproject.util.BudgetTrackerDatabase;
import com.myproject.offlinebudgettrackerappproject.util.DateUtils;
import com.myproject.offlinebudgettrackerappproject.util.MysqlBankListCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlIncomeInsertCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlIncomeListCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingInsertCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingListCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingSumCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BudgetTrackerMysqlIncomeRepository {

    private BudgetTrackerMysqlIncomeInsertDao budgetTrackerMysqlIncomeInsertDao;

    Double searchStoreSum;

    Double searchProductNameSum;

    BudgetTrackerMysqlIncomeNameDao budgetTrackerMysqlSpendingDao;

    /**
     *
     * @param application
     */
    public BudgetTrackerMysqlIncomeRepository(Application application) {
        budgetTrackerMysqlIncomeInsertDao = new BudgetTrackerMysqlIncomeInsertDao(application);
        budgetTrackerMysqlSpendingDao = new BudgetTrackerMysqlIncomeNameDao(application);
    }


    /**
     * insert into income table
     * @param budgetTrackerMysqlIncomeDto
     * @param callback
     */
    public void insert(BudgetTrackerMysqlIncomeDto budgetTrackerMysqlIncomeDto, MysqlIncomeInsertCallback callback) {
        budgetTrackerMysqlIncomeInsertDao.insertIntoIncome(budgetTrackerMysqlIncomeDto, new MysqlIncomeInsertCallback() {
            @Override
            public void onSuccess(List<BudgetTrackerMysqlIncomeDto> incomeInsertList) {
                for (BudgetTrackerMysqlIncomeDto dto : incomeInsertList) {
                    Log.d("ViewModelResponse", dto.toString());
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    /**
     *
     * @param budgetTrackerMysqlIncomeDto
     * @param callback
     */
    public void getIncomeList(BudgetTrackerMysqlIncomeDto budgetTrackerMysqlIncomeDto, MysqlIncomeListCallback callback) {

        Log.d("TAG_DTO", "getDateList: " + budgetTrackerMysqlIncomeDto);
        budgetTrackerMysqlSpendingDao.getSearchIncomeNameList(budgetTrackerMysqlIncomeDto, new MysqlIncomeListCallback() {
            @Override
            public void onSuccess(List<BudgetTrackerMysqlIncomeDto> incomeList) {
                Log.d("Repository", "Insert success. Received bank list with size: " + incomeList.size());
                for (BudgetTrackerMysqlIncomeDto dto : incomeList) {
                    Log.d("ViewModelResponse", dto.toString());
                    Log.d("Repository", "Callback success executed.");
                }
                callback.onSuccess(incomeList);
            }

            @Override
            public void onError(String error) {

            }
        });
    }
}


