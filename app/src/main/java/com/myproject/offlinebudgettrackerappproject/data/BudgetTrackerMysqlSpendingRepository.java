
package com.myproject.offlinebudgettrackerappproject.data;

import android.app.Application;
import android.util.Log;

import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlSpendingDto;
import com.myproject.offlinebudgettrackerappproject.enums.SpendingType;
import com.myproject.offlinebudgettrackerappproject.model.BudgetTrackerSpending;
import com.myproject.offlinebudgettrackerappproject.util.BudgetTrackerDatabase;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingListCallback;
import com.myproject.offlinebudgettrackerappproject.util.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class BudgetTrackerMysqlSpendingRepository {

    private BudgetTrackerMysqlSpendingStoreNameDao budgetTrackerMysqlSpendingStoreNameDao;
    private BudgetTrackerMysqlSpendingProductNameDao budgetTrackerMysqlSpendingProductNameDao;
    private BudgetTrackerMysqlSpendingProductTypeDao budgetTrackerMysqlSpendingProductTypeDao;
    private BudgetTrackerMysqlSpendingDateDao budgetTrackerMysqlSpendingDateDao;
//    private BudgetTrackerMysqlSpendingStoreNameSyncDao budgetTrackerMysqlSpendingStoreNameSyncDao;

    BudgetTrackerDatabase budgetTrackerDatabase;
    private BudgetTrackerSpendingDao budgetTrackerSpendingDao;

    private List<BudgetTrackerMysqlSpendingDto> radioSearchStoreNameList;

    private List<BudgetTrackerSpending> budgetTrackerSpendingList;
    private List<BudgetTrackerMysqlSpendingDto> radioSearchProductNameList;

    private List<BudgetTrackerMysqlSpendingDto> radioSearchProductTypeList;

    Double searchStoreSum;

    Double searchProductNameSum;

    public BudgetTrackerMysqlSpendingRepository(Application application) {
        budgetTrackerMysqlSpendingStoreNameDao = new BudgetTrackerMysqlSpendingStoreNameDao(application);
        budgetTrackerMysqlSpendingProductNameDao = new BudgetTrackerMysqlSpendingProductNameDao(application);
        budgetTrackerMysqlSpendingProductTypeDao = new BudgetTrackerMysqlSpendingProductTypeDao(application);
        budgetTrackerMysqlSpendingDateDao = new BudgetTrackerMysqlSpendingDateDao(application);
//        budgetTrackerMysqlSpendingStoreNameSyncDao = new BudgetTrackerMysqlSpendingStoreNameSyncDao(application);

        budgetTrackerDatabase = BudgetTrackerDatabase.getDatabase(application);

        budgetTrackerSpendingDao = budgetTrackerDatabase.budgetTrackerSpendingDao();
    }

    public void getSearchStoreNameList(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto, MysqlSpendingListCallback callback) {
        budgetTrackerMysqlSpendingStoreNameDao.getSearchStoreNameList(budgetTrackerMysqlSpendingDto, new MysqlSpendingListCallback() {
            @Override
            public void onSuccess(List<BudgetTrackerMysqlSpendingDto> spendingList) {
//                Log.d("RepositoryResponse", spendingList.toString());
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                for (BudgetTrackerMysqlSpendingDto dto : spendingList) {
//                    Log.d("RepositoryResponse", dto.toString());
//                }

                for (BudgetTrackerMysqlSpendingDto dto : spendingList) {
                    try {
                        Date date = dto.getDate();
                        String formattedDate = DateUtils.dateToString(date);
                        dto.setDate(DateUtils.stringToDate(formattedDate));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Log.d("RepositoryResponse", dto.toString());
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

    public void getDateList(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto, MysqlSpendingListCallback callback) {

        Log.d("TAG_DTO", "getDateList: " + budgetTrackerMysqlSpendingDto);
        budgetTrackerMysqlSpendingDateDao.getSearchDateList(budgetTrackerMysqlSpendingDto, new MysqlSpendingListCallback() {
            @Override
            public void onSuccess(List<BudgetTrackerMysqlSpendingDto> spendingList) {
//                Log.d("RepositoryResponse", spendingList.toString());
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                for (BudgetTrackerMysqlSpendingDto dto : spendingList) {
//                    Log.d("RepositoryResponse", dto.toString());
//                }

                for (BudgetTrackerMysqlSpendingDto dto : spendingList) {
                    try {
                        Date date = dto.getDate();
                        String formattedDate = DateUtils.dateToString(date);
                        dto.setDate(DateUtils.stringToDate(formattedDate));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Log.d("RepositoryResponse", dto.toString());
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

    public double getSearchStoreSum(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto) {
        searchStoreSum = budgetTrackerMysqlSpendingStoreNameDao.getSearchStoreSum(budgetTrackerMysqlSpendingDto);
        return searchStoreSum;
    }

//    public List<BudgetTrackerMysqlSpendingDto> getSearchProductNameList(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto) {
//        radioSearchProductNameList = budgetTrackerMysqlSpendingProductNameDao.getSearchProductNameList(budgetTrackerMysqlSpendingDto);
//        return radioSearchProductNameList;
//    }

    public void getSearchProductNameList(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto, MysqlSpendingListCallback callback) {
        budgetTrackerMysqlSpendingProductNameDao.getSearchProductNameList(budgetTrackerMysqlSpendingDto, new MysqlSpendingListCallback() {
            @Override
            public void onSuccess(List<BudgetTrackerMysqlSpendingDto> spendingList) {
//                Log.d("RepositoryResponse", spendingList.toString());
                for (BudgetTrackerMysqlSpendingDto dto : spendingList) {
                    Log.d("RepositoryResponse", dto.toString());
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

    public double getSearchProductNameSum(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto) {
        searchStoreSum = budgetTrackerMysqlSpendingStoreNameDao.getSearchProductNameSum(budgetTrackerMysqlSpendingDto);
        return searchStoreSum;
    }

    public void getSearchProductTypeList(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto, MysqlSpendingListCallback callback) {
        budgetTrackerMysqlSpendingProductTypeDao.getSearchProductTypeList(budgetTrackerMysqlSpendingDto, new MysqlSpendingListCallback() {
            @Override
            public void onSuccess(List<BudgetTrackerMysqlSpendingDto> spendingList) {
//                Log.d("RepositoryResponse", spendingList.toString());
                for (BudgetTrackerMysqlSpendingDto dto : spendingList) {
                    Log.d("RepositoryResponse", dto.toString());
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
        searchProductNameSum = budgetTrackerMysqlSpendingStoreNameDao.getSearchProductTypeSum(budgetTrackerMysqlSpendingDto);
        return searchProductNameSum;
    }

    public void insertFromMysql(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto, MysqlSpendingListCallback callback) {

        SpendingType spendingType = determineSpendingType(budgetTrackerMysqlSpendingDto);

        switch (spendingType) {
            case STORE:
                budgetTrackerMysqlSpendingStoreNameDao.getSearchStoreNameList(budgetTrackerMysqlSpendingDto, new MysqlSpendingListCallback() {
                    public void onSuccess(List<BudgetTrackerMysqlSpendingDto> spendingList) {
                        handleSuccess(spendingList, callback);
                    }

                    @Override
                    public void onError(String error) {
                        callback.onError(error);
                    }
                });
                break;

            case PRODUCT_NAME:
                budgetTrackerMysqlSpendingProductNameDao.getSearchProductNameList(budgetTrackerMysqlSpendingDto, new MysqlSpendingListCallback() {
                    public void onSuccess(List<BudgetTrackerMysqlSpendingDto> spendingList) {
                        handleSuccess(spendingList, callback);
                    }

                    @Override
                    public void onError(String error) {
                        callback.onError(error);
                    }
                });
                break;

            case PRODUCT_TYPE:
                budgetTrackerMysqlSpendingProductTypeDao.getSearchProductTypeList(budgetTrackerMysqlSpendingDto, new MysqlSpendingListCallback() {
                    public void onSuccess(List<BudgetTrackerMysqlSpendingDto> spendingList) {
                        handleSuccess(spendingList, callback);
                    }

                    @Override
                    public void onError(String error) {
                        callback.onError(error);
                    }
                });
                break;

            case CURRENCY:
                budgetTrackerMysqlSpendingDateDao.getSearchDateList(budgetTrackerMysqlSpendingDto, new MysqlSpendingListCallback() {

                    public void onSuccess(List<BudgetTrackerMysqlSpendingDto> spendingList) {
                        handleSuccess(spendingList, callback);
                    }

                    @Override
                    public void onError(String error) {
                        callback.onError(error);
                    }
                });
                break;

        }
    }

    private void handleSuccess(List<BudgetTrackerMysqlSpendingDto> spendingList, MysqlSpendingListCallback callback) {
        for (BudgetTrackerMysqlSpendingDto dto : spendingList) {
            Log.d("RepositoryResponse", dto.toString());
        }

        List<BudgetTrackerSpending> entityList = new ArrayList<>();

        for (BudgetTrackerMysqlSpendingDto dto : spendingList) {
            entityList.add(dto.toEntity());
        }

        BudgetTrackerDatabase.dataWritableExecutor.execute(() -> {
            budgetTrackerSpendingDao.insertFromMysql(entityList);
        });

        callback.onSuccess(spendingList);
    }

    private SpendingType determineSpendingType(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto) {
        if (budgetTrackerMysqlSpendingDto.getStoreName() != null) {
            return SpendingType.STORE;
        } else if (budgetTrackerMysqlSpendingDto.getProductName() != null) {
            return SpendingType.PRODUCT_NAME;
        } else if (budgetTrackerMysqlSpendingDto.getProductType() != null) {
            return SpendingType.PRODUCT_TYPE;
        } else if (budgetTrackerMysqlSpendingDto.getCurrencyCode() != null) {
            return SpendingType.CURRENCY;
        } else {
            return null;
        }

    }
}
