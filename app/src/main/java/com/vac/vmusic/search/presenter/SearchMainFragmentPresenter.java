package com.vac.vmusic.search.presenter;

import android.text.Editable;
import android.text.TextWatcher;

import com.vac.vmusic.search.view.ISearchMainFragment;

/**
 * Created by vac on 16/10/24.
 *
 */
public class SearchMainFragmentPresenter implements TextWatcher{
    private ISearchMainFragment iSearchMainFragment;
    public SearchMainFragmentPresenter(ISearchMainFragment iSearchMainFragment_){
        this.iSearchMainFragment = iSearchMainFragment_;
    }

    public void addTextWatch(){
        iSearchMainFragment.getEditText().addTextChangedListener(this);
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (iSearchMainFragment.getEditText().getText().toString().trim().isEmpty()){
            iSearchMainFragment.closeClearIcon();
        }else {
            iSearchMainFragment.showClearIcon();
        }

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

}
