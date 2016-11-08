package com.vac.vmusic.homefragment.childfragment.onlinemusicfragment.view;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vac.vmusic.R;
import com.vac.vmusic.base.BaseSwipeBackFragment;
import com.vac.vmusic.beans.discover.DiscoverColumnData;
import com.vac.vmusic.beans.search.TingSong;
import com.vac.vmusic.homefragment.childfragment.onlinemusicfragment.presenter.OnLineMusicFragmentPresenter;
import com.vac.vmusic.utils.RxBus;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by vac on 16/10/22.
 *
 */
public class OnLineMusicFragment extends BaseSwipeBackFragment implements IOnLineMusicFragment,View.OnClickListener{

    private View topView;
    private Observable<Integer> colorObservable;

    private ImageView bannerImageView;
    private ImageView channelImageView1,channelImageView2,channelImageView3,channelImageView4;
    private TextView channelTextView1,channelTextView2,channelTextView3,channelTextView4;

    private ImageView everyoneListenImageView1,everyoneListenImageView2,everyoneListenImageView3,
    qualityImageView1,qualityImageView2,qualityImageView3;
    private TextView everyoneNameTextView1,everyoneNameTextView2,everyoneNameTextView3,
    everyoneSingerTextView1,everyoneSingerTextView2,everyoneSingerTextView3;

    public static OnLineMusicFragment getOnLineMusicFragment(Bundle bundle){
        OnLineMusicFragment onLineMusicFragment = new OnLineMusicFragment();
        if (bundle!=null){
            onLineMusicFragment.setArguments(bundle);
        }
        return onLineMusicFragment;
    }
    @Override
    public void getFragmentViewId() {
        fragmentViewId = R.layout.online_music_fragment;

    }

    @Override
    public void initView(View view) {
        topView = view.findViewById(R.id.online_music_fragment_top_view);

        bannerImageView = (ImageView)view.findViewById(R.id.online_music_fragment_banner_image_view);
        bannerImageView.setOnClickListener(this);

        channelImageView1 = (ImageView)view.findViewById(R.id.online_music_fragment_channel_logo1);
        channelImageView2 = (ImageView)view.findViewById(R.id.online_music_fragment_channel_logo2);
        channelImageView3 = (ImageView)view.findViewById(R.id.online_music_fragment_channel_logo3);
        channelImageView4 = (ImageView)view.findViewById(R.id.online_music_fragment_channel_logo4);
        channelTextView1 = (TextView) view.findViewById(R.id.online_music_fragment_channel_title1);
        channelTextView2 = (TextView) view.findViewById(R.id.online_music_fragment_channel_title2);
        channelTextView3 = (TextView) view.findViewById(R.id.online_music_fragment_channel_title3);
        channelTextView4 = (TextView) view.findViewById(R.id.online_music_fragment_channel_title4);

        everyoneListenImageView1 = (ImageView) view.findViewById(R.id.online_music_fragment_everyone_listen_logo1);
        everyoneListenImageView2 = (ImageView) view.findViewById(R.id.online_music_fragment_everyone_listen_logo2);
        everyoneListenImageView3 = (ImageView) view.findViewById(R.id.online_music_fragment_everyone_listen_logo3);
        qualityImageView1 = (ImageView) view.findViewById(R.id.online_music_fragment_everyone_listen_quality1);
        qualityImageView2 = (ImageView) view.findViewById(R.id.online_music_fragment_everyone_listen_quality2);
        qualityImageView3 = (ImageView) view.findViewById(R.id.online_music_fragment_everyone_listen_quality3);
        everyoneNameTextView1 = (TextView) view.findViewById(R.id.online_music_fragment_everyone_listen_name1);
        everyoneNameTextView2 = (TextView) view.findViewById(R.id.online_music_fragment_everyone_listen_name2);
        everyoneNameTextView3 = (TextView) view.findViewById(R.id.online_music_fragment_everyone_listen_name3);
        everyoneSingerTextView1 = (TextView) view.findViewById(R.id.online_music_fragment_everyone_listen_singer1);
        everyoneSingerTextView2 = (TextView) view.findViewById(R.id.online_music_fragment_everyone_listen_singer2);
        everyoneSingerTextView3 = (TextView) view.findViewById(R.id.online_music_fragment_everyone_listen_singer3);

        colorObservable = RxBus.get().register("pageOffsetColor",Integer.class);
        OnLineMusicFragmentPresenter onLineMusicFragmentPresenter = new OnLineMusicFragmentPresenter(this);
        onLineMusicFragmentPresenter.watchTopViewColorByScroll();
        onLineMusicFragmentPresenter.getDiscoverData();
    }

    @Override
    public void showBanner(String url, String linkUrl) {
        Glide.with(getActivity()).load(url).centerCrop().into(bannerImageView);
    }

    @Override
    public void showChannel(List<DiscoverColumnData> channelList) {
        if (channelList.size()>=4){
            Glide.with(getActivity()).load(channelList.get(0).getPicUrl()).into(channelImageView1);
            Glide.with(getActivity()).load(channelList.get(1).getPicUrl()).into(channelImageView2);
            Glide.with(getActivity()).load(channelList.get(2).getPicUrl()).into(channelImageView3);
            Glide.with(getActivity()).load(channelList.get(3).getPicUrl()).into(channelImageView4);
            channelTextView1.setText(channelList.get(0).getName());
            channelTextView2.setText(channelList.get(1).getName());
            channelTextView3.setText(channelList.get(2).getName());
            channelTextView4.setText(channelList.get(3).getName());
        }
    }

    @Override
    public void showEveryOneListener(List<DiscoverColumnData> listenerList) {
        List<TingSong> tingSongList = listenerList.get(0).getSongs();
        if (tingSongList.size()>=3){
            Glide.with(getActivity()).load(tingSongList.get(0).getPicUrl()).into(everyoneListenImageView1);
            Glide.with(getActivity()).load(tingSongList.get(1).getPicUrl()).into(everyoneListenImageView2);
            Glide.with(getActivity()).load(tingSongList.get(2).getPicUrl()).into(everyoneListenImageView3);
            everyoneNameTextView1.setText(tingSongList.get(0).getName());
            everyoneNameTextView2.setText(tingSongList.get(1).getName());
            everyoneNameTextView3.setText(tingSongList.get(2).getName());
            everyoneSingerTextView1.setText(tingSongList.get(0).getSingerName());
            everyoneSingerTextView2.setText(tingSongList.get(1).getSingerName());
            everyoneSingerTextView3.setText(tingSongList.get(2).getSingerName());
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.online_music_fragment_banner_image_view:
                break;
        }
    }

    @Override
    public View getTopView() {
        return topView;
    }

    @Override
    public Observable<Integer> getColorObservable() {
        return colorObservable;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("pageOffsetColor",colorObservable);
    }
}
