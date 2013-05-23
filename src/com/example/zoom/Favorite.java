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
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;

public class Favorite extends Activity
{
	GridView gridimg;
//	private ArrayList<Integer> imagefield;
	private CustomAdapter customadapter;
	
	AppConfig config;
	Context context;
	ArrayList<Integer> newarray = new ArrayList<Integer>();
	
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.favorate);
	
	context = getApplicationContext();
	config = new AppConfig(context);
	
	for (int i = 0; i < AppConfig.imagefield.size(); i++) 
	{
		
		newarray.add(AppConfig.celebritygrid.get(AppConfig.imagefield.get(i)));
	}
	
	for (int i = 0; i < newarray.size(); i++) 
	{
		
		System.out.println("new fav array " + newarray.get(i));
	}
	
	gridimg = (GridView) findViewById(R.id.fav_gridView_style);
	
	customadapter = new CustomAdapter(Favorite.this,null, newarray);
	
	gridimg.setAdapter(customadapter);
	gridimg.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1,
				int position, long arg3) {

			System.out.println("position>>>" + position);
			config.position = position;
			Intent grid = new Intent(Favorite.this,ViewFlipperBiDirectionDemoActivity.class);
			// grid.putExtra("position1", position);
			startActivity(grid);
		}
	});
}
	
private class CustomAdapter extends ArrayAdapter<Object> {
	Activity activity;
	//ArrayList<String> textfield;
//	int[] imagefield;

	public CustomAdapter(Activity context, ArrayList<String> name,
			ArrayList<Integer> newarray) {
		super(context, 0);
		activity = context;
	//	this.textfield = name;
		//this.config.gallery_grid_Images[];
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ImageView imgViewFlag;
		//TextView txtViewTitle;
		LayoutInflater inflator = activity.getLayoutInflater();
		convertView = inflator.inflate(R.layout.favorategridview, null);
		//txtViewTitle = (TextView) convertView.findViewById(R.id.grid_txt);
		imgViewFlag = (ImageView) convertView.findViewById(R.id.fav_grid_img);
		//txtViewTitle.setText(textfield.get(position));
		
			imgViewFlag.setImageResource(newarray.get(position));
			
		// convertView
		// .setOnClickListener(new OnItemClickListener(position));
		return convertView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return newarray.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return newarray.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

}

}
