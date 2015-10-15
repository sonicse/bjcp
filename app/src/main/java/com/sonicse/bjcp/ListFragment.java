package com.sonicse.bjcp;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sonicse.bjcp.R;

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
            public void onGroupClick(int group) {
            }

            public void onItemClick(int group, int item) {
                String str = mAdapter.getItem(group, item);

                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("resourceId", mData.get(str));
                startActivity(intent);

                getActivity().overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
            }
        });

        mRecyclerView.addItemDecoration(new ListDividerDecoration(getActivity()));
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }
}
