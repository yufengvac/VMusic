package com.vac.vmusic.search.normalsearch.searchtab.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vac.vmusic.R;
import com.vac.vmusic.beans.search.TingAlbum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vac on 16/10/18.
 *
 */
public class SearchAlbumAdapter extends RecyclerView.Adapter<SearchAlbumAdapter.MyViewHolder> {

    private Context mContext;
    private List<TingAlbum> mData = new ArrayList<>();
    public SearchAlbumAdapter(Context context){
        this.mContext = context;
    }

    private int mLastPosition = -1;
    public void setData(List<TingAlbum> list,boolean isRefresh){
        if (list!=null&&list.size()>0){
            int count = mData.size();
            if (isRefresh){
                mData.clear();
                mData.addAll(list);
                notifyDataSetChanged();
            }else {
                mData.addAll(list);
                notifyItemChanged(count,list.size());
            }

        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_search_album,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TingAlbum tingAlbum = mData.get(position);
        Glide.with(mContext).load(tingAlbum.getPicUrl()).into(holder.logo);
        holder.name.setText(tingAlbum.getName());
        holder.singer.setText(tingAlbum.getSingerName());
        holder.date.setText(tingAlbum.getPublishDate());
        setAnimation(holder.itemView,position);
    }
    protected void setAnimation(View viewToAnimate, int position) {
        if (position > mLastPosition) {
            Animation animation = AnimationUtils
                    .loadAnimation(viewToAnimate.getContext(), R.anim.item_bottom_in);
            viewToAnimate.startAnimation(animation);
            mLastPosition = position;
        }
    }

    protected class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView logo;
        private TextView name,singer,date;
        public MyViewHolder(View view){
            super(view);
            logo = (ImageView) view.findViewById(R.id.item_search_album_logo);
            name = (TextView) view.findViewById(R.id.item_search_album_name);
            singer = (TextView) view.findViewById(R.id.item_search_album_singer);
            date = (TextView) view.findViewById(R.id.item_search_album_release_date);
        }
    }
}
