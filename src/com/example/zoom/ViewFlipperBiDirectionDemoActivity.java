package com.example.zoom;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.ViewFlipper;

import com.example.testmorph.MainActivityMorph;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.Facebook;
import com.mayank.pojo.ImageLoader;

public class ViewFlipperBiDirectionDemoActivity extends Activity {

	// Your Facebook APP ID
	private static String APP_ID = "366713270110009"; // Replace with your App
														// ID
	// Instance of Facebook Class
	private Facebook facebook = new Facebook(APP_ID);
	private AsyncFacebookRunner mAsyncRunner;
	String FILENAME = "AndroidSSO_data";
	private SharedPreferences mPrefs;
	File file;
	int swipeleft, swiperight, flip_up, flip_down;
	ViewFlipper page, flipperup;
	Bitmap bitmap, finalbitmap;
	ImageView imgsave, camera, fav, changehair, img_back, img_abjust,img_abjuststyle;

	Animation animFlipInForeward;
	Animation animFlipOutForeward;

	Animation animFlipInForewardup;
	Animation animFlipOutForewardup;

	Animation animFlipInForeward_down;
	Animation animFlipOutForeward_down;

	Animation animFlipInBackward;
	Animation animFlipOutBackward;
	
	// private static final String DIRECTORY = "///";
	// private static final String DATA_DIRECTORY = "/sdcard/Hair/";
	// private static final String DATA_FILE = "/sdcard/Hair/imagelist.dat";
	// private int maxIndex = 0;
	// List<String> ImageList;
	// private int currentIndex = 0;

	String filepath;
	String pos = "0";
	int posint = 0;
	RelativeLayout relsave;
	AppConfig config;
	Context context;
	ProgressDialog pDialog;
	private Context mContext;
	Button slideButton, btntaaz, btnfacebook, gallery, email, cancel,cancelabjust;
	SlidingDrawer slidingDrawer, slidingDrawerabjust;

	LinearLayout abjust, abjustallstyle;
	ImageLoader imgloader;
	Drawable d;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_flipper_bi_direction_demo);

		img_abjuststyle = (ImageView) findViewById(R.id.abjustimg);

		abjust = (LinearLayout) findViewById(R.id.ll1);
		abjustallstyle = (LinearLayout) findViewById(R.id.ll2);

		abjustallstyle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				Intent abjust = new Intent(getApplicationContext(),MainActivityMorph.class);
				slidingDrawerabjust.close();
				startActivity(abjust);
			}
		});
		abjust.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent abjust = new Intent(getApplicationContext(), Test.class);
				slidingDrawerabjust.close();
				startActivity(abjust);
				finish();
			}
		});
		img_back = (ImageView) findViewById(R.id.img_back);
		img_back.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				finish();
			}
		});
		context = getApplicationContext();
		config = new AppConfig(context);

		imgsave = (ImageView) findViewById(R.id.img_save);
		img_abjust = (ImageView) findViewById(R.id.img_abjust);
		img_abjust.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stu
				new task().execute();
			}
		});
		camera = (ImageView) findViewById(R.id.camera);
		fav = (ImageView) findViewById(R.id.fav);
		fav.setOnClickListener(new OnClickListener() {
			private boolean flag;

			@Override
			public void onClick(View v) {

				fav.setBackgroundResource(R.drawable.favred);
				// TODO Auto-generated method stub
				config.imagefield.add(swipeleft);
				// config.imagefield.add(swiperight);
				config.test_positionswipeleft = swipeleft;

				for (int i = 0; i < config.imagefield.size(); i++) 
				{
					System.out.println("in chaek id >>"+ config.imagefield.get(i));
				}
			}
		});

		changehair = (ImageView) findViewById(R.id.change);
		changehair.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent changehair = new Intent(getApplicationContext(),TabSample.class);
				startActivity(changehair);
				finish();
			}
		});
		camera.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent camera = new Intent(getApplicationContext(),
						Capture.class);
				startActivity(camera);
			}
		});
		imgsave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				slidingDrawer = (SlidingDrawer) findViewById(R.id.SlidingDrawer);
				slidingDrawer.animateOpen();
				slidingDrawer.bringToFront();

				btntaaz = (Button) findViewById(R.id.btn_taaz);
				email = (Button) findViewById(R.id.btn_email);
				btnfacebook = (Button) findViewById(R.id.btn_share);
				gallery = (Button) findViewById(R.id.btn_save);
				cancel = (Button) findViewById(R.id.btn_cancel);
				cancel.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) 
					{
						// TODO Auto-generated method stub
						slidingDrawer.close();
					}
				});

				btntaaz.setOnClickListener(this);
				btntaaz.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent taaz = new Intent(getApplicationContext(),FacebookLogin.class);
						startActivity(taaz);
					}
				});
				email.setOnClickListener(this);
				email.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						relsave = (RelativeLayout) findViewById(R.id.rel_flip);
						relsave.setDrawingCacheEnabled(true);
						Bitmap bitmap = relsave.getDrawingCache();
						filepath = getFilename();
						file = new File(filepath);
						{
							if (!file.exists()) {
								try {
									file.createNewFile();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							try {
								FileOutputStream ostream = new FileOutputStream(
										file);
								bitmap.compress(CompressFormat.PNG, 10, ostream);
								ostream.close();
								relsave.invalidate();
							} catch (Exception e) {
								e.printStackTrace();
							} 
								finally 
							{
								relsave.setDrawingCacheEnabled(false);
							}
						}
						slidingDrawer.close();
						config.imageupload = file.toString();
						shareimage();
					}
				});
				btnfacebook.setOnClickListener(this);
				btnfacebook.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						// loginToFacebook();
						relsave = (RelativeLayout) findViewById(R.id.rel_flip);
						relsave.setDrawingCacheEnabled(true);
						Bitmap bitmap = relsave.getDrawingCache();
						filepath = getFilename();
						file = new File(filepath);
						{
							if (!file.exists()) {
								try {
									file.createNewFile();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							try {
								FileOutputStream ostream = new FileOutputStream(file);
								bitmap.compress(CompressFormat.PNG, 10, ostream);
								ostream.close();
								relsave.invalidate();
							} catch (Exception e) {
								e.printStackTrace();
							} finally 
							{
								relsave.setDrawingCacheEnabled(false);
							}
						}
						slidingDrawer.close();

						config.imageupload = file.toString();
						Intent facebook = new Intent(getApplicationContext(),TestConnect.class);
						startActivity(facebook);
					}
				});
				gallery.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// savetosdcard();
						// TODO Auto-generated method stub

						relsave = (RelativeLayout) findViewById(R.id.rel_flip);
						relsave.setDrawingCacheEnabled(true);
						Bitmap bitmap = relsave.getDrawingCache();
						filepath = getFilename();
						file = new File(filepath);
						{

							if (!file.exists()) {
								try {
									file.createNewFile();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							try {
								FileOutputStream ostream = new FileOutputStream(
										file);
								bitmap.compress(CompressFormat.PNG, 10, ostream);
								ostream.close();
								relsave.invalidate();
							} catch (Exception e) {
								e.printStackTrace();
							} finally {

								relsave.setDrawingCacheEnabled(false);
							}
						}
						slidingDrawer.close();
					}
				});
			}
		});
		
		RelativeLayout iv = (RelativeLayout) findViewById(R.id.rel_flip);

		filepath = config.filepath;
		File imgFile = new File(filepath);
		Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
		Drawable dr = new BitmapDrawable(myBitmap);
		iv.setBackgroundDrawable(dr);

		page = (ViewFlipper) findViewById(R.id.flipper);
		flipperup = (ViewFlipper) findViewById(R.id.flipper_up);

		/*
		 * File data_directory = new File(DATA_DIRECTORY); if
		 * (!data_directory.exists()) { if (data_directory.mkdir()) { FileUtils
		 * savedata = new FileUtils(); Toast toast = Toast .makeText(
		 * ViewFlipperBiDirectionDemoActivity.this,
		 * "Please wait while we search your SD Card for images...",
		 * Toast.LENGTH_SHORT); toast.show(); SystemClock.sleep(100); ImageList
		 * = FindFiles(); savedata.saveArray(DATA_FILE, ImageList);
		 * 
		 * } else { ImageList = FindFiles(); }
		 * 
		 * } else { File data_file = new File(DATA_FILE); if
		 * (!data_file.exists()) { FileUtils savedata = new FileUtils(); Toast
		 * toast = Toast .makeText( ViewFlipperBiDirectionDemoActivity.this,
		 * "Please wait while we search your SD Card for images...",
		 * Toast.LENGTH_SHORT); toast.show(); SystemClock.sleep(100); ImageList
		 * = FindFiles(); savedata.saveArray(DATA_FILE, ImageList); } else {
		 * FileUtils readdata = new FileUtils(); ImageList =
		 * readdata.loadArray(DATA_FILE); } }
		 * 
		 * if (ImageList == null) { quit(); }
		 * 
		 * SharedPreferences indexPrefs = getSharedPreferences("currentIndex",
		 * MODE_PRIVATE); if (indexPrefs.contains("currentIndex")) {
		 * currentIndex = indexPrefs.getInt("currentIndex", 0); }
		 */

		// maxIndex = ImageList.size() - 1;
		animFlipInForeward = AnimationUtils.loadAnimation(this, R.anim.flipin);
		animFlipOutForeward = AnimationUtils.loadAnimation(this, R.anim.flipout);

		animFlipInForewardup = AnimationUtils.loadAnimation(this,R.anim.slide_in_up);
		animFlipOutForewardup = AnimationUtils.loadAnimation(this,R.anim.slide_out_up);

		animFlipInForeward_down = AnimationUtils.loadAnimation(this,R.anim.slide_down);
		animFlipOutForeward_down = AnimationUtils.loadAnimation(this,R.anim.slide_down_out);
		
		animFlipInBackward = AnimationUtils.loadAnimation(this,R.anim.flipin_reverse);		
		animFlipOutBackward = AnimationUtils.loadAnimation(this,R.anim.flipout_reverse);
		
		for (int i = AppConfig.position; i < AppConfig.haircolour1.length; i++) {
			// This will create dynamic image view and add them to ViewFlipper
			setFlipperImage(AppConfig.haircolour1[i]);
			config.gallery_position = AppConfig.position;
		}
	}

	class task extends AsyncTask<String, String, Void> {
		int success;

		protected void onPreExecute() {
			// pDialog = ProgressDialog.show(getParent(),"",
			// "Please wait...",true);
			pDialog = new ProgressDialog(ViewFlipperBiDirectionDemoActivity.this);
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.setMessage("Please Wait...");
			pDialog.show();
		}

		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			slidingDrawerabjust = (SlidingDrawer) findViewById(R.id.SlidingDrawerabjust);
			slidingDrawerabjust.animateOpen();
			slidingDrawerabjust.bringToFront();

			try 
			{
				InputStream is;
				is = (InputStream) new URL(AppConfig.test_positiongrid).getContent();
				d = Drawable.createFromStream(is, "src name");

			} 
			catch (MalformedURLException e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			cancelabjust = (Button) findViewById(R.id.btn_cancelabjust);
			cancelabjust.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) 
				{
					// TODO Auto-generated method stub
					slidingDrawerabjust.close();
				}
			});
			return null;
		}
		protected void onPostExecute(Void v) {
			// list.setAdapter(new ListAdapter(Celebrity.this));
			img_abjuststyle.setBackgroundDrawable(d);
			pDialog.dismiss();
		}
	}
	public void shareimage() 
	{
		Uri uri = Uri.fromFile(new File(context.getFilesDir(),config.imageupload));

		Intent i = new Intent(Intent.ACTION_SEND);
		i.putExtra(Intent.EXTRA_SUBJECT, "Title");
		i.putExtra(Intent.EXTRA_TEXT, "Content");
		i.putExtra(Intent.EXTRA_STREAM, uri);
		i.setType("image/*");
		startActivity(Intent.createChooser(i, "Send mail"));
	}

	public String getFilename() {

		filepath = Environment.getExternalStorageDirectory().getPath();

		file = new File(filepath, "Hair");

		if (!file.exists()) {
			file.mkdirs();
		}
		config.imageupload = file.getAbsolutePath() + "/"
				+ String.valueOf(System.currentTimeMillis()) + ".png";
		return (file.getAbsolutePath() + "/"
				+ String.valueOf(System.currentTimeMillis()) + ".png");
	}

	private void setFlipperImage(int haircolour1) {
		Log.i("Set Filpper Called", haircolour1 + "");
		ImageView image = new ImageView(getApplicationContext());
		image.setBackgroundResource(haircolour1);
		page.addView(image);
	}

	private void setFlipperImageup(int string) {
		Log.i("Set Filpper Called", string + "");
		ImageView image = new ImageView(getApplicationContext());
		image.setBackgroundResource(string);
		flipperup.addView(image);
	}

	private void SwipeRight() {
		page.getDisplayedChild();

		for (int i = 0; i < config.imagefield.size(); i++) 
		{
			if (config.imagefield.get(i) == swiperight) 
			{
				fav.setBackgroundResource(R.drawable.favred);
			} 
			else 
			{
				fav.setBackgroundResource(R.drawable.fav);
			}
		}
		 swiperight = page.getDisplayedChild();

		 System.out.println("current view right>>" + swiperight);

		page.showPrevious();
		// flipperup.setVisibility(View.GONE);
		// page.setVisibility(View.VISIBLE);
	}
	private void SwipeLeft()
	{
		// page.setInAnimation(animFlipInForeward);
		// page.setOutAnimation(animFlipOutForeward);
		page.setVisibility(View.VISIBLE);
		page.getDisplayedChild();

		for (int i = 0; i < config.imagefield.size(); i++) 
		{
			if (config.imagefield.get(i) == swipeleft) 
			{
				fav.setBackgroundResource(R.drawable.favred);
			} 
				else 
			{
				fav.setBackgroundResource(R.drawable.fav);
			}
		}
		swipeleft = page.getDisplayedChild();
		System.out.println("current view left>>" + swipeleft);
		page.showNext();
	}

	private void SwipeUP() {
		flipperup.setOutAnimation(animFlipOutForewardup);

		flipperup.showNext();
		flip_up = flipperup.getDisplayedChild();

		/*if (swipeleft == 1) {

			for (int j = AppConfig.position; j < AppConfig.haircolour2.length; j++) {
				// This will create dynamic image view and add them to
				// ViewFlipper
				setFlipperImageup(AppConfig.haircolour2[j]);

				page.setVisibility(View.GONE);
				config.gallery_position = AppConfig.position;
			}
		} */
		System.out.println("current view upppp>>" + flip_up);
	}

	private void SwipeDown() {
		
		// flipperup.setInAnimation(animFlipInForeward_down);
		// flipperup.setOutAnimation(animFlipOutForeward_down);
		flipperup.showPrevious();
		// flipperup.setVisibility(View.VISIBLE);
		// page.setVisibility(View.GONE);
		flip_down = flipperup.getDisplayedChild();
		// System.out.println("current view downnnnn>>" + flip_down);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) 
	{
		// TODO Auto-generated method stub
		return gestureDetector.onTouchEvent(event);
	}

	SimpleOnGestureListener simpleOnGestureListener = new SimpleOnGestureListener() {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) 
		{
			float sensitvity = 50;
			float sensitvity1 = -50;

			if ((e1.getX() - e2.getX()) > sensitvity) {
				SwipeLeft();
				// SwipeUP();
			} 
				else if ((e2.getX() - e1.getX()) > sensitvity) 
			{
				SwipeRight();
				// SwipeDown();
			}
			if ((e1.getX() - e2.getX()) > sensitvity1) 
			{
				// SwipeLeft();
				SwipeUP();
			} else if ((e2.getX() - e1.getX()) > sensitvity1) {
				// SwipeRight();
				SwipeDown();
			}
			return true;
		}
	};
	GestureDetector gestureDetector = new GestureDetector(
			simpleOnGestureListener);

	/*
	 * public Bitmap decodeSampledBitmapFromUri(Uri uri, int reqWidth, int
	 * reqHeight) {
	 * 
	 * Bitmap bm = null;
	 * 
	 * try {
	 * 
	 * // First decode with inJustDecodeBounds=true to check dimensions final
	 * BitmapFactory.Options options = new BitmapFactory.Options();
	 * options.inJustDecodeBounds = true;
	 * BitmapFactory.decodeStream(getContentResolver() .openInputStream(uri),
	 * null, options);
	 * 
	 * Log.i(">>> options.out Bitmap: ", options.outWidth + " x " +
	 * options.outHeight);
	 * 
	 * int scalling = calculateInSampleSize(options, reqWidth, reqHeight);
	 * 
	 * options.inSampleSize = scalling;
	 * 
	 * options.inJustDecodeBounds = false;
	 * 
	 * bm = BitmapFactory.decodeStream(getContentResolver()
	 * .openInputStream(uri), null, options);
	 * 
	 * int orientation = getOrientation(getApplicationContext(), uri);
	 * 
	 * Log.i(">>> Oriantation", ">>> Oriantation" + orientation);
	 * 
	 * Matrix m = new Matrix();
	 * 
	 * m.postRotate(orientation);
	 * 
	 * bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m,
	 * true);
	 * 
	 * } catch (FileNotFoundException e) { e.printStackTrace();
	 * Toast.makeText(getApplicationContext(), e.toString(),
	 * Toast.LENGTH_LONG).show(); }
	 * 
	 * return bm; }
	 */
	/*
	 * public int calculateInSampleSize(BitmapFactory.Options options, int
	 * reqWidth, int reqHeight) { // Raw height and width of image final int
	 * height = options.outHeight; final int width = options.outWidth; int
	 * inSampleSize = 1;
	 * 
	 * if (height > reqHeight || width > reqWidth) { if (width > height) {
	 * inSampleSize = Math.round((float) height / (float) reqHeight); } else {
	 * inSampleSize = Math.round((float) width / (float) reqWidth); } } return
	 * inSampleSize; }
	 */
	public static int getOrientation(Context context, Uri photoUri) {
		/* it's on the external media. */
		Cursor cursor = context.getContentResolver().query(photoUri,
				new String[] { MediaStore.Images.ImageColumns.ORIENTATION },
				null, null, null);

		if (cursor.getCount() != 1) {
			return -1;
		}

		cursor.moveToFirst();
		return cursor.getInt(0);
	}

	// ===========================================data from
	// sdcarad======================

	/*
	 * protected void onPause() { super.onPause();
	 * 
	 * SharedPreferences indexPrefs = getSharedPreferences("currentIndex",
	 * MODE_PRIVATE);
	 * 
	 * SharedPreferences.Editor indexEditor = indexPrefs.edit();
	 * indexEditor.putInt("currentIndex", currentIndex); indexEditor.commit(); }
	 * 
	 * protected void onResume() { super.onResume(); SharedPreferences
	 * indexPrefs = getSharedPreferences("currentIndex", MODE_PRIVATE); if
	 * (indexPrefs.contains("currentIndex")) { currentIndex =
	 * indexPrefs.getInt("currentIndex", 0); } }
	 * 
	 * private List<String> FindFiles() { final List<String> tFileList = new
	 * ArrayList<String>(); Resources resources = getResources(); // array of
	 * valid image file extensions String[] imageTypes =
	 * resources.getStringArray(R.array.image); FilenameFilter[] filter = new
	 * FilenameFilter[imageTypes.length];
	 * 
	 * int i = 0; for (final String type : imageTypes) { filter[i] = new
	 * FilenameFilter() { public boolean accept(File dir, String name) { return
	 * name.endsWith("." + type); } }; i++; }
	 * 
	 * FileUtils fileUtils = new FileUtils(); File[] allMatchingFiles =
	 * fileUtils.listFilesAsArray( new File(DIRECTORY), filter, -1); for (File f
	 * : allMatchingFiles) { tFileList.add(f.getAbsolutePath()); } return
	 * tFileList; }
	 * 
	 * public class FileUtils {
	 * 
	 * public void saveArray(String filename, List<String> output_field) { try {
	 * FileOutputStream fos = new FileOutputStream(filename); GZIPOutputStream
	 * gzos = new GZIPOutputStream(fos); ObjectOutputStream out = new
	 * ObjectOutputStream(gzos); out.writeObject(output_field); out.flush();
	 * out.close(); } catch (IOException e) { e.getStackTrace(); } }
	 * 
	 * @SuppressWarnings("unchecked") public List<String> loadArray(String
	 * filename) { try { FileInputStream fis = new FileInputStream(filename);
	 * GZIPInputStream gzis = new GZIPInputStream(fis); ObjectInputStream in =
	 * new ObjectInputStream(gzis); List<String> read_field = (List<String>)
	 * in.readObject(); in.close(); return read_field; } catch (Exception e) {
	 * e.getStackTrace(); } return null; }
	 * 
	 * public File[] listFilesAsArray(File directory, FilenameFilter[] filter,
	 * int recurse) { Collection<File> files = listFiles(directory, filter,
	 * recurse);
	 * 
	 * File[] arr = new File[files.size()]; return files.toArray(arr); }
	 * 
	 * public Collection<File> listFiles(File directory, FilenameFilter[]
	 * filter, int recurse) {
	 * 
	 * Vector<File> files = new Vector<File>();
	 * 
	 * File[] entries = directory.listFiles();
	 * 
	 * if (entries != null) { for (File entry : entries) { for (FilenameFilter
	 * filefilter : filter) { if (filter == null || filefilter
	 * .accept(directory, entry.getName())) { files.add(entry);
	 * Log.v("ImageViewFlipper", "Added: " + entry.getName()); } } if ((recurse
	 * <= -1) || (recurse > 0 && entry.isDirectory())) { recurse--;
	 * files.addAll(listFiles(entry, filter, recurse)); recurse++; } } } return
	 * files; } }
	 * 
	 * public void quit() { SharedPreferences indexPrefs =
	 * getSharedPreferences("currentIndex", MODE_PRIVATE);
	 * 
	 * SharedPreferences.Editor indexEditor = indexPrefs.edit();
	 * indexEditor.putInt("currentIndex", 0); indexEditor.commit();
	 * 
	 * File settings = new File(DATA_FILE); settings.delete(); finish(); int pid
	 * = android.os.Process.myPid(); android.os.Process.killProcess(pid);
	 * System.exit(0); }
	 */
}
