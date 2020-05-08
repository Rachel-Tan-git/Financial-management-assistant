package com.example.fma;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fma.Service.UserService;



public class addRecordFragment extends Fragment {
    private EditText recordType;
    private EditText billName;
    private EditText billNumber;
    private EditText billDate;
    private RadioGroup billType;
    private Button addButton;
    private View view;
    private String username;

    //private TextView test;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_record, container, false);
        //find the views
        recordType = (EditText)view.findViewById(R.id.type);
        billName = (EditText)view.findViewById(R.id.billName);
        billNumber = (EditText)view.findViewById(R.id.billNumber);
        billDate = (EditText)view.findViewById(R.id.billDate);
        billType = (RadioGroup)view.findViewById(R.id.BillType);
        addButton = (Button)view.findViewById(R.id.addButton);

        //test = (TextView)view.findViewById(R.id.test);
        //get the username from the MainPageActivity
        Bundle bundle=getArguments();
        if(bundle != null) {
           username = bundle.getString("user_name");

        }
        //test.setText(username);
        //add a onClickListener for the addButton to add the bill record into the database
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //get the content form each EditText views and RadioGroup
                String type = recordType.getText().toString().trim();
                String name = billName.getText().toString().trim();
                Double number = Double.parseDouble(billNumber.getText().toString().trim());
                String date = billDate.getText().toString().trim();
                String billTy = ((RadioButton)getActivity().findViewById(billType.getCheckedRadioButtonId())).getText().toString();

                Log.i("TAG",billTy+"_"+type+"_"+name+"_"+number+"_"+date);
                //use the UserService to connect the database
                UserService userService=new UserService(getActivity());
                //use the userBill class to integrate data
                userBill oneRecord = new userBill();
                oneRecord.setUsername(username);
                oneRecord.setBillType(billTy);
                oneRecord.setType(type);
                oneRecord.setName(name);
                oneRecord.setNumber(number);
                oneRecord.setBillDate(date);
                //add a record in to the userBill table in database
                userService.addRecord(oneRecord);
                Toast.makeText(getActivity(), "Add bill record successfully!", Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }



}
