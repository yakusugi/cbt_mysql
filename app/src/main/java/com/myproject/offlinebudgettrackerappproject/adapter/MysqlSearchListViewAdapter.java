package com.myproject.offlinebudgettrackerappproject.adapter;

import android.content.Context;
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
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlSpendingDto;
import com.myproject.offlinebudgettrackerappproject.model.BudgetTrackerSpending;

import java.util.List;

public class MysqlSearchListViewAdapter extends ArrayAdapter<BudgetTrackerMysqlSpendingDto> {

    private static final Object INVALID_POSITION = -1;
    private LiveData<List<BudgetTrackerMysqlSpendingDto>> budgetTrackerMysqlSpendingDto;
    private Context context;
    private View.OnClickListener listener;

    public MysqlSearchListViewAdapter(Context context, List<BudgetTrackerMysqlSpendingDto> budgetTrackerMysqlSpendingDtoList) {
        super(context, R.layout.mysql_search_list_item, budgetTrackerMysqlSpendingDtoList);
    }

    //TODO Someday, I need to adapt LiveData data transactions instead of the traditional List data transactions.
    public MysqlSearchListViewAdapter(Context context, LiveData<List<BudgetTrackerMysqlSpendingDto>> budgetTrackerMysqlSpendingDtoList) {
        super(context, R.layout.mysql_search_list_item, (List<BudgetTrackerMysqlSpendingDto>) budgetTrackerMysqlSpendingDtoList);
    }

    public MysqlSearchListViewAdapter(Context context, int num) {
        super(context, num);
    }

    //When tapped
    public void setOnItemClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        BudgetTrackerMysqlSpendingDto budgetTrackerMysqlSpendingDto = getItem(position);


        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.mysql_search_list_item, parent, false);
        }

        ImageView searchImageViewRow = convertView.findViewById(R.id.mysql_search_circle_image_view);
        TextView searchStoreNameRow = convertView.findViewById(R.id.mysql_search_store_name_linear_layout);
        TextView searchDateRow = convertView.findViewById(R.id.mysql_search_date_text_row);
        TextView searchProductRow = convertView.findViewById(R.id.mysql_search_product_name_text_row);
        TextView searchProductTypeRow = convertView.findViewById(R.id.mysql_search_product_type_text_row);
        TextView searchPriceRow = convertView.findViewById(R.id.mysql_search_price_text_row);
        TextView searchVatRow = convertView.findViewById(R.id.mysql_search_vat_text_row);
        TextView searchNote = convertView.findViewById(R.id.mysql_search_note_text_row);
        TextView searchCurrencyCode = convertView.findViewById(R.id.mysql_search_currency_code_text_row);
        TextView searchQuantity = convertView.findViewById(R.id.mysql_search_quantity_text_row);

        searchImageViewRow.setImageResource(R.drawable.search_icon);
        searchStoreNameRow.setText(budgetTrackerMysqlSpendingDto.getStoreName());
        searchDateRow.setText((CharSequence) budgetTrackerMysqlSpendingDto.getDate());
        searchProductRow.setText(budgetTrackerMysqlSpendingDto.getProductName());
        searchProductTypeRow.setText(budgetTrackerMysqlSpendingDto.getProductType());
        searchPriceRow.setText(String.valueOf(budgetTrackerMysqlSpendingDto.getPrice()));
        searchVatRow.setText(String.valueOf(budgetTrackerMysqlSpendingDto.getTaxRate()));
        searchNote.setText(String.valueOf(budgetTrackerMysqlSpendingDto.getNotes()));
        searchCurrencyCode.setText(String.valueOf(budgetTrackerMysqlSpendingDto.getCurrencyCode()));
        searchQuantity.setText(String.valueOf(budgetTrackerMysqlSpendingDto.getQuantity()));

        return convertView;
    }

}
