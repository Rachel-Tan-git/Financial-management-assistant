package com.example.fma.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.content.ContentValues;

import com.example.fma.User;
import com.example.fma.userBill;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserService{
    private DatabaseHelper dbHelper;
    public UserService(Context context){
        dbHelper=new DatabaseHelper(context);
    }

    public boolean login(String username,String password){
        SQLiteDatabase sdb = dbHelper.getReadableDatabase();
        //Select username and password from the table
        String sql = "select * from user where username=? and password=?";
        //Use cursor to check whether the user's entered username and password are in the database
        Cursor cursor = sdb.rawQuery(sql, new String[]{username,password});
        if(cursor.moveToFirst()){
            cursor.close();
            return true;
        }
        return false;
    }
    //Store user data in a database(table user)
    public boolean register(User user){
        SQLiteDatabase sdb = dbHelper.getReadableDatabase();
        String sql = "insert into user(username,password,age,sex) values(?,?,?,?)";
        Object obj[]={user.getUsername(),user.getPassword(),user.getAge(),user.getSex()};
        sdb.execSQL(sql, obj);
        return true;
    }
    public boolean checkUserExist(String username,String password,boolean type){   //判断用户是否存在
        boolean flag=false;
        Cursor cursor=null;
        String sql=null;
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        if(type) {
            sql= "select * from user where username=? and password=?";
            cursor= db.rawQuery(sql, new String[]{username,password});
        }else {
            sql="select * from user where username=?";
            cursor=db.rawQuery(sql,new String[]{username});
        }
        if(cursor.moveToFirst())
            flag=true;
        db.close();
        cursor.close();
        return flag;
    }

    private String getDateString(boolean flag){      //get the real date
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf2= new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Date date=new Date();
        if(flag)
            return sdf.format(date);
        else return sdf2.format(date);
    }

    public void addRecord(userBill userbill){
        ContentValues values=new ContentValues();
        values.put("username",userbill.getUsername());
        values.put("billType",userbill.getType());
        values.put("name",userbill.getName());
        values.put("money",userbill.getMoney());
        values.put("billDetails",userbill.getBillDetails());
        values.put("billDate",getDateString(true));
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        db.insert("userBill",null,values);
        db.close();
    }

    public List<userBill> showAllCharge(String username){
        String sql="select * from charge where username=? order by date desc";
        List<userBill> list=new ArrayList<>();
            if(dbHelper!=null) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursor=db.rawQuery(sql,new String[]{username});
            while (cursor.moveToNext()){
                userBill charge=new userBill();
                charge.setDate(cursor.getString(cursor.getColumnIndex("billDate")));
                charge.setMoney(cursor.getString(cursor.getColumnIndex("money")));
                charge.setName(cursor.getString(cursor.getColumnIndex("name")));
                charge.setType(cursor.getString(cursor.getColumnIndex("billType")));
                charge.setBillDetails(cursor.getString(cursor.getColumnIndex("billDetails")));
                charge.setUsername(username);
                list.add(charge);
            }
            cursor.close();
            db.close();
        }
        return list;
    }




}
