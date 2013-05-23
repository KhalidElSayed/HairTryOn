package com.example.zoom;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

public class Capture extends Activity implements OnClickListener {
	private Uri mImageCaptureUri;
	private static final int PICK_FROM_CAMERA = 1;
	private static final int PICK_FROM_FILE = 3;
	private static final int CROP_FROM_CAMERA = 2;

	String selectedImagePath;
	Bitmap newbitmap, cameranewbitmap;
	Button btn_takenew_photo, btn_use_existing;

	ProgressDialog PD1;
	Bitmap photo;
	ImageView imgsetting,btn_taaz;

	AppConfig config;
	Context context;

	ImageView leftArrowImageView,rightArrowImageView;
	Gallery gallery;

	private int selectedImagePosition = 0;

	private List<Drawable> drawables;

	List<Integer> mThumbIds;
	private byte[] data = {};
	private GalleryImageAdapter galImageAdapter;
	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newcapture);

		context = getApplicationContext();
		config = new AppConfig(context);

		// getDrawablesList();
		// setupUI();
		gallery = (Gallery) findViewById(R.id.gallery1);

		gallery.setAdapter(new ImageAdapter(this, ReadSDCard()));
		
		gallery.setOnItemClickListener(new OnItemClickListener() 
		{
			public void onItemClick(AdapterView<?> parent, View v,int position, long id)
			{
				 System.out.println("gallery position >>>>"+position);
			}
		});
		imgsetting = (ImageView) findViewById(R.id.img_setting);
		imgsetting.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				Intent setting = new Intent(getApplicationContext(),Setting.class);
				startActivity(setting);
			}
		});
		btn_takenew_photo = (Button) findViewById(R.id.btn_takenew_photo);
		btn_use_existing = (Button) findViewById(R.id.btn_use_existing);
		btn_taaz = (ImageView) findViewById(R.id.btn_taazcapture);
		btn_taaz.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(),"You allready Logged in !!", Toast.LENGTH_SHORT).show();
				Intent fblogin = new Intent(getApplicationContext(),FacebookLogin.class);
				startActivity(fblogin);
			}
		});
		btn_use_existing.setOnClickListener(this);
		btn_takenew_photo.setOnClickListener(this);
	}
	private List<String> ReadSDCard() {

		List<String> tFileList = new ArrayList<String>();

		// It have to be matched with the directory in SDCard
		File f = new File(Environment.getExternalStorageDirectory().toString()+ "/hair");

		System.out.println("in sdcared read >>>" + f);
		File[] files = f.listFiles();
		if (f.exists()) 
		{
			for (int i = 0; i < files.length; i++) 
			{
				File file = files[i];
				/* It's assumed that all file in the path are in supported type */
				tFileList.add(file.getPath());
			}
		}
		return tFileList;
	}
	public class ImageAdapter extends BaseAdapter 
	{
		int mGalleryItemBackground;
		private Context mContext;
		private List<String> FileList;

		public ImageAdapter(Context c, List<String> fList) {
			mContext = c;
			FileList = fList;
			// TypedArray a = obtainStyledAttributes(R.styleable.gall);
			// mGalleryItemBackground = a.getResourceId(
			// R.styleable.gall_android_galleryItemBackground,0);
			// a.recycle();
		}

		public int getCount() 
		{
			return FileList.size();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(final int position, View convertView, ViewGroup parent) {
			ImageView i = new ImageView(mContext);

			Bitmap bm = BitmapFactory.decodeFile(FileList.get(position).toString());
			i.setImageBitmap(bm);

			i.setLayoutParams(new Gallery.LayoutParams(150, 100));
			i.setScaleType(ImageView.ScaleType.FIT_XY);
			
			/*Bitmap galleryimage = config.abjustbitmap;
		      
		        Drawable dr = new BitmapDrawable(galleryimage);
				i.setBackgroundDrawable(dr);*/
			
//			i.setBackgroundResource(mGalleryItemBackground);
/*i.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		System.out.println("imge click for gallery" + position);
	}
});*/
			return i;
		}
	}

	public TypedArray obtainStyledAttributes(int theme) {
		// TODO Auto-generated method stub
		return null;
	}

		/*private void setupUI() {

		// selectedImageView = (ImageView)
		// findViewById(R.id.selected_imageview);
		leftArrowImageView = (ImageView) findViewById(R.id.left_arrow_imageview);
		rightArrowImageView = (ImageView) findViewById(R.id.right_arrow_imageview);
		gallery = (Gallery) findViewById(R.id.gallery1);

		leftArrowImageView.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) {

				if (selectedImagePosition > 0) {
					--selectedImagePosition;

				}

				gallery.setSelection(selectedImagePosition, false);
			}
		});

		rightArrowImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (selectedImagePosition < drawables.size() - 1) {
					++selectedImagePosition;

				}

				gallery.setSelection(selectedImagePosition, false);

			}
		});

		gallery.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {

				selectedImagePosition = pos;

				if (selectedImagePosition > 0
						&& selectedImagePosition < drawables.size() - 1) {

					leftArrowImageView.setImageDrawable(getResources()
							.getDrawable(R.drawable.arrow_left_enabled));
					rightArrowImageView.setImageDrawable(getResources()
							.getDrawable(R.drawable.arrow_right_enabled));

				} else if (selectedImagePosition == 0) {

					leftArrowImageView.setImageDrawable(getResources()
							.getDrawable(R.drawable.arrow_left_disabled));

				} else if (selectedImagePosition == drawables.size() - 1) {

					rightArrowImageView.setImageDrawable(getResources()
							.getDrawable(R.drawable.arrow_right_disabled));
				}

				changeBorderForSelectedImage(selectedImagePosition);
				// setSelectedImage(selectedImagePosition);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}

		});
		galImageAdapter = new GalleryImageAdapter(this, drawables);

		gallery.setAdapter(galImageAdapter);

		if (drawables.size() > 0) {

			gallery.setSelection(selectedImagePosition, false);

		}

		if (drawables.size() == 1) {

			// rightArrowImageView.setImageDrawable(getResources().getDrawable(R.drawable.arrow_right_disabled));
		}

	}

	private void changeBorderForSelectedImage(int selectedItemPos) {

		int count = gallery.getChildCount();

		for (int i = 0; i < count; i++) {

			ImageView imageView = (ImageView) gallery.getChildAt(i);
			imageView.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.image_border));
			imageView.setPadding(3, 3, 3, 3);

		}

		ImageView imageView = (ImageView) gallery.getSelectedView();
		imageView.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.selected_image_border));
		imageView.setPadding(3, 3, 3, 3);
	}

	private void getDrawablesList() {

		drawables = new ArrayList<Drawable>();
		//
		// String imageName = "picture"
		// int resID = getResources().getIdentifier(imageName, "drawable",
		// "package.name");
		// ImageView image;

		// drawables.addAll(getResources().getStringArray(PICK_FROM_CAMERA));
		
		 * drawables.add(getResources().getDrawable(R.drawable.celebrity1));
		 * drawables.add(getResources().getDrawable(R.drawable.celebrity1));
		 * drawables.add(getResources().getDrawable(R.drawable.celebrity1));
		 * drawables.add(getResources().getDrawable(R.drawable.celebrity1));
		 * drawables.add(getResources().getDrawable(R.drawable.celebrity1));
		 * drawables.add(getResources().getDrawable(R.drawable.celebrity1));
		 * drawables.add(getResources().getDrawable(R.drawable.celebrity1));
		 

	}
*/
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		if (v.getId() == R.id.btn_takenew_photo) {
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

			try {
				// intent.putExtra("return-data", true);
				startActivityForResult(intent, PICK_FROM_CAMERA);
			} catch (ActivityNotFoundException e) {
				e.printStackTrace();
			}
		}
		if (v.getId() == R.id.btn_use_existing) {

			Intent intent = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			intent.setType("image/*");

			startActivityForResult(
					Intent.createChooser(intent, "Complete action using"),
					PICK_FROM_FILE);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK)
			return;

		switch (requestCode) {
		case PICK_FROM_CAMERA:

			Bitmap photo = (Bitmap) data.getExtras().get("data");

			ByteArrayOutputStream bs = new ByteArrayOutputStream();
			photo.compress(Bitmap.CompressFormat.PNG, 100, bs);

			Intent photointent = new Intent(Capture.this, MainActivity.class);
			photointent.putExtra("photo", bs.toByteArray());
			photointent.putExtra("isgallery", "no");
			startActivity(photointent);
			

			File f1 = new File(Environment.getExternalStorageDirectory(),
					"tmp_avatar_" + String.valueOf(System.currentTimeMillis())
							+ ".jpg");
			System.out.println("====================Camara file path====="
					+ f1.getAbsolutePath());
			//
			if (f1.exists())
				f1.delete();

			break;

		case PICK_FROM_FILE:

			Uri targetUri = data.getData();

			photointent = new Intent(Capture.this, MainActivity.class);

			photointent.putExtra("isgallery", "yes");
			photointent.putExtra("uri", targetUri.toString());

			System.out.println("In Uri>>>>>" + targetUri.toString());

			startActivity(photointent);
			break;

		}
		
	}

}