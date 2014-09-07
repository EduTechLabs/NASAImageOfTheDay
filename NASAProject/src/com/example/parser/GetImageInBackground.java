package com.example.parser;

import android.graphics.Bitmap;
import android.os.AsyncTask;

public class GetImageInBackground extends AsyncTask<String, Void, Bitmap> {
	
	String imageUrl;
	public ReturnFromBackgroundTask deligate = null;
	
	public GetImageInBackground(String mImageUrl) {
		imageUrl = mImageUrl;
	}

	@Override
	protected Bitmap doInBackground(String... params) {
		IotdHandler imageHandler = new IotdHandler();
		Bitmap image = imageHandler.getBitmap(imageUrl);
		
		return image;
	}
	@Override
	protected void onPostExecute(Bitmap result) {
		super.onPostExecute(result);
		deligate.responseImageFromBackground(result);
		
	}
}
