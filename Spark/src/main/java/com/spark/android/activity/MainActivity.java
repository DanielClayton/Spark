package com.spark.android.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.spark.android.R;
import com.spark.android.fragment.AllDealsLandingFragment;
import com.spark.android.fragment.DealsWebViewFragment;
import com.spark.android.fragment.MyDealsLandingFragment;
import com.spark.android.fragment.NavigationDrawerFragment;

public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    public static final String ARG_PAGE_DATA = "page_data";
    public static final String ARG_PAGE_TYPE = "page_type";
    public static final String ARG_RETAILER_NAME = "retailer_name";
    public static final String ARG_CATEGORIES_PAGE = "retailer_home_page";
    public static final String ARG_WEB_VIEW_URL = "web_view_url";
    public static final String ARG_SAVED_USER_RETAILERS = "saved_user_retailers";
    private static Gson sGson = null;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        Fragment fragment = new Fragment();
        switch (position) {
            case 0:
                fragment = AllDealsLandingFragment.newInstance();
                break;
            case 1:
                fragment = MyDealsLandingFragment.newInstance();
                break;
        }

        if (fragment != null) {
            // update the main content by replacing fragments
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        }
    }

    void restoreActionBar() {
        ActionBar actionBar = getActionBar();

        if (actionBar != null) {
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(mTitle);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();

            return true;
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onBackPressed() {
        if (getFragmentManager().findFragmentByTag("webView") != null
                && ((DealsWebViewFragment) getFragmentManager().findFragmentByTag("webView")).getWebView().canGoBack()) {
            ((DealsWebViewFragment) getFragmentManager().findFragmentByTag("webView")).getWebView().goBack();
        } else {
            super.onBackPressed();
        }
    }

    public static Gson getGsonInstance() {
        if (sGson == null) {
            sGson = new Gson();
        }

        return sGson;
    }
}
