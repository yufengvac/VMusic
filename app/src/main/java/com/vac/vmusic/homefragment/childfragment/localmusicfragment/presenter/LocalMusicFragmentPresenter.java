package com.vac.vmusic.homefragment.childfragment.localmusicfragment.presenter;

import com.vac.vmusic.homefragment.childfragment.localmusicfragment.view.ILocalMusicFragment;
import com.vac.vmusic.utils.RxBus;
import com.vac.vmusic.views.MyScrollView;

/**
 * Created by vac on 16/10/22.
 *
 */
public class LocalMusicFragmentPresenter {
    private ILocalMusicFragment iLocalMusicFragment;
    public LocalMusicFragmentPresenter(ILocalMusicFragment iLocalMusicFragment_){
        this.iLocalMusicFragment = iLocalMusicFragment_;
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

}
