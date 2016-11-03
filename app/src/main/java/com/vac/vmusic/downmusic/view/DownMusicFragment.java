package com.vac.vmusic.downmusic.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.vac.vmusic.R;
import com.vac.vmusic.base.BaseSwipeBackFragment;
import com.vac.vmusic.callback.OnItemClickListener;
import com.vac.vmusic.downmusic.presenter.DownMusicFragmentPresenter;

/**
 * Created by vac on 16/11/3.
 *
 */
public class DownMusicFragment extends BaseSwipeBackFragment implements IDownMusicFragment,OnItemClickListener{
    private RecyclerView recyclerView;
    public static DownMusicFragment getDownMusicFragment(Bundle bundle){
        DownMusicFragment downMusicFragment = new DownMusicFragment();
        if (bundle!=null){
            downMusicFragment.setArguments(bundle);
        }
        return downMusicFragment;
    }
    @Override
    public void getFragmentViewId() {
        fragmentViewId = R.layout.down_music_fragment;
    }

    @Override
    public void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.down_music_fragment_recycle_view);
        DownMusicFragmentPresenter downMusicFragmentPresenter = new DownMusicFragmentPresenter(this);
        downMusicFragmentPresenter.loadData();
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public Context getMyContext() {
        return getContext();
    }

    @Override
    public OnItemClickListener getListener() {
        return this;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }
}
