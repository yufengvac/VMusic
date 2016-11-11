package com.vac.vmusic.search.hotsearch.presenter;

import android.graphics.Color;
import android.view.View;

import com.vac.vmusic.beans.search.historysearch.HistorySearch;
import com.vac.vmusic.search.hotsearch.view.IHotSearchFragment;
import com.vac.vmusic.utils.RxBus;
import com.vac.vmusic.views.BorderTextView;
import com.vac.vmusic.views.WordWrapView;

import org.litepal.crud.DataSupport;

import java.util.List;
import java.util.Random;

/**
 * Created by vac on 16/10/24.
 *
 */
public class HotSearchFragmentPresenter implements View.OnClickListener{
    private IHotSearchFragment iHotSearchFragment;
    private Random random;
    public HotSearchFragmentPresenter(IHotSearchFragment iHotSearchFragment_){
        this.iHotSearchFragment = iHotSearchFragment_;
        random = new Random();
    }
    public void loadHistorySearch(WordWrapView wordWrapView){
        List<HistorySearch> historySearchList = DataSupport.findAll(HistorySearch.class);
        for (int i =0;i<historySearchList.size();i++){
            BorderTextView borderTextView = new BorderTextView(iHotSearchFragment.getMyContext());
            String content = historySearchList.get(i).getContent();
            borderTextView.setText(content);
            borderTextView.setBorderColor(Color.argb(255,random.nextInt(255),random.nextInt(255),random.nextInt(255)));
            borderTextView.setClickable(true);
            borderTextView.setOnClickListener(this);
            wordWrapView.addView(borderTextView);
        }
    }

    @Override
    public void onClick(View view) {
        if (view instanceof BorderTextView){
            String content = ((BorderTextView)view).getText().toString();
            RxBus.get().post("search",content);
        }
    }
}
