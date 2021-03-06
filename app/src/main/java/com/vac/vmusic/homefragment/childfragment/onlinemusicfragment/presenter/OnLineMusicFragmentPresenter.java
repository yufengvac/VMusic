package com.vac.vmusic.homefragment.childfragment.onlinemusicfragment.presenter;

import android.util.Log;

import com.vac.vmusic.beans.discover.DiscoverColumn;
import com.vac.vmusic.beans.discover.DiscoverColumnData;
import com.vac.vmusic.callback.RequestCallback;
import com.vac.vmusic.homefragment.childfragment.localmusicfragment.adapter.ExclusiveListAdapter;
import com.vac.vmusic.homefragment.childfragment.localmusicfragment.adapter.HotSongListAdapter;
import com.vac.vmusic.homefragment.childfragment.onlinemusicfragment.model.OnLineMusicFragmentModel;
import com.vac.vmusic.homefragment.childfragment.onlinemusicfragment.view.IOnLineMusicFragment;
import com.vac.vmusic.utils.RxBus;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by vac on 16/10/22.
 *
 */
public class OnLineMusicFragmentPresenter implements RequestCallback<DiscoverColumn<DiscoverColumnData>>{
    private IOnLineMusicFragment iOnLineMusicFragment;
    private OnLineMusicFragmentModel onLineMusicFragmentModel;
    public OnLineMusicFragmentPresenter(IOnLineMusicFragment iOnLineMusicFragment_){
        this.iOnLineMusicFragment = iOnLineMusicFragment_;
        onLineMusicFragmentModel = new OnLineMusicFragmentModel();
    }
    public void watchTopViewColorByScroll(){
        iOnLineMusicFragment.getColorObservable().subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                iOnLineMusicFragment.getTopView().setBackgroundColor(integer);
            }
        });
    }

    public void getDiscoverData(){
        onLineMusicFragmentModel.getDiscoverData(this);
    }

    public void setHotSongListGridView(List<DiscoverColumnData> hotSongListData){
        HotSongListAdapter hotSongListAdapter = new HotSongListAdapter(iOnLineMusicFragment.getMyContext());
        iOnLineMusicFragment.getHotSongListGridView().setAdapter(hotSongListAdapter);
        hotSongListAdapter.setData(hotSongListData);
    }
    public void setPhoneGridView(List<DiscoverColumnData> hotSongListData){
        HotSongListAdapter hotSongListAdapter = new HotSongListAdapter(iOnLineMusicFragment.getMyContext());
        iOnLineMusicFragment.getPhoneGridView().setAdapter(hotSongListAdapter);
        hotSongListAdapter.setData(hotSongListData);
    }

    public void setNewSongGridView(List<DiscoverColumnData> newSongListData){
        HotSongListAdapter hotSongListAdapter = new HotSongListAdapter(iOnLineMusicFragment.getMyContext());
        iOnLineMusicFragment.getNewSongGridView().setAdapter(hotSongListAdapter);
        hotSongListAdapter.setData(newSongListData);
    }

    public void setHotMvGridView(List<DiscoverColumnData> newSongListData){
        HotSongListAdapter hotSongListAdapter = new HotSongListAdapter(iOnLineMusicFragment.getMyContext());
        iOnLineMusicFragment.getHotMvGridView().setAdapter(hotSongListAdapter);
        hotSongListAdapter.setData(newSongListData);
    }

    public void setExclusiveZoneListView(List<DiscoverColumnData> exclusiveZoneListData){
        ExclusiveListAdapter exclusiveListAdapter = new ExclusiveListAdapter(iOnLineMusicFragment.getMyContext());
        iOnLineMusicFragment.getExclusiveZoneListView().setAdapter(exclusiveListAdapter);
        exclusiveListAdapter.setData(exclusiveZoneListData);
    }

    @Override
    public void beforeRequest() {

    }

    @Override
    public void requestCompleted() {

    }

    @Override
    public void requestSuccess(List<DiscoverColumn<DiscoverColumnData>> data) {
        DiscoverColumn<DiscoverColumnData> bannerData= data.get(0);
        List<DiscoverColumnData> bannerDataList = bannerData.getData();
        if (bannerDataList.size()==1){
            iOnLineMusicFragment.showBanner(bannerDataList.get(0).getPicUrl(),bannerDataList.get(0).getAction().getValue());
        }

        List<DiscoverColumnData> channelDataList = data.get(1).getData();
        iOnLineMusicFragment.showChannel(channelDataList);


        setHotSongListGridView(data.get(3).getData());

        setPhoneGridView(data.get(4).getData());
        iOnLineMusicFragment.showRecommend(data.get(5).getData());
        setNewSongGridView(data.get(6).getData());
        setHotMvGridView(data.get(7).getData());

        setExclusiveZoneListView(data.get(8).getData());
        iOnLineMusicFragment.showEveryOneListener(data.get(2).getData());


    }

    @Override
    public void requestError(String errorMsg) {

    }
}
