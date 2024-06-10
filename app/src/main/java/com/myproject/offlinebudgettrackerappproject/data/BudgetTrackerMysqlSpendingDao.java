package com.myproject.offlinebudgettrackerappproject.data;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlSpendingDto;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerUserDto;
import com.myproject.offlinebudgettrackerappproject.util.LoginCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class BudgetTrackerMysqlSpendingDao {

    private List<BudgetTrackerMysqlSpendingDto> radioSearchStoreNameList;

    Double searchStoreSum;

    public List<BudgetTrackerMysqlSpendingDto> getSearchStoreNameList(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto) {

        String storeName = budgetTrackerMysqlSpendingDto.getStoreName();
        Date dateFrom = budgetTrackerMysqlSpendingDto.getDateFrom();
        Date dateTo = budgetTrackerMysqlSpendingDto.getDateTo();

        Log.d("TAG", "getSearchStoreNameList: " + storeName + " " + dateFrom + " " + dateTo);

        return radioSearchStoreNameList;
    }

    public Double getSearchStoreSum(BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto) {

        String storeName = budgetTrackerMysqlSpendingDto.getStoreName();
        Date dateFrom = budgetTrackerMysqlSpendingDto.getDateFrom();
        Date dateTo = budgetTrackerMysqlSpendingDto.getDateTo();

        Log.d("TAG", "getSearchStoreNameList: " + storeName + " " + dateFrom + " " + dateTo);

        return searchStoreSum;
    }


}
