package com.example.fma;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.fma.Adapter.RecyclerAdapter;
import com.example.fma.Service.UserService;
import com.example.fma.userInforClass.userBill;

import java.util.List;

public class showRecordsFragment extends Fragment {
    private UserService userService;
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private List<userBill> list;
    private showRecordsViewModel viewModel;
    private String username;
    private EditText startDate;
    private EditText endDate;
    private Button searchButton;
    private Fragment showRecordsFragment;
    private View view;

    //this is the beginning of this fragment
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_show_records, container, false);
        startDate = (EditText)view.findViewById(R.id.startDate);
        endDate = (EditText)view.findViewById(R.id.endDate);
        searchButton = (Button) view.findViewById(R.id.Search);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //get the username from the MainPageActivity
        Bundle bundle=getArguments();
        if(bundle != null) {
            username = bundle.getString("user_name");
        }
        Log.i("TAG", username );
        showRecordsFragment = this;
        userService = new UserService(getContext());
        list = userService.showAllCharge(username);

        viewModel = showRecordsViewModel.getINSTANCE(showRecordsFragment);
        //use the username to get all the userBill records from database

        //use the viewModel to processing the list data and share the data with adapter
        viewModel.setLiveData(list);
        //set the layout for the recyclerView
        recyclerView = requireActivity().findViewById(R.id.allRecords);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        //set the adapter for the recyclerView
        adapter = new RecyclerAdapter(viewModel);
        recyclerView.setAdapter(adapter);
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
        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String startDay = startDate.getText().toString();
                String endDay = endDate.getText().toString();
                setRecyclerView(username,startDay,endDay);
                Log.i("TAG", username + "_" + startDay+ "_" + endDay);
            }
        });
    }

    private void setRecyclerView(String username, String startDay, String endDay){
        showRecordsFragment = this;
        userService = new UserService(getContext());
        list = userService.showDateCharge(username,startDay,endDay);
        viewModel = showRecordsViewModel.getINSTANCE(showRecordsFragment);
        //use the username to get all the userBill records from database

        //use the viewModel to processing the list data and share the data with adapter
        viewModel.setLiveData(list);
        //set the layout for the recyclerView
        recyclerView = requireActivity().findViewById(R.id.allRecords);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        //set the adapter for the recyclerView
        adapter = new RecyclerAdapter(viewModel);
        recyclerView.setAdapter(adapter);
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

}
