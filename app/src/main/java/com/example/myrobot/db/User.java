package com.example.myrobot.db;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by DELL 15 on 2017/10/14.
 */

public class User extends DataSupport {

    private int id;

    @Column(unique = true,nullable = false)
    private String userName;

    @Column(nullable = false)
    private String passWord;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
