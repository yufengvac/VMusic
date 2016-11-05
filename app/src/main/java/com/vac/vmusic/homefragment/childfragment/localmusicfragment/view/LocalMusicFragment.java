package com.vac.vmusic.homefragment.childfragment.localmusicfragment.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vac.vmusic.R;
import com.vac.vmusic.base.BaseSwipeBackFragment;
import com.vac.vmusic.beans.AddFragment;
import com.vac.vmusic.downmusicfragment.view.DownMusicFragment;
import com.vac.vmusic.homefragment.childfragment.localmusicfragment.presenter.LocalMusicFragmentPresenter;
import com.vac.vmusic.nativemusicfragment.view.NativeMusicFragment;
import com.vac.vmusic.utils.RxBus;
import com.vac.vmusic.views.MyScrollView;

/**
 * Created by vac on 16/10/22.
 *
 */
public class LocalMusicFragment extends BaseSwipeBackFragment implements ILocalMusicFragment,View.OnClickListener{

    private MyScrollView myScrollView;
    private RelativeLayout module_1,module_2,module_3,module_4;
    private TextView nativeMusicCountTextView;
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
        myScrollView = (MyScrollView) view.findViewById(R.id.local_music_fra_scrollView);

        module_1 = (RelativeLayout) view.findViewById(R.id.local_music_fra_module_1);
        module_2 = (RelativeLayout) view.findViewById(R.id.local_music_fra_module_2);
        module_3 = (RelativeLayout) view.findViewById(R.id.local_music_fra_module_3);
        module_4 = (RelativeLayout) view.findViewById(R.id.local_music_fra_module_4);
        module_1.setOnClickListener(this);module_2.setOnClickListener(this);
        module_3.setOnClickListener(this);module_4.setOnClickListener(this);

        nativeMusicCountTextView = (TextView) view.findViewById(R.id.local_music_fra_totalnumber);

        LocalMusicFragmentPresenter localMusicFragmentPresenter = new LocalMusicFragmentPresenter(this);
        localMusicFragmentPresenter.watchMyScrollView();
        localMusicFragmentPresenter.getNativeMusicCount();
    }

    @Override
    public MyScrollView getMyScrollView() {
        return myScrollView;
    }

    @Override
    public Context getMyContext() {
        return getActivity();
    }

    @Override
    public void showNativeMusicCount(int num) {
        String result = num+"首";
        nativeMusicCountTextView.setText(result);
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
        }
    }
}
