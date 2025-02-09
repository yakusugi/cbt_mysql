package com.myproject.offlinebudgettrackerappproject.model;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.myproject.offlinebudgettrackerappproject.data.BudgetTrackerMysqlSpendingRepository;
import com.myproject.offlinebudgettrackerappproject.data.BudgetTrackerMysqlUserPrimaryCurrencyRepository;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlForeignSpendingDto;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlSpendingDto;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlTargetSpendingDto;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlUserCurrencyDto;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlUserPrimaryCurrency;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingForeignListCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingForeignSumCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingInsertCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingListCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingSumCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingTargetSumCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlUserPrimaryCurrencyStringCallback;

import java.util.List;

public class BudgetTrackerMysqlUserPrimaryCurrencyViewModel extends AndroidViewModel {

    public List<BudgetTrackerMysqlSpendingDto> radioSearchStoreNameList;
    public List<BudgetTrackerMysqlSpendingDto> radioSearchProductNameList;
    public List<BudgetTrackerMysqlForeignSpendingDto> dateForeignList;

    public List<BudgetTrackerMysqlSpendingDto> radioSearchProductTypeList;

    public static BudgetTrackerMysqlSpendingRepository repository;

    BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto;
    BudgetTrackerMysqlUserPrimaryCurrencyRepository budgetTrackerMysqlUserPrimaryCurrencyRepository;

    // ✅ Declare `userPrimaryCurrency` at the class level
    private final MutableLiveData<String> userPrimaryCurrency = new MutableLiveData<>();

    Double searchStoreSum;
    Double searchProductSum;

    Double searchProductTypeSum;

    public BudgetTrackerMysqlUserPrimaryCurrencyViewModel(@NonNull Application application) {
        super(application);
        repository = new BudgetTrackerMysqlSpendingRepository(application);
        budgetTrackerMysqlUserPrimaryCurrencyRepository = new BudgetTrackerMysqlUserPrimaryCurrencyRepository(application);
    }

//    budgetTrackerMysqlSpendingDto = new BudgetTrackerMysqlSpendingDto();

    /**
     * ✅ Fetches and updates the user's primary currency.
     */
    public void fetchUserPrimaryCurrency(String email) {
        budgetTrackerMysqlUserPrimaryCurrencyRepository.getUserPrimaryCurrency(email, new MysqlUserPrimaryCurrencyStringCallback() {
            @Override
            public void onSuccess(String primaryCurrency) {
                Log.d("ViewModel", "Received currency: " + primaryCurrency);
                userPrimaryCurrency.postValue(primaryCurrency);
            }

            @Override
            public void onError(String error) {
                Log.e("ViewModel", "Failed to fetch currency: " + error);
            }
        });
    }

    /**
     * ✅ Returns LiveData that UI can observe.
     */
    public LiveData<String> getUserPrimaryCurrency() {

        return userPrimaryCurrency;
    }

}





