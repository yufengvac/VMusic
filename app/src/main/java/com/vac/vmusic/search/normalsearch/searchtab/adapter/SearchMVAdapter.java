package com.vac.vmusic.search.normalsearch.searchtab.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vac.vmusic.R;
import com.vac.vmusic.beans.search.TingMV;
import com.vac.vmusic.beans.search.TingSearchMV;
import com.vac.vmusic.callback.OnItemClickListener;
import com.vac.vmusic.utils.ViewUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by vac on 16/10/18.
 *
 */
public class SearchMVAdapter extends RecyclerView.Adapter<SearchMVAdapter.MyViewHolder> {
    private Context mContext;
    private List<TingSearchMV> mData = new ArrayList<>();
    private SimpleDateFormat sdf ;
    private int mLastPosition = -1;
    private OnItemClickListener onItemClickListener;

    public static final int TYPE_FOOTER = 3;
    public static final int TYPE_NORMAL = 4;
    protected boolean mShowFooter;
    private View footerView;

    public SearchMVAdapter(Context context,OnItemClickListener listener){
        this.mContext = context;
        sdf = new SimpleDateFormat("mm:ss", Locale.CHINA);
        this.onItemClickListener = listener;
    }

    public void setData(List<TingSearchMV> tingMV,boolean isRefresh){
        if (tingMV!=null&&tingMV.size()>0){
            int count = mData.size();
            if (isRefresh){
                mData.clear();
                mData.addAll(tingMV);
                notifyDataSetChanged();
            }else {
                mData.addAll(tingMV);
                notifyItemChanged(count,tingMV.size());
            }

        }
    }
    public List<TingSearchMV> getData(){
        return mData;
    }

    @Override
    public int getItemCount() {
        return mShowFooter?mData.size()+1:mData.size();
    }
    @Override
    public int getItemViewType(int position) {
        if (mShowFooter&&position==getItemCount()-1) {
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }
    public void showFooter() {
        // KLog.e("Adapter显示尾部: " + getItemCount());
        mShowFooter = true;
        notifyItemInserted(getItemCount());

    }

    public void hideFooter() {
        // KLog.e("Adapter隐藏尾部:" + (getItemCount() - 1));
        mShowFooter = false;
        notifyItemRemoved(getItemCount());
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (getItemViewType(position) != TYPE_FOOTER) {
            TingSearchMV tingSearchMV = mData.get(position);
            holder.title.setText(tingSearchMV.getVideoName());
            holder.singer.setText(tingSearchMV.getSingerName());
            List<TingMV> tingMVList = tingSearchMV.getMvList();
            if (tingMVList != null && tingMVList.size() > 0) {
                holder.duration.setText(sdf.format(new Date(tingSearchMV.getMvList().get(0).getDurationMilliSecond())));
                if (tingMVList.get(0).getType() == 2) {//1080p
                    holder.quality.setVisibility(View.VISIBLE);
                    holder.quality.setImageResource(R.drawable.icon_mv_super_hd);
                } else if (tingMVList.get(0).getType() == 1) {//720p
                    holder.quality.setVisibility(View.VISIBLE);
                    holder.quality.setImageResource(R.drawable.icon_mv_hd);
                } else {
                    holder.quality.setVisibility(View.GONE);
                }
            }

            Glide.with(mContext).load(tingSearchMV.getPicUrl()).into(holder.logo);
            setAnimation(holder.itemView, position);
        }
    }
    protected void setAnimation(View viewToAnimate, int position) {
        if (position > mLastPosition) {
            Animation animation = AnimationUtils
                    .loadAnimation(viewToAnimate.getContext(), R.anim.item_bottom_in);
            viewToAnimate.startAnimation(animation);
            mLastPosition = position;
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==TYPE_FOOTER){
            if (footerView==null){
                footerView = LayoutInflater.from(mContext).inflate(R.layout.refresh_layout_footer,parent,false);
            }
            return new MyViewHolder(footerView);
        }else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_search_mv, parent, false);
            return new MyViewHolder(view);
        }
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView logo,quality;
        private TextView title,singer,duration;
        private LinearLayout content;
        public MyViewHolder(View view){
            super(view);
            if (view!=footerView) {
                logo = (ImageView) view.findViewById(R.id.item_search_mv_logo);
                title = (TextView) view.findViewById(R.id.item_search_mv_title);
                singer = (TextView) view.findViewById(R.id.item_search_mv_singer);
                duration = (TextView) view.findViewById(R.id.item_search_mv_duration);
                quality = (ImageView) view.findViewById(R.id.item_search_mv_quality);
                content = (LinearLayout) view.findViewById(R.id.item_search_mv_content);
                content.setClickable(true);
                content.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemClickListener.onItemClick(view, getLayoutPosition());
                    }
                });
            }else {
                ViewUtil.showRefreshLayout(view,"正在加载..");
            }
        }
    }
}
