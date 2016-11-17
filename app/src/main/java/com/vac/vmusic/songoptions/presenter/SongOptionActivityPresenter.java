package com.vac.vmusic.songoptions.presenter;

import com.vac.vmusic.beans.LocalMusic;
import com.vac.vmusic.beans.search.TingAudition;
import com.vac.vmusic.beans.search.TingSong;
import com.vac.vmusic.songoptions.view.ISongOptionActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vac on 16/11/17.
 *
 */
public class SongOptionActivityPresenter {
    private ISongOptionActivity iSongOptionActivity;
    public SongOptionActivityPresenter(ISongOptionActivity iSongOptionActivity_){
        this.iSongOptionActivity = iSongOptionActivity_;
    }
    public TingSong translate(LocalMusic localMusic){
        TingSong tingSong = new TingSong();
        List<TingAudition> tingAuditionList = new ArrayList<>();
        TingAudition tingAudition = new TingAudition();
        tingAudition.setSize(localMusic.getSize());
        tingAudition.setDuration(localMusic.getDuration());
        tingAudition.setSuffix("mp3");
        tingAudition.setUrl(localMusic.getData());
        tingAudition.setTypeDescription("标准品质");
        tingAudition.setBitRate(128);
        tingAuditionList.add(tingAudition);
        tingSong.setAuditionList(tingAuditionList);
        tingSong.setName(localMusic.getTitle());
        tingSong.setSongId(localMusic.getId()>0?localMusic.getId():localMusic.getSongId());
        tingSong.setSingerId(localMusic.getSingerId());
        tingSong.setSingerName(localMusic.getSingerName());
        tingSong.setAlbumName(localMusic.getAlbumName());
        tingSong.setAlbumId(localMusic.getAlbumId());
        tingSong.setAlias(localMusic.getAlias());
        return tingSong;
    }
}
