package com.example.fma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

public class MainPageActivity extends AppCompatActivity {

    private String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Intent intent = getIntent();
        username = intent.getStringExtra("user_name");
        /*Fragment frag;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        frag = new addRecordFragment();
        Bundle bundle=new Bundle();
        bundle.putString("user_name",username);
        frag.setArguments(bundle);

        fragmentTransaction.replace(R.id.fragment, frag);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();*/

    }
}
