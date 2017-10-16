package com.example.myrobot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myrobot.util.ActivityCollector;
import com.example.myrobot.util.HttpUtil;
import com.example.myrobot.util.Utility;
import com.example.myrobot.util.WebActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private String address;
    private String url;
    private List<TextContext> lists;
    private Button send_btn;
    private EditText edit_text;
    private ListView listView;
    private TextAdapter adapter;
    private TextContext textContext;
    private long firstTime;
    private DrawerLayout mDrawerLayout;
    private NavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCollector.addActivity(this);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        initView();
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(MenuItem item){
                switch (item.getItemId()){

                    case R.id.story:
                        url="http://www.xxhh.com/duanzi/";
                        openWeb(url);
                        break;
                    case R.id.scary:
                        url="http://www.guijj.com/dpgs-7-1/";
                        openWeb(url);
                        break;
                    case R.id.photo:
                        url="http://www.xxhh.com/pics/";
                        openWeb(url);
                        break;
                    case R.id.comic:
                        url="https://www.94677.com/";
                        openWeb(url);
                        break;
                    default:
                        break;

                }
                return true;
            }
        });
        TextContext welcome_text=new TextContext("亲爱的主人，我已在此恭候多时了！",TextContext.RECEIVER);
        lists.add(welcome_text);
        listView.setAdapter(adapter);
        send_btn.setOnClickListener(this);

    }

    /**
     * 初始化List
     */
    public void initView(){
        address="http://www.tuling123.com/openapi/api?key=b41ce120ec7c4212b7a2e9c325ee9d38&info=";
        lists=new ArrayList<>();
        send_btn=(Button)findViewById(R.id.send_btn);
        edit_text=(EditText)findViewById(R.id.edit_text);
        listView=(ListView)findViewById(R.id.list_view);
        adapter=new TextAdapter(MainActivity.this,lists);
        firstTime=0;
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        navView=(NavigationView)findViewById(R.id.nav_view);
    }

    public void openWeb(String open_url){
        Intent intent=new Intent(MainActivity.this,WebActivity.class);
        intent.putExtra("url",open_url);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed(){
        long secondTime=System.currentTimeMillis();
        if (secondTime-firstTime>2000){
            Toast.makeText(MainActivity.this,"再按一次退出程序",Toast.LENGTH_SHORT).show();
            firstTime=secondTime;
        }
        else {
            ActivityCollector.finishAll();
        }
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.send_btn:
                String str=edit_text.getText().toString();
                textContext=new TextContext(str,TextContext.SEND);
                lists.add(textContext);
                adapter.notifyDataSetChanged();
                parseText(address+str);
                edit_text.setText("");

                break;
            default:
                break;
        }
    }

    public void parseText(String str){
        HttpUtil.sendOkHttpRequest(str, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,"加载失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText=response.body().string();
                TextContext textContext=new TextContext(Utility.handleText(responseText),TextContext.RECEIVER);
                lists.add(textContext);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }


}
