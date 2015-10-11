package com.example.sonicse.bjcp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by sonicse on 16.09.15.
 */

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> mGroups;
    private HashMap<String, List<String>> mItems;
    private OnClickListener mOnClickListener;
    private List<ListItem> mListItems;
    private RecyclerView mRecyclerView;

    private static final int TYPE_GROUP = 1;
    private static final int TYPE_ITEM = 2;
    private static final int LISTITEM_COLLAPSED = -1;
    private static final int LISTITEM_EXPANDED = -2;

    private static final long LOWER_32BIT_MASK = 0x00000000ffffffffl;
    private static final long LOWER_31BIT_MASK = 0x000000007fffffffl;

    public class ListItem {
        public int group;
        public int item;
    }

    public boolean isGroup(ListItem item)
    {
        return item.item < 0;
    }

    public boolean isExpanded(ListItem item)
    {
        return item.item == LISTITEM_EXPANDED;
    }

    public void setExpanded(ListItem item, boolean expanded)
    {
        if (!isGroup(item) || isExpanded(item) == expanded)
        {
            return;
        }

        if(expanded) {
            expandGroup(item);
        } else {
            collapseGroup(item);
        }
    }

    private void expandGroup(ListItem group)
    {
        group.item = LISTITEM_EXPANDED;

        int pos = this.mListItems.indexOf(group) + 1;
        int itemCount = this.mItems.get(mGroups.get(group.group)).size();

        for (int idx = 0; idx < itemCount; ++idx)
        {
            ListItem item = new ListItem();
            item.group = group.group;
            item.item = idx;

            this.mListItems.add(pos + idx, item);
        }

        notifyItemChanged(pos - 1);
        notifyItemRangeInserted(pos, itemCount);

        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        if (linearLayoutManager.findFirstVisibleItemPosition() < pos
                && linearLayoutManager.findLastCompletelyVisibleItemPosition() < pos + itemCount) {
            mRecyclerView.smoothScrollToPosition(pos + itemCount - 1);
            //mRecyclerView.scrollToPosition(pos + itemCount - 1);
        }
    }

    private void collapseGroup(ListItem group)
    {
        group.item = LISTITEM_COLLAPSED;

        int pos = this.mListItems.indexOf(group) + 1;
        int itemCount = this.mItems.get(mGroups.get(group.group)).size();

        this.mListItems.subList(pos, pos+itemCount).clear();

        notifyItemChanged(pos - 1);
        notifyItemRangeRemoved(pos, itemCount);
    }

    public interface OnClickListener {
        void onGroupClick(int group);
        void onItemClick(int group, int item);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    public static class GroupViewHolder extends RecyclerView.ViewHolder
    {
        public TextView mTextView;
        public ImageView mImageView;

        public GroupViewHolder(View v) {
            super(v);
            mTextView = (TextView)v.findViewById(R.id.list_group_text_id);
            mImageView = (ImageView)v.findViewById(R.id.list_item_expand_arrow);
        }
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder
    {
        public TextView mTextView;

        public ItemViewHolder(View v) {
            super(v);
            mTextView = (TextView)v.findViewById(R.id.list_item_text_id);
        }
    }

    public ListAdapter(List<String> groups, HashMap<String, List<String>> childs)
    {
        this.mGroups = groups;
        this.mItems = childs;
        this.mListItems = new ArrayList<ListItem>();

        setHasStableIds(true);

        int groupSize = this.mGroups.size();
        for (int idx = 0; idx < groupSize; ++idx)
        {
            ListItem item = new ListItem();
            item.group = idx;
            item.item = LISTITEM_COLLAPSED;
            this.mListItems.add(item);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    @Override
    public int getItemViewType(int position) {
        ListItem item = mListItems.get(position);
        return isGroup(item) ? TYPE_GROUP : TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {

            case TYPE_ITEM: {
                View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.listview_item, parent, false);

                final ItemViewHolder holder = new ItemViewHolder(v);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getPosition();
                        ListItem item = mListItems.get(position);
                        mOnClickListener.onItemClick(item.group, item.item);
                    }
                });

                return holder;
            }

            case TYPE_GROUP: {
                View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.listview_group, parent, false);

                final GroupViewHolder holder = new GroupViewHolder(v);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getPosition();
                        ListItem item = mListItems.get(position);
                        setExpanded(item, !isExpanded(item));

                        mOnClickListener.onGroupClick(item.group);
                    }
                });

                return holder;
            }

            default:
                throw new IllegalStateException("Incorrect ViewType found");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        switch (viewHolder.getItemViewType()) {

            case TYPE_ITEM: {
                ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
                ListItem item = mListItems.get(position);
                itemViewHolder.mTextView.setText(this.mItems.get(mGroups.get(item.group)).get(item.item));
            }
            break;

            case TYPE_GROUP: {
                GroupViewHolder groupViewHolder = (GroupViewHolder) viewHolder;
                ListItem item = mListItems.get(position);
                groupViewHolder.mTextView.setText(this.mGroups.get(item.group));

                groupViewHolder.mImageView.setImageResource(isExpanded(item) ? R.drawable.chevron_down : R.drawable.chevron_right);
            }
            break;
        }
    }

    @Override
    public int getItemCount() {
        return this.mListItems.size();
    }

    @Override
    public long getItemId(int position)
    {
        ListItem item = mListItems.get(position);
        long id = ((item.group + 1 & LOWER_31BIT_MASK) << 32) | ((isGroup(item) ? 0 : item.item + 1) & LOWER_32BIT_MASK);
        return id;
    }

    public String getItem(int groupPosition, int childPosititon) {
        return this.mItems.get(this.mGroups.get(groupPosition))
                .get(childPosititon);
    }
}
