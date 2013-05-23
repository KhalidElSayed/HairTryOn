package com.example.zoom;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends Activity implements OnTouchListener {

	Bitmap  bitmap, finalbitmap;
	
    Matrix matrix;
	ImageView btnnext,retake_photo,iv;
	RelativeLayout relsave;
	ImageView imgface;

	String filestr;
	AppConfig config;
	Context context;
	
	/** Called when the activity is first created. */
	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		iv = (ImageView) findViewById(R.id.imageView);
		
		context = getApplicationContext();
		config = new AppConfig(context);
		
		retake_photo=(ImageView) findViewById(R.id.btn_retake);
		retake_photo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent retake = new Intent(getApplicationContext(),Capture.class);
				startActivity(retake);
			}
		});
		btnnext = (ImageView) findViewById(R.id.btn_next);
		imgface = (ImageView) findViewById(R.id.img_face);

		imgface.setVisibility(View.VISIBLE);
		btnnext.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				imgface.setVisibility(View.GONE);
				relsave = (RelativeLayout) findViewById(R.id.root);
				relsave.setDrawingCacheEnabled(true);
				Bitmap bitmap = relsave.getDrawingCache();
				File file = new File("/sdcard/" + "capture" + ".png");
				{
					System.out.println("in file >>>"+file.toString());
					config.filepath = file.toString();
					
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
					} finally {

						relsave.setDrawingCacheEnabled(false);
					}
				}
				System.out.println("bitmap >>>>>"+ bitmap);
				Intent photointent = new Intent(MainActivity.this,TabSample.class);
				//photointent.putExtra("file",file.toString());
				//photointent.putExtra("isgallery", "no");
				startActivity(photointent);
				finish();
			}
		});
		if (getIntent().getExtras().get("isgallery").equals("no")) {
			if (getIntent().hasExtra("photo")) {
				bitmap = BitmapFactory.decodeByteArray(getIntent()
						.getByteArrayExtra("photo"), 0, getIntent()
						.getByteArrayExtra("photo").length);

				finalbitmap = bitmap;
				iv.setImageBitmap(bitmap);
				config.abjustbitmap = bitmap;
				
				config.gallery_bitmap.add(bitmap); // Add a bitmap
				
				System.out.println("gallery bitmapr array >>>>> " +  config.gallery_bitmap.size());
				iv.setOnTouchListener(new Touch());
			}
		} 
			else 
		{
			Uri targetUri = Uri.parse(getIntent().getExtras().get("uri").toString());

			DisplayMetrics dm = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(dm);
			Display display = getWindowManager().getDefaultDisplay();

			int display_width = display.getWidth();
			int display_height = display.getHeight();

			float dpi = (float) (dm.densityDpi / 160.0);

			// Bitmap bitmap;
			bitmap = decodeSampledBitmapFromUri(targetUri, display_width,
					display_height - (int) (66 * dpi));

			if (bitmap == null) {
				Toast.makeText(getApplicationContext(),
						"the image data could not be decoded",
						Toast.LENGTH_LONG).show();
			} 
			else 
			{
				iv.setImageBitmap(bitmap);
				config.abjustbitmap=bitmap;
				
				config.gallery_bitmap.add(bitmap); // Add a bitmap
				
				System.out.println("gallery bitmapr array >>>>> " +  config.gallery_bitmap.size());
				iv.setOnTouchListener(new Touch());
			}
		}
		/*right.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bitmap rBitmap = BitmapFilter.changeStyle(bitmap,BitmapFilter.ROTATE);
				iv.setImageBitmap(rBitmap);
				System.out.println("second click>>>>"+bitmap);
			}
		});
		left.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bitmap rBitmap = BitmapFilter.changeStyle(bitmap,BitmapFilter.ROTATE);
				iv.setImageBitmap(rBitmap);
				System.out.println("second click>>>>"+bitmap);
			}

			
		});*/
	}
	public Bitmap decodeSampledBitmapFromUri(Uri uri, int reqWidth,
			int reqHeight) {

		Bitmap bm = null;

		try {

			// First decode with inJustDecodeBounds=true to check dimensions
			final BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(getContentResolver()
					.openInputStream(uri), null, options);

			Log.i(">>> options.out Bitmap: ", options.outWidth + " x "
					+ options.outHeight);

			int scalling = calculateInSampleSize(options, reqWidth, reqHeight);

			options.inSampleSize = scalling;

			options.inJustDecodeBounds = false;

			bm = BitmapFactory.decodeStream(getContentResolver()
					.openInputStream(uri), null, options);

			int orientation = getOrientation(getApplicationContext(), uri);

			Log.i(">>> Oriantation", ">>> Oriantation" + orientation);

			Matrix m = new Matrix();

			m.postRotate(orientation);

			bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(),m, true);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), e.toString(),
					Toast.LENGTH_LONG).show();
		}
		return bm;
	}
	public int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			if (width > height) {
				inSampleSize = Math.round((float) height / (float) reqHeight);
			} else {
				inSampleSize = Math.round((float) width / (float) reqWidth);
			}
		}
		return inSampleSize;
	}

	public static int getOrientation(Context context, Uri photoUri) {
		/* it's on the external media. */
		Cursor cursor = context.getContentResolver().query(photoUri,
				new String[] { MediaStore.Images.ImageColumns.ORIENTATION },null, null, null);

		if (cursor.getCount() != 1) {
			return -1;
		}

		cursor.moveToFirst();
		return cursor.getInt(0);
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub

		return false;
	}
}