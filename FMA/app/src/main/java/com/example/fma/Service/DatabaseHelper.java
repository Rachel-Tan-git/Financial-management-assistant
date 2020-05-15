package com.example.fma.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.IBinder;

/***
 * use the SQLite to store the user information so need use the DatabaseHelper(SQLiteOpenHelper)
 * create a database
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    static String name="user.db";
    static int dbVersion=1;
    public DatabaseHelper(Context context) {
        super(context, name, null, dbVersion);
    }
//Create a table for store the user information
    public void onCreate(SQLiteDatabase db) {
        String sqlUser = "create table user(id integer primary key autoincrement,username varchar(20),password varchar(20),age integer,sex varchar(2))";
        db.execSQL(sqlUser);
        String sqlUserBill = "create table userBill(id integer primary key autoincrement,username text,billType text,name text,money text,billDetails text, billDate text)";
        db.execSQL(sqlUserBill);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
    //Delete the database
    public boolean deleteDatabase(Context context) {
        return context.deleteDatabase(name);
    }
}
