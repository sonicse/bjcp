package com.sonicse.bjcp;

import android.content.Intent;
import android.content.SharedPreferences;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by sonicse on 19.10.15.
 */
public class FavoritesFragment extends Fragment{

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private FavoritesAdapter mAdapter;
    private List<String> mData;
    private List<String> mTitles;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.list);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        loadFavorites();

        mRecyclerView.addItemDecoration(new ListDividerDecoration(getActivity()));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadFavorites();
    }

    public void loadFavorites() {
        Resources res = getResources();

        List<String> favorites = FavoritesStorage.getFavorites(getActivity());

        mData = new ArrayList<String>();
        mTitles = new ArrayList<String>();

        for (String child_id : favorites) {
            int iResourceId = getResources().getIdentifier(child_id + "_detail", "string", getActivity().getPackageName());

            if (iResourceId == 0) {
                continue;
            }

            String child_title = res.getString(res.getIdentifier(child_id, "string", getActivity().getPackageName()));
            mTitles.add(child_title);
            mData.add(child_id);
        }

        mAdapter = new FavoritesAdapter(mTitles);

        mAdapter.setOnClickListener(new FavoritesAdapter.OnClickListener() {
            @Override
            public void onItemClick(int position) {

                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("resourceId", mData.get(position) + "_detail");
                intent.putExtra("searchText", "");
                startActivity(intent);

                getActivity().overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
            }

            @Override
            public boolean onItemLongClick(int position) {
                FavoritesStorage.removeFavorite(getActivity(), mData.get(position));
                Toast.makeText(getActivity().getApplicationContext(), "\"" + mTitles.get(position) + "\" удален из Избранного", Toast.LENGTH_SHORT).show();

                return true;
            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }
}
