package com.sonicse.bjcp;

import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by sonicse on 17.10.15.
 */

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private OnClickListener mOnClickListener;
    private List<String> mListItems;
    private RecyclerView mRecyclerView;

    public interface OnClickListener {
        void onItemClick(int pos);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder
    {
        public TextView mTextView;

        public ItemViewHolder(View v) {
            super(v);
            mTextView = (TextView)v.findViewById(R.id.list_item_text_id);
        }
    }

    public SearchAdapter(List<String> items)
    {
        this.mListItems = items;

        setHasStableIds(true);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listview_item, parent, false);

        final ItemViewHolder holder = new ItemViewHolder(v);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnClickListener.onItemClick(holder.getPosition());
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
        String item = mListItems.get(position);
        itemViewHolder.mTextView.setText(item);
    }

    @Override
    public int getItemCount() {
        return this.mListItems.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public String getItem(int position) {
        return this.mListItems.get(position);
    }
}
