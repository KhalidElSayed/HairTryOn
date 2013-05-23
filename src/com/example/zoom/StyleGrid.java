package com.example.zoom;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class StyleGrid extends Activity {
	private ArrayList<String> textfield;
	private ArrayList<Integer> imagefield;
	private CustomAdapter customadapter;

	ImageView stylecamera;

	AppConfig config;
	Context context;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stylegrid);

		context = getApplicationContext();
		config = new AppConfig(context);
		GridView gridView = (GridView) findViewById(R.id.gridView_style);

		stylecamera = (ImageView) findViewById(R.id.style_camera);
		stylecamera.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent camerastyle = new Intent(getApplicationContext(),
						Capture.class);
				startActivity(camerastyle);
			}
		});
		// preparetext();
		// prepareimage();

		String listname = getIntent().getExtras().getString("listitem");
		TextView txtstyle = (TextView) findViewById(R.id.txt_style);
		txtstyle.setText(listname);
		System.out.println("list item name  >>>>" + listname);

		if (listname.equalsIgnoreCase("At The Disco Hairstyle")) {

			textfield = new ArrayList<String>();
			textfield.add("Hairstyle 1");
			textfield.add("Hairstyle 2");
			textfield.add("Hairstyle 3");
			textfield.add("Hairstyle 4");
			textfield.add("Hairstyle 5");

			/*imagefield = new ArrayList<Integer>();
			imagefield.add(R.drawable.celebrity1);
			imagefield.add(R.drawable.celebrity1);
			imagefield.add(R.drawable.celebrity1);
			imagefield.add(R.drawable.celebrity1);
			imagefield.add(R.drawable.celebrity1);*/

		} else if (listname.equalsIgnoreCase("beach_hair")) {
			textfield = new ArrayList<String>();
			textfield.add("Hairstyle 1");
			textfield.add("Hairstyle 1");
			textfield.add("Hairstyle 1");
			textfield.add("Hairstyle 1");
			textfield.add("Hairstyle 1");

		/*	imagefield = new ArrayList<Integer>();
			imagefield.add(R.drawable.celebrity1);
			imagefield.add(R.drawable.celebrity1);
			imagefield.add(R.drawable.celebrity1);
			imagefield.add(R.drawable.celebrity1);
			imagefield.add(R.drawable.celebrity1);*/
		}

		else if (listname.equalsIgnoreCase("bussines woman_hairstyle")) {
			textfield = new ArrayList<String>();
			textfield.add("Hairstyle 1");
			textfield.add("Hairstyle 1");
			textfield.add("Hairstyle 1");
			textfield.add("Hairstyle 1");
			textfield.add("Hairstyle 1");

		/*	imagefield = new ArrayList<Integer>();
			imagefield.add(R.drawable.celebrity1);
			imagefield.add(R.drawable.celebrity1);
			imagefield.add(R.drawable.celebrity1);
			imagefield.add(R.drawable.celebrity1);
			imagefield.add(R.drawable.celebrity1);
			*/
		} else if (listname.equalsIgnoreCase("charley_beach_hats")) {
			textfield = new ArrayList<String>();
			textfield.add("Hairstyle 1");
			textfield.add("Hairstyle 1");
			textfield.add("Hairstyle 1");
			textfield.add("Hairstyle 1");
			textfield.add("Hairstyle 1");

			/*imagefield = new ArrayList<Integer>();
			imagefield.add(R.drawable.celebrity1);
			imagefield.add(R.drawable.celebrity1);
			imagefield.add(R.drawable.celebrity1);
			imagefield.add(R.drawable.celebrity1);
			imagefield.add(R.drawable.celebrity1);*/
			
		} else if (listname.equalsIgnoreCase("Get marride_hairstyle")) {
			textfield = new ArrayList<String>();
			textfield.add("Hairstyle 1");
			textfield.add("Hairstyle 1");
			textfield.add("Hairstyle 1");
			textfield.add("Hairstyle 1");
			textfield.add("Hairstyle 1");

		/*	imagefield = new ArrayList<Integer>();
			imagefield.add(R.drawable.celebrity1);
			imagefield.add(R.drawable.celebrity1);
			imagefield.add(R.drawable.celebrity1);
			imagefield.add(R.drawable.celebrity1);
			imagefield.add(R.drawable.celebrity1);*/
		}

		customadapter = new CustomAdapter(this, textfield, imagefield);
		gridView.setAdapter(customadapter);

		// gridView.setOnItemClickListener((OnItemClickListener) this);

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				System.out.println("position>>>" + position);
				config.position = position;
				Intent grid = new Intent(StyleGrid.this,
						ViewFlipperBiDirectionDemoActivity.class);
				// grid.putExtra("position1", position);
				startActivity(grid);
			}
		});
	}

	// public void preparetext() {
	// textfield = new ArrayList<String>();
	// textfield.add("Sunday");
	// textfield.add("Monday");
	// textfield.add("Tuesday");
	// textfield.add("Wednessday");
	// textfield.add("Thursday");
	// }
	//
	// public void prepareimage() {
	// imagefield = new ArrayList<Integer>();
	// imagefield.add(R.drawable.atdhairstyle01hd);
	// imagefield.add(R.drawable.atdhairstyle02hd);
	// imagefield.add(R.drawable.atdhairstyle03hd);
	// imagefield.add(R.drawable.atdhairstyle04hd);
	// imagefield.add(R.drawable.atdhairstyle05hd);
	//
	// }

	private class CustomAdapter extends ArrayAdapter<Object> {
		Activity activity;
		ArrayList<String> textfield;
		ArrayList<Integer> imagefield;

		public CustomAdapter(Activity context, ArrayList<String> name,
				ArrayList<Integer> image) {
			super(context, 0);
			activity = context;
			this.textfield = name;
			this.imagefield = image;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ImageView imgViewFlag;
			TextView txtViewTitle;
			LayoutInflater inflator = activity.getLayoutInflater();
			convertView = inflator.inflate(R.layout.stylegridview, null);
			txtViewTitle = (TextView) convertView.findViewById(R.id.grid_txt);
			imgViewFlag = (ImageView) convertView.findViewById(R.id.grid_img);
			txtViewTitle.setText(textfield.get(position));
			imgViewFlag.setImageResource(imagefield.get(position));
			// convertView
			// .setOnClickListener(new OnItemClickListener(position));
			return convertView;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return textfield.size();
		}

		@Override
		public String getItem(int position) {
			// TODO Auto-generated method stub
			return textfield.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

	}

}