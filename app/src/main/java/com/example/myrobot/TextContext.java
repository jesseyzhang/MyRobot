package com.example.myrobot;

/**
 * Created by DELL 15 on 2017/9/18.
 */

public class TextContext {

    public static final int SEND = 1;
    public static final int RECEIVER =2;
    private int flag;

    private String content;
    public TextContext(String content,int flag){

        setContent(content);
        setFlag(flag);
    }
    public String getContent(){
        return content;
    }
    public void setContent(String content){
        this.content=content;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
