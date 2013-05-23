package com.example.zoom;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;

public class AppConfig {

	Context context;

	public AppConfig(Context mxt) {

		context = mxt;
	}

	public static String filepath = null;
	public static String imageupload = null;

	public static Bitmap abjustbitmap = null;

	//public static Drawable [] gallery_bitmap;
	
	ArrayList<Bitmap> gallery_bitmap = new ArrayList<Bitmap>();
	
	public static int position = 0;

	public static int gallery_position = 0;

	public static int celeb_position = 0;

	public static int test_positionswipeleft = 0;

	public static String test_positiongrid ;

	public static String facebookname = null;
	
	public static String haironurl = "http://webplanex.co.in/projects/donna_bella/iphone_api/";

	public static ArrayList<Integer> imagefield = new ArrayList<Integer>();

	public static ArrayList<Integer> celebritygrid = new ArrayList<Integer>();

	static ArrayList<HashMap<String, String>> celebritylist = new ArrayList<HashMap<String, String>>();
	
	static ArrayList<HashMap<String, String>> colorlist = new ArrayList<HashMap<String, String>>();
	
	static ArrayList<HashMap<String, String>> styleList = new ArrayList<HashMap<String, String>>();
	
	public static ArrayList<HashMap<String, String>> hairstylelist_hair = new ArrayList<HashMap<String, String>>();
	
	public static int haircolour1[] = { R.drawable.haircolour1c1,
		R.drawable.haircolour2c1,R.drawable.haircolour3c1,
			R.drawable.haircolour4c1, };
	
	public static int haircolour2[] = { R.drawable.haircolour2c1,
			R.drawable.haircolour2c2, };
	/*
	public static int haircolour3[] = { R.drawable.haircolour3c1,
			R.drawable.haircolour3c2, R.drawable.haircolour3c3,
			R.drawable.haircolour3c4, };

	public static int haircolour4[] = { R.drawable.haircolour4c1,
			R.drawable.haircolour4c2, R.drawable.haircolour4c3,
			R.drawable.haircolour4c4, };

	public static int haircolour5[] = { R.drawable.haircolour5c1,
			R.drawable.haircolour5c2, R.drawable.haircolour5c3,
			R.drawable.haircolour5c4, };

	public static int haircolour7[] = { R.drawable.haircolour7c1,
			R.drawable.haircolour7c2, R.drawable.haircolour7c3,
			R.drawable.haircolour7c4, };

	public static int haircolour11[] = { R.drawable.haircolour11c1,
			R.drawable.haircolour11c2, R.drawable.haircolour11c3,
			R.drawable.haircolour11c4, };

	public static int haircolour12[] = { R.drawable.haircolour12c1,
			R.drawable.haircolour12c2, R.drawable.haircolour12c3,
			R.drawable.haircolour12c4, };

	public static int gallery_grid_Images[] = { 
		R.drawable.haircolour1c1,
			R.drawable.haircolour2c1, R.drawable.haircolour3c1,
			R.drawable.haircolour4c1, R.drawable.haircolour5c1,
			R.drawable.haircolour7c1, R.drawable.haircolour11c1,
			R.drawable.haircolour12c1 };*/

	public static String photocontent = "https://www.facebook.com/dbhairextension/app_184343041637133";
	public static String shop = "http://www.donnabellahair.com/hair-extensions/catalog/I-Link-Pro-Beaded-Method-7-1.html";
	public static String advice = "http://donnabellahair.com/wordpress";
	public static String news = "http://www.donnabellahair.com/hair-extensions/catalog/Specials-6-1.htm";
	public static String offers = "http://www.donnabellahair.com/be_donnabella.html";
	public static String specialize = "http://donnabellahair.com/wordpress";
	public static String contactus = "http://www.donnabellahair.com/contact_us.html";

//Contact us crseen web view url
	
	public static String fb = "https://www.facebook.com/dbhairextension";
	public static String twitter = "http://www.donnabellahair.com/contact_us.html";
	public static String youtube = "http://www.youtube.com/user/donnabellahair";
	public static String pintrest = "http://www.donnabellahair.com/contact_us.html";
	public static String blog = "http://www.donnabellahair.com/wordpress/";
	
	public static String livechat = "https://secure.livechatinc.com/licence/1187021/open_chat.cgi?groups=0#http%3A%2F%2Fwww.donnabellahair.com%2F%23";
}
