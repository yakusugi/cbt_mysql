package com.myproject.offlinebudgettrackerappproject.view;

import android.annotation.SuppressLint;
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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.myproject.offlinebudgettrackerappproject.R;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlSpendingDto;
import com.myproject.offlinebudgettrackerappproject.modal.DrumrollPickerFragment;
import com.myproject.offlinebudgettrackerappproject.model.BudgetTrackerMysqlSpendingViewModel;
import com.myproject.offlinebudgettrackerappproject.util.DrumrollConstants;
import com.myproject.offlinebudgettrackerappproject.util.ProductNameReplaceCallback;
import com.myproject.offlinebudgettrackerappproject.util.ProductTypeReplaceCallback;
import com.myproject.offlinebudgettrackerappproject.util.StoreNameAverageCallback;

import java.util.ArrayList;
import java.util.List;

public class MysqlAverageActivity extends AppCompatActivity implements DrumrollPickerFragment.OnCategorySelectedListener{

    EditText currencyTxt, search, searchProductType, searchDateFrom, searchDateTo;;
    Button averageBtn, replaceSearchBtn;

    RadioGroup radioGroup;

    TextView searchCalcResultTxt;

    List<BudgetTrackerMysqlSpendingDto> searchedSpendingList = new ArrayList<>();

    SharedPreferences sharedPreferences;

    BudgetTrackerMysqlSpendingViewModel budgetTrackerMysqlSpendingViewModel;

    BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto;

    List<BudgetTrackerMysqlSpendingDto> budgetTrackerMysqlSpendingDtoList = new ArrayList<>();

    private ListView searchListView;

    boolean isProductTypeSelected = false;


    private static final String PREF_CURRENCY_FILENAME = "CURRENCY_SHARED";
    private static final String PREF_CURRENCY_VALUE = "currencyValue";


    @SuppressLint({"ClickableViewAccessibility", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mysql_average);

        searchDateFrom = (EditText) findViewById(R.id.mysql_average_date_from_txt);
        searchDateTo = (EditText) findViewById(R.id.mysql_average_date_to_txt);
        search = (EditText) findViewById(R.id.mysql_average_search_name);
        searchProductType = (EditText) findViewById(R.id.mysql_average_to_search_name_layout_product_type);
        radioGroup = (RadioGroup) findViewById(R.id.mysql_average_radio_group);
        averageBtn = (Button) findViewById(R.id.mysql_average_btn);
        searchCalcResultTxt = (TextView) findViewById(R.id.mysql_average_calc_result_txt);
        searchListView = (ListView) findViewById(R.id.mysql_average_listview);

        TextInputLayout searchNameLayout = (TextInputLayout) findViewById(R.id.mysql_average_to_search_name_layout);
        TextInputLayout searchNameLayoutProductTypeLayout = findViewById(R.id.mysql_average_to_search_type_layout);

//        budgetTrackerMysqlSpendingViewModel = new ViewModelProvider.AndroidViewModelFactory(MysqlReplaceActivity.this.getApplication()).create(BudgetTrackerMysqlSpendingViewModel .class);
        searchNameLayoutProductTypeLayout.setVisibility(View.GONE);


        budgetTrackerMysqlSpendingViewModel = new ViewModelProvider.AndroidViewModelFactory(MysqlAverageActivity.this
                .getApplication())
                .create(BudgetTrackerMysqlSpendingViewModel.class);

        sharedPreferences = getSharedPreferences(PREF_CURRENCY_FILENAME, 0);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.mysql_average_radio_store_name:
                    case R.id.mysql_average_radio_product_name:
                        searchNameLayout.setVisibility(View.VISIBLE);
                        searchNameLayoutProductTypeLayout.setVisibility(View.GONE);
                        break;
                    case R.id.mysql_average_radio_product_type:
                        // Make editText2 visible and editText1 gone
                        searchNameLayoutProductTypeLayout.setVisibility(View.VISIBLE);
                        searchNameLayout.setVisibility(View.GONE);
                        break;
                }
            }
        });


        searchProductType.setOnClickListener(v -> {
            isProductTypeSelected = true;
            DrumrollPickerFragment dialogFragment = DrumrollPickerFragment.newInstance(DrumrollConstants.LIST_KEY_MYSQL_SPENDING, "PRODUCT_TYPE");
            dialogFragment.setOnCategorySelectedListener(this);
            dialogFragment.show(getSupportFragmentManager(), "SpendingTypeDialogFragment");
        });
        isProductTypeSelected = false;

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        searchDateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MysqlAverageActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                        MysqlAverageActivity.this, new DatePickerDialog.OnDateSetListener() {
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

        averageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("averageBtn", "onClick: tapped");
                String dateFrom = searchDateFrom.getText().toString();
                String dateTo = searchDateTo.getText().toString();
                String searchKey = search.getText().toString();

                BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto = new BudgetTrackerMysqlSpendingDto();
                budgetTrackerMysqlSpendingDto.setDateFrom(dateFrom);
                budgetTrackerMysqlSpendingDto.setDateTo(dateTo);
                budgetTrackerMysqlSpendingDto.setStoreName(searchKey);

                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.mysql_average_radio_store_name:
                        if (dateFrom.isEmpty() || dateTo.isEmpty()) {
                            Toast.makeText(MysqlAverageActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        budgetTrackerMysqlSpendingViewModel.getStoreNameAverage(
                                budgetTrackerMysqlSpendingDto,
                                new StoreNameAverageCallback() {

                                    @Override
                                    public void onSuccess(Double average) {
                                        String spendingAverageString = String.valueOf(average);
                                        searchCalcResultTxt.setText(spendingAverageString);
                                    }

                                    @Override
                                    public void onError(String errorMessage) {
                                        Toast.makeText(MysqlAverageActivity.this, "Error: " + errorMessage, Toast.LENGTH_LONG).show();
                                    }
                                }
                        );
                        break;


//                    case R.id.mysql_replace_radio_product_name:
//                        budgetTrackerMysqlSpendingViewModel.replaceProductName(
//                                replaceFrom,
//                                replaceTo,
//                                new ProductNameReplaceCallback() {
//                                    @Override
//                                    public void onSuccess(int affectedRows) {
//                                        Toast.makeText(MysqlAverageActivity.this, "Replaced in " + affectedRows + " rows", Toast.LENGTH_LONG).show();
//                                    }
//
//                                    @Override
//                                    public void onError(String errorMessage) {
//                                        Toast.makeText(MysqlAverageActivity.this, "Error: " + errorMessage, Toast.LENGTH_LONG).show();
//                                    }
//                                }
//                        );
//                        break;
//
//                    case R.id.mysql_replace_radio_product_type:
//                        budgetTrackerMysqlSpendingViewModel.replaceProductType(
//                                replaceFrom,
//                                replaceTo,
//                                new ProductTypeReplaceCallback() {
//                                    @Override
//                                    public void onSuccess(int affectedRows) {
//                                        Toast.makeText(MysqlAverageActivity.this, "Replaced in " + affectedRows + " rows", Toast.LENGTH_LONG).show();
//                                    }
//
//                                    @Override
//                                    public void onError(String errorMessage) {
//                                        Toast.makeText(MysqlAverageActivity.this, "Error: " + errorMessage, Toast.LENGTH_LONG).show();
//                                    }
//                                }
//                        );
//                        break;
                }

            }
        });

    }





    @Override
    public void onCategorySelected(String selectedCategory, String dialogType) {
        searchProductType.setText(selectedCategory);
    }

    @Override
    public void onCategorySelected(String selectedCategory) {

    }
}