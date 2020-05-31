package com.example.fma;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.fma.Service.UserService;
import com.example.fma.userInforClass.userBill;


public class addRecordFragment extends Fragment {
    private EditText billDetails;
    private EditText billName;
    private EditText billMoney;
    private RadioGroup billType;
    private Button addButton;
    private View view;
    private String username;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_record, container, false);
        //find the views
        billDetails = (EditText)view.findViewById(R.id.details);
        billMoney = (EditText)view.findViewById(R.id.billNumber);
        billName = (EditText)view.findViewById(R.id.billName);
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
                String details = billDetails.getText().toString().trim();
                String name = billName.getText().toString().trim();
                String number = billMoney.getText().toString().trim();
                String billTy = ((RadioButton)getActivity().findViewById(billType.getCheckedRadioButtonId())).getText().toString();
                //judge the input is empty or not
                if(name.isEmpty() || number.isEmpty())
                {
                    Toast.makeText(getActivity(), "The bill name and number cannot be empty!", Toast.LENGTH_LONG).show();
                }else {
                    //Log.i("TAG",billTy+"_"+name+"_"+number+"_"+details+"_");
                    //use the UserService to connect the database
                    UserService userService = new UserService(getActivity());
                    //use the userBill class to integrate data
                    userBill oneRecord = new userBill();
                    oneRecord.setUsername(username);
                    oneRecord.setType(billTy);
                    oneRecord.setName(name);
                    oneRecord.setMoney(number);
                    oneRecord.setBillDetails(details);
                    //add a record in to the userBill table in database
                    userService.addRecord(oneRecord);
                    Toast.makeText(getActivity(), "Add bill record successfully!", Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }



}
