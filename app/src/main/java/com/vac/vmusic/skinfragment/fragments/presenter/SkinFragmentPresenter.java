package com.vac.vmusic.skinfragment.fragments.presenter;

import android.content.Context;

import com.vac.vmusic.skinfragment.fragments.model.SkinFragmentModel;
import com.vac.vmusic.skinfragment.fragments.view.ISkinFragment;

/**
 * Created by yufengvac on 16/11/7.
 *
 */
public class SkinFragmentPresenter {
    private ISkinFragment iSkinFragment;
    private SkinFragmentModel skinFragmentModel;
    public SkinFragmentPresenter(ISkinFragment iSkinFragment_){
        this.iSkinFragment = iSkinFragment_;
        skinFragmentModel = new SkinFragmentModel();
    }

    public void updateHomeColor(Context context, String url){
        skinFragmentModel.updateColor(context,url);
    }
}
