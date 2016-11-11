package com.vac.vmusic.search.view;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vac.vmusic.R;
import com.vac.vmusic.base.BaseSwipeBackFragment;
import com.vac.vmusic.search.hotsearch.view.HotSearchFragment;
import com.vac.vmusic.search.normalsearch.searchtab.view.ISearchTabFragment;
import com.vac.vmusic.search.normalsearch.view.SearchHomeFragment;
import com.vac.vmusic.search.presenter.SearchMainFragmentPresenter;
import com.vac.vmusic.utils.HomeColorManager;
import com.vac.vmusic.utils.RxBus;
import com.vac.vmusic.utils.ViewUtil;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by vac on 16/10/22.
 *
 */
public class SearchMainFragment extends BaseSwipeBackFragment implements ISearchMainFragment,View.OnClickListener,TextView.OnEditorActionListener{

    private EditText searchEditText;
    private FragmentManager fragmentManager;
    private ImageView clearImageView;

    private Subscription searchSub;
    public static SearchMainFragment getSearchMainFragment(Bundle bundle){
        SearchMainFragment searchMainFragment = new SearchMainFragment();
        if (bundle!=null){
            searchMainFragment.setArguments(bundle);
        }
        return searchMainFragment;
    }
    @Override
    public void getFragmentViewId() {
        fragmentViewId = R.layout.search_main_fragment;
    }

    @Override
    public void initView(View view) {
        LinearLayout topContentView = (LinearLayout) view.findViewById(R.id.search_main_fragment_top_content);
        topContentView.setBackgroundColor(HomeColorManager.getHomeColorManager().getCurrentColor());

        ImageView backImageView = (ImageView) view.findViewById(R.id.home_search_top_back_imageview);
        backImageView.setOnClickListener(this);
        ImageView searchImageView = (ImageView) view.findViewById(R.id.home_search_fragment_search);
        searchImageView.setOnClickListener(this);

        searchEditText = (EditText) view.findViewById(R.id.search_main_fragment_edit);
        searchEditText.setOnEditorActionListener(this);

        clearImageView = (ImageView) view.findViewById(R.id.home_search_fragment_clearedit_imageview);
        clearImageView.setOnClickListener(this);

        fragmentManager = getChildFragmentManager();
        setHotSearch();

        SearchMainFragmentPresenter searchMainFragmentPresenter = new SearchMainFragmentPresenter(this);
        searchMainFragmentPresenter.addTextWatch();

        searchSub = RxBus.get().register("search",String.class).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                setNormalSearch(s);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (searchSub!=null){
            searchSub.unsubscribe();
        }
    }

    @Override
    public void showClearIcon() {
        if (clearImageView.getVisibility()!=View.VISIBLE){
            clearImageView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void closeClearIcon() {
        if (clearImageView.getVisibility()!=View.GONE){
            clearImageView.setVisibility(View.GONE);
        }
    }

    @Override
    public String getKeyWord() {
        return searchEditText.getText().toString().trim();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.home_search_top_back_imageview){
            getActivity().onBackPressed();
        }else if (id == R.id.home_search_fragment_search){
            setNormalSearch();
        }else if (id == R.id.home_search_fragment_clearedit_imageview){
            searchEditText.setText("");
            setHotSearch();
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i== EditorInfo.IME_ACTION_SEARCH){
            setNormalSearch();
            return true;
        }
        return false;
    }

    @Override
    public void setHotSearch() {
        fragmentManager.beginTransaction().replace(R.id.search_main_fragment_content, HotSearchFragment.getHotSearchFragment(null))
                .commit();
    }

    @Override
    public EditText getEditText() {
        return searchEditText;
    }

    @Override
    public void setNormalSearch() {
        ViewUtil.toggleInput(getActivity());
        Bundle bundle = new Bundle();
        bundle.putString("keyword",getKeyWord());
        fragmentManager.beginTransaction().replace(R.id.search_main_fragment_content, SearchHomeFragment.getSearchHomeFragment(bundle))
                .commit();
    }

    @Override
    public void setNormalSearch(String content) {
        Bundle bundle = new Bundle();
        searchEditText.setText(content);
        bundle.putString("keyword",content);
        fragmentManager.beginTransaction().replace(R.id.search_main_fragment_content, SearchHomeFragment.getSearchHomeFragment(bundle))
                .commit();
    }
}
