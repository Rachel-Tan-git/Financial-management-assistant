package com.example.fma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.fma.Service.UserService;

public class RegisterActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private EditText age;
    private RadioGroup sex;
    private Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViews();
        //when user click the register button
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //get the data from each elements
                String name = username.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String ageStr = age.getText().toString().trim();
                String sexStr = ((RadioButton) RegisterActivity.this.findViewById(sex.getCheckedRadioButtonId())).getText().toString();
                //judge the input is empty or not
                if (name.isEmpty() || pass.isEmpty() || ageStr.isEmpty())
                {
                    Toast.makeText(RegisterActivity.this, "The username, age and the password cannot be null", Toast.LENGTH_SHORT).show();
                }else{
                    //use the UserService to connect the database
                    UserService uService = new UserService(RegisterActivity.this);
                    //check if the username exists
                    if (uService.checkUserExist(name, null, false)) {
                        Toast.makeText(RegisterActivity.this, "The username already exists, please set another one", Toast.LENGTH_SHORT).show();
                    } else {
                        //use the User class to integrate data
                        com.example.fma.userInforClass.User user = new com.example.fma.userInforClass.User();
                        user.setUsername(name);
                        user.setPassword(pass);
                        user.setAge(Integer.parseInt(ageStr));
                        user.setSex(sexStr);
                        //add a line into user table in the database
                        uService.register(user);
                        //back to the login screen
                        startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                        Toast.makeText(RegisterActivity.this, "Register successfully!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
    //find each view and set names for them
    private void findViews() {
        username=(EditText) findViewById(R.id.usernameRegister);
        password=(EditText) findViewById(R.id.passwordRegister);
        age=(EditText) findViewById(R.id.ageRegister);
        sex=(RadioGroup) findViewById(R.id.sexRegister);
        register=(Button) findViewById(R.id.Register);
    }
}
