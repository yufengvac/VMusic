package com.vac.vmusic.playmusic.fragment.lyric.presenter;

import android.util.Log;

import com.vac.vmusic.beans.lyric.LyricXml;
import com.vac.vmusic.beans.search.TingSong;
import com.vac.vmusic.playmusic.fragment.lyric.model.LyricFragmentModel;
import com.vac.vmusic.playmusic.fragment.lyric.view.ILyricFragment;
import com.vac.vmusic.utils.FileUtil;

import java.util.List;

/**
 * Created by vac on 16/11/1.
 *
 */
public class LyricFragmentPresenter implements LyricFragmentModel.OnXmlIDDownloadListener{
    private ILyricFragment iLyricFragment;
    private boolean hasLyric;
    private static final String TAG = LyricFragmentPresenter.class.getName();
    private LyricFragmentModel lyricFragmentModel;
    public LyricFragmentPresenter(ILyricFragment iLyricFragment_){
        this.iLyricFragment = iLyricFragment_;
        lyricFragmentModel = new LyricFragmentModel();
    }

    public void checkLocalLyric(TingSong tingSong){
        hasLyric = tingSong!=null&&FileUtil.isExistLyric(tingSong.getName(),tingSong.getSingerName(),tingSong.getSongId());
        if (hasLyric){

        }else {
            if (tingSong!=null){
                lyricFragmentModel.downloadLyric(tingSong,this);
            }
        }
    }

    @Override
    public void onLyricXmlDownloadCompleted(List<LyricXml> lyricXmlList) {
        for (LyricXml lyricXml:lyricXmlList){
            Log.i(TAG,lyricXml.toString());
        }
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void beforeDownload() {

    }
}
