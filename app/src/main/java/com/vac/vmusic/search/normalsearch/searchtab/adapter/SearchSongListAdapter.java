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
import com.vac.vmusic.beans.search.TingSongList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vac on 16/10/18.
 *
 */
public class SearchSongListAdapter extends RecyclerView.Adapter<SearchSongListAdapter.MyViewHolder>{

    private Context mContext;
    private List<TingSongList> mData = new ArrayList<>();
    public SearchSongListAdapter(Context context){
        this.mContext = context;
    }

    private int mLastPosition = -1;
    public void setData(List<TingSongList> list,boolean isRefresh){
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
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TingSongList tingSongList = mData.get(position);
        holder.author.setText(tingSongList.getAuthor());
        holder.name.setText(tingSongList.getTitle());
        int length = 0;
        if (tingSongList.getSong_list()!=null){
            length = tingSongList.getSong_list().split(",").length;
        }
        String numStr = length+"首";
        holder.num.setText(numStr);
        Glide.with(mContext).load(tingSongList.getPic_url()).into(holder.logo);
        setAnimation(holder.itemView,position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_search_songlist,parent,false);
        return new MyViewHolder(view);
    }

    protected void setAnimation(View viewToAnimate, int position) {
        if (position > mLastPosition) {
            Animation animation = AnimationUtils
                    .loadAnimation(viewToAnimate.getContext(), R.anim.item_bottom_in);
            viewToAnimate.startAnimation(animation);
            mLastPosition = position;
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView logo;
        private TextView name,author,num;
            public MyViewHolder(View view) {
                super(view);
                logo = (ImageView) view.findViewById(R.id.item_search_songlist_logo);
                name = (TextView) view.findViewById(R.id.item_search_songlist_name);
                author = (TextView) view.findViewById(R.id.item_search_songlist_author);
                num = (TextView) view.findViewById(R.id.item_search_songlist_num);
            }
        }

}
