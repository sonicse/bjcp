package com.sonicse.bjcp;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sonicse.bjcp.R;

/**
 * Created by sonicse on 15.09.15.
 */
public class DetailFragment extends Fragment {

    TextView _textView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        _textView = (TextView) view.findViewById(R.id.text_view);

        Intent intent = getActivity().getIntent();
        String resourceId = intent.getStringExtra("resourceId");

        setResource(resourceId);

        return view;
    }

    public void setResource(String resourceId)
    {
        int iResourceId = getResources().getIdentifier(resourceId, "string", getActivity().getPackageName());

        if (iResourceId == 0)
        {
            iResourceId = getResources().getIdentifier("resource_not_found", "string", getActivity().getPackageName());
        }

        String text = getString(iResourceId);

        Spanned spannedText = Html.fromHtml(text/*, htmlImageGetter, htmlTagHandler*/);
        //Spannable reversedText = revertSpanned(spannedText);

        _textView.setText(spannedText);
    }

    /*
    final Spannable revertSpanned(Spanned stext)
    {
        Object[] spans = stext.getSpans(0, stext.length(), Object.class);
        Spannable ret = Spannable.Factory.getInstance().newSpannable(stext.toString());
        if (spans != null && spans.length > 0) {
            for(int i = spans.length - 1; i >= 0; --i) {
                ret.setSpan(spans[i], stext.getSpanStart(spans[i]), stext.getSpanEnd(spans[i]), stext.getSpanFlags(spans[i]));
            }
        }

        return ret;
    }

    Html.ImageGetter htmlImageGetter = new Html.ImageGetter() {
        public Drawable getDrawable(String source) {
            int resId = getResources().getIdentifier(source, "drawable", getActivity().getPackageName());
            Drawable ret = getActivity().getResources().getDrawable(resId);
            ret.setBounds(0, 0, ret.getIntrinsicWidth(), ret.getIntrinsicHeight());
            return ret;
        }
    };

    Html.TagHandler htmlTagHandler = new Html.TagHandler() {
        public void handleTag(boolean opening, String tag, Editable output,	XMLReader xmlReader) {
            Object span = null;
            if (tag.startsWith("article_")) span = new ArticleSpan(MainActivity.this, tag);
            else if ("title".equalsIgnoreCase(tag)) span = new AppearanceSpan(0xffff2020, AppearanceSpan.NONE, 20, true, true, false, false);
            else if (tag.startsWith("color_")) span = new ParameterizedSpan(tag.substring(6));
            if (span != null) processSpan(opening, output, span);
        }
    };

    void processSpan(boolean opening, Editable output, Object span) {
        int len = output.length();
        if (opening) {
            output.setSpan(span, len, len, Spannable.SPAN_MARK_MARK);
        } else {
            Object[] objs = output.getSpans(0, len, span.getClass());
            int where = len;
            if (objs.length > 0) {
                for(int i = objs.length - 1; i >= 0; --i) {
                    if (output.getSpanFlags(objs[i]) == Spannable.SPAN_MARK_MARK) {
                        where = output.getSpanStart(objs[i]);
                        output.removeSpan(objs[i]);
                        break;
                    }
                }
            }

            if (where != len) {
                output.setSpan(span, where, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }
    */
}