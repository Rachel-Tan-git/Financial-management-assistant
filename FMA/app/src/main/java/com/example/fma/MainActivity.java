package com.example.fma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fma.Service.UserService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }
    private EditText username;
    private EditText password;
    private Button login;
    private Button register;

    private void findViews() {
        username=(EditText) findViewById(R.id.username);
        password=(EditText) findViewById(R.id.password);
        login=(Button) findViewById(R.id.login);
        register=(Button) findViewById(R.id.register);
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
    }
}
