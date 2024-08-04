package com.myproject.offlinebudgettrackerappproject.view;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.myproject.offlinebudgettrackerappproject.R;
import com.myproject.offlinebudgettrackerappproject.adapter.MysqlSearchListViewAdapter;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlSpendingDto;
import com.myproject.offlinebudgettrackerappproject.enums.SpendingType;
import com.myproject.offlinebudgettrackerappproject.model.BudgetTrackerMysqlSpendingViewModel;
import com.myproject.offlinebudgettrackerappproject.model.Currency;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingListCallback;

import java.util.ArrayList;
import java.util.List;

public class MysqlExpenseActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    BudgetTrackerMysqlSpendingViewModel budgetTrackerMysqlSpendingViewModel;

    BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto;

    List<BudgetTrackerMysqlSpendingDto> budgetTrackerMysqlSpendingDtoList = new ArrayList<>();

    private ListView searchListView;


    private static final String PREF_CURRENCY_FILENAME = "CURRENCY_SHARED";
    private static final String PREF_CURRENCY_VALUE = "currencyValue";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mysql_expense);

        searchListView = (ListView) findViewById(R.id.mysql_expense_listview);

        budgetTrackerMysqlSpendingViewModel = new ViewModelProvider.AndroidViewModelFactory(MysqlExpenseActivity.this
                .getApplication())
                .create(BudgetTrackerMysqlSpendingViewModel.class);

        sharedPreferences = getSharedPreferences(PREF_CURRENCY_FILENAME, 0);

        //選択された通貨の設定
//        int currentCurrencyNum = sharedPreferences.getInt(PREF_CURRENCY_VALUE, 0);
//        Currency currency = Currency.getCurrencyArrayList().get(currentCurrencyNum);
//        searchCalcResultTxt.setCompoundDrawablesWithIntrinsicBounds(currency.getCurrencyImage(), 0, 0, 0);











    }


    private void dateQuery(String searchKey, String dateFrom, String dateTo) {
        budgetTrackerMysqlSpendingDto = new BudgetTrackerMysqlSpendingDto(SpendingType.CURRENCY, searchKey, dateFrom, dateTo);

        budgetTrackerMysqlSpendingViewModel.getDateList(budgetTrackerMysqlSpendingDto, new MysqlSpendingListCallback() {
            @Override
            public void onSuccess(List<BudgetTrackerMysqlSpendingDto> spendingList) {
                for (BudgetTrackerMysqlSpendingDto dto : spendingList) {
                    Log.d("FragmentResponse", dto.toString());
                }
                budgetTrackerMysqlSpendingDtoList = spendingList;
//                            spendingSum = String.valueOf(budgetTrackerMysqlSpendingViewModel.getSearchStoreSum(budgetTrackerMysqlSpendingDto));
                // Update the UI with the search results
                MysqlSearchListViewAdapter adapter = new MysqlSearchListViewAdapter(MysqlExpenseActivity.this, budgetTrackerMysqlSpendingDtoList);
                searchListView.setAdapter(adapter);
                adapter.notifyDataSetChanged(); // Notify the adapter about the data change
            }

            @Override
            public void onError(String error) {
                Toast.makeText(MysqlExpenseActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                Log.d("Error Now", "onError: "+ error);


            }
        });
    }
}