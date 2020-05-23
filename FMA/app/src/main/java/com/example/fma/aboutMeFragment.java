package com.example.fma;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.pdf.PdfDocument;
//setContentRect
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fma.Adapter.RecyclerAdapter;
import com.example.fma.Service.UserService;
import com.example.fma.userInforClass.userBill;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


public class aboutMeFragment extends Fragment {
    private String username;
    private TextView usernameTextView;
    private TextView feedback;
    private List<userBill> list;
    private UserService userService;
    private showRecordsViewModel viewModel;
    private RecyclerView textContainer;
    private RecyclerAdapter adapter;
    private TextView print;
    private View view;
    private Button logout;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //find view
        view = inflater.inflate(R.layout.fragment_about_me, container, false);
        usernameTextView = (TextView)view.findViewById(R.id.username);
        feedback = (TextView)view.findViewById(R.id.feedback);
        print = (TextView)view.findViewById(R.id.printRecords);
        logout = (Button)view.findViewById(R.id.logout);
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
        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdfInterviewContent();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //back to the login screen
                startActivity(new Intent(getActivity(),MainActivity.class));
            }
        });

        return view ;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userService = new UserService(getContext());
        viewModel = showRecordsViewModel.getINSTANCE(this);
        //use the username to get all the userBill records from database
        this.list = userService.showAllCharge(this.username);
        //use the viewModel to processing the list data and share the data with adapter
        viewModel.setLiveData(this.list);
        //set the layout for the recyclerView
        textContainer = requireActivity().findViewById(R.id.textContainer);
        textContainer.setLayoutManager(new LinearLayoutManager(requireActivity()));
        //set the adapter for the recyclerView
        adapter = new RecyclerAdapter(viewModel);
        textContainer.setAdapter(adapter);
        //this step is equal to set the data to the adapter step by step
        //if the database has more than 1 records then the adapter need a potion to find each record
        //and then the adapter will set each of them to the recyclerView item
        viewModel.getLiveData().observe(requireActivity(), new Observer<List<userBill>>() {
            @Override
            public void onChanged(List<userBill> records) {
                adapter.setList(records);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void pdfInterviewContent(){
        //ask for storage permission dynamic
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(getActivity(), "get permission", Toast.LENGTH_SHORT).show();

            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
        }

        //create PDFDocument
        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(textContainer.getWidth(),textContainer.getHeight(),1).create();
        //Create a new page
        PdfDocument.Page page = document.startPage(pageInfo);
        //canvas draw the recyclerView content
        textContainer.draw(page.getCanvas());
        document.finishPage(page);
        //set the path to save this file
        String path =  Environment.getExternalStorageDirectory().getAbsolutePath();
        //print the path at the backend
        System.out.println(path);
        File file = new File(path,"/file.pdf");
        //if the file not exists
        if(!file.exists()){
            if(file.mkdirs()){
                System.out.println(1);
            }else
                System.out.println(0);
        }
        //if the file exists, them delete it.
        if(file.exists()){
            file.delete();
        }
        try {
            //output the file
            document.writeTo(new FileOutputStream(file));
            Toast.makeText(getActivity(), "Print successful! Please check in you SDK", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            //Exception handling
            e.printStackTrace();
        }
        document.close();
    }

  //  String path = "/mnt/sdcard/";
   // File file = new File(path,"/file.pdf");

}
