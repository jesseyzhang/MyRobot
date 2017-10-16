package com.example.myrobot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myrobot.db.User;
import com.example.myrobot.util.ActivityCollector;

import org.litepal.crud.DataSupport;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private AppCompatButton log_btn;
    private EditText username;
    private EditText password;
    private TextView link_register;
    private long firstTime;
    private TextView link_forget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ActivityCollector.addActivity(this);
        log_btn=(AppCompatButton)findViewById(R.id.btn_login);
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        firstTime=0;
        link_register=(TextView)findViewById(R.id.link_register);
        link_forget=(TextView)findViewById(R.id.link_forget);
        log_btn.setOnClickListener(this);
        link_register.setOnClickListener(this);
        link_forget.setOnClickListener(this);
    }

    @Override
    public void onBackPressed(){
        long sendTime=System.currentTimeMillis();
        if (sendTime-firstTime>2000){
            Toast.makeText(LoginActivity.this,"再按一次退出程序",Toast.LENGTH_SHORT).show();
            firstTime=sendTime;
        }
        else {
            ActivityCollector.finishAll();
        }
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_login:
                String user_name=username.getText().toString();
                String password_get=password.getText().toString();
                List<User> user= DataSupport.select("userName","passWord")
                        .where("userName=?",user_name)
                        .find(User.class);
                if ((user.isEmpty())||!(user.get(0).getPassWord().equals(password_get))){
                    showFailDialog();
                }else {
                    Intent intent_succ = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent_succ);
                }
                break;
            case R.id.link_register:
                Intent intent_reg=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent_reg);
                break;
            case R.id.link_forget:
                showForgetDialog();
                break;
            default:
                break;
        }
    }
    public void showFailDialog(){
        android.support.v7.app.AlertDialog.Builder builder=new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("输入提示：");
        builder.setMessage("账号或密码错误，请重新输入！");
        builder.setPositiveButton("确定",null);
        builder.show();
    }
    public void showForgetDialog(){
        android.support.v7.app.AlertDialog.Builder builder=new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("忘记密码：");
        builder.setMessage("找回密码请联系 jesseyzhang@126.com");
        builder.setPositiveButton("确定",null);
        builder.show();
    }
}
