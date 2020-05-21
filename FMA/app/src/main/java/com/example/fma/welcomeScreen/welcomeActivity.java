package com.example.fma.welcomeScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;

import com.example.fma.MainActivity;
import com.example.fma.R;

public class welcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //set the welcome screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //hide the top actionBar
        getSupportActionBar().hide();
        //Asynchronous processing
        handler.sendEmptyMessageDelayed(0,3000);

    }
    //Asynchronous processing to realize after 3s jump to the MainActivity(login screen)
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            //实现页面的跳转
            Intent intent=new Intent(welcomeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            super.handleMessage(msg);
        }
    };
}
