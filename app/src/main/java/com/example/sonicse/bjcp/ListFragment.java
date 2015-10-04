package com.example.sonicse.bjcp;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sonicse on 15.09.15.
 */
public class ListFragment extends Fragment
{
    HashMap<String, String> mData;

    private RecyclerView mRecyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        Resources res = getResources();

        String[] group_ids = res.getStringArray(R.array.group_ids);

        List<String> groups = new ArrayList<String>();
        HashMap<String, List<String>> items = new HashMap<String, List<String>>();
        mData = new HashMap<String, String>();

        for (String group_id : group_ids)
        {
            String group_title = res.getString(res.getIdentifier(group_id, "string", getActivity().getPackageName()));
            groups.add(group_title);

            List<String> childs = new ArrayList<String>();
            String[] child_ids = res.getStringArray(res.getIdentifier(group_id + "_group", "array", getActivity().getPackageName()));

            for (String child_id : child_ids)
            {
                String child_title = res.getString(res.getIdentifier(child_id, "string", getActivity().getPackageName()));
                childs.add(child_title);

                mData.put(child_title, child_id + "_detail");
            }

            items.put(group_title, childs);
        }

        mAdapter = new ListAdapter(groups, items);

        mAdapter.setOnClickListener(new ListAdapter.OnClickListener() {
            @Override
            public void onGroupClick(int position) {
                //String item = (String)mAdapter.getChild(groupPosition, childPosition);

                Intent intent = new Intent(getActivity(), DetailActivity.class);
                //intent.putExtra("resourceId", mData.get(item));
                intent.putExtra("resourceId", "type_1A_detail");
                startActivity(intent);
            }

            public void onItemClick(int position) {
                //String item = (String)mAdapter.getChild(groupPosition, childPosition);

                Intent intent = new Intent(getActivity(), DetailActivity.class);
                //intent.putExtra("resourceId", mData.get(item));
                intent.putExtra("resourceId", "type_1B_detail");
                startActivity(intent);
            }
        });

        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    /*public boolean onChildClick(ExpandableListView parent, View v,
                                int groupPosition, int childPosition, long id)
    {
        String item = (String)_adapter.getChild(groupPosition, childPosition);

        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("resourceId", mData.get(item));
        startActivity(intent);

        return true;
    }
    */
}
