package com.vac.vmusic.service.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.vac.vmusic.R;
import com.vac.vmusic.application.App;
import com.vac.vmusic.beans.lyric.LyricSentence;
import com.vac.vmusic.beans.search.TingSong;
import com.vac.vmusic.callback.OnPlayMusicStateListener;
import com.vac.vmusic.playmusic.view.PlayMusicActivity;
import com.vac.vmusic.service.binder.MusicBinder;
import com.vac.vmusic.utils.AudioFocusHelper;
import com.vac.vmusic.utils.LyricLoadHelper;
import com.vac.vmusic.utils.PreferHelper;
import com.vac.vmusic.utils.RxBus;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by vac on 16/10/25.
 *
 */
public class PlayService extends Service implements MediaPlayer.OnPreparedListener,MediaPlayer.OnCompletionListener,
        MediaPlayer.OnErrorListener,AudioFocusHelper.MusicFocusable,IService,LyricLoadHelper.LyricListener{
    private static final String TAG = PlayService.class.getSimpleName();

    /**播放状态*/
    public static class PlayState{
        public static final int Stopped =0;
        public static final int Prepraing=1;
        public static final int Playing=2;
        public static final int Paused =3;
    }

    /**各个消息类型*/
    public static final String ACTION_INIT="com.vac.vmusic.service.initservice";
    public static final String ACTION_PLAY ="com.vac.vmusic.service.play";
    public static final String ACTION_PAUSE = "com.vac.vmusic.service.pause";
    public static final String ACTION_STOP ="com.vac.vmusic.service.stop";
    public static final String ACTION_PRIVIOUS= "com.vac.vmusic.service.privious";
    public static final String ACTION_NEXT ="com.vac.vmusic.service.next";

    /**服务中音乐播放的当前状态*/
    private int mState = PlayState.Stopped;

    /**服务中的音乐播放对象*/
    private MediaPlayer mPlayer=null;


    /**通知*/
    private Notification mNotification=null;
    /**通知的管理类*/
    private NotificationManager mNotificationManager=null;
    /**通知的id*/
    private static final int NOTIFICATION_ID =1000;

    /**当前的播放模式*/
    private int mPlayMode =1;

    public static class PlayMode{
        /**单曲循环*/
        public static final int REPEAT_SINGLE=0;

        /**列表循环*/
        public static final int REPEAT =1;

        /**顺序播放*/
        public static final int SEQUENTIAL = 2;

        /**随机播放*/
        public static final int SHUFFLE = 3;
    }

    /**监听器的集合*/
    private List<OnPlayMusicStateListener> onPlayMusicStateListenerList = new ArrayList<>();

    /***歌词监听器的集合*/
    private List<LyricLoadHelper.LyricListener> onLyricLoadHelper = new ArrayList<>();

    /**绑定Service对外提供的Binder类*/
    private MusicBinder musicBinder ;

    /**当前正在播放的音乐*/
    private TingSong currentTingSong;


    /**当前的播放队列*/
    private List<TingSong> mPlayingMusicList = new ArrayList<>();


    /**服务中正在播放的音乐的位置*/
    private int mPlayingMusicPosition = -1;

    /**用户请求的播放的位置**/
    private int mRequestMusicPosition = -1;

    /**请求播放的音乐的id*/
    private long mRequestPlayMusicId = -1L;

    /**音频焦点辅助类*/
    private AudioFocusHelper mAudioFocusHelper =null;

    /** 丢失音频焦点，我们为媒体播放设置一个低音量(1.0f为最大)，而不是停止播放*/
    private static final float DUCK_VOLUME_LOW = 0.1f;
    private static final float DUCK_VOLUME_HIGH= 1.0f;

    /**播放进度*/
    private Subscription processSubscription;

    /**歌词辅助类*/
    LyricLoadHelper lyricLoadHelper;

    /**当前的歌是否有歌词*/
    private boolean hasLyric = false;

    /**歌词的集合*/
    private List<LyricSentence> lyricSentenceList;

    /**是否来自于网络*/
    private boolean isFromNet = false;
    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationManager = (NotificationManager) App.getContext().getSystemService(NOTIFICATION_SERVICE);
        mAudioFocusHelper = new AudioFocusHelper(getApplicationContext(),this);
        musicBinder = new MusicBinder(this);
        lyricLoadHelper = new LyricLoadHelper();
        lyricLoadHelper.setLyricListener(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }



    /**
     * 请求一个播放音乐的动作
     * 需要 mRequestMusicPosition  和 mRequestPlayMusicId
     *
     */
    @Override
    public void requestToPlay(){
        Log.d(TAG, "这是请求播放动作时的音乐播放状态-------="+mState);
        if(mState== PlayState.Stopped){//如果播放器处于停止状态
            mPlayingMusicPosition = mRequestMusicPosition;
            playSong();//开始播放音乐
            Log.d(TAG, "播放器处于停止状态,开始播放音乐");
        }else if(mState== PlayState.Paused){//如果播放器处于暂停状态
            if(mPlayingMusicPosition==mRequestMusicPosition){//用户请求的播放歌曲 和 当前播放歌曲相同
                //继续播放音乐吧，由暂停状态转成播放状态
                mState = PlayState.Playing;
                setServiceAsForeground(currentTingSong.getName() + "-"+currentTingSong.getSingerName());
                configAndStartMediaPlayer();
                Log.d(TAG, "播放器处于暂停状态,播放歌曲 和 当前播放歌曲相同,继续播放音乐吧，由暂停状态转成播放状态");
            }else if(mRequestPlayMusicId != currentTingSong.getSongId()){//用户请求的播放歌曲 和 当前播放歌曲 不 相同
                mPlayingMusicPosition = mRequestMusicPosition;//播放用户请求的歌曲
                playSong();//开始播放音乐
                Log.d(TAG, "播放器处于暂停状态,播放歌曲 和 当前播放歌曲  不  相同,播放用户请求的歌曲");
            }
        }else if(mState== PlayState.Playing){//如果播放器处于播放状态
            if(mRequestPlayMusicId != currentTingSong.getSongId()){//用户请求的播放歌曲 和 当前播放歌曲 不 相同
                mPlayingMusicPosition = mRequestMusicPosition;//播放用户请求的歌曲
                playSong();//开始播放音乐
                Log.d(TAG, "播放器处于播放状态,播放歌曲 和 当前播放歌曲不 相同,播放用户请求的歌曲");
            }else {
                if (mState == PlayMode.REPEAT_SINGLE){
                    Log.i(TAG,"单曲循环");
                    playSong();
                }else {
                    //暂停播放音乐吧，由播放状态转成暂停状态
                    requestToPause();
                    Log.d(TAG, "播放器处于播放状态,播放歌曲 和 当前播放歌曲相同,暂停播放音乐吧，由播放状态转成暂停状态");
                    return;
                }

            }

//            if(mPlayingMusicPosition==mRequestMusicPosition){//用户请求的播放歌曲 和 当前播放歌曲相同
//                //暂停播放音乐吧，由播放状态转成暂停状态
//                requestToPause();
//                Log.d(TAG, "播放器处于播放状态,播放歌曲 和 当前播放歌曲相同,暂停播放音乐吧，由播放状态转成暂停状态");
//                return;
//            }else if(mRequestPlayMusicId != currentTingSong.getSongId()){//用户请求的播放歌曲 和 当前播放歌曲 不 相同
//                mPlayingMusicPosition = mRequestMusicPosition;//播放用户请求的歌曲
//                playSong();//开始播放音乐
//                Log.d(TAG, "播放器处于播放状态,播放歌曲 和 当前播放歌曲不 相同,播放用户请求的歌曲");
//            }
        }

        /**通知回调接口，开始播放*/
        for(int i=0;i<onPlayMusicStateListenerList.size();i++){
            onPlayMusicStateListenerList.get(i).onMusicPlayed(currentTingSong);
        }

//        if (!mServiceHandler.hasMessages(MESSAGE_UPDATE_PLAYING_SONG_PROGRESS)) {
//            mServiceHandler
//                    .sendEmptyMessage(MESSAGE_UPDATE_PLAYING_SONG_PROGRESS);
//        }
    }

    /**
     *
     * 将本服务设置为“前台服务”。“前台服务”是一个与用户正在交互的服务， 必须在通知栏显示一个通知表示正在交互
     */
    @Override
    public void setServiceAsForeground(String text){
        Intent intent = new Intent(getApplicationContext(),PlayMusicActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle(currentTingSong.getName());
        builder.setContentText(currentTingSong.getSingerName());
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setTicker(text);
        builder.setContentIntent(pIntent);
        mNotification = builder.build();
        startForeground(NOTIFICATION_ID, mNotification);
    }

    /**
     *播放在播放列表mCurrentPlayList中mPlayingMusicPosition位置 的音乐
     */
    @Override
    public void playSong(){
        currentTingSong = mPlayingMusicList.get(mPlayingMusicPosition);
        mState = PlayState.Stopped;
        if (processSubscription!=null&&(!processSubscription.isUnsubscribed())){
            processSubscription.unsubscribe();
        }

        releaseResource(false);
        createMediaPlayerIfNeed();

        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
                if (currentTingSong.getAuditionList()!=null&&currentTingSong.getAuditionList().size()>0){
                    mPlayer.setDataSource(currentTingSong.getAuditionList().
                            get(currentTingSong.getAuditionList().size()-1).getUrl());
                }else {
                    onError(mPlayer,mPlayingMusicPosition,mRequestMusicPosition);
                    Log.e(TAG,"发生了Error-"+mPlayingMusicList.get(mPlayingMusicPosition).getName()+","
                    +mPlayingMusicList.get(mPlayingMusicPosition).getSingerName()+","+mPlayingMusicPosition);
                    return;
                }
            mState = PlayState.Prepraing;


            setServiceAsForeground(currentTingSong.getName());

            mPlayer.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "play song Exception"+e.getMessage());
        }

        for(int i =0;i<onPlayMusicStateListenerList.size();i++){
            onPlayMusicStateListenerList.get(i).onNewSongPlayed(currentTingSong,mPlayingMusicPosition);
        }
        PreferHelper.saveLastPlayingPosition(mPlayingMusicPosition);
    }

    /**
     * 请求一个暂停的动作
     */
    @Override
    public void requestToPause(){
        Log.d(TAG, "音乐暂停---requestToPause,mState="+mState);
        if(mState== PlayState.Playing){
            mState = PlayState.Paused;
            mPlayer.pause();
            releaseResource(false);//MediaPlayer对象不释放
        }

        for(int i =0;i<onPlayMusicStateListenerList.size();i++){
            onPlayMusicStateListenerList.get(i).onMusicPaused(currentTingSong);
        }
    }

    @Override
    public void requestTogglePlay(){
        if (mState== PlayState.Playing){
            requestToPause();
        }else if(mState== PlayState.Paused){
            configAndStartMediaPlayer();
            for (int i=0;i<onPlayMusicStateListenerList.size();i++){
                onPlayMusicStateListenerList.get(i).onMusicPlayed(currentTingSong);
            }
        }else {
            if (mPlayingMusicList==null||mPlayingMusicList.size()==0){
                Toast.makeText(this,"播放列表为空",Toast.LENGTH_SHORT).show();
            }else {
                requestToPlay();
            }
        }
    }

    /**
     * 请求播放下一首的动作
     *
     * @param isUserClick
     * 					true 是用户手动点击播放下一首
     * 					false 当歌曲播放完成后，自动播放下一首
     */
    @Override
    public void requestToPlayNext(boolean isUserClick){

        if(mState!= PlayState.Prepraing&&mPlayingMusicList.size()>0){

            switch (mPlayMode) {
                case PlayMode.REPEAT:
                    mRequestMusicPosition =(mPlayingMusicPosition+1)%mPlayingMusicList.size();
                    break;
                case PlayMode.REPEAT_SINGLE:
                    if(isUserClick){
                        mRequestMusicPosition = (mPlayingMusicPosition + 1) % mPlayingMusicList.size();
                    }else{
                        mPlayer.setLooping(true);
                        Log.i(TAG,"单曲循环,继续播放改曲");
                    }
                    break;
                case PlayMode.SEQUENTIAL:
                    mRequestMusicPosition = (mPlayingMusicPosition+1)%mPlayingMusicList.size();
                    if(mRequestMusicPosition==0){
                        if(isUserClick){
                            mRequestMusicPosition=0;
                        }else{
                            mRequestMusicPosition = mPlayingMusicList.size()-1;
                            requestToStop();
                            return;
                        }
                    }
                    break;
                case PlayMode.SHUFFLE:
                    mRequestMusicPosition = new Random().nextInt(mPlayingMusicList.size());
                    break;

                default:
                    break;
            }
            mRequestPlayMusicId = mPlayingMusicList.get(mRequestMusicPosition).getSongId();
            requestToPlay();
        }else {
            releaseResource(false);
            createMediaPlayerIfNeed();
            mState = PlayState.Stopped;
            requestToPlayNext(false);
        }

    }

    /**
     * 请求播放上一首的动作（暂不考虑 播放模式的问题）
     * @param isUserClick
     * 					true 是用户手动点击播放上一首
     * 					false 当歌曲播放完成后，自动播放上一首
     */
    @Override
    public void requestToPlayPrevious(boolean isUserClick){
        if(mState!= PlayState.Prepraing&&mPlayingMusicList.size()>0){
            if(--mRequestMusicPosition<0){
                mRequestMusicPosition = mPlayingMusicList.size()-1;//如果是第一首歌曲，那么将从最后一首歌曲开始播放
            }
            mRequestPlayMusicId = mPlayingMusicList.get(mRequestMusicPosition).getSongId();
            requestToPlay();
        }
    }
    /**
     * 请求一个播放停止的动作
     */
    @Override
    public void requestToStop(){
        requestToStop(false);
    }


    /**
     * 请求一个播放停止的动作
     * @param isFource 强制停止
     */
    @Override
    public void requestToStop(boolean isFource){
        if(mState == PlayState.Playing||mState == PlayState.Prepraing||isFource){
            mState = PlayState.Stopped;
            mRequestMusicPosition = 0;
            mPlayingMusicPosition =0;
            mRequestPlayMusicId = -1;
            releaseResource(true);//释放掉MediaPlayer对象
        }

        // 通知所有的观察者音乐停止播放了
        for (int i = 0; i < onPlayMusicStateListenerList.size(); i++) {
            onPlayMusicStateListenerList.get(i).onMusicStopped();
        }
    }

    /**
     * 释放所有的资源（MediaPlayer由isReleaseMediaPlayer决定是否释放）
     * @param isReleaseMediaPlayer 是否释放MediaPlayer对象
     */
    @Override
    public void releaseResource(boolean isReleaseMediaPlayer){
        stopForeground(true);
        if(mPlayer!=null&&isReleaseMediaPlayer){
            mPlayer.reset();
            mPlayer.release();
            mPlayer = null;
        }
    }
    @Override
    public void createMediaPlayerIfNeed(){
        if(mPlayer==null){
            mPlayer = new MediaPlayer();
            mPlayer.setOnPreparedListener(this);
            mPlayer.setOnCompletionListener(this);
            mPlayer.setOnErrorListener(this);
        }else{
            mPlayer.reset();
            hasLyric = false;
        }
    }

    @Override
    public void configAndStartMediaPlayer(){
        Log.i(TAG, "configAndStartMediaPlayer");
//        if(mAudioFocusHelper.getAudioFocus()==AudioFocusHelper.NoFocusNoDuck){//失去音频焦点且找不回
//            //如果丢失了音频焦点也不允许低声播放，我们必须让播放暂停，即使mState处于State.Playing状态。
//            // 但是我们并不修改mState的状态，因为我们会在获得音频焦点时返回立即返回播放状态。
//            if(mPlayer.isPlaying()){
//                mPlayer.pause();
//            }
//            return;
//        }else if(mAudioFocusHelper.getAudioFocus()==AudioFocusHelper.NoFocusCanDuck){//失去了音频焦点，但是能马上找回来
//            mPlayer.setVolume(DUCK_VOLUME_LOW, DUCK_VOLUME_LOW);// 设置一个低音量播放
//        }else{
//            mPlayer.setVolume(DUCK_VOLUME_HIGH, DUCK_VOLUME_HIGH);//设置最大音量播放
//        }

        if(!mPlayer.isPlaying()){
            Log.i(TAG, "start");
            mState = PlayState.Playing;
            mPlayer.start();
            updatePlayProcess();
        }

    }


    @Override
    public boolean isNeedUpdate(List<TingSong> tingSongList){
        boolean isNeed = false;
        if (tingSongList.size()==mPlayingMusicList.size()){
            for (int i=0;i<tingSongList.size();i++){
                if (tingSongList.get(i).getSongId()!=mPlayingMusicList.get(i).getSongId()){
                    isNeed = true;
                    break;
                }
            }
        }else {
            isNeed = true;
        }
        return isNeed;
    }


    /***
     * 更新通知栏
     * @param text
     */
    @Override
    public void updateNotification(String text){
        PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(),
                0, new Intent(getApplicationContext(),PlayMusicActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        mNotificationManager.notify(NOTIFICATION_ID,mNotification );
    }

    @Override
    public void updatePlayProcess(){
        if (processSubscription!=null){
            processSubscription.unsubscribe();
            processSubscription =null;
        }
        processSubscription = Observable.interval(0,1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        int progress = (int)(mPlayer.getCurrentPosition()*1.0/currentTingSong.getAuditionList().get(0).getDuration()*300);
                        for (int i=0;i<onPlayMusicStateListenerList.size();i++){
                            onPlayMusicStateListenerList.get(i).onPlayProgressUpdate(progress,mPlayer.getCurrentPosition());
                        }
                        if (hasLyric){
                            lyricLoadHelper.notifyTime(mPlayer.getCurrentPosition());
                        }
                    }
                });
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return musicBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        Log.i(TAG, "onPrepared");
        mState = PlayState.Playing;
        updateNotification(currentTingSong.getName() + "-"+currentTingSong.getSingerName());
        configAndStartMediaPlayer();

    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        requestToPlayNext(false);
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        Log.e(TAG,"回调了onError方法->i="+i+",i1="+i1);
        requestToPlayNext(false);
        return true;
    }

    @Override
    public void onGainedAudioFocus() {
        Log.i(TAG, "onGainedAudioFocus");
        if(mState == PlayState.Playing){
            configAndStartMediaPlayer();
        }
    }

    @Override
    public void onLostAudioFocus() {
        Log.i(TAG, "onLostAudioFocus");
        if(mPlayer!=null&&mPlayer.isPlaying()){
            configAndStartMediaPlayer();
        }
    }

    @Override
    public List<OnPlayMusicStateListener> getOnPlayMusicStateListener() {
        return onPlayMusicStateListenerList;
    }

    @Override
    public void setRequestMusicPosition(int position) {
        mRequestMusicPosition = position;
        if (mState==PlayState.Stopped){
            currentTingSong = mPlayingMusicList.get(mRequestMusicPosition);
        }
    }

    @Override
    public void setRequestPlayMusicId(long id) {
        mRequestPlayMusicId = id;
    }

    @Override
    public int getPlayerState() {
        return mState;
    }

    @Override
    public MediaPlayer getPlayer() {
        return mPlayer;
    }

    @Override
    public List<TingSong> getPlayingMusicList() {
        return mPlayingMusicList;
    }

    @Override
    public int getPlayerMode() {
        return mPlayMode;
    }

    @Override
    public void setPlayerMode(int playerMode) {
        mPlayMode = playerMode;
    }

    @Override
    public int getPlayingMusicPosition() {
        return mPlayingMusicPosition;
    }

    @Override
    public TingSong getCurrentSong() {
        return currentTingSong;
    }

    @Override
    public List<LyricLoadHelper.LyricListener> getOnLyricListener() {
        return onLyricLoadHelper;
    }

    @Override
    public void setLyricContent(String lyric) {
        if (lyric!=null){
            lyricLoadHelper.loadLyricFromString(lyric);
        }else {
            hasLyric =false;
        }
    }


    @Override
    public void onLyricLoaded(List<LyricSentence> lyricSentences, int indexOfCurSentence) {
        hasLyric = true;
        lyricSentenceList = lyricSentences;
        RxBus.get().post("lyricLoaded",true);
    }

    @Override
    public void onLyricSentenceChanged(int indexOfCurSentence) {
        RxBus.get().post("index",indexOfCurSentence);
    }

    @Override
    public List<LyricSentence> getLyricSentenceList() {
        return lyricSentenceList;
    }

    @Override
    public TingSong getTingSongById(long songId) {
        for (int i=0;i<mPlayingMusicList.size();i++){
            if (songId==mPlayingMusicList.get(i).getSongId()){
                return mPlayingMusicList.get(i);
            }
        }
        return null;
    }

    @Override
    public void setPlayingMusicList(List<? extends TingSong> playingMusicList) {

    }
}
