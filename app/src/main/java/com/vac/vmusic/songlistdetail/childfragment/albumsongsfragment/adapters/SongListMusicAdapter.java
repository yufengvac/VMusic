package com.vac.vmusic.songlistdetail.childfragment.albumsongsfragment.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vac.vmusic.R;
import com.vac.vmusic.beans.LocalMusic;
import com.vac.vmusic.beans.search.TingAudition;
import com.vac.vmusic.beans.search.TingMV;
import com.vac.vmusic.beans.search.TingSong;
import com.vac.vmusic.callback.OnItemClickListener;
import com.vac.vmusic.utils.HomeColorManager;
import com.vac.vmusic.views.MyManagerButton;
import com.vac.vmusic.views.MyPlayButton;
import com.vac.vmusic.views.PlayingIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vac on 16/11/3.
 *
 */
public class SongListMusicAdapter extends RecyclerView.Adapter<SongListMusicAdapter.MyViewHolder> implements View.OnClickListener{


    private Context mContext;
    private List<TingSong> mData = new ArrayList<>();
    private OnItemClickListener clickListener;

    private final static int TYPE_HEADER = 1;
    private final static int TYPE_NORMAL = 2;

    private View mHeadView;

    private boolean isPause = false;
    private int currentPosition = -1;
    private TingSong currentTingSong;

    private HomeColorManager homeColorManager;
    public SongListMusicAdapter(Context context, OnItemClickListener onItemClickListener){
        this.mContext = context;
        this.clickListener = onItemClickListener;
        homeColorManager = HomeColorManager.getHomeColorManager();
    }
    public void setData(List<TingSong> tingSongs){
        if (tingSongs!=null&&tingSongs.size()>0){
            mData.clear();
            mData.addAll(tingSongs);
            notifyDataSetChanged();
        }
    }

    public void setFlagInPosition(boolean isPause_, int position, TingSong tingSong){
        isPause = isPause_;
        currentPosition = position;
        currentTingSong =tingSong;
        notifyDataSetChanged();
    }

    public void setHeadView(View view){
        this.mHeadView = view;
        notifyItemInserted(0);
    }

    public List<TingSong> getData(){
        return mData;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeadView!=null&&viewType==TYPE_HEADER){
            return new MyViewHolder(mHeadView);
        }else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_search_song,parent,false);

            return new MyViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0){
            return TYPE_HEADER;
        }
        if (mHeadView==null){
            return TYPE_NORMAL;
        }
        return TYPE_NORMAL;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position_) {
        if (getItemViewType(position_)==TYPE_HEADER){
            holder.myPlayButton.setColor(homeColorManager.getCurrentColor());
            holder.myManagerButton.setColor(homeColorManager.getCurrentColor());
        }else {
            int position = getRealPosition(holder);
            TingSong tingSong = mData.get(position);
            holder.name.setText(tingSong.getName());
            holder.singer.setText(tingSong.getSingerName());
            holder.album.setText(tingSong.getAlbumName());
            holder.qualityImageView.setVisibility(View.GONE);
            holder.mvImageView.setVisibility(View.GONE);
            holder.alias.setVisibility(View.GONE);
            holder.addSongListImageView.setVisibility(View.GONE);

            holder.name.setTextColor(ContextCompat.getColor(mContext,R.color.colorBlack));
            holder.alias.setTextColor(ContextCompat.getColor(mContext,R.color.colorDarkGrey));
            holder.singer.setTextColor(HomeColorManager.getHomeColorManager().getCurrentColor());
            holder.album.setTextColor(ContextCompat.getColor(mContext,R.color.colorDarkGrey));


            if (position ==currentPosition&&currentTingSong.getSongId()==tingSong.getSongId()){
                if (isPause){
                    holder.playingIndicator.pauseAnimation();
                }else {
                    holder.playingIndicator.startAnimation();
                }
                holder.playingIndicator.setVisibility(View.VISIBLE);
            }else {
                holder.playingIndicator.pauseAnimation();
                holder.playingIndicator.setVisibility(View.GONE);
            }

            List<TingAudition> tingAuditions = tingSong.getAuditionList();
            if (tingAuditions != null) {
                holder.qualityImageView.setVisibility(View.VISIBLE);
                holder.content.setClickable(true);
                if (tingAuditions.size() == 3) {
                    holder.qualityImageView.setImageResource(R.drawable.icon_hq);
                } else {
                    holder.qualityImageView.setVisibility(View.GONE);
                    if (tingAuditions.size() == 0){
                        holder.content.setClickable(false);
                        holder.name.setTextColor(ContextCompat.getColor(mContext,R.color.colorDarkGrey));
                        holder.alias.setTextColor(ContextCompat.getColor(mContext,R.color.colorDarkGrey));
                        holder.singer.setTextColor(ContextCompat.getColor(mContext,R.color.colorDarkGrey));
                        holder.album.setTextColor(ContextCompat.getColor(mContext,R.color.colorDarkGrey));

                    }
                }

                if (tingSong.getLlList() != null && tingSong.getLlList().size() > 0) {
                    holder.qualityImageView.setImageResource(R.drawable.icon_sq);
                }
            }else {
                holder.content.setBackgroundColor(ContextCompat.getColor(mContext,R.color.colorGrey));
            }

            List<TingMV> tingSearchMVs = tingSong.getMvList();
            if (tingSearchMVs != null && tingSearchMVs.size() > 0) {
                holder.mvImageView.setVisibility(View.VISIBLE);
            } else {
                holder.mvImageView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mHeadView == null ? mData.size() : mData.size() + 1;
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeadView == null ? position : position - 1;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView name,singer,album,alias;
        private LinearLayout content;
        private ImageView qualityImageView,mvImageView,addSongListImageView;

        private MyPlayButton myPlayButton;
        private MyManagerButton myManagerButton;
        private TextView myPlayText,myManageText;

        private PlayingIndicator playingIndicator;
        public MyViewHolder(View view){
            super(view);
            if (view==mHeadView){
                myPlayButton = (MyPlayButton) view.findViewById(R.id.head_play_music_my_play_btn);
                myManagerButton = (MyManagerButton) view.findViewById(R.id.head_play_music_my_manager_btn);
                myPlayButton.setOnClickListener(SongListMusicAdapter.this);
                myManagerButton.setOnClickListener(SongListMusicAdapter.this);

                myPlayText = (TextView)view.findViewById(R.id.head_play_music_my_play_text);
                myManageText = (TextView) view.findViewById(R.id.head_play_music_my_manager_text);
                myPlayText.setOnClickListener(SongListMusicAdapter.this);
                myManageText.setOnClickListener(SongListMusicAdapter.this);
            }else {
                name = (TextView) view.findViewById(R.id.item_home_name);
                singer = (TextView) view.findViewById(R.id.item_home_singer);
                album = (TextView) view.findViewById(R.id.item_home_album);

                qualityImageView = (ImageView) view.findViewById(R.id.item_home_quality_imageview);
                mvImageView = (ImageView) view.findViewById(R.id.item_home_mv_imageview);
                addSongListImageView = (ImageView) view.findViewById(R.id.item_search_song_add_songlist_imageview);
                alias = (TextView) view.findViewById(R.id.item_home_alias);

                playingIndicator = (PlayingIndicator) view.findViewById(R.id.item_search_song_playing_indicator);
                playingIndicator.setIndicatorColor(HomeColorManager.getHomeColorManager().getCurrentColor());

                content = (LinearLayout) view.findViewById(R.id.item_search_song_content);
                content.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clickListener.onItemClick(content,getLayoutPosition()-1);
                    }
                });
            }
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.head_play_music_my_play_btn:
            case R.id.head_play_music_my_play_text:
                Log.i("TAG","随机播放列表");
                break;

            case R.id.head_play_music_my_manager_btn:
            case R.id.head_play_music_my_manager_text:
                Log.i("TAG","管理歌曲");
                break;
        }
    }
}
