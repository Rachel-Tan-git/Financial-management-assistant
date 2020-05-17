package com.example.fma.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fma.R;
import com.example.fma.Service.UserService;
import com.example.fma.showRecordsViewModel;
import com.example.fma.userInforClass.userBill;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>  {

    private List<userBill> list=null;
    private UserService userService;
    private showRecordsViewModel viewModel;

    private static StringBuilder builder=new StringBuilder();

    //Constructor
    public RecyclerAdapter(showRecordsViewModel viewModel) {
        this.viewModel=viewModel;
        this.list=viewModel.getLiveData().getValue();
    }

    public void setList(List<userBill> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        //connect the recyclerView item to the record_item.xml layout file
        View item=inflater.inflate(R.layout.record_item,parent,false);
        return new MyViewHolder(item);
    }

    @Override
    //when the the there is a database record, set the data into each item
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        if(list!=null) {
            final userBill userBill = list.get(position);
            holder.number.setText(String.valueOf(position+1));
            Log.i("TAG",holder.number.getText().toString());
            holder.type.setText(userBill.getType());
            holder.name.setText(userBill.getName());
            holder.date.setText(userBill.getDate());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        //find views
        TextView number,date,name,type;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.billName);
            date=itemView.findViewById(R.id.billDate);
            type=itemView.findViewById(R.id.billType);
            number=itemView.findViewById(R.id.billNumber);
        }
    }
}


