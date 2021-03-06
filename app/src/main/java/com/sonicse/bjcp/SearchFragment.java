package com.sonicse.bjcp;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sonicse on 17.10.15.
 */
public class SearchFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private SearchAdapter mAdapter;
    private List<String> mData;
    private List<String> mTitles;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Intent intent = getActivity().getIntent();
        final String searchText = intent.getStringExtra("searchText");

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.list);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        Resources res = getResources();

        String[] group_ids = res.getStringArray(R.array.group_ids);

        mData = new ArrayList<String>();
        mTitles = new ArrayList<String>();

        for (String group_id : group_ids)
        {
            String[] child_ids = res.getStringArray(res.getIdentifier(group_id + "_group", "array", getActivity().getPackageName()));

            for (String child_id : child_ids)
            {
                int iResourceId = getResources().getIdentifier(child_id + "_detail", "string", getActivity().getPackageName());

                if (iResourceId == 0)
                {
                    continue;
                }

                String text = getString(iResourceId);

                if (text.toLowerCase().indexOf(searchText.toLowerCase()) > -1) {
                    String child_title = res.getString(res.getIdentifier(child_id, "string", getActivity().getPackageName()));
                    mTitles.add(child_title);
                    mData.add(child_id);
                }
            }
        }

        mAdapter = new SearchAdapter(mTitles);

        mAdapter.setOnClickListener(new SearchAdapter.OnClickListener() {
            @Override
            public void onItemClick(int position) {

                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("resourceId", mData.get(position) + "_detail");
                intent.putExtra("searchText", searchText);
                startActivity(intent);

                getActivity().overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
            }

            public boolean onItemLongClick(int position) {
                String str = mTitles.get(position);
                FavoritesStorage.addFavorite(getActivity(), mData.get(position));
                Toast.makeText(getActivity().getApplicationContext(), "\"" + str + "\" добавлен в Избранное", Toast.LENGTH_SHORT).show();

                return true;
            }
        });

        mRecyclerView.addItemDecoration(new ListDividerDecoration(getActivity()));
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }
}
