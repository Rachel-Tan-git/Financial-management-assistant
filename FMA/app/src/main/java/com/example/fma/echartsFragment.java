package com.example.fma;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fma.Service.UserService;


public class echartsFragment extends Fragment {
    private View view;
    private UserService userService;
    private String total;
    private String username;
    private WebView webView;
    private EditText startDate;
    private EditText endDate;
    private Button chartSearch;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_echarts, container, false);
        //get the username from the MainPageActivity
        Bundle bundle=getArguments();
        if(bundle != null) {
            username = bundle.getString("user_name");
        }
        userService = new UserService(getContext());
        //find views
        startDate = (EditText)view.findViewById(R.id.startDate);
        endDate = (EditText)view.findViewById(R.id.endDate);
        webView = (WebView)view.findViewById(R.id.webView);
        //set the webView
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setJavaScriptEnabled(true);
        //webView.getSettings().setDomStorageEnabled(true);
        //put the echarts.html in the webView
        webView.loadUrl("file:///android_asset/echarts.html");
        //set the initial chart
        //total = userService.chartsData(" "," ",username);
        //webView.loadUrl("javascript:createChart("+total+");");
        chartSearch = (Button)view.findViewById(R.id.chartSearch);
        chartSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String startDay = startDate.getText().toString();
                String endDay = endDate.getText().toString();
                //using Regular expression to check the format of the date
                if(startDay.matches("\\d{4}-\\d{2}-\\d{2}") && endDay.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    total = userService.chartsData(startDay,endDay,username);
                    Log.i("TAG", total);
                    //transfer the parameter(total) into the html file to build the charts
                    webView.loadUrl("javascript:createChart("+total+");");
                    //Log.i("TAG", username + "_" + startDay + "_" + endDay);
                }
                else{
                    Toast.makeText(getActivity(), "The date format is wrong. Please enter yyyy-mm-dd(eg.2020-01-01) ", Toast.LENGTH_LONG).show();
                }

            }
        });

        return view;
    }
}
