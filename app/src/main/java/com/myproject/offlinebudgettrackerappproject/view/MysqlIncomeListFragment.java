package com.myproject.offlinebudgettrackerappproject.view;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.myproject.offlinebudgettrackerappproject.R;
import com.myproject.offlinebudgettrackerappproject.adapter.MysqlIncomeListViewAdapter;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlIncomeDto;
import com.myproject.offlinebudgettrackerappproject.model.BudgetTrackerMysqlIncomeViewModel;
import com.myproject.offlinebudgettrackerappproject.model.BudgetTrackerMysqlSpendingViewModel;
import com.myproject.offlinebudgettrackerappproject.model.Currency;
import com.myproject.offlinebudgettrackerappproject.util.MysqlIncomeListCallback;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MysqlIncomeListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MysqlIncomeListFragment extends Fragment {

    EditText enterIncomeName;
    Button searchButton;
    SharedPreferences sharedPreferences;
    private static final String PREF_CURRENCY_FILENAME = "CURRENCY_SHARED";
    private static final String PREF_CURRENCY_VALUE = "currencyValue";
    double vatRate;
    boolean spdBool = false;

    private ListView incomeListView;

    BudgetTrackerMysqlSpendingViewModel budgetTrackerMysqlSpendingViewModel;

    BudgetTrackerMysqlIncomeViewModel budgetTrackerMysqlIncomeViewModel;

    List<BudgetTrackerMysqlIncomeDto> searchedIncomeList = new ArrayList<>();


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MysqlIncomeListFragment() {
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
    public static MysqlIncomeListFragment newInstance(String param1, String param2) {
        MysqlIncomeListFragment fragment = new MysqlIncomeListFragment();
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
        View view = inflater.inflate(R.layout.fragment_mysql_income_list, container, false);

        enterIncomeName = (EditText) view.findViewById(R.id.mysql_income_search_name);
        searchButton = (Button) view.findViewById(R.id.mysql_income_name_search_btn);
        sharedPreferences = getActivity().getSharedPreferences(PREF_CURRENCY_FILENAME, 0);
        incomeListView = (ListView) view.findViewById(R.id.mysql_income_name_search_listview);

        //選択された通貨表示
        int currentCurrencyNum = sharedPreferences.getInt(PREF_CURRENCY_VALUE, 0);
        Currency currency = Currency.getCurrencyArrayList().get(currentCurrencyNum);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        budgetTrackerMysqlIncomeViewModel = new ViewModelProvider(requireActivity()).get(BudgetTrackerMysqlIncomeViewModel.class);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String income = enterIncomeName.getText().toString();
                BudgetTrackerMysqlIncomeDto budgetTrackerMysqlIncomeDto = new BudgetTrackerMysqlIncomeDto(income);
                budgetTrackerMysqlIncomeViewModel.getSearchIncomeNameList(budgetTrackerMysqlIncomeDto, new MysqlIncomeListCallback() {

                    @Override
                    public void onSuccess(List<BudgetTrackerMysqlIncomeDto> incomeList) {
                        for (BudgetTrackerMysqlIncomeDto dto : incomeList) {
                            Log.d("FragmentResponse", dto.toString());
                        }
                        searchedIncomeList = incomeList;
                        MysqlIncomeListViewAdapter adapter = new MysqlIncomeListViewAdapter(getActivity(), searchedIncomeList);
                        incomeListView.setAdapter(adapter);
                        adapter.notifyDataSetChanged(); // Notify the adapter about the data change
                    }

                    @Override
                    public void onError(String error) {

                    }
                });

            }
        });

        return view;
    }


}