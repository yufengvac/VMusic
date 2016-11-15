package com.vac.vmusic.search.hotsearch.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.vac.vmusic.R;
import com.vac.vmusic.base.BaseSwipeBackFragment;
import com.vac.vmusic.search.hotsearch.presenter.HotSearchFragmentPresenter;
import com.vac.vmusic.utils.DialogUtils;
import com.vac.vmusic.utils.HomeColorManager;
import com.vac.vmusic.views.BorderTextView;
import com.vac.vmusic.views.WordWrapView;

/**
 * Created by vac on 16/10/24.
 *
 */
public class HotSearchFragment extends BaseSwipeBackFragment implements IHotSearchFragment,View.OnClickListener{

    private HotSearchFragmentPresenter hotSearchFragmentPresenter;
    private WordWrapView wordWrapView;
    private ImageView deleteImageView;
    public static HotSearchFragment getHotSearchFragment(Bundle bundle){
        HotSearchFragment hotSearchFragment = new HotSearchFragment();
        if (bundle!=null){
            hotSearchFragment.setArguments(bundle);
        }
        return hotSearchFragment;
    }
    @Override
    public void getFragmentViewId() {
        fragmentViewId = R.layout.hot_search_fragment;
    }

    @Override
    public void initView(View view) {
        wordWrapView = (WordWrapView) view.findViewById(R.id.hot_search_fragment_wordwrapView);
        BorderTextView historyTextView = (BorderTextView)view.findViewById(R.id.hot_search_fragment_history_search_border_text_view);
        historyTextView.setBorderColor(HomeColorManager.getHomeColorManager().getCurrentColor());
        deleteImageView = (ImageView) view.findViewById(R.id.hot_search_fragment_history_search_delete_image_view);
        deleteImageView.setOnClickListener(this);
        hotSearchFragmentPresenter = new HotSearchFragmentPresenter(this);
        hotSearchFragmentPresenter.loadHistorySearch(wordWrapView);

    }


    @Override
    public Context getMyContext() {
        return getActivity();
    }

    @Override
    public void reloadWordView() {
        hotSearchFragmentPresenter.loadHistorySearch(wordWrapView);
    }

    @Override
    public void showDeleteIcon() {
        deleteImageView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideDeleteIcon() {
        deleteImageView.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.hot_search_fragment_history_search_delete_image_view){
            DialogUtils.showDialog(getActivity(), "确定删除搜索记录?", new DialogUtils.OnDialogBtnClickListener() {
                @Override
                public void onBtnOk() {
                    hotSearchFragmentPresenter.deleteSearchHistory();
                }

                @Override
                public void onBtnCancel() {

                }
            });
        }
    }
}
