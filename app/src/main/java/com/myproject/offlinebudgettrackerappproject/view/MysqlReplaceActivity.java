package com.myproject.offlinebudgettrackerappproject.view;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.myproject.offlinebudgettrackerappproject.R;
import com.myproject.offlinebudgettrackerappproject.adapter.MysqlSearchListViewAdapter;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlSpendingDto;
import com.myproject.offlinebudgettrackerappproject.enums.SpendingType;
import com.myproject.offlinebudgettrackerappproject.modal.DrumrollPickerFragment;
import com.myproject.offlinebudgettrackerappproject.model.BudgetTrackerMySqlViewModel;
import com.myproject.offlinebudgettrackerappproject.model.BudgetTrackerMysqlSpendingViewModel;
import com.myproject.offlinebudgettrackerappproject.model.Currency;
import com.myproject.offlinebudgettrackerappproject.util.DrumrollConstants;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingListCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingSumCallback;

import java.util.ArrayList;
import java.util.List;

public class MysqlReplaceActivity extends AppCompatActivity implements DrumrollPickerFragment.OnCategorySelectedListener{

    EditText currencyTxt, searchReplaceFrom, searchReplaceTo, searchReplaceToProductType;
    Button replaceBtn, replaceSearchBtn;

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
        setContentView(R.layout.activity_mysql_replace);

        searchReplaceFrom = (EditText) findViewById(R.id.mysql_replace_from);
        searchReplaceTo = (EditText) findViewById(R.id.mysql_replace_to);
        searchReplaceToProductType = (EditText) findViewById(R.id.mysql_replace_to_product_type);
        radioGroup = (RadioGroup) findViewById(R.id.mysql_replace_radio_group);
        replaceBtn = (Button) findViewById(R.id.mysql_replace_btn);
        replaceSearchBtn = (Button) findViewById(R.id.mysql_replace_search_btn);
        searchCalcResultTxt = (TextView) findViewById(R.id.mysql_replace_calc_result_txt);
        searchListView = (ListView) findViewById(R.id.mysql_replace_listview);

        TextInputLayout searchNameLayout = (TextInputLayout) findViewById(R.id.mysql_replace_to_search_name_to_layout);
        TextInputLayout searchReplaceToProductTypeLayout = findViewById(R.id.mysql_replace_to_product_type_layout);

//        budgetTrackerMysqlSpendingViewModel = new ViewModelProvider.AndroidViewModelFactory(MysqlReplaceActivity.this.getApplication()).create(BudgetTrackerMysqlSpendingViewModel .class);
        searchReplaceToProductTypeLayout.setVisibility(View.GONE);


        budgetTrackerMysqlSpendingViewModel = new ViewModelProvider.AndroidViewModelFactory(MysqlReplaceActivity.this
                .getApplication())
                .create(BudgetTrackerMysqlSpendingViewModel.class);

        sharedPreferences = getSharedPreferences(PREF_CURRENCY_FILENAME, 0);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.mysql_replace_radio_store_name:
                    case R.id.mysql_replace_radio_product_name:
                        searchNameLayout.setVisibility(View.VISIBLE);
                        searchReplaceToProductTypeLayout.setVisibility(View.GONE);
                        break;
                    case R.id.mysql_replace_radio_product_type:
                        // Make editText2 visible and editText1 gone
                        searchReplaceToProductTypeLayout.setVisibility(View.VISIBLE);
                        searchNameLayout.setVisibility(View.GONE);
//                        searchName.getParent().requestLayout();
                        break;
                }
            }
        });


        searchReplaceToProductType.setOnClickListener(v -> {
            isProductTypeSelected = true;
            DrumrollPickerFragment dialogFragment = DrumrollPickerFragment.newInstance(DrumrollConstants.LIST_KEY_MYSQL_SPENDING, "PRODUCT_TYPE");
            dialogFragment.setOnCategorySelectedListener(this);
            dialogFragment.show(getSupportFragmentManager(), "SpendingTypeDialogFragment");
        });
        isProductTypeSelected = false;

    }



    @Override
    public void onCategorySelected(String selectedCategory, String dialogType) {

    }

    @Override
    public void onCategorySelected(String selectedCategory) {

    }
}