package com.example.sonicse.bjcp;

import android.app.Application;

import com.bettervectordrawable.VectorDrawableCompat;

/**
 * Created by sonicse on 20.09.15.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        VectorDrawableCompat.enableResourceInterceptionFor(getResources(),
                R.drawable.magnify,
                R.drawable.chevron_right,
                R.drawable.chevron_down,
                R.drawable.arrow_left);
    }
}