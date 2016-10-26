package com.vac.vmusic.playmusicqueue.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vac.vmusic.R;
import com.vac.vmusic.beans.search.TingSong;
import com.vac.vmusic.callback.OnItemClickListener;
import com.vac.vmusic.utils.HomeColorManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vac on 16/10/25.
 *
 */
public class MusicQueueAdapter extends RecyclerView.Adapter<MusicQueueAdapter.MyViewHolder> {

    private List<TingSong> mData = new ArrayList<>();
    private Context mContext;
    private int color;

    public List<TingSong> getData(){
        return mData;
    }

    private OnItemClickListener onItemClickListener;
    public MusicQueueAdapter(Context context,OnItemClickListener onItemClickListener_){
        this.mContext = context;
        this.onItemClickListener = onItemClickListener_;
        color = new HomeColorManager().getCurrentColor();
    }

    public void setData(List<TingSong> tingSongs){
        if (tingSongs!=null&&tingSongs.size()>0){
            mData.clear();
            mData.addAll(tingSongs);
            notifyDataSetChanged();
        }
    }
    public void setFocuse(int lastPosition,int position){
       if (lastPosition>=0){
           mData.get(lastPosition).setPlaying(false);
           notifyItemChanged(lastPosition);
       }
        mData.get(position).setPlaying(true);
        notifyItemChanged(position);
    }

    public void favorSong(int position){
        mData.get(position).setFavored(true);
        notifyItemChanged(position);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_music_queue,parent,false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TingSong tingSong = mData.get(position);
        holder.songNameView.setText(tingSong.getName());
        String singerStr = "-"+tingSong.getSingerName();
        holder.singerView.setText(singerStr);
        if (tingSong.isPlaying()){
            holder.songNameView.setTextColor(color);
            holder.singerView.setTextColor(color);
        }else {
            holder.songNameView.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
            holder.singerView.setTextColor(mContext.getResources().getColor(R.color.colorDarkGrey));
        }

        if (tingSong.isFavored()){
            holder.favorImageView.setImageResource(R.drawable.icon_favor_yes);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout content;
        private TextView songNameView;
        private TextView singerView;
        private ImageView favorImageView,removeImageView;
        public MyViewHolder(View view){
            super(view);
            songNameView =(TextView) view.findViewById(R.id.item_music_queue_song_name_view);
            singerView = (TextView) view.findViewById(R.id.item_music_queue_song_singer_view);
            favorImageView = (ImageView) view.findViewById(R.id.item_music_queue_favor_image_view);
            removeImageView = (ImageView)view.findViewById(R.id.item_music_queue_remove_image_view);

            content = (LinearLayout) view.findViewById(R.id.item_music_queue_content);
            content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(content,getLayoutPosition());
                }
            });
            favorImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    favorImageView.setTag("favor");
                    onItemClickListener.onItemClick(favorImageView,getLayoutPosition());
                }
            });
            removeImageView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    removeImageView.setTag("remove");
                    onItemClickListener.onItemClick(removeImageView,getLayoutPosition());
                }
            });
        }
    }
}
