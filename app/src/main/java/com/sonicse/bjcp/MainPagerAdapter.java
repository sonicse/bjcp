package com.sonicse.bjcp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by sonicse on 19.10.15.
 */
public class MainPagerAdapter extends FragmentPagerAdapter {

    FavoritesFragment mFavoritesFragment;

    public MainPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new ListFragment();
                case 1:
                    return getFavoritesFragment();
                default:
                    throw new IllegalStateException();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Стили";
                case 1:
                    return "Избранное";
                default:
                    throw new IllegalStateException();
            }
        }

    public FavoritesFragment getFavoritesFragment() {
        if (mFavoritesFragment == null) {
            mFavoritesFragment = new FavoritesFragment();
        }
        return mFavoritesFragment;
    }
}
