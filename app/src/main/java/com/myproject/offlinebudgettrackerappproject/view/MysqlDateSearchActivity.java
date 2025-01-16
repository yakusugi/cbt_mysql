package com.myproject.offlinebudgettrackerappproject.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.myproject.offlinebudgettrackerappproject.R;
import com.myproject.offlinebudgettrackerappproject.adapter.MysqlSearchListViewAdapter;
import com.myproject.offlinebudgettrackerappproject.adapter.StoreSearchListViewAdapter;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlSpendingDto;
import com.myproject.offlinebudgettrackerappproject.enums.SpendingType;
import com.myproject.offlinebudgettrackerappproject.modal.DrumrollPickerFragment;
import com.myproject.offlinebudgettrackerappproject.model.BudgetTrackerMysqlSpendingViewModel;
import com.myproject.offlinebudgettrackerappproject.model.BudgetTrackerSpending;
import com.myproject.offlinebudgettrackerappproject.model.BudgetTrackerSpendingViewModel;
import com.myproject.offlinebudgettrackerappproject.model.Currency;
import com.myproject.offlinebudgettrackerappproject.util.DrumrollConstants;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingListCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingSumCallback;

import java.util.ArrayList;
import java.util.List;

public class MysqlDateSearchActivity extends AppCompatActivity implements DrumrollPickerFragment.OnCategorySelectedListener{

    EditText currencyTxt, searchDateFrom, searchDateTo;
    Button searchBtn, syncBtn;

    List<BudgetTrackerMysqlSpendingDto> searchedSpendingList = new ArrayList<>();

    SharedPreferences sharedPreferences;

    BudgetTrackerMysqlSpendingViewModel budgetTrackerMysqlSpendingViewModel;

    BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto;

    List<BudgetTrackerMysqlSpendingDto> budgetTrackerMysqlSpendingDtoList = new ArrayList<>();

    private ListView searchListView;


    private static final String PREF_CURRENCY_FILENAME = "CURRENCY_SHARED";
    private static final String PREF_CURRENCY_VALUE = "currencyValue";


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mysql_date_search);

        currencyTxt = (EditText) findViewById(R.id.mysql_date_search_currency_txt);
        searchDateFrom = (EditText) findViewById(R.id.mysql_date_search_date_from_txt);
        searchDateTo = (EditText) findViewById(R.id.mysql_date_search_date_to_txt);
        searchBtn = (Button) findViewById(R.id.mysql_date_search_btn);
        syncBtn = (Button) findViewById(R.id.mysql_date_sync_btn);
        TextView searchCalcResultTxt = (TextView) findViewById(R.id.mysql_date_search_calc_result_txt);
        searchListView = (ListView) findViewById(R.id.mysql_date_search_listview);

        budgetTrackerMysqlSpendingViewModel = new ViewModelProvider.AndroidViewModelFactory(MysqlDateSearchActivity.this
                .getApplication())
                .create(BudgetTrackerMysqlSpendingViewModel.class);

        sharedPreferences = getSharedPreferences(PREF_CURRENCY_FILENAME, 0);

        //選択された通貨の設定
        int currentCurrencyNum = sharedPreferences.getInt(PREF_CURRENCY_VALUE, 0);
        Currency currency = Currency.getCurrencyArrayList().get(currentCurrencyNum);
        searchCalcResultTxt.setCompoundDrawablesWithIntrinsicBounds(currency.getCurrencyImage(), 0, 0, 0);

        currencyTxt.setOnTouchListener((v, event) -> {
            if (currencyTxt != null && event.getAction() == MotionEvent.ACTION_DOWN) {
                DrumrollPickerFragment dialogFragment = DrumrollPickerFragment.newInstance(DrumrollConstants.LIST_KEY_MYSQL_CURRENCY, "CURRENCY");
                dialogFragment.setOnCategorySelectedListener((DrumrollPickerFragment.OnCategorySelectedListener) this);
                dialogFragment.show(getSupportFragmentManager(), "MySqlCurrencyActivity");
                return true;
            }
            return false;
        });

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        searchDateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MysqlDateSearchActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = year + "-" + month + "-" + dayOfMonth;
                        searchDateFrom.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        searchDateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MysqlDateSearchActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = year + "-" + month + "-" + dayOfMonth;
                        searchDateTo.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });



            searchBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String searchKey = currencyTxt.getText().toString();
                    String dateFrom = searchDateFrom.getText().toString();
                    String dateTo = searchDateTo.getText().toString();

//                dateQuery(searchKey, dateFrom, dateTo);
//                //todo clicking each item to intent to add activity
                    budgetTrackerMysqlSpendingDto = new BudgetTrackerMysqlSpendingDto(SpendingType.CURRENCY, searchKey, dateFrom, dateTo);
                    budgetTrackerMysqlSpendingViewModel.getDateList(budgetTrackerMysqlSpendingDto, new MysqlSpendingListCallback() {
                        @Override
                        public void onSuccess(List<BudgetTrackerMysqlSpendingDto> spendingList) {
//                            Log.d("FragmentResponse", spendingList.toString());
                            for (BudgetTrackerMysqlSpendingDto dto : spendingList) {
                                Log.d("FragmentResponse", dto.toString());
                            }
                            budgetTrackerMysqlSpendingDtoList = spendingList;
//                            spendingSum = String.valueOf(budgetTrackerMysqlSpendingViewModel.getSearchStoreSum(budgetTrackerMysqlSpendingDto));
                            // Update the UI with the search results
                            MysqlSearchListViewAdapter adapter = new MysqlSearchListViewAdapter(MysqlDateSearchActivity.this, budgetTrackerMysqlSpendingDtoList);
                            searchListView.setAdapter(adapter);
                            adapter.notifyDataSetChanged(); // Notify the adapter about the data change
                        }

                        @Override
                        public void onError(String error) {
                            Toast.makeText(MysqlDateSearchActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                            Log.d("Error Now", "onError: " + error);
                        }
                    });

                    budgetTrackerMysqlSpendingViewModel.getCalculatedDateSum(budgetTrackerMysqlSpendingDto, new MysqlSpendingSumCallback(){

                        @Override
                        public void onSuccess(Double spendingSum) {
                            String spendingSumString = String.valueOf(spendingSum);
                            searchCalcResultTxt.setText(spendingSumString);
                        }

                        @Override
                        public void onError(String error) {

                        }
                    });

                }
            });

        syncBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchKey = currencyTxt.getText().toString();
                String dateFrom = searchDateFrom.getText().toString();
                String dateTo = searchDateTo.getText().toString();


                budgetTrackerMysqlSpendingDto = new BudgetTrackerMysqlSpendingDto(SpendingType.CURRENCY, searchKey, dateFrom, dateTo);
                budgetTrackerMysqlSpendingViewModel.getSyncSpendingList(budgetTrackerMysqlSpendingDto, new MysqlSpendingListCallback() {
                    @Override
                    public void onSuccess(List<BudgetTrackerMysqlSpendingDto> spendingList) {
//                            Log.d("FragmentResponse", spendingList.toString());
                        for (BudgetTrackerMysqlSpendingDto dto : spendingList) {
                            Log.d("FragmentResponse", dto.toString());
                        }
                        searchedSpendingList = spendingList;
//                            spendingSum = String.valueOf(budgetTrackerMysqlSpendingViewModel.getSearchStoreSum(budgetTrackerMysqlSpendingDto));
                        // Update the UI with the search results
                        MysqlSearchListViewAdapter adapter = new MysqlSearchListViewAdapter(MysqlDateSearchActivity.this, searchedSpendingList);
                        searchListView.setAdapter(adapter);
                        adapter.notifyDataSetChanged(); // Notify the adapter about the data change
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(MysqlDateSearchActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
        }

    @Override
    public void onCategorySelected(String selectedCategory, String dialogType) {
        currencyTxt.setText(selectedCategory);
    }

    @Override
    public void onCategorySelected(String selectedCategory) {

    }
}