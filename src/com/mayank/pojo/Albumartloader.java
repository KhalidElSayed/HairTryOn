package com.mayank.pojo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Stack;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

public class Albumartloader {
	private HashMap<String, Bitmap> cache = new HashMap<String, Bitmap>();

	private File cacheDir;
	private Bitmap useThisBitmap;
	int IMAGE_MAX_SIZE = 50;
	Context _context;

	public Albumartloader(Context context) {
		_context = context;
		photoLoaderThread.setPriority(Thread.NORM_PRIORITY - 1);
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED))
			cacheDir = new File(
					android.os.Environment.getExternalStorageDirectory(),
					"Streaming_Service/AlbumArt/");
		else
			cacheDir = context.getCacheDir();

		if (!cacheDir.exists())
			cacheDir.mkdirs();
	}

	public Albumartloader(Context context, int maxsize) {

		IMAGE_MAX_SIZE = maxsize;

		photoLoaderThread.setPriority(Thread.NORM_PRIORITY - 1);
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED))
			cacheDir = new File(
					android.os.Environment.getExternalStorageDirectory(),
					"Streaming_Service/AlbumArt/");
		else
			cacheDir = context.getCacheDir();

		if (!cacheDir.exists())
			cacheDir.mkdirs();

	}
	public void DisplayImage(String url, Activity activity, ImageView imageView) {
		if (!url.equals("")) {
			if (cache.containsKey(url)) {
				if (cache.get(url) != null) {
					imageView.setImageBitmap(cache.get(url));
				} else {
					// imageView.setImageBitmap(BitmapFactory.decodeResource(
					// _context.getResources(), R.drawable.noimage));
				}
			} else {
				queuePhoto(url, activity, imageView);
			}
		}
	}

	private void queuePhoto(String url, Activity activity, ImageView imageView) {
		photosQueue.Clean(imageView);
		PhotoToLoad p = new PhotoToLoad(url, imageView);

		synchronized (photosQueue.photosToLoad) {
			photosQueue.photosToLoad.push(p);
			photosQueue.photosToLoad.notifyAll();
		}

		// start thread if it's not started yet
		if (photoLoaderThread.getState() == Thread.State.NEW)
			photoLoaderThread.start();
	}

	public Bitmap getBitmap(String url) {
		try {
			// I identify images by hashcode. Not a perfect solution, good for
			// the
			// demo.
			String filename = String.valueOf(url.hashCode());
			File f = new File(cacheDir, filename);

			// from SD cache
			Bitmap b = decodeFile(f);
			if (b != null)
				return b;

			// from web
			try {
				Bitmap bitmap = null;
				if (!url.equals("")) {
					InputStream is = new URL(url).openStream();
					OutputStream os = new FileOutputStream(f);
					Utils.CopyStream(is, os);
					os.close();
					bitmap = decodeFile(f);
				}
				return bitmap;
			} catch (Exception ex) {
				ex.printStackTrace();
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	/*
	 * decodes image and scales it to reduce memory consumption
	 * 
	 * @param file path
	 * 
	 * @throws FileNotFoundException
	 * 
	 * @return bitmap
	 */
	private Bitmap decodeFile(File f) {
		Bitmap b = null;
		try {

			useThisBitmap = null;
			// Decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;

			BitmapFactory.decodeStream(new FileInputStream(f), null, o);
			int scale = 0;

			// if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE)
			// {
			// scale = 2 ^ (int) Math.ceil(Math.log(IMAGE_MAX_SIZE
			// / (double) Math.max(o.outHeight, o.outWidth))
			// / Math.log(0.5));
			// }

			// Decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inPreferredConfig = Config.ARGB_8888;
			o2.inInputShareable = true;
			o2.inDither = false;

			o2.inSampleSize = 4;
			b = BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
			useThisBitmap = b;

		} catch (FileNotFoundException e) {

		} catch (Exception e) {

		} finally {
			System.gc();
		}
		return useThisBitmap;
	}

	// Task for the queue
	private class PhotoToLoad {
		public String url;
		public ImageView imageView;

		public PhotoToLoad(String u, ImageView i) {
			url = u;
			imageView = i;
		}
	}

	PhotosQueue photosQueue = new PhotosQueue();

	public void stopThread() {
		photoLoaderThread.interrupt();
	}

	// stores list of photos to download
	class PhotosQueue {
		private Stack<PhotoToLoad> photosToLoad = new Stack<PhotoToLoad>();

		// removes all instances of this ImageView
		public void Clean(ImageView image) {
			for (int j = 0; j < photosToLoad.size();) {
				if (photosToLoad.get(j).imageView == image)
					photosToLoad.remove(j);
				else
					++j;
			}
		}
	}

	class PhotosLoader extends Thread {
		public void run() {
			try {
				while (true) {
					// thread waits until there are any images to load in the
					// queue
					if (photosQueue.photosToLoad.size() == 0)
						synchronized (photosQueue.photosToLoad) {
							photosQueue.photosToLoad.wait();
						}
					if (photosQueue.photosToLoad.size() != 0) {
						PhotoToLoad photoToLoad;
						synchronized (photosQueue.photosToLoad) {
							photoToLoad = photosQueue.photosToLoad.pop();
						}
						Bitmap bmp = getBitmap(photoToLoad.url);
						cache.put(photoToLoad.url, bmp);
						if (((String) photoToLoad.imageView.getTag())
								.equals(photoToLoad.url)) {
							BitmapDisplayer bd = new BitmapDisplayer(bmp,
									photoToLoad.imageView);
							Activity a = (Activity) photoToLoad.imageView
									.getContext();
							a.runOnUiThread(bd);
						}
					}
					if (Thread.interrupted())
						break;
				}
			} catch (InterruptedException e) {
				// allow thread to exit
			}
		}
	}

	PhotosLoader photoLoaderThread = new PhotosLoader();

	// Used to display bitmap in the UI thread
	class BitmapDisplayer implements Runnable {
		Bitmap bitmap;
		ImageView imageView;

		public BitmapDisplayer(Bitmap b, ImageView i) {
			bitmap = b;
			imageView = i;
		}

		public void run() {
			if (bitmap != null) {
				imageView.setImageBitmap(bitmap);
			} else {
				// imageView.setImageBitmap(BitmapFactory.decodeResource(
				// _context.getResources(), R.drawable.noimage));
			}
		}
	}

	public void clearCache() {
		// clear memory cache
		cache.clear();
		// clear SD cache
		File[] files = cacheDir.listFiles();
		for (File f : files)
			f.delete();
	}
}