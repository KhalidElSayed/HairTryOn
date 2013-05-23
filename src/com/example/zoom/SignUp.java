package com.example.zoom;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends Activity implements OnClickListener {

	EditText firstname, lastname, email, password, conformpassword;

	String userdata, reqData, strfirst, strlast, stremail, strpassword,
			strconfirmpass, errormessages = "", passwordaboutuser,
			emailvalidate;
	Button login;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);

		if (isOnline()) {
		   // new Task().execute();
			System.out.println("work fine");
		 } else {
		     Toast.makeText(this, "There seems to be no internet access, please try again later!", Toast.LENGTH_LONG).show();
		 }
		
		firstname = (EditText) findViewById(R.id.edt_firstname);
		lastname = (EditText) findViewById(R.id.edt_lastname);
		email = (EditText) findViewById(R.id.edt_email);
		password = (EditText) findViewById(R.id.edt_password);
		conformpassword = (EditText) findViewById(R.id.edt_confirm_password);

		login = (Button) findViewById(R.id.Login);
		login.setOnClickListener(this);
		
	}
	public boolean isOnline() {
	    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	        return false;
	 }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		strfirst = firstname.getText().toString();
		strlast = lastname.getText().toString();
		stremail = email.getText().toString();
		strpassword = password.getText().toString();
		strconfirmpass = conformpassword.getText().toString();

		if (v == login) {

			errormessages = "";
			validateControls();
		}
	}
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0: {
				boolean flag = ParseJsonResponse(reqData);
				if (flag == true) 
				{
					Intent cpature = new Intent(getApplicationContext(),Capture.class);
					startActivity(cpature);
				}

				// Intent intent_validation_alert = new
				// Intent(SignUp.this,Validation_Alert.class);
				// intent_validation_alert.putExtra("intent", "SignUp");
				// intent_validation_alert.putExtra("validation",
				// config.SignUpMsg);
				// startActivityForResult(intent_validation_alert,
				// SignUp_Success);
			}
				break;
			case 1: {

				// Intent intent_validation_alert = new
				// Intent(SignUp.this,Validation_Alert.class);
				// intent_validation_alert.putExtra("intent", "SignUp");
				// intent_validation_alert.putExtra("validation",config.UserValidate);
				// startActivity(intent_validation_alert);
			}
				break;
			}
		}
	};

	private boolean ParseJsonResponse(String reqData) {
		try {
			JSONObject json = new JSONObject(reqData);
			JSONArray nameArray = json.names();
			JSONArray valArray = json.toJSONArray(nameArray);
			for (int i = 0; i < valArray.length(); i++) {
				//if (nameArray.getString(i).compareTo("errors") == 0)
				//	return false;
				if (nameArray.getString(i).compareTo("message") == 0) {
					userdata = valArray.getString(i);
					// JSONArray json1 = new JSONArray(userdata);
					// userId = json1.getString("id");
					// userToken = json1.getString("authentication_token");
					// userName = json1.getString("name");
					// SaveUserPreferences(userId, userToken, userName);
					System.out.println("in json response >>>" + userdata);
					return true;
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return true;
	}
	private void ConvertToEncoding() {
		// TODO Auto-generated method stub

		try {
			strfirst = URLEncoder.encode(strfirst, "utf-8");
			strlast = URLEncoder.encode(strlast, "utf-8");
			stremail = URLEncoder.encode(stremail, "utf-8");
			strpassword = URLEncoder.encode(strpassword, "utf-8");
			strconfirmpass = URLEncoder.encode(strconfirmpass, "utf-8");
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}
	}
	public void validateControls() {
		if (strfirst.equals("") == true || strlast.equals("") == true
				|| stremail.equals("") == true
				|| strpassword.equals("") == true
				|| strconfirmpass.equals("") == true) {
			passwordaboutuser = "Fields are empty..please fill it";
			errormessages = passwordaboutuser;
		} else {
			if (eMailValidation(stremail) == false) {
				emailvalidate = "Your email is not valid!!!";
				errormessages += emailvalidate;
			}

		}
		if (errormessages.compareTo("") != 0) {

			Toast.makeText(getApplicationContext(), errormessages,Toast.LENGTH_LONG).show();
		} 
		else 
		{
			new performBackgroundTask().execute();
		}
	}

	public boolean eMailValidation(String emailstring) {
		Log.d("hello", "inside email method");
		Pattern emailPattern = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher emailMatcher = emailPattern.matcher(emailstring);
		Log.d("return", "" + emailMatcher.matches());
		return emailMatcher.matches();
	}

	public class performBackgroundTask extends AsyncTask<Void, Void, Void> {
		ProgressDialog Dialog = new ProgressDialog(SignUp.this);

		protected void onPreExecute() {
			Dialog.setMessage("Saving...");
			Dialog.show();
		}

		protected void onPostExecute(Void unused) {
			try {
				if (Dialog.isShowing()) 
				{
					Dialog.dismiss();
				}
			} 
				catch (Exception e) {
			}
		}

		@Override
		protected Void doInBackground(Void... params) {
			SaveUserData();
			return null;
		}

		private void SaveUserData() {
			// TODO Auto-generated method stub
			HttpClient httpclient = new DefaultHttpClient();

			ConvertToEncoding();

			String signupUrl = "http://webplanex.net/projects/donna_bella/iphone_api/api.php?task=register&format=json&data[first_name]="
					+ strfirst
					+ "&data[last_name]="
					+ strlast
					+ "&data[email]="
					+ stremail
					+ "&data[password]="
					+ strpassword
					+ "&data[confirm_password]="   
					+ strconfirmpass
					+ "";
			System.out.println("url view >>" + signupUrl);
			HttpPost httppost = new HttpPost(signupUrl);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();

			try {
				reqData = httpclient.execute(httppost, responseHandler).toString();
				
				httpclient.getConnectionManager().shutdown();
				Log.d("SignupData", reqData);

				handler.sendEmptyMessage(0);
			} catch (ClientProtocolException e) {
				handler.sendEmptyMessage(1);
			} catch (IOException e) {
				handler.sendEmptyMessage(1);
			}

		}
	}

}
