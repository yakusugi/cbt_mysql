package com.myproject.offlinebudgettrackerappproject.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.myproject.offlinebudgettrackerappproject.R;
import com.myproject.offlinebudgettrackerappproject.model.Currency;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MysqlSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MysqlSearchFragment extends Fragment {

    private static final String PREF_CURRENCY_FILENAME = "CURRENCY_SHARED";
    private static final String PREF_CURRENCY_VALUE = "currencyValue";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
        EditText searchName = (EditText) view.findViewById(R.id.mysql_search_name);
        EditText searchDateFrom = (EditText) view.findViewById(R.id.mysql_search_date_from_txt);
        EditText searchDateTo = (EditText) view.findViewById(R.id.mysql_search_date_to_txt);
        Button searchBtn = (Button) view.findViewById(R.id.mysql_search_btn);
        Button syncBtn = (Button) view.findViewById(R.id.mysql_sync_btn);
        TextView searchCalcResultTxt = (TextView) view.findViewById(R.id.mysql_search_calc_result_txt);

        //選択された通貨の設定
//        int currentCurrencyNum = sharedPreferencesCurrency.getInt(PREF_CURRENCY_VALUE, 0);
//        Currency currency = Currency.getCurrencyArrayList().get(currentCurrencyNum);
//        searchCalcResultTxt.setCompoundDrawablesWithIntrinsicBounds(currency.getCurrencyImage(), 0, 0, 0);

        // Inflate the layout for this fragment
        return view;
    }
}