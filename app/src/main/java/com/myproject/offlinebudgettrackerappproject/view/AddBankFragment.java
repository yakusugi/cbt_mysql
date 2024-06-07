package com.myproject.offlinebudgettrackerappproject.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.myproject.offlinebudgettrackerappproject.R;
import com.myproject.offlinebudgettrackerappproject.model.BudgetTrackerBanking;
import com.myproject.offlinebudgettrackerappproject.model.BudgetTrackerBankingViewModel;
import com.myproject.offlinebudgettrackerappproject.model.Currency;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddBankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddBankFragment extends Fragment {

    private EditText enterBankName;
    private EditText enterBankBalance;
    private Button saveButton;
    private BudgetTrackerBankingViewModel budgetTrackerBankingViewModel;
    private int budgetTrackerBankIntentId = 0;
    boolean isEdit = false;
    private Button updateBankButton;
    private Button deleteBankButton;

    SharedPreferences sharedPreferences;
    private static final String PREF_CURRENCY_FILENAME = "CURRENCY_SHARED";
    private static final String PREF_CURRENCY_VALUE = "currencyValue";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String ARG_BANKING = "banking";

    private BudgetTrackerBanking mBanking;

    private static final String ARG_REQUESTKEY = "requestKey";
    private static final String ARG_DATA = "data";

    public AddBankFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AddBankFragment newInstance(String param1, String param2) {
        AddBankFragment fragment = new AddBankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static AddBankFragment newInstance(String requestKey, BudgetTrackerBanking banking) {
        AddBankFragment fragment = new AddBankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_REQUESTKEY, requestKey);
        args.putSerializable(ARG_BANKING, banking);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBanking = (BudgetTrackerBanking) getArguments().getSerializable(ARG_BANKING);
        }
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_bank, container, false);

        enterBankName = view.findViewById(R.id.bkg_enter_bank_name);
        enterBankBalance = view.findViewById(R.id.bkg_enter_bank_balance);
        saveButton = view.findViewById(R.id.bkg_save_button);
        updateBankButton = view.findViewById(R.id.bkg_update_btn);
        deleteBankButton = view.findViewById(R.id.bkg_delete_btn);

        sharedPreferences = getActivity().getSharedPreferences(PREF_CURRENCY_FILENAME, 0);

        //選択された通貨の設定
        int currentCurrencyNum = sharedPreferences.getInt(PREF_CURRENCY_VALUE, 0);
        Currency currency = Currency.getCurrencyArrayList().get(currentCurrencyNum);

        enterBankBalance.setCompoundDrawablesWithIntrinsicBounds(currency.getCurrencyImage(), 0, 0, 0);

        budgetTrackerBankingViewModel = new ViewModelProvider(requireActivity()).get(BudgetTrackerBankingViewModel.class);

        if (mBanking != null) {
            enterBankName.setText(mBanking.getBankName());
            enterBankBalance.setText(String.valueOf(mBanking.getBankBalance()));
            isEdit = true;
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bankName = enterBankName.getText().toString();
                Double bankBalance = Double.parseDouble(enterBankBalance.getText().toString());
                BudgetTrackerBanking budgetTrackerBanking = new BudgetTrackerBanking(bankName, bankBalance);
                budgetTrackerBankingViewModel.insert(budgetTrackerBanking);
                getActivity().finish();
            }
        });


        // for update/delete
        String requestKey = null;
        BudgetTrackerBanking budgetTrackerBanking = null;
        if (getArguments() != null) {
            requestKey = getArguments().getString(ARG_REQUESTKEY);
            budgetTrackerBanking = (BudgetTrackerBanking) getArguments().getSerializable(ARG_DATA);
        }


        if (budgetTrackerBanking != null) {
            enterBankName.setText(budgetTrackerBanking.getBankName());
            enterBankBalance.setText(Integer.valueOf((int) budgetTrackerBanking.getBankBalance()));
            isEdit = true;
        }

        FragmentManager fm = getParentFragmentManager();

        BudgetTrackerBanking finalBudgetTrackerBanking = budgetTrackerBanking;
        String finalRequestKey = requestKey;
        deleteBankButton.setOnClickListener(v -> {
            if (finalBudgetTrackerBanking == null) {
                return;
            }

            BudgetTrackerBankingViewModel.deleteBudgetTrackerBanking(finalBudgetTrackerBanking);
            FragmentActivity activity = getActivity();
            if (activity != null) {
                activity.getSupportFragmentManager().popBackStack();
            }
            fm.setFragmentResult(finalRequestKey, new Bundle());
        });

        // 2022/12/07 new update button
        BudgetTrackerBanking finalBudgetTrackerBanking1 = budgetTrackerBanking;
        String finalRequestKey1 = requestKey;
        updateBankButton.setOnClickListener(v -> {
            if (finalBudgetTrackerBanking1 == null) {
                return;
            }

            String bankName = enterBankName.getText().toString();
            double bankBalance = Double.parseDouble(enterBankBalance.getText().toString());

            if (TextUtils.isEmpty(bankName) || TextUtils.isEmpty(String.valueOf(bankBalance))) {
                Snackbar.make(enterBankName, R.string.empty, Snackbar.LENGTH_SHORT).show();
            } else {
                BudgetTrackerBanking newBanking = new BudgetTrackerBanking();
                newBanking.setId(finalBudgetTrackerBanking1.getId());
                newBanking.setBankName(bankName);
                newBanking.setBankBalance(bankBalance);
                BudgetTrackerBankingViewModel.updateBudgetTrackerBanking(newBanking);

                FragmentActivity activity = getActivity();
                if (activity != null) {
                    activity.getSupportFragmentManager().popBackStack();
                }
            }
            fm.setFragmentResult(finalRequestKey1, new Bundle());
        });

        if (isEdit) {
            saveButton.setVisibility(View.GONE);
        } else {
            updateBankButton.setVisibility(View.GONE);
            deleteBankButton.setVisibility(View.GONE);
        }

        return view;
    }
}