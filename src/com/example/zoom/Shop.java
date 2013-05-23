package com.example.zoom;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class Shop extends Activity {

	WebView webView;
	ProgressBar progressBar;
	Context context;
	AppConfig config;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shopwebview);

		context = getApplicationContext();
		config = new AppConfig(context);

		 progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		 
		webView = (WebView) findViewById(R.id.webviewshop);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(new myWebClient());
		webView.loadUrl(config.shop);
	}

	public class myWebClient extends WebViewClient {
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			// TODO Auto-generated method stub
			super.onPageStarted(view, url, favicon);
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			// TODO Auto-generated method stub

			view.loadUrl(url);
			return true;
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			// TODO Auto-generated method stub
			super.onPageFinished(view, url);
			progressBar.setVisibility(View.GONE);
		}
	}
}
