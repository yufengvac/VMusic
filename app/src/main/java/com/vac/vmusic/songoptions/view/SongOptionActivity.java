package com.vac.vmusic.songoptions.view;

import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;

import com.vac.vmusic.R;
import com.vac.vmusic.base.BaseActivity;

/**
 * Created by vac on 16/11/16.
 *
 */
public class SongOptionActivity extends BaseActivity {
    @Override
    public void getContentViewId() {
        contentViewId = R.layout.song_option_activity;
    }

    @Override
    public void initView() {

    }
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        View view = getWindow().getDecorView();
        view.setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = (WindowManager.LayoutParams) view.getLayoutParams();
        lp.gravity = Gravity.BOTTOM;
        lp.x = 0;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindowManager().updateViewLayout(view, lp);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
}
