package com.example.user.shake;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdminReportAdapter extends BaseAdapter {

    Context context;
    ArrayList<ListVT> list_itemArrayList;
    TextView Title,Content,Finish,Bikecode,Renttime;
    ImageView image;

    public AdminReportAdapter(Context context, ArrayList<ListVT> list_itemArrayList) {
        this.context = context;
        this.list_itemArrayList = list_itemArrayList;
    }

    @Override
    public int getCount(){
        return this.list_itemArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return list_itemArrayList.get(position) ;
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    public void onItemClick
            (AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public View getView(int position, View convertView,ViewGroup parent){
        if(convertView==null){
            convertView=LayoutInflater.from(context).inflate(R.layout.adminreportlist,null);
            Title=(TextView)convertView.findViewById(R.id.textView_user);
            Content=(TextView)convertView.findViewById(R.id.textView_content);
            Finish=(TextView)convertView.findViewById(R.id.textView_code);
            image = (ImageView)convertView.findViewById(R.id.img);
            Renttime=(TextView)convertView.findViewById(R.id.textView_time);
            Bikecode=(TextView)convertView.findViewById(R.id.textView_bikecode);
        }
        Title.setText(list_itemArrayList.get(position).getTitle());
        Content.setText(list_itemArrayList.get(position).getContext());
        Finish.setText(list_itemArrayList.get(position).getFinish());
        Renttime.setText(list_itemArrayList.get(position).getRenttime());
        Bikecode.setText(list_itemArrayList.get(position).getRequestCode());
        Glide.with(context).load(list_itemArrayList.get(position).getImg()).into(image);
        return convertView;
    }


}
