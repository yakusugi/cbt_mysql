package com.myproject.offlinebudgettrackerappproject.view;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.myproject.offlinebudgettrackerappproject.R;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlBankDto;
import com.myproject.offlinebudgettrackerappproject.model.BudgetTrackerMysqlBankViewModel;
import com.myproject.offlinebudgettrackerappproject.model.BudgetTrackerMysqlSpendingViewModel;
import com.myproject.offlinebudgettrackerappproject.model.Currency;
import com.myproject.offlinebudgettrackerappproject.util.MysqlBankInsertCallback;

import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MysqlIncomeAddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MysqlIncomeAddFragment extends Fragment {

    EditText enterBankName, enterBankBalance, enterNotes, enterBankCurrencyCode;
    Button saveBankButton, updateBankButton, deleteBankButton;
    SharedPreferences sharedPreferences;
    private static final String PREF_CURRENCY_FILENAME = "CURRENCY_SHARED";
    private static final String PREF_CURRENCY_VALUE = "currencyValue";
    double vatRate;
    boolean spdBool = false;

    BudgetTrackerMysqlSpendingViewModel budgetTrackerMysqlSpendingViewModel;
    BudgetTrackerMysqlBankViewModel budgetTrackerMysqlBankViewModel;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MysqlIncomeAddFragment() {
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
    public static MysqlIncomeAddFragment newInstance(String param1, String param2) {
        MysqlIncomeAddFragment fragment = new MysqlIncomeAddFragment();
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
        View view = inflater.inflate(R.layout.fragment_mysql_bank_add, container, false);

        enterBankName = (EditText) view.findViewById(R.id.mysql_add_bank_name);
        enterBankBalance = (EditText) view.findViewById(R.id.mysql_bank_balance);
        enterNotes = (EditText) view.findViewById(R.id.mysql_bank_note_name);
        enterBankCurrencyCode = (EditText) view.findViewById(R.id.mysql_bank_currency_code);
        sharedPreferences = getActivity().getSharedPreferences(PREF_CURRENCY_FILENAME, 0);
//        budgetTrackerSpinner = (Spinner) view.findViewById(R.id.icm_budget_tracker_spinner);

        saveBankButton = (Button) view.findViewById(R.id.mysql_bank_add_btn);
        updateBankButton = (Button) view.findViewById(R.id.mysql_bank_update_btn);
        deleteBankButton = (Button) view.findViewById(R.id.mysql_bank_delete_btn);

        // Set the click listeners for the buttons
        saveBankButton.setOnClickListener(buttonClickListener);
        updateBankButton.setOnClickListener(buttonClickListener);
        deleteBankButton.setOnClickListener(buttonClickListener);

        //選択された通貨表示
        int currentCurrencyNum = sharedPreferences.getInt(PREF_CURRENCY_VALUE, 0);
        Currency currency = Currency.getCurrencyArrayList().get(currentCurrencyNum);

        enterBankBalance.setCompoundDrawablesWithIntrinsicBounds(currency.getCurrencyImage(), 0, 0, 0);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);


        budgetTrackerMysqlBankViewModel = new ViewModelProvider(requireActivity()).get(BudgetTrackerMysqlBankViewModel.class);

        // Inflate the layout for this fragment
        return view;
    }

    private final View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.mysql_bank_add_btn:
                    handleSaveButtonClick();
                    break;
                case R.id.mysql_update_btn:
                    handleUpdateButtonClick();
                    break;
                case R.id.mysql_bank_delete_btn:
                    handleDeleteButtonClick();
                    break;
            }
        }
    };

    private void handleSaveButtonClick() {

        String bankName = enterBankName.getText().toString();
        double bankBalance = Double.parseDouble(enterBankBalance.getText().toString());
        String notes = enterNotes.getText().toString();
        String currencyCode = enterBankCurrencyCode.getText().toString();

        BudgetTrackerMysqlBankDto budgetTrackerMysqlBankDto = new BudgetTrackerMysqlBankDto(bankName, bankBalance, notes, currencyCode);

        budgetTrackerMysqlBankViewModel.insert(budgetTrackerMysqlBankDto, new MysqlBankInsertCallback() {

            @Override
            public void onSuccess(List<BudgetTrackerMysqlBankDto> spendingInsertList) {
                Toast.makeText(getContext(), "Successfully added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getContext(), "failed to add", Toast.LENGTH_SHORT).show();
            }
        });

        Toast.makeText(getActivity(), "Saved Successfully", Toast.LENGTH_SHORT).show();
    }

    private void handleUpdateButtonClick() {
        // Implement your update logic here
        Toast.makeText(getActivity(), "Updated Successfully", Toast.LENGTH_SHORT).show();
    }

    private void handleDeleteButtonClick() {
        // Implement your delete logic here
        Toast.makeText(getActivity(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
    }
}