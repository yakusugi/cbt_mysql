package com.myproject.offlinebudgettrackerappproject.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.myproject.offlinebudgettrackerappproject.R;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlBankDto;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlSpendingDto;

import java.util.List;
import java.util.Objects;

public class MysqlBankListViewAdapter extends ArrayAdapter<BudgetTrackerMysqlBankDto> {

    private static final Object INVALID_POSITION = -1;
    private LiveData<List<BudgetTrackerMysqlBankDto>> budgetTrackerMysqlBankDto;
    private Context context;
    private View.OnClickListener listener;

    private List<BudgetTrackerMysqlBankDto> bankList;

    public MysqlBankListViewAdapter(Context context, List<BudgetTrackerMysqlBankDto> bankDtoList) {
        super(context, R.layout.mysql_bank_list_item, bankDtoList);
        this.context = context;
        this.bankList = bankDtoList;
    }

    //TODO Someday, I need to adapt LiveData data transactions instead of the traditional List data transactions.
    public MysqlBankListViewAdapter(Context context, LiveData<List<BudgetTrackerMysqlBankDto>> budgetTrackerMysqlBankDto) {
        super(context, R.layout.mysql_bank_list_item, (List<BudgetTrackerMysqlBankDto>) budgetTrackerMysqlBankDto);
    }

    public MysqlBankListViewAdapter(Context context, int num) {
        super(context, num);
    }

    //When tapped
    public void setOnItemClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        BudgetTrackerMysqlBankDto bank = getItem(position);
        // Add logging to check if the adapter is getting the correct data
        Log.d("AdapterResponse", "Position: " + position + ", Data: " + bank.toString());

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.mysql_bank_list_item, parent, false);
        }

        ImageView searchImageViewRow = convertView.findViewById(R.id.mysql_bank_circle_image_view);
        TextView bankNameRow = convertView.findViewById(R.id.mysql_bank_name_text_row);
        TextView bankBalanceRow = convertView.findViewById(R.id.mysql_bank_balance_text_row);
        TextView bankNotesRow = convertView.findViewById(R.id.mysql_bank_notes_text_row);
        TextView bankCurrencyCodeRow = convertView.findViewById(R.id.mysql_bank_currency_code_text_row);

        searchImageViewRow.setImageResource(R.drawable.bank2);
        bankNameRow.setText(bank.getBankName());
        bankBalanceRow.setText(String.valueOf(bank.getBankBalance()));
        bankNotesRow.setText(bank.getNotes());
        bankCurrencyCodeRow.setText(bank.getBankCurrencyCode());

        return convertView;
    }

}
