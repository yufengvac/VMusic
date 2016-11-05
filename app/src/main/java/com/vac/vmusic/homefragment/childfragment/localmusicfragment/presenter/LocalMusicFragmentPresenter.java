package com.vac.vmusic.homefragment.childfragment.localmusicfragment.presenter;

import com.vac.vmusic.beans.LocalMusic;
import com.vac.vmusic.callback.OnLocalMusicLoadListener;
import com.vac.vmusic.homefragment.childfragment.localmusicfragment.model.LocalMusicFragmentModel;
import com.vac.vmusic.homefragment.childfragment.localmusicfragment.view.ILocalMusicFragment;
import com.vac.vmusic.utils.RxBus;
import com.vac.vmusic.views.MyScrollView;

import java.util.List;

/**
 * Created by vac on 16/10/22.
 *
 */
public class LocalMusicFragmentPresenter implements OnLocalMusicLoadListener{
    private ILocalMusicFragment iLocalMusicFragment;
    private LocalMusicFragmentModel localMusicFragmentModel;
    public LocalMusicFragmentPresenter(ILocalMusicFragment iLocalMusicFragment_){
        this.iLocalMusicFragment = iLocalMusicFragment_;
        localMusicFragmentModel = new LocalMusicFragmentModel();
    }

    public void watchMyScrollView(){
        MyScrollView myScrollView = iLocalMusicFragment.getMyScrollView();
        myScrollView.setOnScrollListener(new MyScrollView.OnScrollListener() {
            @Override
            public void onScroll(int scrollY) {
                RxBus.get().post("scrollY",scrollY);
            }
        });
    }

    public void getNativeMusicCount(){
        localMusicFragmentModel.loadNativeMusic(iLocalMusicFragment.getMyContext(),this);
    }

    @Override
    public void onLocalMusicLoadListener(List<LocalMusic> localMusics) {
        iLocalMusicFragment.showNativeMusicCount(localMusics.size());
    }
}
