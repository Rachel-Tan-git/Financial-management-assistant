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
        //put the echarts.html in the webView
        webView.loadUrl("file:///android_asset/echarts.html");
        chartSearch = (Button)view.findViewById(R.id.chartSearch);
        chartSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String startDay = startDate.getText().toString();
                String endDay = endDate.getText().toString();
                total = userService.chartsData(startDay,endDay,username);
                Log.i("TAG", total);
                //transfer the parameter(total) into the html file to build the charts
                webView.loadUrl("javascript:createChart("+total+");");
            }
        });

        return view;
    }
}
