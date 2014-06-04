package com.spark.android.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

import com.spark.android.R;
import com.spark.android.fragment.AllDealsFragment;
import com.spark.android.fragment.NavigationDrawerFragment;
import com.spark.android.fragment.PlaceholderFragment;

public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

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
        Fragment fragment;
        switch(position) {
            case 0:
                fragment = AllDealsFragment.newInstance();
                break;
            case 1:
                fragment = PlaceholderFragment.newInstance(position + 1);
                break;
            case 2:
                fragment = PlaceholderFragment.newInstance(position + 1);
                break;
            default:
                fragment = PlaceholderFragment.newInstance(position + 1);
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

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_all_deals);
                break;
            case 2:
                mTitle = getString(R.string.title_my_deals);
                break;
            case 3:
                mTitle = getString(R.string.title_edit_my_deals);
                break;
        }
    }

    public void restoreActionBar() {
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

    public static String getRetailerUrl(String retailer) {
        String url;

        switch(retailer) {
            case "Ace Hardware":
                url = "https://acehardwareapi.uat.bbhosted.com";
                break;
            case "American Eagle":
                url = "https://aeapi.uat.bbhosted.com";
                break;
            case "Vitacost":
                url = "https://vitacostapi.uat.bbhosted.com";
                break;
            default:
                url = "https://vitacostapi.uat.bbhosted.com";
                break;
        }

        return url;
    }
}
