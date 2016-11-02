package com.vac.vmusic.playmusic.fragment.lyric.presenter;

import android.util.Log;

import com.vac.vmusic.beans.lyric.LyricSentence;
import com.vac.vmusic.beans.lyric.LyricXml;
import com.vac.vmusic.beans.search.TingSong;
import com.vac.vmusic.playmusic.fragment.lyric.model.LyricFragmentModel;
import com.vac.vmusic.playmusic.fragment.lyric.view.ILyricFragment;
import com.vac.vmusic.playmusic.fragment.lyric.view.LyricFragment;
import com.vac.vmusic.utils.FileUtil;
import com.vac.vmusic.utils.LyricLoadHelper;

import java.io.File;
import java.util.List;

/**
 * Created by vac on 16/11/1.
 *
 */
public class LyricFragmentPresenter implements LyricFragmentModel.OnXmlIDDownloadListener,LyricFragmentModel.OnLyricDownloadListener {
    private ILyricFragment iLyricFragment;
    private boolean hasLyric;
    private static final String TAG = LyricFragmentPresenter.class.getSimpleName();
    private LyricFragmentModel lyricFragmentModel;
    private TingSong currentSong;

    public LyricFragmentPresenter(ILyricFragment iLyricFragment_, LyricFragment lyricFragment){
        this.iLyricFragment = iLyricFragment_;
        lyricFragmentModel = new LyricFragmentModel();

    }

    public void checkLocalLyric(TingSong tingSong){
        currentSong = tingSong;
        hasLyric = currentSong!=null&&FileUtil.isExistLyric(currentSong);
        if (hasLyric){
            Log.i(TAG,"歌词存在不需要去下载");
            iLyricFragment.loadLyric(FileUtil.getLyric(currentSong));
        }else {
            if (currentSong!=null){
                lyricFragmentModel.downloadLyricId(currentSong,this);
            }
        }
    }

    @Override
    public void onLyricXmlDownloadCompleted(List<LyricXml> lyricXmlList) {
        long lyricId = -1L;
        for (LyricXml lyricXml:lyricXmlList){
           if ("0".equals(lyricXml.getTrc())){//是一行行的歌词形式
               lyricId = Long.parseLong(lyricXml.getLrcID());
               break;
           }
        }
        if (lyricId!=-1){
            lyricFragmentModel.downloadLyricContent(lyricId,this);
        }else {
            Log.i(TAG,"没有找到一行行的歌词");
        }
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void beforeDownload() {

    }

    @Override
    public void onSuccess(String result) {
        FileUtil.saveLyric(currentSong,result);

        iLyricFragment.loadLyric(result);
    }

}
