package com.example.nasaproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends Activity {

	private TextView title;
	private TextView date;
	private ImageView image;
	private TextView description;
	private Button buttonRefresh;
	private Button buttonSetWallpaper;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeUI();
        
        
    }
    
    private void initializeUI(){
    	
    	title = (TextView) findViewById(R.id.imageTitle);
    	date = (TextView) findViewById(R.id.imageDate);
    	image = (ImageView) findViewById(R.id.imageDisplay);
    	description = (TextView) findViewById(R.id.imageDesc);
    	buttonRefresh = (Button) findViewById(R.id.button_refresh);
    	buttonSetWallpaper = (Button) findViewById(R.id.button_setAsWallpaper);
    }
 
}
