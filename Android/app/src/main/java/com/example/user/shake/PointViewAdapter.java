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

public class PointViewAdapter extends BaseAdapter {

    Context context;
    ArrayList<ListVI> list_itemArrayList;
    TextView Title,Content,Finish;
    ImageView image;

    public PointViewAdapter(Context context, ArrayList<ListVI> list_itemArrayList) {
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
            convertView=LayoutInflater.from(context).inflate(R.layout.pointlist,null);
            Title=(TextView)convertView.findViewById(R.id.title);
            Content=(TextView)convertView.findViewById(R.id.context);
            Finish=(TextView)convertView.findViewById(R.id.finish);
            image = (ImageView)convertView.findViewById(R.id.img);
        }
        Title.setText(list_itemArrayList.get(position).getTitle());
        Content.setText(list_itemArrayList.get(position).getContext());
        Finish.setText(list_itemArrayList.get(position).getFinish());
        Glide.with(context).load(list_itemArrayList.get(position).getImg()).into(image);
        return convertView;
    }


}
