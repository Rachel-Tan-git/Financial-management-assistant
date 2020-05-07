package com.example.fma;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.fma.Service.UserService;

public class RegisterActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    EditText age;
    RadioGroup sex;
    Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViews();

        //when user click the register button
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //get the data from each elements
                String name=username.getText().toString().trim();
                String pass=password.getText().toString().trim();
                String ageStr=age.getText().toString().trim();
                String sexStr=((RadioButton)RegisterActivity.this.findViewById(sex.getCheckedRadioButtonId())).getText().toString();
                Log.i("TAG",name+"_"+pass+"_"+ageStr+"_"+sexStr);

                //use the UserService to connect the database
                UserService uService=new UserService(RegisterActivity.this);

                //use the User class to integrate data
                com.example.fma.User user=new com.example.fma.User();
                user.setUsername(name);
                user.setPassword(pass);
                user.setAge(Integer.parseInt(ageStr));
                user.setSex(sexStr);

                //add a line into user table in the database
                uService.register(user);
                Toast.makeText(RegisterActivity.this, "Register successfully! Please use the back button to Login", Toast.LENGTH_LONG).show();
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
