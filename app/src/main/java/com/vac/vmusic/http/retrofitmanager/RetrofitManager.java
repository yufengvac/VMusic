package com.vac.vmusic.http.retrofitmanager;

import android.support.annotation.NonNull;
import android.util.Log;
import android.util.SparseArray;


import com.vac.vmusic.application.App;
import com.vac.vmusic.beans.detail.HttpAlbumDetail;
import com.vac.vmusic.beans.discover.DiscoverColumn;
import com.vac.vmusic.beans.discover.DiscoverColumnData;
import com.vac.vmusic.beans.discover.HttpData;
import com.vac.vmusic.beans.httpresult.HttpResult;
import com.vac.vmusic.beans.httpresult.HttpResultPic;
import com.vac.vmusic.beans.httpresult.HttpResultPlus;
import com.vac.vmusic.beans.lyric.LyricDataXml;
import com.vac.vmusic.beans.search.TingAlbum;
import com.vac.vmusic.beans.search.TingArtist;
import com.vac.vmusic.beans.search.TingSearchMV;
import com.vac.vmusic.beans.search.TingSong;
import com.vac.vmusic.beans.search.TingSongList;
import com.vac.vmusic.beans.search.artistpic.ArtistPic;
import com.vac.vmusic.beans.search.artistpic.PicUrls;
import com.vac.vmusic.beans.skin.HttpSkin;
import com.vac.vmusic.beans.skin.Skin;
import com.vac.vmusic.beans.songlist.SongListDetail;
import com.vac.vmusic.http.apiconstant.ApiConstants;
import com.vac.vmusic.http.services.ApiService;
import com.vac.vmusic.utils.Constants;
import com.vac.vmusic.utils.NetUtil;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by vac on 16/10/15.
 * RetrofitManager
 */
public class RetrofitManager {

    //设缓存有效期为两天
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;
    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    //查询网络的Cache-Control设置，头部Cache-Control设为max-age=0
    //(假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)时则不会使用缓存而请求服务器
    private static final String CACHE_CONTROL_NETWORK = "max-age=0";

    private static SparseArray<RetrofitManager> sInstanceManager = new SparseArray<>();
    private static HashMap<String,RetrofitManager> managerHashMap = new HashMap<>();

    private static volatile OkHttpClient sOkHttpClient;

    private ApiService mApiService;

    private RetrofitManager(){}
    public static RetrofitManager getInstance(int hostType){
        return getInstance(hostType,null);
    }

    public static RetrofitManager getInstance(int hostType,String dataType){
        RetrofitManager instance = sInstanceManager.get(hostType);
        if (dataType==null){
            if (instance==null){
                instance = new RetrofitManager(hostType);
                sInstanceManager.put(hostType,instance);
            }
        }else {

            RetrofitManager retrofitManager = managerHashMap.get(dataType);

            if (retrofitManager ==null){
                instance = new RetrofitManager(hostType,dataType);
                managerHashMap.put(dataType,instance);
            }else {
                instance = retrofitManager;
            }

        }
        return instance;
    }

    private RetrofitManager(int hostType){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiConstants.getBaseUrl(hostType))
                .client(getOkHttpClient()).addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        mApiService = retrofit.create(ApiService.class);
    }

    private RetrofitManager(int hostType,String tag){
        if (tag.equals("XML")){
            Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiConstants.getBaseUrl(hostType))
                    .client(getOkHttpClient()).addConverterFactory(SimpleXmlConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
            mApiService = retrofit.create(ApiService.class);
        }else if (tag.equals("string")){
            Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiConstants.getBaseUrl(hostType))
                    .client(getOkHttpClient()).addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
            mApiService = retrofit.create(ApiService.class);
        }

    }

    private OkHttpClient getOkHttpClient(){
        if (sOkHttpClient==null){
            synchronized (RetrofitManager.class){
                if (sOkHttpClient==null){
                    /**缓存100M*/
                    Cache cache = new Cache(new File(Constants.ROOT_PATH,Constants.CHILD_HTTP_CACHE)
                    ,1024*1024*100);
                    sOkHttpClient = new OkHttpClient.Builder().cache(cache)
                            .retryOnConnectionFailure(true)
                            .connectTimeout(30, TimeUnit.SECONDS)
                            .addInterceptor(mRewriteCacheControlInterceptor)
                            .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                            .addInterceptor(mLoggingInterceptor)
                            .build();
                }
            }
        }
        return sOkHttpClient;
    }

    /** 云端响应头拦截器，用来配置缓存策略*/
    private Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetUtil.isConnected(App.getContext())){
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }
            Response originalResponse = chain.proceed(request);
            if (NetUtil.isConnected(App.getContext())){
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder().header("Cache-Control",cacheControl).removeHeader("Pragma").build();
            }else {
                return originalResponse.newBuilder().header("Cache-Control","public,only-if-cached,"+CACHE_STALE_SEC)
                        .removeHeader("Pragma").build();
            }
        }
    };

    /**打印返回的json数据拦截器*/
    private Interceptor mLoggingInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            final Request request = chain.request();
            final Response response = chain.proceed(request);

            final ResponseBody responseBody = response.body();
            final long contentLength = responseBody.contentLength();

            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();

            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                try {
                    charset = contentType.charset(charset);
                } catch (UnsupportedCharsetException e) {
                    Log.e("RetrofitManager","Couldn't decode the response body; charset is likely malformed.");
                    return response;
                }
            }

            if (contentLength != 0) {
//                Logger.json(buffer.clone().readString(charset));
                Log.i("RetrofitManager",buffer.clone().readString(charset));
            }

            return response;
        }
    };

    /**
     * 根据网络状况获取缓存的策略
     *
     * @return
     */
    @NonNull
    private String getCacheControl() {
        return NetUtil.isConnected(App.getContext()) ? CACHE_CONTROL_NETWORK : CACHE_CONTROL_CACHE;
    }

    /***
     * 搜索歌曲
     * @param size size
     * @param page page
     * @param q 关键词
     * @return Observable
     */
    public Observable<HttpResult<TingSong>> searchSong(int size, int page, String q){
       return mApiService.searchSong(getCacheControl(),size,page,q).subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread()).unsubscribeOn(Schedulers.io());
    }

    /***
     * 搜索专辑
     * @param size size
     * @param page page
     * @param q 关键词
     * @return Observable
     *
     */
    public Observable<HttpResult<TingAlbum>> searchAlbum(int size, int page, String q){
        return mApiService.searchAlbum(getCacheControl(),size,page,q).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).unsubscribeOn(Schedulers.io());
    }

    /**
     * 搜索歌单
     * @param size size
     * @param page page
     * @param q 关键词
     * @return Observable
     */
    public Observable<HttpResultPlus<TingSongList>> searchSongList(int size, int page, String q){
        return mApiService.searchSongList(getCacheControl(),size,page,q).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).unsubscribeOn(Schedulers.io());
    }

    public Observable<HttpResultPlus<TingSearchMV>> searchMV(int size, int page, String q){
        return mApiService.searchMV(getCacheControl(),size,page,q).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).unsubscribeOn(Schedulers.io());
    }

    /**
     * 搜索歌手
     * @param q 关键词
     * @return Observable
     */
    public Observable<HttpResultPlus<TingArtist>> searchArtist(String q){
        return mApiService.searchArtist(getCacheControl(),1,q).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).unsubscribeOn(Schedulers.io());
    }

    /**
     * 搜索歌手图片
     * @param artist 歌手名字
     * @return Observable
     */
    public Observable<HttpResultPic<ArtistPic<PicUrls>>> searchArtistPics(String artist){
        return mApiService.searchArtistPic(getCacheControl(),artist).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).unsubscribeOn(Schedulers.io());
    }

    /***
     * 搜索歌词id
     * @param songName 歌名
     * @param singerName 歌手名
     * @param songId 歌曲id
     * @param singerId 歌手id
     * @return Observable
     */
    public Observable<LyricDataXml> searchLyricIds(String songName, String singerName, long songId, long singerId){
        return mApiService.searchLyricIds(getCacheControl(),songName,singerName,songId,singerId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).unsubscribeOn(Schedulers.io());
    }

    /**
     * 搜索歌词内容
     * @param lyricId 歌词Id
     * @return Observable
     */
    public Observable<String> searchLyricContent(long lyricId){
        return mApiService.searchLyric(getCacheControl(),lyricId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }

    /**
     * 获取首页的壁纸皮肤
     * @return Observable
     */
    public Observable<HttpSkin<Skin>> getWallPager(){
        return mApiService.getWallpager(getCacheControl()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }

    /**
     * 获取发现页面的所有的数据
     * @return Observable
     */
    public Observable<HttpData<DiscoverColumn<DiscoverColumnData>>> getDiscoverData(){
        return mApiService.getDiscoverData(getCacheControl(),"v8.1.1.2015081020").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }

    /**
     * 获取专辑详情页的数据
     * @param albumId 专辑id
     * @return Observable
     */
    public Observable<HttpAlbumDetail> getAlbumDetailData(long albumId){
        return mApiService.getAlbumDetail(getCacheControl(),albumId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }

    /***
     * 获取歌单详情的数据
     * @param songlistId 歌单id
     * @return Observable
     */
    public Observable<SongListDetail> getSongListDetail(long songlistId){
        return mApiService.getSongListDetail(getCacheControl(),songlistId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }
}
