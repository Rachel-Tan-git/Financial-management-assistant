package com.example.fma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fma.Service.UserService;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp=getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        findViews();
    }
    private EditText username;
    private EditText password;
    private Button login;
    private Button register;
    private CheckBox remember;

    private void findViews() {
        username=(EditText) findViewById(R.id.username);
        password=(EditText) findViewById(R.id.password);
        login=(Button) findViewById(R.id.login);
        register=(Button) findViewById(R.id.register);
        remember = (CheckBox)findViewById(R.id.remember);
        //if the remember in the sp is checked then set the remember be checked again and set the value in sharePreference to the username and password
        if(sp.getBoolean("ISCHECK", true)){
            remember.setChecked(true);
            username.setText(sp.getString("username",""));
            password.setText(sp.getString("password",""));
        }

        //When user click the login button
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the username from EditText
                String name = username.getText().toString();
                System.out.println(name);
                String pass = password.getText().toString();
                //for debug
                System.out.println(pass);
                System.out.println("****************************************");
                Log.i("TAG",name+"_"+pass);
                //Using the UserService to connect the database
                UserService userService = new UserService(MainActivity.this);
                //judge whether the username and password whether are in the database
                boolean flag = userService.login(name, pass);
                System.out.println("==============================================");
                //if in the database then return true
                if(flag){
                    System.out.println("------------------To be successful-------------------");
                    Log.i("TAG","Login successfully");
                    //if the checkBox is checked then put the username and password to the sp
                    if(remember.isChecked()){
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("username",name);
                        editor.putString("password",pass);
                        editor.commit();
                    }
                    //show a toast notice the user login successfully
                    Toast.makeText(MainActivity.this, "Login successfully", Toast.LENGTH_LONG).show();
                    //jump to another activity
                    Intent intent = new Intent(MainActivity.this,MainPageActivity.class);
                    intent.putExtra("user_name",name);
                    startActivity(intent);
                }else{// if not in the database then return false
                    Log.i("TAG","Login failed");
                    Toast.makeText(MainActivity.this, "Login failed! Make sure your username and password are correct!", Toast.LENGTH_LONG).show();
                }
            }
        });
        //if the user click the register then jump to the register activity to let the use register
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        //set a listener to the checkBox to set the value of checkbox to the sp
        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (remember.isChecked()) {
                    sp.edit().putBoolean("ISCHECK", true).commit();

                }else {
                    sp.edit().putBoolean("ISCHECK", false).commit();

                }

            }
        });

    }
}
