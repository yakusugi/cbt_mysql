package com.myproject.offlinebudgettrackerappproject.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.myproject.offlinebudgettrackerappproject.R;
import com.myproject.offlinebudgettrackerappproject.adapter.MysqlForeignSearchListViewAdapter;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlForeignSpendingDto;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlTargetSpendingDto;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlUserCurrencyDto;
import com.myproject.offlinebudgettrackerappproject.modal.DrumrollPickerFragment;
import com.myproject.offlinebudgettrackerappproject.model.BudgetTrackerMysqlSpendingViewModel;
import com.myproject.offlinebudgettrackerappproject.model.BudgetTrackerMysqlUserCurrencyViewModel;
import com.myproject.offlinebudgettrackerappproject.util.DrumrollConstants;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingForeignListCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingForeignSumCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlSpendingTargetSumCallback;
import com.myproject.offlinebudgettrackerappproject.util.MysqlUserCurrencyCallback;

import java.util.List;

public class CurrencyModalFragment extends DialogFragment {

    private EditText searchName;
    private String searchKey;
    Button selectButton;
    boolean isCurrencySelected = false;
    BudgetTrackerMysqlUserCurrencyViewModel budgetTrackerMysqlUserCurrencyViewModel;
    BudgetTrackerMysqlUserCurrencyDto budgetTrackerMysqlUserCurrencyDto;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        budgetTrackerMysqlUserCurrencyViewModel = new ViewModelProvider(requireActivity()).get(BudgetTrackerMysqlUserCurrencyViewModel.class);

        // Remove default title bar
        if (getDialog() != null) {
            getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_currency_modal, container, false);

        // Set up button click to dismiss the dialog
        searchName = (EditText) view.findViewById(R.id.modal_select_currency_txt);
        Button closeButton = view.findViewById(R.id.close_button);
        selectButton = view.findViewById(R.id.currency_select_btn);
        closeButton.setOnClickListener(v -> dismiss());

        searchKey = searchName.getText().toString();

        budgetTrackerMysqlUserCurrencyDto = new BudgetTrackerMysqlUserCurrencyDto(searchKey);

        searchName.setOnClickListener(v -> {
            isCurrencySelected = true;
            DrumrollPickerFragment dialogFragment = DrumrollPickerFragment.newInstance(DrumrollConstants.LIST_KEY_MYSQL_CURRENCY, "CURRENCY");
//            dialogFragment.setOnCategorySelectedListener(this);

            dialogFragment.setOnCategorySelectedListener(new DrumrollPickerFragment.OnCategorySelectedListener() {
                @Override
                public void onCategorySelected(String selectedCategory, String dialogType) {
                    searchName.setText(selectedCategory); // Set selected currency in EditText
                    isCurrencySelected = false;
                }

                @Override
                public void onCategorySelected(String selectedCategory) {
                    searchName.setText(selectedCategory);
                    isCurrencySelected = false;
                }
            });

            dialogFragment.show(getParentFragmentManager(), "SpendingTypeDialogFragment");
        });


        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchKey = searchName.getText().toString();

                budgetTrackerMysqlUserCurrencyDto = new BudgetTrackerMysqlUserCurrencyDto(searchKey);


                budgetTrackerMysqlUserCurrencyViewModel.insert(budgetTrackerMysqlUserCurrencyDto, new MysqlUserCurrencyCallback(){

                    @Override
                    public void onSuccess(List<BudgetTrackerMysqlUserCurrencyDto> userCurrencyList) {
                        Context context = null;
                        Toast.makeText(context, "This is a Toast message", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(String error) {

                    }
                });

            }
        });

        return view;
    }



    @Override
    public void onStart() {
        super.onStart();
        // Set dialog size (medium-sized)
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
}
