package com.example.zoom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableRow;

public class TabMore extends Activity implements OnClickListener {

	TableRow signin,livechat;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabmore_list);

		livechat = (TableRow) findViewById(R.id.tbl_more_livechat);
		livechat.setOnClickListener(this);
		
		signin = (TableRow) findViewById(R.id.tbl_signin);
		signin.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
			Intent sign = new Intent(getApplicationContext(),FacebookLogin.class);
			startActivity(sign);
			}
		});
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.equals(livechat))
		{
			//Intent web = new Intent(getApplicationContext(),ChangePic.class);
			Intent web = new Intent(getParent(), AboutBoardProspects.class);
			web.putExtra("url", "Livechat");
			web.putExtra("txt6", "Livechat");
			
			WorkoutTabGroupActivity parantActivity = (WorkoutTabGroupActivity) getParent();
			parantActivity.startChildActivity("AboutBoardProspects", web);
			//startActivity(web);
		}
	}

}