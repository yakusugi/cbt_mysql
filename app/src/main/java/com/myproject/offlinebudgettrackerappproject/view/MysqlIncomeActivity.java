package com.myproject.offlinebudgettrackerappproject.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.myproject.offlinebudgettrackerappproject.R;

public class MysqlIncomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mysql_income);

        Button bankAddButton = findViewById(R.id.mysql_income_add_btn);
        Button bankListButton = findViewById(R.id.mysql_income_list_btn);
//        Button bankDeleteButton = findViewById(R.id.mysql_bank_delete_btn);
//        Button bankUpdateButton = findViewById(R.id.mysql_update_btn);

        // Load initial fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new MysqlIncomeAddFragment())
                    .commit();
        }

        bankAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new MysqlIncomeListFragment())
                        .addToBackStack(null) // Optional: add to back stack to enable back navigation
                        .commit();
            }
        });

        bankListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new MysqlIncomeListFragment())
                        .addToBackStack(null) // Optional: add to back stack to enable back navigation
                        .commit();
            }
        });

    }
}