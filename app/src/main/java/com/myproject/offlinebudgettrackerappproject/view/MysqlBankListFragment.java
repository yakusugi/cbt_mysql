package com.myproject.offlinebudgettrackerappproject.view;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.myproject.offlinebudgettrackerappproject.R;
import com.myproject.offlinebudgettrackerappproject.adapter.MysqlBankListViewAdapter;
import com.myproject.offlinebudgettrackerappproject.adapter.MysqlSearchListViewAdapter;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlBankDto;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlSpendingDto;
import com.myproject.offlinebudgettrackerappproject.model.BudgetTrackerMysqlBankViewModel;
import com.myproject.offlinebudgettrackerappproject.model.BudgetTrackerMysqlSpendingViewModel;
import com.myproject.offlinebudgettrackerappproject.model.Currency;
import com.myproject.offlinebudgettrackerappproject.util.MysqlBankListCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingInsertCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingListCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MysqlBankListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MysqlBankListFragment extends Fragment {

    EditText enterBankName;
    Button searchButton;
    SharedPreferences sharedPreferences;
    private static final String PREF_CURRENCY_FILENAME = "CURRENCY_SHARED";
    private static final String PREF_CURRENCY_VALUE = "currencyValue";
    double vatRate;
    boolean spdBool = false;

    private ListView bankListView;

    BudgetTrackerMysqlSpendingViewModel budgetTrackerMysqlSpendingViewModel;

    BudgetTrackerMysqlBankViewModel budgetTrackerMysqlBankViewModel;

    List<BudgetTrackerMysqlBankDto> searchedBankList = new ArrayList<>();


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MysqlBankListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MysqlAddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MysqlBankListFragment newInstance(String param1, String param2) {
        MysqlBankListFragment fragment = new MysqlBankListFragment();
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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mysql_bank_list, container, false);

        enterBankName = (EditText) view.findViewById(R.id.mysql_bank_search_name);
        searchButton = (Button) view.findViewById(R.id.mysql_bank_name_search_btn);
        sharedPreferences = getActivity().getSharedPreferences(PREF_CURRENCY_FILENAME, 0);
        bankListView = (ListView) view.findViewById(R.id.mysql_bank_name_search_listview);
//        budgetTrackerSpinner = (Spinner) view.findViewById(R.id.icm_budget_tracker_spinner);

        // Set the click listeners for the buttons


        //選択された通貨表示
        int currentCurrencyNum = sharedPreferences.getInt(PREF_CURRENCY_VALUE, 0);
        Currency currency = Currency.getCurrencyArrayList().get(currentCurrencyNum);

//        enterPrice.setCompoundDrawablesWithIntrinsicBounds(currency.getCurrencyImage(), 0, 0, 0);
//        enterVatRate.setCompoundDrawablesWithIntrinsicBounds(currency.getCurrencyImage(), 0, 0, 0);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        budgetTrackerMysqlSpendingViewModel = new ViewModelProvider(requireActivity()).get(BudgetTrackerMysqlSpendingViewModel.class);

        budgetTrackerMysqlBankViewModel = new ViewModelProvider(requireActivity()).get(BudgetTrackerMysqlBankViewModel.class);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bankName = enterBankName.getText().toString();
                BudgetTrackerMysqlBankDto budgetTrackerMysqlBankDto = new BudgetTrackerMysqlBankDto(bankName);
                budgetTrackerMysqlBankViewModel.getBankList(budgetTrackerMysqlBankDto, new MysqlBankListCallback() {
                    @Override
                    public void onSuccess(List<BudgetTrackerMysqlBankDto> bankList) {
//                            Log.d("FragmentResponse", spendingList.toString());
                        for (BudgetTrackerMysqlBankDto dto : bankList) {
                            Log.d("FragmentResponse", dto.toString());
                        }
                        searchedBankList = bankList;
//                            spendingSum = String.valueOf(budgetTrackerMysqlSpendingViewModel.getSearchStoreSum(budgetTrackerMysqlBankDto));
                        // Update the UI with the search results
                        MysqlBankListViewAdapter adapter = new MysqlBankListViewAdapter(getActivity(), searchedBankList);
                        bankListView.setAdapter(adapter);
                        adapter.notifyDataSetChanged(); // Notify the adapter about the data change
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
                    }
                });

//                getActivity().finish();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }


}