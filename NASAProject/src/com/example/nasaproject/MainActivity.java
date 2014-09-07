package com.example.nasaproject;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parser.ApodFeedItem;
import com.example.parser.BackgroundTask;
import com.example.parser.GetImageInBackground;
import com.example.parser.ReturnFromBackgroundTask;


public class MainActivity extends Activity implements ReturnFromBackgroundTask {

	private static final String mURL = "http://www.nasa.gov/rss/dyn/image_of_the_day.rss";
	final int DEFAULT_FEED_NUMBER = 0;
	int CURRENT_FEED_NUMBER = 0;
	ArrayList<ApodFeedItem> list = null;
	private TextView title;
	private TextView date;
	private ImageView image;
	private TextView description;
	private Button buttonSetWallpaper;
	private ProgressBar pb;
	private Bitmap backgroundImage;
	private ImageButton left,right;
	
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initializeUI();
        initilizeOnClickListener();
        
       pb.setVisibility(ProgressBar.VISIBLE); 
       BackgroundTask t = new BackgroundTask(mURL);
       t.deligate = MainActivity.this;
       t.execute();
       
       
        
    }
    
    private void initilizeOnClickListener() {
    	
    	left.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pb.setVisibility(ProgressBar.VISIBLE);
				--CURRENT_FEED_NUMBER;
				refreshDisplay(list.get(CURRENT_FEED_NUMBER).getTitle(),
	    		   		  list.get(CURRENT_FEED_NUMBER).getDate(), 
	    		          list.get(CURRENT_FEED_NUMBER).getImageUrl(), 
	    		          list.get(CURRENT_FEED_NUMBER).getDescription());
			}
		});
    	right.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pb.setVisibility(ProgressBar.VISIBLE);
				++CURRENT_FEED_NUMBER;
				refreshDisplay(list.get(CURRENT_FEED_NUMBER).getTitle(),
	    		   		  list.get(CURRENT_FEED_NUMBER).getDate(), 
	    		          list.get(CURRENT_FEED_NUMBER).getImageUrl(), 
	    		          list.get(CURRENT_FEED_NUMBER).getDescription());
			}
		});
    	buttonSetWallpaper.setOnClickListener(new  OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(MainActivity.this, "Wallpaper is set !!! ", Toast.LENGTH_LONG).show();
				WallpaperManager wm = WallpaperManager.getInstance(MainActivity.this);
				try {
					wm.setBitmap(backgroundImage);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
    	
	}

	private void initializeUI(){
    	
    	title = (TextView) findViewById(R.id.imageTitle);
    	date = (TextView) findViewById(R.id.imageDate);
    	image = (ImageView) findViewById(R.id.imageDisplay);
    	description = (TextView) findViewById(R.id.imageDesc);
    	buttonSetWallpaper = (Button) findViewById(R.id.button_setAsWallpaper);
    	pb = (ProgressBar) findViewById(R.id.progressBar1);
    	left = (ImageButton) findViewById(R.id.imageButtonLeft);
    	right = (ImageButton) findViewById(R.id.imageButtonRight);
    }
 
	public void refreshDisplay(String mTitle, String mDate,String mImageUrl, String mDescription){
		title.setText(mTitle);
		date.setText(mDate);
		description.setText(mDescription);
		
		Log.i("Rahooool", "About to send request for image : "+mImageUrl);
		GetImageInBackground imageFromBackground = new GetImageInBackground(mImageUrl);
		imageFromBackground.deligate = this;
		imageFromBackground.execute();		
		
		
		
	}

	@Override
	public void responseFromBackground(ArrayList<ApodFeedItem> response) {
		//all the content of rss feed in list call refreshDispaly in Main Activity
		
		list = response;
		CURRENT_FEED_NUMBER = DEFAULT_FEED_NUMBER;
	       refreshDisplay(list.get(DEFAULT_FEED_NUMBER).getTitle(),
	    		   		  list.get(DEFAULT_FEED_NUMBER).getDate(), 
	    		          list.get(DEFAULT_FEED_NUMBER).getImageUrl(), 
	    		          list.get(DEFAULT_FEED_NUMBER).getDescription());
		//refreshDisplay(response.get(1).getTitle(), response.get(1).getDate(),response.get(1).getImageUrl(),response.get(1).getDescription());
	}

	@Override
	public void responseImageFromBackground(Bitmap response) {
		Log.i("Rahooool","Setting Image From Background ");
		pb.setVisibility(ProgressBar.INVISIBLE);
		image.setImageBitmap(response);
		backgroundImage = response;
	}
}
