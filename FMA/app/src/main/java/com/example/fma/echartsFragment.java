package com.example.fma;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.fma.Service.UserService;


public class echartsFragment extends Fragment {
    private View view;
    private UserService userService;
    private String total;
    private WebView webView;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_echarts, container, false);
        webView = (WebView)view.findViewById(R.id.webView);
        webView.loadUrl("javascript:createChart("+total+");");
        return view;
    }
}
