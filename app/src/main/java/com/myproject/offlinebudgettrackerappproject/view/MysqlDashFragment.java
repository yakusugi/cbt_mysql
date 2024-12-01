package com.myproject.offlinebudgettrackerappproject.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myproject.offlinebudgettrackerappproject.R;
import com.myproject.offlinebudgettrackerappproject.util.ConfigManager;
import com.myproject.offlinebudgettrackerappproject.util.SharedPreferencesManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MysqlDashFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MysqlDashFragment extends Fragment implements View.OnClickListener {

    private CardView spendingCard, incomeCard, bankCard, dateCard, bulkExpensecard, bulkIncomeCard, adminCard, settingsCard, aboutCard;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MysqlDashFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MysqlDashFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MysqlDashFragment newInstance(String param1, String param2) {
        MysqlDashFragment fragment = new MysqlDashFragment();
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
        View view = inflater.inflate(R.layout.fragment_mysql_dash, container, false);
        spendingCard = (CardView) view.findViewById(R.id.mysql_spending_card);
        incomeCard = (CardView) view.findViewById(R.id.mysql_income_card);
        bankCard = (CardView) view.findViewById(R.id.mysql_bank_card);
        dateCard = (CardView) view.findViewById(R.id.mysql_date_card);
        bulkExpensecard = (CardView) view.findViewById(R.id.mysql_replace_card);
        bulkIncomeCard = (CardView) view.findViewById(R.id.mysql_replace_card_income);
        adminCard = (CardView) view.findViewById(R.id.mysql_admin_card);
        settingsCard = (CardView) view.findViewById(R.id.mysql_settings_card);
        aboutCard = (CardView) view.findViewById(R.id.mysql_about_card);

        spendingCard.setOnClickListener(this);
        incomeCard.setOnClickListener(this);
        bankCard.setOnClickListener(this);
        dateCard.setOnClickListener(this);
        bulkExpensecard.setOnClickListener(this);
        bulkIncomeCard.setOnClickListener(this);
        adminCard.setOnClickListener(this);
        settingsCard.setOnClickListener(this);
        aboutCard.setOnClickListener(this);

        boolean isAdmin = checkIfUserIsAdmin();
        if (isAdmin) {
            adminCard.setVisibility(View.VISIBLE);
        } else {
            adminCard.setVisibility(View.GONE);
            settingsCard.setVisibility(View.VISIBLE);
        }

        return view;
    }

    @Override
    public void onClick(View view) {
        Fragment mF = getParentFragment();
        Intent i;
        switch (view.getId()) {
            case R.id.mysql_spending_card:
//                i = new Intent(view.getContext(), SpendingTrackerActivity.class);
//                startActivity(i);
                break;
            case R.id.mysql_income_card:
                i = new Intent(view.getContext(), MysqlIncomeActivity.class);
                startActivity(i);
                break;
            case R.id.mysql_bank_card:
                i = new Intent(view.getContext(), MysqlBankActivity.class);
                startActivity(i);
                break;
            case R.id.mysql_date_card:
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.mysql_main_container, new MysqlDateSearchFragment())
//                        .addToBackStack(null)
//                        .commit();
                i = new Intent(view.getContext(), MysqlDateSearchActivity.class);
                startActivity(i);
                break;
            case R.id.mysql_replace_card:
//                i = new Intent(view.getContext(), MysqlLoginActivity.class);
//                startActivity(i);
                break;
            case R.id.mysql_replace_card_income:
//                i = new Intent(view.getContext(), SpendingReplacementActivity.class);
//                startActivity(i);
                break;
            case R.id.mysql_admin_card:
                i = new Intent(view.getContext(), AdminDashActivity.class);
                startActivity(i);
                break;
            case R.id.mysql_settings_card:
//                i = new Intent(view.getContext(), IncomeReplacementActivity.class);
//                startActivity(i);
                break;
            case R.id.mysql_about_card:
//                i = new Intent(view.getContext(), BudgetTrackerSettingsActivity.class);
//                startActivity(i);
                break;
        }

    }

    private boolean checkIfUserIsAdmin() {
        String adminEmail = SharedPreferencesManager.getUserEmail(getContext());
        ConfigManager configManager = new ConfigManager(getContext());
        String storedAdminEmail = configManager.getAdminEmail(getContext());

        return adminEmail != null && adminEmail.equals(storedAdminEmail);
    }
}