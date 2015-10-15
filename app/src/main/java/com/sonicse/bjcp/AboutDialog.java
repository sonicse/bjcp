package com.sonicse.bjcp;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.sonicse.bjcp.R;

/**
 * Created by sonicse on 14.10.15.
 */
public class AboutDialog {
    static void show(Context context) {
        // Inflate the about message contents
        LayoutInflater inflater = LayoutInflater.from(context);
        View messageView = inflater.inflate(R.layout.about, null, false);

        // When linking text, force to always use default color. This works
        // around a pressed color state bug.
        /*TextView textView = (TextView) messageView.findViewById(R.id.about_credits);
        int defaultColor = textView.getTextColors().getDefaultColor();
        textView.setTextColor(defaultColor);
        */

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //builder.setIcon(R.drawable.app_icon);
        builder.setTitle(R.string.app_name);
        builder.setView(messageView);
        builder.create();
        builder.show();
    }
}
