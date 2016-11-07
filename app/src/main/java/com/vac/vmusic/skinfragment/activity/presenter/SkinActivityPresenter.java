package com.vac.vmusic.skinfragment.activity.presenter;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.vac.vmusic.R;
import com.vac.vmusic.base.BaseSwipeBackFragment;
import com.vac.vmusic.beans.skin.Skin;
import com.vac.vmusic.beans.skin.SkinData;
import com.vac.vmusic.callback.RequestCallback;
import com.vac.vmusic.homefragment.adapters.CustomFragmentAdapter;
import com.vac.vmusic.homefragment.adapters.MyFragmentAdapter;
import com.vac.vmusic.skinfragment.activity.model.SkinActivityModel;
import com.vac.vmusic.skinfragment.activity.view.ISkinActivity;
import com.vac.vmusic.skinfragment.fragments.view.SkinFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vac on 16/11/7.
 *
 */
public class SkinActivityPresenter implements RequestCallback<Skin>,ViewPager.OnPageChangeListener{
    private ISkinActivity iSkinActivity;
    private SkinActivityModel skinActivityModel ;
    private CustomFragmentAdapter customFragmentAdapter;
    private List<BaseSwipeBackFragment> skinFragmentList;
    private ViewPager viewPager;
    private List<RadioButton> radioButtonList = new ArrayList<>();
    public SkinActivityPresenter(ISkinActivity iSkinActivity_){
        this.iSkinActivity = iSkinActivity_;
        skinActivityModel = new SkinActivityModel();
        skinFragmentList = new ArrayList<>();
    }
    public void loadData(){
        viewPager = iSkinActivity.getViewPager();
        customFragmentAdapter = new CustomFragmentAdapter(iSkinActivity.getFm());
        viewPager.setAdapter(customFragmentAdapter);
        skinActivityModel.getWallPager(this);
        viewPager.addOnPageChangeListener(this);
    }
    public void removeOnPageListener(){
        viewPager.removeOnPageChangeListener(this);
    }

    private void addRadioButton(Skin skin){
        List<SkinData> skinDataList = skin.getData();
        radioButtonList.clear();
        int size = skinDataList.size()/3+(skinDataList.size()%3==0?0:1);
        for (int i=0;i<size;i++){
            RadioButton radioButton = new RadioButton(iSkinActivity.getMyContext());
            radioButton.setButtonDrawable(android.R.color.transparent);
            radioButton.setBackgroundResource(R.drawable.selector_indicator_skin);
            radioButton.setHeight(5);
            radioButton.setWidth(5);
            if (i==0){
                radioButton.setChecked(true);
            }else {
                radioButton.setChecked(false);
            }
            RadioGroup.LayoutParams lp = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
            lp.leftMargin = 15;
            radioButton.setLayoutParams(lp);
            radioButtonList.add(radioButton);
            iSkinActivity.getRadioGroup().addView(radioButton);


            Bundle bundle = new Bundle();
            bundle.putString("url1",(i*3)<skinDataList.size()?skinDataList.get(i*3).getPicUrl():"");
            bundle.putString("url2",(i*3+1)<skinDataList.size()?skinDataList.get(i*3+1).getPicUrl():"");
            bundle.putString("url3",(i*3+2)<skinDataList.size()?skinDataList.get(i*3+2).getPicUrl():"");
            SkinFragment skinFragment = SkinFragment.getSkinFragment(bundle);
            skinFragment.setArguments(bundle);
            skinFragmentList.add(skinFragment);
        }
        customFragmentAdapter.setData(skinFragmentList);
    }

    @Override
    public void beforeRequest() {

    }

    @Override
    public void requestCompleted() {

    }

    @Override
    public void requestSuccess(List<Skin> data) {
        if (data.size()>0){
            Log.i("SkinActivityPresenter",data.get(0).getTagName());
            addRadioButton(data.get(0));
        }
    }

    @Override
    public void requestError(String errorMsg) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i=0;i<iSkinActivity.getRadioGroup().getChildCount();i++){
            if (i==position){
                radioButtonList.get(i).setChecked(true);
            }else {
                radioButtonList.get(i).setChecked(false);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
