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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.myproject.offlinebudgettrackerappproject.R;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlBankDto;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlIncomeDto;
import com.myproject.offlinebudgettrackerappproject.modal.DrumrollPickerFragment;
import com.myproject.offlinebudgettrackerappproject.model.BudgetTrackerMysqlBankViewModel;
import com.myproject.offlinebudgettrackerappproject.model.BudgetTrackerMysqlIncomeViewModel;
import com.myproject.offlinebudgettrackerappproject.model.BudgetTrackerMysqlSpendingViewModel;
import com.myproject.offlinebudgettrackerappproject.model.Currency;
import com.myproject.offlinebudgettrackerappproject.util.MysqlBankInsertCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlIncomeInsertCallback;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MysqlIncomeAddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MysqlIncomeAddFragment extends Fragment implements DrumrollPickerFragment.OnCategorySelectedListener {

    EditText enterIncomeDate, enterIncomeCategory, enterIncomeName, enterIncome, enterIncomeNote, enterIncomeCurrencyCode;
    Button saveIncomeButton, updateIncomeButton, deleteIncomeButton;
    SharedPreferences sharedPreferences;
    private static final String PREF_CURRENCY_FILENAME = "CURRENCY_SHARED";
    private static final String PREF_CURRENCY_VALUE = "currencyValue";
    double vatRate;
    boolean spdBool = false;
    BudgetTrackerMysqlIncomeDto budgetTrackerMysqlIncomeDto;
    BudgetTrackerMysqlIncomeViewModel budgetTrackerMysqlIncomeViewModel;

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

//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_your_layout, container, false);
//
//
//
//        return view;
//    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mysql_income_add, container, false);

        int[] componentIds = {R.id.mysql_income_date, R.id.mysql_income_category, R.id.mysql_income_name, R.id.mysql_income, R.id.mysql_income_note_name, R.id.mysql_income_currency_code};
        int[] buttonIds = {R.id.mysql_income_add_btn, R.id.mysql_income_update_btn, R.id.mysql_income_delete_btn};

        for (int id : componentIds) {
            EditText editText = view.findViewById(id);
            if (editText != null) {
                // Button exists, perform actions
                editText.setOnClickListener(view2 -> {
                    // Your click logic here
                });
            } else {
                // Button does not exist, handle accordingly
                Log.e("ButtonCheck", "Button with ID " + id + " does not exist.");
            }
        }

        for (int id : buttonIds) {
            Button button = view.findViewById(id);
            if (button != null) {
                // Button exists, perform actions
                button.setOnClickListener(view2 -> {
                    // Your click logic here
                });
            } else {
                // Button does not exist, handle accordingly
                Log.e("ButtonCheck", "Button with ID " + id + " does not exist.");
            }
        }

        enterIncomeDate = (EditText) view.findViewById(R.id.mysql_income_date);
        enterIncomeCategory = (EditText) view.findViewById(R.id.mysql_income_category);
        enterIncomeName = (EditText) view.findViewById(R.id.mysql_income_name);
        enterIncome = (EditText) view.findViewById(R.id.mysql_income);
        enterIncomeNote = (EditText) view.findViewById(R.id.mysql_income_note_name);
        enterIncomeCurrencyCode = (EditText) view.findViewById(R.id.mysql_income_currency_code);
        sharedPreferences = getActivity().getSharedPreferences(PREF_CURRENCY_FILENAME, 0);
//        budgetTrackerSpinner = (Spinner) view.findViewById(R.id.icm_budget_tracker_spinner);

        saveIncomeButton = (Button) view.findViewById(R.id.mysql_income_add_btn);
        updateIncomeButton = (Button) view.findViewById(R.id.mysql_income_update_btn);
        deleteIncomeButton = (Button) view.findViewById(R.id.mysql_income_delete_btn);

        // Set the click listeners for the buttons
        saveIncomeButton.setOnClickListener(buttonClickListener);
        updateIncomeButton.setOnClickListener(buttonClickListener);
        deleteIncomeButton.setOnClickListener(buttonClickListener);

        //open drumroll modal
        EditText enterIncomeCategory = view.findViewById(R.id.mysql_income_category);

        // Set EditText non-editable to focus on dialog interaction
        enterIncomeCategory.setOnClickListener(v -> {
            DrumrollPickerFragment dialogFragment = DrumrollPickerFragment.newInstance();
            dialogFragment.setOnCategorySelectedListener(this);
            dialogFragment.show(getParentFragmentManager(), "IncomeCategoryDialogFragment");
        });

        //選択された通貨表示
        int currentCurrencyNum = sharedPreferences.getInt(PREF_CURRENCY_VALUE, 0);
        Currency currency = Currency.getCurrencyArrayList().get(currentCurrencyNum);

        enterIncome.setCompoundDrawablesWithIntrinsicBounds(currency.getCurrencyImage(), 0, 0, 0);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        budgetTrackerMysqlIncomeViewModel = new ViewModelProvider(requireActivity()).get(BudgetTrackerMysqlIncomeViewModel.class);

        // Inflate the layout for this fragment
        return view;
    }

    private final View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.mysql_income_add_btn:
                    try {
                        handleSaveButtonClick();
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case R.id.mysql_income_update_btn:
//                    handleUpdateButtonClick();
                    break;
                case R.id.mysql_income_delete_btn:
//                    handleDeleteButtonClick();
                    break;
            }
        }
    };

    private void handleSaveButtonClick() throws ParseException {

        String dateString = enterIncomeDate.getText().toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date incomeDate = dateFormat.parse(dateString);
        String incomeCategory = enterIncomeCategory.getText().toString();
        String incomeName = enterIncomeName.getText().toString();
        double income = Double.parseDouble(enterIncome.getText().toString());
        String incomeNote = enterIncomeNote.getText().toString();
        String incomeCurrencyCode = enterIncomeCurrencyCode.getText().toString();

        budgetTrackerMysqlIncomeDto = new BudgetTrackerMysqlIncomeDto(incomeDate, incomeCategory, incomeName, income, incomeNote, incomeCurrencyCode);

        budgetTrackerMysqlIncomeViewModel.insert(budgetTrackerMysqlIncomeDto, new MysqlIncomeInsertCallback() {
            @Override
            public void onSuccess(List<BudgetTrackerMysqlIncomeDto> incomeInsertList) {
                Toast.makeText(getContext(), "Successfully added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getContext(), "failed to add", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //pass the selectedCategory from the modal to this fragment
    @Override
    public void onCategorySelected(String selectedCategory) {
        enterIncomeCategory.setText(selectedCategory);
    }

//    private void handleUpdateButtonClick() {
//        // Implement your update logic here
//        Toast.makeText(getActivity(), "Updated Successfully", Toast.LENGTH_SHORT).show();
//    }
//
//    private void handleDeleteButtonClick() {
//        // Implement your delete logic here
//        Toast.makeText(getActivity(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
//    }


}