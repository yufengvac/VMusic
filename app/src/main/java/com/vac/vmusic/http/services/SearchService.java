package com.vac.vmusic.http.services;


import com.vac.vmusic.beans.httpresult.HttpResult;
import com.vac.vmusic.beans.httpresult.HttpResultPic;
import com.vac.vmusic.beans.httpresult.HttpResultPlus;
import com.vac.vmusic.beans.search.TingAlbum;
import com.vac.vmusic.beans.search.TingArtist;
import com.vac.vmusic.beans.search.TingSearchMV;
import com.vac.vmusic.beans.search.TingSong;
import com.vac.vmusic.beans.search.TingSongList;
import com.vac.vmusic.beans.search.artistpic.ArtistPic;
import com.vac.vmusic.beans.search.artistpic.PicUrls;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by vac on 16/10/15.
 * SearchService
 */
public interface SearchService {

    /****
     * 搜索单曲
     * @param cacheControl 缓存控制
     * @param size 每页条数
     * @param page 第page页
     * @param q 关键字
     * @return Observable
     */
    @GET("song/search")
    Observable<HttpResult<TingSong>> searchSong(@Header("Cache-Control") String cacheControl,
                                                @Query("size") int size, @Query("page") int page, @Query("q") String q);

    /***
     * 搜索专辑
     * @param cacheControl 缓存控制
     * @param size 每页条数
     * @param page 第page页
     * @param q 关键字
     * @return Observable
     */
    @GET("album/search")
    Observable<HttpResult<TingAlbum>> searchAlbum(@Header("Cache-Control") String cacheControl,
                                                  @Query("size") int size, @Query("page") int page, @Query("q") String q);

    /***
     * 搜索MV
     * @param cacheControl 缓存控制
     * @param size 每页条数
     * @param page 第page页
     * @param q 关键字
     * @return Observable
     */
    @GET("mv/search")
    Observable<HttpResultPlus<TingSearchMV>> searchMV(@Header("Cache-Control") String cacheControl,
                                                      @Query("size") int size, @Query("page") int page, @Query("q") String q);
    /***
     * 搜索歌单
     * @param cacheControl 缓存控制
     * @param size 每页条数
     * @param page 第page页
     * @param q 关键字
     * @return Observable
     */
    @GET("songlist/search")
    Observable<HttpResultPlus<TingSongList>> searchSongList(@Header("Cache-Control") String cacheControl,
                                                            @Query("size") int size, @Query("page") int page, @Query("q") String q);

    /**
     * 搜索歌手信息,用于首页的歌手头像的搜索
     * @param cacheControl 缓存控制
     * @param size size
     * @param q 关键词
     * @return Observable
     */
    @GET("artist/search")
    Observable<HttpResultPlus<TingArtist>> searchArtist(@Header("Cache-Control") String cacheControl,
                                                        @Query("size") int size,@Query("q") String q);

    /**
     * 搜索歌手图片
     * @param cacheControl 缓存控制
     * @param artist 歌手名字
     * @return Observable
     */
    @GET("artwork/search")
    Observable<HttpResultPic<ArtistPic<PicUrls>>> searchArtistPic(@Header("Cache-Control") String cacheControl,
                                                                  @Query("artist") String artist);
}