package com.wellstech.tpictest.list_code;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wellstech.tpictest.R;

import java.util.ArrayList;

public class ListAdapterResultGoods extends RecyclerView.Adapter<ListAdapterResultGoods.ViewHolder> {
    private final ArrayList<ListItemResultGoods> mData;
    private Context context;

    private final String TAG = getClass().getName();

    public ListAdapterResultGoods(ArrayList<ListItemResultGoods> list) {mData=list;}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item_search_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListItemResultGoods item = mData.get(position);

        Glide.with(context).
                load(item.getGoodsImgUrl()).
                placeholder(R.drawable.tp_icon_brand01_on).
                diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).
                into(holder.goodsImg);
//        holder.goodsImg.setTag(item.getGoodsId());
        if (item.getGoodsCategory()!=null) holder.goodsCategory.setText(item.getGoodsCategory());
        holder.goodsName.setText(item.getGoodsName());
        holder.goodsName.setTag(item.getGoodsId());
        holder.goodsPrice.setText(item.getGoodsPrice());
        holder.goodsRate.setText(item.getGoodsRate());
        holder.goodsReviews.setText(item.getGoodsReviewCount());
        holder.goodsImg.setOnClickListener(view -> Log.i(TAG, holder.goodsName.getTag().toString()));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageButton goodsImg;
        TextView goodsCategory, goodsName, goodsPrice, goodsRate, goodsReviews;
        public ViewHolder(View itemView) {
            super(itemView);
            goodsImg = itemView.findViewById(R.id.iBtnResultGoodsImg);
            goodsCategory = itemView.findViewById(R.id.tVwResultGoodsCategory);
            goodsName = itemView.findViewById(R.id.tVwResultGoodsName);
            goodsPrice = itemView.findViewById(R.id.tVwResultGoodsPrice);
            goodsRate = itemView.findViewById(R.id.tVwResultGoodsRate);
            goodsReviews = itemView.findViewById(R.id.tVwResultGoodsReviews);
        }
    }
}