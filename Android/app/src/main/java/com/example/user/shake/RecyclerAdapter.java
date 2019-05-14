package com.example.user.shake;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> {
    ArrayList<RecyclerItem> mItems;

    public RecyclerAdapter(ArrayList<RecyclerItem> items){
        mItems = items;
    }


    // 새로운 뷰 홀더 생성
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_recycler_item,parent,false);
        return new ItemViewHolder(view);
    }


    // View 의 내용을 해당 포지션의 데이터로 바꿉니다.
    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.nameView.setText(mItems.get(position).getName());
        holder.dateView.setText(mItems.get(position).getDate().toString());
        holder.ownerBikeView.setText(mItems.get(position).getOwner()+"'s "+mItems.get(position).getBike());
        holder.contentView.setText(mItems.get(position).getContent());
        holder.ratingBar.setRating(mItems.get(position).getRating());
    }

    // 데이터 셋의 크기를 리턴해줍니다.
    @Override
    public int getItemCount() {
        return mItems.size();
    }

    // 커스텀 뷰홀더
// item layout 에 존재하는 위젯들을 바인딩합니다.
    class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView nameView, dateView, ownerBikeView, contentView;
        private RatingBar ratingBar;

        public ItemViewHolder(View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.recyclerNameView);
            dateView = itemView.findViewById(R.id.recyclerDateView);
            ownerBikeView = itemView.findViewById(R.id.recyclerOwnerBikeView);
            contentView = itemView.findViewById(R.id.recyclerContentView);

            ratingBar = itemView.findViewById(R.id.recyclerRatingBar);

        }
    }
}