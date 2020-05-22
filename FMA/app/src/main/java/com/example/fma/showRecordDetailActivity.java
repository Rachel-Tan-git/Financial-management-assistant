package com.example.fma;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.fma.Service.UserService;
import com.example.fma.userInforClass.userBill;

public class showRecordDetailActivity extends AppCompatActivity {

    private userBill bill;
    private UserService userService;
    private EditText showType;
    private EditText showName;
    private EditText showMoney;
    private EditText showDetail;
    private TextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_record_detail);
        //find view
        showType = (EditText)findViewById(R.id.showType);
        showName = (EditText)findViewById(R.id.showName);
        showMoney = (EditText)findViewById(R.id.showMoney);
        showDetail = (EditText)findViewById(R.id.showDetail);
        date = (TextView)findViewById(R.id.billDate);

        userService = new UserService(this);
        //get the billId from the RecyclerView
        String billId = getIntent().getStringExtra("billId");
        //using billId get the match bill in he database
        bill = userService.showBillItem(billId);

        //set the data to the EditText and TextView to show the detail of a bill record
        showType.setText(bill.getType());
        showName.setText(bill.getName());
        showMoney.setText(bill.getMoney());
        showDetail.setText(bill.getBillDetails());
        date.setText(bill.getDate());
    }

}
