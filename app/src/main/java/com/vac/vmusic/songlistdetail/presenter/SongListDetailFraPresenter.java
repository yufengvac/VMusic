package com.vac.vmusic.songlistdetail.presenter;

import android.content.Context;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vac.vmusic.R;
import com.vac.vmusic.base.BaseSwipeBackFragment;
import com.vac.vmusic.beans.detail.AlbumDetail;
import com.vac.vmusic.beans.search.TingAlbum;
import com.vac.vmusic.callback.RequestCallback;
import com.vac.vmusic.homefragment.adapters.MyFragmentAdapter;
import com.vac.vmusic.songlistdetail.childfragment.albumInfofragment.view.AlbumInfoFragment;
import com.vac.vmusic.songlistdetail.childfragment.albumsongsfragment.view.AlbumSongsFragment;
import com.vac.vmusic.songlistdetail.model.SongListDetailFraModel;
import com.vac.vmusic.songlistdetail.view.ISongListDetailFragment;
import com.vac.vmusic.utils.FileUtil;
import com.vac.vmusic.utils.HomeColorManager;
import com.vac.vmusic.utils.ViewUtil;
import com.vac.vmusic.views.ChildViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vac on 16/11/12.
 *
 */
public class SongListDetailFraPresenter implements RequestCallback<AlbumDetail>,View.OnClickListener{
    private ISongListDetailFragment iSongListDetailFragment;
    private List<BaseSwipeBackFragment> swipeBackFragmentList;
    private MyFragmentAdapter myFragmentAdapter;
    private boolean isLoadCompleted =false;
    private AlbumSongsFragment albumSongsFragment;
    private AlbumInfoFragment albumInfoFragment;
    private ImageView indicator;
    private Context context;
    public SongListDetailFraPresenter(ISongListDetailFragment iSongListDetailFragment_){
        this.iSongListDetailFragment = iSongListDetailFragment_;
    }
    public void loadSongListDetailData(long albumId){
        SongListDetailFraModel songListDetailFraModel = new SongListDetailFraModel();
        songListDetailFraModel.loadDetailDate(albumId,this);
    }

    public void initTabLayout(TabLayout tabLayout, ViewPager childViewPager){
        String[] titles = new String[]{"单曲","详情"};
        myFragmentAdapter = new MyFragmentAdapter(iSongListDetailFragment.getFm(),titles);
        swipeBackFragmentList = new ArrayList<>();
        myFragmentAdapter.setData(swipeBackFragmentList);
        childViewPager.setAdapter(myFragmentAdapter);
        tabLayout.setupWithViewPager(childViewPager);
    }
    public void initChildFragment(Context context_, TextView tab1, TextView tab2, ImageView indicator_){
        indicator = indicator_;
        context = context_;
//        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) indicator.getLayoutParams();
//        layoutParams.width = ViewUtil.dpToPx(context_,100);
//        indicator.setLayoutParams(layoutParams);
        indicator.setBackgroundColor(HomeColorManager.getHomeColorManager().getCurrentColor());
        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (!isLoadCompleted){
            return;
        }
        int id = view.getId();
        int eventOffset = context.getResources().getDisplayMetrics().widthPixels/2;
        switch (id){
            case R.id.song_list_detail_fragment_tab_1:
                if (!albumSongsFragment.isHidden()){
                    return;
                }
                iSongListDetailFragment.getFm().beginTransaction().hide(albumInfoFragment).commit();
                iSongListDetailFragment.getFm().beginTransaction().show(albumSongsFragment).commit();


                Animation animation1 = new TranslateAnimation(0, -eventOffset+ ViewUtil.dpToPx(context,45), 0, 0);
                animation1.setFillAfter(true);// True:图片停在动画结束位置
                animation1.setDuration(300);
                indicator.startAnimation(animation1);

                break;
            case R.id.song_list_detail_fragment_tab_2:
                if (!albumInfoFragment.isHidden()){
                    return;
                }
                iSongListDetailFragment.getFm().beginTransaction().hide(albumSongsFragment).commit();
                iSongListDetailFragment.getFm().beginTransaction().show(albumInfoFragment).commit();

                LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) indicator.getLayoutParams();
                layoutParams2.leftMargin = context.getResources().getDisplayMetrics().widthPixels/2;
                indicator.setLayoutParams(layoutParams2);


                Animation animation2 = new TranslateAnimation(-eventOffset+ ViewUtil.dpToPx(context,45), ViewUtil.dpToPx(context,45), 0, 0);
                animation2.setFillAfter(true);// True:图片停在动画结束位置
                animation2.setDuration(300);
                indicator.startAnimation(animation2);
                break;
        }
    }

    @Override
    public void beforeRequest() {

    }

    @Override
    public void requestCompleted() {

    }

    @Override
    public void requestSuccess(List<AlbumDetail> data) {
        AlbumDetail albumDetail = data.get(0);
        iSongListDetailFragment.showInfo(albumDetail);
        isLoadCompleted  =true;
        Bundle bundle = new Bundle();
        bundle.putParcelable("albumDetail",albumDetail);
        albumSongsFragment = AlbumSongsFragment.getAlbumSongsFragment(bundle);
        albumInfoFragment = AlbumInfoFragment.getAlbumInfoFragment(bundle);
        if (!albumSongsFragment.isAdded()){
            iSongListDetailFragment.getFm().beginTransaction().add(R.id.song_list_detail_fragment_content,albumSongsFragment).commit();
        }
        if (!albumInfoFragment.isAdded()){
            iSongListDetailFragment.getFm().beginTransaction().add(R.id.song_list_detail_fragment_content,albumInfoFragment).commit();
            iSongListDetailFragment.getFm().beginTransaction().hide(albumInfoFragment).commit();
        }
    }

    @Override
    public void requestError(String errorMsg) {

    }
}
