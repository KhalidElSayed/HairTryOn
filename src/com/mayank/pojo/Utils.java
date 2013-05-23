package com.mayank.pojo;

/**
 * @todo File input stream util class used by ImageLoader.java
 * @access public
 */

import java.io.FileDescriptor;


import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Calendar;

import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

public class Utils {

	
	public static String CURRENCYSYMBOL="$";
	public static String getDate()
	{
		Calendar calendar=Calendar.getInstance();
		int year=calendar.get(Calendar.YEAR);
		int month=calendar.get(Calendar.MONTH);
		int day=calendar.get(Calendar.DAY_OF_MONTH);
		return ""+day+"/"+month+"/"+year;
	}
	/**
	 * @todo copy stream and write into output buffer for loading image
	 * @access public
	 * @param Inputstream
	 * @param OutputStream
	 */

	public static String tag="category_id=";
	public static boolean isAttributeSelected=false;
	//public static int PRODUCTCOUNT=0;
	public static void CopyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
		}
	}
	public static double round(double unrounded, int precision, int roundingMode)
	{
	    BigDecimal bd = new BigDecimal(unrounded);
	    BigDecimal rounded = bd.setScale(precision, roundingMode);
	    return rounded.doubleValue();
	}
	public static String round_my(double unrounded)
	{
		
	    DecimalFormat df=new DecimalFormat("#0.00");
	    return df.format(unrounded);
	}
	
	public static String round_my(String val)
	{
		double unrounded=Double.parseDouble(val);
	    DecimalFormat df=new DecimalFormat("#0.00");
	    return df.format(unrounded);
	}

	public static Bitmap getAlbumart(Context context, Long album_id) {
		Bitmap bm = null;
		try {
			final Uri sArtworkUri = Uri
					.parse("content://media/external/audio/albumart");

			Uri uri = ContentUris.withAppendedId(sArtworkUri, album_id);

			ParcelFileDescriptor pfd = context.getContentResolver()
					.openFileDescriptor(uri, "r");

			if (pfd != null) {
				FileDescriptor fd = pfd.getFileDescriptor();
				bm = BitmapFactory.decodeFileDescriptor(fd);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return bm;
	}

	public static boolean CheckInternet(Context context) {
		ConnectivityManager connec = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		android.net.NetworkInfo wifi = connec
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		android.net.NetworkInfo mobile = connec
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

		// Here if condition check for wifi and mobile network is available or
		// not.
		// If anyone of them is available or connected then it will return true,
		// otherwise false;

		if (wifi.isConnected()) {
			return true;
		} else if (!mobile.isConnected()) {
			return false;
		} else if (mobile.isConnected()) {
			return true;
		}
		return false;
	}
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }


}