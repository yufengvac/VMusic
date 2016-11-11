package com.vac.vmusic.search.normalsearch.searchtab.adapter;

import android.content.Context;
import android.media.Image;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.vac.vmusic.R;
import com.vac.vmusic.beans.search.TingAudition;
import com.vac.vmusic.beans.search.TingMV;
import com.vac.vmusic.beans.search.TingSearchMV;
import com.vac.vmusic.beans.search.TingSong;
import com.vac.vmusic.callback.OnItemClickListener;
import com.vac.vmusic.utils.HomeColorManager;
import com.vac.vmusic.utils.ViewUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vac on 16/10/17.
 *
 */
public class SearchSongAdapter extends RecyclerView.Adapter<SearchSongAdapter.MyViewHolder> {

    private Context mContext;
    private List<TingSong> mData = new ArrayList<>();
    public static final int TYPE_FOOTER = 3;
    public static final int TYPE_NORMAL = 4;
    protected boolean mShowFooter;
    private OnItemClickListener clickListener;

    private View footerView;

    public SearchSongAdapter(Context context, OnItemClickListener onItemClickListener) {
        this.mContext = context;
        this.clickListener = onItemClickListener;
    }

    public List<TingSong> getmData() {
        return mData;
    }

    private int mLastPosition = -1;

    public void setData(List<TingSong> tingSongs, boolean isRefresh) {
        if (tingSongs != null && tingSongs.size() > 0) {
            if (isRefresh) {
                mData.clear();
            }
            mData.addAll(tingSongs);
            notifyDataSetChanged();
        }
    }


    @Override
    public int getItemCount() {
        return mShowFooter?mData.size()+1:mData.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            if (footerView == null) {
                footerView = LayoutInflater.from(mContext).inflate(R.layout.refresh_layout_footer, parent, false);
            }
            return new MyViewHolder(footerView);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_search_song, parent, false);

            return new MyViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_FOOTER) {

        } else {
            TingSong tingSong = mData.get(position);
            holder.name.setText(tingSong.getName());
            holder.singer.setText(tingSong.getSingerName());
            holder.album.setText(tingSong.getAlbumName());

            if (tingSong.getAlias() == null || tingSong.getAlias().isEmpty()) {
                holder.alias.setVisibility(View.GONE);
            } else {
                holder.alias.setVisibility(View.VISIBLE);
                holder.alias.setText(tingSong.getAlias());
            }

            holder.name.setTextColor(ContextCompat.getColor(mContext,R.color.colorBlack));
            holder.alias.setTextColor(ContextCompat.getColor(mContext,R.color.colorDarkGrey));
            holder.singer.setTextColor(HomeColorManager.getHomeColorManager().getCurrentColor());
            holder.album.setTextColor(ContextCompat.getColor(mContext,R.color.colorDarkGrey));

            List<TingAudition> tingAuditions = tingSong.getAuditionList();
            if (tingAuditions != null) {
                holder.qualityView.setVisibility(View.VISIBLE);
                holder.content.setClickable(true);
                if (tingAuditions.size() == 3) {
                    holder.qualityView.setImageResource(R.drawable.icon_hq);
                } else {
                    holder.qualityView.setVisibility(View.GONE);
                    if (tingAuditions.size() == 0){
                        holder.content.setClickable(false);
                        holder.name.setTextColor(ContextCompat.getColor(mContext,R.color.colorDarkGrey));
                        holder.alias.setTextColor(ContextCompat.getColor(mContext,R.color.colorDarkGrey));
                        holder.singer.setTextColor(ContextCompat.getColor(mContext,R.color.colorDarkGrey));
                        holder.album.setTextColor(ContextCompat.getColor(mContext,R.color.colorDarkGrey));

                    }
                }

                if (tingSong.getLlList() != null && tingSong.getLlList().size() > 0) {
                    holder.qualityView.setImageResource(R.drawable.icon_sq);
                }
            }else {
                holder.content.setBackgroundColor(ContextCompat.getColor(mContext,R.color.colorGrey));
            }

            List<TingMV> tingSearchMVs = tingSong.getMvList();
            if (tingSearchMVs != null && tingSearchMVs.size() > 0) {
                holder.mvView.setVisibility(View.VISIBLE);
            } else {
                holder.mvView.setVisibility(View.GONE);
            }

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
    public int getItemViewType(int position) {
        if (mShowFooter&&position==getItemCount()-1) {
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }

    @Override
    public void onViewDetachedFromWindow(MyViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if (holder.itemView.getAnimation() != null && holder.itemView
                .getAnimation().hasStarted()) {
            holder.itemView.clearAnimation();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name, singer, album, alias;
        private ImageView qualityView, mvView;
        private ImageView addSongImageView;
        private LinearLayout content;

        public MyViewHolder(View view) {
            super(view);
            if (view == footerView) {
                ViewUtil.showRefreshLayout(view,"正在加载..");
            } else {
                name = (TextView) view.findViewById(R.id.item_home_name);
                singer = (TextView) view.findViewById(R.id.item_home_singer);
                album = (TextView) view.findViewById(R.id.item_home_album);
                alias = (TextView) view.findViewById(R.id.item_home_alias);
                qualityView = (ImageView) view.findViewById(R.id.item_home_quality_imageview);
                mvView = (ImageView) view.findViewById(R.id.item_home_mv_imageview);

                addSongImageView = (ImageView) view.findViewById(R.id.item_search_song_add_songlist_imageview);
                content = (LinearLayout) view.findViewById(R.id.item_search_song_content);
                content.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clickListener.onItemClick(content, getLayoutPosition());
                    }
                });
                addSongImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clickListener.onItemClick(addSongImageView, getAdapterPosition());
                    }
                });
            }
        }
    }
}
