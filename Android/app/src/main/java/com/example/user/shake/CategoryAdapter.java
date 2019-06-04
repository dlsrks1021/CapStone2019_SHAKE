package com.example.user.shake;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideContext;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ItemViewHolder> {
    ArrayList<CategoryItem> mItems;
    Context context;
    String borrower;

    public CategoryAdapter(Context context, ArrayList<CategoryItem> items, Intent intent){
        mItems = items;
        this.context = context;
        borrower = intent.getStringExtra("userId");
    }


    // 새로운 뷰 홀더 생성
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_category_item,parent,false);
        return new ItemViewHolder(view);
    }


    // View 의 내용을 해당 포지션의 데이터로 바꿉니다.
    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        String text = mItems.get(position).getName()+"\n"+String.format("%.3f", mItems.get(position).getDistance())+"km\n"+mItems.get(position).getPrice()+"원";
        holder.textView.setText(text);
        holder.ratingBar.setRating(mItems.get(position).getRating());
        if (mItems.get(position).getImageUrl().contains("http")) {
            Glide.with(context).load(mItems.get(position).getImageUrl()).into(holder.bikeImage);
        }

        holder.rentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), RentActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("bikecode", mItems.get(position).getBikecode());
                intent.putExtra("borrower", borrower);
                view.getContext().startActivity(intent);
            }
        });
    }

    // 데이터 셋의 크기를 리턴해줍니다.
    @Override
    public int getItemCount() {
        return mItems.size();
    }

    // 커스텀 뷰홀더
// item layout 에 존재하는 위젯들을 바인딩합니다.
    class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        private RatingBar ratingBar;
        private ImageView bikeImage;
        private Button rentButton;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.category_textview);
            ratingBar = itemView.findViewById(R.id.category_ratingBar);
            bikeImage = itemView.findViewById(R.id.category_bike_image);
            rentButton = itemView.findViewById(R.id.category_rent_button);
        }
    }
}