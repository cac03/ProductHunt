package com.caco3.producthunt.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.caco3.producthunt.R;
import com.caco3.producthunt.ui.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.caco3.producthunt.util.Preconditions.checkState;


public class WebViewActivity extends BaseActivity {
  private static final String URL_EXTRA = "url";
  @BindView(R.id.web_view_activity_web_view)
  WebView webView;

  public static Intent forUrl(Context context, String url) {
    return new Intent(context, WebViewActivity.class)
            .putExtra(URL_EXTRA, url);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_web_view);
    ButterKnife.bind(this);

    checkState(getIntent().hasExtra(URL_EXTRA), "No url provided. Did you use static forUrl method?");
    if (savedInstanceState == null) {
      initWebView();
    } else {
      webView.restoreState(savedInstanceState);
    }
  }

  @SuppressLint("SetJavaScriptEnabled")
  private void initWebView() {
    webView.setWebViewClient(new WebViewClient(){
      @Override
      public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        webView.loadUrl(request.getUrl().toString());
        return true;
      }
    });
    webView.setWebChromeClient(new WebChromeClient());
    webView.getSettings().setJavaScriptEnabled(true);
    webView.getSettings().setBuiltInZoomControls(true);
    webView.getSettings().setSupportZoom(true);
    webView.loadUrl(getIntent().getStringExtra(URL_EXTRA));
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    webView.saveState(outState);
  }
}
