package com.myproject.offlinebudgettrackerappproject.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.myproject.offlinebudgettrackerappproject.R;

public class MysqlBankActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mysql_bank);

        Button bankAddButton = findViewById(R.id.mysql_bank_add_btn);
        Button bankListButton = findViewById(R.id.mysql_bank_list_btn);
        Button bankDeleteButton = findViewById(R.id.mysql_bank_delete_btn);
        Button bankUpdateButton = findViewById(R.id.mysql_update_btn);

        // Load initial fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new MysqlBankAddFragment())
                    .commit();
        }

        bankAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new MysqlBankAddFragment())
                        .addToBackStack(null) // Optional: add to back stack to enable back navigation
                        .commit();
            }
        });

        bankListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new MysqlBankListFragment())
                        .addToBackStack(null) // Optional: add to back stack to enable back navigation
                        .commit();
            }
        });

    }
}