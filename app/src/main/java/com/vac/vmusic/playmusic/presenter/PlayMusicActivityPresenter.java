package com.vac.vmusic.playmusic.presenter;

import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.vac.vmusic.base.BaseSwipeBackFragment;
import com.vac.vmusic.beans.search.TingAudition;
import com.vac.vmusic.beans.search.TingSong;
import com.vac.vmusic.beans.search.artistpic.PicUrls;
import com.vac.vmusic.callback.RequestCallback;
import com.vac.vmusic.downloadmanager.DownLoadListener;
import com.vac.vmusic.downloadmanager.DownLoadManager;
import com.vac.vmusic.downloadmanager.dbcontrol.FileHelper;
import com.vac.vmusic.homefragment.adapters.CustomFragmentAdapter;
import com.vac.vmusic.playmusic.fragment.lyric.view.LyricFragment;
import com.vac.vmusic.playmusic.fragment.relative.view.RelativeFragment;
import com.vac.vmusic.playmusic.model.PlayMusicActivityModel;
import com.vac.vmusic.playmusic.view.IPlayMusicActivity;
import com.vac.vmusic.utils.FileUtil;
import com.vac.vmusic.utils.RxBus;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by vac on 16/10/29.
 *
 */
public class PlayMusicActivityPresenter implements RequestCallback<PicUrls>,PlayMusicActivityModel.OnPicDownloadListener{
    private IPlayMusicActivity iPlayMusicActivity;
    private PlayMusicActivityModel playMusicActivityModel;
    private String singerName;
    private int picCount = -1;

    private Subscription downloadSongSub;

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
    public void checkIsDownloaded(TingSong tingSong){
        if (FileUtil.isExistSong(tingSong)){
            iPlayMusicActivity.songHasDownload();
        }else {
            iPlayMusicActivity.songNotDownload();
        }
    }

    public void downloadSong(TingSong tingSong){
        DownLoadManager downLoadManager = new DownLoadManager(iPlayMusicActivity.getPlayMusicContext());
        String taskID = tingSong.getSongId()+"";
        if (tingSong.getAuditionList()!=null&&tingSong.getAuditionList().size()>0){
            TingAudition tingAudition = tingSong.getAuditionList().get(tingSong.getAuditionList().size()-1);
            String downUrl = tingAudition.getUrl();
            String fileName = tingSong.getName()+ "." +tingAudition.getSuffix();
            int state = downLoadManager.addTask(taskID, downUrl,fileName, FileHelper.getFileDefaultPath()+fileName);
            downLoadManager.setAllTaskListener((DownLoadListener)iPlayMusicActivity.getPlayMusicContext());
            if (state==-1){
                Snackbar.make(iPlayMusicActivity.getSnakeBarView(),"文件已存在!",Snackbar.LENGTH_LONG).show();
            }else if (state==1){
                Snackbar.make(iPlayMusicActivity.getSnakeBarView(),"正在下载!",Snackbar.LENGTH_LONG).show();
                if (downloadSongSub==null){
                    downloadSongSub = RxBus.get().register("downloadSong",Long.class).subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long id) {
                            if (iPlayMusicActivity.getCurrentTingSong().getSongId()==id){
                                iPlayMusicActivity.songHasDownload();
                            }
                        }
                    });
                }
            }
        }else {
            Snackbar.make(iPlayMusicActivity.getSnakeBarView(),"无法下载!",Snackbar.LENGTH_LONG).show();
        }
    }

    public void unSubScritionForDownload(){
        if (downloadSongSub!=null&&(!downloadSongSub.isUnsubscribed())){
            downloadSongSub.unsubscribe();
            downloadSongSub = null;
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
        picCount = data.size();
        Log.i("PlayMusicActivityPre","size="+picCount);
        for (int i=0;i<data.size();i++){
            String picUrl = data.get(i).getPicUrl();
            playMusicActivityModel.downloadPic(iPlayMusicActivity.getPlayMusicContext(),picUrl,singerName,i,this);
        }
    }

    @Override
    public void requestError(String errorMsg) {

    }

    @Override
    public void onPicDownload(int i) {
        String[] picUrls = FileUtil.getArtistByName(singerName);
        if (picUrls!=null&&picUrls.length==picCount){
            iPlayMusicActivity.showArtistPic(singerName);
        }
    }
}
