package me.mikasa.cyy.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import me.mikasa.cyy.R;
import woo.mikasa.lib.base.BaseRvAdapter;

/**
 * Created by mikasa on 2018/11/25.
 */
public class FlowerAdapter extends BaseRvAdapter<Integer> {
    private static final int fade=3000;
    private Context mContext;
    public FlowerAdapter(Context context){
        this.mContext=context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.item_flowers,parent,false);
        return new FlowerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((FlowerHolder)holder).bindView(mDataList.get(position));
    }
    class FlowerHolder extends BaseRvViewHolder{
        ImageView iv_flower;
        FlowerHolder(View itemView){
            super(itemView);
            iv_flower=itemView.findViewById(R.id.iv_flower);
        }

        @Override
        protected void bindView(Integer i) {
            Glide.with(mContext).load(i)
                    .crossFade(fade)
                    .into(iv_flower);
        }
    }
}
