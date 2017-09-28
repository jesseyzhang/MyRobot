package com.example.myrobot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by DELL 15 on 2017/9/19.
 */

public class TextAdapter extends BaseAdapter{

    private int resourceId;
    private List<TextContext> list=null;
    private Context context=null;
    private LayoutInflater inflater=null;

    public TextAdapter(Context context, List<TextContext> list){
        this.context=context;
        this.list=list;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public boolean areAllItemsEnabled(){
        return false;
    }

    @Override
    public boolean isEnabled(int position){
        return false;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        View view=null;
        TextContext text=list.get(position);
        if (text.getFlag()==1){
            view=inflater.inflate(R.layout.right_talker,null);
        }else if (text.getFlag()==2){
            view=inflater.inflate(R.layout.left_talker,null);
        }

        TextView textView=(TextView)view.findViewById(R.id.text_view);
        textView.setText(text.getContent());
        return view;
    }
}
