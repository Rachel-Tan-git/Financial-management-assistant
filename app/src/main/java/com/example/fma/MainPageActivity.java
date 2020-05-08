package com.example.fma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainPageActivity extends AppCompatActivity {

    private String username;

    private TextView welcomeText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        welcomeText = (TextView) findViewById(R.id.WelcomeText);
        Intent intent = getIntent();
        username = intent.getStringExtra("user_name");
        //set the Username in the TextView to show the account
        welcomeText.setText("Username: " + username);
/*
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fragments = new addRecordFragment();

        Bundle bundle=new Bundle();
        bundle.putString("user_name",username);
        fragments.setArguments(bundle);

        transaction.show(fragments[0]);
        transaction.replace(R.id.fragment, fragments[0]);

        transaction.commit();

 */

    }
}
