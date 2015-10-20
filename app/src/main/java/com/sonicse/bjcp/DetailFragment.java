package com.sonicse.bjcp;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by sonicse on 15.09.15.
 */
public class DetailFragment extends Fragment {

    TextView mTextView;
    TextView mSrmTextView;
    ImageView mImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        mTextView = (TextView) view.findViewById(R.id.text_view);
        mSrmTextView = (TextView) view.findViewById(R.id.srm_title);
        mImageView = (ImageView) view.findViewById(R.id.srm_image);

        Intent intent = getActivity().getIntent();
        String resourceId = intent.getStringExtra("resourceId");
        String searchText = intent.getStringExtra("searchText");

        setResource(resourceId, searchText);

        return view;
    }

    public void setResource(String resourceId, String searchText)
    {
        Resources res = getResources();
        int iResourceId = res.getIdentifier(resourceId, "string", getActivity().getPackageName());

        if (iResourceId == 0) {
            iResourceId = res.getIdentifier("resource_not_found", "string", getActivity().getPackageName());
        }

        String text = getString(iResourceId);
        Spanned spannedText = Html.fromHtml(highlightText(text, searchText));

        mTextView.setText(spannedText);

        srmImages(resourceId);
    }


    private static String highlightText(String text, String query) {

        if (query.isEmpty()) {
            return text;
        }

        StringBuilder formatted = new StringBuilder();
        String subString = "";
        int queryLength = query.length();
        int i = 0;

        while (i < text.length()) {
            if ((i + queryLength) < text.length()) {
                subString = text.substring(i, i + queryLength);
            }

            if (((i + queryLength) < text.length()) && query.equalsIgnoreCase(subString)) {
                formatted.append("<font color='red'>");
                formatted.append(subString);
                formatted.append("</font>");
                i += (queryLength - 1);
            }
            else {
                formatted.append(text.charAt(i));
            }
            i++;
        }

        return formatted.toString();
    }

    private void srmImages(String resourceId) {

        Resources res = getResources();
        int iResourceId = res.getIdentifier(resourceId + "_srm", "array", getActivity().getPackageName());

        if (iResourceId == 0) {
            setSrmViewVisible(false);
            //Toast.makeText(getActivity().getApplicationContext(), R.string.srm_not_found, Toast.LENGTH_SHORT).show();
            return;
        }

        int[] srm_min_max = res.getIntArray(iResourceId);
        if (srm_min_max.length != 2) {
            setSrmViewVisible(false);
            Toast.makeText(getActivity().getApplicationContext(), R.string.srm_not_found, Toast.LENGTH_SHORT).show();
        }

        int srm_min = srm_min_max[0];
        int srm_max = srm_min_max[1];

        int[] colors = new int[srm_max-srm_min+1];

        for (int srm_idx = srm_min; srm_idx <= srm_max; ++srm_idx) {
            int srmId = res.getIdentifier("srm_"+srm_idx, "color", getActivity().getPackageName());
            int color = res.getColor(srmId);
            colors[srm_idx-srm_min] = color;
        }

        GradientDrawable g = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors);
        mImageView.setImageDrawable(g);

        mSrmTextView.setText("Цвет SRM (" + srm_min + " - " + srm_max +"):");
    }

    private void setSrmViewVisible(boolean visible)
    {
        if (visible) {
            mSrmTextView.setVisibility(View.VISIBLE);
            mImageView.setVisibility(View.VISIBLE);
        }
        else {
            mSrmTextView.setVisibility(View.GONE);
            mImageView.setVisibility(View.GONE);
        }
    }
}