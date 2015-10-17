package com.sonicse.bjcp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.sonicse.bjcp.R;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private Toolbar mToolbar;
    private Menu mMenu;
    private static final int MIN_SEARCH_STRING_LEN = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
    }

    private void initToolbar()
    {
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mMenu = menu;

        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.about:
                AboutDialog.show(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String keyword) {
        if (MIN_SEARCH_STRING_LEN  <= keyword.length()) {
            Intent intent = new Intent(this, SearchActivity.class);
            intent.putExtra("searchText", keyword);
            startActivity(intent);
        } else {
            Toast.makeText(this.getApplicationContext(), R.string.not_enough_search_string_len, Toast.LENGTH_SHORT).show();
        }

        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        // Associate searchable configuration with the SearchView
        MenuItem searchItem = mMenu.findItem(R.id.search);
        MenuItemCompat.collapseActionView(searchItem);
    }
}
