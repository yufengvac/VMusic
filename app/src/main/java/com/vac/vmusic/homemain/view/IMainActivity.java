package com.vac.vmusic.homemain.view;

import android.support.v4.app.Fragment;

import com.vac.vmusic.beans.AddFragment;
import com.vac.vmusic.beans.search.TingSong;
import com.vac.vmusic.service.binder.MusicBinder;

import java.util.List;

import rx.Observable;

/**
 * Created by vac on 16/10/21.
 * MainActivity 结论
 */
public interface IMainActivity {
    void addHomeFragment(Fragment fromFragment, Fragment toFragment);
    Observable<AddFragment> getAddFragmentObservable();
    void initService();
    void showSingerPic(String url);
    void initMusicList(List<TingSong> tingSongs);
    MusicBinder getMusicBinderFromMain();
    void initPlayingBottom(TingSong tingSong);
}
