package com.example.zoom;
 
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TabHost;
 
public class TabSample extends TabActivity {
    /** Called when the activity is first created. */

    ImageView tabcamera;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
 
        tabcamera=(ImageView) findViewById(R.id.tab_camera);
        tabcamera.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent cameratab = new Intent(getApplicationContext(),Capture.class);
				startActivity(cameratab);	
			}
		});
       /* *//** TabHost will have Tabs *//*
        TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);
        *//** TabSpec used to create a new tab.
         * By using TabSpec only we can able to setContent to the tab.
         * By using TabSpec setIndicator() we can set name to tab. *//*
 
        *//** tid1 is firstTabSpec Id. Its used to access outside. *//*
        TabSpec firstTabSpec = tabHost.newTabSpec("tab_id1");
        TabSpec secondTabSpec = tabHost.newTabSpec("tab_id2");
        TabSpec thirdTabSpec = tabHost.newTabSpec("tab_id3");
 
        *//** TabSpec setIndicator() is used to set name for the tab. *//*
        *//** TabSpec setContent() is used to set content for a particular tab. *//*
        firstTabSpec.setIndicator("Celebrety").setContent(new Intent(this,FirstTab.class));
        secondTabSpec.setIndicator("Style ").setContent(new Intent(this,Style.class));
        thirdTabSpec.setIndicator("Favorate").setContent(new Intent(this,ThirdTab.class));
 
        *//** Add tabSpec to the TabHost to display. *//*
        tabHost.addTab(firstTabSpec);
        tabHost.addTab(secondTabSpec);
        tabHost.addTab(thirdTabSpec);*/

        Resources res = getResources();
        Intent i = new Intent(this,Celebrity.class);
        Intent i1 = new Intent(this,StyleList.class);
        Intent i2 = new Intent(this,ColorHair.class);
         
        TabHost mTabHst = getTabHost();
        
        mTabHst.addTab(mTabHst.newTabSpec("tab_test1").setIndicator("Celebs",res.getDrawable(R.drawable.tabexplore)).setContent(i));
        mTabHst.addTab(mTabHst.newTabSpec("tab_test2").setIndicator("Style",res.getDrawable(R.drawable.tabexplore)).setContent(i1));
        mTabHst.addTab(mTabHst.newTabSpec("tab_test3").setIndicator("Favorites",res.getDrawable(R.drawable.tabexplore)).setContent(i2));
        
        mTabHst.setCurrentTab(0);
    }
}