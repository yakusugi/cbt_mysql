package com.myproject.offlinebudgettrackerappproject.view;

import android.app.DatePickerDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import com.myproject.offlinebudgettrackerappproject.R;
import com.myproject.offlinebudgettrackerappproject.adapter.MysqlForeignSearchListViewAdapter;
import com.myproject.offlinebudgettrackerappproject.adapter.MysqlIncomeListViewAdapter;
import com.myproject.offlinebudgettrackerappproject.adapter.MysqlSearchListViewAdapter;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlForeignSpendingDto;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlIncomeDto;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlSpendingDto;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlTargetSpendingDto;
import com.myproject.offlinebudgettrackerappproject.enums.SpendingType;
import com.myproject.offlinebudgettrackerappproject.modal.DrumrollPickerFragment;
import com.myproject.offlinebudgettrackerappproject.model.BudgetTrackerMysqlIncomeViewModel;
import com.myproject.offlinebudgettrackerappproject.model.BudgetTrackerMysqlSpendingViewModel;
import com.myproject.offlinebudgettrackerappproject.util.DrumrollConstants;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingForeignListCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingForeignSumCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingListCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingSumCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingTargetSumCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MysqlConvertFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MysqlConvertFragment extends Fragment implements DrumrollPickerFragment.OnCategorySelectedListener {

    private EditText searchName, searchDateFrom, searchDateTo;
    private Button searchBtn,syncBtn;
    private TextView originalCalcResultTxt, convertedCalcResultTxt,convertedSumResultTxt;
    private ListView searchListView;
    boolean isProductTypeSelected = false;

    BudgetTrackerMysqlSpendingViewModel budgetTrackerMysqlSpendingViewModel;
    BudgetTrackerMysqlForeignSpendingDto budgetTrackerMysqlForeignSpendingDto;
    BudgetTrackerMysqlTargetSpendingDto budgetTrackerMysqlTargetSpendingDto;
    List<BudgetTrackerMysqlForeignSpendingDto> budgetTrackerMysqlForeignDtoList = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MysqlConvertFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MysqlConvertFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MysqlConvertFragment newInstance(String param1, String param2) {
        MysqlConvertFragment fragment = new MysqlConvertFragment();
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
        View view = inflater.inflate(R.layout.fragment_mysql_convert, container, false);
        searchName = (EditText) view.findViewById(R.id.mysql_transfer_search_currency_txt);
        searchDateFrom = (EditText) view.findViewById(R.id.mysql_transfer_search_currency_date_from);
        searchDateTo = (EditText) view.findViewById(R.id.mysql_transfer_search_currency_date_to);
        searchBtn = (Button) view.findViewById(R.id.mysql_transfer_date_search_btn);
        originalCalcResultTxt = (TextView) view.findViewById(R.id.mysql_transfer_search_calc_result_txt1);
        convertedCalcResultTxt = (TextView) view.findViewById(R.id.mysql_transfer_search_calc_result_txt2);
        convertedSumResultTxt = (TextView) view.findViewById(R.id.mysql_transfer_search_calc_result_txt3);
        searchListView = (ListView) view.findViewById(R.id.mysql_transfer_date_search_listview);

        searchName.setOnClickListener(v -> {
            isProductTypeSelected = true;
            DrumrollPickerFragment dialogFragment = DrumrollPickerFragment.newInstance(DrumrollConstants.LIST_KEY_MYSQL_CURRENCY, "CURRENCY");
            dialogFragment.setOnCategorySelectedListener(this);
            dialogFragment.show(getParentFragmentManager(), "SpendingTypeDialogFragment");
        });
        isProductTypeSelected = false;

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        budgetTrackerMysqlSpendingViewModel = new ViewModelProvider(requireActivity()).get(BudgetTrackerMysqlSpendingViewModel.class);

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

//                dateQuery(searchKey, dateFrom, dateTo);
//                //todo clicking each item to intent to add activity
                budgetTrackerMysqlForeignSpendingDto = new BudgetTrackerMysqlForeignSpendingDto(searchKey, dateFrom, dateTo);
                budgetTrackerMysqlTargetSpendingDto = new BudgetTrackerMysqlTargetSpendingDto(searchKey, dateFrom, dateTo);
                budgetTrackerMysqlSpendingViewModel.getDateForeignList(budgetTrackerMysqlForeignSpendingDto, new MysqlSpendingForeignListCallback() {
                    @Override
                    public void onSuccess(List<BudgetTrackerMysqlForeignSpendingDto> spendingList) {
//                            Log.d("FragmentResponse", spendingList.toString());
                        for (BudgetTrackerMysqlForeignSpendingDto dto : spendingList) {
                            Log.d("FragmentResponse", dto.toString());
                        }
                        budgetTrackerMysqlForeignDtoList = spendingList;
//                            spendingSum = String.valueOf(budgetTrackerMysqlSpendingViewModel.getSearchStoreSum(budgetTrackerMysqlSpendingDto));
                        // Update the UI with the search results
                        MysqlForeignSearchListViewAdapter adapter = new MysqlForeignSearchListViewAdapter(requireContext(), budgetTrackerMysqlForeignDtoList);
                        searchListView.setAdapter(adapter);
                        adapter.notifyDataSetChanged(); // Notify the adapter about the data change
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(requireContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
                        Log.d("Error Now", "onError: " + error);
                    }
                });

                budgetTrackerMysqlSpendingViewModel.getSearchForeignDateSum(budgetTrackerMysqlForeignSpendingDto, new MysqlSpendingForeignSumCallback(){

                    @Override
                    public void onSuccess(Double spendingSum) {
                        String spendingSumString = String.valueOf(spendingSum);
                        originalCalcResultTxt.setText(spendingSumString);
                    }

                    @Override
                    public void onError(String error) {

                    }
                });

                budgetTrackerMysqlSpendingViewModel.getSearchTargetDateSum(budgetTrackerMysqlTargetSpendingDto, new MysqlSpendingTargetSumCallback(){

                    @Override
                    public void onSuccess(Double targetSum) {
                        String spendingSumString = String.valueOf(targetSum);
                        convertedCalcResultTxt.setText(spendingSumString);
                    }

                    @Override
                    public void onError(String error) {

                    }
                });

//                budgetTrackerMysqlSpendingViewModel.getCalculatedDateSum(budgetTrackerMysqlSpendingDto, new MysqlSpendingSumCallback(){
//
//                    @Override
//                    public void onSuccess(Double spendingSum) {
//                        String spendingSumString = String.valueOf(spendingSum);
//                        searchCalcResultTxt.setText(spendingSumString);
//                    }
//
//                    @Override
//                    public void onError(String error) {
//
//                    }
//                });

            }
        });


        // Inflate the layout for this fragment
        return view;
    }


    @Override
    public void onCategorySelected(String selectedCategory, String dialogType) {
        if (dialogType.equals("CURRENCY")) {
            searchName.setText(selectedCategory);
            isProductTypeSelected = false;
        }
    }

    @Override
    public void onCategorySelected(String selectedCategory) {

    }
}