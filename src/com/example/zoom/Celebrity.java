package com.example.zoom;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources.Theme;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class Celebrity extends ActivityGroup {

	// Albumartloader imaAlbumartloader;
	ImageLoader imgImageLoader;
	SQLiteDatabase db;
	JSONArray celebrities = null;
	// All static variables
	static final String URL = "http://webplanex.co.in/projects/donna_bella/iphone_api/api.php?task=celebrities&format=json";
	// XML node keys
	static final String KEY_ITEM = "celebrities"; // parent node
	static final String KEY_ID = "id";
	static final String KEY_TITLE = "title";

	static final String KEY_CELEBRITY_IMAGE = "celebrity_image";
	static final String KEY_HAIRSTYLE_IMAGE = "hairstyle_image";
	static final String KEY_HAIRSTYLE_ID = "hairstyle_id";

	static final String KEY_USER_ID = "user_id";
	static final String KEY_HAIRSTYLE_PATH = "hairstyle_image_path";
	static final String KEY_CELEBRITY_PATH = "celebrity_image_path";

	String STR_ID, STR_TITLE, STR_HAIR_IMG, STR_HAIR_ID, STR_USER_ID,
			STR_HAIR_PATH, STR_CELB_PATH, fullurl_celeb, fullurl_hair;

	String STR_CELB_IMG;

	ArrayList<String> urllist = new ArrayList<String>();

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

		config.celebritylist.clear();
		config.hairstylelist_hair.clear();
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
				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
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
			} catch (Exception e) {
				Log.e(TAG, "Exception: " + e.toString());
			}
			return null;

		}

		protected void onPostExecute(Bitmap result) {
			String name = reviewImageLink.substring(reviewImageLink
					.lastIndexOf("/") + 1);
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
			pDialog = new ProgressDialog(Celebrity.this);
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.setMessage("Please Wait...");
			pDialog.show();
		}

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			imgImageLoader = new ImageLoader(Celebrity.this);
			JSOnParser jParser = new JSOnParser();

			JSONObject json = jParser.getJSONFromUrl(URL);
			try {
				celebrities = json.getJSONArray(KEY_ITEM);

				for (int i = 0; i < celebrities.length(); i++) {

					JSONObject c = celebrities.getJSONObject(i);
					STR_TITLE = c.getString(KEY_TITLE);
					STR_CELB_PATH = c.getString(KEY_CELEBRITY_PATH);
					STR_HAIR_PATH = c.getString(KEY_HAIRSTYLE_PATH);

					fullurl_celeb = config.haironurl + STR_CELB_PATH;
					fullurl_hair = config.haironurl + STR_HAIR_PATH;

					urllist.add(fullurl_hair);

					HashMap<String, String> map = new HashMap<String, String>();
					HashMap<String, String> maphair = new HashMap<String, String>();

					map.put(KEY_TITLE, STR_TITLE);
					map.put(KEY_CELEBRITY_PATH, fullurl_celeb);
					maphair.put(KEY_HAIRSTYLE_PATH, fullurl_hair);

					config.celebritylist.add(map);
					config.hairstylelist_hair.add(maphair);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*
			 * for (int j = 0; j < urllist.size(); j++) {
			 * 
			 * reviewImageLink = urllist.get(j);
			 * System.out.println("url list in second loop >>>>>" +
			 * urllist.get(j)); URL reviewImageURL; String name =
			 * reviewImageLink.substring(reviewImageLink .lastIndexOf("/") + 1);
			 * try { reviewImageURL = new URL(reviewImageLink); if
			 * (!hasExternalStoragePublicPicture(name)) { isImage = false; new
			 * DownloadImageTask().execute(reviewImageURL); Log.v("log_tag",
			 * "if"); isImage = true; File sdImageMainDirectory = new File(
			 * Environment.getExternalStorageDirectory(),
			 * getResources().getString(R.string.directory)); File file = new
			 * File(sdImageMainDirectory, name); Log.v("log_tag",
			 * "Directory created"); } } catch (MalformedURLException e) {
			 * Log.v(TAG, e.toString()); } }
			 */
			return null;
		}

		protected void onPostExecute(Void v) {
			list.setAdapter(new ListAdapter(Celebrity.this));
			pDialog.dismiss();
		}
	}

	class ListAdapter extends BaseAdapter {
		LayoutInflater inflater;
		ViewHolder viewHolder;

		public ListAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public int getCount() {
			// TODO Auto-generated method stub
			return config.celebritylist.size();
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

				convertView = inflater.inflate(R.layout.celebrity_griditem,
						null);

				viewHolder = new ViewHolder();

				viewHolder.title = (TextView) convertView
						.findViewById(R.id.name);

				viewHolder.imageview = (ImageView) convertView
						.findViewById(R.id.imgview);

				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			try {

				viewHolder.title.setText(Html.fromHtml(config.celebritylist
						.get(position).get(KEY_TITLE)));

				viewHolder.imageview.setTag(config.celebritylist.get(position)
						.get(KEY_CELEBRITY_PATH));

				imgImageLoader.DisplayImage(config.celebritylist.get(position)
						.get(KEY_CELEBRITY_PATH), Celebrity.this,
						viewHolder.imageview);
				convertView
						.setOnClickListener(new OnItemClickListener(position));
			} catch (Exception e) {
				// TODO: handle exception
			}
			return convertView;
		}

		private class ViewHolder {
			TextView title;
			ImageView imageview;

		}
	}

	private class OnItemClickListener implements OnClickListener {
		private int mPosition;

		OnItemClickListener(int position) {
			mPosition = position;
		}

		public void onClick(View v) {

			if (AppConfig.celebritylist == null)
				return;
			if (AppConfig.celebritylist.size() < mPosition + 1)
				return;

			Toast.makeText(Celebrity.this, "position count" + mPosition,
					Toast.LENGTH_LONG).show();

			config.position = mPosition;
			config.test_positiongrid = config.celebritylist.get(mPosition).get(
					KEY_CELEBRITY_PATH);
			// config.celebritygrid.get(mPosition);

			// config.test_positiongrid = config.celebritygrid.get(mPosition);;

			 System.out.println("in image field >>>" +	 config.test_positiongrid);
			Intent i = new Intent(Celebrity.this,
					ViewFlipperBiDirectionDemoActivity.class);

			Bundle bundle = new Bundle();
			bundle.putInt("position", mPosition);
			config.celeb_position = mPosition;
			i.putExtras(bundle);

			startActivity(i);
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
