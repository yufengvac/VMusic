package com.vac.vmusic.homefragment.childfragment.localmusicfragment.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.vac.vmusic.R;
import com.vac.vmusic.base.BaseSwipeBackFragment;
import com.vac.vmusic.beans.AddFragment;
import com.vac.vmusic.beans.skin.SkinPalette;
import com.vac.vmusic.beans.songlist.SongListDetail;
import com.vac.vmusic.downmusicfragment.view.DownMusicFragment;
import com.vac.vmusic.homefragment.childfragment.localmusicfragment.adapter.MySongListAdapter;
import com.vac.vmusic.homefragment.childfragment.localmusicfragment.presenter.LocalMusicFragmentPresenter;
import com.vac.vmusic.nativemusicfragment.view.NativeMusicFragment;
import com.vac.vmusic.skinfragment.activity.view.SkinActivity;
import com.vac.vmusic.utils.AlphaUtil;
import com.vac.vmusic.utils.DialogUtils;
import com.vac.vmusic.utils.HomeColorManager;
import com.vac.vmusic.utils.PreferHelper;
import com.vac.vmusic.utils.RxBus;
import com.vac.vmusic.views.BorderTextView;
import com.vac.vmusic.views.BounceScrollView;
import com.vac.vmusic.views.ListViewForScrollView;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by vac on 16/10/22.
 *
 */
@SuppressLint("NewApi")
public class LocalMusicFragment extends BaseSwipeBackFragment implements ILocalMusicFragment,View.OnClickListener{

    private BounceScrollView myScrollView;
    private RelativeLayout module_1,module_2,module_3,module_4;
    private LinearLayout favor_linear,song_list_linear,favor_song_list_linear;
    private TextView nativeMusicCountTextView;
    private Subscription skinSubcription;

    private ImageView imageView1,imageView2;
    private Drawable bg1,bg2;

    private BorderTextView createSongListTv,manageSongListTv;
    private LocalMusicFragmentPresenter localMusicFragmentPresenter;

    private ListViewForScrollView songListListView;
    private LinearLayout createSongListLinear;
    private MySongListAdapter mySongListAdapter;
    private TextView songListCountTv;
    public static LocalMusicFragment getLocalMusicFragment(Bundle bundle){
        LocalMusicFragment localMusicFragment = new LocalMusicFragment();
        if (bundle!=null){
            localMusicFragment.setArguments(bundle);
        }
        return localMusicFragment;
    }

    @Override
    public void getFragmentViewId() {
        fragmentViewId = R.layout.local_music_fragment;
    }

    @Override
    public void initView(View view) {
        myScrollView = (BounceScrollView) view.findViewById(R.id.local_music_fra_scrollView);

        module_1 = (RelativeLayout) view.findViewById(R.id.local_music_fra_module_1);
        module_2 = (RelativeLayout) view.findViewById(R.id.local_music_fra_module_2);
        module_3 = (RelativeLayout) view.findViewById(R.id.local_music_fra_module_3);
        module_4 = (RelativeLayout) view.findViewById(R.id.local_music_fra_module_4);
        module_1.setOnClickListener(this);module_2.setOnClickListener(this);
        module_3.setOnClickListener(this);module_4.setOnClickListener(this);

        nativeMusicCountTextView = (TextView) view.findViewById(R.id.local_music_fra_totalnumber);

        imageView1 = (ImageView) view.findViewById(R.id.local_music_bg);
        imageView2 = (ImageView) view.findViewById(R.id.local_music_bg1);
        imageView1.setOnClickListener(this);
        imageView2.setOnClickListener(this);
        bg1 = imageView1.getBackground().mutate();
        bg2 = imageView2.getBackground().mutate();

        favor_linear = (LinearLayout) view.findViewById(R.id.local_music_fra_module_favor);
        song_list_linear = (LinearLayout) view.findViewById(R.id.local_music_fra_crate_songlist);
        favor_song_list_linear = (LinearLayout) view.findViewById(R.id.local_music_fra_module_songlist_favor);

        createSongListTv = (BorderTextView) view.findViewById(R.id.local_music_fra_createlist_textview);
        manageSongListTv = (BorderTextView) view.findViewById(R.id.local_music_fra_managerlist_textview);

        songListListView =(ListViewForScrollView) view.findViewById(R.id.local_music_fra_favor_listview);
        songListCountTv = (TextView) view.findViewById(R.id.local_music_fra_song_list_count_text_view);

        initColor(true);

        createSongListTv.setOnClickListener(this);
        manageSongListTv.setOnClickListener(this);

        createSongListLinear = (LinearLayout) view.findViewById(R.id.local_music_fra_createlist_linearlayour);
        createSongListLinear.setOnClickListener(this);

        localMusicFragmentPresenter = new LocalMusicFragmentPresenter(this);
        localMusicFragmentPresenter.watchMyScrollView();
        localMusicFragmentPresenter.getNativeMusicCount();


        myScrollView.setImageView(imageView2);

        skinSubcription =RxBus.get().register("color",SkinPalette.class).subscribe(new Action1<SkinPalette>() {
            @Override
            public void call(SkinPalette skinPalette) {
                initColor(false);
                changeSkin(skinPalette.getUrl());
            }
        });
    }

    @Override
    public BounceScrollView getMyScrollView() {
        return myScrollView;
    }

    @Override
    public Context getMyContext() {
        return getActivity();
    }

    @Override
    public void initColor(boolean isInit) {
        module_1.setBackgroundColor(HomeColorManager.getHomeColorManager().getCurrentColor());
        module_4.setBackgroundColor(HomeColorManager.getHomeColorManager().getCurrentColor());

        module_2.setBackgroundColor(HomeColorManager.getHomeColorManager().getCurrentLightColor());
        module_3.setBackgroundColor(HomeColorManager.getHomeColorManager().getCurrentLightColor());

        favor_linear.setBackgroundColor(HomeColorManager.getHomeColorManager().getCurrentColor());

        song_list_linear.setBackgroundColor(HomeColorManager.getHomeColorManager().getCurrentColor());
        favor_song_list_linear.setBackgroundColor(HomeColorManager.getHomeColorManager().getCurrentColor());

        createSongListTv.setBorderColor(HomeColorManager.getHomeColorManager().getCurrentColor());
        manageSongListTv.setBorderColor(HomeColorManager.getHomeColorManager().getCurrentColor());

        if (mySongListAdapter!=null){
            mySongListAdapter.notifyDataSetChanged();
        }

        if (isInit&&!PreferHelper.getLastSkinUrl().isEmpty()){
            Glide.with(getActivity()).load(PreferHelper.getLastSkinUrl()).dontAnimate().placeholder(R.drawable.default_bg_big).centerCrop().into(imageView1);
            Glide.with(getActivity()).load(PreferHelper.getLastSkinUrl()).dontAnimate().placeholder(R.drawable.default_bg_big).centerCrop().into(imageView2);
            bg1 = imageView1.getBackground().mutate();
            bg2 = imageView2.getBackground().mutate();
            bg1.setAlpha(255);
            bg2.setAlpha(255);
        }

    }

    @Override
    public void changeSkin(final String url) {
        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                try {
                    Bitmap bitmap = Glide.with(getActivity()).load(url).asBitmap().centerCrop().into(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL).get();
                    final  Drawable drawable = new BitmapDrawable(bitmap);
                    subscriber.onNext(drawable);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Drawable>() {
                    @Override
                    public void call(Drawable drawable) {
                        if (bg2.getAlpha()<20){
                            bg2 = drawable;
                            imageView2.setImageDrawable(bg2);
                            bg1.setAlpha(255);
                            bg2.setAlpha(0);
                            Log.i("LocalMusicFragment","显示bg2,隐藏bg1");
                            AlphaUtil alphaUtil = new AlphaUtil(bg1,bg2);
                            alphaUtil.toExecute();
                        }else {
                            bg1 = drawable;
                            imageView1.setImageDrawable(bg1);
                            bg1.setAlpha(0);
                            bg2.setAlpha(255);
                            Log.i("LocalMusicFragment","======显示bg1,隐藏bg2");
                            AlphaUtil alphaUtil = new AlphaUtil(bg2,bg1);
                            alphaUtil.toExecute();
                        }
                    }
                });
    }

    @Override
    public void showNativeMusicCount(int num) {
        String result = num+"首";
        nativeMusicCountTextView.setText(result);
    }

    @Override
    public void refreshMySonglist(List<SongListDetail> songListDetailList) {
        String songListCountStr ;
        if (songListDetailList==null||songListDetailList.size()==0){
            songListListView.setVisibility(View.GONE);
            createSongListLinear.setVisibility(View.VISIBLE);
            songListCountStr = "0个";
        }else {
            songListListView.setVisibility(View.VISIBLE);
            createSongListLinear.setVisibility(View.GONE);
            if (mySongListAdapter==null){
                mySongListAdapter = new MySongListAdapter(getMyContext());
                songListListView.setAdapter(mySongListAdapter);
            }

            mySongListAdapter.setData(songListDetailList);
            songListCountStr = songListDetailList.size()+"个";
        }
        songListCountTv.setText(songListCountStr);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.local_music_fra_module_1:
                AddFragment addFragment1 = new AddFragment();
                addFragment1.setFromFragment((BaseSwipeBackFragment) getParentFragment());
                addFragment1.setToFragment(NativeMusicFragment.getNativeMusicFragment(null));
                RxBus.get().post("addFragment",addFragment1);
                break;
            case R.id.local_music_fra_module_2:
                AddFragment addFragment2 = new AddFragment();
                addFragment2.setFromFragment((BaseSwipeBackFragment) getParentFragment());
                addFragment2.setToFragment(DownMusicFragment.getDownMusicFragment(null));
                RxBus.get().post("addFragment",addFragment2);
                break;
            case R.id.local_music_fra_module_3:
                break;
            case R.id.local_music_fra_module_4:
                break;
            case R.id.local_music_bg:
            case R.id.local_music_bg1:
                startActivity(new Intent(getActivity(), SkinActivity.class));
                break;
            case R.id.local_music_fra_createlist_textview:
            case R.id.local_music_fra_createlist_linearlayour:
                DialogUtils.showEditTextDialog(getActivity(), "新建歌单", "请输入歌单名字", new DialogUtils.OnDialogBtnClickListener() {
                    @Override
                    public void onBtnOk(String message) {
                        localMusicFragmentPresenter.saveSongList(message);
                    }

                    @Override
                    public void onBtnCancel() {

                    }
                });
                break;
            case R.id.local_music_fra_managerlist_textview:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (skinSubcription!=null){
            skinSubcription.unsubscribe();
            skinSubcription = null;
        }
    }
}
