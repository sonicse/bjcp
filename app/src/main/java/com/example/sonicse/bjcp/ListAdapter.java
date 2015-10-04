package com.example.sonicse.bjcp;

import java.util.HashMap;
import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by sonicse on 16.09.15.
 */

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> mGroups;
    private HashMap<String, List<String>> mItems;
    private OnClickListener mOnClickListener;

    private static final int TYPE_GROUP = 1;
    private static final int TYPE_ITEM = 2;

    public interface OnClickListener {
        void onGroupClick(int position);
        void onItemClick(int position);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    public static class GroupViewHolder extends RecyclerView.ViewHolder
    {
        public TextView mTextView;

        public GroupViewHolder(View v) {
            super(v);
            mTextView = (TextView)v.findViewById(R.id.list_group_text_id);
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
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_ITEM : TYPE_GROUP;
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
                        mOnClickListener.onItemClick(holder.getPosition());
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
                        mOnClickListener.onGroupClick(holder.getPosition());
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

            case TYPE_ITEM:
                ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
                itemViewHolder.mTextView.setText(this.mGroups.get(position));
                break;

            case TYPE_GROUP:
                GroupViewHolder groupViewHolder = (GroupViewHolder) viewHolder;
                groupViewHolder.mTextView.setText(this.mGroups.get(position));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return this.mGroups.size();
    }
}

/*
public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> mGroups;
    private HashMap<String, List<String>> mItems;

    public ExpandableListAdapter(Context context, List<String> groups,
                                 HashMap<String, List<String>> childs) {
        this._context = context;
        this.mGroups = groups;
        this.mItems = childs;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.mItems.get(this.mGroups.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.listview_item, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.listview_item_text_id);

        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.mItems.get(this.mGroups.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.mGroups.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.mGroups.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.listview_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.listview_group_header_id);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
*/