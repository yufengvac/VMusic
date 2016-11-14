package com.vac.vmusic.songlistdetail.view;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.vac.vmusic.R;
import com.vac.vmusic.base.BaseSwipeBackFragment;
import com.vac.vmusic.beans.detail.AlbumDetail;
import com.vac.vmusic.songlistdetail.presenter.SongListDetailFraPresenter;
import com.vac.vmusic.utils.GlideCircleTransform;
import com.vac.vmusic.utils.HomeColorManager;
import com.vac.vmusic.views.MyScrollView;

/**
 * Created by vac on 16/11/12.
 *
 */
public class SongListDetailFragment extends BaseSwipeBackFragment implements ISongListDetailFragment,View.OnClickListener{

    private TextView titleTextView,singerNameTextView,publishDateTextView;
    private ImageView logoImageView,singerLogoImageView;
    private LinearLayout topContent;
    public static SongListDetailFragment getSongListDetailFragment(Bundle bundle){
        SongListDetailFragment songListDetailFragment = new SongListDetailFragment();
        if (bundle!=null){
            songListDetailFragment.setArguments(bundle);
        }
        return songListDetailFragment;
    }
    @Override
    public void getFragmentViewId() {
        fragmentViewId = R.layout.songlist_detail_fragment;
    }

    @Override
    public void initView(View view) {
        long albumId = getArguments().getLong("albumId");
        titleTextView = (TextView) view.findViewById(R.id.song_list_detail_fragment_title_text_view);
        ImageView backImageView = (ImageView) view.findViewById(R.id.song_list_detail_fragment_back_image_view);
        backImageView.setOnClickListener(this);

        logoImageView = (ImageView) view.findViewById(R.id.song_list_detail_fragment_logo_image_view);
        singerLogoImageView = (ImageView) view.findViewById(R.id.song_list_detail_fragment_singer_logo);
        singerNameTextView = (TextView) view.findViewById(R.id.song_list_detail_fragment_singer_name);
        publishDateTextView = (TextView)view.findViewById(R.id.song_list_detail_fragment_publish_date_text_view);

        SongListDetailFraPresenter songListDetailFraPresenter = new SongListDetailFraPresenter(this);

        TextView tab1 = (TextView)view.findViewById(R.id.song_list_detail_fragment_tab_1);
        TextView tab2 = (TextView)view.findViewById(R.id.song_list_detail_fragment_tab_2);
        ImageView indicator = (ImageView)view.findViewById(R.id.song_list_detail_fragment_indicator);
        songListDetailFraPresenter.initChildFragment(getActivity(),tab1,tab2,indicator);

        topContent = (LinearLayout) view.findViewById(R.id.song_list_detail_fragment_top_content);

        songListDetailFraPresenter.loadSongListDetailData(albumId);
        MyScrollView myScrollView = (MyScrollView) view.findViewById(R.id.mScroller);
        myScrollView.setOnScrollListener(new MyScrollView.OnScrollListener() {
            @Override
            public void onScroll(int scrollY) {
                topContent.setBackgroundColor(HomeColorManager.getHomeColorManager().transferColorByScroll(scrollY));
            }
        });
    }

    @Override
    public void showInfo(AlbumDetail albumDetail) {
        titleTextView.setText(albumDetail.getName());
        Glide.with(getActivity()).load(albumDetail.getPicUrl()).diskCacheStrategy(DiskCacheStrategy.ALL)
               .into(logoImageView);
        Glide.with(getActivity()).load(albumDetail.getSingerPicUrl()).placeholder(R.drawable.default_bg).diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new GlideCircleTransform(getActivity())).into(singerLogoImageView);
        singerNameTextView.setText(albumDetail.getSingerName());
        String publishDateInfo = albumDetail.getPublishDate()+"发行";
        publishDateTextView.setText(publishDateInfo);
    }

    @Override
    public FragmentManager getFm() {
        return getChildFragmentManager();
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.song_list_detail_fragment_back_image_view:
                getActivity().onBackPressed();
                break;
        }
    }
}
