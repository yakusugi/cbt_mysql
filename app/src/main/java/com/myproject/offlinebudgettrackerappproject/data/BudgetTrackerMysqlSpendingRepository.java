
package com.myproject.offlinebudgettrackerappproject.data;

import android.app.Application;
import android.util.Log;

import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlForeignSpendingDto;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlSpendingDto;
import com.myproject.offlinebudgettrackerappproject.enums.SpendingType;
import com.myproject.offlinebudgettrackerappproject.model.BudgetTrackerSpending;
import com.myproject.offlinebudgettrackerappproject.util.BudgetTrackerDatabase;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingForeignListCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingInsertCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingListCallback;
import com.myproject.offlinebudgettrackerappproject.util.DateUtils;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingSumCallback;

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
    private BudgetTrackerMysqlSpendingDateSumDao budgetTrackerMysqlSpendingDateSumDao;
    private BudgetTrackerMysqlSpendingStoreNameSumDao budgetTrackerMysqlSpendingStoreNameSumDao;
    private BudgetTrackerMysqlSpendingDateDao budgetTrackerMysqlSpendingDateDao;

    private BudgetTrackerMysqlConvertingOriginalDateDao budgetTrackerMysqlConvertingOriginalDateDao;
    private BudgetTrackerMysqlSpendingInsertDao budgetTrackerMysqlSpendingInsertDao;
    private BudgetTrackerMysqlSpendingProductTypeSumDao budgetTrackerMysqlSpendingProductTypeSumDao;
    private BudgetTrackerMysqlSpendingStoreStatsDao budgetTrackerMysqlSpendingStoreStatsDao;
    private BudgetTrackerMysqlSpendingProductTypeStatsDao budgetTrackerMysqlSpendingProductTypeStatsDao;
    private BudgetTrackerMysqlSpendingProductNameStatsDao budgetTrackerMysqlSpendingProductNameStatsDao;
    private BudgetTrackerMysqlSpendingDateStatsDao budgetTrackerMysqlSpendingDateStatsDao;
    private BudgetTrackerMysqlSpendingProductNameSumDao budgetTrackerMysqlSpendingProductNameSumDao;

    BudgetTrackerDatabase budgetTrackerDatabase;
    private BudgetTrackerSpendingDao budgetTrackerSpendingDao;

    private List<BudgetTrackerMysqlSpendingDto> radioSearchStoreNameList;

    private List<BudgetTrackerMysqlForeignSpendingDto> searchForeignOriginalList;

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
        budgetTrackerMysqlSpendingInsertDao = new BudgetTrackerMysqlSpendingInsertDao(application);
        budgetTrackerDatabase = BudgetTrackerDatabase.getDatabase(application);
        budgetTrackerSpendingDao = budgetTrackerDatabase.budgetTrackerSpendingDao();
        budgetTrackerMysqlSpendingDateSumDao = new BudgetTrackerMysqlSpendingDateSumDao(application);
        budgetTrackerMysqlSpendingStoreNameSumDao = new BudgetTrackerMysqlSpendingStoreNameSumDao(application);
        budgetTrackerMysqlSpendingProductTypeSumDao = new BudgetTrackerMysqlSpendingProductTypeSumDao(application);
        budgetTrackerMysqlSpendingStoreStatsDao = new BudgetTrackerMysqlSpendingStoreStatsDao(application);
        budgetTrackerMysqlSpendingProductTypeStatsDao = new BudgetTrackerMysqlSpendingProductTypeStatsDao(application);
        budgetTrackerMysqlSpendingProductNameStatsDao = new BudgetTrackerMysqlSpendingProductNameStatsDao(application);
        budgetTrackerMysqlSpendingDateStatsDao = new BudgetTrackerMysqlSpendingDateStatsDao(application);
        budgetTrackerMysqlSpendingProductNameSumDao = new BudgetTrackerMysqlSpendingProductNameSumDao(application);
        budgetTrackerMysqlConvertingOriginalDateDao = new BudgetTrackerMysqlConvertingOriginalDateDao(application);
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

    public void getDateForeignList(BudgetTrackerMysqlForeignSpendingDto budgetTrackerMysqlForeignSpendingDto, MysqlSpendingForeignListCallback callback) {

        Log.d("TAG_DTO", "getDateList: " + budgetTrackerMysqlForeignSpendingDto);
        budgetTrackerMysqlConvertingOriginalDateDao.getSearchDateForeignList(budgetTrackerMysqlForeignSpendingDto, new MysqlSpendingForeignListCallback() {
            @Override
            public void onSuccess(List<BudgetTrackerMysqlForeignSpendingDto> spendingList) {
//                Log.d("RepositoryResponse", spendingList.toString());
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                for (BudgetTrackerMysqlSpendingDto dto : spendingList) {
//                    Log.d("RepositoryResponse", dto.toString());
//                }

                for (BudgetTrackerMysqlForeignSpendingDto dto : spendingList) {
                    try {
                        Date date = dto.getDate();
                        String formattedDate = DateUtils.dateToString(date);
                        dto.setDate(DateUtils.stringToDate(formattedDate));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Log.d("RepositoryResponse", dto.toString());
                }

                searchForeignOriginalList = spendingList;
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

    public void getCalculatedDateSum(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto, MysqlSpendingSumCallback callback) {
        budgetTrackerMysqlSpendingDateSumDao.getSearchDateSum(budgetTrackerMysqlSpendingDto, new MysqlSpendingSumCallback() {

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
        budgetTrackerMysqlSpendingStoreNameSumDao.getSearchStoreNameSum(budgetTrackerMysqlSpendingDto, new MysqlSpendingSumCallback() {

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
        Log.d("STORE_TAG", "getCalculatedStoreNameSum: " + budgetTrackerMysqlSpendingDto);
        budgetTrackerMysqlSpendingProductTypeSumDao.getSearchProductTypeSum(budgetTrackerMysqlSpendingDto, new MysqlSpendingSumCallback() {

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

    public void getCalculatedProductNameSum(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto, MysqlSpendingSumCallback callback) {
        Log.d("STORE_TAG", "getCalculatedStoreNameSum: " + budgetTrackerMysqlSpendingDto);
        budgetTrackerMysqlSpendingProductNameSumDao.getSearchProductNameSum(budgetTrackerMysqlSpendingDto, new MysqlSpendingSumCallback() {

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

    /**
     * PieChart animation (store name)
     * @param budgetTrackerMysqlSpendingDto
     * @param callback
     */
    public void getSearchStoreStatsList(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto, MysqlSpendingListCallback callback) {
        budgetTrackerMysqlSpendingStoreStatsDao.getSearchStoreStatsList(budgetTrackerMysqlSpendingDto, new MysqlSpendingListCallback() {
            @Override
            public void onSuccess(List<BudgetTrackerMysqlSpendingDto> spendingList) {
                //todo make this part a method
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

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

    /**
     * PieChart animation (product type)
     * @param budgetTrackerMysqlSpendingDto
     * @param callback
     */
    public void getSearchProductTypeStatsList(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto, MysqlSpendingListCallback callback) {
        budgetTrackerMysqlSpendingProductTypeStatsDao.getSearchProductTypeStatsList(budgetTrackerMysqlSpendingDto, new MysqlSpendingListCallback() {
            @Override
            public void onSuccess(List<BudgetTrackerMysqlSpendingDto> spendingList) {
                //todo make this part a method
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

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

    /**
     * PieChart animation (product name)
     * @param budgetTrackerMysqlSpendingDto
     * @param callback
     */
    public void getSearchProductNameStatsList(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto, MysqlSpendingListCallback callback) {
        budgetTrackerMysqlSpendingProductNameStatsDao.getSearchProductNameStatsList(budgetTrackerMysqlSpendingDto, new MysqlSpendingListCallback() {
            @Override
            public void onSuccess(List<BudgetTrackerMysqlSpendingDto> spendingList) {
                //todo make this part a method
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

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

    /**
     * PieChart animation (date)
     * @param budgetTrackerMysqlSpendingDto
     * @param callback
     */
    public void getSearchDateStatsList(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto, MysqlSpendingListCallback callback) {
        budgetTrackerMysqlSpendingDateStatsDao.getSearchDateStatsList(budgetTrackerMysqlSpendingDto, new MysqlSpendingListCallback() {
            @Override
            public void onSuccess(List<BudgetTrackerMysqlSpendingDto> spendingList) {
                //todo make this part a method
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

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

    public void insert(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto, MysqlSpendingInsertCallback callback) {
        budgetTrackerMysqlSpendingInsertDao.insertIntoSpending(budgetTrackerMysqlSpendingDto, new MysqlSpendingListCallback() {
            @Override
            public void onSuccess(List<BudgetTrackerMysqlSpendingDto> spendingList) {
                for (BudgetTrackerMysqlSpendingDto dto : spendingList) {
                    Log.d("ViewModelResponse", dto.toString());
                }
                callback.onSuccess(spendingList);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        });
    }
}
