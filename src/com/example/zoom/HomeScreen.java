package com.example.zoom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class HomeScreen extends Activity implements OnClickListener {
	/** Called when the activity is first created. */
	ImageView after, calculator, photos, shop, advice, news, offers,specialize, contactus;
	RelativeLayout before,calculatorrel, photosrel, shoprel, advicerel, newsrel, offersrel,specializerel, contactusrel;

	public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newhomescreen);
 
       after = (ImageView) findViewById(R.id.img_before);
       before = (RelativeLayout) findViewById(R.id.rel_before);
       before.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent before = new Intent(getApplicationContext(),Capture.class);
			startActivity(before);
			}
		});
       calculatorrel = (RelativeLayout) findViewById(R.id.rel_calculator);
       calculatorrel.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent calculatorrel = new Intent(getApplicationContext(),Calculator.class);
			startActivity(calculatorrel);
		}
	});
       photosrel = (RelativeLayout) findViewById(R.id.rel_photo);
       photosrel.setOnClickListener(this);
       shoprel = (RelativeLayout) findViewById(R.id.rel_shop);
       shoprel.setOnClickListener(this);
       advicerel = (RelativeLayout) findViewById(R.id.rel_advice);
       advicerel.setOnClickListener(this);
//       newsrel = (RelativeLayout) findViewById(R.id.rel_news);
//       newsrel.setOnClickListener(this);
       offersrel = (RelativeLayout) findViewById(R.id.rel_offers);
       offersrel.setOnClickListener(this);
       
       specializerel = (RelativeLayout) findViewById(R.id.rel_socialize);
       specializerel.setOnClickListener(this);
       
       contactusrel = (RelativeLayout) findViewById(R.id.rel_contactus);
       calculator = (ImageView) findViewById(R.id.img_calculator);
       
       photos=(ImageView) findViewById(R.id.img_photo);
	   photos.setOnClickListener(this);
		
	   shop=(ImageView) findViewById(R.id.img_shop);
	   shop.setOnClickListener(this);
		
   	   advice=(ImageView) findViewById(R.id.img_advice);
	   advice.setOnClickListener(this);
		
		/*news=(ImageView) findViewById(R.id.rel_news);
		news.setOnClickListener(this);*/
		
		offers=(ImageView) findViewById(R.id.img_offers);
		offers.setOnClickListener(this);
		
		specialize=(ImageView) findViewById(R.id.img_socialize);
		specialize.setOnClickListener(this);
		
		/*contactus=(ImageView) findViewById(R.id.rel_contactus);
		contactus.setOnClickListener(this);*/
		
       calculator.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent calculatorimg = new Intent(getApplicationContext(),Calculator.class);
			startActivity(calculatorimg);
		}
	});
       after.setOnClickListener(new OnClickListener() 
       {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		Intent before = new Intent(getApplicationContext(),Capture.class);
		startActivity(before);
		}
	});
    }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.equals(photos)) {
			Intent web = new Intent(getParent(), AboutBoardProspects.class);
			web.putExtra("url", "Photo Contest");
			web.putExtra("txtphoto", "Photo Contest");
			
			WorkoutTabGroupActivity parantActivity = (WorkoutTabGroupActivity) getParent();
			parantActivity.startChildActivity("AboutBoardProspects", web);
		} 
		
		else if (v.equals(photosrel)) {
			Intent web = new Intent(getParent(), AboutBoardProspects.class);
			web.putExtra("url", "Photo Contest");
			web.putExtra("txtphoto", "Photo Contest");
			WorkoutTabGroupActivity parantActivity = (WorkoutTabGroupActivity) getParent();
			parantActivity.startChildActivity("AboutBoardProspects", web);
		}
		else if (v.equals(shop)) {
			Intent web = new Intent(getParent(), AboutBoardProspects.class);
			web.putExtra("url", "Shop");
			web.putExtra("txtphoto1", "Shop");
			WorkoutTabGroupActivity parantActivity = (WorkoutTabGroupActivity) getParent();
			parantActivity.startChildActivity("AboutBoardProspects", web);
		} 
		else if (v.equals(shoprel)) {
			Intent web = new Intent(getParent(), AboutBoardProspects.class);
			web.putExtra("url", "Shop");
			web.putExtra("txtphoto1", "Shop");
			WorkoutTabGroupActivity parantActivity = (WorkoutTabGroupActivity) getParent();
			parantActivity.startChildActivity("AboutBoardProspects", web);
		}
		else if (v.equals(advice)) {
			Intent web = new Intent(getParent(), AboutBoardProspects.class);
			web.putExtra("url", "Advice");
			web.putExtra("txtphoto2", "Most Popular Advice");
			WorkoutTabGroupActivity parantActivity = (WorkoutTabGroupActivity) getParent();
			parantActivity.startChildActivity("AboutBoardProspects", web);
		}
		else if (v.equals(advicerel)) {
			Intent web = new Intent(getParent(), AboutBoardProspects.class);
			web.putExtra("url", "Advice");
			web.putExtra("txtphoto2", "Most Popular Advice");
			WorkoutTabGroupActivity parantActivity = (WorkoutTabGroupActivity) getParent();
			parantActivity.startChildActivity("AboutBoardProspects", web);
		}
		else if (v.equals(offers)) {
			Intent web = new Intent(getParent(), AboutBoardProspects.class);
			web.putExtra("url", "Offers");
			web.putExtra("txtphoto4", "Mobile Offers");
			WorkoutTabGroupActivity parantActivity = (WorkoutTabGroupActivity) getParent();
			parantActivity.startChildActivity("AboutBoardProspects", web);
		} 
		else if (v.equals(offersrel)) {
			Intent web = new Intent(getParent(), AboutBoardProspects.class);
			web.putExtra("url", "Offers");
			web.putExtra("txtphoto4", "Mobile Offers");
			WorkoutTabGroupActivity parantActivity = (WorkoutTabGroupActivity) getParent();
			parantActivity.startChildActivity("AboutBoardProspects", web);
		}
		else if (v.equals(specialize)) {
			Intent web = new Intent(getParent(), AboutBoardProspects.class);
			web.putExtra("url", "Specialize");
			web.putExtra("txtphoto5", "Socialize");
			WorkoutTabGroupActivity parantActivity = (WorkoutTabGroupActivity) getParent();
			parantActivity.startChildActivity("AboutBoardProspects", web);
		}
		else if (v.equals(specializerel)) {
			Intent web = new Intent(getParent(), AboutBoardProspects.class);
			web.putExtra("url", "Specialize");
			web.putExtra("txtphoto5", "Socialize");
			WorkoutTabGroupActivity parantActivity = (WorkoutTabGroupActivity) getParent();
			parantActivity.startChildActivity("AboutBoardProspects", web);
		}
	}
}