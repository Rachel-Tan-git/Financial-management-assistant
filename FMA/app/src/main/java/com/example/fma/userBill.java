package com.example.fma;

import java.io.Serializable;
import java.sql.Date;

/***
 * The userBill class is used to store the bill information and is easy to store directly into the database
 */
public class userBill implements Serializable {

    private String username;
    private String type;
    private String name;
    private String money;
    private String date;
    private String billDetails;
    public userBill() {
        super();
        // TODO Auto-generated constructor stub
    }

    public userBill(String username, String type, String name, String money, String billDetails, String date){
        super();
        this.username = username;
        this.billDetails = billDetails;
        this.name = name;
        this.money = money;
        this.type = type;
        this.date = date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getBillDetails() {
        return billDetails;
    }

    public void setBillDetails(String billDetails) {
        this.billDetails = billDetails;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
