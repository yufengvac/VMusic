package com.vac.vmusic.songoptions.view;

import android.os.Parcelable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.vac.vmusic.R;
import com.vac.vmusic.base.BaseActivity;
import com.vac.vmusic.beans.LocalMusic;
import com.vac.vmusic.beans.search.TingSong;
import com.vac.vmusic.songoptions.presenter.SongOptionActivityPresenter;

/**
 * Created by vac on 16/11/16.
 *
 */
public class SongOptionActivity extends BaseActivity implements ISongOptionActivity, View.OnClickListener{
    private TextView songNameTextView;
    private TingSong tingSong;
    private SongOptionActivityPresenter songOptionActivityPresenter;
    @Override
    public void getContentViewId() {
        contentViewId = R.layout.song_option_activity;
    }

    @Override
    public void initView() {
        Parcelable parcelable = getIntent().getParcelableExtra("TingSong");
        songOptionActivityPresenter = new SongOptionActivityPresenter(this);
        if (parcelable instanceof LocalMusic){
            tingSong = songOptionActivityPresenter.translate((LocalMusic) parcelable);
        }else if (parcelable instanceof TingSong){
            tingSong = (TingSong) parcelable;
        }
        songNameTextView = (TextView) findViewById(R.id.song_option_activity_name_text_view);
        Button cancelBtn = (Button) findViewById(R.id.song_option_activity_cancel_btn);
        cancelBtn.setOnClickListener(this);

        songNameTextView.setText(tingSong.getName());
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.song_option_activity_cancel_btn:
                finish();
                overridePendingTransition(R.anim.push_bottom_out, 0);
                break;
        }
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
        if (keyCode == KeyEvent.KEYCODE_BACK){
            finish();
            overridePendingTransition(R.anim.push_bottom_out, 0);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
