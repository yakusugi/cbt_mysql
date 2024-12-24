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
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlIncomeDto;

import java.util.List;

public class MysqlIncomeListViewAdapter extends ArrayAdapter<BudgetTrackerMysqlIncomeDto> {

    private static final Object INVALID_POSITION = -1;
    private LiveData<List<BudgetTrackerMysqlBankDto>> budgetTrackerMysqlBankDto;
    private Context context;
    private View.OnClickListener listener;

    private List<BudgetTrackerMysqlIncomeDto> incomeList;

    public MysqlIncomeListViewAdapter(Context context, List<BudgetTrackerMysqlIncomeDto> incomeDtoList) {
        super(context, R.layout.mysql_income_list_item, incomeDtoList);
        this.context = context;
        this.incomeList = incomeDtoList;
    }

    //TODO Someday, I need to adapt LiveData data transactions instead of the traditional List data transactions.
    public MysqlIncomeListViewAdapter(Context context, LiveData<List<BudgetTrackerMysqlIncomeDto>> budgetTrackerMysqlIncomeDto) {
        super(context, R.layout.mysql_income_list_item, (List<BudgetTrackerMysqlIncomeDto>) budgetTrackerMysqlIncomeDto);
    }

    public MysqlIncomeListViewAdapter(Context context, int num) {
        super(context, num);
    }

    //When tapped
    public void setOnItemClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        BudgetTrackerMysqlIncomeDto income = getItem(position);
        // Add logging to check if the adapter is getting the correct data
        Log.d("AdapterResponse", "Position: " + position + ", Data: " + income.toString());

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.mysql_income_list_item, parent, false);
        }

        ImageView searchImageViewRow = convertView.findViewById(R.id.mysql_bank_circle_image_view);
        TextView incomeNameRow = convertView.findViewById(R.id.mysql_income_name_text_row);
        TextView incomeDateRow = convertView.findViewById(R.id.mysql_income_date_text_row);
        TextView incomeCategoryRow = convertView.findViewById(R.id.mysql_income_category_text_row);
        TextView incomeTextRow = convertView.findViewById(R.id.mysql_income_text_row);
        TextView incomeNoteRow = convertView.findViewById(R.id.mysql_income_note_text_row);
        TextView incomeCurrencyCodeRow = convertView.findViewById(R.id.mysql_income_currency_code_text_row);

        searchImageViewRow.setImageResource(R.drawable.bank2);
        incomeNameRow.setText(income.getIncomeName());
        incomeDateRow.setText(income.getDate().toString());
        incomeNameRow.setText(income.getIncomeName());
        incomeTextRow.setText(String.valueOf(income.getIncome()));
        incomeCategoryRow.setText(income.getIncomeCategory());
        incomeNoteRow.setText(income.getIncomeNote());
        incomeCurrencyCodeRow.setText(income.getIncomeCurrencyCode());

        return convertView;
    }

}
