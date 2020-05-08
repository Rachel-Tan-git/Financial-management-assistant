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
    private BottomNavigationView bottomNavigation;
    private TextView welcomeText;
    private Fragment[] fragments;
    private int lastFragment = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        welcomeText = (TextView) findViewById(R.id.WelcomeText);
        Intent intent = getIntent();
        username = intent.getStringExtra("user_name");
        //set the Username in the TextView to show the account
        welcomeText.setText("Username: " + username);
        //find the BottomNavigationView
        bottomNavigation = (BottomNavigationView) findViewById(R.id.bottomNavigationView);

        /**
         *The reason to initialize the fragment and pass username here is
         * because if you put it in an onNavigationItemSelected or in a switchFragment
         * it will only be passed if you click the button at the bottom,
         * which will cause the null pointer to appear
         */
        //initialize the first fragment(addRecordFragment) and put the bundle with the username into the fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fragments = new Fragment[]{new addRecordFragment(), new chartsFragment()};
        //use bundle to transfer the parameter between the activity and fragment
        Bundle bundle=new Bundle();
        bundle.putString("user_name",username);
        fragments[0].setArguments(bundle);
        transaction.show(fragments[0]);
        transaction.replace(R.id.fragment, fragments[0]);
        // the most important step for show the fragment.
        transaction.commit();

        //Set the click event for the bottom navigation bar
        BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    //if the user click the home, then show the addRecordFragment and hide the another fragment
                    case R.id.home: {
                        if (lastFragment != 0) {
                            switchFragment(lastFragment, 0);
                            lastFragment = 0;
                        }
                        return true;
                    }
                    //if the user click the charts, then show the chartsFragment and hide the another fragment
                    case R.id.charts: {
                        if (lastFragment != 1) {
                            switchFragment(lastFragment, 1);
                            lastFragment = 1;
                        }
                        return true;
                    }
                    default:
                        break;
                }
                return false;
            }
        };
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    // a function for switching the fragments
    private void switchFragment(int lastFragment, int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //hiding last Fragment
        transaction.hide(fragments[lastFragment]);
        if (fragments[index].isAdded() == false) {
            transaction.add(R.id.fragment, fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();
    }

}
