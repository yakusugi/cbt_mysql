package com.myproject.offlinebudgettrackerappproject.view;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.myproject.offlinebudgettrackerappproject.R;
import com.myproject.offlinebudgettrackerappproject.adapter.MysqlSearchListViewAdapter;
import com.myproject.offlinebudgettrackerappproject.databinding.ActivityMainBinding;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlSpendingDto;
import com.myproject.offlinebudgettrackerappproject.model.BudgetTrackerMysqlSpendingViewModel;
import com.myproject.offlinebudgettrackerappproject.model.BudgetTrackerSpending;
import com.myproject.offlinebudgettrackerappproject.model.Currency;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingListCallback;
import com.myproject.offlinebudgettrackerappproject.enums.SpendingType;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MysqlSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MysqlSearchFragment extends Fragment {

    List<BudgetTrackerMysqlSpendingDto> searchedSpendingList = new ArrayList<>();

    BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto;

    String spendingSum = "";

    int searchMode = 0;

    private ListView searchListView;

    private static final String PREF_CURRENCY_FILENAME = "CURRENCY_SHARED";
    private static final String PREF_CURRENCY_VALUE = "currencyValue";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RadioGroup radioGroup;

    ActivityMainBinding activityMainBinding;

    SharedPreferences sharedPreferences;

    BudgetTrackerMysqlSpendingViewModel budgetTrackerMysqlSpendingViewModel;

    public MysqlSearchFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MysqlSearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MysqlSearchFragment newInstance(String param1, String param2) {
        MysqlSearchFragment fragment = new MysqlSearchFragment();
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

            // Retrieve email from SharedPreferences

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SharedPreferences sharedPref = requireContext().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String email = sharedPref.getString("user_email", null);

        SharedPreferences sharedPreferencesCurrency = null;
        View view = inflater.inflate(R.layout.fragment_mysql_search, container, false);
        radioGroup = (RadioGroup) view.findViewById(R.id.mysql_search_radio_group);
        searchListView = (ListView) view.findViewById(R.id.mysql_search_listview);
//        view = inflater.inflate(R.layout.fragment_mysql_search, container, false);
        Log.d("TAG", "onCreateView: test");
        EditText searchName = (EditText) view.findViewById(R.id.mysql_search_name);
        EditText searchDateFrom = (EditText) view.findViewById(R.id.mysql_search_date_from_txt);
        EditText searchDateTo = (EditText) view.findViewById(R.id.mysql_search_date_to_txt);
        Button searchBtn = (Button) view.findViewById(R.id.mysql_search_btn);
        Button syncBtn = (Button) view.findViewById(R.id.mysql_sync_btn);
        TextView searchCalcResultTxt = (TextView) view.findViewById(R.id.mysql_search_calc_result_txt);

        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());

        sharedPreferences = getActivity().getSharedPreferences(PREF_CURRENCY_FILENAME, 0);

        //選択された通貨の設定
        int currentCurrencyNum = sharedPreferences.getInt(PREF_CURRENCY_VALUE, 0);
        Currency currency = Currency.getCurrencyArrayList().get(currentCurrencyNum);
        searchCalcResultTxt.setCompoundDrawablesWithIntrinsicBounds(currency.getCurrencyImage(), 0, 0, 0);

        budgetTrackerMysqlSpendingViewModel = new ViewModelProvider(requireActivity()).get(BudgetTrackerMysqlSpendingViewModel.class);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        searchDateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(), new DatePickerDialog.OnDateSetListener() {
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
                        getActivity(), new DatePickerDialog.OnDateSetListener() {
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
                String searchKey = searchName.getText().toString();
                String dateFrom = searchDateFrom.getText().toString();
                String dateTo = searchDateTo.getText().toString();

//                budgetTrackerMysqlSpendingDto = new BudgetTrackerMysqlSpendingDto(searchKey, dateFrom, dateTo);

                if (radioGroup.getCheckedRadioButtonId() == R.id.mysql_search_radio_store_name) {
                    BudgetTrackerMysqlSpendingDto storeDto = new BudgetTrackerMysqlSpendingDto(SpendingType.STORE, searchKey, dateFrom, dateTo);
                    budgetTrackerMysqlSpendingViewModel.getSearchStoreNameList(storeDto, new MysqlSpendingListCallback() {
                        @Override
                        public void onSuccess(List<BudgetTrackerMysqlSpendingDto> spendingList) {
//                            Log.d("FragmentResponse", spendingList.toString());
                            for (BudgetTrackerMysqlSpendingDto dto : spendingList) {
                                Log.d("FragmentResponse", dto.toString());
                            }
                            searchedSpendingList = spendingList;
//                            spendingSum = String.valueOf(budgetTrackerMysqlSpendingViewModel.getSearchStoreSum(budgetTrackerMysqlSpendingDto));
                            // Update the UI with the search results
                            MysqlSearchListViewAdapter adapter = new MysqlSearchListViewAdapter(getActivity(), searchedSpendingList);
                            searchListView.setAdapter(adapter);
                            adapter.notifyDataSetChanged(); // Notify the adapter about the data change
                        }

                        @Override
                        public void onError(String error) {
                            Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else if (radioGroup.getCheckedRadioButtonId() == R.id.mysql_search_radio_product_name) {

                    BudgetTrackerMysqlSpendingDto productDto = new BudgetTrackerMysqlSpendingDto(SpendingType.PRODUCT_NAME, searchKey, dateFrom, dateTo);
                    budgetTrackerMysqlSpendingViewModel.getSearchProductNameList(productDto, new MysqlSpendingListCallback() {
                        @Override
                        public void onSuccess(List<BudgetTrackerMysqlSpendingDto> spendingList) {
//                            Log.d("FragmentResponse", spendingList.toString());
                            for (BudgetTrackerMysqlSpendingDto dto : spendingList) {
                                Log.d("FragmentResponse", dto.toString());
                            }
                            searchedSpendingList = spendingList;
//                            spendingSum = String.valueOf(budgetTrackerMysqlSpendingViewModel.getSearchStoreSum(budgetTrackerMysqlSpendingDto));
                            // Update the UI with the search results
                            MysqlSearchListViewAdapter adapter = new MysqlSearchListViewAdapter(getActivity(), searchedSpendingList);
                            searchListView.setAdapter(adapter);
                            adapter.notifyDataSetChanged(); // Notify the adapter about the data change
                        }

                        @Override
                        public void onError(String error) {
                            Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
                        }
                    });
//                } else if (radioGroup.getCheckedRadioButtonId() == R.id.mysql_search_radio_product_type) {
//                    searchedSpendingList = budgetTrackerMysqlSpendingViewModel.getSearchProductTypeList(budgetTrackerMysqlSpendingDto);
//                    spendingSum = String.valueOf(budgetTrackerMysqlSpendingViewModel.getSearchProductTypeSum(budgetTrackerMysqlSpendingDto));
//                    searchMode = 2;
                }

//                searchListView.setAdapter(new MysqlStoreNameSearchListViewAdapter(getActivity(), searchedSpendingList));
                searchCalcResultTxt.setText(spendingSum);
                searchListView.setOnItemClickListener((adapterView, view1, position, id) -> {
                    MainActivity mainActivity = ((MainActivity)getActivity());
                    if(mainActivity != null) {
                        Intent intent = new Intent(getActivity(), AddBudgetTracker.class);
                        intent.putExtra(AddBudgetTracker.EXTRA_DATA, (BudgetTrackerSpending)searchListView.getItemAtPosition(position));
                        startActivity(intent);

                    }
                });
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}