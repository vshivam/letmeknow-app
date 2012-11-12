package com.android.letmeknow;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MainActivity extends TabActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        TabHost tabHost = getTabHost();
        TabSpec searchspec = tabHost.newTabSpec("search");
        // setting Title and Icon for the Tab
        searchspec.setIndicator(null, getResources().getDrawable(R.layout.search_icon));
        Intent searchintent = new Intent(this, search_tab.class);
        searchspec.setContent(searchintent);
        
        TabSpec categoriesspec = tabHost.newTabSpec("Songs");
        categoriesspec.setIndicator(null, getResources().getDrawable(R.layout.cat_icon));
        Intent catIntent = new Intent(this, categories_tab.class);
        categoriesspec.setContent(catIntent);
        
        tabHost.addTab(categoriesspec); // Adding categories tab
        tabHost.addTab(searchspec); // Adding search tab
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
