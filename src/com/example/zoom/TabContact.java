package com.example.zoom;

import android.app.ActivityGroup;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableRow;
import android.widget.TextView;

public class TabContact extends ActivityGroup implements OnClickListener {

	TextView call, email;
	TableRow fb, twitter, pintrest, blog, youtube, livechat;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_contactlist);

		fb = (TableRow) findViewById(R.id.tbl_facebook);
		fb.setOnClickListener(this);

		twitter = (TableRow) findViewById(R.id.tbl_twitter);
		twitter.setOnClickListener(this);

		pintrest = (TableRow) findViewById(R.id.tbl_pinterest);
		pintrest.setOnClickListener(this);

		youtube = (TableRow) findViewById(R.id.tbl_youtube);
		youtube.setOnClickListener(this);

		blog = (TableRow) findViewById(R.id.tbl_blog);
		blog.setOnClickListener(this);

		livechat = (TableRow) findViewById(R.id.tbl_livechat);
		livechat.setOnClickListener(this);

		call = (TextView) findViewById(R.id.txt_call);
		call.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:888-424-7548"));
				startActivity(callIntent);
			}
		});
		email = (TextView) findViewById(R.id.txt_email);
		email.setText("info@donnabellahair.com");

		email.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String to = email.getText().toString();

				Intent email = new Intent(Intent.ACTION_SEND);
				email.putExtra(Intent.EXTRA_EMAIL, new String[] { to });

				email.setType("message/rfc822");
				startActivity(Intent.createChooser(email,
						"Choose an Email client :"));
			}
		});
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.equals(fb)) {
			Intent web = new Intent(getParent(), AboutBoardProspects.class);
			web.putExtra("url", "Facebook");
			web.putExtra("txt1", "Facebook");
			WorkoutTabGroupActivity parantActivity = (WorkoutTabGroupActivity) getParent();
			parantActivity.startChildActivity("AboutBoardProspects", web);
		} else if (v.equals(twitter)) {
			Intent web = new Intent(getParent(), AboutBoardProspects.class);
			web.putExtra("url", "Twitter");
			web.putExtra("txt2", "Twitter");
			WorkoutTabGroupActivity parantActivity = (WorkoutTabGroupActivity) getParent();
			parantActivity.startChildActivity("AboutBoardProspects", web);
		} else if (v.equals(youtube)) {
			Intent web = new Intent(getParent(), AboutBoardProspects.class);
			web.putExtra("url", "Youtube");
			web.putExtra("txt3", "Youtube");
			WorkoutTabGroupActivity parantActivity = (WorkoutTabGroupActivity) getParent();
			parantActivity.startChildActivity("AboutBoardProspects", web);
		} else if (v.equals(pintrest)) {
			Intent web = new Intent(getParent(), AboutBoardProspects.class);
			web.putExtra("url", "Pintrest");
			web.putExtra("txt4", "Pintrest");
			WorkoutTabGroupActivity parantActivity = (WorkoutTabGroupActivity) getParent();
			parantActivity.startChildActivity("AboutBoardProspects", web);
		} else if (v.equals(blog)) {
			Intent web = new Intent(getParent(), AboutBoardProspects.class);
			web.putExtra("url", "Blog");
			web.putExtra("txt5", "Blog");
			WorkoutTabGroupActivity parantActivity = (WorkoutTabGroupActivity) getParent();
			parantActivity.startChildActivity("AboutBoardProspects", web);
		}

		else if (v.equals(livechat)) {
			// Intent web = new Intent(getApplicationContext(),ChangePic.class);
			Intent web = new Intent(getParent(), AboutBoardProspects.class);
			web.putExtra("url", "Livechat");
			web.putExtra("txt6", "Livechat");

			WorkoutTabGroupActivity parantActivity = (WorkoutTabGroupActivity) getParent();
			parantActivity.startChildActivity("AboutBoardProspects", web);
			// startActivity(web);
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  
	{
	    if (  Integer.valueOf(android.os.Build.VERSION.SDK) < 7 //Instead use android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ECLAIR
	            && keyCode == KeyEvent.KEYCODE_BACK
	            && event.getRepeatCount() == 0) {
	        // Take care of calling this method on earlier versions of
	        // the platform where it doesn't exist.
	        onBackPressed();
	    }

	    return super.onKeyDown(keyCode, event);
	}
	@Override
	public void onBackPressed() {
	    // This will be called either automatically for you on 2.0
	    // or later, or by the code above on earlier versions of the
	    // platform.
		finish();
	    return;
	}
}