package com.example.myrobot;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myrobot.db.User;
import com.example.myrobot.util.ActivityCollector;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private AppCompatButton reg_btn;
    private EditText username;
    private EditText password;
    private TextView link_login;
    private long firstTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        ActivityCollector.addActivity(this);
        reg_btn=(AppCompatButton)findViewById(R.id.btn_register);
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        link_login=(TextView)findViewById(R.id.link_login);
        firstTime=0;
        reg_btn.setOnClickListener(this);
        link_login.setOnClickListener(this);
    }

    @Override
    public void onBackPressed(){
        long secondTime=System.currentTimeMillis();
        if (secondTime-firstTime>2000){
            Toast.makeText(RegisterActivity.this,"再按一次退出程序",Toast.LENGTH_SHORT).show();
            firstTime=secondTime;
        }
        else {
            ActivityCollector.finishAll();
        }
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_register:

                if ((username.getText().toString().equals(""))||(password.getText().toString().equals(""))){
                    showNullDialog();
                }else {
                    User user=new User();
                    user.setUserName(username.getText().toString());
                    user.setPassWord(password.getText().toString());
                    user.save();
                    showSuccessDialog(username.getText().toString());
                }

                break;

            case R.id.link_login:

                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);

                break;

            default:
                break;
        }
    }
    public void showNullDialog(){
        android.support.v7.app.AlertDialog.Builder builder=new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("输入提示：");
        builder.setMessage("账号或密码不能为空！请重新输入");
        builder.setCancelable(false);
        builder.setPositiveButton("知道了",null);
        builder.show();
    }
    public void showSuccessDialog(final String username){
        android.support.v7.app.AlertDialog.Builder builder=new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("注册成功！");
        builder.setMessage("立刻前往登陆？");
        builder.setNegativeButton("退出",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog,int which){
                ActivityCollector.finishAll();
            }
        });
        builder.setPositiveButton("好的",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog,int which){
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });
        builder.show();
    }
}
