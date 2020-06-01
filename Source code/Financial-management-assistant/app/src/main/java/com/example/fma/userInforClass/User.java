package com.example.fma.userInforClass;

import java.io.Serializable;

/***
 * The user class is used to store user information and is easy to store directly into the database
 */
public class User implements Serializable {
    private int id;
    private String username;
    private String password;
    private int age;
    private String sex;
    public User() {
        super();
        // TODO Auto-generated constructor stub
    }
    public User(String username, String password, int age, String sex) {
        super();
        this.username = username;
        this.password = password;
        this.age = age;
        this.sex = sex;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", password="
                + password + ", age=" + age + ", sex=" + sex + "]";
    }
}
