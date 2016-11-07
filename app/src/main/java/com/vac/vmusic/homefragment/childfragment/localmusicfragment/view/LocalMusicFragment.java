package com.vac.vmusic.homefragment.childfragment.localmusicfragment.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vac.vmusic.R;
import com.vac.vmusic.base.BaseSwipeBackFragment;
import com.vac.vmusic.beans.AddFragment;
import com.vac.vmusic.beans.skin.Skin;
import com.vac.vmusic.beans.skin.SkinPalette;
import com.vac.vmusic.downmusicfragment.view.DownMusicFragment;
import com.vac.vmusic.homefragment.childfragment.localmusicfragment.presenter.LocalMusicFragmentPresenter;
import com.vac.vmusic.nativemusicfragment.view.NativeMusicFragment;
import com.vac.vmusic.skinfragment.activity.view.SkinActivity;
import com.vac.vmusic.utils.HomeColorManager;
import com.vac.vmusic.utils.RxBus;
import com.vac.vmusic.views.BounceScrollView;
import com.vac.vmusic.views.DampView;
import com.vac.vmusic.views.MyScrollView;

import rx.functions.Action1;

/**
 * Created by vac on 16/10/22.
 *
 */
public class LocalMusicFragment extends BaseSwipeBackFragment implements ILocalMusicFragment,View.OnClickListener{

    private BounceScrollView myScrollView;
    private RelativeLayout module_1,module_2,module_3,module_4;
    private LinearLayout favor_linear,song_list_linear;
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
        myScrollView = (BounceScrollView) view.findViewById(R.id.local_music_fra_scrollView);

        module_1 = (RelativeLayout) view.findViewById(R.id.local_music_fra_module_1);
        module_2 = (RelativeLayout) view.findViewById(R.id.local_music_fra_module_2);
        module_3 = (RelativeLayout) view.findViewById(R.id.local_music_fra_module_3);
        module_4 = (RelativeLayout) view.findViewById(R.id.local_music_fra_module_4);
        module_1.setOnClickListener(this);module_2.setOnClickListener(this);
        module_3.setOnClickListener(this);module_4.setOnClickListener(this);

        nativeMusicCountTextView = (TextView) view.findViewById(R.id.local_music_fra_totalnumber);

        ImageView bg = (ImageView) view.findViewById(R.id.local_music_bg);
        ImageView bg1 = (ImageView) view.findViewById(R.id.local_music_bg1);
        bg.setOnClickListener(this);
        bg1.setOnClickListener(this);

        favor_linear = (LinearLayout) view.findViewById(R.id.local_music_fra_module_favor);
        song_list_linear = (LinearLayout) view.findViewById(R.id.local_music_fra_crate_songlist);

        LocalMusicFragmentPresenter localMusicFragmentPresenter = new LocalMusicFragmentPresenter(this);
        localMusicFragmentPresenter.watchMyScrollView();
        localMusicFragmentPresenter.getNativeMusicCount();


        myScrollView.setImageView(bg1);

        RxBus.get().register("color",SkinPalette.class).subscribe(new Action1<SkinPalette>() {
            @Override
            public void call(SkinPalette skinPalette) {
//                if (skinPalette.getLightVibrantSwatchRgb()!=-1){
//                    module_2.setBackgroundColor(skinPalette.getVibrantSwatchRgb());
//                    module_3.setBackgroundColor(skinPalette.getVibrantSwatchRgb());
//                }else if (skinPalette.getMutedSwatchRgb()!=-1){
//                    module_2.setBackgroundColor(skinPalette.getMutedSwatchRgb());
//                    module_3.setBackgroundColor(skinPalette.getMutedSwatchRgb());
//                }
//                if (skinPalette.getDarkVibrantSwatchRgb()!=-1){
//                    module_1.setBackgroundColor(skinPalette.getDarkVibrantSwatchRgb());
//                    module_4.setBackgroundColor(skinPalette.getDarkVibrantSwatchRgb());
//                }else {
//                    module_1.setBackgroundColor(skinPalette.getDarkMutedSwatchRgb());
//                    module_4.setBackgroundColor(skinPalette.getDarkMutedSwatchRgb());
//                }

                module_1.setBackgroundColor(new HomeColorManager().getCurrentColor());
                module_4.setBackgroundColor(new HomeColorManager().getCurrentColor());

                module_2.setBackgroundColor(new HomeColorManager().getCurrentLightColor());
                module_3.setBackgroundColor(new HomeColorManager().getCurrentLightColor());

                favor_linear.setBackgroundColor(new HomeColorManager().getCurrentColor());
                song_list_linear.setBackgroundColor(new HomeColorManager().getCurrentColor());
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
            case R.id.local_music_bg:
            case R.id.local_music_bg1:
                startActivity(new Intent(getActivity(), SkinActivity.class));
                break;
        }
    }
}
