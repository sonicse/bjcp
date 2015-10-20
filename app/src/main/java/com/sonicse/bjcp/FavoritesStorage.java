package com.sonicse.bjcp;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sonicse on 19.10.15.
 */
public class FavoritesStorage {
    private static final String STORAGE_NAME = "Storage";
    private static final String KEY_NAME = "favorites";

    static List<String> getFavorites(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(STORAGE_NAME, context.MODE_PRIVATE);

        String favoritesData = preferences.getString(KEY_NAME, "");

        if (favoritesData.isEmpty())
        {
            return new ArrayList<String>();
        }

        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();
        String[] list = gson.fromJson(favoritesData, String[].class);

        return new ArrayList<String>(Arrays.asList(list));
    }

    static void addFavorite(Context context, String favorite)
    {
        List<String> list = getFavorites(context);
        if (!list.contains(favorite))
        {
            list.add(0, favorite);
        }

        saveFavorites(context, list);
    }

    static void removeFavorite(Context context, String favorite)
    {
        List<String> list = getFavorites(context);
        if (list.contains(favorite))
        {
            list.remove(favorite);
        }

        saveFavorites(context, list);
    }

    static void saveFavorites(Context context, List<String> list) {
        SharedPreferences preferences = context.getSharedPreferences(STORAGE_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.remove(KEY_NAME);

        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();
        String value = gson.toJson(list);
        editor.putString(KEY_NAME, value);
        editor.commit();
    }
}
