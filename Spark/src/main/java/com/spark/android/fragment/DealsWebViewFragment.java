package com.spark.android.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebViewFragment;

import com.spark.android.R;
import com.spark.android.activity.MainActivity;
import com.spark.android.util.UrlFormatter;

/**
 * Created by danielclayton on 6/5/14.
 */
public class DealsWebViewFragment extends WebViewFragment {
    public static Fragment newInstance(String retailerName, String url) {
        DealsWebViewFragment fragment = new DealsWebViewFragment();
        Bundle args = new Bundle();
        args.putString(MainActivity.ARG_RETAILER_NAME, retailerName);
        args.putString(MainActivity.ARG_WEB_VIEW_URL, url);
        fragment.setArguments(args);

        return fragment;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        WebView webView = getWebView();
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getActivity().getString(R.string.progress_dialog_loading_your_deal_message));
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                getActivity().onBackPressed();
            }
        });

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressDialog.show();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!url.contains(UrlFormatter.getRetailerBaseUrl(getArguments().getString(MainActivity.ARG_RETAILER_NAME)))) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);

                    return true;
                }

                progressDialog.setMessage(getActivity().getString(R.string.progress_dialog_default_message));

                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (progressDialog.isShowing()) {
                    progressDialog.hide();
                }
            }
        });

        webView.loadUrl(getArguments().getString(MainActivity.ARG_WEB_VIEW_URL));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_open_in_browser) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(getArguments().getString(MainActivity.ARG_WEB_VIEW_URL)));
            startActivity(i);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        for (int i = 0; i < menu.size(); i++) {
            if (menu.getItem(i).getItemId() == R.id.action_open_in_browser) {
                menu.getItem(i).setVisible(true);
            }
        }
    }
}
