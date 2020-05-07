package com.example.fma;

import java.io.Serializable;
import java.sql.Date;

/***
 * The userBill class is used to store the bill information and is easy to store directly into the database
 */
public class userBill implements Serializable {

    private String username;
    private String billType;
    private String type;
    private String name;
    private Double number;
    private String billDate;
    public userBill() {
        super();
        // TODO Auto-generated constructor stub
    }

    public userBill(String username, String billType,String type, String name, Double number, String billDate){
        super();
        this.username = username;
        this.billDate = billDate;
        this.billType = billType;
        this.name = name;
        this.number = number;
        this.type = type;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public Double getNumber() {
        return number;
    }

    public void setNumber(Double number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
