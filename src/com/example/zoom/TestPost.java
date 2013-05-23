package com.example.zoom;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.BaseRequestListener;
import com.facebook.android.Facebook;
import com.facebook.android.SessionStore;

/**
 * This example shows how to post status to Facebook wall.
 * 
 * @author Lorensius W. L. T <lorenz@londatiga.net>
 * 
 * http://www.londatiga.net
 */
public class TestPost extends Activity{
	private Facebook mFacebook;
	private CheckBox mFacebookCb;
	private ProgressDialog mProgress;
	
	AppConfig config;
	Context context;
	
	
	private Handler mRunOnUi = new Handler();
	
	private static final String APP_ID = "366713270110009";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.post);
		
		context = getApplicationContext();
		config = new AppConfig(context);
		
		final EditText reviewEdit = (EditText) findViewById(R.id.revieew);
		mFacebookCb				  = (CheckBox) findViewById(R.id.cb_facebook);
		
		mProgress	= new ProgressDialog(this);
		
		mFacebook 	= new Facebook(APP_ID);
		
		SessionStore.restore(mFacebook, this);

		if (mFacebook.isSessionValid()) {
			mFacebookCb.setChecked(true);
				
			String name = SessionStore.getName(this);
			name = (name.equals("")) ? "Unknown" : name;
				
			mFacebookCb.setText("  Facebook  (" + name + ")");
		}
		
		((Button) findViewById(R.id.button1)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String review = reviewEdit.getText().toString();
				
				if (review.equals("")) return;
			
				if (mFacebookCb.isChecked()) postToFacebook(review);
			}
		});
	}
	
	private void postToFacebook(String review) {	
		mProgress.setMessage("Posting ...");
		mProgress.show();
		
		AsyncFacebookRunner mAsyncFbRunner = new AsyncFacebookRunner(mFacebook);
		
//		Bitmap bi = BitmapFactory.decodeFile(APP_FILE_PATH + "/"+filename+".jpg");
//	    //Bitmap bi = BitmapFactory.decodeResource(getResources(), R.drawable.icon);             
//	    ByteArrayOutputStream baos = new ByteArrayOutputStream();              
//	    bi.compress(Bitmap.CompressFormat.JPEG, 100, baos); 
	    
	    
		 byte[] data = null;
		 try {
		     FileInputStream fis = new FileInputStream(config.imageupload);
		     System.out.println("in data config>>>>>>>>>>>>>>>>>>>>>>>>"+config.imageupload);
		     Bitmap bi = BitmapFactory.decodeStream(fis);
		     ByteArrayOutputStream baos = new ByteArrayOutputStream();
		     bi.compress(Bitmap.CompressFormat.PNG, 100, baos);
		     data = baos.toByteArray(); 
		     System.out.println("in data>>>"+data);
		     System.out.println("in fis>>>"+fis);
		     
		  } catch (FileNotFoundException e) { 
		     e.printStackTrace();
		     Log.d("onCreate", "debug error  e = " + e.toString());
		  }
		 
		 
		Bundle params = new Bundle();
    		
		params.putString("message", review);
//		params.putString("name", "Dexter");
//		params.putString("caption", "londatiga.net");
//		params.putString("link", "http://www.londatiga.net");
//		params.putString("description", "Dexter, seven years old dachshund who loves to catch cats, eat carrot and krupuk");
//		
		 params.putString("method", "photos.upload");
		params.putByteArray("picture", data);

		
		mAsyncFbRunner.request(null, params, "POST", new WallPostListener());
	}

	private final class WallPostListener extends BaseRequestListener {
        public void onComplete(final String response) {
        	mRunOnUi.post(new Runnable() {
        		@Override
        		public void run() {
        			mProgress.cancel();
        			
        			Toast.makeText(TestPost.this, "Posted to Facebook", Toast.LENGTH_SHORT).show();
        		}
        	});
        }
    }
}