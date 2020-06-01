package com.example.fma;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.fma.userInforClass.userBill;

import java.util.List;

public class showRecordsViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private  static showRecordsViewModel INSTANCE=null;
    public static showRecordsViewModel getINSTANCE(Fragment fragment){
        if(INSTANCE==null){
            INSTANCE=new ViewModelProvider(fragment).get(showRecordsViewModel.class);
        }
        return INSTANCE;
    }

    private MutableLiveData<List<userBill>> liveData=new MutableLiveData<>();

    public MutableLiveData<List<userBill>> getLiveData(){
        return this.liveData;
    }

    public void setLiveData(List<userBill> list){
        getLiveData().setValue(list);
    }
}
