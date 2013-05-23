package com.example.zoom;

import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.SessionStore;
import com.facebook.android.Facebook.DialogListener;

public class Setting extends Activity implements OnClickListener{
	
	Button taaz,facebook;
	ImageView back;
	LinearLayout calculator,photos,shop,advice,news,offers,specialize,contactus;
	TextView photo,fblogin;
	Context context;
	AppConfig config;
	
	private Facebook mFacebook;
	private Button mFacebookBtn;
	private ProgressDialog mProgress;
	
	private static final String[] PERMISSIONS = new String[] {"publish_stream", "read_stream", "offline_access"};
	
	private static final String APP_ID = "366713270110009";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		
		context= getApplicationContext();
		config= new AppConfig(context);
		
		 mFacebookBtn	= (Button) findViewById(R.id.btn_fb_login);
	        
	        mProgress		= new ProgressDialog(this);
	        mFacebook		= new Facebook(APP_ID);
	        
	        SessionStore.restore(mFacebook, this);
	        
	        if (mFacebook.isSessionValid()) {
				//mFacebookBtn.setChecked(true);
				
				String name = SessionStore.getName(this);
				name		= (name.equals("")) ? "Unknown" : name;
				
				//mFacebookBtn.setText("  Facebook (" + name + ")");
				
				//config.facebookname=name;
				
				//System.out.println("in test class"+name);
				//mFacebookBtn.setTextColor(Color.WHITE);
			}
	        
	        mFacebookBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					onFacebookClick();
				}
			});
	        
	       /* ((Button) findViewById(R.id.button1)).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					startActivity(new Intent(Setting.this, TestPost.class));
				}
			});*/
		
	        calculator=(LinearLayout) findViewById(R.id.rel_pricecalculator);
	        calculator.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent calculator = new Intent(getApplicationContext(),Calculator.class);	
					startActivity(calculator);
				}
			});
		
		fblogin=(TextView) findViewById(R.id.txt_fblogin);
		fblogin.setText(" Login To Facebook (" + config.facebookname + ")");
		
		photos=(LinearLayout) findViewById(R.id.rel_photo);
		photos.setOnClickListener(this);
		
		shop=(LinearLayout) findViewById(R.id.rel_shop);
		shop.setOnClickListener(this);
		
		advice=(LinearLayout) findViewById(R.id.rel_advice);
		advice.setOnClickListener(this);
		
		news=(LinearLayout) findViewById(R.id.rel_news);
		news.setOnClickListener(this);
		
		offers=(LinearLayout) findViewById(R.id.rel_offers);
		offers.setOnClickListener(this);
		
		specialize=(LinearLayout) findViewById(R.id.rel_spcialize);
		specialize.setOnClickListener(this);
		
		contactus=(LinearLayout) findViewById(R.id.rel_contactus);
		contactus.setOnClickListener(this);
		
		taaz=(Button) findViewById(R.id.btn_taaz_login);
		//facebook=(Button) findViewById(R.id.btn_fb_login);
		back = (ImageView)findViewById(R.id.btn_back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				finish();
			}
		});
		taaz.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			Intent taaz = new Intent(getApplicationContext(),FacebookLogin.class);	
			startActivity(taaz);
			}
		});
		
		/*facebook.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent fb = new Intent(getApplicationContext(),TestConnect.class);	
				startActivity(fb);
			}
		});*/
	}
	  private void onFacebookClick() {
			if (mFacebook.isSessionValid()) {
				final AlertDialog.Builder builder = new AlertDialog.Builder(this);
				
				builder.setMessage("Delete current Facebook connection?")
				       .setCancelable(false)
				       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	   fbLogout();
				           }
				       })
				       .setNegativeButton("No", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				                dialog.cancel();
				           }
				       });
				
				final AlertDialog alert = builder.create();
				
				alert.show();
			} else {
				//mFacebookBtn.setChecked(false);
				
				mFacebook.authorize(this, PERMISSIONS, -1, new FbLoginDialogListener());
			}
		}
	  private final class FbLoginDialogListener implements DialogListener {
	        public void onComplete(Bundle values) {
	            SessionStore.save(mFacebook, Setting.this);
	           
	            fblogin.setText("  Facebook (No Name)");
	           // mFacebookBtn.setChecked(true);
				//mFacebookBtn.setTextColor(Color.WHITE);
				 
	            getFbName();
	        }
	        private void getFbName() {
	    		mProgress.setMessage("Finalizing ...");
	    		mProgress.show();
	    		
	    		new Thread() {
	    			@Override
	    			public void run() {
	    		        String name = "";
	    		        int what = 1;
	    		        
	    		        try {
	    		        	String me = mFacebook.request("me");
	    		        	
	    		        	JSONObject jsonObj = (JSONObject) new JSONTokener(me).nextValue();
	    		        	name = jsonObj.getString("name");
	    		        	what = 0;
	    		        } catch (Exception ex) {
	    		        	ex.printStackTrace();
	    		        }
	    		        
	    		        mFbHandler.sendMessage(mFbHandler.obtainMessage(what, name));
	    			}
	    		}.start();
	    	}
	        private Handler mFbHandler = new Handler() {
	    		@Override
	    		public void handleMessage(Message msg) {
	    			mProgress.dismiss();
	    			
	    			if (msg.what == 0) {
	    				String username = (String) msg.obj;
	    		        username = (username.equals("")) ? "No Name" : username;
	    		            
	    		        SessionStore.saveName(username, Setting.this);
	    		        
	    		        fblogin.setText("  Facebook (" + username + ")");
	    		         
	    		        Toast.makeText(Setting.this, "Connected to Facebook as " + username, Toast.LENGTH_SHORT).show();
	    		        
	    		        Intent fblogin = new Intent(getApplicationContext(),Capture.class);
	    		        startActivity(fblogin);
	    			} else {
	    				Toast.makeText(Setting.this, "Connected to Facebook", Toast.LENGTH_SHORT).show();
	    			}
	    		}
	    	};
	    	
	        public void onFacebookError(FacebookError error) {
	           Toast.makeText(Setting.this, "Facebook connection failed", Toast.LENGTH_SHORT).show();
	           
	           //mFacebookBtn.setChecked(false);
	        }
	        
	        public void onError(DialogError error) {
	        	Toast.makeText(Setting.this, "Facebook connection failed", Toast.LENGTH_SHORT).show(); 
	        	
	        	//mFacebookBtn.setChecked(false);
	        }

	        public void onCancel() {
	        	//mFacebookBtn.setChecked(false);
	        }
	    }
	  
	  private void fbLogout() {
			mProgress.setMessage("Disconnecting from Facebook");
			mProgress.show();
				
			new Thread() {
				@Override
				public void run() {
					SessionStore.clear(Setting.this);
			        	   
					int what = 1;
						
			        try {
			        	mFacebook.logout(Setting.this);
			        		 
			        	what = 0;
			        } catch (Exception ex) {
			        	ex.printStackTrace();
			        }
			        	
			        mHandler.sendMessage(mHandler.obtainMessage(what));
				}
			}.start();
		}
	  private Handler mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				mProgress.dismiss();
				
				if (msg.what == 1) {
					Toast.makeText(Setting.this, "Facebook logout failed", Toast.LENGTH_SHORT).show();
				} else {
					//mFacebookBtn.setChecked(false);
		        	fblogin.setText("  Facebook (Not connected)");
		        	//mFacebookBtn.setTextColor(Color.GRAY);
		        	   
					Toast.makeText(Setting.this, "Disconnected from Facebook", Toast.LENGTH_SHORT).show();
				}
			}
		};

	  @Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.equals(photos)) 
		{
			Intent web = new Intent(getApplicationContext(),AboutBoardProspects.class);
			web.putExtra("url", "Photo Contest");
			web.putExtra("txtphoto", "Photo Contest");
			startActivity(web);
		}
		else if (v.equals(shop))
		{
			Intent web = new Intent(getApplicationContext(),AboutBoardProspects.class);
			web.putExtra("url", "Shop");
			web.putExtra("txtphoto1", "Shop");
			startActivity(web);
		}
		else if (v.equals(advice))
		{
			Intent web = new Intent(getApplicationContext(),AboutBoardProspects.class);
			web.putExtra("url", "Advice");
			web.putExtra("txtphoto2", "Most Popular Advice");
			startActivity(web);
		} 
		else if (v.equals(news))
		{
			Intent web = new Intent(getApplicationContext(),AboutBoardProspects.class);
			web.putExtra("url", "News");
			web.putExtra("txtphoto3", "What's New/Recent News");
			startActivity(web);
		} 
		else if (v.equals(offers))
		{
			Intent web = new Intent(getApplicationContext(),AboutBoardProspects.class);
			web.putExtra("url", "Offers");
			web.putExtra("txtphoto4", "Mobile Offers");
			startActivity(web);
		} 
		else if (v.equals(specialize))
		{
			Intent web = new Intent(getApplicationContext(),AboutBoardProspects.class);
			web.putExtra("url", "Specialize");
			web.putExtra("txtphoto5", "Socialize");
			startActivity(web);
		} 
		else if (v.equals(contactus))
		{
			Intent web = new Intent(getApplicationContext(),AboutBoardProspects.class);
			web.putExtra("url", "Contact");
			web.putExtra("txtphoto6", "Contact Us");
			startActivity(web);
		} 
	}
}
