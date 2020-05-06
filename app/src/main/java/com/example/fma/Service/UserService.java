package com.example.fma.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;


public class UserService{
    private DatabaseHelper dbHelper;
    public UserService(Context context){
        dbHelper=new DatabaseHelper(context);
    }

    public boolean login(String username,String password){
        SQLiteDatabase sdb=dbHelper.getReadableDatabase();
        //Select username and password from the table
        String sql="select * from user where username=? and password=?";
        //Use cursor to check whether the user's entered username and password are in the database
        Cursor cursor=sdb.rawQuery(sql, new String[]{username,password});
        if(cursor.moveToFirst()){
            cursor.close();
            return true;
        }
        return false;
    }

}
