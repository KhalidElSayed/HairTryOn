package com.example.zoom;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import android.R.raw;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;



public class ThirdTab {
        double matrix_size;
        Uri inputImageUri;
        Context currContext;
        
        private static final String TAG = "Scope.java";
        
        // Constructor
        public ThirdTab(Context c, Uri inputUri)
        {
                currContext = c;
                inputImageUri = inputUri;
        }
        
        // Method to set image only, if class has already been instantiated
        public void SetImage(Uri inputUri)
        {
                inputImageUri = inputUri;
        }
        
        // Input beta value for brightness as a percentage
        public Uri erode(double matrixPercentage) {
                
                if (matrixPercentage > 100)
                        matrixPercentage = 100;
                else if (matrixPercentage < 100)
                        matrixPercentage = -100;
                
                matrix_size = matrixPercentage/10;
                
                Mat sourceImageMat = new Mat();
                
                Bitmap sourceImage = null;
                Bitmap destImage = null;

                try {
                        sourceImage = MediaStore.Images.Media.getBitmap(
                                        currContext.getContentResolver(), inputImageUri);
                } catch (FileNotFoundException e) {
                        e.printStackTrace();
                } catch (IOException e) {
                        Log.v(TAG, "NULL");
                        e.printStackTrace();
                }

               // Log.v(TAG, "sourceImage Size: " + sourceImage.getByteCount());
                
                destImage=sourceImage;
                
             //   Utils.bitmapToMat(sourceImage, sourceImageMat);
                Mat destImageMat = Mat.zeros(sourceImageMat.size(), sourceImageMat.type());
                
                if(matrix_size>=0)
                {
                        Size size = new Size(matrix_size,matrix_size);
                        Imgproc.erode( sourceImageMat, destImageMat, Mat.ones(size,0)); 
                }
                else
                {
                        Size size = new Size(-matrix_size,-matrix_size);
                        Imgproc.dilate( sourceImageMat, destImageMat, Mat.ones(size,0));        
                }
                        
                Utils.matToBitmap(destImageMat, destImage);
                
               // Log.v(TAG, "destImage Size: " + destImage.getByteCount());
                
                File file = new File(
                                Environment
                                                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                                "temp.bmp");

                try {
                        FileOutputStream out = new FileOutputStream(file);
                        destImage.compress(Bitmap.CompressFormat.PNG, 90, out);
                } catch (Exception e) {
                        Log.v(TAG, "null2");
                        e.printStackTrace();
                }

                final Uri uri = Uri.fromFile(file);
                Log.v(TAG, uri.toString());
                
                return uri;
        }
}

