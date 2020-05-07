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
import android.widget.Toast;

import com.example.fma.Service.UserService;



public class addRecordFragment extends Fragment {
    /*private EditText recordType;
    private EditText billName;
    private EditText billNumber;
    private EditText billDate;
    private RadioGroup billType;
    private Button addButton;
    private View view;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_record, container, false);
        findView();
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String type = recordType.getText().toString().trim();
                String name = billName.getText().toString().trim();
                Double number = Double.parseDouble(billNumber.getText().toString().trim());
                String date = billDate.getText().toString().trim();
                String billTy = ((RadioButton)getActivity().findViewById(billType.getCheckedRadioButtonId())).getText().toString();

                Log.i("TAG",billTy+"_"+type+"_"+name+"_"+number+"_"+date);
                UserService userService=new UserService(getActivity());
                Bundle bundle=getArguments();
                String username=bundle.getString("one");

                userBill oneRecord = new userBill();
                oneRecord.setUsername(username);
                oneRecord.setBillType(billTy);
                oneRecord.setType(type);
                oneRecord.setName(name);
                oneRecord.setNumber(number);
                oneRecord.setBillDate(date);

                userService.addRecord(oneRecord);
                Toast.makeText(getActivity(), "Add bill record successfully!", Toast.LENGTH_LONG).show();
            }

        });
        return view;
    }

    private void findView(){
        recordType = getActivity().findViewById(R.id.type);
        billName = getActivity().findViewById(R.id.billName);
        billNumber = getActivity().findViewById(R.id.billNumber);
        billDate = getActivity().findViewById(R.id.billDate);
        billType = getActivity().findViewById(R.id.BillType);
        addButton = getActivity().findViewById(R.id.addButton);
    }*/


}
