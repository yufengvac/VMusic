package com.vac.vmusic.http.retrofitmanager;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.SparseArray;


import com.vac.vmusic.application.App;
import com.vac.vmusic.beans.httpresult.HttpResult;
import com.vac.vmusic.beans.httpresult.HttpResultPlus;
import com.vac.vmusic.beans.search.TingAlbum;
import com.vac.vmusic.beans.search.TingSearchMV;
import com.vac.vmusic.beans.search.TingSong;
import com.vac.vmusic.beans.search.TingSongList;
import com.vac.vmusic.http.apiconstant.ApiConstants;
import com.vac.vmusic.http.services.SearchService;
import com.vac.vmusic.utils.NetUtil;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
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

    private static volatile OkHttpClient sOkHttpClient;

    private SearchService mSearchService;

    private RetrofitManager(){}
    public static RetrofitManager getInstance(int hostType){
        RetrofitManager instance = sInstanceManager.get(hostType);
        if (instance==null){
            instance = new RetrofitManager(hostType);
            sInstanceManager.put(hostType,instance);
        }
        return instance;
    }

    private RetrofitManager(int hostType){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiConstants.getBaseUrl(hostType))
                .client(getOkHttpClient()).addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        mSearchService = retrofit.create(SearchService.class);
    }

    private OkHttpClient getOkHttpClient(){
        if (sOkHttpClient==null){
            synchronized (RetrofitManager.class){
                if (sOkHttpClient==null){
                    /**缓存100M*/
                    Cache cache = new Cache(new File(Environment.getExternalStorageDirectory()+"/vmusic/","httpCache")
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
       return mSearchService.searchSong(getCacheControl(),size,page,q).subscribeOn(Schedulers.io())
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
        return mSearchService.searchAlbum(getCacheControl(),size,page,q).subscribeOn(Schedulers.io())
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
        return mSearchService.searchSongList(getCacheControl(),size,page,q).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).unsubscribeOn(Schedulers.io());
    }

    public Observable<HttpResultPlus<TingSearchMV>> searchMV(int size, int page, String q){
        return mSearchService.searchMV(getCacheControl(),size,page,q).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).unsubscribeOn(Schedulers.io());
    }
}
