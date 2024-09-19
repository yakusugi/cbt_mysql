package com.myproject.offlinebudgettrackerappproject.view;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.myproject.offlinebudgettrackerappproject.R;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlSpendingDto;
import com.myproject.offlinebudgettrackerappproject.model.BudgetTrackerMysqlSpendingViewModel;
import com.myproject.offlinebudgettrackerappproject.model.Currency;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingInsertCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    EditText enterDate, enterStoreName, enterProductName, enterProductType, enterVatRate, enterPrice, enterNotes, enterCurrencyCode, enterQuantity;
    RadioGroup radioGroup;
    Button saveButton, updateButton, deleteButton;
    SharedPreferences sharedPreferences;
    private static final String PREF_CURRENCY_FILENAME = "CURRENCY_SHARED";
    private static final String PREF_CURRENCY_VALUE = "currencyValue";
    double vatRate;
    boolean spdBool = false;

    BudgetTrackerMysqlSpendingViewModel budgetTrackerMysqlSpendingViewModel;


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

        enterDate = (EditText) view.findViewById(R.id.mysql_add_date_name);
        enterStoreName = (EditText) view.findViewById(R.id.mysql_add_store_name);
        enterProductName = (EditText) view.findViewById(R.id.mysql_add_product_name);
        enterProductType = (EditText) view.findViewById(R.id.mysql_add_product_type);
        enterVatRate = (EditText) view.findViewById(R.id.mysql_add_vat_rate);
        enterPrice = (EditText) view.findViewById(R.id.mysql_add_price);
        enterNotes = (EditText) view.findViewById(R.id.mysql_add_note_name);
        radioGroup = (RadioGroup) view.findViewById(R.id.mysql_add_radio_group);
        enterCurrencyCode = (EditText) view.findViewById(R.id.mysql_add_currency_code);
        enterQuantity = (EditText) view.findViewById(R.id.mysql_add_quantity);
        saveButton = (Button) view.findViewById(R.id.mysql_add_btn);
        updateButton = (Button) view.findViewById(R.id.mysql_update_btn);
        deleteButton = (Button) view.findViewById(R.id.mysql_delete_btn);
        sharedPreferences = getActivity().getSharedPreferences(PREF_CURRENCY_FILENAME, 0);
//        budgetTrackerSpinner = (Spinner) view.findViewById(R.id.icm_budget_tracker_spinner);

        // Set the click listeners for the buttons
        saveButton.setOnClickListener(buttonClickListener);
        updateButton.setOnClickListener(buttonClickListener);
        deleteButton.setOnClickListener(buttonClickListener);

        //選択された通貨表示
        int currentCurrencyNum = sharedPreferences.getInt(PREF_CURRENCY_VALUE, 0);
        Currency currency = Currency.getCurrencyArrayList().get(currentCurrencyNum);

        enterPrice.setCompoundDrawablesWithIntrinsicBounds(currency.getCurrencyImage(), 0, 0, 0);
        enterVatRate.setCompoundDrawablesWithIntrinsicBounds(currency.getCurrencyImage(), 0, 0, 0);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        enterDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = year + "-" + month + "-" + dayOfMonth;
                        enterDate.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        budgetTrackerMysqlSpendingViewModel = new ViewModelProvider(requireActivity()).get(BudgetTrackerMysqlSpendingViewModel.class);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.mysql_vat_no) {
                    enterVatRate.setEnabled(false);
                    enterVatRate.setText("");  // Optionally clear the VAT rate if disabled
                } else if (checkedId == R.id.mysql_vat_yes) {
                    enterVatRate.setEnabled(true);
                }
            }
        });

        //        todo: July 27th, 2022: Gray out VAT EditText (It disables when yes is chosen)
        if (radioGroup.getCheckedRadioButtonId() == R.id.mysql_vat_no) {
            enterVatRate.setEnabled(false);
        } else if (radioGroup.getCheckedRadioButtonId() == R.id.mysql_vat_yes) {
            enterVatRate.setEnabled(true);
        }

//        saveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Date date = (Date) enterDate.getText();
//                String storeName = enterStoreName.getText().toString();
//                String productName = enterProductName.getText().toString();
//                String productType = enterProductType.getText().toString();
//                vatRate = Double.parseDouble(enterVatRate.getText().toString());
//                double price = Double.parseDouble(enterPrice.getText().toString());
//                if (radioGroup.getCheckedRadioButtonId() == R.id.mysql_vat_no) {
//                    vatRate = 0.0;
//                    enterVatRate.setEnabled(false);
//                } else {
//                    spdBool = true;
//                    vatRate = Double.parseDouble(enterVatRate.getText().toString());
//                    price = price * vatRate;
//                }
//                String notes = enterNotes.getText().toString();
//                String currencyCode = enterCurrencyCode.getText().toString();
//                int quantity = Integer.parseInt(enterQuantity.getText().toString());
//
//                BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto = new BudgetTrackerMysqlSpendingDto(date, storeName, productName, productType, price, vatRate, notes, currencyCode, quantity);
//                budgetTrackerSpendingViewModel.insert(budgetTrackerSpending);
//                if (spinnerText != null) {
//                    double spendingNum = budgetTrackerSpending.getPrice();
//                    budgetTrackerBankingViewModel.updateSubtraction(spendingNum, spinnerText);
//                } else {
//                    //Todo Need to make this a snackbar
//                    Toast.makeText(getActivity(), "Insert a bank record", Toast.LENGTH_SHORT).show();
//                }
//                getActivity().finish();
//            }
//        });

        // Inflate the layout for this fragment
        return view;
    }

    private final View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.mysql_add_btn:
                    handleSaveButtonClick();
                    break;
                case R.id.mysql_update_btn:
                    handleUpdateButtonClick();
                    break;
                case R.id.mysql_delete_btn:
                    handleDeleteButtonClick();
                    break;
            }
        }
    };

    private void handleSaveButtonClick() {
        // Get the date string from the EditText
        String dateString = enterDate.getText().toString();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        Date date = null;
        try {
            // Parse the date string into a Date object
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            // Show an error message to the user if parsing fails
            Toast.makeText(getActivity(), "Invalid date format", Toast.LENGTH_SHORT).show();
            return;
        }

//        date = (Date) enterDate.getText();
        String storeName = enterStoreName.getText().toString();
        String productName = enterProductName.getText().toString();
        String productType = enterProductType.getText().toString();
        double price = Double.parseDouble(enterPrice.getText().toString());
        double vatRate = radioGroup.getCheckedRadioButtonId() == R.id.mysql_vat_no ? 0.0 : Double.parseDouble(enterVatRate.getText().toString());
        String notes = enterNotes.getText().toString();
        String currencyCode = enterCurrencyCode.getText().toString();
        int quantity = Integer.parseInt(enterQuantity.getText().toString());

        BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto = new BudgetTrackerMysqlSpendingDto(date, storeName, productName, productType, price, vatRate, notes, currencyCode, quantity);

        budgetTrackerMysqlSpendingViewModel.insert(budgetTrackerMysqlSpendingDto, new MysqlSpendingInsertCallback() {
            @Override
            public void onSuccess(List<BudgetTrackerMysqlSpendingDto> spendingInsertList) {
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