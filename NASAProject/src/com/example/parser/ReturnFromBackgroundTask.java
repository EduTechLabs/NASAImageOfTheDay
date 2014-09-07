package com.example.parser;

import java.util.ArrayList;

import android.graphics.Bitmap;

public interface ReturnFromBackgroundTask {
	public void responseFromBackground(ArrayList<ApodFeedItem> response);
	public void responseImageFromBackground(Bitmap response);
	
}
