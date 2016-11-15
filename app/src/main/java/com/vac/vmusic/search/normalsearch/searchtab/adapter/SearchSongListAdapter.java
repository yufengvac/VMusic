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
import com.vac.vmusic.beans.search.TingSongList;
import com.vac.vmusic.callback.OnItemClickListener;
import com.vac.vmusic.utils.ViewUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vac on 16/10/18.
 *
 */
public class SearchSongListAdapter extends RecyclerView.Adapter<SearchSongListAdapter.MyViewHolder>{

    private Context mContext;
    private List<TingSongList> mData = new ArrayList<>();

    public static final int TYPE_FOOTER = 3;
    public static final int TYPE_NORMAL = 4;
    protected boolean mShowFooter;
    private View footerView;
    private OnItemClickListener listener;
    public SearchSongListAdapter(Context context, OnItemClickListener onItemClickListener){
        this.mContext = context;
        this.listener = onItemClickListener;
    }

    private int mLastPosition = -1;
    public void setData(List<TingSongList> list,boolean isRefresh){
        if (list!=null&&list.size()>0){
            int count = mData.size();
            if (isRefresh){
                mData.clear();
                mData.addAll(list);
                notifyDataSetChanged();
            }else {
                mData.addAll(list);
                notifyItemChanged(count,list.size());
            }
        }
    }

    public List<TingSongList> getData(){
        return mData;
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
    public int getItemCount() {
        return mShowFooter?mData.size()+1:mData.size();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (getItemViewType(position) != TYPE_FOOTER) {
            TingSongList tingSongList = mData.get(position);
            holder.author.setText(tingSongList.getAuthor());
            holder.name.setText(tingSongList.getTitle());
            int length = 0;
            if (tingSongList.getSong_list() != null) {
                length = tingSongList.getSong_list().split(",").length;
            }
            String numStr = length + "首";
            holder.num.setText(numStr);
            Glide.with(mContext).load(tingSongList.getPic_url()).into(holder.logo);
            setAnimation(holder.itemView, position);
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
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_search_songlist, parent, false);
            return new MyViewHolder(view);
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

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView logo;
        private TextView name,author,num;
        private LinearLayout content;
            public MyViewHolder(View view) {
                super(view);
                if (view!=footerView) {
                    logo = (ImageView) view.findViewById(R.id.item_search_songlist_logo);
                    name = (TextView) view.findViewById(R.id.item_search_songlist_name);
                    author = (TextView) view.findViewById(R.id.item_search_songlist_author);
                    num = (TextView) view.findViewById(R.id.item_search_songlist_num);
                    content = (LinearLayout) view.findViewById(R.id.item_search_songlist_content);
                    content.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            listener.onItemClick(view,getLayoutPosition());
                        }
                    });
                }else {
                    ViewUtil.showRefreshLayout(view,"正在加载..");
                }
            }
        }

}
