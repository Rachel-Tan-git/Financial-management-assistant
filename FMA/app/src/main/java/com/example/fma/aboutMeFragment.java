package com.example.fma;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class aboutMeFragment extends Fragment {
    private String username;
    private TextView usernameTextView;
    private TextView feedback;

    private View view;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //find view
        view = inflater.inflate(R.layout.fragment_about_me, container, false);
        usernameTextView = (TextView)view.findViewById(R.id.username);
        feedback = (TextView)view.findViewById(R.id.feedback);
        //get the username from the MainPageActivity
        Bundle bundle=getArguments();
        if(bundle != null) {
            username = bundle.getString("user_name");
        }
        //set the username in the usernameTextView
        usernameTextView.setText("USER: "+username);
        //set clickListener to the feedback(make it clickable)
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //there is a Intent action called ACTION_SEND for jumping to the mail_box in the device to send email
                Intent it = new Intent(Intent.ACTION_SEND);
                it.setType("plain/text");
                //set the receiver email address(the developer email address)
                String strAddressBuf[] = new String[]{"tanchen@deakin.edu.au"};
                //set the email subject
                String strSubject = "Feedback on Financial-management-assistant Application.";
                //set the email content (first line)
                String strContent = "I want to give the developer some feedback:";
                it.putExtra(Intent.EXTRA_EMAIL, strAddressBuf);
                it.putExtra(Intent.EXTRA_SUBJECT, strSubject);
                it.putExtra(Intent.EXTRA_TEXT, strContent);
                try
                {
                    startActivity(it);
                }catch (Exception e)// Exception catch, if there is a background to play a hint
                {
                    Log.i("TAG",e.getMessage());
                }
            }
        });
        return view ;
    }
}
