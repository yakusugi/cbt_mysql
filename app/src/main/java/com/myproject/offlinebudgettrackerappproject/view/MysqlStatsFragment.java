package com.myproject.offlinebudgettrackerappproject.view;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.myproject.offlinebudgettrackerappproject.R;
import com.myproject.offlinebudgettrackerappproject.adapter.MysqlSearchListViewAdapter;
import com.myproject.offlinebudgettrackerappproject.databinding.ActivityMainBinding;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlSpendingDto;
import com.myproject.offlinebudgettrackerappproject.enums.SpendingType;
import com.myproject.offlinebudgettrackerappproject.model.BudgetTrackerMysqlSpendingViewModel;
import com.myproject.offlinebudgettrackerappproject.model.BudgetTrackerSpendingAlias;
import com.myproject.offlinebudgettrackerappproject.model.BudgetTrackerSpendingAliasViewModel;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingListCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MysqlStatsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MysqlStatsFragment extends Fragment {

    private BudgetTrackerSpendingAliasViewModel budgetTrackerSpendingAliasViewModel;
    RadioGroup radioGroup;
    RadioButton radioStoreButton;
    RadioButton radioProductNameButton;
    RadioButton radioProductTypeButton;
    EditText searchName;

    EditText currencyCode;
    EditText dateFrom;
    EditText dateTo;
    Button searchBtn;
    ActivityMainBinding activityMainBinding;
    List<BudgetTrackerSpendingAlias> spendingAliasList;
    PieChart pieChart;
    ArrayList<PieEntry> pieEntries;

    BudgetTrackerMysqlSpendingViewModel budgetTrackerMysqlSpendingViewModel;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MysqlStatsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MysqlStatsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MysqlStatsFragment newInstance(String param1, String param2) {
        MysqlStatsFragment fragment = new MysqlStatsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mysql_stats, container, false);

        radioGroup = (RadioGroup) view.findViewById(R.id.mysql_stats_radio_group);
        radioStoreButton = (RadioButton) view.findViewById(R.id.mysql_stats_radio_store_name);
        radioProductNameButton = (RadioButton) view.findViewById(R.id.mysql_stats_radio_product_name);
        radioProductTypeButton = (RadioButton) view.findViewById(R.id.mysql_stats_radio_product_type);
        searchName = (EditText) view.findViewById(R.id.mysql_stats_name);
        currencyCode = (EditText) view.findViewById(R.id.mysql_stats_currency_code);
        dateFrom = (EditText) view.findViewById(R.id.mysql_stats_date_from_txt);
        dateTo = (EditText) view.findViewById(R.id.mysql_stats_date_to_txt);
        searchBtn = (Button) view.findViewById(R.id.mysql_stats_search_btn);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        pieChart = (PieChart) view.findViewById(R.id.mysql_stats_pie_chart);
        budgetTrackerSpendingAliasViewModel = new ViewModelProvider(requireActivity()).get(BudgetTrackerSpendingAliasViewModel.class);
        budgetTrackerMysqlSpendingViewModel = new ViewModelProvider(requireActivity()).get(BudgetTrackerMysqlSpendingViewModel.class);

        pieEntries = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        dateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = year + "-" + month + "-" + dayOfMonth;
                        dateFrom.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        dateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = year + "-" + month + "-" + dayOfMonth;
                        dateTo.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchKey = searchName.getText().toString();
                String searchCurrencyCode = currencyCode.getText().toString();
                String searchDateFrom = dateFrom.getText().toString();
                String searchDateTo = dateTo.getText().toString();
                if (radioGroup.getCheckedRadioButtonId() == R.id.mysql_stats_radio_store_name) {
                    BudgetTrackerMysqlSpendingDto storeDto = new BudgetTrackerMysqlSpendingDto(SpendingType.STORE, searchKey, searchCurrencyCode, searchDateFrom, searchDateTo);
                    budgetTrackerMysqlSpendingViewModel.getSearchStoreStatsList(storeDto, new MysqlSpendingListCallback() {
                        @Override
                        public void onSuccess(List<BudgetTrackerMysqlSpendingDto> spendingList) {
//                            Log.d("FragmentResponse", spendingList.toString());
                            if (spendingList == null || spendingList.isEmpty()) {
                                Toast.makeText(getContext(), "No data found!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            storePieChartShow(spendingList);
                        }

                        @Override
                        public void onError(String error) {
                            Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
                        }
                    });

                } else if (radioGroup.getCheckedRadioButtonId() == R.id.mysql_stats_radio_product_name){
//                    deleteAliasSpendingTable(() ->
//                            deleteSequence(() ->
//                                    insertProductNameDataSpendingAlias(searchDateFrom, searchDateTo, searchKey, ()->productPieChartShow())));
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.mysql_stats_radio_product_type){
//                    deleteAliasSpendingTable(() ->
//                            deleteSequence(() ->
//                                    insertProductTypeDataSpendingAlias(searchDateFrom, searchDateTo, searchKey, ()->productTypePieChartShow())));
                }

            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void storePieChartShow(List<BudgetTrackerMysqlSpendingDto> spendingList) {
        pieEntries = new ArrayList<>();
        for (BudgetTrackerMysqlSpendingDto dto : spendingList) {
            float value = (float) dto.getAliasPercentage();
            String label = dto.getProductType();
            pieEntries.add(new PieEntry(value, label));
        }
        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Store Percentage");
        pieChartAnimation(pieDataSet);
    }

    private void pieChartAnimation(PieDataSet pieDataSet) {
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextSize(20f);
        pieChart.notifyDataSetChanged();
        pieChart.invalidate();
        pieChart.setData(new PieData(pieDataSet));
        pieChart.animateXY(5000, 5000);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.getDescription().setEnabled(false);
    }
}