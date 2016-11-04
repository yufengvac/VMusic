package com.vac.vmusic.downmusic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vac.vmusic.R;
import com.vac.vmusic.beans.LocalMusic;
import com.vac.vmusic.callback.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vac on 16/11/3.
 *
 */
public class LocalMusicAdapter extends RecyclerView.Adapter<LocalMusicAdapter.MyViewHolder>{


    private Context mContext;
    private List<LocalMusic> mData = new ArrayList<>();
    private OnItemClickListener clickListener;
    public LocalMusicAdapter(Context context, OnItemClickListener onItemClickListener){
        this.mContext = context;
        this.clickListener = onItemClickListener;
    }
    public void setData(List<LocalMusic> localMusics){
        if (localMusics!=null&&localMusics.size()>0){
            mData.clear();
            mData.addAll(localMusics);
            notifyDataSetChanged();
        }
    }

    public List<LocalMusic> getData(){
        return mData;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_search_song,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        LocalMusic localMusic = mData.get(position);
        holder.name.setText(localMusic.getTitle());
        holder.singer.setText(localMusic.getSingerName());
        holder.album.setText(localMusic.getAlbumName());
        holder.qualityImageView.setVisibility(View.GONE);
        holder.mvImageView.setVisibility(View.GONE);
        holder.alias.setVisibility(View.GONE);
        holder.addSongListImageView.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView name,singer,album,alias;
        private LinearLayout content;
        private ImageView qualityImageView,mvImageView,addSongListImageView;
        public MyViewHolder(View view){
            super(view);
            name = (TextView) view.findViewById(R.id.item_home_name);
            singer = (TextView) view.findViewById(R.id.item_home_singer);
            album = (TextView) view.findViewById(R.id.item_home_album);

            qualityImageView = (ImageView) view.findViewById(R.id.item_home_quality_imageview);
            mvImageView = (ImageView) view.findViewById(R.id.item_home_mv_imageview);
            addSongListImageView = (ImageView) view.findViewById(R.id.item_search_song_add_songlist_imageview);
            alias = (TextView) view.findViewById(R.id.item_home_alias);

            content = (LinearLayout) view.findViewById(R.id.item_search_song_content);
            content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClick(content,getLayoutPosition());
                }
            });
        }
    }
}
