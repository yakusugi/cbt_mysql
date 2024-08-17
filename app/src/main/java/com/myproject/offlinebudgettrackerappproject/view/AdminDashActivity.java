package com.myproject.offlinebudgettrackerappproject.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.myproject.offlinebudgettrackerappproject.R;

public class AdminDashActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView adminCard;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dash);

        // Initialize the CardViews
        adminCard = findViewById(R.id.admin_users_card);

        // Set onClickListeners
        adminCard.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.admin_users_card:
                // Navigate to user management activity
                i = new Intent(AdminDashActivity.this, AdminUsersActivity.class);
                startActivity(i);
                break;
        }
    }
}
