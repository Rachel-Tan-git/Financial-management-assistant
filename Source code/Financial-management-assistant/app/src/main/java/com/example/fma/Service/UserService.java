package com.example.fma.Service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;

import com.example.fma.userInforClass.User;
import com.example.fma.userInforClass.userBill;

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
    public boolean checkUserExist(String username,String password,boolean type){   //check the user exist or not
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

    //for add a new record into database
    public void addRecord(userBill userbill){
        ContentValues values=new ContentValues();
        //put all the values of the userBill into the ContentValues
        values.put("username",userbill.getUsername());
        values.put("billType",userbill.getType());
        values.put("name",userbill.getName());
        values.put("money",userbill.getMoney());
        values.put("billDetails",userbill.getBillDetails());
        values.put("billDate",getDateString(true));
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        //store the userBill record into the database
        db.insert("userBill",null,values);
        db.close();
    }

    //show all the userBills in the database
    public List<userBill> showAllCharge(String username){
        String sql="select * from userBill where username=? order by billDate desc";
        List<userBill> list=new ArrayList<>();
            if(dbHelper!=null) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursor=db.rawQuery(sql,new String[]{username});
            while (cursor.moveToNext()){
                userBill charge=new userBill();
                charge.setId(cursor.getString(cursor.getColumnIndex("id")));
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
    //select userBill records based date
    public List<userBill> showDateCharge(String username, String startDate, String endDate){
        String sql="select * from userBill where username='"+username+"' and (billDate>='"+startDate+" 00:00:00' and billDate<='"+endDate+" 23:59:59')";
        List<userBill> list=new ArrayList<>();
        if(dbHelper!=null) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursor=db.rawQuery(sql,null);
            while (cursor.moveToNext()){
                userBill charge=new userBill();
                charge.setId(cursor.getString(cursor.getColumnIndex("id")));
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

    //select userBill records income money and spending money based date
    public String getAllDateRecordsMoney(String username,String startDate,String endDate){
        String income="",outcome="";
        String sql="";
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        sql="select sum(money) from userBill where username='"+username+"' and (billDate>='"+startDate+" 00:00:00' and billDate<='"+endDate+" 23:59:59') and billType='Income'";
        Cursor cursor=db.rawQuery(sql,null);
        if(cursor.moveToFirst())
            income=cursor.getString(0);
        cursor.close();
        sql="select sum(money) from userBill where username='"+username+"' and (billDate>='"+startDate+" 00:00:00' and billDate<='"+endDate+" 23:59:59') and billType='Spending'";
        cursor=db.rawQuery(sql,null);
        if(cursor.moveToFirst())
            outcome=cursor.getString(0);
        cursor.close();
        db.close();
        return "Total income: "+income+";  "+"Total spending"+outcome;
    }
    //select all the userBill records income money and spending money
    public String getAllRecordsMoney(String username){
        String income="",outcome="";
        String sql="";
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        sql="select sum(money) from userBill where username='"+username+"' and billType='Income'";
        Cursor cursor=db.rawQuery(sql,null);
        if(cursor.moveToFirst())
            income=cursor.getString(0);
        cursor.close();
        sql="select sum(money) from userBill where username='"+username+"' and billType='Spending'";
        cursor=db.rawQuery(sql,null);
        if(cursor.moveToFirst())
            outcome=cursor.getString(0);
        cursor.close();
        db.close();
        return "Total income: "+income+";  "+"Total spending"+outcome;
    }

    //using the id of userBill to get a bill
    public userBill showBillItem(String id){
        String sql="select * from userBill where id='"+id+"'";
        userBill charge=new userBill();
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            charge.setId(cursor.getString(cursor.getColumnIndex("id")));
            charge.setDate(cursor.getString(cursor.getColumnIndex("billDate")));
            charge.setMoney(cursor.getString(cursor.getColumnIndex("money")));
            charge.setName(cursor.getString(cursor.getColumnIndex("name")));
            charge.setType(cursor.getString(cursor.getColumnIndex("billType")));
            charge.setBillDetails(cursor.getString(cursor.getColumnIndex("billDetails")));
            charge.setUsername(cursor.getString(cursor.getColumnIndex("username")));
        }
        return charge;
    }

    //delete the bill record
    public void deleteItem(String id){
        String sql="delete from userBill where id='"+id+"'";
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.execSQL(sql);
    }
    //change the bill record
    public void updateItem(userBill bill){
        String sql="update userBill set username='"+bill.getUsername()+"',billType='"+bill.getType()+"'," +
                "name='"+bill.getName()+"',money='"+bill.getMoney()+"',billDetails='"+bill.getBillDetails()+"',billDate='"+bill.getDate()+"' where id='"+bill.getId()+"'";
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.execSQL(sql);
        db.close();
    }

    public List<userBill> keySearchItem(String username, String key){
        String sql="select * from userBill where username=? and (billDate like '%"+key+"%' or name like '%"+key+"%' or billType like '%"+key+"%')";
        List<userBill> list=new ArrayList<>();
        if(dbHelper!=null) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery(sql, new String[]{username});
            while (cursor.moveToNext()) {
                userBill charge = new userBill();
                charge.setId(cursor.getString(cursor.getColumnIndex("id")));
                charge.setDate(cursor.getString(cursor.getColumnIndex("billDate")));
                charge.setMoney(cursor.getString(cursor.getColumnIndex("money")));
                charge.setName(cursor.getString(cursor.getColumnIndex("name")));
                charge.setType(cursor.getString(cursor.getColumnIndex("billType")));
                charge.setBillDetails(cursor.getString(cursor.getColumnIndex("billDetails")));
                charge.setUsername(cursor.getString(cursor.getColumnIndex("username")));
                list.add(charge);
            }
            cursor.close();
            db.close();
        }
        return list;
    }

    //get the all the need bill data(billDate, name, money, type), and store them into the StringBuilder
    public String chartsData(String startDate,String endDate,String username){
        String tempstr="";
        StringBuilder builder=new StringBuilder();
        String sql="select * from userBill where username='"+username+"' and (billDate>='"+startDate+" 00:00:00' and billDate<='"+endDate+" 23:59:59')";
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.rawQuery(sql,null);
        while (cursor.moveToNext()) {
            if(!tempstr.contains(cursor.getString(cursor.getColumnIndex("billDate")).split(" ")[0]))
                //get the date and store them into a string line
                tempstr += cursor.getString(cursor.getColumnIndex("billDate")).split(" ")[0] + "/";
        }
        cursor.close();
        //get each date and use the date count to get the other data in that date
        String str[]=tempstr.split("/");
        for (int i=0;i<str.length;i++){
            builder.append("{billDate:'").append(str[i]+"',");
            sql="select sum(money) from userBill where username='"+username+"' and billType='Income' and billDate like '"+str[i]+"%'";
            cursor=db.rawQuery(sql,null);
            cursor.moveToFirst();
            builder.append("chargein:'"+cursor.getString(0)).append("',");
            cursor.close();
            sql="select sum(money) from userBill where username='"+username+"' and billType='Spending' and billDate like '"+str[i]+"%'";
            cursor=db.rawQuery(sql,null);
            cursor.moveToFirst();
            builder.append("chargeout:'"+cursor.getString(0)).append("',").append("day:[");
            cursor.close();

            sql="select * from userBill where username='"+username+"' and billDate like '"+str[i]+"%'";
            cursor=db.rawQuery(sql,null);
            while (cursor.moveToNext()){
                builder.append("{").append("name:'"+cursor.getString(cursor.getColumnIndex("name")))
                        .append("',type:'"+cursor.getString(cursor.getColumnIndex("billType")))
                        .append("',money:'"+cursor.getString(cursor.getColumnIndex("money"))).append("'},");
            }
            builder.replace(builder.lastIndexOf(","),builder.lastIndexOf(",")+1,"");
            builder.append("]},");
            cursor.close();
        }
        builder.replace(builder.lastIndexOf(","),builder.lastIndexOf(",")+1,"");
        db.close();
        return "["+builder.toString()+"]";
    }

}
