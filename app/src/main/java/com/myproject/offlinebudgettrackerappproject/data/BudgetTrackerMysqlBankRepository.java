
package com.myproject.offlinebudgettrackerappproject.data;

import android.app.Application;
import android.util.Log;

import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlBankDto;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlSpendingDto;
import com.myproject.offlinebudgettrackerappproject.enums.SpendingType;
import com.myproject.offlinebudgettrackerappproject.model.BudgetTrackerSpending;
import com.myproject.offlinebudgettrackerappproject.util.BudgetTrackerDatabase;
import com.myproject.offlinebudgettrackerappproject.util.DateUtils;
import com.myproject.offlinebudgettrackerappproject.util.MysqlBankInsertCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlBankListCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingInsertCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingListCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BudgetTrackerMysqlBankRepository {

    private BudgetTrackerMysqlBankInsertDao budgetTrackerMysqlBankInsertDao;

    BudgetTrackerDatabase budgetTrackerDatabase;

    BudgetTrackerBankDao budgetTrackerBankDao;


    public BudgetTrackerMysqlBankRepository(Application application) {
        budgetTrackerMysqlBankInsertDao = new BudgetTrackerMysqlBankInsertDao(application);

        budgetTrackerDatabase = BudgetTrackerDatabase.getDatabase(application);

        budgetTrackerBankDao = budgetTrackerDatabase.budgetTrackerBankDao();
    }

    public void insert(BudgetTrackerMysqlBankDto budgetTrackerMysqlBankDto, MysqlBankInsertCallback callback) {
        budgetTrackerMysqlBankInsertDao.insertIntoBank(budgetTrackerMysqlBankDto, new MysqlBankInsertCallback() {
            @Override
            public void onSuccess(List<BudgetTrackerMysqlBankDto> bankList) {
//                Log.d("ViewModelResponse", spendingList.toString());
                for (BudgetTrackerMysqlBankDto dto : bankList) {
                    Log.d("ViewModelResponse", dto.toString());
                }
//                radioSearchStoreNameList = spendingList;
                callback.onSuccess(bankList);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        });
    }
}
