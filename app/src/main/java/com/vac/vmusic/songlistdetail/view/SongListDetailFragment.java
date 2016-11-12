package com.vac.vmusic.songlistdetail.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.vac.vmusic.R;
import com.vac.vmusic.base.BaseSwipeBackFragment;
import com.vac.vmusic.beans.detail.AlbumDetail;
import com.vac.vmusic.songlistdetail.presenter.SongListDetailFraPresenter;
import com.vac.vmusic.utils.GlideCircleTransform;
import com.vac.vmusic.views.ChildViewPager;

/**
 * Created by vac on 16/11/12.
 *
 */
public class SongListDetailFragment extends BaseSwipeBackFragment implements ISongListDetailFragment,View.OnClickListener{

    private TextView titleTextView,singerNameTextView;
    private ImageView logoImageView,singerLogoImageView;
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

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.song_list_detail_fragment_tab_layout);
        ChildViewPager childViewPager = (ChildViewPager) view.findViewById(R.id.song_list_detail_fragment_view_pager);

        SongListDetailFraPresenter songListDetailFraPresenter = new SongListDetailFraPresenter(this);
        songListDetailFraPresenter.initTabLayout(tabLayout,childViewPager);
        songListDetailFraPresenter.loadSongListDetailData(albumId);

    }

    @Override
    public void showInfo(AlbumDetail albumDetail) {
        titleTextView.setText(albumDetail.getName());
        Glide.with(getActivity()).load(albumDetail.getPicUrl()).diskCacheStrategy(DiskCacheStrategy.ALL)
               .into(logoImageView);
        Glide.with(getActivity()).load(albumDetail.getSingerPicUrl()).placeholder(R.drawable.default_bg).diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new GlideCircleTransform(getActivity())).into(singerLogoImageView);
        singerNameTextView.setText(albumDetail.getSingerName());
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
