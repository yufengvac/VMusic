package com.vac.vmusic.songlistdetail.presenter;

import android.support.design.widget.TabLayout;

import com.vac.vmusic.beans.detail.AlbumDetail;
import com.vac.vmusic.beans.search.TingAlbum;
import com.vac.vmusic.callback.RequestCallback;
import com.vac.vmusic.songlistdetail.model.SongListDetailFraModel;
import com.vac.vmusic.songlistdetail.view.ISongListDetailFragment;
import com.vac.vmusic.views.ChildViewPager;

import java.util.List;

/**
 * Created by vac on 16/11/12.
 *
 */
public class SongListDetailFraPresenter implements RequestCallback<AlbumDetail>{
    private ISongListDetailFragment iSongListDetailFragment;
    public SongListDetailFraPresenter(ISongListDetailFragment iSongListDetailFragment_){
        this.iSongListDetailFragment = iSongListDetailFragment_;
    }
    public void loadSongListDetailData(long albumId){
        SongListDetailFraModel songListDetailFraModel = new SongListDetailFraModel();
        songListDetailFraModel.loadDetailDate(albumId,this);
    }

    public void initTabLayout(TabLayout tabLayout, ChildViewPager childViewPager){

    }

    @Override
    public void beforeRequest() {

    }

    @Override
    public void requestCompleted() {

    }

    @Override
    public void requestSuccess(List<AlbumDetail> data) {
        AlbumDetail tingAlbum = data.get(0);
        iSongListDetailFragment.showInfo(tingAlbum);
    }

    @Override
    public void requestError(String errorMsg) {

    }
}
