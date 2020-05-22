package com.example.fma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    private Button delete;
    private Button fix;
    private String username;

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
        delete = (Button)findViewById(R.id.delete);
        fix = (Button)findViewById(R.id.fix);

        userService = new UserService(this);
        //get the billId from the RecyclerView
        final String billId = getIntent().getStringExtra("billId");


        //using billId get the match bill in he database
        bill = userService.showBillItem(billId);
        username = bill.getUsername();

        //set the data to the EditText and TextView to show the detail of a bill record
        showType.setText(bill.getType());
        showName.setText(bill.getName());
        showMoney.setText(bill.getMoney());
        showDetail.setText(bill.getBillDetails());
        date.setText(bill.getDate());
        //when user update the bill record then could click the button to update the data in the database
        fix.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                userBill billFix = new userBill();
                billFix.setUsername(username);
                Log.i("TAG",username);
                //set the data to the bill to update
                billFix.setId(billId);
                billFix.setType(showType.getText().toString());
                billFix.setName(showName.getText().toString());
                billFix.setMoney(showMoney.getText().toString());
                billFix.setBillDetails(showDetail.getText().toString());
                billFix.setDate(date.getText().toString());
                userService.updateItem(billFix);
                Toast.makeText(showRecordDetailActivity.this, "This bill record has been update", Toast.LENGTH_LONG).show();
                //go back to MainPageActivity
                startActivity(new Intent(showRecordDetailActivity.this,MainPageActivity.class).putExtra("user_name",bill.getUsername()));
            }
        });
        //delete this record
        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                userService.deleteItem(billId);
                startActivity(new Intent(showRecordDetailActivity.this,MainPageActivity.class).putExtra("user_name",username));
            }
        });
    }

}
