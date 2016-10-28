package com.vac.vmusic.homemain.presenter;


import android.util.Log;

import com.vac.vmusic.beans.AddFragment;
import com.vac.vmusic.beans.search.TingArtist;
import com.vac.vmusic.beans.search.TingAudition;
import com.vac.vmusic.beans.search.TingSong;
import com.vac.vmusic.callback.RequestCallback;
import com.vac.vmusic.homefragment.view.HomeFragment;
import com.vac.vmusic.homemain.model.MainActivityModel;
import com.vac.vmusic.homemain.view.IMainActivity;
import com.vac.vmusic.utils.PreferHelper;

import org.litepal.crud.DataSupport;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by vac on 16/10/21.
 * MainActivityPresenter
 */
public class MainActivityPresenter implements RequestCallback<TingArtist>{
    private IMainActivity iMainActivity;
    private MainActivityModel mainActivityModel;
    public MainActivityPresenter(IMainActivity iMainActivity_){
        this.iMainActivity = iMainActivity_;
        mainActivityModel = new MainActivityModel();
    }

    public void initContent(){
        iMainActivity.addHomeFragment(null, HomeFragment.getHomeFragment(null));
        iMainActivity.getAddFragmentObservable().subscribe(new Action1<AddFragment>() {
            @Override
            public void call(AddFragment addFragment) {
                iMainActivity.addHomeFragment(addFragment.getFromFragment(),addFragment.getToFragment());
            }
        });

    }

    public void loadLastMusicList(){
        iMainActivity.initMusicList(DataSupport.findAll(TingSong.class,true));
//        List<TingAudition> tingAuditions = DataSupport.findAll(TingAudition.class,true);
//        for (TingAudition tingAudition:tingAuditions){
//            Log.i("TAG",tingAudition.toString());
//        }

//        TingSong tingSong = DataSupport.find(TingSong.class,1,true);
//        if (tingSong!=null){
//            Log.i("TAG",tingSong.toString());
//        }
//        List<TingAudition> tingAuditions = DataSupport.findAll(TingAudition.class,true);
//        for (int i=0;i<tingAuditions.size();i++){
//            Log.i("TAG",tingAuditions.get(i).toString());
//        }

//        List<TingSong> tingSongList = DataSupport.findAll(TingSong.class,true);
//        for (TingSong tingSong:tingSongList){
//            Log.i("TAG",tingSong.toString());
//        }

    }

    public void loadArtistPic(String singerName){
        mainActivityModel.searchArtist(singerName,this);
    }

    public void initFirstWidgetPlayBottom(){
        int lastPlayingPosition = PreferHelper.getLastPlayingPosition();
        if (lastPlayingPosition!=-1){
            List<TingSong> tingSongs = iMainActivity.getMusicBinderFromMain().getMusicPlayList();
            iMainActivity.getMusicBinderFromMain().initToPlay(lastPlayingPosition,tingSongs.get(lastPlayingPosition));
            iMainActivity.initPlayingBottom(tingSongs.get(lastPlayingPosition));
        }
    }

    @Override
    public void beforeRequest() {

    }

    @Override
    public void requestCompleted() {

    }

    @Override
    public void requestSuccess(List<TingArtist> data) {
        if (data!=null&&data.size()>0){
            iMainActivity.showSingerPic(data.get(0).getPic_url());
        }

    }

    @Override
    public void requestError(String errorMsg) {

    }
}
