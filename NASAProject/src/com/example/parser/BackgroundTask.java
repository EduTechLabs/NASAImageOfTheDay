package com.example.parser;

import java.util.ArrayList;

import android.os.AsyncTask;
import android.util.Log;

public class BackgroundTask extends AsyncTask<Void, Void, ArrayList<ApodFeedItem>> {
	
	public ReturnFromBackgroundTask deligate = null;
	
	
	String murl;
	public BackgroundTask(String mURL) {
		murl = mURL;
	}
	
	 
	
	@Override
	protected ArrayList<ApodFeedItem> doInBackground(Void... params) {
		 IotdHandler handler = new IotdHandler(murl);
	     handler.processFeeds();
	     ArrayList<ApodFeedItem>feedItemList = handler.getFeedItemList();
	    for (int i = 0; i < feedItemList.size(); i++) {
			Log.d("Rahool", ((ApodFeedItem)feedItemList.get(i)).toString() );
		}
		return feedItemList;
	}
	@Override
	protected void onPostExecute(ArrayList<ApodFeedItem> result) {
		super.onPostExecute(result);
		deligate.responseFromBackground(result);
		//Log.d("Rahoool",result.toString());
	}
	
	
}
