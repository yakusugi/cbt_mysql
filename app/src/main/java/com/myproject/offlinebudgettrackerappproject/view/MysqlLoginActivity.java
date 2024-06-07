package com.myproject.offlinebudgettrackerappproject.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
        //test
        enterEmail = (EditText) findViewById(R.id.mysql_login_email);
        enterPassword = (EditText) findViewById(R.id.mysql_login_password);
        loginButton = (Button) findViewById(R.id.mysql_login_btn);
        registerButton = (Button) findViewById(R.id.mysql_register_btn);

        budgetTrackerMySqlViewModel = new ViewModelProvider.AndroidViewModelFactory(MysqlLoginActivity.this
                .getApplication())
                .create(BudgetTrackerMySqlViewModel.class);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = enterEmail.getText().toString();
                String password = enterPassword.getText().toString();
                BudgetTrackerUserDto budgetTrackerUserDto = new BudgetTrackerUserDto(email, password);

                budgetTrackerMySqlViewModel.login(budgetTrackerUserDto, new LoginCallback() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(MysqlLoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                        //Share and contain user email info
                        SharedPreferences sharedPref = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("user_email", email);
                        editor.apply();

                        // Navigate to another activity if needed
                        i = new Intent(view.getContext(), MysqlMainActivity.class);
                        startActivity(i);
//                        replaceFragment(new MyqlDashboardFragment());
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        Toast.makeText(MysqlLoginActivity.this, "Login failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MysqlLoginActivity.this, MysqlRegistrationActivity.class);
                startActivity(intent);
            }
        });




    }

//    private void replaceFragment(Fragment fragment) {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.activity_mysql_login, fragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
//    }
}