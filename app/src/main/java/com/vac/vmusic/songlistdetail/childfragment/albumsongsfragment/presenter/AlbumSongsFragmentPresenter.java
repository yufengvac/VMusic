package com.vac.vmusic.songlistdetail.childfragment.albumsongsfragment.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.vac.vmusic.R;
import com.vac.vmusic.beans.detail.AlbumDetail;
import com.vac.vmusic.beans.songlist.SongListDetail;
import com.vac.vmusic.callback.OnItemClickListener;
import com.vac.vmusic.songlistdetail.childfragment.albumsongsfragment.adapters.SongListMusicAdapter;
import com.vac.vmusic.songlistdetail.childfragment.albumsongsfragment.view.IAlbumSongsFragment;
import com.vac.vmusic.views.DividerItemDecoration;

/**
 * Created by vac on 16/11/14.
 *
 */
public class AlbumSongsFragmentPresenter implements OnItemClickListener{
    private AlbumDetail albumDetail;
    private SongListDetail songListDetail;
    private IAlbumSongsFragment iAlbumSongsFragment;
    private String type;
    public AlbumSongsFragmentPresenter(IAlbumSongsFragment iAlbumSongsFragment_){
        this.iAlbumSongsFragment = iAlbumSongsFragment_;
    }
    public void loadData(Bundle bundle, RecyclerView recyclerView, Context context){
        type = bundle.getString("type");
        if ("songlist".equals(type)){
            songListDetail = bundle.getParcelable("songlistDetail");
            if (songListDetail==null){
                return;
            }
        }else if ("album".equals(type)){
            albumDetail = bundle.getParcelable("albumDetail");
            if (albumDetail==null){
                return;
            }
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(context,LinearLayoutManager.VERTICAL));
        SongListMusicAdapter songListMusicAdapter = new SongListMusicAdapter(context,this);
        songListMusicAdapter.setData(type.equals("album")?albumDetail.getSongList():songListDetail.getSongs());
        recyclerView.setAdapter(songListMusicAdapter);
        View headView = LayoutInflater.from(context).inflate(R.layout.head_play_music,recyclerView,false);
        songListMusicAdapter.setHeadView(headView);
    }

    @Override
    public void onItemClick(View view, int position) {
        if ("songlist".equals(type)){
            iAlbumSongsFragment.getMyMusicBinder().setMusicPlayList(songListDetail.getSongs(),true);
            iAlbumSongsFragment.getMyMusicBinder().beginToPlay(position,songListDetail.getSongs().get(position));
        }else if ("album".equals(type)){
            iAlbumSongsFragment.getMyMusicBinder().setMusicPlayList(albumDetail.getSongList(),true);
            iAlbumSongsFragment.getMyMusicBinder().beginToPlay(position,albumDetail.getSongList().get(position));
        }

    }
}
