package com.vac.vmusic.skinfragment.fragments.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.vac.vmusic.R;
import com.vac.vmusic.base.BaseSwipeBackFragment;
import com.vac.vmusic.skinfragment.fragments.presenter.SkinFragmentPresenter;
import com.vac.vmusic.utils.HomeColorManager;
import com.vac.vmusic.utils.RxBus;

import java.util.concurrent.ExecutionException;

/**
 * Created by vac on 16/11/7.
 *
 */
public class SkinFragment extends BaseSwipeBackFragment implements View.OnClickListener,ISkinFragment{
    private ImageView imageView1,imageView2,imageView3;
    private  String url1,url2,url3;
    private SkinFragmentPresenter skinFragmentPresenter;
    public static SkinFragment getSkinFragment(Bundle bundle){
        SkinFragment skinFragment = new SkinFragment();
        if (bundle!=null){
            skinFragment.setArguments(bundle);
        }
        return skinFragment;
    }
    @Override
    public void getFragmentViewId() {
        fragmentViewId = R.layout.skin_fragment;
    }

    @Override
    public void initView(View view) {
        imageView1 = (ImageView)view.findViewById(R.id.skin_imageview_1);
        imageView2 = (ImageView)view.findViewById(R.id.skin_imageview_2);
        imageView3 = (ImageView)view.findViewById(R.id.skin_imageview_3);
        imageView1.setOnClickListener(this);
        imageView2.setOnClickListener(this);
        imageView3.setOnClickListener(this);
        Bundle bundle = getArguments();
        if (bundle!=null){
            url1 = bundle.getString("url1");
            url2 = bundle.getString("url2");
            url3 = bundle.getString("url3");
            if (url1!=null&&!url1.isEmpty()){
                Glide.with(getActivity()).load(url1).into(imageView1);
            }
            if (url2!=null&&!url2.isEmpty()){
                Glide.with(getActivity()).load(url2).into(imageView2);
            }else {
                imageView2.setVisibility(View.INVISIBLE);
            }
           if (url3!=null&&!url3.isEmpty()){
               Glide.with(getActivity()).load(url3).into(imageView3);
           }else {
               imageView3.setVisibility(View.INVISIBLE);
           }

        }

        skinFragmentPresenter = new SkinFragmentPresenter(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            case R.id.skin_imageview_1:
                skinFragmentPresenter.updateHomeColor(getActivity(),url1);
                break;
            case R.id.skin_imageview_2:
                skinFragmentPresenter.updateHomeColor(getActivity(),url2);
                break;
            case R.id.skin_imageview_3:
                skinFragmentPresenter.updateHomeColor(getActivity(),url3);
                break;
        }
    }
}
