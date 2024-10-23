package com.myproject.offlinebudgettrackerappproject.model;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.myproject.offlinebudgettrackerappproject.data.BudgetTrackerMysqlSpendingRepository;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlSpendingDto;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingInsertCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingListCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingSumCallback;

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

    public void getSearchProductNameList(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto, MysqlSpendingListCallback callback) {
        repository.getSearchProductNameList(budgetTrackerMysqlSpendingDto, new MysqlSpendingListCallback() {
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

//    public double getSearchStoreSum(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto) {
//        searchStoreSum = repository.getSearchStoreSum(budgetTrackerMysqlSpendingDto);
//        return searchStoreSum;
//    }
//

//
//    public double getSearchProductNameSum(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto) {
//        searchProductSum = repository.getSearchProductNameSum(budgetTrackerMysqlSpendingDto);
//        return searchProductSum;
//    }
//
public void getSearchProductTypeList(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto, MysqlSpendingListCallback callback) {
    repository.getSearchProductTypeList(budgetTrackerMysqlSpendingDto, new MysqlSpendingListCallback() {
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

    public double getSearchProductTypeSum(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto) {
        searchProductTypeSum = repository.getSearchProductTypeSum(budgetTrackerMysqlSpendingDto);
        return searchProductTypeSum;
    }

    public void getDateList(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto, MysqlSpendingListCallback callback) {
        repository.getDateList(budgetTrackerMysqlSpendingDto, new MysqlSpendingListCallback() {
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

    public void getSyncSpendingList(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto, MysqlSpendingListCallback callback) {
        repository.insertFromMysql(budgetTrackerMysqlSpendingDto, new MysqlSpendingListCallback() {
            @Override
            public void onSuccess(List<BudgetTrackerMysqlSpendingDto> spendingList) {
//                Log.d("ViewModelResponse", spendingList.toString());
                for (BudgetTrackerMysqlSpendingDto dto : spendingList) {
                    Log.d("ViewModelResponse", dto.toString());
                }
//                radioSearchStoreNameList = spendingList;
                callback.onSuccess(spendingList);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        });
    }

    public void getCalculatedDateSum(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto, MysqlSpendingSumCallback callback) {
        repository.getCalculatedDateSum(budgetTrackerMysqlSpendingDto, new MysqlSpendingSumCallback() {

            @Override
            public void onSuccess(Double spendingSum) {
                Log.d("RepositoryResponse", "Total Spending: " + spendingSum);
                callback.onSuccess(spendingSum); // Pass the total spending to the callback
            }

            @Override
            public void onError(String error) {
                callback.onError(error); // Pass the error to the callback
            }
        });
    }

    public void getCalculatedStoreNameSum(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto, MysqlSpendingSumCallback callback) {
        Log.d("STORE_TAG", "getCalculatedStoreNameSum: " + budgetTrackerMysqlSpendingDto);
        repository.getCalculatedStoreNameSum(budgetTrackerMysqlSpendingDto, new MysqlSpendingSumCallback() {

            @Override
            public void onSuccess(Double spendingSum) {
                Log.d("RepositoryResponse", "Total Spending: " + spendingSum);
                callback.onSuccess(spendingSum); // Pass the total spending to the callback
            }

            @Override
            public void onError(String error) {
                callback.onError(error); // Pass the error to the callback
            }
        });
    }

    public void getCalculatedProductTypeSum(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto, MysqlSpendingSumCallback callback) {
        Log.d("PRODUCT_TYPE_TAG", "getCalculatedStoreNameSum: " + budgetTrackerMysqlSpendingDto);
        repository.getCalculatedProductTypeSum(budgetTrackerMysqlSpendingDto, new MysqlSpendingSumCallback() {

            @Override
            public void onSuccess(Double spendingSum) {
                Log.d("RepositoryResponse", "Total Spending: " + spendingSum);
                callback.onSuccess(spendingSum); // Pass the total spending to the callback
            }

            @Override
            public void onError(String error) {
                callback.onError(error); // Pass the error to the callback
            }
        });
    }


    public void insert(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto, MysqlSpendingInsertCallback callback) {
        repository.insert(budgetTrackerMysqlSpendingDto, new MysqlSpendingInsertCallback() {
            @Override
            public void onSuccess(List<BudgetTrackerMysqlSpendingDto> spendingList) {
//                Log.d("ViewModelResponse", spendingList.toString());
                for (BudgetTrackerMysqlSpendingDto dto : spendingList) {
                    Log.d("ViewModelResponse", dto.toString());
                }
//                radioSearchStoreNameList = spendingList;
                callback.onSuccess(spendingList);
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





