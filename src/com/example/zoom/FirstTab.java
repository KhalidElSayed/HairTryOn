package com.example.zoom;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class FirstTab extends Activity {
	private ArrayList<String> textfield;
	private ArrayList<Integer> celebritygrid;
	private CustomAdapter customadapter;

	AppConfig config;
	Context context;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.changehair);
		
		context= getApplicationContext();
		config= new AppConfig(context);
		GridView gridView = (GridView) findViewById(R.id.gridView1);
		preparetext();
		//prepareimage();
		//celebritygrid = new ArrayList<Integer>();
		
		/*config.celebritygrid.add(R.drawable.celebrity1);
		config.celebritygrid.add(R.drawable.celebrity2);
		config.celebritygrid.add(R.drawable.celebrity3);
		config.celebritygrid.add(R.drawable.celebrity4);
		config.celebritygrid.add(R.drawable.celebrity5);
		config.celebritygrid.add(R.drawable.celebrity6);
		config.celebritygrid.add(R.drawable.celebrity7);
		config.celebritygrid.add(R.drawable.celebrity8);
		*/
		customadapter = new CustomAdapter(FirstTab.this, textfield, config.celebritygrid);
		
		gridView.setAdapter(customadapter);

	//	gridView.setOnItemClickListener((OnItemClickListener) this);
		
		gridView.setOnItemClickListener(new OnItemClickListener() {

		    @Override
		    public void onItemClick(AdapterView<?> arg0, View arg1, int position,
		            long arg3) {
		      
		    	System.out.println("position>>>" + position);
		    	config.position=position;
		    	
		    	config.celebritygrid.get(position);
		    	
		    	//config.test_positiongrid = config.celebritygrid.get(position);
		    	
		    	System.out.println("in image field >>>"+config.test_positiongrid);
		    	
		    	Intent grid = new Intent(FirstTab.this,ViewFlipperBiDirectionDemoActivity.class);
		    	//grid.putExtra("position1", position);
		    	startActivity(grid);
		    }});
	}

	public void preparetext() {
		textfield = new ArrayList<String>();
		textfield.add("Hairstyle 1");
		textfield.add("Hairstyle 2");
		textfield.add("Hairstyle 3");
		textfield.add("Hairstyle 4");
		textfield.add("Hairstyle 5");
		textfield.add("Hairstyle 6");
		textfield.add("Hairstyle 7");
		textfield.add("Hairstyle 8");
		
	}

	/*public int prepareimage() 
	 * {
		
		celebritygrid = new ArrayList<Integer>();
		celebritygrid.add(R.drawable.celebrity1);
		celebritygrid.add(R.drawable.celebrity2);
		celebritygrid.add(R.drawable.celebrity3);
		celebritygrid.add(R.drawable.celebrity4);
		celebritygrid.add(R.drawable.celebrity5);
		celebritygrid.add(R.drawable.celebrity6);
		celebritygrid.add(R.drawable.celebrity7);
		celebritygrid.add(R.drawable.celebrity8);
		return 0;
	}*/

	
	
	private class CustomAdapter extends ArrayAdapter<Object> {
		Activity activity;
		ArrayList<String> textfield;
		ArrayList<Integer> celebritygrid;

		public CustomAdapter(Activity context, ArrayList<String> name,
				ArrayList<Integer> image) {
			super(context, 0);
			activity = context;
			this.textfield = name;
			this.celebritygrid = image;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ImageView imgViewFlag;
			TextView txtViewTitle;
			LayoutInflater inflator = activity.getLayoutInflater();
			convertView = inflator.inflate(R.layout.gridview, null);
			
			txtViewTitle = (TextView) convertView.findViewById(R.id.grid_txt);
			imgViewFlag = (ImageView) convertView.findViewById(R.id.grid_img);
			txtViewTitle.setText(textfield.get(position));
			
			imgViewFlag.setImageResource(celebritygrid.get(position));
			
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