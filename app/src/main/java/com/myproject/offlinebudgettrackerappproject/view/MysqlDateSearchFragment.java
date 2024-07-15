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
import com.myproject.offlinebudgettrackerappproject.enums.SpendingType;
import com.myproject.offlinebudgettrackerappproject.model.BudgetTrackerMysqlSpendingViewModel;
import com.myproject.offlinebudgettrackerappproject.model.BudgetTrackerSpending;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingListCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MysqlDateSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MysqlDateSearchFragment extends Fragment {

    List<BudgetTrackerMysqlSpendingDto> searchedSpendingList = new ArrayList<>();

    BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto;

    String spendingSum = "";

    int searchMode = 0;

    private ListView searchListView;

    private static final String PREF_CURRENCY_FILENAME = "CURRENCY_SHARED";
    private static final String PREF_CURRENCY_VALUE = "currencyValue";

    ActivityMainBinding activityMainBinding;

    SharedPreferences sharedPreferences;

    BudgetTrackerMysqlSpendingViewModel budgetTrackerMysqlSpendingViewModel;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MysqlDateSearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MysqlDateSearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MysqlDateSearchFragment newInstance(String param1, String param2) {
        MysqlDateSearchFragment fragment = new MysqlDateSearchFragment();
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
        // Inflate the layout for this fragment

        SharedPreferences sharedPref = requireContext().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String email = sharedPref.getString("user_email", null);

        SharedPreferences sharedPreferencesCurrency = null;
        View view = inflater.inflate(R.layout.fragment_mysql_date_search, container, false);
        searchListView = (ListView) view.findViewById(R.id.mysql_date_search_listview);
//        view = inflater.inflate(R.layout.fragment_mysql_search, container, false);
        Log.d("TAG", "onCreateView: test");
        EditText searchDateFrom = (EditText) view.findViewById(R.id.mysql_date_search_date_from_txt);
        EditText searchDateTo = (EditText) view.findViewById(R.id.mysql_date_search_date_to_txt);
        Button searchBtn = (Button) view.findViewById(R.id.mysql_date_search_btn);
        Button syncBtn = (Button) view.findViewById(R.id.mysql_date_sync_btn);
        TextView searchCalcResultTxt = (TextView) view.findViewById(R.id.mysql_date_search_calc_result_txt);

        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());

        sharedPreferences = getActivity().getSharedPreferences(PREF_CURRENCY_FILENAME, 0);

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
                String dateFrom = searchDateFrom.getText().toString();
                String dateTo = searchDateTo.getText().toString();

                BudgetTrackerMysqlSpendingDto dateDto = new BudgetTrackerMysqlSpendingDto(dateFrom, dateTo);
                budgetTrackerMysqlSpendingViewModel.getDateList(dateDto, new MysqlSpendingListCallback() {
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


//                searchListView.setAdapter(new MysqlStoreNameSearchListViewAdapter(getActivity(), searchedSpendingList));
                searchCalcResultTxt.setText(spendingSum);
                searchListView.setOnItemClickListener((adapterView, view1, position, id) -> {
                    MainActivity mainActivity = ((MainActivity) getActivity());
                    if (mainActivity != null) {
                        Intent intent = new Intent(getActivity(), AddBudgetTracker.class);
                        intent.putExtra(AddBudgetTracker.EXTRA_DATA, (BudgetTrackerSpending) searchListView.getItemAtPosition(position));
                        startActivity(intent);

                    }
                });
            }
        });


        return view;
    }
}