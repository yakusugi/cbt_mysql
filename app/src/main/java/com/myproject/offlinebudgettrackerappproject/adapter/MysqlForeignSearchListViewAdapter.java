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
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlForeignSpendingDto;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerMysqlSpendingDto;

import java.util.List;
import java.util.Objects;

public class MysqlForeignSearchListViewAdapter extends ArrayAdapter<BudgetTrackerMysqlForeignSpendingDto> {

    private static final Object INVALID_POSITION = -1;
    private LiveData<List<BudgetTrackerMysqlForeignSpendingDto>> budgetTrackerMysqlSpendingDto;
    private Context context;
    private View.OnClickListener listener;

    private List<BudgetTrackerMysqlForeignSpendingDto> spendingList;

    public MysqlForeignSearchListViewAdapter(Context context, List<BudgetTrackerMysqlForeignSpendingDto> spendingList) {
        super(context, R.layout.mysql_foreign_search_list_item, spendingList);
        this.context = context;
        this.spendingList = spendingList;
    }

    //TODO Someday, I need to adapt LiveData data transactions instead of the traditional List data transactions.
//    public MysqlForeignSearchListViewAdapter(Context context, LiveData<List<BudgetTrackerMysqlForeignSpendingDto>> budgetTrackerMysqlSpendingDtoList) {
//        super(context, R.layout.mysql_foreign_search_list_item, (List<BudgetTrackerMysqlForeignSpendingDto>) budgetTrackerMysqlSpendingDtoList);
//    }

    public MysqlForeignSearchListViewAdapter(Context context, int num) {
        super(context, num);
    }

    //When tapped
    public void setOnItemClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        BudgetTrackerMysqlForeignSpendingDto spending = getItem(position);
        // Add logging to check if the adapter is getting the correct data
        Log.d("AdapterResponse", "Position: " + position + ", Data: " + spending.toString());

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.mysql_foreign_search_list_item, parent, false);
        }

        ImageView searchImageViewRow = convertView.findViewById(R.id.mysql_foreign_search_circle_image_view);
        TextView searchStoreNameRow = convertView.findViewById(R.id.mysql_foreign_search_store_name_text_row);
        TextView searchDateRow = convertView.findViewById(R.id.mysql_foreign_search_date_text_row);
        TextView searchProductRow = convertView.findViewById(R.id.mysql_foreign_search_product_name_text_row);
        TextView searchProductTypeRow = convertView.findViewById(R.id.mysql_foreign_search_product_type_text_row);
        TextView searchPriceRow = convertView.findViewById(R.id.mysql_foreign_search_price_text_row);
        TextView convertedPrice = convertView.findViewById(R.id.mysql_foreign_search_converted_price_text_row);
        TextView targetCurrencyCode = convertView.findViewById(R.id.mysql_foreign_search_target_currency_code_text_row);
        TextView conversionRate = convertView.findViewById(R.id.mysql_foreign_search_conversion_rate_text_row);
        TextView searchVatRow = convertView.findViewById(R.id.mysql_foreign_search_vat_text_row);
        TextView searchNote = convertView.findViewById(R.id.mysql_foreign_search_note_text_row);
        TextView searchCurrencyCode = convertView.findViewById(R.id.mysql_foreign_search_currency_code_text_row);
        TextView searchQuantity = convertView.findViewById(R.id.mysql_foreign_search_quantity_text_row);

        if (Objects.equals(spending.getStoreName(), "Amazon")) {
            searchImageViewRow.setImageResource(R.drawable.amazon);
        } else if (Objects.equals(spending.getStoreName(), "Starbucks")) {
            searchImageViewRow.setImageResource(R.drawable.starbucks);
        } else {
            searchImageViewRow.setImageResource(R.drawable.search_icon);
        }
        searchStoreNameRow.setText(spending.getStoreName());
        searchDateRow.setText(spending.getDate().toString());
        searchProductRow.setText(spending.getProductName());
        searchProductTypeRow.setText(spending.getProductType());
        searchPriceRow.setText(String.valueOf(spending.getPrice()));
        convertedPrice.setText(String.valueOf(spending.getConvertedPrice()));
        targetCurrencyCode.setText(String.valueOf(spending.getTargetCurrencyCode()));
        conversionRate.setText(String.valueOf(spending.getConversionRate()));
        searchVatRow.setText(String.valueOf(spending.getTaxRate()));
        searchNote.setText(String.valueOf(spending.getNotes()));
        searchCurrencyCode.setText(String.valueOf(spending.getCurrencyCode()));
        searchQuantity.setText(String.valueOf(spending.getQuantity()));

        return convertView;
    }

}
