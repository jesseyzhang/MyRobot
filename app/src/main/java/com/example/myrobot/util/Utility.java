package com.example.myrobot.util;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by DELL 15 on 2017/9/18.
 */

public class Utility {
    public static String handleText(String response){
        if (!TextUtils.isEmpty(response)){
            try{
                JSONObject textObject=new JSONObject(response);
                // textObject.getString("code");
                return textObject.getString("text");
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return null;
    }
}
