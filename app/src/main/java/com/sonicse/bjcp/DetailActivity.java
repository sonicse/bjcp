package com.sonicse.bjcp;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.sonicse.bjcp.R;

/**
 * Created by sonicse on 15.09.15.
 */

public class DetailActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initToolbar();
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
    }

    private void initToolbar()
    {
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        /*
        mToolbar.setTitle(R.string.app_name);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return false;
            }
        });
        */
        mToolbar.inflateMenu(R.menu.menu);

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (android.R.id.home): {
                onBackPressed();
                return true;
            }
            case (R.id.about): {
                AboutDialog.show(this);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}