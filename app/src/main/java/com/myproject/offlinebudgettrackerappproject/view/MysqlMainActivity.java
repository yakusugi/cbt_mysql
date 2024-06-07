package com.myproject.offlinebudgettrackerappproject.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.myproject.offlinebudgettrackerappproject.R;

public class MysqlMainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mysql_main);

        bottomNavigationView = findViewById(R.id.mysql_bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.mysql_main_container, new MysqlDashFragment()).commit();

        bottomNavigationView.setSelectedItemId(R.id.nav_mysql_dash);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.nav_mysql_dash:
                        fragment = new MysqlDashFragment();
                        break;
                    case R.id.nav_mysql_stats:
                        fragment = new MysqlStatsFragment();
                        break;
                    case R.id.nav_mysql_add:
                        fragment = new MysqlAddFragment();
                        break;
                    case R.id.nav_mysql_search:
                        fragment = new MysqlSearchFragment();
                        break;
                    case R.id.nav_mysql_exchange:
                        fragment = new MysqlConvertFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.mysql_main_container, fragment).commit();

                return false;
            }
        });

    }


}