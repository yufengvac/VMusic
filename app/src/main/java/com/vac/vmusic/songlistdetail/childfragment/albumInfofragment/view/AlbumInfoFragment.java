package com.vac.vmusic.songlistdetail.childfragment.albumInfofragment.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.vac.vmusic.R;
import com.vac.vmusic.base.BaseSwipeBackFragment;
import com.vac.vmusic.beans.detail.AlbumDetail;

/**
 *
 * Created by vac on 16/11/14.
 */
public class AlbumInfoFragment extends BaseSwipeBackFragment{
    public static AlbumInfoFragment getAlbumInfoFragment(Bundle bundle){
        AlbumInfoFragment albumInfoFragment = new AlbumInfoFragment();
        if (bundle!=null){
            albumInfoFragment.setArguments(bundle);
        }
        return albumInfoFragment;
    }
    @Override
    public void getFragmentViewId() {
        fragmentViewId = R.layout.album_info_fragment;
    }

    @Override
    public void initView(View view) {
        AlbumDetail albumDetail = getArguments().getParcelable("albumDetail");
        if (albumDetail==null){
            Log.e("TAG","albumDetail=null");
            return;
        }
        Log.i("TAG","albumDetail="+albumDetail.toString());
        TextView name =(TextView) view.findViewById(R.id.album_info_fragment_name);
        TextView publishDate =(TextView) view.findViewById(R.id.album_info_fragment_publish_date);
        TextView type =(TextView) view.findViewById(R.id.album_info_fragment_publish_type);
        TextView lang =(TextView) view.findViewById(R.id.album_info_fragment_lang);
        TextView company =(TextView) view.findViewById(R.id.album_info_fragment_company);
        TextView produce = (TextView) view.findViewById(R.id.album_info_fragment_content);

        String albumNameStr = getString(R.string.album_detail_album_name)+albumDetail.getName();
        name.setText(albumNameStr);
        String publishDateStr = getString(R.string.album_detail_publish_date)+albumDetail.getPublishDate();
        publishDate.setText(publishDateStr);
        String typeStr = getString(R.string.album_detail_type)+albumDetail.getTypeName();
        type.setText(typeStr);
        String langStr = getString(R.string.album_detail_lang)+albumDetail.getLang();
        lang.setText(langStr);
        String companyStr = getString(R.string.album_detail_company)+albumDetail.getCompanyName();
        company.setText(companyStr);
        produce.setText(albumDetail.getDescription());
    }
}
