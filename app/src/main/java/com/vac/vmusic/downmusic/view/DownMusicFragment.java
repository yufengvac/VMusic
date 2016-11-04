package com.vac.vmusic.downmusic.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.vac.vmusic.R;
import com.vac.vmusic.base.BaseSwipeBackFragment;
import com.vac.vmusic.callback.OnItemClickListener;
import com.vac.vmusic.downmusic.presenter.DownMusicFragmentPresenter;
import com.vac.vmusic.utils.HomeColorManager;

/**
 * Created by vac on 16/11/3.
 *
 */
public class DownMusicFragment extends BaseSwipeBackFragment implements IDownMusicFragment,OnItemClickListener,View.OnClickListener{
    private RecyclerView recyclerView;
    private DownMusicFragmentPresenter downMusicFragmentPresenter;
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

        LinearLayout topContent = (LinearLayout) view.findViewById(R.id.down_music_fragment_top_content);
        topContent.setBackgroundColor(new HomeColorManager().getCurrentColor());
        ImageView backImageView = (ImageView) view.findViewById(R.id.down_music_top_back_imageview);
        backImageView.setOnClickListener(this);

        downMusicFragmentPresenter = new DownMusicFragmentPresenter(this);
        downMusicFragmentPresenter.loadData();
    }

    @Override
    public void onItemClick(View view, int position) {
        getMusicIbinder().setMusicPlayList(downMusicFragmentPresenter.getLocalMusic(),true);
        getMusicIbinder().beginToPlay(position,downMusicFragmentPresenter.getLocalMusic().get(position));
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.down_music_top_back_imageview:
                getActivity().onBackPressed();
                break;
        }
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
