package com.example.fma.userInforClass;

import java.io.Serializable;

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
    private String id;
    public userBill() {
        super();
        // TODO Auto-generated constructor stub
    }

    public userBill(String id, String username, String type, String name, String money, String billDetails, String date){
        super();
        this.id = id;
        this.username = username;
        this.billDetails = billDetails;
        this.name = name;
        this.money = money;
        this.type = type;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
