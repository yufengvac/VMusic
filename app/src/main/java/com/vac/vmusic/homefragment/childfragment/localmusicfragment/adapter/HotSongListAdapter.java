package com.vac.vmusic.homefragment.childfragment.localmusicfragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vac.vmusic.R;
import com.vac.vmusic.beans.discover.DiscoverColumnData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yufengvac on 2016/11/8.
 *
 */
public class HotSongListAdapter extends BaseAdapter{

    private List<DiscoverColumnData> mData = new ArrayList<>();
    private Context mContext;
    public HotSongListAdapter(Context context){
        this.mContext = context;
    }
    public void setData(List<DiscoverColumnData> list){
        if (list!=null){
            mData.clear();
            mData.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mData.size()>6?6:mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mData.get(position).getId();
    }

    ViewHolder holder;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_hot_song_list,parent,false);
            holder.imageView = (ImageView) convertView.findViewById(R.id.item_hot_song_list_logo);
            holder.textView = (TextView) convertView.findViewById(R.id.item_hot_song_list_desc);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        DiscoverColumnData discoverColumnData = mData.get(position);
        Glide.with(mContext).load(discoverColumnData.getPicUrl()).centerCrop().into(holder.imageView);
        holder.textView.setText(discoverColumnData.getName());
        return convertView;
    }

    private class ViewHolder{
        private ImageView imageView;
        private TextView textView;
    }
}
