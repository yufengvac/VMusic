package com.vac.vmusic.homefragment.childfragment.localmusicfragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vac.vmusic.R;
import com.vac.vmusic.beans.search.TingSong;
import com.vac.vmusic.beans.songlist.SongListDetail;
import com.vac.vmusic.utils.HomeColorManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vac on 16/11/16.
 *
 */
public class MySongListAdapter extends BaseAdapter{
    private List<SongListDetail> mData = new ArrayList<>();
    private Context mContext;
    public MySongListAdapter(Context context){
        this.mContext = context;
    }
    public void setData(List<SongListDetail> listDetails){
        mData.clear();
        mData.addAll(listDetails);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mData.get(i).getSonglist_id();
    }

    ViewHolder holder;
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if (convertView==null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_my_song_list,viewGroup,false);
            holder.bgLinearLayout = (LinearLayout) convertView.findViewById(R.id.item_my_song_list_bg_linear_layout);
            holder.title = (TextView) convertView.findViewById(R.id.item_my_song_list_title);
            holder.count = (TextView) convertView.findViewById(R.id.item_my_song_list_count);
            convertView.setTag(holder);
        }else {
            holder =(ViewHolder) convertView.getTag();
        }
        holder.title.setText(mData.get(position).getTitle());
        List<TingSong> tingSongList = mData.get(position).getSongs();
        String countStr ;
        if (tingSongList==null||tingSongList.size()==0){
            countStr = "0首";
        }else {
            countStr = tingSongList.size()+"首";
        }
        holder.count.setText(countStr);
        holder.bgLinearLayout.setBackgroundColor(HomeColorManager.getHomeColorManager().getCurrentColor());
        return convertView;
    }

    private static class ViewHolder{
        private LinearLayout bgLinearLayout;
        private TextView title,count;
    }
}
