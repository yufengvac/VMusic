package com.vac.vmusic.playmusic.view;

import android.view.KeyEvent;
import android.view.View;

import com.vac.vmusic.R;
import com.vac.vmusic.base.BaseActivity;

/**
 * Created by vac on 16/10/25.
 *
 */
public class PlayMusicActivity extends BaseActivity {
    @Override
    public void getContentViewId() {
        contentViewId = R.layout.play_music_activity;
    }

    @Override
    public void initView() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            back(null);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    public void back(View view){
        finish();
        overridePendingTransition(R.anim.push_bottom_out,R.anim.push_bottom_out);
    }
}
