package com.vac.vmusic.playmusic.presenter;

import android.graphics.Bitmap;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.vac.vmusic.beans.search.artistpic.PicUrls;
import com.vac.vmusic.callback.RequestCallback;
import com.vac.vmusic.playmusic.model.PlayMusicActivityModel;
import com.vac.vmusic.playmusic.view.IPlayMusicActivity;
import com.vac.vmusic.utils.FileUtil;

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
        if (FileUtil.isExitAritstPic(singerName)){
            Log.i("TAG","存在图片-"+singerName_);
            iPlayMusicActivity.showArtistPic(singerName);
        }else {
            Log.i("TAG","不存在图片-"+singerName_+",开始下载");
            playMusicActivityModel = new PlayMusicActivityModel();
            playMusicActivityModel.searchArtistPics(singerName,this);
        }

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
