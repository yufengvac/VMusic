package com.vac.vmusic.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.vac.vmusic.beans.LocalMusic;
import com.vac.vmusic.beans.search.TingSong;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vac on 16/11/3.
 *
 */
public class ContentProviderHelper {
    private ContentResolver contentResolver;
    private List<LocalMusic> localMusicList = new ArrayList<>();
    private String[] mProjection = null;
    public ContentProviderHelper(Context context){
        if (contentResolver==null){
            contentResolver = context.getContentResolver();
        }
        if (mProjection==null){
            mProjection = new String[]{
                    MediaStore.Audio.Media._ID,
                    MediaStore.Audio.Media.TITLE,
                    MediaStore.Audio.Media.DATA,
                    MediaStore.Audio.Media.ALBUM,
                    MediaStore.Audio.Media.ALBUM_ID,
                    MediaStore.Audio.Media.ARTIST,
                    MediaStore.Audio.Media.DATE_ADDED,
                    MediaStore.Audio.Media.DATE_MODIFIED,
                    MediaStore.Audio.Media.DURATION,
                    MediaStore.Audio.Media.SIZE,
            };
        }
    }
    public void addContent(TingSong tingSong,String path){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Audio.Media.TITLE,tingSong.getName());
        contentValues.put(MediaStore.Audio.Media.ALBUM,tingSong.getAlbumName());
        contentValues.put(MediaStore.Audio.Media.ARTIST,tingSong.getSingerName());
        contentValues.put(MediaStore.Audio.Media.ALBUM_ID,tingSong.getAlbumId());
        contentValues.put(MediaStore.Audio.Media.ARTIST_ID,tingSong.getSingerId());
        contentValues.put(MediaStore.Audio.Media.DURATION,tingSong.getAuditionList().get(tingSong.getAuditionList().size()-1).getDuration());
        contentValues.put(MediaStore.Audio.Media.SIZE,tingSong.getAuditionList().get(tingSong.getAuditionList().size()-1).getSize());
        contentValues.put(MediaStore.Audio.Media.DATA,path);
        contentResolver.insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,contentValues);
    }

    public List<LocalMusic> getLocalMusicList(){
        localMusicList.clear();
        Cursor cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,mProjection,null,null,null);
        if (cursor!=null){
            while (cursor.moveToNext()){
                LocalMusic localMusic = new LocalMusic();
                localMusic.setSongId(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)));
                localMusic.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)));
                localMusic.setAlbumId(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)));
                localMusic.setAlbumName(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)));
//                localMusic.setArtistId(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST_ID)));
                localMusic.setSingerName(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)));
                localMusic.setData(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));
                localMusic.setDuration(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)));
                localMusic.setSize(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)));
                localMusicList.add(localMusic);
            }
            cursor.close();
        }
        return localMusicList;
    }
}
