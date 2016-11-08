package com.vac.vmusic.homefragment.childfragment.onlinemusicfragment.presenter;

import android.util.Log;

import com.vac.vmusic.beans.discover.DiscoverColumn;
import com.vac.vmusic.beans.discover.DiscoverColumnData;
import com.vac.vmusic.callback.RequestCallback;
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

        iOnLineMusicFragment.showEveryOneListener(data.get(2).getData());
    }

    @Override
    public void requestError(String errorMsg) {

    }
}
