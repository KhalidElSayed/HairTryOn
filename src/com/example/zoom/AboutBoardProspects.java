package com.example.zoom;

import android.app.ActivityGroup;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

public class AboutBoardProspects extends ActivityGroup {
	/** Called when the activity is first created. */

	WebView web;
	ProgressBar progressBar;
	Context context;
	AppConfig config;
	String allurl, txtheader, txtheader1, txtheader2, txtheader3, txtheader4,
			txtheader5, txtheader6, txtheader7, txtheader8, txtheader9, txtheader10, txtheader11, txtheader12;
	TextView header;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);

		txtheader = getIntent().getExtras().getString("txtphoto");
		txtheader1 = getIntent().getExtras().getString("txtphoto1");
		txtheader2 = getIntent().getExtras().getString("txtphoto2");
		txtheader3 = getIntent().getExtras().getString("txtphoto3");
		txtheader4 = getIntent().getExtras().getString("txtphoto4");
		txtheader5 = getIntent().getExtras().getString("txtphoto5");
		txtheader6 = getIntent().getExtras().getString("txtphoto6");
		
//		contact us header start from here 

		txtheader7 = getIntent().getExtras().getString("txt1");
		txtheader8 = getIntent().getExtras().getString("txt2");
		txtheader9 = getIntent().getExtras().getString("txt3");
		txtheader10 = getIntent().getExtras().getString("txt4");
		txtheader11 = getIntent().getExtras().getString("txt5");
		txtheader12 = getIntent().getExtras().getString("txt6");
		
		context = getApplicationContext();
		config = new AppConfig(context);

		allurl = getIntent().getStringExtra("url");
		web = (WebView) findViewById(R.id.webview);
		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		header = (TextView) findViewById(R.id.txtheader);

		if (allurl.equals("Photo Contest")) {
			allurl = config.photocontent;
			header.setText(txtheader);

		} else if (allurl.equals("Shop")) {

			allurl = config.shop;
			header.setText(txtheader1);

		} else if (allurl.equals("Advice")) {

			allurl = config.advice;
			header.setText(txtheader2);
		} else if (allurl.equals("News")) {

			allurl = config.news;
			header.setText(txtheader3);
		} else if (allurl.equals("Offers")) {

			allurl = config.offers;
			header.setText(txtheader4);
		} else if (allurl.equals("Specialize")) {

			allurl = config.specialize;
			header.setText(txtheader5);
		} else if (allurl.equals("Contact")) {

			allurl = config.contactus;
			header.setText(txtheader6);
		}
//contact ====================================================us url call here
		
		else if (allurl.equals("Facebook")) {

			allurl = config.fb;
			header.setText(txtheader7);
		}
		
		else if (allurl.equals("Twitter")) {

			allurl = config.twitter;
			header.setText(txtheader8);
		}
		
		else if (allurl.equals("Youtube")) {

			allurl = config.youtube;
			header.setText(txtheader9);
		}
		
		else if (allurl.equals("Pintrest")) {

			allurl = config.pintrest;
			header.setText(txtheader10);
		}
		
		else if (allurl.equals("Blog")) {

			allurl = config.blog;
			header.setText(txtheader11);
		}
		else if (allurl.equals("Livechat")) {

			allurl = config.livechat;
			header.setText(txtheader12);
			
		}
		web.setWebViewClient(new myWebClient());
		web.getSettings().setJavaScriptEnabled(true);
		web.requestFocus(View.FOCUS_DOWN);
	    web.setOnTouchListener(new View.OnTouchListener() {
	        @Override
	        public boolean onTouch(View v, MotionEvent event) {
	            switch (event.getAction()) {
	                case MotionEvent.ACTION_DOWN:
	                case MotionEvent.ACTION_UP:
	                    if (!v.hasFocus()) {
	                        v.requestFocus();
	                    }
	                    break;
	            }
	            return false;
	        }
	    });
		web.loadUrl(allurl);
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

	// To handle "Back" key press event for WebView to go back to previous
	// screen.
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && web.canGoBack()) {
			web.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
