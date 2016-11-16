package com.vac.vmusic.homefragment.childfragment.localmusicfragment.presenter;

import com.vac.vmusic.beans.LocalMusic;
import com.vac.vmusic.beans.search.TingSong;
import com.vac.vmusic.beans.songlist.SongListDetail;
import com.vac.vmusic.beans.songlist.SongListDetailImage;
import com.vac.vmusic.callback.OnLocalMusicLoadListener;
import com.vac.vmusic.callback.RequestCallback;
import com.vac.vmusic.homefragment.childfragment.localmusicfragment.model.LocalMusicFragmentModel;
import com.vac.vmusic.homefragment.childfragment.localmusicfragment.view.ILocalMusicFragment;
import com.vac.vmusic.utils.RxBus;
import com.vac.vmusic.views.BounceScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vac on 16/10/22.
 *
 */
public class LocalMusicFragmentPresenter implements OnLocalMusicLoadListener,RequestCallback<SongListDetail>{
    private ILocalMusicFragment iLocalMusicFragment;
    private LocalMusicFragmentModel localMusicFragmentModel;
    public LocalMusicFragmentPresenter(ILocalMusicFragment iLocalMusicFragment_){
        this.iLocalMusicFragment = iLocalMusicFragment_;
        localMusicFragmentModel = new LocalMusicFragmentModel();
    }

    public void watchMyScrollView(){
        BounceScrollView myScrollView = iLocalMusicFragment.getMyScrollView();
        myScrollView.setOnScrollListener(new BounceScrollView.OnScrollListener() {
            @Override
            public void onScroll(int scrollY) {
                RxBus.get().post("scrollY",scrollY);
            }
        });
    }

    public void getNativeMusicCount(){
        localMusicFragmentModel.loadNativeMusic(iLocalMusicFragment.getMyContext(),this);
        localMusicFragmentModel.loadMySongList(this);
    }

    public void saveSongList(String songlistName){
        SongListDetail songListDetail = new SongListDetail();
        songListDetail.setCreated_time(System.currentTimeMillis());
        songListDetail.setTitle(songlistName);
        songListDetail.setComment_count("0");
        songListDetail.setLast_updated(System.currentTimeMillis());
        songListDetail.setDesc("");
        SongListDetailImage songListDetailImage = new SongListDetailImage();
        songListDetailImage.setSource(2);
        songListDetail.setImage(songListDetailImage);
        songListDetail.setSong_count(0);
        songListDetail.setSonglist_id(System.currentTimeMillis());
        songListDetail.setSongs(new ArrayList<TingSong>());
        songListDetail.save();

        localMusicFragmentModel.loadMySongList(this);
    }

    @Override
    public void onLocalMusicLoadListener(List<LocalMusic> localMusics) {
        iLocalMusicFragment.showNativeMusicCount(localMusics.size());
    }

    @Override
    public void beforeRequest() {

    }

    @Override
    public void requestCompleted() {

    }

    @Override
    public void requestSuccess(List<SongListDetail> data) {
        iLocalMusicFragment.refreshMySonglist(data);
    }

    @Override
    public void requestError(String errorMsg) {

    }
}
