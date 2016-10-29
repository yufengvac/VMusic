package com.vac.vmusic.playmusic.presenter;

import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.vac.vmusic.beans.search.artistpic.PicUrls;
import com.vac.vmusic.callback.RequestCallback;
import com.vac.vmusic.playmusic.model.PlayMusicActivityModel;
import com.vac.vmusic.playmusic.view.IPlayMusicActivity;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by vac on 16/10/29.
 *
 */
public class PlayMusicActivityPresenter implements RequestCallback<PicUrls>{
    private IPlayMusicActivity iPlayMusicActivity;

    public PlayMusicActivityPresenter(IPlayMusicActivity iPlayMusicActivity_){
        this.iPlayMusicActivity = iPlayMusicActivity_;
    }

    public void loadArtistPic(String singerName){
        PlayMusicActivityModel playMusicActivityModel = new PlayMusicActivityModel();
        playMusicActivityModel.searchArtistPics(singerName,this);

    }

    @Override
    public void beforeRequest() {

    }

    @Override
    public void requestCompleted() {

    }

    @Override
    public void requestSuccess(List<PicUrls> data) {
        for (int i=0;i<data.size();i++){
            String picUrl = data.get(i).getPicUrl();
            try {
                Log.e("PlayMusicActivityPre","i="+i+",url="+picUrl);
                File file = Glide.with(iPlayMusicActivity.getPlayMusicContext()).load(picUrl)
                        .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
                Log.i("PlayMusicActivityPre",file.getAbsolutePath());
            }catch (ExecutionException e){
                e.printStackTrace();
                Log.e("PlayMusicActivityPre",e.getMessage());
            }catch (InterruptedException e){
                e.printStackTrace();
                Log.e("PlayMusicActivityPre",e.getMessage());
            }

        }
    }

    @Override
    public void requestError(String errorMsg) {

    }
}
