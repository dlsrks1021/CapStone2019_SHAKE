package com.example.user.shake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ReportViewAdapter extends BaseAdapter {

    Context context;
    ArrayList<ListVO> list_itemArrayList;
    TextView Title,Content;
    ImageView image;

    public ReportViewAdapter(Context context, ArrayList<ListVO> list_itemArrayList) {
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
            convertView=LayoutInflater.from(context).inflate(R.layout.report_listview,null);
            Title=(TextView)convertView.findViewById(R.id.title);
            Content=(TextView)convertView.findViewById(R.id.context);
            image = (ImageView)convertView.findViewById(R.id.img);
        }
        Title.setText(list_itemArrayList.get(position).getTitle());
        Content.setText(list_itemArrayList.get(position).getContext());
        Glide.with(context).load(list_itemArrayList.get(position).getImg()).into(image);
        return convertView;
    }


}
