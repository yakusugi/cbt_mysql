package com.myproject.offlinebudgettrackerappproject.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.myproject.offlinebudgettrackerappproject.R;
import com.myproject.offlinebudgettrackerappproject.dto.BudgetTrackerUserDto;
import com.myproject.offlinebudgettrackerappproject.model.BudgetTrackerMySqlViewModel;
import com.myproject.offlinebudgettrackerappproject.model.BudgetTrackerSpendingViewModel;
import com.myproject.offlinebudgettrackerappproject.util.Callback;
import com.myproject.offlinebudgettrackerappproject.util.LoginCallback;
import com.myproject.offlinebudgettrackerappproject.util.SharedPreferencesManager;

import androidx.fragment.app.Fragment;
import android.content.SharedPreferences;

public class MysqlLoginActivity extends AppCompatActivity {

    EditText enterEmail, enterPassword;
    Button loginButton, registerButton;

    BudgetTrackerUserDto budgetTrackerUserDto;

    BudgetTrackerMySqlViewModel budgetTrackerMySqlViewModel;

    Intent i;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mysql_login);

        enterEmail = (EditText) findViewById(R.id.mysql_login_email);
        enterPassword = (EditText) findViewById(R.id.mysql_login_password);
        loginButton = (Button) findViewById(R.id.mysql_login_btn);
        registerButton = (Button) findViewById(R.id.mysql_register_btn);

        Log.d("LOGIN_TAG", "Views initialized");

        budgetTrackerMySqlViewModel = new ViewModelProvider.AndroidViewModelFactory(MysqlLoginActivity.this.getApplication()).create(BudgetTrackerMySqlViewModel.class);

        Log.d("LOGIN_TAG", "onCreate: ViewModel and views initialized");

        if (enterEmail != null && enterPassword != null) {
            enterEmail.setError("Email cannot be empty");
            enterPassword.setError("Password cannot be empty");
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("LOGIN_TAG", "Login button clicked");

                String email = enterEmail.getText().toString();
                String password = enterPassword.getText().toString();

                Log.d("LOGIN_TAG", "Email: " + email + ", Password: " + password);

//                if (email.isEmpty()) {
//                    enterEmail.setTextColor(Color.BLUE);
//                    enterEmail.setError("Email cannot be empty");
//                    return;
//                }
//
//                if (password.isEmpty()) {
//                    enterEmail.setTextColor(Color.RED);
//                    enterPassword.setError("Password cannot be empty");
//                    return;
//                }

                BudgetTrackerUserDto budgetTrackerUserDto = new BudgetTrackerUserDto(email, password);

                budgetTrackerMySqlViewModel.login(budgetTrackerUserDto, new LoginCallback() {
                    @Override
                    public void onSuccess() {
                        Log.d("LOGIN_TAG", "Login successful");
                        Toast.makeText(MysqlLoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                        //Share and contain user email info
                        SharedPreferences sharedPref = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("user_email", email);
                        SharedPreferencesManager.saveUserEmail(MysqlLoginActivity.this, email);
                        editor.apply();

                        // Navigate to another activity if needed
                        i = new Intent(view.getContext(), MysqlMainActivity.class);
                        startActivity(i);
//                        replaceFragment(new MyqlDashboardFragment());
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        Log.d("LOGIN_TAG", "Login failed: " + errorMessage);
                        Toast.makeText(MysqlLoginActivity.this, "Login failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

}