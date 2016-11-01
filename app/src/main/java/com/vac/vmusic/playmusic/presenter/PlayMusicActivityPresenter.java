package com.vac.vmusic.playmusic.presenter;

import android.graphics.Bitmap;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.vac.vmusic.base.BaseSwipeBackFragment;
import com.vac.vmusic.beans.search.artistpic.PicUrls;
import com.vac.vmusic.callback.RequestCallback;
import com.vac.vmusic.homefragment.adapters.CustomFragmentAdapter;
import com.vac.vmusic.homefragment.adapters.MyFragmentAdapter;
import com.vac.vmusic.playmusic.fragment.lyric.view.LyricFragment;
import com.vac.vmusic.playmusic.fragment.relative.view.RelativeFragment;
import com.vac.vmusic.playmusic.model.PlayMusicActivityModel;
import com.vac.vmusic.playmusic.view.IPlayMusicActivity;
import com.vac.vmusic.utils.FileUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vac on 16/10/29.
 *
 */
public class PlayMusicActivityPresenter implements RequestCallback<PicUrls>{
    private IPlayMusicActivity iPlayMusicActivity;
    private PlayMusicActivityModel playMusicActivityModel;
    private String singerName;

    public PlayMusicActivityPresenter(IPlayMusicActivity iPlayMusicActivity_){
        this.iPlayMusicActivity = iPlayMusicActivity_;
    }

    public void loadArtistPic(String singerName_){
        singerName = singerName_;
        if (FileUtil.isExistArtistPic(singerName)){
            Log.i("TAG","存在图片-"+singerName_);
            iPlayMusicActivity.showArtistPic(singerName);
        }else {
            Log.i("TAG","不存在图片-"+singerName_+",开始下载");
            playMusicActivityModel = new PlayMusicActivityModel();
            playMusicActivityModel.searchArtistPics(singerName,this);
        }

    }

    public void loadViewPager(FragmentManager fragmentManager,ViewPager viewPager){
        List<BaseSwipeBackFragment> baseSwipeBackFragments = new ArrayList<>();
        LyricFragment lyricFragment = LyricFragment.getLyricFragment();
        RelativeFragment relativeFragment = RelativeFragment.getRelativedFragment();
        baseSwipeBackFragments.add(lyricFragment);
        baseSwipeBackFragments.add(relativeFragment);
        CustomFragmentAdapter myFragmentAdapter = new CustomFragmentAdapter(fragmentManager);
        myFragmentAdapter.setData(baseSwipeBackFragments);
        viewPager.setAdapter(myFragmentAdapter);
    }

    @Override
    public void beforeRequest() {

    }

    @Override
    public void requestCompleted() {

    }

    @Override
    public void requestSuccess(List<PicUrls> data) {
        Log.i("PlayMusicActivityPre","size="+data.size());
        for (int i=0;i<data.size();i++){
            String picUrl = data.get(i).getPicUrl();
            playMusicActivityModel.downloadPic(iPlayMusicActivity.getPlayMusicContext(),picUrl,singerName);
        }
        iPlayMusicActivity.showArtistPic(singerName);
    }

    @Override
    public void requestError(String errorMsg) {

    }
}
