package com.example.zoom;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActivityGroup;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mayank.pojo.ImageLoader;

public class ColorHair extends ActivityGroup {

	// Albumartloader imaAlbumartloader;
	ImageLoader imgImageLoader;

	// ArrayList<HashMap<String, String>> contactList;
	// ArrayList<HashMap<String, String>> contactList = new
	// ArrayList<HashMap<String, String>>();
	
	JSONArray hairstyle_colors = null;
	// All static variables
	static final String URL = "http://webplanex.co.in/projects/donna_bella/iphone_api/api.php?task=hairstyle_colors&format=json";
	// XML node keys
	static final String KEY_ITEM = "hairstyle_colors"; // parent node
	static final String KEY_ID = "id";
	static final String KEY_TITLE = "title";
	static final String KEY_CELEB_ID = "celebrity_id";

	static final String KEY_COLOR_IMG_PATH = "image_path";

	String STR_ID, STR_TITLE, STR_CELEB_ID, 
			STR_HAIR_COLOR_PATH, STR_CELB_PATH, fullurl_celeb, fullurl_color;

	String STR_CELB_IMG;

	ProgressDialog pDialog;
	Integer test;
	private static final String TAG = "PRANJAL";
	private boolean isImage = false;
	private String reviewImageLink;

	GridView list;
	AppConfig config;
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.celebritygrid);

		context = getApplicationContext();
		config = new AppConfig(context);

		config.colorlist.clear();

		list = (GridView) findViewById(R.id.list);
		new task().execute();
	}

	private class DownloadImageTask extends AsyncTask<URL, Integer, Bitmap> {
		// This class definition states that DownloadImageTask will take String
		// parameters, publish Integer progress updates, and return a Bitmap
		protected Bitmap doInBackground(URL... paths) {
			URL url;
			try {
				url = paths[0];
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				int length = connection.getContentLength();
				InputStream is = (InputStream) url.getContent();
				byte[] imageData = new byte[length];
				int buffersize = (int) Math.ceil(length / (double) 100);
				int downloaded = 0;
				int read;
				while (downloaded < length) {
					if (length < buffersize) {
						read = is.read(imageData, downloaded, length);
					} else if ((length - downloaded) <= buffersize) {
						read = is.read(imageData, downloaded, length
								- downloaded);
					} else {
						read = is.read(imageData, downloaded, buffersize);
					}
					downloaded += read;
					publishProgress((downloaded * 100) / length);
				}
				Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0,
						length);
				if (bitmap != null) {
					Log.i(TAG, "Bitmap created");
				} else {
					Log.i(TAG, "Bitmap not created");
				}
				is.close();
				return bitmap;
			} catch (MalformedURLException e) {
				Log.e(TAG, "Malformed exception: " + e.toString());
			} catch (IOException e) {
				Log.e(TAG, "IOException: " + e.toString());
			} catch (Exception e) 
			{
				Log.e(TAG, "Exception: " + e.toString());
			}
			return null;
		}

		protected void onPostExecute(Bitmap result) {
			String name = reviewImageLink.substring(reviewImageLink.lastIndexOf("/") + 1);
			if (result != null) {
				hasExternalStoragePublicPicture(name);
				saveToSDCard(result, name);
				isImage = true;

			} else {
				isImage = false;

			}
		}
	}
	public void saveToSDCard(Bitmap bitmap, String name) {
		boolean mExternalStorageAvailable = false;
		boolean mExternalStorageWriteable = false;
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			mExternalStorageAvailable = mExternalStorageWriteable = true;
			Log.v(TAG, "SD Card is available for read and write "
					+ mExternalStorageAvailable + mExternalStorageWriteable);
			saveFile(bitmap, name);
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			mExternalStorageAvailable = true;
			mExternalStorageWriteable = false;
			Log.v(TAG, "SD Card is available for read "
					+ mExternalStorageAvailable);
		} else {
			mExternalStorageAvailable = mExternalStorageWriteable = false;
			Log.v(TAG, "Please insert a SD Card to save your Video "
					+ mExternalStorageAvailable + mExternalStorageWriteable);
		}
	}
	private void saveFile(Bitmap bitmap, String name) {
		String filename = name;
		ContentValues values = new ContentValues();
		File sdImageMainDirectory = new File(
				Environment.getExternalStorageDirectory(), getResources()
						.getString(R.string.directory));
		sdImageMainDirectory.mkdirs();
		File outputFile = new File(sdImageMainDirectory, filename);
		values.put(MediaStore.MediaColumns.DATA, outputFile.toString());
		values.put(MediaStore.MediaColumns.TITLE, filename);
		values.put(MediaStore.MediaColumns.DATE_ADDED,
				System.currentTimeMillis());
		values.put(MediaStore.MediaColumns.MIME_TYPE, "image/png");
		Uri uri = this.getContentResolver().insert(
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,

				values);
		try {
			OutputStream outStream = this.getContentResolver()
					.openOutputStream(uri);
			bitmap.compress(Bitmap.CompressFormat.PNG, 95, outStream);

			outStream.flush();
			outStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean hasExternalStoragePublicPicture(String name) {
		File sdImageMainDirectory = new File(
				Environment.getExternalStorageDirectory(), getResources()
						.getString(R.string.directory));
		File file = new File(sdImageMainDirectory, name);
		if (file != null) {
			file.delete();
		}
		return file.exists();
	}

	class task extends AsyncTask<String, String, Void> {
		int success;

		protected void onPreExecute() {
			super.onPreExecute();

			// pDialog = ProgressDialog.show(getParent(),"",
			// "Please wait...",true);
			pDialog = new ProgressDialog(ColorHair.this);
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.setMessage("Please Wait...");
			pDialog.show();
		}
		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			imgImageLoader = new ImageLoader(ColorHair.this);
			JSOnParser jParser = new JSOnParser();
			// contactList = new ArrayList<HashMap<String, String>>();
			JSONObject json = jParser.getJSONFromUrl(URL);

			try {

				hairstyle_colors = json.getJSONArray(KEY_ITEM);

				for (int i = 0; i < hairstyle_colors.length(); i++) {

					JSONObject c = hairstyle_colors.getJSONObject(i);

					STR_ID= c.getString(KEY_ID);
					STR_TITLE = c.getString(KEY_TITLE);
					STR_CELEB_ID = c.getString(KEY_CELEB_ID);
					STR_HAIR_COLOR_PATH = c.getString(KEY_COLOR_IMG_PATH);

					// System.out.println("contact list data str title" +
					// STR_TITLE);
					// System.out.println("contact list data str title" +
					// STR_CELB_PATH);
					fullurl_color = config.haironurl + STR_HAIR_COLOR_PATH;
					System.out.println("contact list hair data color >>>>>>>>>" + fullurl_color);
					// String id = c.getString("cat_id");

					// String urlanimation = c.getString("animation_url");

					HashMap<String, String> map = new HashMap<String, String>();

					map.put(KEY_ID, STR_ID);
					map.put(KEY_TITLE, STR_TITLE);
					map.put(KEY_CELEB_ID, STR_CELEB_ID);
					map.put(KEY_COLOR_IMG_PATH, fullurl_color);

					config.colorlist.add(map);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("contact list hair data >>>>>>>>>" + config.colorlist);
			return null;
		}

		protected void onPostExecute(Void v) {
			list.setAdapter(new ListAdapter(ColorHair.this));
			pDialog.dismiss();
		}
	}

	class ListAdapter extends BaseAdapter 
	{
		LayoutInflater inflater;
		ViewHolder viewHolder;

		public ListAdapter(Context context) {
			inflater = LayoutInflater.from(context);

		}

		public int getCount() {
			// TODO Auto-generated method stub
			return config.colorlist.size();
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {

				convertView = inflater.inflate(R.layout.celebrity_griditem,null);

				viewHolder = new ViewHolder();

				viewHolder.title = (TextView) convertView
						.findViewById(R.id.name);

				viewHolder.imageview = (ImageView) convertView
						.findViewById(R.id.imgview);

				convertView.setTag(viewHolder);
			} 
			else
			{
				viewHolder = (ViewHolder) convertView.getTag();
			}
			try {

				viewHolder.title.setText(Html.fromHtml(config.colorlist.get(position).get(KEY_TITLE)));

				viewHolder.imageview.setTag(config.colorlist.get(position).get(KEY_COLOR_IMG_PATH));
				
					/*STR_CELB_IMG = config.contactList_hair.get(position).get(
						KEY_HAIRSTYLE_PATH);

					test = Integer.parseInt(STR_CELB_IMG);
					
				System.out.println("contact list data new" + STR_CELB_IMG);
				
				reviewImageLink = getString(test);
				URL reviewImageURL;
				String name = reviewImageLink.substring(reviewImageLink
						.lastIndexOf("/") + 1);
				try {
					reviewImageURL = new URL(reviewImageLink);
					if (!hasExternalStoragePublicPicture(name)) {
						isImage = false;
						new DownloadImageTask().execute(reviewImageURL);
						Log.v("log_tag", "if");
						isImage = true;
						File sdImageMainDirectory = new File(Environment
								.getExternalStorageDirectory(), getResources()
								.getString(R.string.directory));
						sdImageMainDirectory.mkdirs();
						File file = new File(sdImageMainDirectory, name);
						Log.v("log_tag", "Directory created");
					}

				} catch (MalformedURLException e) {
					Log.v(TAG, e.toString());
				}*/
				imgImageLoader.DisplayImage(config.colorlist.get(position).get(KEY_COLOR_IMG_PATH),ColorHair.this,viewHolder.imageview);

				// System.out.println("contact list data for celebrity" +
				// AppConfig.contactList_hair.get(position).get(KEY_HAIRSTYLE_PATH));

				convertView.setOnClickListener(new OnItemClickListener(position));
			} catch (Exception e) {
				// TODO: handle exception
			}

			return convertView;
		}

		private class ViewHolder {
			TextView title;

			ImageView imageview;

			// Dynamic view

		}
	}
	private class OnItemClickListener implements OnClickListener {
		private int mPosition;

		OnItemClickListener(int position) {
			mPosition = position;
		}

		public void onClick(View v) {

			if (AppConfig.colorlist == null)
				return;
			if (AppConfig.colorlist.size() < mPosition + 1)
				return;

			Toast.makeText(ColorHair.this, "position count" + mPosition,
					Toast.LENGTH_LONG).show();

			//Intent i = new Intent(ColorHair.this,ViewFlipperBiDirectionDemoActivity.class);
			/*
			 * Bundle bundle = new Bundle(); bundle.putInt("position",
			 * mPosition); config.celeb_position = mPosition;
			 * i.putExtras(bundle);
			 */
		//	startActivity(i);
			//
			//
			// WorkoutTabGroupActivity parantActivity =
			// (WorkoutTabGroupActivity) getParent();
			// parantActivity.startChildActivity("ListItemName", i);

			// Intent inn = new Intent(getParent(), ListItemName.class);
			// Bundle bundle = new Bundle();
			// bundle.putString("id", id);
			// bundle.putString("catname", catname);
			// inn.putExtras(bundle);
			//
			// WorkoutTabGroupActivity parantActivity =
			// (WorkoutTabGroupActivity) getParent();
			// parantActivity.startChildActivity("ListItemName", inn);

			// WorkoutTabGroupActivity parantActivity =
			// (WorkoutTabGroupActivity) getParent();
			// parantActivity.startChildActivity("ListItemName", inn);

		}
	}
}
