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
import android.widget.TextView;
import android.widget.Toast;

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
    private EditText searchKey;
    private Button searchButton, keySearch;
    private Fragment showRecordsFragment;
    private TextView total;
    private View view;

    //this is the beginning of this fragment
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_show_records, container, false);
        startDate = (EditText)view.findViewById(R.id.startDate);
        endDate = (EditText)view.findViewById(R.id.endDate);
        searchButton = (Button) view.findViewById(R.id.Search);
        total = (TextView)view.findViewById(R.id.total);
        keySearch = (Button)view.findViewById(R.id.keySearch);
        searchKey = (EditText)view.findViewById(R.id.searchKey);

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
        showRecordsFragment = this;
        userService = new UserService(getContext());
        //use the username to get all the userBill records from database
        list = userService.showAllCharge(username);
        //user user name to get all total income money and spending money
        String totalMoney = userService.getAllRecordsMoney(username);
        total.setText(totalMoney);
        //use the viewModel to processing the list data and share the data with adapter
        viewModel = showRecordsViewModel.getINSTANCE(showRecordsFragment);
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
        //set the click lister to the search button for search the billRecords(based on the startDate and endDate)
        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String startDay = startDate.getText().toString();
                String endDay = endDate.getText().toString();
                if(startDay.matches("\\d{4}-\\d{2}-\\d{2}") && endDay.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    setRecyclerView(username, startDay, endDay);
                    Log.i("TAG", username + "_" + startDay + "_" + endDay);
                }
                else{
                    Toast.makeText(getActivity(), "The date format is wrong. Please enter yyyy-mm-dd(eg.2020-01-01) ", Toast.LENGTH_LONG).show();
                }
            }
        });
        //if the user enter some keyword and then click the search button.
        keySearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //the database will give a userBill list contain the record include the keyword
                viewModel.setLiveData(userService.keySearchItem(username,searchKey.getText().toString()));
                adapter = new RecyclerAdapter(viewModel);
                //the recyclerView could show the search result
                recyclerView.setAdapter(adapter);
            }
        });
    }
    //set the RecyclerView to store the records(get from the date)
    private void setRecyclerView(String username, String startDay, String endDay){
        showRecordsFragment = this;
        userService = new UserService(getContext());
        //use the username and startDay,endDay to get the userBill records and total income and spending money from database
        list = userService.showDateCharge(username,startDay,endDay);
        String totalMoney = userService.getAllDateRecordsMoney(username,startDay,endDay);
        total.setText(totalMoney);
        //use the viewModel to processing the list data and share the data with adapter
        viewModel = showRecordsViewModel.getINSTANCE(showRecordsFragment);
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
